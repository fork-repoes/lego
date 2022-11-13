package com.geekhalo.lego.feign;

import lombok.Data;

/**
 * Created by taoli on 2022/11/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class FeignResultDemo {
    private static final int SUCCESS = 0;
    private UserFeignClient userFeignClient;

    public boolean login(Long userId){
        FeignResult<User> userFeignResult = this.userFeignClient.getByUserId(userId);
        if (userFeignResult.getCode() != SUCCESS){
            throw new BizException(userFeignResult.getMsg());
        }
        User user = userFeignResult.getData();
        return user.checkPassword(userId);
    }

    interface UserFeignClient{
        FeignResult<User> getByUserId(Long userId);
    }

    class User{

        public boolean checkPassword(Long userId) {
            return false;
        }
    }

    class BizException extends RuntimeException{

        public BizException(String msg) {

        }
    }
    @Data
    class FeignResult<D>{
        private int code;
        private String msg;
        private D data;
    }
}
