## 第一种：通过Data ID与profile实现。

优点：这种方式与Spring Cloud Config的实现非常像，用过Spring Cloud Config的用户，可以毫无违和感的过渡过来，由于命名规则类似，所以要从Spring Cloud Config中做迁移也非常简单。  
缺点：这种方式在项目与环境多的时候，配置内容就会显得非常混乱。配置列表中会看到各种不同应用，不同环境的配置交织在一起，非常不利于管理。  
建议：项目不多时使用，或者可以结合Group对项目根据业务或者组织架构做一些拆分规划。  
## 第二种：通过Group实现。

优点：通过Group按环境讲各个应用的配置隔离开。可以非常方便的利用Data ID和Group的搜索功能，分别从应用纬度和环境纬度来查看配置。  
缺点：由于会占用Group纬度，所以需要对Group的使用做好规划，毕竟与业务上的一些配置分组起冲突等问题。  
建议：这种方式虽然结构上比上一种更好一些，但是依然可能会有一些混乱，主要是在Group的管理上要做好规划和控制。  
## 第三种：通过Namespace实现。

优点：官方建议的方式，通过Namespace来区分不同的环境，释放了Group的自由度，这样可以让Group的使用专注于做业务层面的分组管理。同时，Nacos控制页面上对于Namespace也做了分组展示，不需要搜索，就可以隔离开不同的环境配置，非常易用。  
缺点：没有啥缺点，可能就是多引入一个概念，需要用户去理解吧。  
建议：直接用这种方式长远上来说会比较省心。虽然可能对小团队而言，项目不多，第一第二方式也够了，但是万一后面做大了呢？  


## 配置加载的优先级
当我们加载多个配置的时候，如果存在相同的key时，我们需要深入了解配置加载的优先级关系。  

在使用Nacos配置的时候，主要有以下三类配置：  
A: 通过spring.cloud.nacos.config.shared-dataids定义的共享配置  
B: 通过spring.cloud.nacos.config.ext-config[n]定义的加载配置  
C: 通过内部规则（spring.cloud.nacos.config.prefix、spring.cloud.nacos.config.file-extension、spring.cloud.nacos.config.group这几个参数）拼接出来的配置  
A < B < C  