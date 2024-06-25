package com.example.taihovue.anno;

import com.example.taihovue.validation.StateValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { StateValidation.class })
public @interface State {
    // 提供校验失败后的提示信息
    String message() default "{state参数的值只能是已发布或者草稿}";

    // 指定分组
    Class<?>[] groups() default {};

    // 负载 获取State注解的附加信息
    Class<? extends Payload>[] payload() default {};

}
