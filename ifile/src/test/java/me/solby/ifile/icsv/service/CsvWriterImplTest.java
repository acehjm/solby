package me.solby.ifile.icsv.service;

import com.fasterxml.jackson.core.type.TypeReference;
import me.solby.ifile.icsv.service.impl.CsvWriterImpl;
import me.solby.itool.json.JsonUtil;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.List;

/**
 * me.solby.ifile.icsv.service
 *
 * @author majhdk
 * @date 2019-08-07
 */
public class CsvWriterImplTest {

    private String data = "[{\"name\":\"呃呃\",\"size\":10,\"color\":\"red\"},{\"name\":\"bb\",\"size\":5,\"color\":\"blue\"},{\"name\":\"cc\",\"size\":23,\"color\":\"blue\"}]";

    @Test
    public void tesWrite1() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        List<Item> items = JsonUtil.toCollection(data, new TypeReference<>() {
        });
        new CsvWriterImpl().write(items, "write.csv", Item.class, response);
        System.out.println(response.getBufferSize());
    }

    @Test
    public void testWrite2() throws IOException {
        String path = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX).getAbsolutePath();

        List<Item> items = JsonUtil.toCollection(data, new TypeReference<>() {
        });
        new CsvWriterImpl().write(items, path + "/write.csv", Item.class);
    }
}