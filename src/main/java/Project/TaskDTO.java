package Project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

public class TaskDTO implements Serializable {
    public Task task;
    public String parentId;
    public String taskTreeName;

    @JsonCreator
    TaskDTO(){}
    TaskDTO(@JsonProperty("task") Task task, @JsonProperty("parentId") String parentId, @JsonProperty("taskTreeName") String taskTreeName) throws IOException{
        this.task = task;
        this.parentId = parentId;
        this.taskTreeName = taskTreeName;
    }

    @Override
    public String toString() {
        return
                "{\"task\":" + task +
                        ", \"parentId\":" + parentId +

                        '}';
    }
    /* public void setName(String name) {
        this.name = name;
    }

    public void setSpentTime(long spentTime) {
        this.spentTime = spentTime;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public void setStatus(Status status) {
        this.status = status;
    }*/
}
