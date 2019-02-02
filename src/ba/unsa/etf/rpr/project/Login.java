package ba.unsa.etf.rpr.project;

public class Login {

    private int id;
    private String user;
    private String time;

    public Login(int id, String user, String time) {
        this.id = id;
        this.user = user;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
