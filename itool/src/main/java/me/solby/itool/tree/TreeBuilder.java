package me.solby.itool.tree;

import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author majhdk
 * @DESCRIPTION Tree构建工具
 * @date 2018-12-22
 */
public class TreeBuilder {

    /**
     * 树节点构造器
     *
     * @param nodes 节点集合
     * @param currentNode   标签
     * @return
     */
    public static List<Node> treeBuilder(List<Node> nodes, String currentNode) {
        List<Node> tree = new ArrayList<>();
        if (ObjectUtils.isEmpty(nodes))
            return tree;

        Map<String, List<Node>> map = new HashMap<>();
        for (Node node : nodes) {
            if (!ObjectUtils.isEmpty(map.get(node.getParentTag()))) {
                map.get(node.getParentTag()).add(node);
            } else {
                List<Node> list = new ArrayList<>();
                list.add(node);
                map.put(node.getParentTag(), list);
            }
        }
        for (Node node : nodes) {
            if (map.containsKey(node.getUid())) {
                node.setChildren(map.get(node.getUid()));
            }
            if (ObjectUtils.isEmpty(currentNode) && ObjectUtils.isEmpty(node.getParentTag())
                    || !ObjectUtils.isEmpty(currentNode) && ObjectUtils.nullSafeEquals(node.getUid(), currentNode)) {
                tree.add(node);
            }
        }
        return tree;
    }

}
