package Project;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by user on 25.11.2017.
 */
public class Hierarchy<NodeType extends Node> implements Serializable {
    private Map<NodeType, Set<NodeType>> children;
    private Map<NodeType, NodeType> parent;

    Hierarchy() {
        this.children = new HashMap<>();
        this.parent = new HashMap<>();
    }

    public NodeType getParent(NodeType NodeType) {
        return this.parent.get(NodeType);
    }

    public void setRelHead(NodeType head) {
        this.parent.put(head, null);
        this.children.put(head, null);
    }

    public void setParent(NodeType NodeType, NodeType parent) {//нужно удалить ребенка от родителя первого
        this.getChildren(getParent(NodeType)).remove(NodeType);
        System.out.print(NodeType);
        this.parent.put(NodeType, parent);
        if (this.children.get(parent) == null) {
            Set<NodeType> childrenList = new HashSet<>();
            childrenList.add(NodeType);
            this.children.put(parent, childrenList);
        } else {
            this.children.get(parent).add(NodeType);
        }
    }

    public Set<NodeType> getChildren(NodeType NodeType) {
        //  System.out.print(this.children.get(NodeType));
        Set<NodeType> newChildren = new HashSet<>();
        if (this.children.get(NodeType) != null) {
            newChildren.addAll(this.children.get(NodeType));
        }
        return newChildren;
    }


    public void addChildren(NodeType parent, Set<NodeType> children) {
        if (this.children.get(parent) != null) {
            this.children.get(parent).addAll(children);
        } else {
            this.children.put(parent, children);
        }
        if (children != null) {
            for (NodeType node : children) {
                this.parent.put(node, parent);
            }
        }
    }

    /*public void deleteChild(NodeType parent, NodeType NodeType) {
        if (parent != null) {
            this.children.get(parent).remove(NodeType);
        } else {
            deleteParent(NodeType);
        }
    }

    public void deleteParent(NodeType NodeType) {
        this.children.get(getParent(NodeType)).remove(NodeType);
        parent.remove(NodeType);
    }*/
    public void deleteChild(NodeType parent, NodeType NodeType) {
        if (parent != null) {
            this.children.get(parent).remove(NodeType);
        } else {
            deleteParent(NodeType);
        }
    }

    public void deleteParent(NodeType NodeType) {
//System.out.print();
        this.children.remove(NodeType);
// this.children.get(getParent(NodeType)).remove(NodeType);
        parent.remove(NodeType);

    }

    @Override
    public String toString() {
        return "Hierarchy{" +
                "children=" + children +
                ", parent=" + parent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hierarchy hierarchy = (Hierarchy) o;

        if (children != null ? !children.equals(hierarchy.children) : hierarchy.children != null) return false;
        return parent != null ? parent.equals(hierarchy.parent) : hierarchy.parent == null;
    }

    @Override
    public int hashCode() {
        int result = children != null ? children.hashCode() : 0;
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        return result;
    }
}