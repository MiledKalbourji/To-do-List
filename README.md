# Front-end To-Do List Application

This is a simple front-end to-do list application implemented in Java. It allows users to manage their tasks by adding, deleting, completing, and displaying them. The application does not store tasks into a file; it only maintains them in memory during runtime.

## Features

- **Add Task**: Users can add a new task to the list by providing the task name, due date, and priority.
- **Delete Task**: Users can delete a task from the list by specifying its index.
- **Toggle Bullet List**: Users can toggle the display format between using bullet points and numbering for task lists.
- **Complete Task**: Users can mark a task as completed by its index.
- **Display Tasks**: Users can view all tasks currently in the list.

## How to Use

1. **Running the Application**:
   - Ensure you have Java installed on your system.
   - Compile the `Main.java` file: `javac Main.java`.
   - Run the compiled program: `java Main`.

2. **Menu Options**:
   - Upon running the program, a menu will be displayed with options to perform various tasks.
   - Choose an option by entering the corresponding number and pressing Enter.

3. **Adding a Task**:
   - Choose option `1` from the menu.
   - Enter the task name, due date, and priority as prompted.

4. **Deleting a Task**:
   - Choose option `2` from the menu.
   - Enter the index (line number) of the task you want to delete. The index refers to the line number of the task as displayed in the list.

5. **Toggling Bullet List**:
   - Choose option `3` from the menu.
   - The bullet list setting will be toggled between enabled and disabled.

6. **Completing a Task**:
   - Choose option `4` from the menu.
   - Enter the index (line number) of the task you want to mark as completed. The index refers to the line number of the task as displayed in the list.

7. **Displaying Tasks**:
   - Choose option `5` from the menu.
   - All tasks in the list will be displayed.

8. **Exiting the Application**:
   - Choose option `6` from the menu to exit the application.

## Notes

- This application is a front-end implementation, meaning it operates solely in memory and does not save tasks to a file or database.
- It provides a simple way to manage tasks interactively through the command line interface.
