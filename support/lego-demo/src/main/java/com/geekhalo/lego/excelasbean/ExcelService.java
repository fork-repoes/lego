package com.geekhalo.lego.excelasbean;

import com.geekhalo.lego.core.excelasbean.ExcelAsBeanService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by taoli on 2022/8/8.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
public class ExcelService {
    @Autowired
    private ExcelAsBeanService excelAsBeanService;

    public HSSFWorkbook downloadUser(){
        List<User> users = createUser(100);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        this.excelAsBeanService.writToSheet(hssfWorkbook,"User", User.class, users);
        return hssfWorkbook;
    }

    private List<User> createUser(int count) {
        List<User> users = Lists.newArrayList();
        for (int i = 0; i < count; i++){
            Integer age = (i) % 50 + 5;
            User user = User.builder()
                    .id(i + 1L)
                    .name("测试用户-" + i)
                    .age(age)
                    .birthAt(DateUtils.addYears(new Date(), - age))
                    .formatDate(DateUtils.addYears(new Date(), age))
                    .address(createAddress(i+1))
                    .build();
            users.add(user);
        }
        return users;
    }

    private Address createAddress(int i) {
        if (i % 5 == 0){
            return null;
        }
        return Address.builder()
                .l1("北京")
                .l2("北京")
                .l3("海淀")
                .l4("中关村-" + i)
                .build();
    }
}
