package contacts.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Provides storage for contact records
 */
public class Contacts implements Serializable {
    private static final long serialVersionUID = 1L;

    List<ContactsRecord> contactsList = new ArrayList<>();

    /**
     * Searches query in each field of every record in phone book and returns list of records or empty list
     * if there's no records suitable for query. <br>
     * Search is case insensitive.
     * Query can contain regular expression or text.
     *
     * @param query Query to search.
     * @return List of records corresponding query ordered the same way as in the phone book.
     */
    public List<ContactsRecord> search(String query) {
        Pattern pattern = Pattern.compile(query.toLowerCase());
        return contactsList.stream()
                .filter(record -> {
                    Matcher matcher = pattern.matcher(record.toSearchableString());
                    return matcher.find();
                })
                .collect(Collectors.toList());
    }

    public void addRecord(ContactsRecord contactsRecord) {
        contactsList.add(contactsRecord);
    }

    public void deleteRecord(ContactsRecord contactsRecord) {
        contactsList.remove(contactsRecord);
    }

    public int getNumberOfRecords() {
        return contactsList.size();
    }

    /**
     * Provides access to record by it's ID, which is index of record in list.
     *
     * @param ID ID of record. Should be greater than or equals to 0 and less than number or records in phone book.
     * @return Record corresponding to provided ID.
     */
    public ContactsRecord getRecordByID(int ID) {
        return contactsList.get(ID);
    }
}