/**
 * 
 */
package com.z.ngxcfg.upstream;

import java.io.Serializable;

/**
 * 
 * Syntax: server address [parameters]; Default: — Context: upstream
 * https://nginx.org/en/docs/http/ngx_http_upstream_module.html
 * 
 * @author Administrator
 *
 */
public class NgxServerCfg implements Serializable,Comparable<NgxServerCfg> {

    /**
     * 
     */
    private static final long serialVersionUID = 8018367777008291947L;

    
    private String address;
    private Integer weight;
    private Integer maxConns;
    private Integer maxFails;
    private Integer failTimeout;// 单位秒 形式 fail_timeout=30s
    private boolean backup;
    private boolean down;
    private boolean resolve;
    private String route;

    private String service;

    private Integer slowStart;// 单位秒 15s

    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("server ");
        sb.append(address);
        if (weight != null) {
            sb.append(" ");
            sb.append(para_weight);
            sb.append("=");
            sb.append(this.weight);
        }

        if (maxConns != null) {
            sb.append(" ");
            sb.append(para_max_conns);
            sb.append("=");
            sb.append(maxConns);
        }

        if (maxFails != null) {
            sb.append(" ");
            sb.append(para_max_fails);
            sb.append("=");
            sb.append(maxFails);
        }

        if (failTimeout != null) {
            sb.append(" ");
            sb.append(para_fail_timeout);
            sb.append("=");
            sb.append(failTimeout);
            sb.append("s");
        }
        if (backup) {
            sb.append(" ");
            sb.append(para_backup);
        }
        if (down) {
            sb.append(" ");
            sb.append(para_down);
        }

        if (resolve) {
            sb.append(" ");
            sb.append(para_resolve);
        }

        if (route != null) {
            sb.append(" ");
            sb.append(para_route);
            sb.append("=");
            sb.append(route);

        }

        if (service != null) {
            sb.append(" ");
            sb.append(para_service);
            sb.append("=");
            sb.append(service);

        }
        if (slowStart != null) {
            sb.append(" ");
            sb.append(para_slow_start);
            sb.append("=");
            sb.append(slowStart);
            sb.append("s");
        }
        sb.append(" ;");
        
        return sb.toString();
    }

    

    public static final String para_weight = "weight";
    public static final String para_max_conns = "max_conns";
    public static final String para_max_fails = "max_fails";
    public static final String para_fail_timeout = "fail_timeout";
    public static final String para_backup = "backup";
    public static final String para_down = "down";
    public static final String para_resolve = "resolve";
    public static final String para_route = "route";
    public static final String para_service = "service";
    public static final String para_slow_start = "slow_start";

   
    public void setAddress(String address) {
        this.address = address;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setMaxConns(Integer maxConns) {
        this.maxConns = maxConns;
    }

    public void setMaxFails(Integer maxFails) {
        this.maxFails = maxFails;
    }

    public void setFailTimeout(Integer failTimeout) {
        this.failTimeout = failTimeout;
    }

    public void setBackup(boolean backup) {
        this.backup = backup;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setResolve(boolean resolve) {
        this.resolve = resolve;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setSlowStart(Integer slowStart) {
        this.slowStart = slowStart;
    }

	@Override
	public int compareTo(NgxServerCfg o) {
		// TODO Auto-generated method stub
		return this.address.compareTo(o.address);
	}

}
