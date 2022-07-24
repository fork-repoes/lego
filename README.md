# 乐高

#### 介绍
软件开发就像玩乐高。
1. 明确要搭建的作品（明确功能目标）
2. 找到所需要的积木块（开发所需要的组件）
3. 将积木块组装起来（将业务组件连接起来，完成业务流程）

当目标改变时：
1. 将作品还原成 积木块（系统需求发生变更）
2. 寻找新的积木块（开发新的组件）
3. 将新旧积木块组装成作品（将新旧功能组件连接起来，完成新需求）

#### 软件架构
核心模块基于 Spring boot 进行打造，提供标准的 starter 扩展，便于使用。

#### 模块说明

##### support
对日常开发中常见场景进行封装，以方便其使用。核心模块包括：

名称 | 功能 
---|---
splitter | 请求拆分，将大的请求拆分为多个小请求，用于降低服务压力




##### service
DDD 中的支撑子域或通用子域，提供标准的服务实现，大多场景简单修改即可使用。支持的模块包括：


名称 | 功能 
---|---
tiny-url | 短链服务，将长链接转换为短链接，主要用于 App Push 或 短信发送场景




#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
