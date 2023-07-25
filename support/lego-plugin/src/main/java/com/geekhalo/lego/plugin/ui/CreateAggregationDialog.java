package com.geekhalo.lego.plugin.ui;


import com.geekhalo.lego.plugin.creator.JavaFileCreator;
import com.geekhalo.lego.plugin.template.*;
import com.geekhalo.lego.plugin.util.Utils;
import com.intellij.ide.util.ClassFilter;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

import static com.geekhalo.lego.plugin.util.Utils.*;

public class CreateAggregationDialog extends JDialog {
    private Project project;
    private Module appModule;
    private Module domainModule;
    private Module infraModule;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel buttonsPanel;
    private JPanel buttons;
    private JPanel Form;
    private JPanel titlePanel;
    private JPanel aggPanel;
    private JPanel repositoryPanel;
    private JTextField aggPackage;
    private JButton selectPkgForAgg;
    private JTextField aggClassName;
    private JTextField aggParentClass;
    private JButton aggParentSelectButton;
    private JTextField repositoryPkg;
    private JButton repositoryPackageSelectButton;
    private JTextField commandRepository;
    private JTextField queryRepository;
    private JPanel applicationService;
    private JTextField applicationPkg;
    private JButton applicationPackageSelectButton;
    private JTextField commandApplication;
    private JTextField queryApplication;
    private JPanel domainEvent;
    private JTextField domainEventPkg;
    private JButton domainEventPackageSelectButton;
    private JTextField domainEventClass;
    private JTextField aggIdTextField;
    private JButton aggIdTypeSelectButton;
    private JTextField aggModuleName;
    private JTextField repositoryModule;
    private JCheckBox createQueryRepository;
    private JCheckBox createCommandRepository;
    private JTextField applicationModule;
    private JCheckBox createCommandApplication;
    private JCheckBox createQueryApplication;
    private JTextField abstractDomainEventModule;
    private JCheckBox createAbstractDomainEvent;

    private void init(Project project, String pkg){
        this.project = project;
        // 绑定模块信息
        bindModules();
        // 更新 包信息
        updateByPackage(pkg);

        // 聚合根包改变，更新其他包
        this.aggPackage.getDocument()
                .addDocumentListener(new DocumentUpdateListener(this::updateByPackage));

        // 选择 Agg 所在包
        selectPkgForAgg.addActionListener(e -> {
            PackageChooserDialog chooserDialog = new PackageChooserDialog("请选择包", project);
            if(chooserDialog.showAndGet()) {
                PsiPackage selectedPackage = chooserDialog.getSelectedPackage();
                if (selectedPackage != null) {
                    aggPackage.setText(selectedPackage.getQualifiedName());
                }
            }
        });

        // 选择 Agg 父类
        this.aggParentSelectButton.addActionListener(e -> {
            TreeClassChooserFactory factory = TreeClassChooserFactory.getInstance(project);
            // 设置过滤条件，只选择 public 类型的类
            ClassFilter classFilter = psiClass -> psiClass.getModifierList().hasExplicitModifier(PsiModifier.PUBLIC);
            // 弹出选择类的窗口
            TreeClassChooser chooser = factory.createWithInnerClassesScopeChooser("选择聚合根父类", GlobalSearchScope.allScope(project), classFilter, null);
            chooser.showDialog();
            // 获取用户选择的类
            PsiClass selectedClass = chooser.getSelected();
            // 如果用户选择了类，则在控制台输出类名
            if (selectedClass != null) {
                aggParentClass.setText(selectedClass.getQualifiedName());
            }
        });

        // 选择 Agg Id 类型
        this.aggIdTypeSelectButton.addActionListener(e->{
            TreeClassChooserFactory factory = TreeClassChooserFactory.getInstance(project);
            // 设置过滤条件，只选择 public 类型的类
            ClassFilter classFilter = psiClass -> psiClass.getModifierList().hasExplicitModifier(PsiModifier.PUBLIC);
            // 弹出选择类的窗口
            TreeClassChooser chooser = factory.createWithInnerClassesScopeChooser("选择聚合根主键", GlobalSearchScope.allScope(project), classFilter, null);
            chooser.showDialog();
            // 获取用户选择的类
            PsiClass selectedClass = chooser.getSelected();
            // 如果用户选择了类，则在控制台输出类名
            if (selectedClass != null) {
                aggIdTextField.setText(selectedClass.getQualifiedName());
            }
        });

        // 选择 repository 所在的包
        this.repositoryPackageSelectButton.addActionListener(e->{
            PackageChooserDialog chooserDialog = new PackageChooserDialog("请选择包", project);
            if(chooserDialog.showAndGet()) {
                PsiPackage selectedPackage = chooserDialog.getSelectedPackage();
                if (selectedPackage != null) {
                    repositoryPkg.setText(selectedPackage.getQualifiedName());
                }
            }
        });

        // 选择 application 所在包
        this.applicationPackageSelectButton.addActionListener(e->{
            PackageChooserDialog chooserDialog = new PackageChooserDialog("请选择包", project);
            if(chooserDialog.showAndGet()) {
                PsiPackage selectedPackage = chooserDialog.getSelectedPackage();
                if (selectedPackage != null) {
                    applicationPkg.setText(selectedPackage.getQualifiedName());
                }
            }
        });

        // 选择 领域事件所在包
        this.domainEventPackageSelectButton.addActionListener(e->{
            PackageChooserDialog chooserDialog = new PackageChooserDialog("请选择包", project);
            if(chooserDialog.showAndGet()) {
                PsiPackage selectedPackage = chooserDialog.getSelectedPackage();
                if (selectedPackage != null) {
                    domainEventPkg.setText(selectedPackage.getQualifiedName());
                }
            }
        });

        ;
        this.aggClassName.getDocument()
                .addDocumentListener(new DocumentUpdateListener(this::refreshByAggNameChanged));
    }

    /**
     * 绑定 Module <br />
     */
    private void bindModules() {
        this.aggModuleName.setText(this.domainModule.getName());
        this.repositoryModule.setText(this.domainModule.getName());
        this.applicationModule.setText(this.appModule.getName());
        this.abstractDomainEventModule.setText(this.domainModule.getName());
    }

    /**
     * 更新 pkg
     * @param pkg
     */
    private void updateByPackage(String pkg) {
        if (StringUtils.isNotEmpty(pkg)) {
            aggPackage.setText(pkg);
            repositoryPkg.setText(pkg);
            domainEventPkg.setText(pkg);

            String appPkg = Utils.domainPkgToApp(pkg);
            applicationPkg.setText(appPkg);
        }

    }

    private void refreshByAggNameChanged(String aggClass){
        this.commandRepository.setText(createCommandRepositoryByAgg(aggClass));

        this.queryRepository.setText(createQueryRepositoryByAgg(aggClass));

        this.commandApplication.setText(createCommandApplicationByAgg(aggClass));

        this.queryApplication.setText(createQueryApplicationByAgg(aggClass));

        this.domainEventClass.setText(createAbstractDomainEventByAgg(aggClass));
    }


    public CreateAggregationDialog(Project project, String pkg, Module appModule, Module domainModule, Module infraModule) {
        this.appModule = appModule;
        this.domainModule = domainModule;
        this.infraModule = infraModule;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        init(project, pkg);
    }

    private void onOK() {
        // add your code here
        createFile();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createFile(){
        // 创建 聚合根
        {
            AggregationTemplate.CreateAggregationContext context = new AggregationTemplate.CreateAggregationContext(this.aggPackage.getText(), this.aggClassName.getText());
            bindCommon(context);
            context.setParentClassFull(aggParentClass.getText());
            String content = AggregationTemplate.create(context);
            JavaFileCreator.createJavaFileInPackage(this.project, this.domainModule, this.aggPackage.getText(), this.aggClassName.getText(), content);
        }

        // 创建 CommandRepository
        if (createCommandRepository.isSelected()){
            RepositoryTemplate.CreateCommandRepositoryContext context = new RepositoryTemplate.CreateCommandRepositoryContext(this.repositoryPkg.getText(), this.commandRepository.getText());
            bindCommon(context);
            String commandContent = RepositoryTemplate.createCommand(context);
            JavaFileCreator.createJavaFileInPackage(this.project, this.domainModule,  this.repositoryPkg.getText(), this.commandRepository.getText(), commandContent);
        }

        // 创建 QueryRepository
        if (createQueryRepository.isSelected()){
            RepositoryTemplate.CreateQueryRepositoryContext context = new RepositoryTemplate.CreateQueryRepositoryContext(this.repositoryPkg.getText(), this.queryRepository.getText());
            bindCommon(context);
            String queryContent = RepositoryTemplate.createQuery(context);
            JavaFileCreator.createJavaFileInPackage(this.project, this.domainModule, this.repositoryPkg.getText(), this.queryRepository.getText(), queryContent);
        }

        // 创建 AbstractDomainEvent
        if (createAbstractDomainEvent.isSelected()){
            DomainEventTemplate.CreateAbstractDomainEventContext context = new DomainEventTemplate.CreateAbstractDomainEventContext(this.domainEventPkg.getText(), this.domainEventClass.getText());
            bindCommon(context);
            String domainContent = DomainEventTemplate.createAbstractEvent(context);
            JavaFileCreator.createJavaFileInPackage(this.project,  this.domainModule, this.domainEventPkg.getText(), this.domainEventClass.getText(), domainContent);
        }

        // 创建 CommandApplication
        if (createCommandApplication.isSelected()){
            ApplicationTemplate.CreateCommandApplicationContext context = new ApplicationTemplate.CreateCommandApplicationContext(this.applicationPkg.getText(), this.commandApplication.getText());
            bindCommon(context);
            context.setCommandRepositoryTypeFull(this.repositoryPkg.getText() + "." + this.commandRepository.getText());
            String content = ApplicationTemplate.createCommandApplication(context);
            JavaFileCreator.createJavaFileInPackage(this.project, this.appModule, this.applicationPkg.getText(), this.commandApplication.getText(), content);
        }

        // 创建 QueryApplication
        if (createQueryApplication.isSelected()){
            ApplicationTemplate.CreateQueryApplicationContext context = new ApplicationTemplate.CreateQueryApplicationContext(this.applicationPkg.getText(), this.queryApplication.getText());
            bindCommon(context);
            context.setQueryRepositoryTypeFull(this.repositoryPkg.getText() + "." + this.queryRepository.getText());
            String content = ApplicationTemplate.createQueryApplication(context);
            JavaFileCreator.createJavaFileInPackage(this.project, this.appModule, this.applicationPkg.getText(), this.queryApplication.getText(), content);
        }

    }

    private void bindCommon(CreateClassContext context){
        context.setIdTypeFull(this.aggIdTextField.getText());
        context.setAggTypeFull(this.aggPackage.getText() + "." + this.aggClassName.getText());
    }
}
