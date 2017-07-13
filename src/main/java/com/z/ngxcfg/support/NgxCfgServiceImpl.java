/**
 * 
 */
package com.z.ngxcfg.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.z.ngxcfg.NgxCfgService;
import com.z.ngxcfg.upstream.NgxUpStreamCfg;

/**
 * @author Administrator
 * 
 */
public class NgxCfgServiceImpl implements NgxCfgService {

    private String upsteramCfgName = "/up.conf";
    private String locationCfgName = "/lo.conf";

    private String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setUpsteramCfgName(String upsteramCfgName) {
        if (!upsteramCfgName.startsWith("/")) {
            upsteramCfgName = "/" + upsteramCfgName;
        }
        this.upsteramCfgName = upsteramCfgName;
    }

    public void setLocationCfgName(String locationCfgName) {
        if (!locationCfgName.startsWith("/")) {
            locationCfgName = "/" + locationCfgName;
        }
        this.locationCfgName = locationCfgName;
    }

    @Override
    public void writeUpStreamCfg(List<NgxUpStreamCfg> ngxUpStreamCfgs) throws IOException {
        if (ngxUpStreamCfgs == null) {
            return;
        }
        filePath = checkFilepath(filePath);
        checkDirExists(filePath);
        File f = new File(filePath + upsteramCfgName);
        /*
         * if (f.exists()) { f.renameTo(new
         * File(filePath,f.getName()+"."+System.currentTimeMillis())); }
         */
        backFile(f);
        FileOutputStream fos = new FileOutputStream(f);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");

        for (NgxUpStreamCfg cfg : ngxUpStreamCfgs) {
            writeSingleUpStream(cfg, osw);
        }

        osw.close();

    }

    private String checkFilepath(String filePath) {
        if (filePath.endsWith("/")) {
            filePath = filePath.substring(0, filePath.length() - 1);
        }

        return filePath;
    }

    private void checkDirExists(String filepath) {
        File f = new File(filepath);
        if (f.exists()) {
            if (f.isDirectory()) {
                System.out.println("dir exists");
            } else {

                throw new RuntimeException("the same name file exists, can not create dir");
            }
        } else {
            f.mkdirs();
        }

    }

    private void backFile(File f) {
        if (f.exists() && f.isFile()) {
            f.renameTo(new File(f.getParent(), f.getName() + "." + System.currentTimeMillis()));
        }
    }

    @Override
    public void writeLocationsCfg(List<String> names) throws IOException {

        if (names == null) {
            return;
        }
        filePath = checkFilepath(filePath);
        checkDirExists(filePath);
        File f = new File(filePath + locationCfgName);
        backFile(f);
        FileOutputStream fos = new FileOutputStream(f);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        for (String name : names) {
            writeSingleLocation(name, osw);
        }
        osw.close();

    }

    private void writeSingleUpStream(NgxUpStreamCfg upStreamCfg, OutputStreamWriter osw) throws IOException {
        osw.write(upStreamCfg.toString());
        osw.write(" \r\n");
        osw.write(" \r\n");
        osw.write(" \r\n");
        osw.flush();
    }

    private void writeSingleLocation(String name, OutputStreamWriter locationOsw) throws IOException {
        locationOsw.write("location /");
        locationOsw.write(name);
        locationOsw.write(" { \r\n");
        locationOsw.write("  proxy_set_header Host $host:$server_port; \r\n");
        locationOsw.write("  proxy_set_header X-Real-IP $remote_addr;\r\n");
        locationOsw.write("  proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;\r\n");

        locationOsw.write("  proxy_buffering off;\r\n");

        locationOsw.write("  proxy_pass http://");
        locationOsw.write(name);
        locationOsw.write(NgxUpStreamCfg.UPSTREAM_SUFFIX);
        locationOsw.write("; \r\n");
        locationOsw.write(" } \r\n");

        locationOsw.write(" \r\n");
        locationOsw.write(" \r\n");
        locationOsw.write(" \r\n");
        locationOsw.flush();

    }

}
