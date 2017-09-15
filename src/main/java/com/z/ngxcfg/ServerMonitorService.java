/**
 * 
 */
package com.z.ngxcfg;

/**
 * @author Administrator
 *
 */
public interface ServerMonitorService {

    /**
     *  该服务主要责任
     *  1.监听 zookeeper注册服务中心，从上面拉取相应的后端服务的地址信息，
     *  即完成后端server的动态上下线;
     *  
     *  2.根据动态的server内容生成nginx 配置文件；
     *  
     *  3.执行 nginx -s reload命令，使nginx 重新加载配置文件
     */   
    public void  serverUrlListener();  //后端服务的动态扩缩
    
    
}
