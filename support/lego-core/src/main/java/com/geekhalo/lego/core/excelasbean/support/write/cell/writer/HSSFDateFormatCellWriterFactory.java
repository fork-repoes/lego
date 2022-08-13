package com.geekhalo.lego.core.excelasbean.support.write.cell.writer;

import com.geekhalo.lego.annotation.excelasbean.HSSFDateFormat;
import com.geekhalo.lego.core.excelasbean.support.write.cell.writer.HSSFCellWriter;
import com.geekhalo.lego.core.excelasbean.support.write.cell.writer.HSSFCellWriterContext;
import com.geekhalo.lego.core.excelasbean.support.write.cell.writer.HSSFDataCellWriterFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;

import java.lang.reflect.AnnotatedElement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by taoli on 2022/8/12.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 处理 HSSFDateFormat 注解
 */
@Order(1)
@Slf4j
public class HSSFDateFormatCellWriterFactory implements HSSFDataCellWriterFactory {

    @Override
    public boolean support(AnnotatedElement annotatedElement) {
        return AnnotatedElementUtils.hasAnnotation(annotatedElement, HSSFDateFormat.class);
    }

    @Override
    public HSSFCellWriter create(AnnotatedElement element) {
        HSSFDateFormat dateFormat = AnnotatedElementUtils.findMergedAnnotation(element, HSSFDateFormat.class);
        return new DateFormatHSSFCellWriter(dateFormat.value(), element);
    }

    private static class DateFormatHSSFCellWriter implements HSSFCellWriter{
        private final String format;
        private final AnnotatedElement element;

        private DateFormatHSSFCellWriter(String format,
                                         AnnotatedElement element) {
            this.format = format;
            this.element = element;
        }

        @Override
        public void write(HSSFCellWriterContext context, HSSFCell cell, Object data) {
            if (data instanceof Date){
                Date date = (Date) data;
                String value = DateFormatUtils.format(date, this.format);
                cell.setCellValue(value);
                return;
            }

            if (data instanceof LocalDateTime){
                LocalDateTime localDateTime = (LocalDateTime) data;
                String value = localDateTime.format(DateTimeFormatter.ofPattern(this.format));
                cell.setCellValue(value);
                return;
            }

            if (data instanceof LocalDate){
                LocalDate localDate = (LocalDate) data;
                String value = localDate.format(DateTimeFormatter.ofPattern(this.format));
                cell.setCellValue(value);
                return;
            }

            if (data instanceof LocalTime){
                LocalTime localTime = (LocalTime) data;
                String value = localTime.format(DateTimeFormatter.ofPattern(this.format));
                cell.setCellValue(value);
                return;
            }
            log.warn("failed to handle @HSSFDateFormat for {}", this.element);
        }
    }
}
