package com.graphql.tutorial.salesservice.utils;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public final class BaseUtils {
    private static final Logger log = LoggerFactory.getLogger(BaseUtils.class);

    public static <S, D> Function<ModelMapper, D> map(S s, Class<D> clazz) {
        return mapper -> {
            D result = null;
            if (null == s) {
                return null;
            }

            try {
                result = clazz.getDeclaredConstructor().newInstance();
                mapper.map(s, result);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                log.error("Error: ", e);
            }

            return result;
        };
    }

    private BaseUtils() {
    }
}
