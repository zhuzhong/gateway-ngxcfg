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
public interface ConfigService {

    void writeUpStreamCfg(List<NgxUpStreamCfg> ngxUpStreamCfgs) throws IOException;

    void writeLocationsCfg(List<String> names) throws IOException;

}