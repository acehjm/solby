package me.solby.itool.verify;

import me.solby.itool.tree.TNode;
import org.junit.jupiter.api.Test;

/**
 * me.solby.itool.verify
 *
 * @author majhdk
 * @date 2019-06-11
 */
class CheckUtilTest {

    @Test
    void checkTrue() {
        CheckUtil.checkTrue(true, "false");
        CheckUtil.checkFalse(false, "true");
    }

    @Test
    void checkEquals() {
        TNode node1 = new TNode("11", "aa", true, null);
        TNode node2 = new TNode("11", "aa", true, null);

        CheckUtil.checkEquals("aa", "aa", "notEquals");
        CheckUtil.checkNotEquals("aa", 11, "Equals");
        CheckUtil.checkEquals(node1, node2, "notEquals");
    }

    @Test
    void checkEmpty() {
        TNode node1 = new TNode("11", "aa", true, null);

        CheckUtil.checkNotEmpty("aa", "Empty");
        CheckUtil.checkNotEmpty(node1, "Empty");
        CheckUtil.checkEmpty(null, "notEmpty");
    }
}