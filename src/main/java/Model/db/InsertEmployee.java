package Model.db;

import java.sql.Timestamp;
import java.util.Objects;

public class InsertEmployee {
    private String first_name;
    private String last_name;
    private String middle_name;
    private String phone;
    private String email;
    private String avatar_url;
    private int company_id;
    private Timestamp birthdate;
    private boolean isActive;

    public InsertEmployee(String first_name, String last_name, String middle_name, String phone, String email,
                          String avatar_url, int company_id, Timestamp birthdate,boolean isActive) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.middle_name = middle_name;
        this.phone = phone;
        this.email = email;
        this.avatar_url = avatar_url;
        this.company_id = company_id;
        this.birthdate = birthdate;
        this.isActive = isActive;
    }

    public InsertEmployee() {
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public Timestamp getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Timestamp birthdate) {
        this.birthdate = birthdate;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InsertEmployee employee)) return false;
        return getCompany_id() == employee.getCompany_id() && isActive == employee.isActive && Objects.equals(getFirst_name(), employee.getFirst_name()) && Objects.equals(getLast_name(), employee.getLast_name()) && Objects.equals(getMiddle_name(), employee.getMiddle_name()) && Objects.equals(getPhone(), employee.getPhone()) && Objects.equals(getEmail(), employee.getEmail()) && Objects.equals(getAvatar_url(), employee.getAvatar_url()) && Objects.equals(getBirthdate(), employee.getBirthdate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirst_name(), getLast_name(), getMiddle_name(), getPhone(), getEmail(), getAvatar_url(), getCompany_id(), getBirthdate(), isActive);
    }
}
