package com.allen.minispring.factory;

import com.allen.minispring.beans.*;
import com.allen.minispring.exception.BeanDefinitionReadException;
import com.allen.minispring.io.Resource;
import com.allen.minispring.utils.IOUtil;
import com.allen.minispring.utils.ReflectionUtil;
import com.allen.minispring.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @ClassName XmlBeanDefinitionReader
 * @Description 实现从XML配置文件中读取并解析Bean定义
 * @Author XianChuLun
 * @Date 2020/9/5
 * @Version 1.0
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    private static final Logger logger = LoggerFactory.getLogger(XmlBeanDefinitionReader.class);

    private static final String TAG_TOP_BEANS = "beans";

    private static final String TAG_BEAN = "bean";

    private static final String TAG_CONSTRUCTOR_ARG = "constructor-arg";

    private static final String TAG_PROPERTY = "property";

    private static final String ATTRIBUTE_ID = "id";

    private static final String ATTRIBUTE_NAME = "name";

    private static final String ATTRIBUTE_CLASS = "class";

    private static final String ATTRIBUTE_REF = "ref";

    private static final String ATTRIBUTE_VALUE = "value";

    private static final String ATTRIBUTE_SCOPE = "scope";

    private static final String ATTRIBUTE_SCOPE_SINGLETON = "singleton";

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public int loadBeanDefinitions(Resource resource) throws BeanDefinitionReadException {
        // 加载XML文档
        Document doc = openDocument(resource);
        Element root = doc.getDocumentElement();
        // 解析所有 <bean> 标签
        NodeList beansTags = root.getElementsByTagName(TAG_BEAN);
        for (int i = 0; i < beansTags.getLength(); i++) {
            Element beanTag = (Element) beansTags.item(i);

            // 解析bean标签
            BeanDefinition beanDefinition = parseBeanTag(beanTag);

            // 注册Bean定义
            super.doRegisterBeanDefinition(beanDefinition);

            logger.debug("Read And Registered BeanDefinition. name: {}", beanDefinition.getBeanName());
        }
        return beansTags.getLength();
    }

    private Document openDocument(Resource resource) throws BeanDefinitionReadException {

        InputStream is = null;
        try {
            is = resource.getInputStream();
        } catch (IOException e) {
            IOUtil.closeSilently(is);
            throw new BeanDefinitionReadException("resource's InputSteam failed to open!", e);
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(is);
        } catch (Exception e) {
            throw new BeanDefinitionReadException("DocumentReader failed to init! Caused by :" + e.getClass(), e);
        }finally {
            IOUtil.closeSilently(is);
        }

    }

    private BeanDefinition parseBeanTag(Element beanElement) throws BeanDefinitionReadException {
        // 解析bean主标签的属性
        BeanDefinition beanDefinition = parseBeanTagMainAttr(beanElement);
        // 解析bean标签的子标签
        parseConstructorArgsElement(beanElement, beanDefinition);
        parsePropertyElement(beanElement, beanDefinition);
        return beanDefinition;
    }

    private BeanDefinition parseBeanTagMainAttr(Element beanElement) throws BeanDefinitionReadException {
        String id = beanElement.getAttribute(ATTRIBUTE_ID);
        String className = beanElement.getAttribute(ATTRIBUTE_CLASS);
        String name = beanElement.getAttribute(ATTRIBUTE_NAME);
        String scope = beanElement.getAttribute(ATTRIBUTE_SCOPE);
        boolean singleton = StringUtils.isEmpty(scope) || ATTRIBUTE_SCOPE_SINGLETON.equals(scope);
        if (StringUtils.isEmpty(id)) {
            throw new BeanDefinitionReadException("bean's id must not be null!");
        }
        if (StringUtils.isEmpty(className)) {
            throw new BeanDefinitionReadException("bean's class must not be null!");
        }
        if (StringUtils.isEmpty(name)) {
            name = id;
        }

        BeanDefinition beanDefinition;
        try {
            beanDefinition = new BeanDefinition(name, className, singleton);
        } catch (ClassNotFoundException e) {
            throw new BeanDefinitionReadException("Bean's class : " + className + " can not be found! ", e);
        }
        return beanDefinition;
    }

    private void parseConstructorArgsElement(Element beanElement, BeanDefinition beanDefinition) throws BeanDefinitionReadException {

        ConstructorArgumentValues cavs = beanDefinition.getConstructorArgumentValues();
        NodeList constructorArgsElements = beanElement.getElementsByTagName(TAG_CONSTRUCTOR_ARG);
        for (int i = 0; i < constructorArgsElements.getLength(); i++) {
            Element element = (Element) constructorArgsElements.item(i);

            /*
             * 读取填入的属性
             */
            String name = element.getAttribute(ATTRIBUTE_NAME);
            String valueStr = element.getAttribute(ATTRIBUTE_VALUE);
            String className = element.getAttribute(ATTRIBUTE_CLASS);
            String ref = element.getAttribute(ATTRIBUTE_REF);
            Class<?> clazz = null;

            if (StringUtils.isEmpty(name)) {
                throw new BeanDefinitionReadException("constructor-args attribute: \"name\" must not be null");
            }

            /*
             * 处理注入值
             */
            Object value = null;
            if (StringUtils.notEmpty(className) && StringUtils.notEmpty(valueStr)) {
                try {
                    clazz = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    throw new BeanDefinitionReadException("ClassNotFound: " + className, e);
                }
                value = ReflectionUtil.parseStringToObjectByClass(clazz, valueStr);
                if (Objects.isNull(value)) {
                    throw new BeanDefinitionReadException("Can not parse value " + valueStr + " to "
                                                                  + className);
                }
            } else if (StringUtils.notEmpty(ref)) {
                value = new RunTimeReferenceBean(ref);
                clazz = RunTimeReferenceBean.class;
            } else {
                throw new BeanDefinitionReadException("constructor-args attributes:  " +
                                                              "no \"value\" & \"class\" or \"ref\" specific");
            }

            cavs.addArgumentValue(name, clazz, value);
        }
    }

    private void parsePropertyElement(Element beanElement, BeanDefinition beanDefinition) throws BeanDefinitionReadException {

        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        NodeList propertyElements = beanElement.getElementsByTagName(TAG_PROPERTY);

        for (int i = 0; i < propertyElements.getLength(); i++) {
            Element element = (Element) propertyElements.item(i);

            /*
             * 读取填入的属性
             */
            String name = element.getAttribute(ATTRIBUTE_NAME);
            String valueStr = element.getAttribute(ATTRIBUTE_VALUE);
            String className = element.getAttribute(ATTRIBUTE_CLASS);
            String ref = element.getAttribute(ATTRIBUTE_REF);
            Class<?> clazz = null;
            boolean converted;
            if (StringUtils.isEmpty(name)) {
                throw new BeanDefinitionReadException("property attribute: \"name\" must not be null");
            }

            /*
             * 处理注入值
             */
            Object value = null;
            if (StringUtils.notEmpty(className) && StringUtils.notEmpty(valueStr)) {
                try {
                    clazz = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    throw new BeanDefinitionReadException("ClassNotFound: " + className, e);
                }
                value = ReflectionUtil.parseStringToObjectByClass(clazz, valueStr);
                if (Objects.isNull(value)) {
                    throw new BeanDefinitionReadException("Can not parse value " + valueStr + " to "
                                                                  + className);
                }
                converted = true;
            } else if (StringUtils.notEmpty(ref)) {
                value = new RunTimeReferenceBean(ref);
                clazz = RunTimeReferenceBean.class;
                converted = false;
            } else {
                throw new BeanDefinitionReadException("property attributes:  " +
                                                              "no \"value\" & \"class\" or \"ref\" specific");
            }

            propertyValues.addPropertyValue(new PropertyValues.PropertyValue(name, value, clazz, converted));

        }
    }

}
