package me.solby.xtool.verify;

import me.solby.xtool.tree.TNode;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * me.solby.itool.verify
 *
 * @author majhdk
 * @date 2019-06-10
 */
class ObjectUtilTest {

    @Test
    void isEmpty() {
        assertTrue(ObjectUtil.isEmpty(null));
        assertTrue(ObjectUtil.isEmpty(""));
        assertFalse(ObjectUtil.isEmpty("ab"));
        assertTrue(ObjectUtil.isEmpty(List.of()));
        assertFalse(ObjectUtil.isEmpty(new int[]{1, 2}));
        assertTrue(ObjectUtil.isEmpty(new int[]{}));
        assertTrue(ObjectUtil.isEmpty(Map.of()));
        assertFalse(ObjectUtil.isEmpty(new Object()));
        assertTrue(ObjectUtil.isEmpty(Collections.EMPTY_SET));
    }

    @Test
    void isNotEmpty() {
        assertFalse(ObjectUtil.isNotEmpty(null));
        assertFalse(ObjectUtil.isNotEmpty(""));
        assertTrue(ObjectUtil.isNotEmpty("ab"));
        assertFalse(ObjectUtil.isNotEmpty(List.of()));
        assertTrue(ObjectUtil.isNotEmpty(new int[]{1, 2}));
        assertFalse(ObjectUtil.isNotEmpty(new int[]{}));
        assertFalse(ObjectUtil.isNotEmpty(Map.of()));
        assertTrue(ObjectUtil.isNotEmpty(new TNode("11", null, null, null)));
        assertFalse(ObjectUtil.isNotEmpty(Collections.EMPTY_SET));
    }

    @Test
    void defaultIfEmpty() {
        assertEquals(ObjectUtil.defaultIfEmpty(null, "NULL"), "NULL");
        assertEquals(ObjectUtil.defaultIfEmpty("full", "empty"), "full");
    }

    @Test
    void compare() {
        assertTrue(ObjectUtil.compare("aa", "bb") < 0);
        assertTrue(ObjectUtil.compare("bb", "aa") > 0);
        assertTrue(ObjectUtil.compare("bb", null, true) < 0);
        assertEquals(0, ObjectUtil.compare("bb", "bb"));
    }

}