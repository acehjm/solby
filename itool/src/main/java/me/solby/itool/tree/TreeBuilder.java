package me.solby.itool.tree;

import me.solby.itool.verify.ObjectUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public static List<TNode> tree(final List<TNode> nodes, final String currentNode) {
        List<TNode> tree = new ArrayList<>();
        if (null == nodes || nodes.isEmpty())
            return tree;

        Map<String, List<TNode>> map = new HashMap<>();
        nodes.forEach(node -> {
            if (ObjectUtil.isNotEmpty(map.get(node.getParentTag()))) {
                map.get(node.getParentTag()).add(node);
            } else {
                List<TNode> list = new ArrayList<>();
                list.add(node);
                map.put(node.getParentTag(), list);
            }
        });

        nodes.forEach(node -> {
            if (map.containsKey(node.getUid())) {
                node.setChildren(map.get(node.getUid()));
            }

            boolean isParentBlank = ObjectUtil.isEmpty(currentNode) && ObjectUtil.isEmpty(node.getParentTag());
            boolean isCurrentNode = ObjectUtil.isNotEmpty(currentNode) && Objects.equals(node.getUid(), currentNode);
            if (isParentBlank || isCurrentNode) {
                tree.add(node);
            }
        });
        return tree;
    }

}
