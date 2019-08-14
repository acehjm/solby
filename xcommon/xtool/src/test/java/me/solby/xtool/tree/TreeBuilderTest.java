package me.solby.xtool.tree;

import me.solby.xtool.json.JsonUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-12-23
 */
public class TreeBuilderTest {

    private static List<TNode> nodeList = new ArrayList<>();

    @BeforeAll
    public static void setUp(){
        TNode node = new TNode("001", "aaa", true, null);
        TNode node1 = new TNode("002", "bbb", false, null);
        TNode node3 = new TNode("003", "ccc", true, "001");
        TNode node4 = new TNode("004", "ddd", true, "003");
        TNode node5 = new TNode("005", "eee", false, "003");
        TNode node6 = new TNode("006", "fff", false, "002");
        nodeList.add(node);
        nodeList.add(node1);
        nodeList.add(node3);
        nodeList.add(node4);
        nodeList.add(node5);
        nodeList.add(node6);
    }

    @Test
    public void treeBuilder() {
        List<TNode> node1 = TreeBuilder.tree(nodeList, null);
        System.out.println(JsonUtil.toJson(node1));

        System.out.println("-----------------------------------------");

        List<TNode> node2 = TreeBuilder.tree(nodeList, "002");
        System.out.println(JsonUtil.toJson(node2));
    }
}