package ba.unsa.etf.rpr.project;

import java.time.LocalDate;

public class Contract {

    private int id;
    private String contractNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private String jobTitle;
    private String employeeFullname;

    public Contract(int id, String contractNumber, LocalDate startDate, LocalDate endDate, String jobTitle, String employeeFullname) {
        this.id = id;
        this.contractNumber = contractNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.jobTitle = jobTitle;
        this.employeeFullname = employeeFullname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmployeeFullname() {
        return employeeFullname;
    }

    public void setEmployeeFullname(String employeeFullname) {
        this.employeeFullname = employeeFullname;
    }
}
