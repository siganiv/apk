package com.example.siganiv.receiver;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class DataValuesTest {

    DataValues result = new DataValues();

    Receive.ReceiveInterface rec_interface = new Receive.ReceiveInterface() {
        @Override
        public void onUpdate(String value) {
            result.add(value);
        }
    };

    Receive rec = new Receive(rec_interface);

    private boolean initializedFlag = false;

    @Before
    public void setup(){
        if (!initializedFlag) {
            result.add("10#12.5#15#13.6");
            result.add("garbage");
            result.add("11#11111.1111#15.1111");
            result.add("12#garbage#11");
            result.add("13#13.4#15.5#13.6#10");
            result.add("14#13.5#16#17#13#11.11");
            result.add("14#13.5#16#17#13#11.11");
            result.add("14#13.5#16#17#13#11.11");

            initializedFlag = true;
        }
    }

    @Test
    public void shouldCheckSize() throws Exception {

        assertEquals(6, result.getSeries1().length);
        assertEquals(6, result.getSeries2().length);
        assertEquals(6, result.getSeries3().length);
        assertEquals(6, result.getSeries4().length);
        assertEquals(5, result.getAverage().length);
    }

    @Test
    public void shouldCheckHistorySize() throws Exception {

        assertEquals(6, result.getHistory().length());
    }

//    @Test
//    public void shouldCheckHistory() throws Exception {
//
//        assertEquals("Time: 10 Values: 12.5 15.0 13.6 0.0", result.getHistory().get(0));
//        assertEquals("Invalid data", result.getHistory().get(1));
//        assertEquals("Time: 11 Values: 11111.1111 15.1111 0.0 0.0", result.getHistory().get(2));
//        assertEquals("Invalid data", result.getHistory().get(3));
//        assertEquals("Time: 13 Values: 13.4 15.5 13.6 10.0", result.getHistory().get(4));
//        assertEquals("Time: 14 Values: 13.5 16.0 17.0 13.0", result.getHistory().get(5));
//    }

    @Test
    public void shouldCheckFirstValues() throws Exception {

        assertEquals(10, result.getSeries1()[0].getX(), 0);
        assertEquals(12.5, result.getSeries1()[0].getY(),0);

        assertEquals(11, result.getSeries1()[1].getX(), 0);
        assertEquals(11111.1111, result.getSeries1()[1].getY(),0);

        assertEquals(13, result.getSeries1()[2].getX(), 0);
        assertEquals(13.4, result.getSeries1()[2].getY(),0);

        assertEquals(14, result.getSeries1()[3].getX(), 0);
        assertEquals(13.5, result.getSeries1()[3].getY(),0);
    }

    @Test
    public void shouldCheckSecondValues() throws Exception {

        assertEquals(10, result.getSeries2()[0].getX(), 0);
        assertEquals(15, result.getSeries2()[0].getY(),0);

        assertEquals(11, result.getSeries2()[1].getX(), 0);
        assertEquals(15.1111, result.getSeries2()[1].getY(),0);

        assertEquals(13, result.getSeries2()[2].getX(), 0);
        assertEquals(15.5, result.getSeries2()[2].getY(),0);

        assertEquals(14, result.getSeries2()[3].getX(), 0);
        assertEquals(16, result.getSeries2()[3].getY(),0);
    }

    @Test
    public void shouldCheckThirdValues() throws Exception {

        assertEquals(10, result.getSeries3()[0].getX(), 0);
        assertEquals(13.6, result.getSeries3()[0].getY(),0);

        assertEquals(11, result.getSeries3()[1].getX(), 0);
        assertEquals(0, result.getSeries3()[1].getY(),0);

        assertEquals(13, result.getSeries3()[2].getX(), 0);
        assertEquals(13.6, result.getSeries3()[2].getY(),0);

        assertEquals(14, result.getSeries3()[3].getX(), 0);
        assertEquals(17, result.getSeries3()[3].getY(),0);
    }

    @Test
    public void shouldCheckFourthValues() throws Exception {

        assertEquals(10, result.getSeries4()[0].getX(), 0);
        assertEquals(0, result.getSeries4()[0].getY(),0);

        assertEquals(11, result.getSeries4()[1].getX(), 0);
        assertEquals(0, result.getSeries4()[1].getY(),0);

        assertEquals(13, result.getSeries4()[2].getX(), 0);
        assertEquals(10, result.getSeries4()[2].getY(),0);

        assertEquals(14, result.getSeries4()[3].getX(), 0);
        assertEquals(13, result.getSeries4()[3].getY(),0);
    }

    @Test
    public void shouldCheckAverageValues() throws Exception {

        assertEquals(14, result.getAverage()[0].getX(), 0);
        assertEquals(14.875, result.getAverage()[0].getY(),0);

        assertEquals(13, result.getAverage()[1].getX(), 0);
        assertEquals(13.125, result.getAverage()[1].getY(),0);

        assertEquals(11, result.getAverage()[2].getX(), 0);
        assertEquals(5563.1111, result.getAverage()[2].getY(),0);

        assertEquals(10, result.getAverage()[3].getX(), 0);
        assertEquals(13.7, result.getAverage()[3].getY(),0.1);
    }
}