package com.allen.minispring.factory;

import com.allen.minispring.beans.*;
import com.allen.minispring.beans.propertyeditors.DefaultPropertyEditorRegistry;
import com.allen.minispring.beans.propertyeditors.PropertyEditorRegistry;
import com.allen.minispring.exception.BeanCreationException;
import com.allen.minispring.exception.BeansException;
import com.allen.minispring.exception.NoSuchBeanDefinitionException;
import com.allen.minispring.utils.Assert;
import com.allen.minispring.utils.ClassUtil;
import com.allen.minispring.utils.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName GenericBeanFactory
 * @Description 通用的BeanFactory接口实现类，实现了BeanFactory的所有方法
 * @Author XianChuLun
 * @Date 2020/9/4
 * @Version 1.0
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    private static final Logger logger = LoggerFactory.getLogger(DefaultBeanFactory.class);

    /**
     * Bean定义注册中心
     */
    protected final BeanDefinitionRegistry beanDefinitionRegistry;

    /**
     *
     */
    protected final BeanDefinitionValueResolver beanDefinitionValueResolver;

    protected PropertyEditorRegistry propertyEditorRegistry;

    public DefaultBeanFactory() {
        this.beanDefinitionRegistry = new GenericBeanDefinitionRegistry();
        this.beanDefinitionValueResolver = new BeanDefinitionValueResolver(this);
        this.propertyEditorRegistry = new DefaultPropertyEditorRegistry();
    }

    public DefaultBeanFactory(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
        this.beanDefinitionValueResolver = new BeanDefinitionValueResolver(this);
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, null, args);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBean(requiredType, (Object[])(null));
    }

    @Override
    public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
        Assert.notNull(requiredType, "RequiredType must not be null");
        String[] candidateNames = beanDefinitionRegistry.getBeanDefinitionNamesByType(requiredType);
        if(candidateNames.length == 0){
            throw new NoSuchBeanDefinitionException(requiredType);
        }
        if(candidateNames.length > 1){
            throw new BeansException("More than one candidate of type: " + requiredType);
        }
        String beanName = candidateNames[0];
        return doGetBean(beanName,requiredType, args);
    }

    @Override
    public boolean containsBean(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(name);
        return beanDefinition.isSingleton();
    }

    @Override
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return !isSingleton(name);
    }

    /**
     * 从Bean工厂获取Bean
     *
     * @param name  bean名称
     * @param clazz bean的class对象
     * @param args  构造器参数
     * @return 要获取的bean
     * @throws BeansException 获取失败
     */
    @SuppressWarnings("unchecked")
    private <T> T doGetBean(String name, Class<T> clazz, Object... args) throws BeansException {
        // 从BeanDefinition注册中心获取该Bean的定义
        BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(name);
        Object bean = null;
        if (beanDefinition.isSingleton()) {
            // 若是单例Bean 先从缓存获取已创建的bean
            bean = getSingleton(name);
            if (Objects.isNull(bean)) {
                // 缓存中没有，说明还未创建，进行创建
                bean = getSingleton(name, () -> createBean(name, beanDefinition, clazz, args));
            }
        } else {
            // 多例Bean直接创建并返回
            bean = createBean(name, beanDefinition, clazz, args);
        }

        // 类型判断和转换
        if(Objects.nonNull(bean) && Objects.nonNull(clazz) && !clazz.isInstance(bean)){
            // TODO 类型转换
            throw new BeansException("bean's type" + bean.getClass() + " not match to " + clazz);
        }

        return (T) bean;
    }

    /**
     * 创建bean
     *
     * @param beanName       beanName
     * @param beanDefinition beanDefinition
     * @param clazz          bean的Class对象
     * @param args           构造器参数
     * @return 创建出来的bean
     */
    private Object createBean(String beanName, BeanDefinition beanDefinition, Class<?> clazz, Object... args) {
        Object bean;
        // 实例化bean （此时还未完成初始化）
        bean = instantiateBean(beanDefinition, clazz, args);

        // 如果该bean允许提前暴露，则添加到提前暴露的缓存
        if (beanDefinition.isSingleton() && beanDefinition.isEnableEarlyExpose()) {
            addSingletonFactory(beanName, () -> bean);
        }

        // 依赖注入
        populateBean(bean, beanDefinition);

        return bean;
    }

    /**
     * 完成bean的实例化
     *
     * @param beanDefinition beanDefinition
     * @param clazz          bean的Class对象
     * @param args           构造器参数
     * @return 实例化后的bean
     * @throws BeansException 实例化出错
     */
    private Object instantiateBean(BeanDefinition beanDefinition, Class<?> clazz, Object[] args) throws BeansException {
        Object bean;
        if (Objects.isNull(clazz)) {
            clazz = beanDefinition.getBeanClass();
        }
        // 若没有指明参数，则使用bean定义中的构造器参数
        if (Objects.isNull(args) || args.length == 0) {
            // 若构造器参数还未解析，则先进行解析
            if (beanDefinition.isConstructorArgumentsResolved()) {
                args = beanDefinition.getResolvedConstructorArgumentsRawValues();
            } else {
                ConstructorArgumentValues cav = beanDefinition.getConstructorArgumentValues();
                beanDefinitionValueResolver.resolveValues(cav);
                args = cav.getRawArgValues();
                beanDefinition.setResolvedConstructorArgumentsRawValues(args);
                beanDefinition.setConstructorArgumentsResolved(true);
            }
        }
        // 获取bean已解析的构造器
        Constructor<?> constructor = beanDefinition.getResolvedConstructor();
        // 若bean构造器还未解析，先进行解析
        if (Objects.isNull(constructor)) {
            constructor = determineConstructor(beanDefinition, args);
            beanDefinition.setResolvedConstructor(constructor);
        }
        // 使用解析后的构造器和解析后的构造器参数实例化
        try {
            bean = ReflectionUtil.instantiate(constructor, args);
        } catch (ReflectiveOperationException e) {
            logger.error("Fail to create Bean due to {} {}", e, e.getMessage());
            throw new BeanCreationException(beanDefinition.getBeanName(),
                                            "Fail to create Bean. Some ReflectiveOperationException occurs " + e.getMessage());
        }
        return bean;
    }

    /**
     * 从bean的多个构造器中选择一个与参数匹配的可行的构造器
     *
     * @param beanDefinition beanDefinition
     * @param args           构造参数
     * @return 选择出的构造器
     */
    private Constructor<?> determineConstructor(BeanDefinition beanDefinition, Object... args) {
        Class<?> clazz = beanDefinition.getBeanClass();
        // 没有传入参数，尝试使用无参构造器
        if (args == null || args.length == 0) {
            Class<?>[] empty = {};
            try {
                return clazz.getConstructor(empty);
            } catch (NoSuchMethodException e) { // 不存在无参构造器，抛出异常
                throw new BeansException("No default contructor exsits", e);
            }
        }
        // 尝试找到一个参数列表等于传入参数数量，且参数类型相匹配的构造器
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        ConstructorArgumentValues cav = beanDefinition.getConstructorArgumentValues();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() == args.length
                    && isConstructorArgumentsMatch(cav, constructor)) {
                return constructor;
            }
        }
        // 无法找到构造器，抛出异常
        throw new BeansException("No matching constructor was found");
    }

    /**
     * 判断构造器是否与构造参数匹配
     *
     * @param cav         构造参数
     * @param constructor 构造器
     * @return 构造器是否与构造参数匹配
     */
    private boolean isConstructorArgumentsMatch(ConstructorArgumentValues cav,
                                                Constructor<?> constructor) {
        Map<String, ConstructorArgumentValues.ArgumentValue> mapArgumentValues =
                cav.getMapArgumentValues();
        Parameter[] parameters = constructor.getParameters();
        for (Parameter parameter : parameters) {
            ConstructorArgumentValues.ArgumentValue argumentValue
                    = mapArgumentValues.get(parameter.getName());
            if (argumentValue == null ||
                    (argumentValue.getTypeClazz() != null &&
                            !parameter.getType().equals(argumentValue.getTypeClazz()))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 依赖注入，完成属性填充
     *
     * @param bean           bean
     * @param beanDefinition beanDefinition
     */
    private void populateBean(Object bean, BeanDefinition beanDefinition) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if (!propertyValues.hasProperties()) {
            return;
        }

        List<PropertyValues.PropertyValue> propertyValueList = propertyValues.getPropertyValueList();
        Map<String, Object> propertyMap = new HashMap<>(propertyValueList.size());
        for (PropertyValues.PropertyValue propertyValue : propertyValueList) {
            Object val;
            String name = propertyValue.getName();
            if (propertyValue.isConverted()) {
                val = propertyValue.getValue();
            } else {
                val = beanDefinitionValueResolver.resolveValue(propertyValue.getValue());
                propertyValue.setValue(val);
                propertyValue.setConverted(true);
            }
            propertyMap.put(name, val);
        }

        doApplyProperty(bean, beanDefinition, propertyMap);

    }

    /**
     * 通过反射调用对象setter方法设置值
     * @param bean bean
     * @param beanDefinition beanDefinition
     * @param propertyMap 要设置的属性和值
     * @throws BeanCreationException setter方法不存在 或 反射操作失败
     */
    private void doApplyProperty(Object bean, BeanDefinition beanDefinition, Map<String, Object> propertyMap) {
        Class<?> clazz = beanDefinition.getBeanClass();
        for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
            String propertyName = entry.getKey();
            Object propertyValue = entry.getValue();
            try {
                ReflectionUtil.setObjectProperty(bean, clazz, entry.getKey(), propertyValue);
            } catch (NoSuchMethodException e) {
                throw new BeanCreationException("Can not create Bean: There is no setter method for property "
                                                        + propertyName + " in bean " + clazz, beanDefinition.getBeanName());
            } catch (ReflectiveOperationException e) {
                throw new BeanCreationException("Can not create Bean: ReflectiveOperationException occurs when create" +
                                                        " bean " + clazz, e, beanDefinition.getBeanName());
            }
        }
    }

    @Override
    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        this.propertyEditorRegistry.registerCustomEditor(requiredType, propertyEditor);
    }

    @Override
    public PropertyEditorRegistry getPropertyEditorRegistry() {
        return this.propertyEditorRegistry;
    }
}
