package spano.unica.it.plain;

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




public class MainActivity extends AppCompatActivity {
    private final long SCANTIME = 2000;
    private final long WAITTIME = 1000;
    private static final String TAG = "MainActivity";
    private AulaManager aulamanager;
    private WebSocketClient mWebSocketClient;
    private Student student = new Student();
    private Room room = new Room();
    private static int i = 0;
    private BeaconManager beaconManager;
    private BeaconID beaconID1 = new BeaconID();
    private BeaconID beaconID2 = new BeaconID();
    private BeaconID beaconID3 = new BeaconID();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        student.setName("Filippo");
        student.setSurname("Fanni");
        student.setId(24);
        student.getStickerList().add("B");
        room.setLatoZero(5.0);
        room.setLatoUno(3.0);
        System.out.println(room.getArea());



        beaconManager = new BeaconManager(getApplicationContext());



        //mWebSocketClient.onMessage("Ciao Marteena");

        /*if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/


        /*Button doveSOno = (Button) findViewById(R.id.sonoQui);
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


        });*/
        EstimoteSDK.initialize(getApplicationContext(), "plain-e3d", "4180e1971192b9fc330cb6210dc79916");


        Button scanNearable = (Button) findViewById(R.id.scanNearable);
        scanNearable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("button scan nearable");

                Map<NearableID, Banco> banchi = new HashMap<>();
                NearableID nearable1 = new NearableID("283913311736b9e9");
                Banco banco1 = new Banco("Banco n°1");

                banchi.put(nearable1, banco1);
                aulamanager = new AulaManager(getApplicationContext(), banchi);
                aulamanager.startUpdates();
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
            }


        });


        Button scanBeacon = (Button) findViewById(R.id.scanBeacon);
        scanBeacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("button scan beacon");
                beaconManager = new BeaconManager(getApplicationContext());

                /* Questa parte controlla e verifica se ci sono beacon nella zona
                 * quando ne trova uno o più li mette in una lista che contiene tutte
                 * le informazionidei beacon
                 */
                beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
                    @Override
                    public void onBeaconsDiscovered(BeaconRegion region, List<Beacon> list) {
                        double kFilteringFactor = 0.1;
                        int major = 0;
                        double distance = 0.0;

                        Vector2 beacon1Position = new Vector2(0.0,1.2);
                        Vector2 beacon2Position = new Vector2(1.25,0.35);
                        Vector2 beacon3Position = new Vector2(3.4,1.7);
                        Vector2[] intersectionPoints1 = new Vector2[2];
                        Vector2[] intersectionPoints2 = new Vector2[2];
                        Vector2[] intersectionPoints3 = new Vector2[2];
                        Circle circle1 = new Circle(beacon1Position,1.0);
                        Circle circle2 = new Circle(beacon2Position,1.0);
                        Circle circle3 = new Circle(beacon3Position,1.0);


                        double distanceMajor20426=0.0;
                        double distanceMajor37227=0.0;
                        double distanceMajor20657=0.0;



                        for (Beacon beacon : list) {

                            major=beacon.getMajor();
                            //distance = computeAccuracy(beacon);
                            System.out.println(list.size());




                            switch (major){
                                case 61780:
                                    //distanceMajor20426=calculateAccuracyRSSi(beacon.getRssi());
                                    distanceMajor20426=computeAccuracy(beacon);
                                    distanceMajor20426=Math.floor(distanceMajor20426*100)/100;
                                    circle1.setC(beacon1Position);
                                    circle1.setR(distanceMajor20426);

                                System.out.println("a: " + distanceMajor20426);
                                    break;

                                case 37227:
                                    //distanceMajor37227=calculateAccuracyRSSi(beacon.getRssi());
                                    distanceMajor37227=computeAccuracy(beacon);
                                    distanceMajor37227=Math.floor(distanceMajor37227*100)/100;
                                    System.out.println("b: "+ distanceMajor37227);
                                    circle2.setC(beacon2Position);
                                    circle2.setR(distanceMajor37227);

                                    break;
                                case 34833:
                                    //distanceMajor20657=calculateAccuracyRSSi(beacon.getRssi());
                                    distanceMajor20657=computeAccuracy(beacon);
                                    distanceMajor20657=Math.floor(distanceMajor20657*100)/100;
                                    System.out.println("c: "+ distanceMajor20657);
                                    circle3.setC(beacon3Position);
                                    circle3.setR(distanceMajor20657);

                                    break;
                            }

                            beaconManager.setForegroundScanPeriod(SCANTIME, WAITTIME);

                            System.out.println("major: " + beacon.getMajor() + " minor: " + beacon.getMinor() + " RSSI: " + beacon.getRssi());

                        }


                        CircleCircleIntersection intersection1= new CircleCircleIntersection(circle1,circle2);
                        intersectionPoints1=intersection1.getIntersectionPoints();
                        if (intersectionPoints1.length==0) System.out.println("nessuna intersezione 1");
                        for (int i = 0; i < intersectionPoints1.length; i++) {
                            System.out.println("1: " + intersectionPoints1[i] + " ");
                        }
                        CircleCircleIntersection intersection2= new CircleCircleIntersection(circle1,circle3);
                        intersectionPoints2=intersection2.getIntersectionPoints();
                        if (intersectionPoints2.length==0) System.out.println("nessuna intersezione 2");
                        for (int i = 0; i < intersectionPoints2.length; i++) {
                            System.out.println("2: " + intersectionPoints2[i] + " ");
                        }
                        CircleCircleIntersection intersection3= new CircleCircleIntersection(circle3,circle2);
                        intersectionPoints3=intersection3.getIntersectionPoints();
                        if (intersectionPoints3.length==0) System.out.println("nessuna intersezione 3");
                        for (int i = 0; i < intersectionPoints3.length; i++) {
                            System.out.println("3:" + intersectionPoints3[i] + " ");
                        }



                    }

                });

                beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
                    @Override
                    public void onServiceReady() {
                        beaconManager.startRanging(new BeaconRegion(
                                "StanzaDueBatcave",
                                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                                null, null));
                        System.out.println("cercandone beacon");
                    }
                });


                /* Questa parte MONITORA e verifica se ci sono beacon nella zona
                 * appena ne trova uno, invia una notifica e continua a controllare
                 * se si esce dal range dei beacon
                 */
                /*beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
                    public void showNotification(String title, String message) {
                        System.out.println("mostrando la notifica");
                        Intent notifyIntent = new Intent(getApplicationContext(), MainActivity.class);
                        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        PendingIntent pendingIntent = PendingIntent.getActivities(getApplicationContext(), 0,
                                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
                        Notification notification = new Notification.Builder(getApplicationContext())
                                .setSmallIcon(android.R.drawable.ic_dialog_info)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent)
                                .build();
                        notification.defaults |= Notification.DEFAULT_SOUND;
                        notification.defaults |= Notification.DEFAULT_VIBRATE;
                        NotificationManager notificationManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(1, notification);
                    }


                    @Override
                    public void onEnteredRegion(BeaconRegion region, List<BeaconID> list) {
                        System.out.println("entrato nella regione");
                        for (BeaconID beacon : list) {

                            System.out.println(beacon.getProximityUUID()+ " " + beacon.getUniqueKey() + " " + beacon.getMajor() + " " + beacon.getMinor() + " " + beacon.getRssi());

                        }
                        showNotification(
                                "Buongiorno Djanni!",
                                "Bentornato in batcaverna");


                    }
                    @Override
                    public void onExitedRegion(BeaconRegion region) {
                        showNotification(
                                "Arrivederci Djanni!",
                                "Torna presto a trovarci");
                    }
                });



                beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
                    @Override
                    public void onServiceReady() {
                        beaconManager.startMonitoring(new BeaconRegion(
                                "StanzaDueBatcave",
                                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                                null, null));
                        System.out.println("cercandone beacon");
                    }
                });*/


                /* modifica il tempo di attesa per una nuova scan*/
                //beaconManager.setForegroundScanPeriod(SCANTIME, WAITTIME);

            }


        });


        beaconManager.setForegroundScanPeriod(SCANTIME, WAITTIME);




        /*aulamanager.setListener(new AulaManager.Listener(){
            @Override
            public void nerableTrovati(Banco banco, NearableID nearable) {
                ((TextView) findViewById(R.id.titolo1)).setText(banco.getNome());
                //((TextView) findViewById(R.id.descriptionLabel)).setText(product.getSummary() + " beacon simbolo: " + nearable.getTipo() + "con id: " + nearable.getNearableIDString());
                //findViewById(R.id.descriptionLabel).setVisibility(View.VISIBLE);
            }

        });*/







        /*if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
    }


    public double calculateAccuracyRSSi(double rssi) {
        //formula adapted from David Young's Radius Networks Android iBeacon Code
        if (rssi == 0) {
            return -1.0; // if we cannot determine accuracy, return -1.
        }


        double txPower = -70;
        double ratio = rssi * 1.0 / txPower;
        if (ratio < 1.0) {
            return pow(ratio, 10);
        } else {
            double accuracy = (0.89976) * pow(ratio, 7.7095) + 0.111;
            return accuracy;
        }
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
            if (aulamanager == null)
                System.out.println("ciao");
            else
                aulamanager.startUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Stopping ShowroomManager updates");
        if (aulamanager == null)
            System.out.println("ciao");
        else
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




   /* public void sendMessage(View view) {
        EditText editText = (EditText)findViewById(R.id.message);
        mWebSocketClient.send("ciao marteena");
        editText.setText("");
    }*/
}
