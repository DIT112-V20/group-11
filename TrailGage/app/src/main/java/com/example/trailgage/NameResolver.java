package com.example.trailgage;
import androidx.appcompat.app.AppCompatActivity;

import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
public class NameResolver extends AppCompatActivity  {
    private String TAG = "trailgage";
    NsdManager mNsdManager;

    LocalNetworkDeviceNameResolver mDeviceNameResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Don't use the same device name for multiple instances as they overwrite each other
        // Asynchronous device name resolution (suggested)
        mDeviceNameResolver =
                new LocalNetworkDeviceNameResolver(this.getApplicationContext(),
                        TAG, "_http._tcp.", 80,
                        new LocalNetworkDeviceNameResolver.AddressResolutionListener() {
                            @Override
                            public void onAddressResolved(InetAddress address) {
                                Log.i("trailgage", "Asynchronous IP resolution: " + address.getHostName
                                        ());
                            }
                        });

    }

}
