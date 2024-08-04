import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import static java.nio.file.StandardOpenOption.CREATE;

public class Main {

    static ArrayList<String> MyArray = new ArrayList<>();
    static Scanner in = new Scanner(System.in);
    static String fileName = "";
    static boolean needsToBeSaved = false;
    static File workingDirectory = new File(System.getProperty("user.dir"));
    static Path file;

    public static void main(String[] args) {
        String choice = "";
        boolean done = false;

        do {
            //display the menu
            displayMenu();
            //get the choice
            choice = getUserChoice().toUpperCase();
            //execute the choice
            userChoiceExecution(choice);
        } while (!done);
    }
    private static void userChoiceExecution(String userChoice) {
        switch (userChoice) {
            case"A":
                addToList();
                break;
            case"D":
                deleteItemFromList();
                break;
            case"I":
                insertItemIntoList();
                break;
            case"M":
                moveItem(); // Move an item
                break;
            case"O":
                openListFile(); // Open a list file from disk
                break;
            case"S":
                saveListFile(); // Save the current list file to disk
                break;
            case"C":
                clearList(); // Clear removes all the elements from the current list
                break;
            case"V":
                viewList();
                break;
            case"Q":
                quitApplication();
                break;
            default:
                System.out.println("Invalid Choice");
                break;
        }
    }

    private static void viewList() {
        for (int i = 0; i < MyArray.size(); i++) {
            System.out.println( "Item" + (i+1) + ":" + MyArray.get(i));
        }
    }

    private static void insertItemIntoList() {
        viewList();
        int insertIndex = SafeInput.getRangedInt(in,"Enter a location where you want to insert an item: ", 1, MyArray.size() + 1) - 1;
        String insertItem = SafeInput.getNonZeroLenString(in,"Enter the name of the item you want to insert into the list: ");
        MyArray.add(insertIndex, insertItem);
        needsToBeSaved = true;
        viewList();
    }

    private static void deleteItemFromList() {
        viewList();
        String itemToDelete = SafeInput.getNonZeroLenString(in, "Enter the item name you want to delete from the list: ");
        if (MyArray.contains(itemToDelete)) {
            MyArray.remove(itemToDelete);
            needsToBeSaved = true;
        }
        else {
            System.out.println("The item you entered is not found in the list");
        }
        viewList();
    }

    private static void addToList() {
        String item = SafeInput.getNonZeroLenString(in,"Please enter an item you want to add to the list: ");
        MyArray.add(item);
        needsToBeSaved = true;
        viewList();
    }

    private static void moveItem() {
        viewList();

        int originalIndex = SafeInput.getRangedInt(in,"Enter the location of the item you want to move: ", 1, MyArray.size()) - 1;
        int newIndex = SafeInput.getRangedInt(in,"Enter the new location where you want to move the item: ", 1, MyArray.size()) - 1;

        String itemToMove = MyArray.get(originalIndex);
        MyArray.remove(originalIndex);
        MyArray.add(newIndex, itemToMove);
        needsToBeSaved = true;

        viewList();
    }

    private static void openListFile() {
        if (needsToBeSaved) {
            String prompt;
            if (fileName.isEmpty()) {
                prompt = "You have unsaved changes in the list. Would you like to save them?[y/n]";
            } else {
                prompt = "You have opened file " + fileName + ", and you have unsaved changes in the list. Would you like to save them?[y/n]";
            }
            if (SafeInput.getYNConfirm(in, prompt)) {
                saveListFile();
            }
        }
        clearList();
        needsToBeSaved = false;
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String listItem = "";

        try {
            chooser.setCurrentDirectory(workingDirectory);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                file = selectedFile.toPath();
                fileName = selectedFile.getName();
                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                while(reader.ready()) {
                    listItem = reader.readLine();
                    MyArray.add(listItem);
                }
                reader.close(); // must close the file to seal it and flush buffer
                System.out.println("List file read!");
                viewList();
            }
            else {
                System.out.println("Failed to choose a file to process");
                System.out.println("Run the program again!");
                System.exit(0);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveListFile() {
        if (fileName.isEmpty()) {
            boolean isFileAlreadyExist = false;
            do {
                fileName = SafeInput.getNonZeroLenString(in,"Please enter the file name: ").trim() + ".txt";
                file = Paths.get(workingDirectory.getPath(), "src", fileName);
                isFileAlreadyExist = Files.exists(file);
                if (isFileAlreadyExist) {
                    System.out.println("File already exists. Please enter a different file name.");
                }
            } while (isFileAlreadyExist);
        }
        else {
            file = Paths.get(workingDirectory.getPath(), "src", fileName);
        }

        try {
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            for(String listItem : MyArray) {
                writer.write(listItem, 0, listItem.length());
                writer.newLine();  // adds the new line
            }
            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("Data file written!");
            needsToBeSaved = false;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void clearList() {
        MyArray.clear();
        needsToBeSaved = true;
    }

    private static void quitApplication() {
        if (needsToBeSaved && SafeInput.getYNConfirm(in, "You have unsaved changes in the list. Would you like to save them?[y/n]")) {
            saveListFile();
        }
        System.exit(0);
    }

    private static void displayMenu() {
        System.out.println("********************************************************************");
        System.out.println("\t\t\t\t\t\t\tMenu\n\n" +
                "\t\t\t\tA – Add an item to the list\n" +
                "\t\t\t\tD – Delete an item from the list\n" +
                "\t\t\t\tI – Insert an item into the list\n" +
                "\t\t\t\tM – Move an item in the list\n" +
                "\t\t\t\tO – Open a list from disk\n" +
                "\t\t\t\tS – Save the current list file to disk\n" +
                "\t\t\t\tC – Clear removes all the elements from the current list\n" +
                "\t\t\t\tV – View the list\n" +
                "\t\t\t\tQ – Quit");
        System.out.println("********************************************************************");
    }

    private static String getUserChoice() {
        return SafeInput.getNonZeroLenString(in,"Please enter your choice: ");
    }
}