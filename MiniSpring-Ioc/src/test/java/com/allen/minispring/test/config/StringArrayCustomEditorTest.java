package com.allen.minispring.test.config;

import com.allen.minispring.beans.propertyeditors.StringArrayCustomEditor;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Function;

public class StringArrayCustomEditorTest {

    @Test
    public void parse() {
        String[] arr = {"1", "2", "3"};
        Integer[] res = Arrays.stream(arr)
                .map(Integer::valueOf).toArray(Integer[]::new);
    }

    @Test
    public void test1() {
        Function<Integer, Integer[]> s = Integer[]::new;
        String[] arr = {"1", "2", "3"};
        Object a = fuck(arr, Integer::valueOf, Integer.class);
        System.out.println(a.getClass());
        System.out.println(Arrays.toString((Integer[]) a));
        Object b = fuck(arr, Integer::parseInt, int.class);
        System.out.println(b.getClass());
        System.out.println(Arrays.toString((int[]) b));
    }

    <T> Object fuck(String[] strArr, Function<String, Object> parseFunc, Class<?> type) {
        Object arr = Array.newInstance(type, strArr.length);
        for (int i = 0; i < strArr.length; i++) {
            Array.set(arr, i, parseFunc.apply(strArr[i]));
        }
        return arr;
    }

    @Test
    public void test2() {
        Class<?> c = int[].class;
        System.out.println(c.getComponentType());
    }


    @Test
    public void test4() {
        StringArrayCustomEditor editor = new StringArrayCustomEditor(int[].class);
        editor.setAsText("1,2,3,4");
        int[] value = (int[]) editor.getValue();
        System.out.println(Arrays.toString(value));
        Assertions.assertArrayEquals(new int[]{1,2,3,4}, value);

        editor = new StringArrayCustomEditor(Integer[].class);
        editor.setAsText("1,2,3,4");
        Integer[] value1 = (Integer[]) editor.getValue();
        System.out.println(Arrays.toString(value));
        Assertions.assertArrayEquals(new Integer[]{1,2,3,4}, value1);

        editor = new StringArrayCustomEditor(double[].class);
        editor.setAsText("1.3,2.1,3,4");
        double[] value2 = (double[]) editor.getValue();
        System.out.println(Arrays.toString(value2));
        Assertions.assertArrayEquals(new double[]{1.3,2.1,3,4}, value2);

        editor = new StringArrayCustomEditor(boolean[].class);
        editor.setAsText("true, false, false");
        boolean[] value3 = (boolean[]) editor.getValue();
        System.out.println(Arrays.toString(value3));
        Assertions.assertArrayEquals(new boolean[]{true, false, false}, value3);

    }

    @Test
    public void test5(){
        StringArrayToCharArrayCustomEditor editor = new StringArrayToCharArrayCustomEditor(char[].class);
        editor.setAsText("a,b,c");
        char[] val = (char[]) editor.getValue();
        System.out.println(Arrays.toString(val));
        System.out.println(val.getClass());

        StringArrayToCharArrayCustomEditor editor2 = new StringArrayToCharArrayCustomEditor(Character[].class);
        editor2.setAsText("a,b,c");
        Character[] val2 = (Character[]) editor2.getValue();
        System.out.println(Arrays.toString(val2));
        System.out.println(val2.getClass());
    }
}