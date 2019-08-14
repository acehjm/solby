package me.solby.ifile.excel.common;

import me.solby.ifile.excel.exception.ExcelException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * me.solby.ifile.excel.common
 *
 * @author majhdk
 * @date 2019-08-10
 */
public enum ExcelFormatEnum {

    /**
     * hssf-2003及以下版本
     */
    EXCEL_HSSF(".xls") {
        @Override
        public Workbook getWorkBook(InputStream is) {
            try (is) {
                return new HSSFWorkbook(is);
            } catch (IOException e) {
                throw new ExcelException("Get HSSF workbook error ", e);
            }
        }
    },

    /**
     * xssf-2007及以上版本
     */
    EXCEL_XSSF(".xlsx") {
        @Override
        public Workbook getWorkBook(InputStream is) {
            try (is) {
                return new XSSFWorkbook(is);
            } catch (IOException e) {
                throw new ExcelException("Get XSSF workbook error ", e);
            }
        }
    },
    ;

    private String suffix;

    ExcelFormatEnum(String suffix) {
        this.suffix = suffix;
    }

    public static ExcelFormatEnum suffixOf(String suffix) {
        return Arrays.stream(ExcelFormatEnum.values())
                .filter(it -> suffix.equals(it.getSuffix()))
                .findFirst()
                .orElseThrow(() -> new ExcelException("Invalid excel format"));
    }

    public String getSuffix() {
        return suffix;
    }

    public Workbook getWorkBook(InputStream is) {
        return null;
    }

}
