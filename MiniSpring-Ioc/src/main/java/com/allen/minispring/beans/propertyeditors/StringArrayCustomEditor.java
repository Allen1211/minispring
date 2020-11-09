package com.allen.minispring.beans.propertyeditors;

import com.allen.minispring.utils.ReflectionUtil;

import java.beans.PropertyEditorSupport;
import java.util.Objects;

/**
 * @ClassName StringArrayCustomEditor
 * @Description 将字符串形式表示的数组解析成对应的类型数组
 * @Author XianChuLun
 * @Date 2020/11/7
 * @Version 1.0
 */
public class StringArrayCustomEditor extends PropertyEditorSupport {

    protected final Class<?> requireType;

    protected final Class<?> componentType;

    protected static final String DEFAULT_SEPARATOR = ",";

    public StringArrayCustomEditor(Class<?> requireType) {
        this.requireType = requireType;
        this.componentType = requireType.getComponentType();
        if (componentType == null) {
            throw new IllegalArgumentException("requireType is not a type of Array");
        }
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (Objects.isNull(text)) {
            super.setValue(null);
        }
        String[] strArr = text.split(DEFAULT_SEPARATOR);
        Object val = ReflectionUtil.parseStrArrToTypeArr(strArr, componentType);
        super.setValue(val);
    }

}
