import java.util.ArrayList;
import java.util.Scanner;

// Class representing a task
class Task {
    String name;        // Task name
    String dueDate;     // Due date of the task
    int priority;       // Priority of the task
    boolean completed;  // Flag indicating if the task is completed or not

    // Task constructor to initialize task properties
    Task(String name, String dueDate, int priority) {
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = false; // Task is initially not completed
    }

    // Method to mark the task as completed
    void markCompleted() {
        completed = true;
    }

    // Method to mark the task as incomplete
    void markIncomplete() {
        completed = false;
    }
}

// Class representing a to-do list
class ToDoList {
    private ArrayList<Task> tasks = new ArrayList<>(); // List of tasks
    private boolean useBulletList = true;              // Flag indicating if bullet list is enabled

    // Method to get the size of the tasks list
    public int getTasksSize() {
        return tasks.size();
    }

    // Method to add a task to the list
    void addTask(String name, String dueDate, int priority) {
        Task newTask = new Task(name, dueDate, priority);
        tasks.add(newTask);
        System.out.println("Task added successfully.");
    }

    // Method to edit a task in the list
    void editTask(int index, String name, String dueDate, int priority) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.name = name;
            task.dueDate = dueDate;
            task.priority = priority;
            System.out.println("Task edited successfully.");
        } else {
            System.out.println("Invalid task index.");
        }
    }

    // Method to delete a task from the list
    void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("Invalid task index.");
        }
    }

    // Method to toggle the bullet list setting
    void toggleBulletList() {
        useBulletList = !useBulletList;
        System.out.println("Bullet list setting " + (useBulletList ? "enabled." : "disabled."));
    }

    // Method to mark a task as completed by its index
    void completeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.markCompleted();
            System.out.println("Task marked as completed.");
        } else {
            System.out.println("Invalid task index.");
        }
    }

    // Method to mark a task as incomplete by its index
    void incompleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.markIncomplete();
            System.out.println("Task marked as incomplete.");
        } else {
            System.out.println("Invalid task index.");
        }
    }

    // Method to display tasks
    void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks in the list.");
        } else {
            System.out.println("Tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                if (useBulletList) {
                    System.out.print("\u2022 "); // Bullet point
                } else {
                    System.out.print((i + 1) + ". ");
                }
                if (task.completed) {
                    System.out.print("\u001B[9m"); // Strikethrough effect start
                    System.out.print(task.name + " (Due: " + task.dueDate + ", Priority: " + task.priority + ")");
                    System.out.print("\u001B[0m"); // Reset formatting
                } else {
                    System.out.print(task.name + " (Due: " + task.dueDate + ", Priority: " + task.priority + ")");
                }
                System.out.println();
            }
        }
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        ToDoList todo = new ToDoList();            // Create a new ToDoList instance
        Scanner scanner = new Scanner(System.in); // Create a new Scanner instance for user input

        int choice;       // Variable to store user's choice from the menu
        String name;      // Variable to store task name
        String dueDate;   // Variable to store task due date
        int priority;     // Variable to store task priority
        int index;        // Variable to store task index

        // Main program loop
        do {
            System.out.println("\n1. Add Task\n2. Delete Task\n3. Toggle Bullet List\n4. Complete Task\n5. Display Tasks\n6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt(); // Read user's choice from the menu

            // Switch statement to handle user's choice
            switch (choice) {
                case 1:
                    System.out.print("Enter task name: ");
                    name = scanner.next(); // Read task name from user input
                    System.out.print("Enter due date: ");
                    dueDate = scanner.next(); // Read due date from user input
                    System.out.print("Enter priority (1: Low, 2: Medium, 3: High): ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        scanner.next(); // Consume the invalid input
                    }
                    priority = scanner.nextInt(); // Read priority from user input
                    todo.addTask(name, dueDate, priority); // Add task to the ToDoList
                    break;
                case 2:
                    // Delete task
                    boolean validIndex = false;
                    while (!validIndex) {
                        System.out.print("Enter task index to delete: ");
                        if (scanner.hasNextInt()) {
                            index = scanner.nextInt(); // Read task index from user input
                            if (index >= 1 && index <= todo.getTasksSize()) {
                                todo.deleteTask(index - 1); // Delete task from ToDoList
                                validIndex = true; // Exit the loop if a valid index is entered
                            } else {
                                System.out.println("Invalid task index. Please try again.");
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a valid integer.");
                            scanner.next(); // Consume the invalid input
                        }
                    }
                    break;
                case 3:
                    todo.toggleBulletList(); // Toggle bullet list setting
                    break;
                case 4:
                    System.out.print("Enter task index to mark as completed: ");
                    index = scanner.nextInt(); // Read task index from user input
                    todo.completeTask(index - 1); // Mark task as completed
                    break;
                case 5:
                    todo.displayTasks(); // Display tasks
                    break;
                case 6:
                    System.out.println("Exiting..."); // Exit the program
                    break;
                default:
                    System.out.println("Invalid choice. Please try again."); // Invalid choice message
            }
        } while (choice != 6); // Continue the loop until the user chooses to exit

        scanner.close(); // Close the scanner to release resources
    }
}