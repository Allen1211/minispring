package com.allen.minispring.beans;

import com.allen.minispring.utils.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PropertyValues
 * @Description bean定义中bean属性值的封装
 * @Author XianChuLun
 * @Date 2020/9/14
 * @Version 1.0
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList;

    public PropertyValues() {
        this.propertyValueList = new ArrayList<>(16);
    }

    public PropertyValues(List<PropertyValue> propertyValueList) {
        Assert.notNull(propertyValueList, "propertyValueList must not be null!");
        this.propertyValueList = propertyValueList;
    }

    public boolean hasProperties(){
        return !propertyValueList.isEmpty();
    }

    public List<PropertyValue> getPropertyValueList(){
        return this.propertyValueList;
    }

    public PropertyValue getPropertyValueByName(String name){
        Assert.notNull(name, "name must not be null!");
        for (PropertyValue pv : propertyValueList) {
            if (pv.getName().equals(name)) {
                return pv;
            }
        }
        return null;
    }

    public void addPropertyValue(PropertyValue newPv){
        Assert.notNull(newPv, "propertyValue to be added must not be null!");
        for (int i = 0; i < propertyValueList.size(); i++) {
            PropertyValue pv = propertyValueList.get(i);
            if(pv.getName().equals(newPv.getName())){
                propertyValueList.set(i, newPv);
                return;
            }
        }
        this.propertyValueList.add(newPv);
    }

    public void removePropertyValue(PropertyValue propertyValue){
        this.propertyValueList.remove(propertyValue);
    }

    public void removePropertyValueByName(String name){
        Assert.notNull(name, "name must not be null!");
        for (int i = 0; i < propertyValueList.size(); i++) {
            PropertyValue pv = propertyValueList.get(i);
            if(pv.getName().equals(name)){
                propertyValueList.remove(i);
                i--;
            }
        }
    }

    public static class PropertyValue {

        private String name;

        private Object value;

        private Class<?> clazz;

        private boolean converted;

        public PropertyValue() {
        }

        public PropertyValue(String name, Object value, boolean converted) {
            this.name = name;
            this.value = value;
            this.converted = converted;
        }

        public PropertyValue(String name, Object value, Class<?> clazz, boolean converted) {
            this.name = name;
            this.value = value;
            this.clazz = clazz;
            this.converted = converted;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public void setClazz(Class<?> clazz) {
            this.clazz = clazz;
        }

        public boolean isConverted() {
            return converted;
        }

        public void setConverted(boolean converted) {
            this.converted = converted;
        }
    }

}
