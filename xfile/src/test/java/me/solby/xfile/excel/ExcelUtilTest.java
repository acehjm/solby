package me.solby.xfile.excel;

import me.solby.xfile.excel.service.ExcelReader;
import me.solby.xfile.excel.service.ExcelWriter;
import me.solby.xfile.excel.service.impl.ExcelReaderImpl;
import me.solby.xfile.excel.service.impl.ExcelWriterImpl;
import me.solby.xtool.json.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author majhdk
 * @DESCRIPTION 测试导入导出
 * @date 2018-12-02
 */
public class ExcelUtilTest {

    private ExcelReader reader = new ExcelReaderImpl();

    private ExcelWriter writer = new ExcelWriterImpl();

    private InputStream is;

    @Before
    public void setUp() {
        try {
            File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "test.xls");

            is = new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readExcelTest() {
        Instant start = Instant.now();
        List<ExcelData> list = reader.readExcel("test.xls", is, ExcelData.class);
        Instant end = Instant.now();

        System.out.println(end.toEpochMilli() - start.toEpochMilli());
        System.out.println("list: " + JsonUtil.toJson(list));
    }

    @Test
    public void writeExcel() throws FileNotFoundException {
        List<ExcelData> list = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            list.add(new ExcelData("z1", i + ""));
        }

        String path = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath() + "export.xlsx";
        System.out.println("path: --> " + path);

        Instant start = Instant.now();
        writer.writeExcelPath(path, list, ExcelData.class, true);
        Instant end = Instant.now();

        System.out.println(end.toEpochMilli() - start.toEpochMilli());
    }
}