package com.z.ngxcfg;

import java.io.IOException;
import java.util.List;

import com.z.ngxcfg.upstream.NgxUpStreamCfg;

/**
 * http://blog.csdn.net/akin_zhou/article/details/50373414
 * 
 * @author Administrator
 *
 */
public interface NgxCfgService {

    public void writeUpStreamCfg(String filePath, List<NgxUpStreamCfg> ngxUpStreamCfgs) throws IOException;

    public void writeLocationsCfg(String filePath, List<String> contexts) throws IOException;

}