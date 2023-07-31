import javax.swing.*;
import javax.swing.undo.UndoManager;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextEditor extends JFrame {
    private JTextPane textPane;
    private JFileChooser fileChooser;
    private File currentFile;
    private UndoManager undoManager;
    private int fontSize = 12; // Default font size
    private boolean isUnderlined = false;

    public TextEditor() {
        setTitle("Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textPane = new JTextPane();
        textPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontSize)); // Set default font size
        JScrollPane scrollPane = new JScrollPane(textPane);
        add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFile();
            }
        });

        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        JMenuItem undoMenuItem = new JMenuItem("Undo");
        JMenuItem redoMenuItem = new JMenuItem("Redo");

        undoManager = new UndoManager();
        textPane.getDocument().addUndoableEditListener(undoManager);

        copyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPane.copy();
            }
        });

        pasteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPane.paste();
            }
        });

        undoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
            }
        });

        redoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canRedo()) {
                    undoManager.redo();
                }
            }
        });

        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        editMenu.addSeparator();
        editMenu.add(undoMenuItem);
        editMenu.add(redoMenuItem);
        menuBar.add(editMenu);

        JMenu formatMenu = new JMenu("Format");
        JMenuItem boldMenuItem = new JMenuItem("Bold");
        JMenuItem italicMenuItem = new JMenuItem("Italic");
        JMenuItem underlineMenuItem = new JMenuItem("Underline");
        JMenuItem plainMenuItem = new JMenuItem("Plain");
        JMenuItem increaseFontSizeMenuItem = new JMenuItem("Font Size +");
        JMenuItem decreaseFontSizeMenuItem = new JMenuItem("Font Size -");

        boldMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFormattingToSelectedText(Font.BOLD);
            }
        });

        italicMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFormattingToSelectedText(Font.ITALIC);
            }
        });

        underlineMenuItem.addActionListener(new ActionListener() { // ActionListener for underline menu item
        @Override
        public void actionPerformed(ActionEvent e) {
            toggleUnderline();
        }
    });

        plainMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFormattingToSelectedText(Font.PLAIN);
            }
        });

        increaseFontSizeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                increaseFontSize();
            }
        });

        decreaseFontSizeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decreaseFontSize();
            }
        });

        formatMenu.add(plainMenuItem);
        formatMenu.add(boldMenuItem);
        formatMenu.add(italicMenuItem);
        formatMenu.add(underlineMenuItem);
        formatMenu.addSeparator();
        formatMenu.add(increaseFontSizeMenuItem);
        formatMenu.add(decreaseFontSizeMenuItem);
        menuBar.add(formatMenu);

        setJMenuBar(menuBar);

        fileChooser = new JFileChooser();

        setVisible(true);
    }

    private void newFile() {
        textPane.setText("");
        currentFile = null;
    }

    private void openFile() {
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileReader reader = new FileReader(file);
                BufferedReader br = new BufferedReader(reader);
                textPane.read(br, null);
                br.close();
                currentFile = file;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        int returnVal;
        if (currentFile != null) {
            returnVal = fileChooser.showSaveDialog(this);
        } else {
            returnVal = fileChooser.showSaveDialog(this);
        }

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(writer);
                textPane.write(bw);
                bw.close();
                currentFile = file;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void applyFormattingToSelectedText(int style) {
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();
        StyledDocument doc = textPane.getStyledDocument();
        Style styleObj = textPane.addStyle("selected", null);
        StyleConstants.setBold(styleObj, style == Font.BOLD);
        StyleConstants.setItalic(styleObj, style == Font.ITALIC);
        StyleConstants.setFontSize(styleObj, fontSize); // Apply font size
        doc.setCharacterAttributes(start, end - start, styleObj, false);
    }

    private void increaseFontSize() {
        fontSize += 2; // Increase font size by 2
        updateTextPaneFont();
    }

    private void decreaseFontSize() {
        if (fontSize > 2) {
            fontSize -= 2; // Decrease font size by 2
            updateTextPaneFont();
        }
    }

    private void updateTextPaneFont() {
        Font currentFont = textPane.getFont();
        Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), fontSize);
        textPane.setFont(newFont);
    }


    private void toggleUnderline() {
        isUnderlined = !isUnderlined;
        updateTextUnderline();
    }

    private void updateTextUnderline() {
        if (isUnderlined) {
            // Add underline attribute to selected text
            int start = textPane.getSelectionStart();
            int end = textPane.getSelectionEnd();
            StyledDocument doc = textPane.getStyledDocument();
            StyleConstants.setUnderline(doc.addStyle("underline", null), true);
            doc.setCharacterAttributes(start, end - start, doc.getStyle("underline"), true);
        } else {
            // Remove underline attribute from selected text
            int start = textPane.getSelectionStart();
            int end = textPane.getSelectionEnd();
            StyledDocument doc = textPane.getStyledDocument();
            doc.setCharacterAttributes(start, end - start, doc.getStyle("selected"), true);
        }
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TextEditor();
            }
        });
    }
}
