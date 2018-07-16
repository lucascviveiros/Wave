package Model;

public class User {

    private String login_number;
    private String name;

    public User(String login_number, String name) {
        this.login_number = login_number;
        this.name = name;
    }

    public String getLogin_number() {
        return login_number;
    }

    public void setLogin_number(String login_number) {
        this.login_number = login_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
