package codepath.apps.demointroandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SimpleReturnResultActivity extends Activity {

TextView textview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        Intent myIntent = getIntent(); // gets the previously created intent
        String firstKeyName = myIntent.getStringExtra("firstKeyName");
        String secondKeyName = myIntent.getStringExtra("secondKeyName");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_return_result);

        textview = (TextView) findViewById(R.id.textView);
        textview.setText(firstKeyName);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.7));


	}

    /*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_simple_return_result, menu);
		return true;
	}*/
	
	public void sendResult(View v) {
		String result = ((EditText) findViewById(R.id.txtRandomResultText)).getText().toString();
		Intent i = new Intent();
		i.putExtra("result", result);
		setResult(RESULT_OK, i);
		finish();
	}

}
