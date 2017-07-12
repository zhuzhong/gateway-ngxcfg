/**
 * 
 */
package com.z.ngxcfg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.z.ngxcfg.support.NginxCfgServiceImpl;
import com.z.ngxcfg.upstream.NgxServerCfg;
import com.z.ngxcfg.upstream.NgxUpStreamCfg;

/**
 * @author Administrator
 *
 */
public class NginxCfgServiceImplTest {

    static NgxCfgService ngs;

    @BeforeClass
    public static void init() {
        ngs = new NginxCfgServiceImpl();
    }

    @Test
    public void writeUpStreamCfg() {
        String filePath = "C:/Users/Administrator/Desktop/nginx-1.10.3/";
        NgxUpStreamCfg c1 = initStreamCfg("c1");
        NgxUpStreamCfg c2 = initStreamCfg("c2");
        NgxUpStreamCfg c3 = initStreamCfg("c3");
        NgxUpStreamCfg c4 = initStreamCfg("c4");
        List<NgxUpStreamCfg> ngxUpStreamCfgs = new ArrayList<>();
        ngxUpStreamCfgs.add(c1);
        ngxUpStreamCfgs.add(c2);
        ngxUpStreamCfgs.add(c3);
        ngxUpStreamCfgs.add(c4);
        try {
            ngs.writeUpStreamCfg(filePath, ngxUpStreamCfgs);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("oook");
    }

    @Test
    public void writeLocationsCfg(){
        String filePath = "C:/Users/Administrator/Desktop/nginx-1.10.3";

        List<String> ngxUpStreamCfgs = new ArrayList<>();
        ngxUpStreamCfgs.add("c1");
        ngxUpStreamCfgs.add("c2");
       // ngxUpStreamCfgs.add("c3");
        ngxUpStreamCfgs.add("c4");
        try {
            ngs.writeLocationsCfg(filePath, ngxUpStreamCfgs);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("oook");
    }
    private NgxUpStreamCfg initStreamCfg(String name) {
        NgxUpStreamCfg upStreamCfg = new NgxUpStreamCfg();
        upStreamCfg.setName(name);

        NgxServerCfg serverCfg = new NgxServerCfg();
        serverCfg.setAddress("192.168.1.101:80");
        serverCfg.setWeight(1);
        serverCfg.setMaxFails(3);
        serverCfg.setFailTimeout(20);

        upStreamCfg.addServer(serverCfg);

        serverCfg = new NgxServerCfg();
        serverCfg.setAddress("192.168.1.102:80");
        serverCfg.setWeight(1);
        serverCfg.setMaxFails(3);
        serverCfg.setFailTimeout(20);

        upStreamCfg.addServer(serverCfg);
        serverCfg = new NgxServerCfg();
        serverCfg.setAddress("192.168.1.103:80");
        serverCfg.setWeight(2);
        serverCfg.setMaxFails(3);
        serverCfg.setFailTimeout(20);
        upStreamCfg.addServer(serverCfg);

        return upStreamCfg;
    }
}
