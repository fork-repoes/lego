package com.geekhalo.lego.joininmemory.demo.v4;

import com.geekhalo.lego.annotation.joininmemory.JoinInMemory;
import com.geekhalo.lego.joininmemory.demo.AddressVO;
import com.geekhalo.lego.service.user.User;

/**
 * Created by taoli on 2023/3/19.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class UserDetailVO {
    private User user;

    @JoinInMemory(keyFromSourceData = "#{user.addressId}",
            keyFromJoinData = "#{id}",
            loader = "#{@addressRepository.getByIds(#root)}",
            joinDataConverter = "#{T(com.geekhalo.lego.joininmemory.demo.AddressVO).apply(#root)}"
    )
    private AddressVO address;
}
