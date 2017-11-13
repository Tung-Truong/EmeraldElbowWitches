package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class JanitorServiceTest {
    JanitorService dave = new JanitorService();

    @Test
    public void sendEmailServiceRequest() throws Exception {
        try {
            dave.sendEmailServiceRequest("I'm so tired");
        }
        catch(Exception e){

        }
    }

}