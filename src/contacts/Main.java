package contacts;

import contacts.model.Contacts;
import contacts.model.SerializationUtils;
import contacts.view.CLI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Entry point of application.
 */
public class Main {

    /**
     * Creates phone book from file, which path passed as command line argument.
     * If no arguments passed creates empty phone book.
     * After this step starts CLI.
     *
     * @param args Command line arguments. Accepts 1 argument - path to serialized phone book.
     */
    public static void main(String[] args) {
        String filename = null;
        Contacts contactsList = null;
        Path path;
        if (args.length > 0) {
            filename = args[0];
            path = Path.of(filename);
            System.out.println("open " + filename);
            try {
                if (Files.exists(path)) {
                    contactsList = SerializationUtils.deserialize(filename);
                } else {
                    Files.createFile(path);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (contactsList == null) {
            contactsList = new Contacts();
        }
        CLI cli = new CLI(contactsList, filename);
        cli.mainMenu();
    }
}
