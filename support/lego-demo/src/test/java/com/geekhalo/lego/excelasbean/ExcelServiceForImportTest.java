package com.geekhalo.lego.excelasbean;

import com.geekhalo.lego.DemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
public class ExcelServiceForImportTest {
    @Autowired
    private ExcelService excelService;

    @Test
    void downloadTemplateUserV1() throws IOException {
        HSSFWorkbook workbook = excelService.downloadCreateUserTemplate(CreateUserFromV1.class);
        workbook.write(new File("/tmp/createUserFromV1.xls"));
    }

    @Test
    void readUserV1() throws IOException {
        String fileName = "createUserFromV1_Data.xls";
        HSSFWorkbook hssfWorkbook = loadWorkbook(fileName);

        List<CreateUserFrom> forms = excelService.readFromExcel(hssfWorkbook.getSheet("CreateUser"), CreateUserFromV1.class);
        Assertions.assertNotNull(forms);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(forms));
        forms.stream()
                .map(createUserFrom -> (CreateUserFromV1) createUserFrom)
                .forEach(createUserFrom -> {
                    Assertions.assertNotNull(createUserFrom.getName());
                    Assertions.assertNotNull(createUserFrom.getBirthAt());
                    Assertions.assertNotNull(createUserFrom.getAge());
                    log.info("CreateUserV1: {}", createUserFrom);
        });
    }

    private HSSFWorkbook loadWorkbook(String fileName) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/excelAsBean/" + fileName);
        InputStream is = resource.getInputStream();
        return new HSSFWorkbook(is);
    }


    @Test
    void downloadTemplateUserV2() throws IOException {
        HSSFWorkbook workbook = excelService.downloadCreateUserTemplate(CreateUserFromV2.class);
        workbook.write(new File("/tmp/createUserFromV2.xls"));
    }

    @Test
    void readUserV2() throws IOException {
        String fileName = "createUserFromV2_Data.xls";

        HSSFWorkbook hssfWorkbook = loadWorkbook(fileName);

        List<CreateUserFrom> forms = excelService.readFromExcel(hssfWorkbook.getSheet("CreateUser"), CreateUserFromV2.class);
        Assertions.assertNotNull(forms);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(forms));
        forms.stream()
                .map(createUserFrom -> (CreateUserFromV2) createUserFrom)
                .forEach(createUserFrom -> {
                    Assertions.assertNotNull(createUserFrom.getName());
                    Assertions.assertNotNull(createUserFrom.getBirthAt());
                    Assertions.assertNotNull(createUserFrom.getAge());

                    Assertions.assertNotNull(createUserFrom.getAddressForm());
//                    Assertions.assertNotNull(createUserFrom.getAddressForm().getL1());
//                    Assertions.assertNotNull(createUserFrom.getAddressForm().getL2());
//                    Assertions.assertNotNull(createUserFrom.getAddressForm().getL3());
//                    Assertions.assertNotNull(createUserFrom.getAddressForm().getL4());

                    log.info("CreateUserV2: {}", createUserFrom);
                });
    }
}
