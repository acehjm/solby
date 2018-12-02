package me.solby.ifile.icsv.service;

import com.opencsv.CSVWriter;
import me.solby.ifile.icsv.serialize.CsvLineSerializer;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-12-01
 */
public class CsvWriterImpl implements ICsvWriter {

    public CsvWriterImpl() {
    }

    @Override
    public <T> void write(List<String[]> heads, List<T> objects, OutputStream os, String charset,
                          boolean autoClose, CsvLineSerializer<T> serializer)
            throws IOException {
        this.assertParameters(os, charset, serializer);
        this.checkCharset(os, charset);
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(os, charset), ',', '"', '"', "\n")) {
            this.writeHead(writer, heads);
            for (T object : objects) {
                this.writeLine(writer, object, serializer);
            }
        } finally {
            if (autoClose) {
                os.close();
            }
        }
    }

    private void writeHead(CSVWriter writer, List<String[]> heads) {
        if (heads != null) {
            for (String[] head : heads) {
                writer.writeNext(head);
            }
        }
    }

    private void checkCharset(OutputStream os, String charset) throws IOException {
        if ("utf-8".equalsIgnoreCase(charset) || "utf8".equalsIgnoreCase(charset)) {
            os.write(0);
        }
    }

    private <T> void assertParameters(OutputStream os, String charset, CsvLineSerializer<T> serializer) {
        Assert.notNull(os, "the parameter os is null");
        Assert.notNull(charset, "the parameter charset is blank");
        Assert.notNull(serializer, "the parameter serializer is null");
    }

    private <T> void writeLine(CSVWriter writer, T object, CsvLineSerializer<T> serializer) {
        String[] strs = serializer.serialize(object);
        writer.writeNext(strs);
        writer.flushQuietly();
    }

}
