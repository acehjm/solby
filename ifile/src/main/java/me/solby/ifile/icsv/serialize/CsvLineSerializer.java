package me.solby.ifile.icsv.serialize;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-12-01
 */
public interface CsvLineSerializer<T> {
    String[] serialize(T t);
}
