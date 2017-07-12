/**
 * 
 */
package com.z.ngxcfg.upstream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Syntax:  upstream name { ... }
Default:    —
Context:    http
https://nginx.org/en/docs/http/ngx_http_upstream_module.html
 * @author Administrator
 *
 */
public class NgxUpStreamCfg implements Serializable {

    
    
    public static final String UPSTREAM_SUFFIX = "_server";

    /**
     * 
     */
    private static final long serialVersionUID = 6877470089020046453L;

    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder("upstream ");
        sb.append(name);
        sb.append(UPSTREAM_SUFFIX);
        sb.append("    { ");
        sb.append("\r\n");
        if(ipHash){
            sb.append(para_ip_hash);
        }
        for(NgxServerCfg serverCfg:servers){
            sb.append("     ");
            sb.append(serverCfg.toString());
            sb.append("\r\n");
        }
        if(keepalive!=null){
            sb.append(para_keepalive);
            sb.append("=");
            sb.append(keepalive);
            sb.append(";");
            sb.append("\r\n");
        }
        sb.append(" }");
        return sb.toString();
    }
    private String name;
   
    private List<NgxServerCfg> servers;

    
    /*
     * Syntax:  ip_hash;
    Default:    —
    Context:    upstream

     */
    public static final String para_ip_hash="ip_hash";
   /*
    * Syntax:   keepalive connections;
    Default:    —
    Context:    upstream
    This directive appeared in version 1.1.4.
    */    
    public static final String para_keepalive="keepalive";
    
    /*
    Syntax: hash key [consistent];
    Default:    —
    Context:    upstream
    This directive appeared in version 1.7.2.
    */
    public static final String para_hash_key="hash key ";
    
    /*
     * Syntax:  least_conn;
    Default:    —
    Context:    upstream
    This directive appeared in versions 1.3.1 and 1.2.2.
     */
    public static final String para_least_conn="least_conn";
    
    private boolean lastConn;
    
    private Integer keepalive;
    
    private boolean ipHash;

    public void setName(String name) {
        this.name = name;
    }

  

    public void setLastConn(boolean lastConn) {
        this.lastConn = lastConn;
    }

    public void setKeepalive(Integer keepalive) {
        this.keepalive = keepalive;
    }

    public void setIpHash(boolean ipHash) {
        this.ipHash = ipHash;
    }
    
    
    public void setServers(List<NgxServerCfg> servers) {
        this.servers = servers;
    }
    
    public void addServer(NgxServerCfg serverCfg){
        if(servers==null){
            servers=new ArrayList<>();
        }
        servers.add(serverCfg);
    }



    public String getName() {
        return name;
    }

    
    
}
