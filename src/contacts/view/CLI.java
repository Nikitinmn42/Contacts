package contacts.view;

import contacts.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;


/**
 * Provides user interface to interact with phone book.
 * It is pack of menus to manage data and display it.
 */
public class CLI {

    private final String WRONG_INPUT = "Wrong input";
    private final Scanner scanner = new Scanner(System.in);
    private final Contacts contactsBook;
    private final String filename;

    /**
     * Constructs command line interface.
     *
     * @param contactsBook Phone book with stored contacts.
     * @param filename     Path to serialized form of phone book.
     */
    public CLI(Contacts contactsBook, String filename) {
        this.contactsBook = contactsBook;
        this.filename = filename;
    }

    /**
     * Displays menu to choose actions for user.
     * Accepts input from command line and redirects to corresponding submenu.
     */
    public void mainMenu() {
        while (true) {
            System.out.print("\n[menu] Enter action (add, list, search, count, exit): ");
            String input = scanner.nextLine();
            switch (input) {
                case "add":
                    addMenu();
                    break;
                case "list":
                    listMenu();
                    break;
                case "search":
                    searchMenu();
                    break;
                case "count":
                    System.out.printf("The Phone Book has %d records.\n", contactsBook.getNumberOfRecords());
                    break;
                case "exit":
                    if (filename != null && !filename.isBlank()) {
                        try {
                            SerializationUtils.serialize(contactsBook, filename);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.exit(0);
                default:
                    System.out.println(WRONG_INPUT);
            }
        }
    }

    /**
     * Menu accepts search query from command line and displays search result for user.
     * Query is case insensitive, also can be regular expression.
     * Provides access to record menu.
     */
    private void searchMenu() {
        while (true) {
            System.out.print("Enter search query: ");
            String query = scanner.nextLine();
            List<ContactsRecord> foundRecordsList = contactsBook.search(query);
            int size = foundRecordsList.size();
            System.out.printf("Found %d results:\n", size);
            for (int i = 0; i < size; i++) {

                ContactsRecord contactsRecord = foundRecordsList.get(i);
                String fullName = contactsRecord.getName();
                if (contactsRecord.getAccessibleFields().contains("surname")) {
                    fullName += " " + ((PersonContactsRecord) contactsRecord).getSurname();
                }
                System.out.println(i + 1 + ". " + fullName);
            }

            System.out.print("\n[search] Enter action ([number], back, again): ");
            String action = scanner.nextLine();
            if ("back".equals(action)) {
                return;
            }
            if ("again".equals(action)) {
                continue;
            }
            if (actionIDToRecordMenu(action)) {
                return;
            }
        }
    }

    /**
     * Shows all records stored in phone book and provides access to record menu.
     */
    private void listMenu() {
        int bound = contactsBook.getNumberOfRecords();
        while (true) {
            for (int i = 0; i < bound; i++) {
                ContactsRecord contactsRecord = contactsBook.getRecordByID(i);
                String fullName = contactsRecord.getName();
                if (contactsRecord.getAccessibleFields().contains("surname")) {
                    fullName += " " + ((PersonContactsRecord) contactsRecord).getSurname();
                }
                System.out.println(i + 1 + ". " + fullName);
            }
            System.out.print("\n[list] Enter action ([number], back): ");
            String action = scanner.nextLine();
            if ("back".equals(action)) {
                return;
            }
            if (actionIDToRecordMenu(action)) {
                return;
            }
        }
    }

    /**
     * Validates the "action" input from user, parses ID if it's correct and redirect to the record menu.
     *
     * @param action User input should be number greater than 0.
     * @return True if record found by provided action, false otherwise.
     */
    private boolean actionIDToRecordMenu(String action) {
        int ID = -1;
        if (action.matches("([1-9]\\d*)")) {
            ID = Integer.parseInt(action) - 1;
        }
        if (ID >= 0 && ID < contactsBook.getNumberOfRecords()) {
            recordMenu(contactsBook.getRecordByID(ID));
            return true;
        } else {
            System.out.println(WRONG_INPUT);
        }
        return false;
    }


    /**
     * Displays information from record.
     * Accepts user input to redirect to edit menu or delete record from phone book.
     *
     * @param contactsRecord Record, with which user interacts.
     */
    private void recordMenu(ContactsRecord contactsRecord) {
        List<String> accessibleFields = contactsRecord.getAccessibleFields();
        while (true) {
            accessibleFields.forEach(fieldName -> {
                try {
                    System.out.println(contactsRecord.getFieldByName(fieldName));
                } catch (WrongFieldNameException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("Time created: " + contactsRecord.getTimeCreated());
            System.out.println("Time last edit: " + contactsRecord.getTimeEdited());

            System.out.print("\n[record] Enter action (edit, delete, menu): ");
            String action = scanner.nextLine();
            switch (action) {
                case "edit":
                    editMenu(contactsRecord, accessibleFields);
                    continue;
                case "delete":
                    contactsBook.deleteRecord(contactsRecord);
                    System.out.println("Record deleted");
                    return;
                case "menu":
                    return;
                default:
                    System.out.println(WRONG_INPUT);
            }
        }
    }

    /**
     * Provides access to editing already stored records from phone book.
     * If field successfully edited updates time of last editing in record.
     *
     * @param contactsRecord   Record to edit.
     * @param accessibleFields List of fields for concrete record which can be edited by user.
     */
    private void editMenu(ContactsRecord contactsRecord, List<String> accessibleFields) {
        String fieldsString = String.join(", ", accessibleFields);
        System.out.printf("Select a field (%s): ", fieldsString);
        String fieldName = scanner.nextLine();
        System.out.printf("Enter %s: ", fieldName);
        String newValue = scanner.nextLine();
        try {
            contactsRecord.editFieldByName(fieldName, newValue);
            contactsRecord.updateTimeEdited();
            System.out.println("Saved");
        } catch (WrongFieldNameException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Menu for choosing type of record user wants to create.
     * Redirects to corresponding menu.
     */
    private void addMenu() {
        while (true) {
            System.out.print("Enter the type (person, organization): ");
            String type = scanner.nextLine();
            switch (type) {
                case "person":
                    createPerson();
                    return;
                case "organization":
                    createOrganization();
                    return;
                default:
                    System.out.println(WRONG_INPUT);
            }
        }
    }

    /**
     * Creates organization record from user input and stores it to phone book.
     */
    private void createOrganization() {
        OrganizationContactsRecord record = new OrganizationContactsRecord();
        System.out.print("Enter the organization name: ");
        String name = scanner.nextLine();
        record.setName(name);
        System.out.print("Enter the address: ");
        String address = scanner.nextLine();
        record.setAddress(address);
        System.out.print("Enter the number: ");
        String number = scanner.nextLine();
        record.setNumber(number);
        contactsBook.addRecord(record);
        System.out.println("The record added.\n");
    }

    /**
     * Creates person record from user input and stores it to phone book.
     */
    private void createPerson() {
        PersonContactsRecord record = new PersonContactsRecord();
        System.out.print("Enter the name: ");
        String name = scanner.nextLine();
        record.setName(name);
        System.out.print("Enter the surname: ");
        String surname = scanner.nextLine();
        record.setSurname(surname);
        System.out.print("Enter the birth date: ");
        String birth = scanner.nextLine();
        record.setBirthDate(birth);
        System.out.print("Enter the gender (M, F): ");
        String gender = scanner.nextLine();
        record.setGender(gender);
        System.out.print("Enter the number: ");
        String number = scanner.nextLine();
        record.setNumber(number);
        contactsBook.addRecord(record);
        System.out.println("The record added.\n");
    }
}
