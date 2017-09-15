/**
 * 
 */
package com.z.ngxcfg.support;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.z.ngxcfg.NgxService;
import com.z.ngxcfg.ServerMonitorService;

/**
 * @author Administrator
 * 
 */
public class ServerMonitorServiceImpl implements ServerMonitorService {

    private String rootPath;

    private String zkServers;
    private NgxService ngxService;

    private static final String GATEWAY = "/provider";

    private static final Logger logger = LoggerFactory.getLogger(ServerMonitorServiceImpl.class);
    /*
     * private String context;
     * 
     * private String ip; private String port;
     */
    private ZkClient zkClient = null;
    private String SERVICE_PATH = null;

    public void init() {
        if (rootPath == null) {
            throw new RuntimeException("rootpath must not be null!");
        }
        if (!rootPath.startsWith("/")) {
            rootPath = "/" + rootPath;
        }

        SERVICE_PATH = rootPath + GATEWAY;// 服务节点路径
        zkClient = new ZkClient(zkServers);

        serverUrlListener();
    }

    @Override
    public void serverUrlListener() {
        /*
         * 拉取注册中心服务,这个与gateway-dubbox的虚拟服务注册相对应，没有必要像dubbox那样将其注册搞的那么复杂，
         * 这里主要是为了传递nginx配置信息所必须的信息
         */
        boolean serviceExists = zkClient.exists(SERVICE_PATH);
        if (serviceExists) {
            List<String> serverList;
            serverList = zkClient.getChildren(SERVICE_PATH);
            if (serverList != null && serverList.size() > 0) {
                logger.info("get service...");
                restartNgx(serverList);
            }else{
                logger.warn("service not exist!");
            }
        } else {
            // throw new RuntimeException("service not exist!");
            logger.warn("service not exist!");
        }

        // 注册事件监听
        zkClient.subscribeChildChanges(SERVICE_PATH, new IZkChildListener() {
            // @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
            	restartNgx(currentChilds);
            }
        });

    }


    /**
     * 这里重启ngx,其实主要作的：重新生成新的配置文件，并加载新的配置文件
     * @param serverUrls
     */
    private void restartNgx(List<String> serverUrls) {
        ngxService.rewriteCfg(serverUrls);
        ngxService.reloadNgxCfg();
    }
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public void setZkServers(String zkServers) {
        this.zkServers = zkServers;
    }

    public void setNgxService(NgxService ngxService) {
        this.ngxService = ngxService;
    }

}
