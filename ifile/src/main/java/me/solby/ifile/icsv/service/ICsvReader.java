package me.solby.ifile.icsv.service;

import com.fasterxml.jackson.core.type.TypeReference;
import me.solby.ifile.icsv.serialize.FieldInformation;
import me.solby.ifile.icsv.validate.DataReadRecord;
import me.solby.ifile.icsv.validate.DataValidator;

import java.io.File;
import java.util.List;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-12-01
 */
public interface ICsvReader {

    <T> String read(File file, TypeReference<T> type, DataValidator<T> validator);

    <T> String read(File file, TypeReference<T> type, List<FieldInformation> infos, DataValidator<T> validator);

    <T> DataReadRecord<T> getReadRecord(String progressCode);

    boolean removeRecord(String progressCode, boolean deleteFile);
}
