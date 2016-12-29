package com.foolishserver.remindmewhere;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button b_get;
    private GPSTracker gps;
    double longitude;
    double latitude;
    private TextView tlat;
    private TextView tlong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b_get = (Button)findViewById(R.id.get);
        tlat = (TextView)findViewById(R.id.show_latitude);
        tlong = (TextView)findViewById(R.id.show_longitude);



        b_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gps = new GPSTracker(MainActivity.this);


                if(gps.canGetLocation()){


                    longitude = gps.getLongitude();
                    latitude = gps .getLatitude();

                    String longstring = Double.toString(longitude);
                    String latstring = Double.toString(latitude);

                    tlat.setText(latstring);
                    tlong.setText(longstring);

                }
                else
                {
                    tlat.setText("Sorry no latitude");
                    tlong.setText("Sorry no longitude");
                }

            }
        });
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        gps.stopUsingGPS();
    }
   */ @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
    }

}