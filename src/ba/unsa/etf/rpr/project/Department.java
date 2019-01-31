package ba.unsa.etf.rpr.project;

public class Department {

    private int id;
    private String name;
    private Location location;
    private Employee manager;

    public Department(int id, String name, Location location, Employee manager) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.manager = manager;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

}
