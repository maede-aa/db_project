import db.Database;
import todo.CommandProcessor;
import todo.entity.*;
import todo.serializer.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database.registerSerializer(Task.TASK_ENTITY_CODE, new TaskSerializer());
        Database.registerSerializer(Step.STEP_ENTITY_CODE, new StepSerializer());

        Database.load();
        CommandProcessor processor = new CommandProcessor(new Scanner(System.in));
        processor.start();
    }
}