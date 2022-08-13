package com.geekhalo.lego.core.excelasbean.support.write.cell.writer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.lang.reflect.AnnotatedElement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class DateFormatHSSFCellWriter implements HSSFCellWriter{
    private final String format;
    private final AnnotatedElement element;

    public DateFormatHSSFCellWriter(String format,
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
        log.warn("failed to handle DateFormat for {}", this.element);
    }
}