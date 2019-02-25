package ba.unsa.etf.rpr.project;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContractTest {
    @Test
    public void contractTestOne(){
        Contract contract = new Contract(1,"test1", LocalDate.now(), LocalDate.now(),"test manager","testName testSurname");
        assertEquals(1,contract.getId());
        assertEquals("test1",contract.getContractNumber());
        assertEquals("test manager",contract.getJobTitle());
        assertEquals("testName testSurname",contract.getEmployeeFullname());
    }

    @Test
    public void contractTestTwo(){
        Contract contract = new Contract();
        contract.setId(1);
        assertEquals(1, contract.getId());
        contract.setContractNumber("123");
        assertEquals("123",contract.getContractNumber());
        contract.setJobTitle("test job title");
        assertEquals("test job title", contract.getJobTitle());
        contract.setEmployeeFullname("test test");
        assertEquals("test test", contract.getEmployeeFullname());
    }
}