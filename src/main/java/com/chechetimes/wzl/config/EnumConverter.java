package com.chechetimes.wzl.config;

import com.chechetimes.wzl.util.CommonUtils;
import org.apache.commons.beanutils.converters.AbstractConverter;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class EnumConverter extends AbstractConverter {

    protected static final Set<Class<?>> ENUMS_SET = Collections.unmodifiableSet(new HashSet<Class<?>>() {{

    }});

    public EnumConverter() {

    }

    public EnumConverter(final Object defaultValue) {
        super(defaultValue);
    }

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        Optional<Class<?>> optional = ENUMS_SET.stream().filter(type::equals).findAny();
        if (optional.isPresent()) {
            Class<?> aClass = optional.get();
            Method strMethod = null;
            Method intMethod = null;
            try {
                // 获取get方法
                strMethod = aClass.getDeclaredMethod("get", String.class);
                intMethod = aClass.getDeclaredMethod("get", int.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String strValue = value == null ? "" : value.toString();
            if (intMethod != null) {
                if (value instanceof Number || CommonUtils.isInteger(strValue)) {
                    int i = Double.valueOf(strValue).intValue();
                    return type.cast(intMethod.invoke(aClass, i));
                }
            }
            if (strMethod != null) {
                return type.cast(strMethod.invoke(aClass, strValue));
            }
        }
        return null;
    }

    @Override
    protected Class<?> getDefaultType() {
        return null;
    }
}
