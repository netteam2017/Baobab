package Project;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by user on 06.02.2018.
 */
public class TaskTree extends Tree<Task> {

    public TaskTree(String executor, String name) {
        super(new Task(executor, name, new Id(1,1)));
    }

    public TaskTree(Tree<Task> otherTree) {
        super(otherTree);
    }

    public Task addTask(String executor, String name, Task parent) {
        Id id = getNewNodeTypeId(parent);
        Task task = new Task(executor, name, id);
        addNodeType(task, getIdOfNodeType(parent));
        return task;
    }
    public Task getTaskOnName(String name){
        Task task = null;
        Set<Map.Entry<Id, Task>> entrySet = getNodeMap().entrySet();
        for (Map.Entry<Id, Task> pair : entrySet){
            if (Objects.equals(pair.getValue().getName(), name))
                task=pair.getValue();
        }
        return task;
    }
    public Id parseId(String taskId){
        int h = 0;
        int n = 0;
        String s_h = "";
        String s_n = "";
        int k = 0;
        for (int i=0; i < taskId.length(); i++) {
            char c = taskId.charAt(i);
            if (c < '0' || c > '9') {
                k=i;
                break;
            }
            s_h = s_h + c;
        }
        for (int i = k+1; i < taskId.length();i++){
            char c = taskId.charAt(i);
            if (c < '0' || c > '9') continue;
            s_n=s_n+c;
        }
        h = Integer.parseInt(s_h);
        n = Integer.parseInt(s_n);
        Id newTaskId = new Id(h,n);
        return newTaskId;
    }

    public void updateTask(Id id, String newName) {
        Task task = getTask(id);
        task.setName(newName);                                            /////////////////traves
    }

    public void deleteTask(Id id) {
        Task task = getNodeType(id);
        deleteNodeType(task);
    }

    public Task getTask(Id id) {
        Node node = getNodeType(id);
        return (Task) node;
    }


    @Override
    Task createNode(Task oldNode, Id newId) {
        return null;
    }

    @Override
    Tree<Task> createTree(Task head) {
        return null;
    }
}
