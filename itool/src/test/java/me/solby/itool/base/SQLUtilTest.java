package me.solby.itool.base;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * me.solby.itool.base
 *
 * @author majhdk
 * @date 2019-08-04
 */
class SQLUtilTest {

    @Test
    void mysqlEscape() {
        assertEquals("\\_aa", SQLUtil.mysqlEscape("_aa"));
        assertEquals("\\%aa", SQLUtil.mysqlEscape("%aa"));
        assertEquals("\\'aa", SQLUtil.mysqlEscape("'aa"));
        assertEquals("/aa", SQLUtil.mysqlEscape("/aa"));
        assertEquals("\\aa", SQLUtil.mysqlEscape("\\aa"));
    }

}