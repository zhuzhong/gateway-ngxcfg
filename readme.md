#gateway-ngxcfg


##目的

使用nginx作为反向代理的后端服务器，只能通过手动进行扩缩，无法动态扩缩。为了解决这个问题，可以采用

- LUA脚本
- 采用nginx plus

但是这两个要求对于我来说，都不可行，所以只能自造轮子。该轮子主要是为了解决项目[gateway-dubbox](https://github.com/zhuzhong/gateway-dubbox.git)的负载均衡及动态扩缩问题。


##设计思路

设计思路主要参考 [http://blog.csdn.net/akin_zhou/article/details/50373414](http://blog.csdn.net/akin_zhou/article/details/50373414)


1. 从注册中心zookeeper拉取相应的服务列表；
	从注册中心拉取相应的服务列表，主要是为了获取nginx的配置信息需要服务地址及端口相应的信息。
	
	
1. 生成nginx的配置文件；
   生成nginx的配置信息，主要分为两部分
	
	- upstream部分
    

	- location部分
	- 这两部分以include的形式引入nginx的主配置文件
  
2. 调用shell脚本，nginx重新加载配置文件








