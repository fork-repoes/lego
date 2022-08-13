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
joinInMemory | 使用注解，无需 Coding，在内存中完成数据 Join




##### service
DDD 中的支撑子域或通用子域，提供标准的服务实现，大多场景简单修改即可使用。支持的模块包括：


名称 | 功能 
---|---
tiny-url | 短链服务，将长链接转换为短链接，主要用于 App Push 或 短信发送场景




#### 学习沟通
如果对项目感兴趣，不想错过精粹内容，可以：
1. 知识星球找 "草根架构师的成长史"
2. 加我个人微信"litao851025"
3. 关注公众号 "geekhalo"

知识星球：
![知识星球](http://images.geekhalo.com/qrcode/zsxq.jpeg)


个人微信：
![个人微信](http://images.geekhalo.com/qrcode/litao851025_wx.jpeg)


公众号：
![个人微信](http://images.geekhalo.com/qrcode/qrcode.jpg)


