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

    protected Map<Class<?>, PropertyEditor> defaultEditors;

    protected Map<Class<?>, PropertyEditor> customEditors;

    public DefaultPropertyEditorRegistry() {
        this.defaultEditors = new HashMap<>();
        this.customEditors = new HashMap<>();

        initDefaultEditors();
    }

    private void initDefaultEditors(){
        this.defaultEditors.put(byte.class, new CustomNumberEditor(Byte.class, false));
        this.defaultEditors.put(Byte.class, new CustomNumberEditor(Byte.class, true));
        this.defaultEditors.put(short.class, new CustomNumberEditor(Short.class, false));
        this.defaultEditors.put(Short.class, new CustomNumberEditor(Short.class, true));
        this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, false));
        this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
        this.defaultEditors.put(long.class, new CustomNumberEditor(Long.class, false));
        this.defaultEditors.put(Long.class, new CustomNumberEditor(Long.class, true));
        this.defaultEditors.put(float.class, new CustomNumberEditor(Float.class, false));
        this.defaultEditors.put(Float.class, new CustomNumberEditor(Float.class, true));
        this.defaultEditors.put(double.class, new CustomNumberEditor(Double.class, false));
        this.defaultEditors.put(Double.class, new CustomNumberEditor(Double.class, true));
        this.defaultEditors.put(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
        this.defaultEditors.put(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
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
        if(Objects.isNull(propertyEditor)) {
            propertyEditor = defaultEditors.get(requiredType);
        }
        return propertyEditor;
    }
}
