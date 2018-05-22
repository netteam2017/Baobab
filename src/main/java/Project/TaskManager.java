package Project;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeType;

import java.util.Map;
import java.util.Set;

public class TaskManager extends Tree {

    public TaskManager(Node head) {
        super(head);
    }

    public Task addTask(String executor, String name, Id parentId) { //WARNING!!! I changed this method
        Id id = getNewNodeTypeId(new Node(parentId));
        Task task = new Task(executor, name, id);
        addNodeType(task, parentId);
        return task;
    }

    public Task getTaskOnName(String name){
        Task task = null;
        Set<Map.Entry<Id, Task>> entrySet = getNodeMap().entrySet();
        for (Map.Entry<Id, Task> pair : entrySet){
            if (pair.getValue().getName()==name)
                task=pair.getValue();
        }
        return task;
    }
    public void updateTask(Id id, String newName) {
        Task task = getTask(id);
        task.name = newName;
    }

    public void deleteTask(Id id) {
        Node node = getNodeType(id);
        deleteNodeType(node);
    }

    public Task getTask(Id id) {
        Node node = getNodeType(id);
        return (Task) node;
    }


    @Override
    Node createNode(Node oldNode, Id newId) {
        return null;
    }

    @Override
    Tree createTree(Node head) {
        return null;
    }
}
