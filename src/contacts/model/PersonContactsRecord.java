package contacts.model;

import java.util.List;
import java.util.Objects;


public class PersonContactsRecord extends ContactsRecord {

    private String surname;
    private String birthDate;
    private String gender;

    public PersonContactsRecord() {
        super();
        super.accessibleFields = List.of("name", "surname", "birth", "gender", "number");
    }

    @Override
    public void editFieldByName(String fieldName, String newValue) throws WrongFieldNameException {
        switch (fieldName) {
            case "name":
                setName(newValue);
                break;
            case "surname":
                setSurname(newValue);
                break;
            case "birth":
                setBirthDate(newValue);
                break;
            case "gender":
                setGender(newValue);
                break;
            case "number":
                setNumber(newValue);
                break;
            default:
                throw new WrongFieldNameException("There's no such field to edit: " + fieldName);
        }
    }

    @Override
    public String getFieldByName(String fieldName) throws WrongFieldNameException {
        switch (fieldName) {
            case "name":
                return "Name: " + getName();
            case "surname":
                return "Surname: " + getSurname();
            case "birth":
                return "Birth date: " + getBirthDate();
            case "gender":
                return "Gender: " + getGender();
            case "number":
                return "Number: " + getNumber();
            default:
                throw new WrongFieldNameException("There's no such field to edit: " + fieldName);
        }
    }

    @Override
    public String toSearchableString() {
        return (getName() + getSurname() + getNumber() + getGender() + getBirthDate()).toLowerCase();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Stores date of birth of person if provided parameter satisfy format "YYYY-MM-DD" otherwise stores "[no data]".
     *
     * @param birthDate String representation of date in format "YYYY-MM-DD".
     */
    public void setBirthDate(String birthDate) {
        if (birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            this.birthDate = birthDate;
        } else {
            System.out.println("Bad birth date!");
            this.birthDate = "[no data]";
        }
    }

    public String getGender() {
        return gender;
    }

    /**
     * Stores the gender of person, where M - male, F - female.
     * If gender provided in other formats than M or F stores text "[no data]"
     *
     * @param gender Persons gender in format "M" or "F"
     */
    public void setGender(String gender) {
        if ("M".equals(gender) || "F".equals(gender)) {
            this.gender = gender;
        } else {
            System.out.println("Bad gender!");
            this.gender = "[no data]";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonContactsRecord)) return false;
        if (!super.equals(o)) return false;
        PersonContactsRecord that = (PersonContactsRecord) o;
        return Objects.equals(surname, that.surname) &&
                Objects.equals(birthDate, that.birthDate) &&
                Objects.equals(gender, that.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), surname, birthDate, gender);
    }
}
