package Project;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.crypto.URIDereferencer;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Root resource (exposed at "myresource" path)
 */

@Path("/myresource")

@Singleton
public class MyResource {

    static TaskTree taskTree = new TaskTree("headTask", "Kate");

    static  {
        Task task2 = taskTree.addTask("subTask", "executor2", taskTree.getHead());
        taskTree.addTask("subsubTask", "3", task2);
        taskTree.addTask("Vova", "Task2", taskTree.getHead());
        taskTree.addTask("Dima", "Task3", taskTree.getHead());
        taskTree.addTask("Ivan", "Task4", taskTree.getHead());
        taskTree.addTask("Ivan", "Task5", taskTree.getTaskOnName("Task4"));
        taskTree.addTask("Vera", "Task6", taskTree.getTaskOnName("Task5"));
        System.out.println(taskTree.getHierarchy());
    }

    @GET
    @Path("/task/{taskName}")
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.APPLICATION_JSON)
    public CompositeTaskDTO getTaskTree(@PathParam("taskName") String name) {
        System.out.print(name);


        //Task task = taskTree.getHead();
        // System.out.print(taskTree.getNodeMap());
        CompositeTaskDTO compositeTaskDTO = new CompositeTaskDTO(taskTree,"create tasktreenamemethod"); //(task, taskTree.getNodeMap());

        return compositeTaskDTO;
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateTask(TaskDTO taskDTO) {
        ObjectMapper om = new ObjectMapper();
        // Id id = om.readValues(taskDTO.)
        System.out.println(taskDTO.task.getId());
        System.out.println("!!!!!!!!!!!!!!!!"+taskTree.getTask(taskDTO.task.getId()).name);
        taskTree.updateTask(taskDTO.task.getId(), taskDTO.task.name);
        System.out.println("!!!!!!!!!!!!!!!!"+taskTree.getTask(taskDTO.task.getId()).name);
    }
    //{height: 56, id: 75}
/*    private Id parseId(String taskId){
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
    */

    // проиденскировать айди(через мэп) проверить ниже.
    @DELETE
    @Path("/delete/{taskId}/{taskName}") //два параметра
    public void deleteTask(@PathParam("taskId") String taskId,@PathParam("taskName") String taskName) throws IOException{
        ObjectMapper om = new ObjectMapper();

        System.out.println("This name" + taskId+taskName);
        Id id = om.readValue(URLDecoder.decode(taskId),Id.class);
        taskTree.deleteTask(id);
        System.out.println(taskTree.getHierarchy());
    }


    @POST
    @Path("/create") //сделать так чтобы возвращал строку.
    @Consumes(MediaType.APPLICATION_JSON)
    public void createTask(TaskDTO taskDTO) throws IOException {
        ObjectMapper om = new ObjectMapper();
        Id id = om.readValue(taskDTO.parentId.toString(),Id.class);
        taskTree.addTask(taskDTO.task.getExecutor(), taskDTO.task.getName(), taskTree.getTask(id));
    }
}
