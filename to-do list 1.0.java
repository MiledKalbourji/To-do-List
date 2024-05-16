import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable; 

// Class representing a task
class Task implements Serializable {
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

    // Method to convert task to a string
    @Override
    public String toString() {
        return name + "," + dueDate + "," + priority + "," + completed;
    }

    // Static method to create a task from a string
    public static Task fromString(String str) {
        String[] parts = str.split(",");
        Task task = new Task(parts[0], parts[1], Integer.parseInt(parts[2]));
        task.completed = Boolean.parseBoolean(parts[3]);
        return task;
    }
}

// Class representing a to-do list
class ToDoList implements Serializable {
    private ArrayList<Task> tasks = new ArrayList<>(); // List of tasks

    // Method to get the tasks list
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    // Method to add a task to the list
    void addTask(String name, String dueDate, int priority) {
        Task newTask = new Task(name, dueDate, priority);
        tasks.add(newTask);
    }

    // Method to delete a task from the list
    void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }

    // Method to mark a task as completed by its index
    void completeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.markCompleted();
        }
    }

    // Method to mark a task as incomplete by its index
    void incompleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.markIncomplete();
        }
    }

    // Method to save tasks to a file in plain text format
    void saveTasksToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Task task : tasks) {
                writer.write(task.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load tasks from a file in plain text format
    void loadTasksFromFile(String filename) {
        tasks.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = Task.fromString(line);
                tasks.add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Main class
public class ToDoListApp {
    private JFrame frame;              // Main frame
    private DefaultListModel<Task> listModel; // List model for tasks
    private JList<Task> taskList;      // Task list component
    private ToDoList todo;             // ToDoList instance
    private boolean useBulletList = true;  // Flag to toggle bullet list display

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ToDoListApp window = new ToDoListApp(); // Create application window
                window.frame.setVisible(true); // Display window
            } catch (Exception e) {
                e.printStackTrace(); // Print stack trace if an error occurs
            }
        });
    }

    // Constructor to initialize the application
    public ToDoListApp() {
        todo = new ToDoList(); // Initialize ToDoList
        initialize(); // Initialize UI
    }

    // Initialize the contents of the frame
    private void initialize() {
        frame = new JFrame(); // Create main frame
        frame.setBounds(100, 100, 450, 300); // Set frame bounds
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        frame.getContentPane().setLayout(new BorderLayout(0, 0)); // Set layout

        listModel = new DefaultListModel<>(); // Create list model for tasks
        taskList = new JList<>(listModel); // Create task list with list model
        taskList.setCellRenderer(new TaskCellRenderer()); // Set custom cell renderer
        frame.getContentPane().add(new JScrollPane(taskList), BorderLayout.CENTER); // Add task list to center of frame

        JPanel panel = new JPanel(); // Create panel for buttons
        frame.getContentPane().add(panel, BorderLayout.SOUTH); // Add panel to bottom of frame

        // Create buttons for various actions
        JButton btnAdd = new JButton("Add Task");
        panel.add(btnAdd);

        JButton btnDelete = new JButton("Delete Task");
        panel.add(btnDelete);

        JButton btnComplete = new JButton("Complete Task");
        panel.add(btnComplete);

        JButton btnSave = new JButton("Save Tasks");
        panel.add(btnSave);

        JButton btnLoad = new JButton("Load Tasks");
        panel.add(btnLoad);

        JButton btnToggleBullet = new JButton("Toggle Bullet List");
        panel.add(btnToggleBullet);

        // Add task button action
        btnAdd.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter task name:");
            String dueDate = JOptionPane.showInputDialog("Enter due date:");
            int priority = Integer.parseInt(JOptionPane.showInputDialog("Enter priority (1: Low, 2: Medium, 3: High):"));
            todo.addTask(name, dueDate, priority);
            updateTaskList();
        });

        // Delete task button action
        btnDelete.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                todo.deleteTask(index);
                updateTaskList();
            }
        });

        // Complete task button action
        btnComplete.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                todo.completeTask(index);
                updateTaskList();
            }
        });

        // Save tasks button action
        btnSave.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            int returnValue = fileChooser.showSaveDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                todo.saveTasksToFile(selectedFile.getAbsolutePath());
            }
        });

        // Load tasks button action
        btnLoad.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            int returnValue = fileChooser.showOpenDialog(frame);
             if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                todo.loadTasksFromFile(selectedFile.getAbsolutePath());
                updateTaskList();
            }
        });

        // Toggle bullet list button action
        btnToggleBullet.addActionListener(e -> {
            useBulletList = !useBulletList;
            updateTaskList();
        });
    }

    // Update the task list in the UI
    private void updateTaskList() {
        listModel.clear();
        for (Task task : todo.getTasks()) {
            listModel.addElement(task);
        }
    }

    // Custom cell renderer for the task list
    private class TaskCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Task) {
                Task task = (Task) value;
                String taskText;
                if (task.completed) {
                taskText = "<html><s>" + task.name + " (Due: " + task.dueDate + ", Priority: " + task.priority + ")</s></html>";
            } else {
                taskText = task.name + " (Due: " + task.dueDate + ", Priority: " + task.priority + ")";
            }
            if (useBulletList) {
                setText("\u2022 " + taskText); // Add bullet point
            } else {
                setText((index + 1) + ". " + taskText); // Use numbered list
            }
        }
            return c;
        }
    }
}
