package codepath.apps.demointroandroid;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yeshy on 3/8/2016.
 */
public class LoginActivity extends Activity{
    public boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this); //this needs to be done for all activities that use firebase.
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_intent_with_result, menu);
        return true;
    }

    public void startMain(View v) {
        Firebase ref = new Firebase("https://healthsurvey1.firebaseio.com/");
        AutoCompleteTextView mEdit = (AutoCompleteTextView)findViewById(R.id.email);
        EditText eEdit = (EditText)findViewById(R.id.password);

        String email = mEdit.getText().toString();
        String password = eEdit.getText().toString();
        Log.d("SOMETHING", email);
        Log.d("SOMETHINGELSE", password);

        register(email, password, ref);
        login(email, password, ref);
        

    }

    public void register(String email, String password, Firebase ref) {
        ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                System.out.println("Successfully created user account with uid: " + result.get("uid"));
                Log.d("works", "hey");
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error
                Log.d("doesnt", "hey");

            }
        });
    }

    public boolean login(String email, String password, Firebase ref1) {

        final Firebase ref = ref1; //final is necessary for use inside authWithPassword method.
        ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("provider", authData.getProvider());
                if(authData.getProviderData().containsKey("displayName")) {
                    map.put("displayName", authData.getProviderData().get("displayName").toString());
                }
                ref.child("users").child(authData.getUid()).setValue(map);
                ((MyApplication) LoginActivity.this.getApplication()).setUID(authData.getUid());

                Intent i = new Intent(LoginActivity.this, IntentWithResultActivity.class);
                startActivity(i);
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
                check = false;
            }
        });
        return check;
    }

}
