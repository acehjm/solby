package me.solby.ifile.icsv.constant;

/**
 * @author majhdk
 * @DESCRIPTION 错误枚举类
 * @date 2018-11-29
 */
public enum ValidFailType {

    FormatError,
    NoUnique,
    ValueEmpty,
    ValueRangeError,
    DeserializeError,
    NoUniqueFile,
    NoUniqueDB;

    ValidFailType() {
    }

}
