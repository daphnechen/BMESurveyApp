package codepath.apps.demointroandroid;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class IntentWithResultActivity extends Activity {
	
	final static int GET_RESULT_TEXT = 0;
    ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intent_with_result);
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(new yourAdapter(this, new String[] { "Do you have diabetes?",
                "Are you on medication for diabetes?", "How many hours of exercise " +
                "(not including daily walking) did you get last week",
        "Has anyone else had diabetes in your family?",
        "Do you have any other medical conditions?"}));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_intent_with_result, menu);
		return true;
	}
	
	public void enterText(View v) {
		startActivityForResult(
				  new Intent(IntentWithResultActivity.this, SimpleReturnResultActivity.class), 
				    GET_RESULT_TEXT);
	}
	
	// Handle the result once the activity returns a result, display contact
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				TextView tvResult = (TextView)findViewById(R.id.txtDisplayResult);
				tvResult.setText(data.getStringExtra("result"));
				Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
			}
		}
	}

}
