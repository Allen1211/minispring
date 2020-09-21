package com.allen.minispring.test.bean;

import com.allen.minispring.beans.BeanDefinition;
import com.allen.minispring.beans.BeanDefinitionRegistry;
import com.allen.minispring.beans.GenericBeanDefinitionRegistry;
import com.allen.minispring.exception.BeansException;
import com.allen.minispring.exception.NoSuchBeanDefinitionException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BeanDefinitionRegistryTest {

    private BeanDefinitionRegistry registry;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void beforeTest(){
        registry = new GenericBeanDefinitionRegistry();
    }

    @Test
    public void registerBeanDefinition() {
        registry.registerBeanDefinition("a" , new BeanDefinition("a", A.class, true));
        registry.registerBeanDefinition("b" , new BeanDefinition("b", B.class, false));

        thrown.expect(IllegalArgumentException.class);
        registry.registerBeanDefinition(null, new BeanDefinition("a", A.class, true));

        thrown.expect(IllegalArgumentException.class);
        registry.registerBeanDefinition("a",null);

        thrown.expect(BeansException.class);
        thrown.expectMessage("BeanName: " + "b" +" has already registered");
        registry.registerBeanDefinition("b" , new BeanDefinition("b", B.class, false));

    }

    @Test
    public void removeBeanDefinition() {
        registry.registerBeanDefinition("a" , new BeanDefinition("a", A.class, true));

        registry.removeBeanDefinition("a");

        thrown.expect(NoSuchBeanDefinitionException.class);
        registry.removeBeanDefinition("a");

    }

    @Test
    public void getBeanDefinition() {
        registry.registerBeanDefinition("a" , new BeanDefinition("a", A.class, true));

        BeanDefinition a = registry.getBeanDefinition("a");
        Assert.assertNotNull(a);

        a = registry.getBeanDefinition(A.class)[0];
        Assert.assertNotNull(a);

        thrown.expect(NoSuchBeanDefinitionException.class);
        registry.getBeanDefinition("abc");
        thrown.expect(NoSuchBeanDefinitionException.class);
        registry.getBeanDefinition(Person.class);
    }

    @Test
    public void getBeanDefinitionNamesByType() {
        registry.registerBeanDefinition("a" , new BeanDefinition("a", Person.class, true));
        registry.registerBeanDefinition("b" , new BeanDefinition("b", Person.class, true));

        String[] names = registry.getBeanDefinitionNamesByType(Person.class);
        Assert.assertNotNull(names);
        Assert.assertEquals(2, names.length);

        names = registry.getBeanDefinitionNamesByType(A.class);

        Assert.assertNotNull(names);
        Assert.assertEquals(0, names.length );
    }

    @Test
    public void containsBeanDefinition() {
        registry.registerBeanDefinition("a" , new BeanDefinition("a", Person.class, true));

        Assert.assertTrue(registry.containsBeanDefinition("a"));
        Assert.assertFalse(registry.containsBeanDefinition("b"));
    }

    @Test
    public void getBeanDefinitionNames() {
        registry.registerBeanDefinition("a" , new BeanDefinition("a", Person.class, true));
        registry.registerBeanDefinition("b" , new BeanDefinition("b", Person.class, true));

        String[] names = registry.getBeanDefinitionNames();

        Assert.assertNotNull(names);
        Assert.assertEquals(2, names.length);
    }
}