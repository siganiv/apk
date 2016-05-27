package com.example.siganiv.receiver;

/**
 * Created by Siganiv on 2016-03-27.
 */
public class CustomDataPoint {

    private final static String INVALID_MSG = "Invalid data";

    private int time;
    private double value1;
    private double value2;
    private double value3;
    private double value4;
    private DataPointState state = DataPointState.INVALID;

    public CustomDataPoint() {
    }

    public CustomDataPoint(int time, double value1, double value2, double value3, double value4) {
        this.state = DataPointState.VALID;
        this.time = time;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
    }

    public int getTime() {
        return time;
    }

    public double getValue1() {
        return value1;
    }

    public double getValue2() {
        return value2;
    }

    public double getValue3() {
        return value3;
    }

    public double getValue4() {
        return value4;
    }

    public DataPointState getState() {
        return state;
    }

    @Override
    public String toString() {

        if (state == DataPointState.INVALID) {
            return INVALID_MSG;
        } else {
            return "Time: " + time + " Values: " + value1 + " " + value2 + " " + value3 + " " + value4;
        }
    }
}
