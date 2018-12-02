package me.solby.ifile.icsv.validate;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-12-01
 */
public interface DataValidator<T> {

    boolean valid(DataValidResult<T> valid, DataReadRecord<T> record);

}
