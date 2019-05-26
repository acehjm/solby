package me.solby.itool.tree;

import me.solby.itool.json.JsonUtil;
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

    private static List<Node> nodeList = new ArrayList<>();

    @BeforeAll
    public static void setUp(){
        Node node = new Node("001", "aaa", true, null);
        Node node1 = new Node("002", "bbb", false, null);
        Node node3 = new Node("003", "ccc", true, "001");
        Node node4 = new Node("004", "ddd", true, "003");
        Node node5 = new Node("005", "eee", false, "003");
        Node node6 = new Node("006", "fff", false, "002");
        nodeList.add(node);
        nodeList.add(node1);
        nodeList.add(node3);
        nodeList.add(node4);
        nodeList.add(node5);
        nodeList.add(node6);
    }

    @Test
    public void treeBuilder() {
        List<Node> node1 = TreeBuilder.tree(nodeList, null);
        System.out.println(JsonUtil.toJson(node1));

        System.out.println("-----------------------------------------");

        List<Node> node2 = TreeBuilder.tree(nodeList, "002");
        System.out.println(JsonUtil.toJson(node2));
    }
}