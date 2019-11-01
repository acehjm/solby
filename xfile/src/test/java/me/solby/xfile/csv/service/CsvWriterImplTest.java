package me.solby.xfile.csv.service;

import com.fasterxml.jackson.core.type.TypeReference;
import me.solby.xfile.csv.service.impl.CsvWriterImpl;
import me.solby.xtool.json.JsonUtil;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * me.solby.ifile.icsv.service
 *
 * @author majhdk
 * @date 2019-08-07
 */
public class CsvWriterImplTest {

    private String data = "[{\"name\":\"呃呃\",\"size\":10,\"color\":\"red\"},{\"name\":\"bb\",\"size\":5,\"color\":\"blue\"},{\"name\":\"cc\",\"size\":23,\"color\":\"blue\"}]";

    /*
        测试数据直接写入到Servlet输出流
    */
    @Test
    public void tesWrite1() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        List<Item> items = JsonUtil.toCollection(data, new TypeReference<>() {
        });
        new CsvWriterImpl().write(items, "write.csv", Item.class, response);
        System.out.println(response.getBufferSize());
    }

    /*
        测试数据直接写入文件
    */
    @Test
    public void testWrite2() throws IOException {
        String path = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath() + "write_03.csv";

        List<Item> items = JsonUtil.toCollection(data, new TypeReference<>() {
        });
        for (int i = 0; i < 50000; i++) {
            items.add(new Item("item" + i, i, "red"));
        }

        Instant start = Instant.now();
        new CsvWriterImpl().write(items, path, Item.class);
        Instant end = Instant.now();
        System.out.println(end.toEpochMilli() - start.toEpochMilli());
    }

    /*
        测试字节数组输出流写入到Servlet输出流
    */
    @Test
    public void testWrite3() throws IOException {
        List<Item> items = JsonUtil.toCollection(data, new TypeReference<>() {
        });
        ByteArrayOutputStream outputStream = new CsvWriterImpl().writeOutputStream(items, "write.csv", Item.class);

        MockHttpServletResponse response = new MockHttpServletResponse();
        response.getOutputStream().write(outputStream.toByteArray());
        response.setCharacterEncoding("utf-8");

        System.out.println(response.getContentAsString());
    }

    /*
        测试字节数组输出流写入到文件
     */
    @Test
    public void testWrite4() throws IOException {
        String path = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath() + "write_02.csv";

        List<Item> items = JsonUtil.toCollection(data, new TypeReference<>() {
        });

        ByteArrayOutputStream outputStream = new CsvWriterImpl().writeOutputStream(items, "write_02.csv", Item.class);
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        fileOutputStream.write(outputStream.toByteArray());
    }
}