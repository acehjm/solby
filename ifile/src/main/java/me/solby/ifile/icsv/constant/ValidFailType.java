package me.solby.ifile.icsv.constant;

/**
 * @author majhdk
 * @DESCRIPTION 错误枚举类
 * @date 2018-11-29
 */
public enum ValidFailType {

    FormatError,
    ValueEmpty,
    ValueRangeError,
    DeserializeError,
    NotUnique,
    NotUniqueFile,
    NotUniqueDB;

    ValidFailType() {
    }

}
