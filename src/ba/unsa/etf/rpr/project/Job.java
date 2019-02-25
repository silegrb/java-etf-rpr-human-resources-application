package ba.unsa.etf.rpr.project;

public class Job {

    private int id;
    private String jobTitle;

    public Job( int id, String jobTitle ) {
        this.id = id;
        this.jobTitle = jobTitle;
    }

    public Job() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
