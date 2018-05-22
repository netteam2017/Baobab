import Project.Id;
import Project.Task;
import Project.TaskTree;
import org.junit.Test;

import sun.reflect.generics.tree.Tree;

import static org.testng.Assert.*;

public class TaskTreeTest {

    private String Vova;
    private String Kukusiki;

    private String first;
    private String second;

    private String tree;

    private Id id = new Id(1,1);
    private Id id2 = new Id(2,1);
    private Task parent;

    @Test
    public void testAddTask() throws Exception {
        Task mainTask = new Task(Vova, first, id);
        TaskTree taskTree = new TaskTree(Vova, first);
        taskTree.addTask(Vova, second, mainTask);
        taskTree.getHierarchy().getChildren(mainTask);
        //Hamcrest
        //
        System.out.println();
        boolean founded = false;
        for (Task task :
                taskTree.getHierarchy().getChildren(mainTask)) {
            if (task.getName().equals(second) && task.getExecutor().equals(Vova)){
                founded = true;
                break;
            }
        }
        assertTrue(founded);
    }

    @Test
    public void testUpdateTask() throws Exception {
        TaskTree taskTree = new TaskTree(Vova, first);
        String newName = "lala";
        taskTree.updateTask(id, newName);
        assertEquals(taskTree.getHead().getName(), newName);
    }

    @Test
    public void testDeleteTask() throws Exception {
        TaskTree taskTree  = new TaskTree(Vova, first);
        taskTree.addTask(Vova, second, parent);
        taskTree.deleteTask(id);
        boolean empty = taskTree.getHierarchy().getChildren(taskTree.getHead()).isEmpty();
        assertTrue(empty);
    }

    @Test
    public void testGetTask() throws Exception {
        Task mainTask = new Task(Vova, first, id);
        Task task = new Task(Vova, second, id2);
        TaskTree taskTree = new TaskTree(Vova, first);
        taskTree.addTask(Vova, second, mainTask);

        if (taskTree.getTask(id2).getName() == task.getName()){
            System.out.println("Уии");
        }else System.out.println("oh...");

    }
}