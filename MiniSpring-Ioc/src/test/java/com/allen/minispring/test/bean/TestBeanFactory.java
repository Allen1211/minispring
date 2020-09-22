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
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @ClassName TestBeanFactory
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/5
 * @Version 1.0
 */
public class TestBeanFactory {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

        Assert.assertEquals(mike1, mike2);

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

        Assert.assertNotNull(mike2);
        Assert.assertNotNull(mike1);
        Assert.assertNotNull(john);
        Assert.assertNotNull(mary1);
        Assert.assertNotNull(mary2);

        Assert.assertSame(mike1, mike2);
        Assert.assertNotEquals(mary1, mary2);

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

        Assert.assertNotNull(cdPlayer1);
        Assert.assertNotNull(cdPlayer2);

        Assert.assertEquals(cdPlayer1, cdPlayer2);

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

        Assert.assertNotNull(a);
        Assert.assertNotNull(b);
        Assert.assertNotNull(a.getB());
        Assert.assertNotNull(b.getA());

        Assert.assertEquals(a.getB(), b);
        Assert.assertEquals(b.getA(), a);

        System.out.println(a.getB());
        System.out.println(b.getA());
    }


    @Test
    public void testPropertyPopulate() throws BeanDefinitionReadException {
        BeanFactory beanFactory = new XmlBeanFactory("applicationContext.xml");

        Store store = (Store) beanFactory.getBean("store");

        Assert.assertNotNull(store);

        Assert.assertNotNull(store.getCounter());
        Assert.assertNotNull(store.getStaff());

        System.out.println(store);
    }

    @Test
    public void testPhotoTypeBeanWithSingletonProperty() throws BeanDefinitionReadException {
        BeanFactory beanFactory = new XmlBeanFactory("applicationContext.xml");

        Store store1 = (Store) beanFactory.getBean("photoTypeStore");
        Store store2 = (Store) beanFactory.getBean("photoTypeStore");

        Assert.assertNotNull(store1);
        Assert.assertNotNull(store2);

        Assert.assertNotEquals(store1, store2);

        Assert.assertNotNull(store1.getCounter());
        Assert.assertNotNull(store1.getStaff());
        Assert.assertNotNull(store2.getCounter());
        Assert.assertNotNull(store2.getStaff());

        Assert.assertEquals(store1.getStaff(), store2.getStaff());
        Assert.assertEquals(store1.getCounter(), store2.getCounter());

    }


    @Test
    public void testGetBeanByTypeUnique() throws BeanDefinitionReadException {
        BeanFactory beanFactory = new XmlBeanFactory("applicationContext.xml");

        A a = beanFactory.getBean(A.class);
        B b = beanFactory.getBean(B.class);

        Assert.assertNotNull(a);
        Assert.assertNotNull(a.getB());

        Assert.assertNotNull(b);
        Assert.assertNotNull(b.getA());


        CDPlayer cdPlayer1 = beanFactory.getBean(CDPlayer.class);
        CDPlayer cdPlayer2 = beanFactory.getBean(CDPlayer.class);

        Assert.assertEquals(cdPlayer1, cdPlayer2);


    }

    @Test
    public void testGetBeanByTypeNotUnique() throws BeanDefinitionReadException {

        thrown.expect(BeansException.class);
        thrown.expectMessage("More than one candidate of type: " + Person.class);

        BeanFactory beanFactory = new XmlBeanFactory("applicationContext.xml");
        Person person = beanFactory.getBean(Person.class);


        Assert.assertNotNull(person);
        System.out.println(person);

    }

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void testGetBeanByTypeNotExist() throws BeanDefinitionReadException {
        BeanFactory beanFactory = new XmlBeanFactory("applicationContext.xml");
        beanFactory.getBean(TestBeanFactory.class);

    }
}
