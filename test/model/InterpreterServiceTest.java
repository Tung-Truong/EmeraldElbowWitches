package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class InterpreterServiceTest {
    @Test
    public void generateReport() throws Exception {
        Employee frenchFry = new Employee("fry@pizza.deliv", "Phillip", "Fry",
                "interpret", "French", "T", "oldboy", "clover");

        InterpreterService ida = new InterpreterService();
        InterpreterService ima = new InterpreterService();

        ida.setAssigned(frenchFry);
        ida.setSent();
        ida.setReceived(35000);
        ida.setActive(false);
        ida.generateReport();

        ima.setAssigned(frenchFry);
        ima.setSent();
        ima.setReceived(5000);
        ima.setActive(false);
        ima.generateReport();

        assertEquals(ima.statistic.avgTimeTaken, 20);
    }

    @Test
    public void testStatDB() throws Exception {

    }

}