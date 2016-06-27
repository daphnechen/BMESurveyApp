package com.getbase.floatingactionbutton.sample;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import android.support.v7.app.ActionBarDrawerToggle;


public class MainActivity extends AppCompatActivity {



    private static String TAG = MainActivity.class.getSimpleName();
    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    final static int GET_RESULT_TEXT = 0;
    public ListView listview;
    public TextView textview;
    public String questionArr [];
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavItems.add(new NavItem("Home", "Main screen", R.drawable.ic_fab_star));
        mNavItems.add(new NavItem("Records", "Check your logs", R.drawable.ic_fab_star));
        mNavItems.add(new NavItem("Survey", "Answer questions", R.drawable.ic_fab_star));
        mNavItems.add(new NavItem("Log Out", "Sign out of account", R.drawable.ic_fab_star));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigation Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              selectItemFromDrawer(position);
          }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
              super.onDrawerOpened(drawerView);

              invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
              super.onDrawerClosed(drawerView);
              Log.d(TAG, "onDrawerClosed: " + getTitle());

              invalidateOptionsMenu();
            }
        };


          mDrawerLayout.addDrawerListener(mDrawerToggle);


        findViewById(R.id.action_a).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //open up the diabetes add menu
                //Toast.makeText(MainActivity.this, "Adding Glucose Log...", Toast.LENGTH_SHORT).show();
                Intent glucoseIntent = new Intent(MainActivity.this, GlucoseLogActivity.class);
                startActivity(glucoseIntent);
                final FloatingActionsMenu menuButton  = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
                menuButton.toggle();
            }
        });

        findViewById(R.id.action_b).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //open up the diabetes add menu
                //Toast.makeText(MainActivity.this, "Adding Blood Pressure Log...", Toast.LENGTH_SHORT).show();
                Intent BloodPressureIntent = new Intent(MainActivity.this, BloodPressureLogActivity.class);
                startActivity(BloodPressureIntent);
                final FloatingActionsMenu menuButton  = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
                menuButton.toggle();
            }
        });

        findViewById(R.id.action_c).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //open up the diabetes add menu
                //Toast.makeText(MainActivity.this, "Adding Cholesterol Log...", Toast.LENGTH_SHORT).show();
                Intent CholesterolIntent = new Intent(MainActivity.this, CholesterolLogActivity.class);
                startActivity(CholesterolIntent);
                final FloatingActionsMenu menuButton  = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
                menuButton.toggle();
            }
        });

        Fragment fragment = new PreferencesFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragment)
                .commit();

        startAlarm(this);
        //Intent intent = new Intent(MainActivity.this, SyncService.class);
        //startActivity(intent);
    }

    public void startAlarm(Context context) {
        Intent alarmIntent = new Intent(this, InviteService.class);
        startService(alarmIntent);  //**for testing the notification looks
        long scTime = 60* 1000 *60*24;// 1 day
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + scTime, pendingIntent);

    }
    /*
    * Called when a particular item from the navigation drawer
    * is selected.
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void selectItemFromDrawer(int position) {
        switch (position) {
            case 0: {
                Fragment fragment = new PreferencesFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContent, fragment)
                        .commit();
                break;
            }
            case 1: {
                Fragment fragment = new PreferencesFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContent, fragment)
                        .commit();
                break;
            }
            case 2: {
                Fragment fragment = new SurveyFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContent, fragment)
                        .addToBackStack("survey")
                        .commit();
                fragmentManager.executePendingTransactions();
                questionFiller();

                break;
            }
            case 3: {
                String PREFS_NAME = "MyPrefsFile";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("UID", "Default");
                editor.commit();

                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
            }
        }
        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).mTitle);

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    public void questionFiller() {
        listview = (ListView) findViewById(R.id.listview);
        textview = (TextView) findViewById(R.id.textView6);

        Firebase ref = new Firebase("https://healthsurvey1.firebaseio.com/survey");
// Attach an listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int childrenCount = (int)snapshot.child("questions").getChildrenCount();
                questionArr = new String[childrenCount];
                for(int i = 1; i <= childrenCount; i++){
                    Log.d("hey", snapshot.child("questions").child(Integer.toString(i)).getValue().toString());
                    questionArr[i-1] = snapshot.child("questions").child(Integer.toString(i)).getValue().toString();
                }
                ((MyApplication) MainActivity.this.getApplication()).setQuestionArr(questionArr);
                listview.setAdapter(new yourAdapter(MainActivity.this, questionArr));
                textview.setText("Survey 1: " + Integer.toString(childrenCount) + " questions");
                Log.d("hey1111", Integer.toString(childrenCount));
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("bitchdidn'twork", "The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void enterText(View v) {
        if(((MyApplication) MainActivity.this.getApplication()).getUID() == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent myIntent = new Intent(MainActivity.this, QuestionActivity.class);
            TextView t = (TextView) v.findViewById(R.id.text);
            String hello = t.getText().toString();
            myIntent.putExtra("firstKeyName", hello);
            myIntent.putExtra("secondKeyName", "SecondKeyValue");
            startActivityForResult(myIntent, GET_RESULT_TEXT);
        }
    }

    public void submit(View v) {
        if(((MyApplication) MainActivity.this.getApplication()).getUID() == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent myIntent = new Intent(MainActivity.this, ConsentFormActivity.class);
            String hello = "CONSENT FORM";
            myIntent.putExtra("firstKeyName", hello);
            myIntent.putExtra("secondKeyName", "SecondKeyValue");
            startActivityForResult(myIntent, GET_RESULT_TEXT);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                TextView tvResult = (TextView)findViewById(R.id.txtDisplayResult);
                tvResult.setText(data.getStringExtra("result"));
                Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
                ProgressBar mProgress = (ProgressBar) findViewById(R.id.progressBar);
                int max = ((MyApplication) MainActivity.this.getApplication()).getQuestionArr().length;
                mProgress.setMax(max);
                mProgress.setProgress(((MyApplication) MainActivity.this.getApplication()).getQuestionsAnswered());
                listview.setAdapter(new yourAdapter(MainActivity.this, questionArr,
                        ((MyApplication) MainActivity.this.getApplication()).getCount()));
            }
        }
    }

    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

    class DrawerListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            }
            else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);

            titleView.setText( mNavItems.get(position).mTitle );
            subtitleView.setText( mNavItems.get(position).mSubtitle );
            iconView.setImageResource(mNavItems.get(position).mIcon);

            return view;
        }
    }
}
