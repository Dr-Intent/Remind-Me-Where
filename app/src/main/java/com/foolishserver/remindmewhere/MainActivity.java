package com.foolishserver.remindmewhere;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.foolishserver.remindmewhere.R.id.get;


public class MainActivity extends AppCompatActivity {

    private Button b_get;
    private Button b_signout;
    private GPSTracker gps;
    double longitude;
    double latitude;
    private TextView tlat;
    private TextView tlong;
    private DatabaseReference mDatabase;
    private FirebaseUser mFirebaseUser;
    final Context context = this;

    private String mUserId;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();


        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            mUserId = mFirebaseUser.getUid();


            b_get = (Button) findViewById(get);
            tlat = (TextView) findViewById(R.id.show_latitude);
            tlong = (TextView) findViewById(R.id.show_longitude);
            mDatabase = FirebaseDatabase.getInstance().getReference();
            b_signout = (Button) findViewById(R.id.sign_out);


            //Get Location and send to server
            b_get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    gps = new GPSTracker(MainActivity.this);


                    if (gps.canGetLocation()) {


                        longitude = gps.getLongitude();
                        latitude = gps.getLatitude();

                        String longstring = Double.toString(longitude);
                        String latstring = Double.toString(latitude);


                        tlat.setText(latstring);
                        tlong.setText(longstring);

                    } else {
                        tlat.setText("Sorry no latitude");
                        tlong.setText("Sorry no longitude");
                    }


                    mDatabase.child(mUserId).child("Longitude").setValue(longitude);
                    mDatabase.child(mUserId).child("Latitude").setValue(latitude);
                    mDatabase.child(mUserId).child("Time").setValue(System.currentTimeMillis());
                    mDatabase.child(mUserId).child("Email").setValue(mFirebaseUser.getEmail());
                }
            });
        }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        gps.stopUsingGPS();
    }
   */
    }
    private void loadLogInView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    public void signOut(View view){
        Intent intent = new Intent (this, GoogleSignIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }



}