package ch.xero88.qoqa.Model;

import com.google.common.base.Objects;

/**
 * Created by Anthony on 21/01/2016.
 */
public class User {

    public static final String FIRSTNAME = "firstname"; // TODO virer à terme car c'est lié à parse
    public static final String LASTNAME = "lastname"; // TODO virer à terme car c'est lié à parse

    private Object id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public void setId(Object id) {
        this.id = id;
    }

    public Object getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User note = (User) o;
        return Objects.equal(id, note.id) &&
                Objects.equal(firstName, note.firstName) &&
                Objects.equal(lastName, note.lastName) &&
                Objects.equal(username, note.username);
    }
}
