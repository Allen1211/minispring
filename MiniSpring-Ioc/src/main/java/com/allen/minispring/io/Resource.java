package com.allen.minispring.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName Resource
 * @Description 定义一个可读取的资源 可以是配置文件，class文件，URL资源
 * @Author XianChuLun
 * @Date 2020/9/5
 * @Version 1.0
 */
public interface Resource {

    /**
     * 获取资源文件对象
     * @return 文件对象
     */
    File getFile();

    /**
     * 资源是否存在
     * @return 资源是否存在
     */
    boolean isExist();

    /**
     * 获取资源路径
     * @return 资源路径
     */
    String getPath();

    /**
     * 获取输入流
     * @return 资源输入流
     * @throws IOException 获取失败
     */
    InputStream getInputStream() throws IOException;
}
