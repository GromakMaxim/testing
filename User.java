import java.util.Objects;

public class User {
    private final String id;
    private final String fullname;
    private final String address;

    public User(String id, String fullname, String address) {
        this.id = id;
        this.fullname=fullname;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getFullname() {
        return fullname;
    }


    @Override
    public String toString() {
        return "Пользователь: " + id + "  " + fullname + "  " + address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
