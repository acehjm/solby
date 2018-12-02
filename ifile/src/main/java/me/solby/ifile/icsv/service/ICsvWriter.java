package me.solby.ifile.icsv.service;

import me.solby.ifile.icsv.serialize.CsvLineSerializer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-12-01
 */
public interface ICsvWriter {

    <T> void write(List<String[]> heads, List<T> objects, OutputStream os, String charset,
                   boolean autoClose, CsvLineSerializer<T> serializer)
            throws IOException;

}
