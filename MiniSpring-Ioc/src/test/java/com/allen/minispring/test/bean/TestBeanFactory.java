package com.allen.minispring.test.bean;

import com.allen.minispring.beans.BeanDefinition;
import com.allen.minispring.beans.BeanDefinitionRegistry;
import com.allen.minispring.beans.ConstructorArgumentValues;
import com.allen.minispring.beans.GenericBeanDefinitionRegistry;
import com.allen.minispring.exception.BeanDefinitionReadException;
import com.allen.minispring.exception.BeansException;
import com.allen.minispring.exception.NoSuchBeanDefinitionException;
import com.allen.minispring.factory.*;
import com.allen.minispring.io.ClassPathResource;
import com.allen.minispring.io.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @ClassName TestBeanFactory
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/5
 * @Version 1.0
 */
public class TestBeanFactory {

    @Test
    public void testGetBean() {
        BeanDefinition beanDefinition = new BeanDefinition("mike", Person.class, true);
        ConstructorArgumentValues cav = beanDefinition.getConstructorArgumentValues();
        cav.addArgumentValue("id", Integer.class, 1);
        cav.addArgumentValue("name", String.class, "mike");

        BeanDefinitionRegistry registry = new GenericBeanDefinitionRegistry();
        registry.registerBeanDefinition("mike", beanDefinition);

        BeanFactory beanFactory = new DefaultBeanFactory(registry);
        Person mike1 = (Person) beanFactory.getBean("mike");
        Person mike2 = (Person) beanFactory.getBean("mike");

        System.out.println(mike1.getId() + "---" + mike1.getName());

        Assertions.assertEquals(mike1, mike2);

        System.out.println(mike1 == mike2);
    }

    @Test
    public void testReader() throws BeanDefinitionReadException {
        BeanDefinitionRegistry registry = new GenericBeanDefinitionRegistry();
        BeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
        Resource resource = new ClassPathResource("applicationContext.xml");
        reader.loadBeanDefinitions(resource);

    }

    @Test
    public void testRegister() throws BeanDefinitionReadException {
        Resource resource = new ClassPathResource("applicationContext.xml");
        BeanFactory beanFactory = new XmlBeanFactory(resource);

        Person mike1 = (Person) beanFactory.getBean("mike");
        Person mike2 = (Person) beanFactory.getBean("mike");
        Person john = (Person) beanFactory.getBean("john");
        Person mary1 = (Person) beanFactory.getBean("mary");
        Person mary2 = (Person) beanFactory.getBean("mary");

        Assertions.assertNotNull(mike2);
        Assertions.assertNotNull(mike1);
        Assertions.assertNotNull(john);
        Assertions.assertNotNull(mary1);
        Assertions.assertNotNull(mary2);

        Assertions.assertSame(mike1, mike2);
        Assertions.assertNotEquals(mary1, mary2);

        System.out.println(mike1);
        System.out.println(mike2);
        System.out.println(mike1 == mike2);
        System.out.println(john);
        System.out.println(mary1);
        System.out.println(mary2);
        System.out.println(mary1 == mary2);
    }

    @Test
    public void testRef() throws BeanDefinitionReadException {
        Resource resource = new ClassPathResource("applicationContext.xml");
        BeanFactory beanFactory = new XmlBeanFactory(resource);

        CDPlayer cdPlayer1 = (CDPlayer) beanFactory.getBean("cdPlayer");
        CDPlayer cdPlayer2 = (CDPlayer) beanFactory.getBean("cdPlayer");

        Disk disk = (Disk) beanFactory.getBean("Luv");

        Assertions.assertNotNull(cdPlayer1);
        Assertions.assertNotNull(cdPlayer2);

        Assertions.assertEquals(cdPlayer1, cdPlayer2);

        System.out.println(cdPlayer1);
        System.out.println(cdPlayer1 == cdPlayer2);

        System.out.println(disk);
    }

    @Test
    public void testCircularReference() throws BeanDefinitionReadException {
        Resource resource = new ClassPathResource("applicationContext.xml");
        BeanFactory beanFactory = new XmlBeanFactory(resource);

        A a = (A) beanFactory.getBean("a");
        B b = (B) beanFactory.getBean("b");

        Assertions.assertNotNull(a);
        Assertions.assertNotNull(b);
        Assertions.assertNotNull(a.getB());
        Assertions.assertNotNull(b.getA());

        Assertions.assertEquals(a.getB(), b);
        Assertions.assertEquals(b.getA(), a);

        System.out.println(a.getB());
        System.out.println(b.getA());
    }


    @Test
    public void testPropertyPopulate() throws BeanDefinitionReadException {
        BeanFactory beanFactory = new XmlBeanFactory("applicationContext.xml");

        Store store = (Store) beanFactory.getBean("store");

        Assertions.assertNotNull(store);

        Assertions.assertNotNull(store.getCounter());
        Assertions.assertNotNull(store.getStaff());

        System.out.println(store);
    }

    @Test
    public void testPhotoTypeBeanWithSingletonProperty() throws BeanDefinitionReadException {
        BeanFactory beanFactory = new XmlBeanFactory("applicationContext.xml");

        Store store1 = (Store) beanFactory.getBean("photoTypeStore");
        Store store2 = (Store) beanFactory.getBean("photoTypeStore");

        Assertions.assertNotNull(store1);
        Assertions.assertNotNull(store2);

        Assertions.assertNotEquals(store1, store2);

        Assertions.assertNotNull(store1.getCounter());
        Assertions.assertNotNull(store1.getStaff());
        Assertions.assertNotNull(store2.getCounter());
        Assertions.assertNotNull(store2.getStaff());

        Assertions.assertEquals(store1.getStaff(), store2.getStaff());
        Assertions.assertEquals(store1.getCounter(), store2.getCounter());

    }


    @Test
    public void testGetBeanByTypeUnique() throws BeanDefinitionReadException {
        BeanFactory beanFactory = new XmlBeanFactory("applicationContext.xml");

        A a = beanFactory.getBean(A.class);
        B b = beanFactory.getBean(B.class);

        Assertions.assertNotNull(a);
        Assertions.assertNotNull(a.getB());

        Assertions.assertNotNull(b);
        Assertions.assertNotNull(b.getA());


        CDPlayer cdPlayer1 = beanFactory.getBean(CDPlayer.class);
        CDPlayer cdPlayer2 = beanFactory.getBean(CDPlayer.class);

        Assertions.assertEquals(cdPlayer1, cdPlayer2);


    }

    @Test
    public void testGetBeanByTypeNotUnique() throws BeanDefinitionReadException {

        BeanFactory beanFactory = new XmlBeanFactory("applicationContext.xml");
        Assertions.assertThrows(BeansException.class, () -> {
            beanFactory.getBean(Person.class);
        }, "More than one candidate of type: " + Person.class);

    }

    @Test
    public void testGetBeanByTypeNotExist() throws BeanDefinitionReadException {
        BeanFactory beanFactory = new XmlBeanFactory("applicationContext.xml");

        Assertions.assertThrows(NoSuchBeanDefinitionException.class, ()->{
            beanFactory.getBean(TestBeanFactory.class);
        });

    }
}
