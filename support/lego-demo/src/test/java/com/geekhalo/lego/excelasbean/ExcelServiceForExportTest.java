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

/**
 * Created by taoli on 2022/8/8.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
class ExcelServiceForExportTest {
    @Autowired
    private ExcelService excelService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void downloadUserV1() throws IOException {
        HSSFWorkbook workbook = excelService.downloadUser(UserV1.class, UserV1::new);
        workbook.write(new File("/tmp/downloadUserV1.xls"));
    }

    @Test
    void downloadUserV2() throws IOException {
        HSSFWorkbook workbook = excelService.downloadUser(UserV2.class, UserV2::new);
        workbook.write(new File("/tmp/downloadUserV2.xls"));
    }

    @Test
    void downloadUserV3() throws IOException {
        HSSFWorkbook workbook = excelService.downloadUser(UserV3.class, UserV3::new);
        workbook.write(new File("/tmp/downloadUserV3.xls"));
    }

    @Test
    void downloadUserV4() throws IOException {
        HSSFWorkbook workbook = excelService.downloadUser(UserV4.class, UserV4::new);
        workbook.write(new File("/tmp/downloadUserV4.xls"));
    }

    @Test
    void downloadUserV5() throws IOException {
        HSSFWorkbook workbook = excelService.downloadUser(UserV5.class, UserV5::new);
        workbook.write(new File("/tmp/downloadUserV5.xls"));
    }
}