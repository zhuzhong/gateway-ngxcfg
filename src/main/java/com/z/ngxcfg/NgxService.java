/**
 * 
 */
package com.z.ngxcfg;

import java.util.List;


/**
 * @author sunff
 *
 */
public interface NgxService {

    /**
     *  重新生成nginx 配置文件
     * @param servers
     */
    public void rewriteCfg(List<String> servers);
	/**
	 * nginx 配置重新加载
	 */
	 public void reloadNgxCfg();
}
