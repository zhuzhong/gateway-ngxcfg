/**
 * 
 */
package com.z.ngxcfg.location;

import java.io.Serializable;

/**
 * 
 * Syntax:  location [ = | ~ | ~* | ^~ ] uri { ... }
    location @name { ... }
    Default:    —
    Context:    server, location
    
    
    https://nginx.org/en/docs/http/ngx_http_core_module.html#location
 * @author Administrator
 *
 */
@Deprecated
public class NgxLocationCfg implements Serializable{

    
    
    private String context;
    //proxy_pass, fastcgi_pass, uwsgi_pass, scgi_pass, and memcached_pass directives.
    
    
    
}
