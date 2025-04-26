import db.exception.InvalidEntityException;
import todo.CommandProcessor;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InvalidEntityException {
        CommandProcessor commandProcessor = new CommandProcessor(new Scanner(System.in));
        commandProcessor.start();
}}