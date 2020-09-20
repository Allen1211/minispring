package com.allen.minispring.beans;


import java.lang.reflect.Constructor;
import java.util.Objects;

/**
 * @ClassName BeanDefinition
 * @Description Bean定义。描述一个Bean的基本信息，相当于Bean的"图纸"。
 * @Author XianChuLun
 * @Date 2020/9/4
 * @Version 1.0
 */
public class BeanDefinition {

    /**
     * Bean名称 在一个上下文内应该是唯一的
     */
    protected String beanName;

    /**
     * Bean的全路径类名
     */
    protected String beanClassName;

    /**
     * Bean的class对象
     */
    protected Class<?> beanClass;

    /**
     * 是否是单例Bean
     */
    protected boolean singleton;

    /**
     * 是否是多例Bean
     */
    protected boolean protoType;

    /**
     * 是否允许将未初始化的实例提前暴露
     */
    protected boolean enableEarlyExpose;

    /**
     * 已解析出来的用于创建Bean的构造器。第一次创建后被设置
     */
    protected Constructor<?> resolvedConstructor;

    /**
     * 未被解析的构造器参数
     */
    protected ConstructorArgumentValues constructorArgumentValues;

    /**
     * 构造器参数是否已经被解析
     */
    protected boolean isConstructorArgumentResolved = false;

    /**
     * 已解析的构造器参数。第一次创建后被设置
     */
    protected Object[] resolvedConstructorArgumentRawValues;

    /**
     * 属性注入的参数
     */
    protected PropertyValues propertyValues;

    /**
     *  构造BeanDefinition
     * @param beanName beanName
     * @param className bean的全路径类名
     * @param singleton 是否是单例Bean
     * @throws ClassNotFoundException 若className对应的class不存在
     */
    public BeanDefinition(String beanName, String className, boolean singleton) throws ClassNotFoundException {
        this(beanName, Class.forName(className), singleton);
    }

    /**
     *  构造BeanDefinition
     * @param className bean的全路径类名
     * @param singleton 是否是单例Bean
     * @throws ClassNotFoundException 若className对应的class不存在
     */
    public BeanDefinition(String className, boolean singleton) throws ClassNotFoundException {
        this(null, Class.forName(className), singleton);
    }

    /**
     * 构造BeanDefinition
     * @param beanName beanName
     * @param clazz bean的Class对象
     * @param singleton 是否是单例Bean
     */
    public BeanDefinition(String beanName, Class<?> clazz, boolean singleton) {
        if(Objects.isNull(clazz)){
            throw new IllegalArgumentException("clazz can not be null");
        }
        this.beanName = beanName;
        this.beanClassName = clazz.getName();
        this.beanClass = clazz;
        this.singleton = singleton;
        this.protoType = !singleton;
        this.constructorArgumentValues = new ConstructorArgumentValues();
        this.propertyValues = new PropertyValues();
        this.enableEarlyExpose = true;
    }

    /**
     * 获取该Bean的Class名称
     * @return 该Bean的Class名称
     */
    public String getBeanClassName() {
        return this.beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    /**
     * 返回该Bean是否是单例Bean
     * MiniSpring中的Bean默认是单例Bean
     * @return 是否是单例Bean
     */
    public boolean isSingleton() {
        return this.singleton;
    }

    /**
     * 返回该Bean是否是多例Bean
     * MiniSpring中的Bean默认是单例Bean
     * @return 是否是多例Bean
     */
    public boolean isProtoType() {
        return this.protoType;
    }

    /**
     * 获取已解析好的构造器函数
     * Bean首次被初始化后，该属性才不为空
     * @return 已解析好的构造器函数
     */
    public Constructor<?> getResolvedConstructor() {
        return this.resolvedConstructor;
    }

    /**
     * 设置解析好的构造器函数
     */
    public void setResolvedConstructor(Constructor<?> constructor) {
        if(this.resolvedConstructor != null){
            throw new IllegalStateException("resolvedConstructor has already set");
        }
        this.resolvedConstructor = constructor;
    }

    /**
     * 获取构造器参数
     */
    public ConstructorArgumentValues getConstructorArgumentValues() {
        return constructorArgumentValues;
    }

    /**
     * 设置构造器参数
     * @param constructorArgumentValues 构造器参数
     */
    public void setConstructorArgumentValues(ConstructorArgumentValues constructorArgumentValues) {
        this.constructorArgumentValues = constructorArgumentValues;
    }

    /**
     * 获取构造器参数是否已经被解析完成
     * @return 构造器参数是否已经被解析完成
     */
    public boolean isConstructorArgumentsResolved() {
        return false;
    }

    /**
     * 设置构造器参数已经被解析完成
     * @param constructorArgumentsResolved 构造器参数是否已经被解析完成
     */
    public void setConstructorArgumentsResolved(boolean constructorArgumentsResolved) {
        this.isConstructorArgumentResolved = constructorArgumentsResolved;
    }

    /**
     * 获取解析处理后的构造器参数
     * @return 解析处理后的构造器参数
     */
    public Object[] getResolvedConstructorArgumentsRawValues() {
        return this.resolvedConstructorArgumentRawValues;
    }

    /**
     * 设置解析处理后的构造器参数
     */
    public void setResolvedConstructorArgumentsRawValues(Object[] resolvedConstructorArgumentsRawValues) {
        this.resolvedConstructorArgumentRawValues = resolvedConstructorArgumentsRawValues;
    }

    /**
     * 判断是否允许提前暴露已创建但未完成初始化的对象
     * @return 否允许提前暴露已创建但未完成初始化的对象
     */
    public boolean isEnableEarlyExpose() {
        return this.enableEarlyExpose;
    }

    /**
     * 判断是否允许提前暴露已创建但未完成初始化的对象
     * @param enableEarlyExpose 是否允许提前暴露已创建但未完成初始化的对象
     */
    public void setEnableEarlyExpose(boolean enableEarlyExpose) {
        this.enableEarlyExpose = enableEarlyExpose;
    }

    /**
     * 获取bean声明了的成员属性
     * @return bean声明了的成员属性
     */
    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    /**
     * 设置bean声明了的成员属性
     * @param propertyValues bean声明了的成员属性
     */
    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    /**
     * 获取该Bean的Class对象
     * @return 该Bean的Class对象
     */
    public Class<?> getBeanClass() {
        return beanClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
