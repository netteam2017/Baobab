package Project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Task extends Node{
    public    String name;
    private   long spentTime;
    private  String executor;
    private  Status status;
   @JsonCreator
   public Task(){}
    public Task(@JsonProperty("executor") String executor,@JsonProperty("name") String name,@JsonProperty("id") Id id) {
        super(id);
        this.name = name;
        this.executor = executor;
        spentTime = 0;
        status = Status.NOT_STARTED;
    }

    public String getName() {
        return name;
    }

    public long getSpentTime() {
        return spentTime;
    }

    public String getExecutor() {
        return executor;
    }

    public Status getStatus() {
        return status;
    }

    public void setName(String name) {
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
    }
}