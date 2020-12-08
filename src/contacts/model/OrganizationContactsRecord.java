package contacts.model;

import java.util.List;
import java.util.Objects;


public class OrganizationContactsRecord extends ContactsRecord {

    private String address;

    public OrganizationContactsRecord() {
        super();
        super.accessibleFields = List.of("name", "address", "number");
    }

    @Override
    public void editFieldByName(String fieldName, String newValue) throws WrongFieldNameException {
        switch (fieldName) {
            case "name":
                setName(newValue);
                break;
            case "address":
                setAddress(newValue);
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
                return "Organization name: " + getName();
            case "address":
                return "Address: " + getAddress();
            case "number":
                return "Number: " + getNumber();
            default:
                throw new WrongFieldNameException("There's no such field to edit: " + fieldName);
        }
    }

    @Override
    public String toSearchableString() {
        return (getName() + getAddress() + getNumber()).toLowerCase();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationContactsRecord)) return false;
        if (!super.equals(o)) return false;
        OrganizationContactsRecord that = (OrganizationContactsRecord) o;
        return Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address);
    }
}
