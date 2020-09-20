package com.allen.minispring.utils;

import java.io.Closeable;

/**
 * @ClassName IOUtils
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/20
 * @Version 1.0
 */
public class IOUtil {

    private IOUtil() {}

    public static void closeSilently(Closeable ...closeables){
        for (Closeable closeable : closeables){
            try {
                closeable.close();
            }catch(Exception e){
                // ignore
            }
        }
    }

}
