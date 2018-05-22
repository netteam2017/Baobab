package Project;

import java.io.*;

import java.io.*;

public class WriteRead {

    public void outSerialize(TaskTree taskTree) {
        try (FileOutputStream fos = new FileOutputStream("Tree.txt");
             ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            oos.writeObject(taskTree);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            System.out.println("Исключение: " + e);
            throw new RuntimeException(e);
        }
    }

    public TaskTree inSerialize() {
        try (FileInputStream fis = new FileInputStream("Tree.txt");
             ObjectInputStream oin = new ObjectInputStream(fis)) {
            return new TaskTree((TaskTree) oin.readObject());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("load tree faild", e);

        } catch (IOException i) {
            throw new RuntimeException("load tree faild", i);

        } catch (ClassNotFoundException c) {
            throw new RuntimeException("load tree faild", c);
        }
    }
}