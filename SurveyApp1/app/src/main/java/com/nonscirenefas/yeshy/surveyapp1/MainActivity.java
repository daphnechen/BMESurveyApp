package com.nonscirenefas.yeshy.surveyapp1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference mDatabase;
    private ArrayList<Medication> medicationList = new ArrayList<>();
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "pls take ur meds!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        boolean alarmUp = (PendingIntent.getBroadcast(this, 0,
                new Intent("com.my.package.MY_UNIQUE_ACTION"),
                PendingIntent.FLAG_NO_CREATE) != null);

        if (alarmUp)
        {
            Log.d("myTag", "Alarm is already active");
        }


        mDatabase = FirebaseDatabase.getInstance().getReference();
        String UID = ((MyApplication) this.getApplication()).getUID();
        mDatabase.child("app").child("users").child(UID).child("medicine").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ((MyApplication) MainActivity.this.getApplication()).resetMedicationList();
                        ArrayList<String> onePerDay = new ArrayList<>();
                        ArrayList<String> twoPerDay = new ArrayList<>();
                        ArrayList<String> threePerDay = new ArrayList<>();
                        ArrayList<String> fourPerDay = new ArrayList<>();
                        // Get user value
                        Log.e("reading",dataSnapshot.toString());

                        Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator();
                        while (it.hasNext()) {
                            DataSnapshot medicine = (DataSnapshot) it.next();
                            Log.e("reading2", medicine.toString());
                            Log.e("reading3", medicine.getKey());
                            Log.e("reading4", medicine.child(medicine.getKey()).toString());
                            Medication med = medicine.getValue(Medication.class);
                            ((MyApplication) MainActivity.this.getApplication()).addMedication(med);

                            Log.e("reading5", med.getName() + " " + med.getDaysSupply() + " " + med.getFrequency());

                            if(med.getFrequency().equals("Daily")) {
                                onePerDay.add(med.getName());
                            } else if(med.getFrequency().equals("Twice daily")) {
                                twoPerDay.add(med.getName());
                            } else if(med.getFrequency().equals("Three times daily")) {
                                threePerDay.add(med.getName());
                            } else if(med.getFrequency().equals("Four times daily")) {
                                fourPerDay.add(med.getName());
                            }
                        }
                        //User user = dataSnapshot.getValue(User.class);

                        // ...
                        medicationList = ((MyApplication) MainActivity.this.getApplication()).getMedicationList();

                        String oneDay = Arrays.toString(onePerDay.toArray());
                        String twoDay = Arrays.toString(twoPerDay.toArray());
                        String threeDay = Arrays.toString(threePerDay.toArray());
                        String fourDay = Arrays.toString(fourPerDay.toArray());

                        SharedPreferences settings = getSharedPreferences("Medication", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("onePerDay", oneDay);
                        editor.putString("twoPerDay", twoDay);
                        editor.putString("threePerDay", threeDay);
                        editor.putString("fourPerDay", fourDay);
                        editor.commit();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });


        initializeMessagesList();
        startAlarm(this);
    }


    public void startAlarm(Context context) {
        Intent alarmIntent0 = new Intent(this, ReminderService.class);
        startService(alarmIntent0);  //**for testing the notification looks


    }

    private void updateMessagesList() {

    }

    private void initializeMessagesList(){
        ArrayList<String> messages = new ArrayList<>();
        String [] LifestylePositiveMessages = getResources().getStringArray(R.array.LifestylePositiveMessagesArray);
        String [] LifestyleNegativeMessages = getResources().getStringArray(R.array.LifestyleNegativeMessagesArray);
        int[] LifestyleAnswers = ((MyApplication) MainActivity.this.getApplication()).getLifestyleSurveyAnswers();

        int counter = 0;
        for(int e: LifestyleAnswers) {
            if(e == 0) { //if right
                messages.add(LifestylePositiveMessages[counter]);
            } else if(e == 1) { //if wrong
                messages.add(LifestyleNegativeMessages[counter]);
            }
            counter++;
        }

        //Calendar calendar = Calendar.getInstance();
        //long day = (long) calendar.get(Calendar.DAY_OF_YEAR);
        Random generator = new Random();
        int num = generator.nextInt(messages.size());
        //Log.e("num",Integer.toString(num));

        //String [] mArrayBefore = new String[messages.size()];
        //mArrayBefore = messages.toArray(mArrayBefore);

        String [] mArray = {"Messages will appear here once the survey data has loaded."};
        if(num < messages.size())
            mArray[0] = messages.get(num);

        final ListView lv = (ListView) findViewById(R.id.messagesListView);
        lv.setAdapter(new MedicationAdapter(MainActivity.this, mArray));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            //finish();
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bloodpressure) {
            Intent i = new Intent(this, BloodPressureActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_medication) {
            Intent i = new Intent(this, MedicationActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_surveys) {
            Intent i = new Intent(this, SurveySelectionActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_callmypharmacist) {
            Intent i = new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:6783600636"));
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            String UIDstored = settings.getString("UID", "Default");
            Log.e("logout", UIDstored);

            SharedPreferences.Editor editor = settings.edit();
            editor.putString("UID", "Default");
            editor.commit();

            UIDstored = settings.getString("UID", "Default");
            Log.e("logout", UIDstored);
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
