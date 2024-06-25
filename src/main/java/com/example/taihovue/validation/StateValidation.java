package com.example.taihovue.validation;

import com.example.taihovue.anno.State;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {
    /**
     * @param value 将来要校验的数据
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 提供校验规则
        if (value == null) {
            return false;
        }
        if ("已发布".equals(value) || "草稿".equals(value)) {
            return true;
        }
        return false;
    }

}
