package com.geekhalo.lego.validator;

import com.geekhalo.lego.DemoApplication;
import com.geekhalo.lego.validator.address.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

/**
 * Created by taoli on 2022/9/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
class ApplicationValidateServiceTest {
    @Autowired
    private ApplicationValidateService applicationValidateService;

    @Test
    void singleValidate_error() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                applicationValidateService.singleValidate((Long) null);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void singleValidate() {
        applicationValidateService.singleValidate(1L);
    }

    @Test
    void testSingleValidate_error1() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                this.applicationValidateService.singleValidate((SingleForm) null);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });

    }

    @Test
    void testSingleValidate_error2() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                SingleForm singleForm = new SingleForm();
                this.applicationValidateService.singleValidate(singleForm);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void testSingleValidate_error3() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                SingleForm singleForm = new SingleForm();
                singleForm.setId(1L);
                this.applicationValidateService.singleValidate(singleForm);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void testSingleValidate_error4() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                SingleForm singleForm = new SingleForm();
                singleForm.setName("");
                this.applicationValidateService.singleValidate(singleForm);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void testSingleValidate_error5() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                SingleForm singleForm = new SingleForm();
                singleForm.setName("name");
                this.applicationValidateService.singleValidate(singleForm);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void testSingleValidate() {
        SingleForm singleForm = new SingleForm();
        singleForm.setId(1L);
        singleForm.setName("name");
        this.applicationValidateService.singleValidate(singleForm);
    }

    @Test
    void customSingleValidate_error1() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                this.applicationValidateService.customSingleValidate(null);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void customSingleValidate_error2() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                CustomSingleForm customSingleForm = new CustomSingleForm();
                this.applicationValidateService.customSingleValidate(customSingleForm);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void customSingleValidate_error3() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                CustomSingleForm customSingleForm = new CustomSingleForm();
                customSingleForm.setId(1L);
                this.applicationValidateService.customSingleValidate(customSingleForm);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void customSingleValidate_error4() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                CustomSingleForm customSingleForm = new CustomSingleForm();
                customSingleForm.setId(1L);
                customSingleForm.setName("name");
                this.applicationValidateService.customSingleValidate(customSingleForm);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }

        });
    }

    @Test
    void customSingleValidate_error5() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                CustomSingleForm customSingleForm = new CustomSingleForm();
                customSingleForm.setId(1L);
                customSingleForm.setName("name");
                Address address = new Address();
                customSingleForm.setAddress(address);
                this.applicationValidateService.customSingleValidate(customSingleForm);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void customSingleValidate_error6() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                CustomSingleForm customSingleForm = new CustomSingleForm();
                customSingleForm.setId(1L);
                customSingleForm.setName("name");
                Address address = new Address();
                address.setCity("北京");
                address.setDetail("朝阳");
                customSingleForm.setAddress(address);
                this.applicationValidateService.customSingleValidate(customSingleForm);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void customSingleValidate_success1() {
        CustomSingleForm customSingleForm = new CustomSingleForm();
        customSingleForm.setId(1L);
        customSingleForm.setName("name");
        Address address = new Address();
        address.setCity("北京");
        customSingleForm.setAddress(address);
        this.applicationValidateService.customSingleValidate(customSingleForm);
    }

    @Test
    void customSingleValidate_success2() {
        CustomSingleForm customSingleForm = new CustomSingleForm();
        customSingleForm.setId(1L);
        customSingleForm.setName("name");
        Address address = new Address();
        address.setDetail("朝阳");
        customSingleForm.setAddress(address);
        this.applicationValidateService.customSingleValidate(customSingleForm);
    }

    @Test
    void validateForm_error1() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                this.applicationValidateService.validateForm(null);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void validateForm_error2() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                UserValidateForm userValidateForm = new UserValidateForm();
                this.applicationValidateService.validateForm(userValidateForm);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void validateForm_error3() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                UserValidateForm userValidateForm = new UserValidateForm();
                userValidateForm.setName(null);
                this.applicationValidateService.validateForm(userValidateForm);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void validateForm_error4() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                UserValidateForm userValidateForm = new UserValidateForm();
                userValidateForm.setName("");
                this.applicationValidateService.validateForm(userValidateForm);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void validateForm_error5() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                UserValidateForm userValidateForm = new UserValidateForm();
                userValidateForm.setName("name");
                userValidateForm.setPassword(null);
                this.applicationValidateService.validateForm(userValidateForm);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void validateForm_error6() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                UserValidateForm userValidateForm = new UserValidateForm();
                userValidateForm.setName("name");
                userValidateForm.setPassword("");
                this.applicationValidateService.validateForm(userValidateForm);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void validateForm_error7() {
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            try {
                UserValidateForm userValidateForm = new UserValidateForm();
                userValidateForm.setName("name");
                userValidateForm.setPassword("name");
                this.applicationValidateService.validateForm(userValidateForm);
            }catch (RuntimeException e){
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void validateForm() {
        UserValidateForm userValidateForm = new UserValidateForm();
        userValidateForm.setName("name");
        userValidateForm.setPassword("namename");
        this.applicationValidateService.validateForm(userValidateForm);
    }
}