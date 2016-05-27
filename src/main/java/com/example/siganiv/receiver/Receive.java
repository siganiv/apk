package com.example.siganiv.receiver;

import android.os.AsyncTask;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Siganiv on 2016-03-11.
 */
public class Receive extends AsyncTask<Integer, String, Void> {

    private final static String LOG_MSG_UDP = "UDP";
    private final static String LOG_MSG_REC = "Receiving...";
    private final static String LOG_MSG_REC_STR = "Received String: ";
    private final static String LOG_MSG_ADRESS = "IP Adress: ";
    private final static String LOG_MSG_PORT = "Port: ";
    private final static String LOG_MSG_ERROR = "Error";

    private ReceiveInterface rec_interface;
    private DatagramSocket clientsocket;

    private boolean started;

    public Receive(ReceiveInterface rec_interface) {
        this.rec_interface = rec_interface;
        started = true;
    }

    @Override
    protected Void doInBackground(Integer... params) {
        try {
            clientsocket = new DatagramSocket(params[0]);

            while (started) {
                byte[] receivedata = new byte[1024];
                DatagramPacket recv_packet = new DatagramPacket(receivedata, receivedata.length);
                Log.d(LOG_MSG_UDP, LOG_MSG_REC);
                clientsocket.receive(recv_packet);
                String rec_str = new String(recv_packet.getData());
                Log.d(LOG_MSG_REC_STR, rec_str);
                InetAddress ipaddress = recv_packet.getAddress();
                int port = recv_packet.getPort();
                Log.d(LOG_MSG_ADRESS, ipaddress.toString());
                Log.d(LOG_MSG_PORT, Integer.toString(port));

                if (!rec_str.isEmpty()) {
                    publishProgress(rec_str);
                }

            }
        } catch (Exception e) {
            Log.e(LOG_MSG_UDP, LOG_MSG_ERROR, e);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        rec_interface.onUpdate(values[0]);

    }

    public void stop() {
        started = false;
        clientsocket.close();
    }

    public interface ReceiveInterface {
        void onUpdate(String value);
    }
}
