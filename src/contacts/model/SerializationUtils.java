package contacts.model;

import java.io.*;


/**
 * Util class provides possibility to store phone book to users drive.
 */
public class SerializationUtils {

    /**
     * Stores phone book to drive.
     *
     * @param contactsBook Contacts object to store.
     * @param fileName     String representation of path to file for storing phone book.
     * @throws IOException Thrown when failed to store phone book.
     */
    public static void serialize(Contacts contactsBook, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(contactsBook);
        oos.close();
    }

    /**
     * Restores phone book from serialized form.
     *
     * @param fileName String representation of path to file, from which phone book should be restored.
     * @return Phone book restored from file.
     * @throws IOException            Thrown when failed to read the file.
     * @throws ClassNotFoundException Thrown when failed to deserialize phone book from file.
     */
    public static Contacts deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Contacts obj = (Contacts) ois.readObject();
        ois.close();
        return obj;
    }
}
