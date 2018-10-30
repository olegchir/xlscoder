package com.xlscoder.lang;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * https://www.oreilly.com/ideas/handling-checked-exceptions-in-java-streams
 */
public class Functional {
    public static final Logger logger = LoggerFactory.getLogger(Functional.class);

    public static <T, R, E extends Exception>
    Function<T, R> re(FunctionWithException<T, R, E> fe) {
        return arg -> {
            try {
                return fe.apply(arg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static <T, R, E extends Exception>
    Function<T, R> ie(FunctionWithException<T, R, E> fe) {
        return arg -> {
            try {
                return fe.apply(arg);
            } catch (Exception e) {
                // that's fine
            }
            return null;
        };
    }

    public static <T, R, E extends Exception>
    Function<T, R> le(FunctionWithException<T, R, E> fe) {
        return arg -> {
            try {
                return fe.apply(arg);
            } catch (Exception e) {
                logger.error(ExceptionUtils.getStackTrace(e));
            }
            return null;
        };
    }
}
