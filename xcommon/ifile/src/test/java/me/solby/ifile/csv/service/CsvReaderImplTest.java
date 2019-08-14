package me.solby.ifile.csv.service;

import me.solby.ifile.csv.service.impl.CsvReaderImpl;
import me.solby.itool.json.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * me.solby.ifile.icsv.service
 *
 * @author majhdk
 * @date 2019-08-04
 */
public class CsvReaderImplTest {

    private File file;

    @Before
    public void setUp() {
        try {
            file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "read.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRead1() {
        Instant start = Instant.now();
        List<Item> list = new CsvReaderImpl().read(file, Item.class);
        System.out.println(Instant.now().toEpochMilli() - start.toEpochMilli());

        System.out.println(JsonUtil.toJson(list));
        System.out.println(list.size());
    }

    @Test
    public void testRead2() {
        List<Item> list = new CsvReaderImpl().read(file, Item.class, new ItemVerifier());
        System.out.println(JsonUtil.toJson(list));
    }
}