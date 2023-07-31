# Text Editor

The Text Editor project is a simple Java application that provides a user interface for editing and manipulating text. It allows users to create new files, open existing files, and save files to the local filesystem. The editor supports basic text formatting options such as bold, italic, and underline. Users can also increase or decrease the font size of the selected text. The application incorporates undo and redo functionality using the UndoManager class. It provides a user-friendly menu bar with File, Edit, and Format menus for easy access to various actions. Overall, the Text Editor project serves as a lightweight and versatile tool for editing and formatting text documents.

<br>


<p align="center" >
  <img align="center" src="ss/texteditor.png?raw=true" alt="TextEditor" width="100%">  
</p>



## Features

- **File Menu**
  - New: Create a new empty file.
  - Open: Open an existing text file.
  - Save: Save the current file.
  - Exit: Close the application.

- **Edit Menu**
  - Copy: Copy the selected text.
  - Paste: Paste the clipboard content.
  - Undo: Undo the previous edit.
  - Redo: Redo the previously undone edit.

- **Format Menu**
  - Plain: Remove any formatting (bold or italic) from the selected text.
  -  Bold: Apply bold formatting to the selected text.
  - Italic: Apply italic formatting to the selected text.
  - Underline: Apply underline formatting to the selected text.
  - Font Size + can be used to increase text editor font.
  - Font Size - can be used to decrease text editor font.

## Getting Started

1. Ensure you have Java Development Kit (JDK) installed on your system.

2. Clone the Repository

```shell
git clone https://github.com/nisha5155/Text-Editor.git
cd Text-Editor
```

3. Compile the source code using the following command:
   
```shell
javac TextEditor.java
```

4. Run the application using the following command:

```shell
java TextEditor
```


## Dependencies

This project requires the following dependencies:

- Java Swing: Used for building the graphical user interface.
- Java Undo Manager: Used for managing undo and redo functionality.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please feel free to open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

