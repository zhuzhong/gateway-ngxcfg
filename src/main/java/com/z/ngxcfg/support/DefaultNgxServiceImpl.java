/**
 * 
 */
package com.z.ngxcfg.support;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.z.ngxcfg.ConfigService;
import com.z.ngxcfg.NgxService;
import com.z.ngxcfg.upstream.NgxServerCfg;
import com.z.ngxcfg.upstream.NgxUpStreamCfg;

/**
 * @author sunff
 * 
 */
public class DefaultNgxServiceImpl implements NgxService {

	private ConfigService cfgService;

	public void setCfgService(ConfigService ngxCfgService) {
		this.cfgService = ngxCfgService;
	}

	@Override
	public void rewriteCfg(List<String> servers) {
		/**
		 * 所遵守的注册格式 rootpath(持久节点）/provider （持久节点）/服务信息（ip:port#context）(临时节点 ）
		 */
		if (servers == null) {
			return;
		}

		Map<String, NgxUpStreamCfg> ngxUpStreamCfgs = new HashMap<>();
		List<String> locationContexts = new ArrayList<>();

		for (String server : servers) {
			String[] serArray = server.split("#");
			String host = serArray[0];
			String context = serArray[1];
			// 设置location
			if (!locationContexts.contains(context)) {
				locationContexts.add(context);
			}

			// 设置upstream
			NgxUpStreamCfg n = new NgxUpStreamCfg();
			
			if (ngxUpStreamCfgs.get(context) == null) {
				n.setName(context);
				ngxUpStreamCfgs.put(context, n);
			} else {
				n = ngxUpStreamCfgs.get(context);
			}

			NgxServerCfg serverCfg = new NgxServerCfg();

			serverCfg.setAddress(host);
			n.addServer(serverCfg);

		}

		List<NgxUpStreamCfg> list = new ArrayList<>();
		for (NgxUpStreamCfg key : ngxUpStreamCfgs.values()) {
			list.add(key);
		}
		// 生成配置文件
		try {
			cfgService.writeLocationsCfg(locationContexts);
			cfgService.writeUpStreamCfg(list);
		} catch (IOException e) {

			e.printStackTrace();
		}

		// reloadNgxCfg();
	}

	private String shellPath;

	public void setShellPath(String shellPath) {
		this.shellPath = shellPath;
	}

	@Override
	public void reloadNgxCfg() {
		
		/*
		 * 这里执行会有权限的问题，需要解决
		 */
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(shellPath);
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();

		}
	}

}
