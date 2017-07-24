package spano.unica.it.plain.Nearables;

import android.content.Context;
import android.util.Log;

import com.estimote.coresdk.recognition.internal.module.RecognitionModule;
import com.estimote.coresdk.recognition.packets.Nearable;
import com.estimote.coresdk.service.BeaconManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by giovy on 16/05/2017.
 */

public class AulaManager {
    private Listener listener;

    private BeaconManager beaconManager;
    private String scanId;

    //private Map<NearableID, Boolean> nearablesMotionStatus = new HashMap<>();

    public AulaManager(Context context, final Map<NearableID, Banco> banchi) {
        beaconManager = new BeaconManager(context);
        beaconManager.setNearableListener(new BeaconManager.NearableListener() {
            @Override
            public void onNearablesDiscovered(List<Nearable> list) {
                for (Nearable nearable : list) {
                    NearableID nearableID = new NearableID(nearable.identifier);
                    System.out.println(nearable.identifier + " " + nearable.color + " " + nearable.rssi);
                    //System.out.println(nearable.color.toString() + " " + nearable.identifier + " " +  nearable.isMoving);
                    if (!banchi.keySet().contains(nearableID)) { continue; }

                    /*boolean previousStatus = nearablesMotionStatus.containsKey(nearableID) && nearablesMotionStatus.get(nearableID);
                    if (previousStatus != nearable.isMoving) {*/
                    Banco banco = banchi.get(nearableID);
                       /*if (!nearable.isMoving) {
                            listener.nearableNontrovato(banco);
                        } else {*/
                    listener.nearableTrovato(banco, nearableID);

                    // }
                    // nearablesMotionStatus.put(nearableID, nearable.isMoving);
                    //}
                }
            }
        });
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void nearableTrovato(Banco banco, NearableID nearable);
        void nearableNontrovato(Banco banco);
    }

    public void startUpdates() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startNearableDiscovery();
            }
        });
    }

    public void stopUpdates() {
        beaconManager.stopNearableDiscovery();
    }

    public void destroy() {
        beaconManager.disconnect();
    }
}
