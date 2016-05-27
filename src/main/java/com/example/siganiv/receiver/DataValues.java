package com.example.siganiv.receiver;

import com.jjoe64.graphview.series.DataPoint;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siganiv on 2016-03-11.
 */
public class DataValues {

    private final static String SPLITTER = "#";
    private final static int INVALID_VALUE = -1;
    private final static int INITIAL_VALUE = 0;
    private final static int MAX_VALUES = 5;
    private final static String INITIAL_TEXT = "";
    private final static String NEXT_LINE = "\n";
    private final static int MAX_AVERAGE = 5;
    private final static int MAX_SERIES = 50;
    private final static int MAX_LENGTH = 1024;

    public List<CustomDataPoint> values;

    public DataValues() {
        values = new ArrayList<>();
    }

    public void add(String value) {

        if (value.contains(SPLITTER)) {
            String tempValues[] = value.split(SPLITTER);
            int tempTime = INITIAL_VALUE;
            double tempCheckedValues[] = {INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE, INITIAL_VALUE};

            for (int i = 0; i < tempValues.length; i++) {

                if (i == 0) {

                    tempTime = checkTime(tempValues[0]);

                } else if (i > 0 && i < MAX_VALUES) {

                    tempCheckedValues[i - 1] = checkValue(tempValues[i]);

                } else {
                    break;
                }
            }

            addPoint(tempTime, tempCheckedValues);

        } else {
            values.add(new CustomDataPoint());
        }

    }

    private void addPoint(int tempTime, double[] tempCheckedValues) {
        boolean validityFlag = true;

        if (tempTime != INVALID_VALUE) {
            for (int i = 0; i < tempCheckedValues.length; i++) {
                if (tempCheckedValues[i] == INVALID_VALUE) {
                    validityFlag = false;
                }
            }
        } else {
            validityFlag = false;
        }

        if (validityFlag) {

            tempCheckedValues = convertValues(tempCheckedValues);

            if (values.size() < MAX_LENGTH) {
                values.add(new CustomDataPoint(tempTime, tempCheckedValues[0], tempCheckedValues[1], tempCheckedValues[2], tempCheckedValues[3]));
            } else {
                for (int i = 0; i < values.size() - 1; i++) {
                    values.set(i, values.get(i + 1));
                }
                values.set(values.size() - 1, new CustomDataPoint(tempTime, tempCheckedValues[0], tempCheckedValues[1], tempCheckedValues[2], tempCheckedValues[3]));
            }
        } else {
            values.add(new CustomDataPoint());
        }
    }

    private double[] convertValues(double[] initialPoints) {

        final int DEMONINATOR = 1024;
        final int INPUT_SENSOR_VALUE = 16;
        final int OUTPUT_SENSOR_VALUE = 100;

        initialPoints[0] = initialPoints[0] * INPUT_SENSOR_VALUE / DEMONINATOR - 1;
        initialPoints[2] = initialPoints[2] * INPUT_SENSOR_VALUE / DEMONINATOR - 1;

        initialPoints[1] = initialPoints[1] * OUTPUT_SENSOR_VALUE / DEMONINATOR;
        initialPoints[3] = initialPoints[3] * OUTPUT_SENSOR_VALUE / DEMONINATOR;

        return initialPoints;
    }

    private int checkTime(String time) {
        try {
            return Integer.parseInt(time);
        } catch (NumberFormatException e) {
            return INVALID_VALUE;
        }
    }

    private double checkValue(String time) {
        try {
            return Double.parseDouble(time);
        } catch (NumberFormatException e) {
            return INVALID_VALUE;
        }
    }

    public String getHistory() {
        String tempString = INITIAL_TEXT;
        for (CustomDataPoint dataPoint : values) {
            tempString = (dataPoint.toString() + NEXT_LINE + tempString);
        }

        return tempString;
    }

    public DataPoint[] getSeries1() {
        List<DataPoint> tempDataPoints = new ArrayList<>();

        int count = 0;

        for (int i = values.size() - 1; i >= 0; i--) {
            if (values.get(i).getState() == DataPointState.VALID && count < MAX_SERIES) {
                tempDataPoints.add(new DataPoint(values.get(i).getTime(), values.get(i).getValue1()));
                count++;
            }
        }

        List<DataPoint> tempDataPoints2 = new ArrayList<>();

        for (int i = tempDataPoints.size() - 1; i >= 0; i--) {
            tempDataPoints2.add(tempDataPoints.get(i));
        }

        DataPoint[] array = tempDataPoints2.toArray(new DataPoint[tempDataPoints2.size()]);
        return array;
    }

    public DataPoint[] getSeries2() {
        List<DataPoint> tempDataPoints = new ArrayList<>();

        int count = 0;

        for (int i = values.size() - 1; i >= 0; i--) {
            if (values.get(i).getState() == DataPointState.VALID && count < MAX_SERIES) {
                tempDataPoints.add(new DataPoint(values.get(i).getTime(), values.get(i).getValue2()));
                count++;
            }
        }

        List<DataPoint> tempDataPoints2 = new ArrayList<>();

        for (int i = tempDataPoints.size() - 1; i >= 0; i--) {
            tempDataPoints2.add(tempDataPoints.get(i));
        }

        DataPoint[] array = tempDataPoints2.toArray(new DataPoint[tempDataPoints2.size()]);
        return array;
    }

    public DataPoint[] getSeries3() {
        List<DataPoint> tempDataPoints = new ArrayList<>();

        int count = 0;

        for (int i = values.size() - 1; i >= 0; i--) {
            if (values.get(i).getState() == DataPointState.VALID && count < MAX_SERIES) {
                tempDataPoints.add(new DataPoint(values.get(i).getTime(), values.get(i).getValue3()));
                count++;
            }
        }

        List<DataPoint> tempDataPoints2 = new ArrayList<>();

        for (int i = tempDataPoints.size() - 1; i >= 0; i--) {
            tempDataPoints2.add(tempDataPoints.get(i));
        }

        DataPoint[] array = tempDataPoints2.toArray(new DataPoint[tempDataPoints2.size()]);
        return array;
    }

    public DataPoint[] getSeries4() {
        List<DataPoint> tempDataPoints = new ArrayList<>();

        int count = 0;

        for (int i = values.size() - 1; i >= 0; i--) {
            if (values.get(i).getState() == DataPointState.VALID && count < MAX_SERIES) {
                tempDataPoints.add(new DataPoint(values.get(i).getTime(), values.get(i).getValue4()));
                count++;
            }
        }

        List<DataPoint> tempDataPoints2 = new ArrayList<>();

        for (int i = tempDataPoints.size() - 1; i >= 0; i--) {
            tempDataPoints2.add(tempDataPoints.get(i));
        }

        DataPoint[] array = tempDataPoints2.toArray(new DataPoint[tempDataPoints2.size()]);
        return array;
    }

    public DataPoint[] getAverage(int series) {
        List<DataPoint> tempDataPoints = new ArrayList<>();

        int count = 0;

        if (series == 0) {
            for (int i = values.size() - 1; i >= 0; i--) {
                if ((values.size() > 4 && (values.get(i).getState() == DataPointState.VALID) && count < MAX_SERIES)) {
                    tempDataPoints.add(new DataPoint(values.get(i).getTime(), countAverage(values.get(i - 1).getValue1(), values.get(i - 2).getValue1(), values.get(i - 3).getValue1(), values.get(i - 4).getValue1())));
                    count++;
                }
            }
        } else if (series == 1) {
            for (int i = values.size() - 1; i >= 0; i--) {
                if ((values.size() > 4 && (values.get(i).getState() == DataPointState.VALID) && count < MAX_SERIES)) {
                    tempDataPoints.add(new DataPoint(values.get(i).getTime(), countAverage(values.get(i - 1).getValue2(), values.get(i - 2).getValue2(), values.get(i - 3).getValue2(), values.get(i - 4).getValue2())));
                    count++;
                }
            }
        } else if (series == 2) {
            for (int i = values.size() - 1; i >= 0; i--) {
                if ((values.size() > 4 && (values.get(i).getState() == DataPointState.VALID) && count < MAX_SERIES)) {
                    tempDataPoints.add(new DataPoint(values.get(i).getTime(), countAverage(values.get(i - 1).getValue3(), values.get(i - 2).getValue3(), values.get(i - 3).getValue3(), values.get(i - 4).getValue3())));
                    count++;
                }
            }
        } else if (series == 3) {
            for (int i = values.size() - 1; i >= 0; i--) {
                if ((values.size() > 4 && (values.get(i).getState() == DataPointState.VALID) && count < MAX_SERIES)) {
                    tempDataPoints.add(new DataPoint(values.get(i).getTime(), countAverage(values.get(i - 1).getValue4(), values.get(i - 2).getValue4(), values.get(i - 3).getValue4(), values.get(i - 4).getValue4())));
                    count++;
                }
            }
        }

        List<DataPoint> tempDataPoints2 = new ArrayList<>();

        for (int i = tempDataPoints.size() - 1; i >= 0; i--) {
            tempDataPoints2.add(tempDataPoints.get(i));
        }

        DataPoint[] array = tempDataPoints2.toArray(new DataPoint[tempDataPoints.size()]);
        return array;
    }

    public double getPreciseAverage(int series) {
        double result = 0;

        int count = 0;

        double numerator = 0;
        int denominator = 0;

        if (series == 0) {
            for (int i = values.size() - 1; i >= 0; i--) {
                if (values.get(i).getState() == DataPointState.VALID && count < MAX_SERIES) {
                    numerator += values.get(i).getValue1();
                    count++;
                }
            }
        } else if (series == 1) {
            for (int i = values.size() - 1; i >= 0; i--) {
                if (values.get(i).getState() == DataPointState.VALID && count < MAX_SERIES) {
                    numerator += values.get(i).getValue2();
                    count++;
                }
            }
        } else if (series == 2) {
            for (int i = values.size() - 1; i >= 0; i--) {
                if (values.get(i).getState() == DataPointState.VALID && count < MAX_SERIES) {
                    numerator += values.get(i).getValue3();
                    count++;
                }
            }
        } else if (series == 3) {
            for (int i = values.size() - 1; i >= 0; i--) {
                if (values.get(i).getState() == DataPointState.VALID && count < MAX_SERIES) {
                    numerator += values.get(i).getValue4();
                    count++;
                }
            }
        }

        if (count != 0) {
            denominator = count;
        } else {
            return 0;
        }

        result = numerator / denominator;

        result = round(result);

        return result;
    }

    private double round(double initialValue){
        BigDecimal bd = new BigDecimal(Double.toString(initialValue));
        bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
        double result = bd.doubleValue();
        return result;
    }

    public double getRMS(int series) {
        double result = 0;

        int count = 0;

        double numerator = 0;
        int denominator = 0;

        if (series == 0) {
            for (int i = values.size() - 1; i >= 0; i--) {
                if (values.get(i).getState() == DataPointState.VALID && count < MAX_SERIES) {
                    numerator += Math.pow(values.get(i).getValue1(), 2);
                    count++;
                }
            }
        } else if (series == 1) {
            for (int i = values.size() - 1; i >= 0; i--) {
                if (values.get(i).getState() == DataPointState.VALID && count < MAX_SERIES) {
                    numerator += Math.pow(values.get(i).getValue2(), 2);
                    count++;
                }
            }
        } else if (series == 2) {
            for (int i = values.size() - 1; i >= 0; i--) {
                if (values.get(i).getState() == DataPointState.VALID && count < MAX_SERIES) {
                    numerator += Math.pow(values.get(i).getValue3(), 2);
                    count++;
                }
            }
        } else if (series == 3) {
            for (int i = values.size() - 1; i >= 0; i--) {
                if (values.get(i).getState() == DataPointState.VALID && count < MAX_SERIES) {
                    numerator += Math.pow(values.get(i).getValue4(), 2);
                    count++;
                }
            }
        }

        if (count != 0) {
            denominator = count;
        } else {
            return 0;
        }

        result = Math.sqrt(numerator / denominator);

        result = round(result);

        return result;
    }

    public double getLastData1() {
        if (getSeries1().length == 0) {
            return 0;
        }
        return round(getSeries1()[getSeries1().length - 1].getY());
    }

    public double getLastData2() {
        if (getSeries2().length == 0) {
            return 0;
        }
        return round(getSeries2()[getSeries2().length - 1].getY());
    }

    public double getLastData3() {
        if (getSeries3().length == 0) {
            return 0;
        }
        return round(getSeries3()[getSeries3().length - 1].getY());
    }

    public double getLastData4() {
        if (getSeries4().length == 0) {
            return 0;
        }
        return round(getSeries4()[getSeries4().length - 1].getY());
    }

    private double countAverage(double first, double second, double third, double fourth) {
        double result = 0;

        int denominator = 4;

        if (first == 0) {
            denominator--;
        }

        if (second == 0) {
            denominator--;
        }

        if (third == 0) {
            denominator--;
        }

        if (fourth == 0) {
            denominator--;
        }

        if (denominator == 0) {
            denominator = 1;
        }

        double numerator = first + second + third + fourth;

        result = numerator / denominator;

        return result;
    }
}
