package com.example.siganiv.receiver;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {

    private static final String DEFAULT_PORT = "9999";
    private static final String START_RECEIVING = "Start";
    private static final String STOP_RECEIVING = "Stop";
    private static final String LAST_DATA_MESSAGE = "Last data: ";
    private static final String AVERAGE_MESSAGE = "SMA (last 20 samples): ";
    private static final String RMS_MESSAGE = "RMS (last 20 samples): ";
    public static final String MESSAGE_TO_ARCHIVE = "message";

    private TextView message;
    private EditText port;
    private EditText averageText;
    private Button start;
    private Button archive;
    private DataValues result;
    private Receive receive;
    private GraphView graph;

    private boolean toggleAverageFlag = false;

    private int seriesController = 0;
    private int series2Controller = 0;

    private LineGraphSeries<DataPoint> series1;
    private LineGraphSeries<DataPoint> series2;
    private LineGraphSeries<DataPoint> series3;
    private LineGraphSeries<DataPoint> series4;
    private LineGraphSeries<DataPoint> seriesAverage;

    Receive.ReceiveInterface receiveInterface = new Receive.ReceiveInterface() {
        @Override
        public void onUpdate(String value) {
            result.add(value);
            series1 = new LineGraphSeries<DataPoint>(result.getSeries1());
            series2 = new LineGraphSeries<DataPoint>(result.getSeries2());
            series3 = new LineGraphSeries<DataPoint>(result.getSeries3());
            series4 = new LineGraphSeries<DataPoint>(result.getSeries4());

            graph.removeAllSeries();

            averageText.setText(LAST_DATA_MESSAGE + result.getLastData1());

            if (series2Controller == 0) {
                setBareSeries();
            } else if (series2Controller == 1) {
                setAverageSeries();
            } else if (series2Controller == 2) {
                setRMSSeries();
            }
        }
    };

    private void setBareSeries() {

        message.setText(LAST_DATA_MESSAGE);

        if (seriesController == 0) {
            averageText.setText(" " + result.getLastData1());
            graph.addSeries(series1);
        } else if (seriesController == 1) {
            averageText.setText(" " + result.getLastData2());
            graph.addSeries(series2);
        } else if (seriesController == 2) {
            averageText.setText(" " + result.getLastData3());
            graph.addSeries(series3);
        } else if (seriesController == 3) {
            averageText.setText(" " + result.getLastData4());
            graph.addSeries(series4);
        }
    }

    private void setAverageSeries() {

        message.setText(AVERAGE_MESSAGE);

        if (seriesController == 0) {
            seriesAverage = new LineGraphSeries<DataPoint>(result.getAverage(0));
            averageText.setText(" " + result.getPreciseAverage(seriesController));
            graph.addSeries(seriesAverage);
        } else if (seriesController == 1) {
            seriesAverage = new LineGraphSeries<DataPoint>(result.getAverage(1));
            averageText.setText(" " + result.getPreciseAverage(seriesController));
            graph.addSeries(seriesAverage);
        } else if (seriesController == 2) {
            seriesAverage = new LineGraphSeries<DataPoint>(result.getAverage(2));
            averageText.setText(" " + result.getPreciseAverage(seriesController));
            graph.addSeries(seriesAverage);
        } else if (seriesController == 3) {
            seriesAverage = new LineGraphSeries<DataPoint>(result.getAverage(3));
            averageText.setText(" " + result.getPreciseAverage(seriesController));
            graph.addSeries(seriesAverage);
        }
    }

    private void setRMSSeries() {

        message.setText(RMS_MESSAGE);

        if (seriesController == 0) {
            averageText.setText(" " + result.getRMS(seriesController));
            graph.addSeries(series1);
        } else if (seriesController == 1) {
            averageText.setText(" " + result.getRMS(seriesController));
            graph.addSeries(series2);
        } else if (seriesController == 2) {
            averageText.setText(" " + result.getRMS(seriesController));
            graph.addSeries(series3);
        } else if (seriesController == 3) {
            averageText.setText(" " + result.getRMS(seriesController));
            graph.addSeries(series4);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        result = new DataValues();
        setContentView(R.layout.activity_main);
        port = (EditText) findViewById(R.id.portButton);
        averageText = (EditText) findViewById(R.id.averageText);
        start = (Button) findViewById(R.id.startButton);
        archive = (Button) findViewById(R.id.archiveButton);
        graph = (GraphView) findViewById(R.id.graph);
        message = (TextView) findViewById(R.id.message);

        series1 = new LineGraphSeries<DataPoint>(result.getSeries1());
        series2 = new LineGraphSeries<DataPoint>(result.getSeries2());
        series3 = new LineGraphSeries<DataPoint>(result.getSeries3());
        series4 = new LineGraphSeries<DataPoint>(result.getSeries4());

        port.setText(String.valueOf(DEFAULT_PORT));
        message.setText(LAST_DATA_MESSAGE);
        averageText.setEnabled(false);

        setSpinner();
        setSpinner2();
    }

    private void setSpinner() {
        String[] elements = {"Channel 1", "Channel 2", "Channel 3", "Channel 4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elements);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int id, long position) {
                graph.removeAllSeries();

                switch ((int) position) {
                    case 0:
                        seriesController = 0;

                        if (series2Controller == 0) {
                            message.setText(LAST_DATA_MESSAGE);
                            averageText.setText(" " + result.getLastData1());
                            graph.addSeries(series1);
                        } else if (series2Controller == 1) {
                            seriesAverage = new LineGraphSeries<DataPoint>(result.getAverage(0));
                            message.setText(AVERAGE_MESSAGE);
                            averageText.setText(" " + result.getPreciseAverage(seriesController));
                            graph.addSeries(seriesAverage);
                        } else if (series2Controller == 2) {
                            message.setText(RMS_MESSAGE);
                            averageText.setText(" " + result.getRMS(seriesController));
                            graph.addSeries(series1);
                        }

                        break;
                    case 1:
                        seriesController = 1;

                        if (series2Controller == 0) {
                            message.setText(LAST_DATA_MESSAGE);
                            averageText.setText(" " + result.getLastData2());
                            graph.addSeries(series2);
                        } else if (series2Controller == 1) {
                            seriesAverage = new LineGraphSeries<DataPoint>(result.getAverage(1));
                            message.setText(AVERAGE_MESSAGE);
                            averageText.setText(" " + result.getPreciseAverage(seriesController));
                            graph.addSeries(seriesAverage);
                        } else if (series2Controller == 2) {
                            message.setText(RMS_MESSAGE);
                            averageText.setText(" " + result.getRMS(seriesController));
                            graph.addSeries(series2);
                        }


                        break;
                    case 2:
                        seriesController = 2;

                        if (series2Controller == 0) {
                            message.setText(LAST_DATA_MESSAGE);
                            averageText.setText(" " + result.getLastData3());
                            graph.addSeries(series3);
                        } else if (series2Controller == 1) {
                            seriesAverage = new LineGraphSeries<DataPoint>(result.getAverage(2));
                            message.setText(AVERAGE_MESSAGE);
                            averageText.setText(" " + result.getPreciseAverage(seriesController));
                            graph.addSeries(seriesAverage);
                        } else if (series2Controller == 2) {
                            message.setText(RMS_MESSAGE);
                            averageText.setText(" " + result.getRMS(seriesController));
                            graph.addSeries(series3);
                        }

                        break;
                    case 3:
                        seriesController = 3;

                        if (series2Controller == 0) {
                            message.setText(LAST_DATA_MESSAGE);
                            averageText.setText(" " + result.getLastData4());
                            graph.addSeries(series4);
                        } else if (series2Controller == 1) {
                            seriesAverage = new LineGraphSeries<DataPoint>(result.getAverage(3));
                            message.setText(AVERAGE_MESSAGE);
                            averageText.setText(" " + result.getPreciseAverage(seriesController));
                            graph.addSeries(seriesAverage);
                        } else if (series2Controller == 2) {
                            message.setText(RMS_MESSAGE);
                            averageText.setText(" " + result.getRMS(seriesController));
                            graph.addSeries(series4);
                        }

                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    private void setSpinner2() {
        String[] elements2 = {"Bare data", "SMA", "RMS", "TBA"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elements2);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter1);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int id, long position) {

                graph.removeAllSeries();

                switch ((int) position) {
                    case 0:
                        series2Controller = 0;

                        setBareSeries();

                        break;

                    case 1:
                        series2Controller = 1;

                        setAverageSeries();

                        break;
                    case 2:
                        series2Controller = 2;

                        setRMSSeries();

                        break;
                    case 3:
                        series2Controller = 3;

                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    public void onClickArchive(View view) {
        Intent getNameScreenIntent = new Intent(this, ArchiveActivity.class);

        getNameScreenIntent.putExtra(MESSAGE_TO_ARCHIVE, result.getHistory());
        startActivity(getNameScreenIntent);
    }

    public void onClickStart(View view) {
        if (receive == null || receive.getStatus() != AsyncTask.Status.RUNNING) {
            receive = new Receive(receiveInterface);
            receive.execute(Integer.parseInt(port.getText().toString()));

            start.setText(STOP_RECEIVING);

        } else {
            receive.stop();

            start.setText(START_RECEIVING);
        }
    }

    public void onClickAverage(View view) {
        if (!toggleAverageFlag) {

            graph.addSeries(seriesAverage);
            toggleAverageFlag = true;

        } else {

            graph.removeSeries(seriesAverage);
            toggleAverageFlag = false;
        }
    }

}
