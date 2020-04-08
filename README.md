# cloud_demo
spring cloud 集成含有 spring cloud gateway，sentinel，nacos服务发现/配置

> 大致流程，开启Nacos服务 -> 开启sentinel dashboard控制台 -> 开启网关服务 -> 开启消费者1-> 服务提供者1

## [nacos 地址](http://127.0.0.1:8848/nacos/index.html)
### nacos [配置文件目录](source/nacos/)

## sentinel 控制台 
默认8080端口，便于观察接入sentinel的服务（包含网关、自己业务的微服务等）的请求情况、设置降级甚至熔断等策略,官方推荐500台一下服务可以采用控制台的方式，方便简单得对url资源进行操作


## spring cloud gateway
简称 gateway，本实例占用端口 9090。nacos 配合gateway可以自动进行服务转发，比如访问 http://localhost:9090/client-consumer1/echo?string=hello ,就可以访问nacos中注册服务名为client-consumer1的echo接口