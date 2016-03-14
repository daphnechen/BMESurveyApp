package codepath.apps.demointroandroid;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

public class IntentWithResultActivity extends Activity {
	
	final static int GET_RESULT_TEXT = 0;
    public ListView listview;
    public TextView textview;
    public String questionArr [];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
		setContentView(R.layout.activity_intent_with_result);
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
                ((MyApplication) IntentWithResultActivity.this.getApplication()).setQuestionArr(questionArr);
                listview.setAdapter(new yourAdapter(IntentWithResultActivity.this, questionArr));
                textview.setText("Survey 1: " + Integer.toString(childrenCount) + " questions");
                Log.d("hey1111", Integer.toString(childrenCount));
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("bitchdidn'twork", "The read failed: " + firebaseError.getMessage());
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_intent_with_result, menu);
		return true;
	}
	
	public void enterText(View v) {
        if(((MyApplication) IntentWithResultActivity.this.getApplication()).getUID() == null) {
            Intent intent = new Intent(IntentWithResultActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent myIntent = new Intent(IntentWithResultActivity.this, SimpleReturnResultActivity.class);
            TextView t = (TextView) v.findViewById(R.id.text);
            String hello = t.getText().toString();
            myIntent.putExtra("firstKeyName", hello);
            myIntent.putExtra("secondKeyName", "SecondKeyValue");
            startActivityForResult(myIntent, GET_RESULT_TEXT);
        }
	}
    public void submit(View v) {
        if(((MyApplication) IntentWithResultActivity.this.getApplication()).getUID() == null) {
            Intent intent = new Intent(IntentWithResultActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent myIntent = new Intent(IntentWithResultActivity.this, ConsentFormActivity.class);
            String hello = "CONSENT FORM";
            myIntent.putExtra("firstKeyName", hello);
            myIntent.putExtra("secondKeyName", "SecondKeyValue");
            startActivityForResult(myIntent, GET_RESULT_TEXT);
        }
    }

    public void ifNotLoggedIn() {

    }
	// Handle the result once the activity returns a result, display contact
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				TextView tvResult = (TextView)findViewById(R.id.txtDisplayResult);
				tvResult.setText(data.getStringExtra("result"));
				Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
                ProgressBar mProgress = (ProgressBar) findViewById(R.id.progressBar);
                int max = ((MyApplication) IntentWithResultActivity.this.getApplication()).getQuestionArr().length;
                mProgress.setMax(max);
                mProgress.setProgress(((MyApplication) IntentWithResultActivity.this.getApplication()).getQuestionsAnswered());
                listview.setAdapter(new yourAdapter(IntentWithResultActivity.this, questionArr,
                        ((MyApplication) IntentWithResultActivity.this.getApplication()).getCount()));
            }
		}
	}



}
