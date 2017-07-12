/**
 * 
 */
package com.z.ngxcfg.support;

import com.z.ngxcfg.NgxService;

/**
 * @author Administrator
 *
 */
public class NgxServiceImpl implements NgxService{

    @Override
    public void serverDynamicExpansion() {
        /*
         * 拉取注册中心服务,这个与gateway-dubbox的虚拟服务注册相对应，没有必在像dubbox那样将其注册搞的那么复杂，
         * 这里主要是为了传递nginx配置信息所必须的信息
         */
        
        /**
         * 所遵守的注册格式 rootpath(持久节点）/provider （持久节点）/服务信息（ip:port#context）(临时节点 ）
         */
        
        
        
       //生成配置文件
        
        
        //重启 nginx
        
    }

}
