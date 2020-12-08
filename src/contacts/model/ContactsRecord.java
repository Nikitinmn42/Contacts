package contacts.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


/**
 * Abstract class represents record stored in phone book.
 */
public abstract class ContactsRecord implements Serializable {

    protected final LocalDateTime timeCreated;

    protected String name;
    protected String number;
    protected LocalDateTime timeEdited;
    protected List<String> accessibleFields;

    /**
     * Constructs new record with empty fields for further filling.
     * Fills only time of creating and last editing, which are the same for newly created record.
     */
    public ContactsRecord() {
        timeCreated = LocalDateTime.now();
        timeEdited = timeCreated;
    }

    /**
     * Edits field of record by it's name, replacing it with provided new value.
     * If field doesn't exist throws WrongFieldNameException.
     *
     * @param fieldName Name of the field to be edited.
     * @param newValue  Value, with which field be replaced if present.
     * @throws WrongFieldNameException Thrown when record doesn't have field with such name.
     */
    public abstract void editFieldByName(String fieldName, String newValue) throws WrongFieldNameException;

    /**
     * Provides access to value of the field by it's name
     *
     * @param fieldName Name of the field to be returned.
     * @return value of the field by it's name.
     * @throws WrongFieldNameException Thrown when record doesn't have field with such name.
     */
    public abstract String getFieldByName(String fieldName) throws WrongFieldNameException;

    /**
     * To support search through all fields concatenates their values in one string.
     *
     * @return One string representing all fields.
     */
    public abstract String toSearchableString();

    /**
     * Supporting access to fields of the record by name provides list of field names.
     *
     * @return List of field names, which can be edited
     */
    public List<String> getAccessibleFields() {
        return accessibleFields;
    }

    /**
     * Updates time when record was edited last time.
     * Should be called on each editing any field of the record.
     */
    public void updateTimeEdited() {
        timeEdited = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    /**
     * Sets phone number of the record.
     * Phone number should satisfy next rules: <br>
     * 1. The phone number should be split into groups using a space or dash. One group is also possible. <br>
     * 2. Before the first group, there may or may not be a plus symbol. <br>
     * 3. The first group or the second group can be wrapped in parentheses, but there should be no more than one group
     * which is wrapped in parentheses. There may be no groups wrapped in parentheses. <br>
     * 4. A group can contain numbers, uppercase, and lowercase English letters. A group should be at least 2 symbols
     * in length. But the first group may be only one symbol in length. <br>
     * If phone number is not satisfying provided rules text "[no data]" will be stored instead of provided input.
     *
     * @param number String representation of the phone number.
     */
    public void setNumber(String number) {
        String numberFormat = "\\+?((\\w+)|" +
                "(\\(\\w+\\))|" +
                "(\\(\\w+\\)[\\s-]\\w{2,})|" +
                "(\\w+[\\s-](\\(\\w{2,}\\)))|" +
                "\\w+[\\s-]\\w{2,})" +
                "([\\s-]\\w{2,})*";
        if (number.matches(numberFormat)) {
            this.number = number;
        } else {
            System.out.println("Bad number!");
            this.number = "[no data]";
        }
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public LocalDateTime getTimeEdited() {
        return timeEdited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactsRecord)) return false;
        ContactsRecord contactsRecord = (ContactsRecord) o;
        return Objects.equals(timeCreated, contactsRecord.timeCreated) &&
                Objects.equals(name, contactsRecord.name) &&
                Objects.equals(number, contactsRecord.number) &&
                Objects.equals(timeEdited, contactsRecord.timeEdited) &&
                Objects.equals(accessibleFields, contactsRecord.accessibleFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeCreated, name, number, timeEdited, accessibleFields);
    }
}
