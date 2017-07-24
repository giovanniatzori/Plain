package spano.unica.it.plain;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import spano.unica.it.plain.Nearables.NearableID;
import spano.unica.it.plain.Nearables.Banco;
import spano.unica.it.plain.Nearables.AulaManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private final long SCANTIME = 2000;
    private final long WAITTIME = 1000;
    private static final String TAG = "MainActivity";
    private AulaManager aulamanager;
    private WebSocketClient mWebSocketClient;
    private Student student = new Student();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        student.setName("Filippo");
        student.setSurname("Fanni");
        student.setId(24);
        student.getStickerList().add("B");
        connectWebSocket();
        //mWebSocketClient.onMessage("Ciao Marteena");

        /*if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
        Button doveSOno = (Button) findViewById(R.id.sonoQui);
        doveSOno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mettere l'invio del file geison
                ObjectMapper mapper = new ObjectMapper();
                //map del json
                try {
                    String msg = mapper.writeValueAsString(student);
                    mWebSocketClient.send(msg);
                    ((TextView) findViewById(R.id.descriptionLabel)).setText(" ho inviato");
                    ((TextView) findViewById(R.id.titleLabel)).setText(student.getName() + " " + student.getSurname());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            }


        });


        Map<NearableID, Banco> banchi = new HashMap<>();
        NearableID nearable1 = new NearableID("283913311736b9e9");
        Banco banco1 = new Banco("Banco n°1");

        banchi.put(nearable1, banco1);
        EstimoteSDK.initialize(getApplicationContext(), "plain-e3d", "4180e1971192b9fc330cb6210dc79916");

        aulamanager = new AulaManager(this, banchi);
        /*aulamanager.setListener(new AulaManager.Listener(){
            @Override
            public void nerableTrovati(Banco banco, NearableID nearable) {
                ((TextView) findViewById(R.id.titolo1)).setText(banco.getNome());
                //((TextView) findViewById(R.id.descriptionLabel)).setText(product.getSummary() + " beacon simbolo: " + nearable.getTipo() + "con id: " + nearable.getNearableIDString());
                //findViewById(R.id.descriptionLabel).setVisibility(View.VISIBLE);
            }

        });*/

        aulamanager.setListener(new AulaManager.Listener() {
            @Override
            public void nearableTrovato(Banco banco, NearableID nearable) {
                //((TextView) findViewById(R.id.titleLabel)).setText(banco.getNomeBanco());
                //((TextView) findViewById(R.id.descriptionLabel)).setText(" beacon con id: " + nearable.getNearableIDString());
                findViewById(R.id.descriptionLabel).setVisibility(View.VISIBLE);
            }

            @Override
            public void nearableNontrovato(Banco banco) {
                //((TextView) findViewById(R.id.titleLabel)).setText("Nessun nearable nelle vicinanze");
                //findViewById(R.id.descriptionLabel).setVisibility(View.INVISIBLE);
            }
        });

        //beaconManager.setForegroundScanPeriods(SCANTIME, WAITTIME);



        /*if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else {
            Log.d(TAG, "Start updates");
            aulamanager.startUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Stopping ShowroomManager updates");
        aulamanager.stopUpdates();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        aulamanager.destroy();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    /*public static class PlaceholderFragment extends android.app.Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }*/
    //192.168.1.145/PlainWeb/endpoint
    private void connectWebSocket() {
        URI uri;
        try {
            //uri = new URI("ws://echo.websocket.org");
            uri = new URI("ws://192.168.1.143:8080/PlainWeb/endpoint");
            System.out.println("Apertooo22222");
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

   /* public void sendMessage(View view) {
        EditText editText = (EditText)findViewById(R.id.message);
        mWebSocketClient.send("ciao marteena");
        editText.setText("");
    }*/
}
