#!/bin/bash
function updateCode(){
    git remote prune origin && \
    git checkout develop && git pull origin develop && git checkout master && git pull origin master
}
function featureStart(){
	updateCode && \
        mvn clean \
	    -DenableFeatureVersions=true \
	    jgitflow:feature-start
}

function featureFinish(){
	updateCode && \
    mvn clean \
	    -Dmaven.test.skip=true \
	    -Dmaven.javadoc.skip=true \
	    jgitflow:feature-finish && \
        git push origin develop
}

function hotfixStart(){
    updateCode && \
    	mvn clean \
    		-DautoVersionSubmodules=true \
    		jgitflow:hotfix-start
}

function hotfixFinish(){
    #branch=`git symbolic-ref --short -q HEAD`
    branch=`git branch -r | grep hotfix`
    emails=`git log origin/master..$branch --pretty=format:"%ce" | uniq`
    read -p "please enter hotfix tag comment: " comment
    updateCode && \
	mvn clean \
	    -Dmaven.test.skip=true \
	    -Dmaven.javadoc.skip=true \
	    -DautoVersionSubmodules=true \
    	-DnoDeploy=true \
	    jgitflow:hotfix-finish && \
	    git checkout develop && git push origin develop && git checkout master && git push origin master
    tag=$(git describe --tags `git rev-list --tags --max-count=1`)
    git tag -f $tag -m "$comment"
    git push origin --tags
    echo $tag
    title="${branch},tag已打好${tag}"
    sendemail $branch $title $emails
    /usr/bin/osascript -e "display notification \" $title \" with title \"$branch\""
}

function release(){
    updateCode && \
	mvn clean \
	    -DautoVersionSubmodules=true \
	    jgitflow:release-start
	if [ $? -ne 0 ]; then
	    echo "release-start error, will terminate"
	    exit 1
	fi
	mvn clean \
	    -Dmaven.test.skip=true \
	    -Dmaven.javadoc.skip=true \
	    -DautoVersionSubmodules=true \
	    -DnoDeploy=true \
	    jgitflow:release-finish
	if [ $? -ne 0 ]; then
	    echo "release-finish error"
	    exit 1
	fi
	git push origin develop && git checkout master && git push origin master

}

function featureFinishAndRelease() {
    emails=`git log origin/master..$branch --pretty=format:"%ce" | uniq`
    featureFinish
    branch=`git log --oneline -1 | awk -F"'" '{print $2}'`
    echo "分支名称: $branch"
    echo "邮件列表地址: ${emails}"
    release
    read -p "please enter feature tag comment: " comment
    tag=$(git describe --tags `git rev-list --tags --max-count=1`)
    date=`date "+%Y-%m-%d#%H:%M:%S"`
    git tag -f $tag -m "content: ${comment}, time: ${date}"
    git push origin --tags
    content="feature分支-${branch}已合并打tag"
    subject="${branch}"
    sendemail $subject $content $emails
    /usr/bin/osascript -e "display notification \" $subject \" with title \"$content\""
}

function deploy(){
	mvn clean \
		-Dmaven.test.skip=true \
		-T 1C \
		-Dmaven.compile.fork=true \
		deploy
}

function sendemail() {
    addr=`git config --global user.email`
    echo $2 | mail -s $1 $3 $addr
}

function help(){
    echo "\n=========================EE脚本====================="
	echo "|                 1.   拉新功能分支                "
	echo "|                 2.   合并新功能分支（仅限Master权限的人选择） "
	echo "|                 3.   拉BUG修复分支"
	echo "|                 4.   完成BUG分支合并且推送tag（仅限Master权限的人选择）"
	echo "|                 5.   发版本版本打tag（仅限Master权限的人选择）"
	echo "|                 6.   部署Jar包到私服"
	echo "|                 7.   合并新功能分支并且发版本打tag(合并了步骤2,4 仅限Master权限的人选择)"
    echo "=====================================================\n"
}

function action(){
	choice=$1
    echo "Your choice: $choice"
	case $choice in
		1 )
			featureStart
			;;
		2 )
			featureFinish
			;;
		3 )
			hotfixStart
			;;
		4 )
			hotfixFinish
			;;
		5 )
			release
			;;
		6 )
			deploy
			;;
		7 )
		    featureFinishAndRelease
		    ;;
		* )
			help
			;;
	esac
}

if [ $# -eq 0 ]
then
	help
	read -p "请选择对应的操作：" choice
	action $choice
else
	action $1
fi
