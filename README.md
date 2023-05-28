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
async | 基于 RocketMQ 实现异步注解，解决 Spring @Async 注解服务重启丢失问题
bitop | 对 bit 位操作的封装，基于 Value Object 的设计对其进行封装，包括内存操作、数据操作等
command | 基于 DDD 实现的"写操作"封装，将 DDD 核心流程组件化、标准化
delay | 基于 RocketMQ 的延时队列实现的延时注解，通过增加注解的方式实现延时任务
enums | 对枚举的封装，包括基于 code 的枚举，枚举的持久化 和 枚举字典等
excelasbean | 将 Excel 当做 Java Bean 使用，提供 Excel 到 Bean 和 Bean 到 Excel 的转换
fault recovery | 基于Action类型自动选择异常恢复机制，Command 首先为 Retry，Query 首选为 Fallback
fegin | 对 FeignClient 和 FeignService 进行封装，实现 RPC 中的异常管理
idempotent|操作幂等性，基于注解实现操作幂等性，底层存储支持 redis 和 MySQL
joinInMemory | 使用注解，无需 Coding，在内存中完成数据 Join
loader | 基于注解实现延迟加载，主要应用于 Context 模式
msg | 基于事务消息表，实现业务操作和消息发送两者的一致性
query | 基于接口自动实现 QueryService，只需定义，无需编码
singlequery | 基于注解完成单表查询，在 QueryObject 上增加注解，实现单表的查询能力
splitter | 请求拆分，将大的请求拆分为多个小请求，用于降低服务压力
validator | 标准的验证组件，支持接口验证和业务验证
web | 基于注解自动实现 Controller
wide | 基于宽表实现多副本的最终一致性


##### service
DDD 中的支撑子域或通用子域，提供标准的服务实现，大多场景简单修改即可使用。支持的模块包括：


名称 | 功能 
---|---
like | 赞&踩服务，提供赞和踩的标准操作，同时提供领域事件、业务验证、缓存加速、异步处理、分库分表等高级特性
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


