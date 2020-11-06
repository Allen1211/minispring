package com.allen.minispring.beans.propertyeditors;

import java.beans.PropertyEditor;

/**
 * @ClassName PropertyEditorRegistry
 * @Description
 * @Author XianChuLun
 * @Date 2020/11/6
 * @Version 1.0
 */
public interface PropertyEditorRegistry {

    void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor);

    PropertyEditor findPropertyEditor(Class<?> requiredType);
}
