package com.geekhalo.lego.core.excelasbean.support.write.sheet;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Created by taoli on 2022/8/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
@Builder
public class HSSFSheetContext {
    private final HSSFWorkbook workbook;
}
