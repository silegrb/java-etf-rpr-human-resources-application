package ba.unsa.etf.rpr.project;

import java.time.LocalDate;

public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private String parentName;
    private LocalDate birthDate;
    private String umcn;
    private String mobileNumber;
    private String emailAddress;
    private String creditCard;
    private int salary;
    private String photo;
    private Location location;
    private Department department;
    private Job job;
    private Employee manager;

    public Employee(int id, String firstName, String lastName, String parentName, LocalDate birthDate, String umcn, String mobileNumber, String emailAdress, String creditCard, int salary, String photo, Location location, Department department, Job job, Employee manager) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.parentName = parentName;
        this.birthDate = birthDate;
        this.umcn = umcn;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAdress;
        this.creditCard = creditCard;
        this.salary = salary;
        this.photo = photo;
        this.location = location;
        this.department = department;
        this.job = job;
        this.manager = manager;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getUmcn() {
        return umcn;
    }

    public void setUmcn(String umcn) {
        this.umcn = umcn;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAdress) {
        this.emailAddress = emailAdress;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return firstName + " " +  lastName;
    }
}
