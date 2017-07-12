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
     *  更新nginx 配置问件
     * @param servers
     */
    public void updateNgxCfg(List<String> servers);
	/**
	 * nginx 配置重新加载
	 */
	 public void reloadNgxCfg();
}
