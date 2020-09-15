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
}
