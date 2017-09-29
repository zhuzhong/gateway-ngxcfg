/**
 * 
 */
package com.z.ngxcfg.support;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.z.ngxcfg.NgxService;
import com.z.ngxcfg.ServerMonitorService;

/**
 * 直接获取dubbox提供的restful生成nginx的配置文件
 * 
 * @author sunff
 *
 */
public class RestfulServerMonitorServiceImpl implements ServerMonitorService {

	private String rootPath;

	private String zkServers;

	private NgxService ngxService;

	public void setNgxService(NgxService ngxService) {
		this.ngxService = ngxService;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public void setZkServers(String zkServers) {
		this.zkServers = zkServers;
	}

	@Override
	public void serverUrlListener() {
		runaway(zkClient, rootPath);
	}

	public void init() {
		zkClient = new ZkClient(zkServers, 5000);
		if (!rootPath.startsWith(SLASH)) {
			rootPath = SLASH + rootPath;
		}

		serverUrlListener();
	}

	private static final Logger logger = LoggerFactory.getLogger(RestfulServerMonitorServiceImpl.class);

	private static final String REST = "rest";

	private static final String PROVIDERS = "providers";

	private static final String REST_SLASH = REST + "://";
	private static final String SLASH = "/";
	// private static final String UTF_8 = "utf-8";

	private ZkClient zkClient;

	private void runaway(final ZkClient zkClient, final String path) {
		List<String> restfulserver = new ArrayList<String>();
		zkClient.unsubscribeAll();
		// ConcurrentHashMap<String, List<String>> newHosts = new
		// ConcurrentHashMap<String, List<String>>();
		zkClient.subscribeChildChanges(path, new IZkChildListener() {

			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				/*
				 * System.out.println(parentPath + " 's child changed, currentChilds:" +
				 * currentChilds);
				 */
				logger.info("{}'s child changed, currentChilds:{}", parentPath, currentChilds);
				// 一级节点的子节点发生变化
				runaway(zkClient, path); // 重新再来

			}

		});

		List<String> firstGeneration = zkClient.getChildren(path); // 二级节点
																	// /dubbo-online/com.aldb.test.Testapi
		if (firstGeneration != null && firstGeneration.size() > 0) {
			for (String child : firstGeneration) {
				String firstNextPath = path + SLASH + child;
				zkClient.subscribeChildChanges(firstNextPath, new IZkChildListener() {

					public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
						/*
						 * System.out.println(parentPath + " 's child changed, currentChilds:" +
						 * currentChilds);
						 */
						logger.info("{}'s child changed, currentChilds:{}", parentPath, currentChilds);
						// 2级节点的子节点发生
						runaway(zkClient, path); // 重新再来

					}

				});

				List<String> secondGeneration = zkClient.getChildren(firstNextPath); // 三级子节点
																						// /dubbo-online/com.aldb.test.Testapi/providers
				if (secondGeneration != null && secondGeneration.size() > 0) {
					for (String secondChild : secondGeneration) {
						if (secondChild.startsWith(PROVIDERS)) {
							String secondNextPath = firstNextPath + SLASH + secondChild;
							zkClient.subscribeChildChanges(secondNextPath, new IZkChildListener() {

								public void handleChildChange(String parentPath, List<String> currentChilds)
										throws Exception {
									/*
									 * System.out .println(parentPath + " 's child changed, currentChilds:" +
									 * currentChilds);
									 */
									logger.info("{}'s child changed, currentChilds:{}", parentPath, currentChilds);
									// 3级节点的子节点发生
									runaway(zkClient, path); // 重新再来

								}

							});

							List<String> thirdGeneration = zkClient.getChildren(secondNextPath);// 4级子节点
																								// /dubbo-online/com.aldb.test.Testapi/providers/rest://localhost:8080
							if (thirdGeneration != null && thirdGeneration.size() > 0) {
								for (String thirdChild : thirdGeneration) {
									if (thirdChild.startsWith(REST)) {
										/*
										 * 样例 rest://10.148.16.27:8480/magicmall/ com.aldb.magicmall.facade.api.
										 * MallFacadeService
										 */
										ServiceProvider sp = new ServiceProvider(thirdChild);
										String contextPath = sp.getContextPath();
										String host = sp.getHost();

										String server = host + "#" + contextPath;
										if (!restfulserver.contains(server))
											restfulserver.add(server);
									}
								}
							}
						}
					}
				}
			}

		}

		restartNgx(restfulserver);
	}

	/**
	 * 这里重启ngx,其实主要作的：重新生成新的配置文件，并加载新的配置文件
	 * 
	 * @param serverUrls
	 */
	private void restartNgx(List<String> serverUrls) {
		ngxService.rewriteCfg(serverUrls);
		 ngxService.reloadNgxCfg();
	}

	private static class ServiceProvider {

		private String host;
		private String contextPath;
		private String provider;

		public ServiceProvider(String provider) {
			try {
				this.provider = URLDecoder.decode(provider, "utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("地址解码错误{}", e);
				this.provider = provider;
			}
			parse();
		}

		private void parse() {
			String subString = provider.substring(REST_SLASH.length());

			int indexOfFirstSlash = subString.indexOf(SLASH);

			host = subString.substring(0, indexOfFirstSlash);
			String subSubString = subString.substring(indexOfFirstSlash + 1);
			int indexOfSecondSlash = subSubString.indexOf(SLASH);
			contextPath = subSubString.substring(0, indexOfSecondSlash);
		}

		public String getHost() {
			return host;
		}

		public String getContextPath() {
			return contextPath;
		}

	}

}
