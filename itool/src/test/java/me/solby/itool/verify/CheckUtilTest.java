package me.solby.itool.verify;

import me.solby.itool.tree.Node;
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
        Node node1 = new Node("11", "aa", true, null);
        Node node2 = new Node("11", "aa", true, null);

        CheckUtil.checkEquals("aa", "aa", "notEquals");
        CheckUtil.checkNotEquals("aa", 11, "Equals");
        CheckUtil.checkEquals(node1, node2, "notEquals");
    }

    @Test
    void checkEmpty() {
        Node node1 = new Node("11", "aa", true, null);

        CheckUtil.checkNotEmpty("aa", "Empty");
        CheckUtil.checkNotEmpty(node1, "Empty");
        CheckUtil.checkEmpty(null, "notEmpty");
    }
}