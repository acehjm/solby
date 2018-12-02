package me.solby.ifile.icsv.validate;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-12-01
 */
public class DefaultDataValidator<T> implements DataValidator<T> {
    public DefaultDataValidator(){

    }

    @Override
    public boolean valid(DataValidResult<T> valid, DataReadRecord<T> record) {
        valid.success();
        return true;
    }
}
