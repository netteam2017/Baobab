package Project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;


/**
 * Created by user on 15.02.2018.
 */

public class CompositeTaskDTO implements Serializable {
    public Task task;
    public Map<Id, Task> subTasks;

    public Map<TaskDTO,List<TaskDTO>> tasks;

    @JsonCreator
    public CompositeTaskDTO() {
    }


    public CompositeTaskDTO( TaskTree taskTree, String taskTreeName) {
        Collection<Id> taskList = taskTree.getNodeMap().keySet();
        tasks = new HashMap<>();
        Id parentId = null;

        for (Id id: taskList){
            LinkedList<TaskDTO> taskDTOs = new LinkedList<>();
            try {
                if (id.height>1){parentId = taskTree.getHierarchy().getParent(taskTree.getTask(id)).getId();}
                Set<Task> taskCh = taskTree.getHierarchy().getChildren(taskTree.getTask(id));
                for (Task t: taskCh){
                    if (t!=null){
                        taskDTOs.add(new TaskDTO(t,id.toString(),taskTreeName));}
                }
                TaskDTO td;
                if (id.height>1){
                    // System.out.print(parentId);
                    td = new TaskDTO(taskTree.getTask(id),parentId.toString(),taskTreeName);}
                else{
                    td = new TaskDTO(taskTree.getTask(id),null,taskTreeName);
                }
                tasks.put(td,taskDTOs);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //System.out.print(tasks.keySet());
    }


}
   /* public  Set<Task>getSubTasks()
    {
        return subTasks;
    }
}
/*
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class CompositeTaskDTO implements Serializable {


    Task task;
    Set<Task> subTasks;

    public CompositeTaskDTO() {
    }

    @JsonCreator
    public CompositeTaskDTO(Task task,Set<Task> subTasks) {
        @
        this.task=task;
        this.subTasks = subTasks;
    }
    @JsonProperty
    public Task getTask(){
        return this.task;
    }
    @JsonProperty
    public void setTask(Task task){
        this.task=task;
    }
    @JsonProperty
    public Set<Task> getSubTasks(){
        return this.subTasks;
    }
    @JsonProperty
    public void setSubTasks(Set<Task> subTasks){
        this.subTasks=subTasks;
    }


    @Override
    public String toString() {
        return "CompositeTaskDTO{" +
                "task=" + task +
                ", subTasks=" + subTasks +
                '}';
    }
}
*/