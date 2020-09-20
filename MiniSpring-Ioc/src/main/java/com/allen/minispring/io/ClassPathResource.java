package com.allen.minispring.io;

import com.allen.minispring.utils.ClassUtil;

import java.io.*;

/**
 * @ClassName ClassPathResource
 * @Description 类路径下的资源文件
 * @Author XianChuLun
 * @Date 2020/9/6
 * @Version 1.0
 */
public class ClassPathResource implements Resource {

    private final File file;

    private final boolean isExist;

    private final String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this.path = path;
        this.file = new File(path);
        this.isExist = file.exists();
        this.classLoader = ClassUtil.getDefaultClassLoader();
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public boolean isExist() {
        return isExist;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (this.classLoader != null) {
            return this.classLoader.getResourceAsStream(this.path);
        } else if (this.file != null) {
            return new BufferedInputStream(new FileInputStream(file));
        }
        return null;

    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
