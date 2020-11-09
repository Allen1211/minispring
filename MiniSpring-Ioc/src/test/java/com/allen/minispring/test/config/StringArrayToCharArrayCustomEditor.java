package com.allen.minispring.test.config;

import com.allen.minispring.beans.propertyeditors.StringArrayCustomEditor;
import com.allen.minispring.utils.ReflectionUtil;

import java.util.Arrays;
import java.util.Objects;

/**
 * @ClassName StringArrayToCharArrayCustomEditor
 * @Description
 * @Author XianChuLun
 * @Date 2020/11/9
 * @Version 1.0
 */
public class StringArrayToCharArrayCustomEditor extends StringArrayCustomEditor {

    public StringArrayToCharArrayCustomEditor(Class<?> requireType) {
        super(requireType);
        if (requireType != char[].class && requireType != Character[].class) {
            throw new IllegalArgumentException("Unsupported type");
        }
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (Objects.isNull(text)) {
            super.setValue(null);
        }
        String[] strArr = text.split(DEFAULT_SEPARATOR);
        Object val = ReflectionUtil.parseStrArrToTypeArr(strArr, componentType, (str) -> {
            if (str.length() != 1) {
                throw new IllegalArgumentException("can not parse " + Arrays.toString(strArr) + " to char array");
            }
            return str.charAt(0);
        });
        super.setValue(val);
    }
}
