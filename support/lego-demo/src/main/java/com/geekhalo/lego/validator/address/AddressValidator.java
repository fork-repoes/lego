package com.geekhalo.lego.validator.address;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by taoli on 2022/9/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class AddressValidator implements ConstraintValidator<MustAddress, Address> {
    @Override
    public boolean isValid(Address address, ConstraintValidatorContext constraintValidatorContext) {
        if (address == null){
            return false;
        }
        boolean cityEmpty = StringUtils.isEmpty(address.getCity());
        boolean detailEmpty = StringUtils.isEmpty(address.getDetail());

        return (cityEmpty && !detailEmpty) || (!cityEmpty && detailEmpty);
    }
}
