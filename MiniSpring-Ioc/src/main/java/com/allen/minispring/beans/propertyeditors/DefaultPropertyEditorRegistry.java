package com.allen.minispring.beans.propertyeditors;

import com.allen.minispring.utils.Assert;

import java.beans.PropertyEditor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName DefaultPropertyEditorRegistry
 * @Description
 * @Author XianChuLun
 * @Date 2020/11/6
 * @Version 1.0
 */
public class DefaultPropertyEditorRegistry implements PropertyEditorRegistry {

    protected Map<Class<?>, PropertyEditor> customEditors = new HashMap<>();

    protected Map<Class<?>, PropertyEditor> defaultEditors = new HashMap<Class<?>, PropertyEditor>() {{
        put(byte.class, new NumberCustomEditor(Byte.class, false));
        put(Byte.class, new NumberCustomEditor(Byte.class, true));
        put(short.class, new NumberCustomEditor(Short.class, false));
        put(Short.class, new NumberCustomEditor(Short.class, true));
        put(int.class, new NumberCustomEditor(Integer.class, false));
        put(Integer.class, new NumberCustomEditor(Integer.class, true));
        put(long.class, new NumberCustomEditor(Long.class, false));
        put(Long.class, new NumberCustomEditor(Long.class, true));
        put(float.class, new NumberCustomEditor(Float.class, false));
        put(Float.class, new NumberCustomEditor(Float.class, true));
        put(double.class, new NumberCustomEditor(Double.class, false));
        put(Double.class, new NumberCustomEditor(Double.class, true));
        put(BigDecimal.class, new NumberCustomEditor(BigDecimal.class, true));
        put(BigInteger.class, new NumberCustomEditor(BigInteger.class, true));

        put(short[].class, new StringArrayCustomEditor(short[].class));
        put(Short[].class, new StringArrayCustomEditor(Short[].class));
        put(int[].class, new StringArrayCustomEditor(int[].class));
        put(Integer[].class, new StringArrayCustomEditor(Integer[].class));
        put(long[].class, new StringArrayCustomEditor(long[].class));
        put(Long[].class, new StringArrayCustomEditor(Long[].class));
        put(float[].class, new StringArrayCustomEditor(float[].class));
        put(Float[].class, new StringArrayCustomEditor(Float[].class));
        put(double[].class, new StringArrayCustomEditor(double[].class));
        put(Double[].class, new StringArrayCustomEditor(Double[].class));
    }};

    public DefaultPropertyEditorRegistry() {
    }

    @Override
    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        Assert.notNull(requiredType, "requiredType cannot be null!");
        Assert.notNull(propertyEditor, "propertyEditor cannot be null!");
        customEditors.put(requiredType, propertyEditor);
    }

    @Override
    public PropertyEditor findPropertyEditor(Class<?> requiredType) {
        if (Objects.isNull(requiredType)) {
            return null;
        }
        PropertyEditor propertyEditor = customEditors.get(requiredType);
        if (Objects.isNull(propertyEditor)) {
            propertyEditor = defaultEditors.get(requiredType);
        }
        return propertyEditor;
    }
}
