package com.allen.minispring.beans.propertyeditors;

import java.beans.PropertyEditor;

/**
 * @ClassName PropertyEditorRegistry
 * @Description 管理PropertyEditor的注册中心
 * @Author XianChuLun
 * @Date 2020/11/6
 * @Version 1.0
 */
public interface PropertyEditorRegistry {

    /**
     * 注册一个自定义的PropertyEditor
     * @param requiredType 目的类型
     * @param propertyEditor editor
     */
    void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor);

    /**
     * 查找一个已注册的PropertyEditor
     * @param requiredType 目的类型
     * @return editor
     */
    PropertyEditor findPropertyEditor(Class<?> requiredType);
}
