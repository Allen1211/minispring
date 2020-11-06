package com.allen.minispring.beans.propertyeditors;

import com.allen.minispring.utils.StringUtils;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * @ClassName CustomNumberEditor
 * @Description
 * @Author XianChuLun
 * @Date 2020/11/6
 * @Version 1.0
 */
public class CustomNumberEditor extends PropertyEditorSupport {

    private final Class<? extends Number> numberClass;

    private final boolean allowNull;

    public CustomNumberEditor(Class<? extends Number> numberClass, boolean allowNull) {
        this.numberClass = numberClass;
        this.allowNull = allowNull;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if(StringUtils.isEmpty(text) && allowNull) {
            super.setValue(null);
        }

        Object val;
        if (Byte.class == numberClass) {
            val = Byte.valueOf(text);
        } else if (Short.class == numberClass) {
            val = Short.valueOf(text);
        } else if (Integer.class == numberClass) {
            val = Integer.valueOf(text);
        } else if (Long.class == numberClass) {
            val = Long.valueOf(text);
        } else if (BigInteger.class == numberClass) {
            val = new BigInteger(text);
        } else if (Float.class == numberClass) {
            val = Float.valueOf(text);
        } else if (Double.class == numberClass) {
            val = Double.valueOf(text);
        } else if (BigDecimal.class == numberClass || Number.class == numberClass) {
            val = new BigDecimal(text);
        } else {
            throw new IllegalArgumentException(
                    "Cannot convert String [" + text + "] to target class [" + numberClass + "]");
        }

        super.setValue(val);
    }

    public Class<? extends Number> getNumberClass() {
        return numberClass;
    }

}
