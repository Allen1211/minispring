package com.allen.minispring.beans;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ConstructorArgumentValues
 * @Description 构造器参数的封装
 * @Author XianChuLun
 * @Date 2020/9/5
 * @Version 1.0
 */
public class ConstructorArgumentValues {

    private final Map<String, ArgumentValue> mapArgumentValues = new HashMap<>();

    private final List<ArgumentValue> listArgumentValues = new ArrayList<>();

    public void addArgumentValue(ArgumentValue argumentValue){
        this.listArgumentValues.add(argumentValue);
        if(argumentValue.getName() != null){
            mapArgumentValues.put(argumentValue.getName(), argumentValue);
        }
    }

    public void addArgumentValue(String name, Class<?> clazz, Object value){
        ArgumentValue argumentValue = new ArgumentValue(name, clazz, value);
        this.listArgumentValues.add(argumentValue);
        this.mapArgumentValues.put(name, argumentValue);
    }

    public ArgumentValue getArgumentValueByName(String name){
        return this.mapArgumentValues.get(name);
    }

    public List<ArgumentValue> getListArgumentValues() {
        return listArgumentValues;
    }

    public Map<String, ArgumentValue> getMapArgumentValues() {
        return mapArgumentValues;
    }

    public Object[] getRawArgValues(){
        return listArgumentValues.stream()
                .map(ArgumentValue::getValue)
                .toArray();
    }

    public static class ArgumentValue {

        private String name;

        private Class<?> typeClazz;

        private Object value;

        private boolean converted;

        public ArgumentValue() {
        }

        public ArgumentValue(String name, Class<?> typeClazz, Object value) {
            this.name = name;
            this.typeClazz = typeClazz;
            this.value = value;
            this.converted = false;
        }

        public String getName() {
            return name;
        }

        public Class<?> getTypeClazz() {
            return typeClazz;
        }

        public Object getValue() {
            return value;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTypeClazz(Class<?> typeClazz) {
            this.typeClazz = typeClazz;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public boolean isConverted() {
            return converted;
        }

        public void setConverted(boolean converted) {
            this.converted = converted;
        }
    }


}
