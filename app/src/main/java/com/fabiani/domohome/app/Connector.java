package com.fabiani.domohome.app;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import java.io.IOException;
import java.net.InetAddress;

import android.net.wifi.WifiManager;
import android.widget.Toast;
import com.myhome.fcrisciani.connector.MyHomeJavaConnector;
import com.myhome.fcrisciani.exception.MalformedCommandOPEN;

//read "Effective Java" by Joshua Bloch, second edition,   item 14 on page 71

public class Connector {
    private static final String TAG = "Connector";
    public static final int PORT = 20000;
    public static boolean NullPointerExceptionCaught=false;
    static String sIp;
    static int sPort;
    private MyHomeJavaConnector mMyHomeJavaConnector;
    Connector() {
    }

    Connector(String ip, int port) {
        sIp = ip;
        sPort = port;
        mMyHomeJavaConnector = new MyHomeJavaConnector(ip, port);
    }

    public void sendCommand(Command c) throws MalformedCommandOPEN {
        try {
            mMyHomeJavaConnector.sendCommandSync("*" + c.getWho() + "*" + c.getWhat() + "*"
                    + c.getWhere() + "##").toString();
        }catch (NullPointerException e){
           NullPointerExceptionCaught=true;
        }
    }

    public static boolean isNetworkActiveConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnected();
    }
    /*not yet implemented
    public static boolean isHostReachable() throws IOException {
        InetAddress inetAddress;
        inetAddress=InetAddress.getByName(sIp);
        return  inetAddress.isReachable(10000);
    }*/
}