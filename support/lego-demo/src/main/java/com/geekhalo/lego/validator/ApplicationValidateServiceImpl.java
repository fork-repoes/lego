package com.geekhalo.lego.validator;

import com.geekhalo.lego.validator.pwd.Password;
import org.springframework.stereotype.Service;

/**
 * Created by taoli on 2022/9/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
public class ApplicationValidateServiceImpl implements ApplicationValidateService {

    @Override
    public void singleValidate(Long id){

    }

    @Override
    public void singleValidate(SingleForm singleForm){

    }

    @Override
    public void customSingleValidate(Password password){

    }

    @Override
    public void validateForm(UserValidateForm userValidateForm){

    }
}
