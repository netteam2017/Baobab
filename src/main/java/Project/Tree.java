package Project;

import java.io.Serializable;
import java.util.*;
/*
/**
 * Created by user on 16.11.2017.
 */

public abstract class Tree<NodeType extends Node> implements Serializable {
    private NodeType head;
    private Hierarchy hierarchy;
    private Map<Id, NodeType> nodeMap;

    public Tree(NodeType head) {
        this.head = head;
            this.hierarchy = new Hierarchy();
            this.nodeMap = new HashMap<>();
            hierarchy.setRelHead(this.head);
            hierarchy.addChildren(this.head, null);
            this.nodeMap.put(new Id(1, 1), head);


    }

    abstract NodeType createNode(NodeType oldNode, Id newId);

    abstract Tree<NodeType> createTree(NodeType head);

    /*  public Tree() {
              this.head = new NodeType(new Id(1, 1));
              this.nodeMap = new HashMap<>();
              this.nodeMap.put(head.getId(), head);
              this.hierarchy = new Hierarchy();


      }
  */
    public NodeType getNodeType(Id id) {
        NodeType tmp = null;

        if (this.nodeMap.containsKey(id) == true) {
            tmp = getNodeMap().get(id);
        } else {
            System.out.print("Vertex is not exist! " + id);
        }
        return tmp;
    }

    public Map<Id, NodeType> getNodeMap() {
        return this.nodeMap;
    }

    public Hierarchy<NodeType> getHierarchy() {
        return hierarchy;
    }

    public NodeType getHead() {
        return this.head;
    }

    public Id getIdOfNodeType(NodeType NodeType) {
        Id id = new Id(0, 0);
        Set<Map.Entry<Id, NodeType>> entrySet = getNodeMap().entrySet();
        for (Map.Entry<Id, NodeType> pair : entrySet) {
            if (NodeType.equals(pair.getValue())) {
                id = pair.getKey();
                return pair.getKey();
            }
        }
        return id;
    }

    public Id getNewNodeTypeId(NodeType parent) {
        int height = parent.getId().getHeight();
        Id newId = new Id(height + 1, getQuantityNumbers(height + 1) + 1);
        return newId;
    }

    public int getQuantityNumbers(int height) {
        int quantity = 0;
        Set<Map.Entry<Id, NodeType>> entrySet = getNodeMap().entrySet();
        for (Map.Entry<Id, NodeType> pair : entrySet) {
            if (pair.getKey().getHeight() == height) {
                quantity = quantity + 1;
            }
        }
        return quantity;
    }

    public void rememberNodeTypeMap(NodeType NodeType) {
        Id id;
        if (nodeMap == null) {
            id = new Id(1, 1);
        } else {
        }

        nodeMap.put(NodeType.getId(), NodeType);
    }

    public void deleteNodeType(NodeType NodeType) {
        if (getNodeMap().containsValue(NodeType) == false) {
            return;
        } else {
            ArrayList<NodeType> newNodeType = new ArrayList<>(getHierarchy().getChildren(NodeType));
            for (NodeType ch : newNodeType) {
                if (ch != null)
                    deleteNodeType(ch);
            }
            getHierarchy().deleteChild(getHierarchy().getParent(NodeType), NodeType);
            getHierarchy().deleteParent(NodeType);
            getNodeMap().remove(NodeType.getId());
        }
    }

    public NodeType addNodeType(NodeType nodeType, Id id) {
        if (getNodeType(id) == null) {
            return null;
        }

        rememberNodeTypeMap(nodeType);
        Set<NodeType> NodeTypeSet = new HashSet<NodeType>();
        NodeTypeSet.add(nodeType);
        getHierarchy().addChildren(getNodeType(id), NodeTypeSet);
        return nodeType;
    }

    public void addNodeTypeForSplit(NodeType NodeType, Id id) {
        if (getNodeType(id) == null) {
            return;
        }
        NodeType tmp = createNode(NodeType, getNewNodeTypeId(getNodeType(id)));
        rememberNodeTypeMap(tmp);
        Set<NodeType> NodeTypeSet = new HashSet<NodeType>();
        NodeTypeSet.add(tmp);
        if (this.getNodeMap().containsValue(NodeType)) {
            getNodeMap().remove(NodeType.getId());
            this.getHierarchy().deleteChild(getHierarchy().getParent(NodeType), NodeType);
        }
        getHierarchy().addChildren(getNodeType(id), NodeTypeSet);
    }

    public void split(Id id) {
        ArrayList<NodeType> children = new ArrayList<>(getHierarchy().getChildren(getNodeType(id)));
        if (getNodeMap().containsKey(id)) {
            NodeType parent = getHierarchy().getParent(getNodeType(id));
            if (getHierarchy().getChildren(getNodeType(id)) != null) {
                for (NodeType ch : children) {
                    System.out.println(getIdOfNodeType(parent).getHeight());
                    addNodeTypeForSplit(ch, getIdOfNodeType(parent));
                }
            }
            deleteNodeType(getNodeType(id));
        } else {
            System.out.print("Error of id");
        }
    }

    public Tree(Tree<NodeType> otherTree) {
        this.head = otherTree.getHead();
        this.nodeMap = otherTree.getNodeMap();
        this.hierarchy = otherTree.getHierarchy();
    }

    public Tree createTree(Id id) {
        Tree<NodeType> newTree = createTree(createNode(this.getNodeType(id), new Id(1, 1)));
        NodeType NodeType = getNodeType(id);
        ArrayList<NodeType> child = new ArrayList<>();
        collect(newTree, newTree.getHead(), NodeType);
        return newTree;
    }

    public void collect(Tree<NodeType> newTree, NodeType newnode, NodeType NodeType) {
        ArrayList<NodeType> children = new ArrayList<>(getHierarchy().getChildren(NodeType));
        if (children != null) {
            if (children.size() != 0) {
                for (NodeType ch : children) {
                    System.out.println("Idnew " + newTree.getIdOfNodeType(NodeType).getHeight());
                    NodeType newNode = newTree.addNodeType(ch, newnode.getId());
                    if (getHierarchy().getChildren(ch) != null)
                        collect(newTree, newNode, ch);
                }
            }
        }
    }

    public void addTree(Tree<NodeType> newTree, Id idParent) {
        NodeType NodeType = newTree.getHead();
        System.out.println("head = " + NodeType);
        NodeType tmp;
        tmp = addNodeType(NodeType, idParent);
        System.out.println("tmp = " + tmp);
        ArrayList<NodeType> children = new ArrayList<>(newTree.getHierarchy().getChildren(NodeType));
        if (children != null) {
            if (children.size() != 0) {
                for (NodeType child : children) {
                    addTreeInternal(newTree, NodeType.getId(), tmp.getId());
                }
            }
        }
    }

    public void addTreeInternal(Tree newTree, Id idParentSource, Id idParentTarget) { //source -из текущего target - куда в новом дереве.
        NodeType NodeType = getNodeType(new Id(idParentSource.getHeight(), idParentSource.getNumber()));
        System.out.println("NodeType = " + NodeType);
        System.out.println("target = " + idParentTarget);
        ArrayList<NodeType> children = new ArrayList<>(newTree.getHierarchy().getChildren(NodeType));
        if (children != null) {
            if (children.size() != 0) {
                for (NodeType child : children) {
                    NodeType tmp = addNodeType(child, idParentTarget);
                    addTreeInternal(newTree, child.getId(), tmp.getId());
                }
            }
        }
    }

    @Override
    public String toString() {

        return "Tree{" +
                "head=" + head +
                //", hierarchy=" + hierarchy +

                '}';
    }
}