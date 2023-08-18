package Model.api;

import java.util.Objects;

public class UpdateEmployeeRequest {
    private String lastName;
    private String email;
    private String url;
    private boolean isActive;

    public UpdateEmployeeRequest(String lastName, String email, String url, boolean isActive) {
        this.lastName = lastName;
        this.email = email;
        this.url = url;
        this.isActive = isActive;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        if (!(o instanceof UpdateEmployeeRequest that)) return false;
        return isActive == that.isActive && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getUrl(), that.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLastName(), getEmail(), getUrl(), isActive);
    }
}
