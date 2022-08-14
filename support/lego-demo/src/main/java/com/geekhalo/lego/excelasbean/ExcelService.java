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
import java.util.function.Supplier;

/**
 * Created by taoli on 2022/8/8.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
public class ExcelService {
    // 注入 excelAsBeanService
    // 由 ExcelAsBeanAutoConfiguration 完成 ExcelAsBeanService 的注册
    @Autowired
    private ExcelAsBeanService excelAsBeanService;

    public <D extends User> HSSFWorkbook downloadUser(Class<D> cls, Supplier<D> supplier){
        // 1. 准备数据
        List<D> users = createUser(100, supplier);
        // 2. 创建 Workbook
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        // 3. 向 Sheet 中写入数据
        this.excelAsBeanService.writHeaderAndDataToSheet(hssfWorkbook,"User", cls, users);
        // 4. 返回 workbook
        return hssfWorkbook;
    }

    public HSSFWorkbook downloadCreateUserTemplate(Class formCls){
        HSSFWorkbook workbook = new HSSFWorkbook();
        this.excelAsBeanService.writTemplateToSheet(workbook,"CreateUser", formCls);
        return workbook;
    }

    public List<CreateUserFrom> readFromExcel(HSSFSheet hssfSheet, Class cls){
        return this.excelAsBeanService.readFromSheet(hssfSheet, cls);
    }

    private<D extends User> List<D> createUser(int count, Supplier<D> supplier) {
        List<D> users = Lists.newArrayList();
        for (int i = 0; i < count; i++){
            Integer age = (i) % 50 + 5;
            D user = supplier.get();
            user.setId(i + 1L);
            user.setName("测试用户-" + i);
            user.setAge(age);
            user.setBirthAt(DateUtils.addYears(new Date(), - age));
            user.setAddress(createAddress(i+1));
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
