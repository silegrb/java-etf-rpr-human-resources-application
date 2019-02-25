package ba.unsa.etf.rpr.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JobTest {
    @Test
    public void jobTestOne(){
        Job job = new Job(1,"test");
        assertEquals(1, job.getId());
        assertEquals("test", job.getJobTitle());
    }

    @Test
    public void jobTestTwo(){
        Job job = new Job();
        job.setId(1);
        job.setJobTitle("test");
        assertEquals(1, job.getId());
        assertEquals("test", job.getJobTitle());
    }
}