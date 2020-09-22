package com.allen.minispring.test.bean;

import com.allen.minispring.beans.BeanDefinition;
import com.allen.minispring.beans.BeanDefinitionRegistry;
import com.allen.minispring.beans.GenericBeanDefinitionRegistry;
import com.allen.minispring.exception.BeansException;
import com.allen.minispring.exception.NoSuchBeanDefinitionException;
import com.allen.minispring.utils.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BeanDefinitionRegistryTest {

    private BeanDefinitionRegistry registry;

    @BeforeEach
    void beforeTest() {
        registry = new GenericBeanDefinitionRegistry();
    }

    @Test
    public void registerBeanDefinition() {
        registry.registerBeanDefinition("a", new BeanDefinition("a", A.class, true));
        registry.registerBeanDefinition("b", new BeanDefinition("b", B.class, false));

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            registry.registerBeanDefinition(null, new BeanDefinition("a", A.class, true));
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            registry.registerBeanDefinition("a", null);
        });
        Assertions.assertThrows(BeansException.class, () -> {
            registry.registerBeanDefinition("b", new BeanDefinition("b", B.class, false));
        }, "BeanName: " + "b" + " has already registered");

    }

    @Test
    public void removeBeanDefinition() {
        registry.registerBeanDefinition("a", new BeanDefinition("a", A.class, true));

        registry.removeBeanDefinition("a");

        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> {
            registry.removeBeanDefinition("a");
        });

    }

    @Test
    public void getBeanDefinition() {
        registry.registerBeanDefinition("a", new BeanDefinition("a", A.class, true));

        BeanDefinition a = registry.getBeanDefinition("a");
        Assertions.assertNotNull(a);
        a = registry.getBeanDefinition(A.class)[0];
        Assertions.assertNotNull(a);

        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> {
            registry.getBeanDefinition("abc");
        });
        BeanDefinition[] beanDefinitions = registry.getBeanDefinition(Person.class);
        Assertions.assertArrayEquals(new String[0],beanDefinitions);
    }

    @Test
    public void getBeanDefinitionNamesByType() {
        registry.registerBeanDefinition("a", new BeanDefinition("a", Person.class, true));
        registry.registerBeanDefinition("b", new BeanDefinition("b", Person.class, true));

        String[] names = registry.getBeanDefinitionNamesByType(Person.class);
        Assertions.assertNotNull(names);
        Assertions.assertEquals(2, names.length);

        names = registry.getBeanDefinitionNamesByType(A.class);

        Assertions.assertNotNull(names);
        Assertions.assertEquals(0, names.length);
    }

    @Test
    public void containsBeanDefinition() {
        registry.registerBeanDefinition("a", new BeanDefinition("a", Person.class, true));

        Assertions.assertTrue(registry.containsBeanDefinition("a"));
        Assertions.assertFalse(registry.containsBeanDefinition("b"));
    }

    @Test
    public void getBeanDefinitionNames() {
        registry.registerBeanDefinition("a", new BeanDefinition("a", Person.class, true));
        registry.registerBeanDefinition("b", new BeanDefinition("b", Person.class, true));

        String[] names = registry.getBeanDefinitionNames();

        Assertions.assertNotNull(names);
        Assertions.assertEquals(2, names.length);
    }
}