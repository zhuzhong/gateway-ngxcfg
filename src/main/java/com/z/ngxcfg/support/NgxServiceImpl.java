/**
 * 
 */
package com.z.ngxcfg.support;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.z.ngxcfg.NgxCfgService;
import com.z.ngxcfg.NgxService;
import com.z.ngxcfg.upstream.NgxServerCfg;
import com.z.ngxcfg.upstream.NgxUpStreamCfg;

/**
 * @author sunff
 * 
 */
public class NgxServiceImpl implements NgxService {

	private String filePath;

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public void loadBalanceNgx(List<String> servers) {
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
			n.setName(context);
			if (ngxUpStreamCfgs.get(context) == null) {
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
			ngxCfgService.writeLocationsCfg(filePath, locationContexts);
			ngxCfgService.writeUpStreamCfg(filePath, list);
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		// 重启 nginx

	}

	private NgxCfgService ngxCfgService;

	public void setNgxCfgService(NgxCfgService ngxCfgService) {
		this.ngxCfgService = ngxCfgService;
	}

}
