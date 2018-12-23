package me.solby.ifile.iexcel;

import com.fasterxml.jackson.core.type.TypeReference;
import me.solby.ifile.iexcel.handler.ExportHandler;
import me.solby.ifile.iexcel.handler.ImportHandler;
import me.solby.itool.json.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author majhdk
 * @DESCRIPTION 测试导入导出
 * @date 2018-12-02
 */
public class ExcelUtilTest {

    private ImportHandler importHandler = new ImportHandler();

    private ExportHandler exportHandler = new ExportHandler();

    private MultipartFile multipartFile;

    @Before
    public void setUp() {
        try {
            File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "test.xls");

            InputStream is = new FileInputStream(file);
            multipartFile = new MockMultipartFile("test.xls", "test.xls", null, is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readExcelTest() {
        List<ExcelData> list = importHandler.readExcel(multipartFile, new TypeReference<>() {
        });

        System.out.println("list: " + JsonUtil.toJson(list));

        for (ExcelData data : list) {
            System.err.println(data.getName() + " // " + data.getIndex());
        }
    }

    @Test
    public void writeExcel() throws FileNotFoundException {
        List<ExcelData> list = new ArrayList<>();
        list.add(new ExcelData("z1", "101"));
        list.add(new ExcelData("a2", "202"));
        list.add(new ExcelData("c3", "303"));
        list.add(new ExcelData("t4", "404"));
        list.add(new ExcelData("k5", "5 05"));
        list.add(new ExcelData("o6", " 60 6 "));

        String path = ResourceUtils.getURL("classpath:").getPath() + "export.xls";
        System.out.println("path: --> " + path);
        exportHandler.writeExcelPath(path, list, ExcelData.class, false);
    }
}