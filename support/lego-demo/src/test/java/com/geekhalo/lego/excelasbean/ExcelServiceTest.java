package com.geekhalo.lego.excelasbean;

import com.geekhalo.lego.DemoApplication;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by taoli on 2022/8/8.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
class ExcelServiceTest {
    @Autowired
    private ExcelService excelService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void downloadUser() throws IOException {
        HSSFWorkbook workbook = excelService.downloadUser();
        workbook.write(new File("/tmp/downloadUser.xls"));
    }
}