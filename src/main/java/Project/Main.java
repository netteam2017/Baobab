package Project;

/**
 * Created by user on 15.11.2017.
 */
public class Main {
    public static void main(String[] args) {
        TaskTree taskTree = new TaskTree("1","1");
        System.out.print(taskTree.parseId(""));
    }
}
/*
// Node nodeTest = new Node();
        Node node1 = new Node(1, new Id(1, 1));
        Node node2 = new Node(2, new Id(1, 1));
        Node node3 = new Node(3, new Id(1, 1));
        Node node4 = new Node(4, new Id(1, 1));
        Node node5 = new Node(5, new Id(1, 1));

        Tree tree = new Tree(node1.getData());


        tree.addNode(node2, new Id(1, 1));

        tree.addNode(node3, new Id(1, 1));

        tree.addNode(new Node(4, new Id(1, 1)), new Id(2, 1));
        tree.addNode(new Node(5, new Id(1, 1)), new Id(2, 1));
        System.out.println("________________________");
/// Tree tree2 = tree.createTree(new Id(2, 1));
// tree.split(new Id(2,1));
        Node node11 = new Node(10, new Id(1, 1));
        Node node22 = new Node(20, new Id(1, 1));
        Node node33 = new Node(30, new Id(1, 1));
        Node node44 = new Node(40, new Id(1, 1));

        Tree tree2 = new Tree(node1.getData());
        tree2.addNode(node22, new Id(1, 1));
        tree2.addNode(node33, new Id(1, 1));
        tree2.addNode(node44, new Id(2, 1));

// tree.addTree(tree2,new Id(3,1));

//tree.addTree(tree2,new Id(2,2));
        System.out.println(tree.getNodeMap());

        // WriteRead writeRead = new WriteRead();
        //writeRead.outSerialize(tree);

/*Tree tree1;
WriteRead writeRead = new WriteRead();
tree1 = writeRead.inSerialize();
System.out.println(tree1.getNodeMap());*/


