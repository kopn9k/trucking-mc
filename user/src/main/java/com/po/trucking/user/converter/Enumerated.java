package com.po.trucking.user.converter;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * Specifies how a persistent property or field should be persisted
 * as a enumerated type
 */

@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Enumerated {

    EnumType value() default EnumType.POSTGRES;
}

