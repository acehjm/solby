package me.solby.itool.tree;

import lombok.Data;

import java.util.List;

/**
 * @author majhdk
 * @DESCRIPTION Tree节点信息
 * @date 2018-12-22
 */
@Data
public class TNode {

    /**
     * 树节点唯一ID
     */
    private String uid;

    /**
     * 树节点名称
     */
    private String name;

    /**
     * 是否检出
     */
    private Boolean checked;

    /**
     * 树节点的父节点
     */
    private String parentTag;

    /**
     * 树节点的子节点
     */
    private List<TNode> children;

    public TNode() {
    }

    public TNode(String uid, String name, Boolean checked, String parentTag) {
        this.uid = uid;
        this.name = name;
        this.checked = checked;
        this.parentTag = parentTag;
    }

}
