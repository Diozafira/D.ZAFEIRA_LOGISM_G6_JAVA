package src;

public class User {
    int iduser;
    String username;
    String password;
    String role;

    public User(int iduser, String username, String password, String role) {
        this.iduser = iduser;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
