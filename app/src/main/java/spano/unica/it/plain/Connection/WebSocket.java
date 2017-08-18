package spano.unica.it.plain.Connection;

import android.util.Log;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import spano.unica.it.plain.Beacons.BeaconID;
import spano.unica.it.plain.Beacons.Circle;
import spano.unica.it.plain.Beacons.CircleCircleIntersection;
import spano.unica.it.plain.Beacons.Vector2;
import spano.unica.it.plain.Nearables.AulaManager;
import spano.unica.it.plain.Nearables.Banco;
import spano.unica.it.plain.Nearables.NearableID;
import spano.unica.it.plain.School.Room;
import spano.unica.it.plain.School.Student;

import static com.estimote.coresdk.observation.region.RegionUtils.computeAccuracy;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by giovy on 15/08/2017.
 */

public class WebSocket {

    private WebSocketClient mWebSocketClient;


    //192.168.1.145/PlainWeb/endpoint
    public void connectWebSocket() {
        URI uri;
        try {
            //uri = new URI("ws://echo.websocket.org");
            uri = new URI("ws://192.168.1.143:8080/PlainWeb/endpoint");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri, new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                System.out.println("APERTOOOOOOOOOOO!!!");
                //mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
                //mWebSocketClient.send("ciao e basta");
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //TextView textView = (TextView)findViewById(R.id.message);
                        //textView.setText(textView.getText() + "\n" + message);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }

    private void runOnUiThread(Runnable runnable) {
    }
}
