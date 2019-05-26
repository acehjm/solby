package me.solby.itool.tree;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author majhdk
 * @DESCRIPTION Tree构建工具
 * @date 2018-12-22
 */
public class TreeBuilder {

    private TreeBuilder() {
    }

    /**
     * 树节点构造器
     *
     * @param nodes       节点集合
     * @param currentNode 标签
     * @return
     */
    public static List<Node> tree(List<Node> nodes, String currentNode) {
        List<Node> tree = new ArrayList<>();
        if (null == nodes || nodes.isEmpty())
            return tree;

        Map<String, List<Node>> map = new HashMap<>();
        for (Node node : nodes) {
            if (null != map.get(node.getParentTag())) {
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
            if (StringUtils.isBlank(currentNode) && StringUtils.isBlank(node.getParentTag())
                    || StringUtils.isNoneBlank(currentNode) && Objects.equals(node.getUid(), currentNode)) {
                tree.add(node);
            }
        }
        return tree;
    }

}
