package com.geekhalo.lego.core.excelasbean.support.reader;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class DefaultHSSFCellReader implements HSSFCellReader{

    @Override
    public Object readValue(HSSFCell cell){
        CellType cellTypeEnum = cell.getCellTypeEnum();
        switch (cellTypeEnum){
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)){
                    return cell.getDateCellValue();
                }else {
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case BLANK:
                // 空值
                return null;
            case FORMULA:
                // 公式，暂不支持
                break;
            case ERROR:
                // 错误
                break;
            case _NONE:
                break;
        }
        return null;
    }
}
