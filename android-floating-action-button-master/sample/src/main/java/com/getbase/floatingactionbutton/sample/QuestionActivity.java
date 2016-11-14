package com.getbase.floatingactionbutton.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

/**
 * Created by Yeshy on 4/11/2016.
 */
public class QuestionActivity extends Activity {
        private String question;
        private TextView textview;
        private final Firebase ref = new Firebase("https://healthsurvey1.firebaseio.com/survey");

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            Intent myIntent = getIntent(); // gets the previously created intent
            Firebase.setAndroidContext(this);
            String firstKeyName = myIntent.getStringExtra("firstKeyName");
            question = firstKeyName;
            String secondKeyName = myIntent.getStringExtra("secondKeyName");
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_question);

            textview = (TextView) findViewById(R.id.textView);
            textview.setText(firstKeyName);
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            int width = dm.widthPixels;
            int height = dm.heightPixels;

            getWindow().setLayout((int)(width*.8),(int)(height*.55));




        }

    /*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_simple_return_result, menu);
		return true;
	}*/

        public void sendResult(View v) {
            String answer = ((EditText) findViewById(R.id.txtRandomResultText)).getText().toString();
            String uid = ((MyApplication) this.getApplication()).getUID(); //to get uid.
            //ref.child("answers").child(uid).child(question).setValue(result);

            if(question.equalsIgnoreCase("CONSENT FORM")) {
                int questionNum = ((MyApplication) this.getApplication()).getQuestionNum();
                int answerNum = ((MyApplication) this.getApplication()).getAnswerNum();
                String[] questions = ((MyApplication) this.getApplication()).getQuestions();
                String[] answers = ((MyApplication) this.getApplication()).getAnswers();
                for(int i = 0; i < questionNum; i++) {
                    ref.child("answers").child(uid).child(questions[i]).setValue(answers[i]);
                    Log.d(questions[i],answers[i]);
                }
                Log.d("heyyoooo","hella1111");
            } else {
                ((MyApplication) this.getApplication()).setQuestions(question.substring(0,question.length()-1));
                ((MyApplication) this.getApplication()).setAnswers(answer);
                Log.d("heyyoooo","hella");
            }
            Intent i = new Intent();
            i.putExtra("result", "Answer stored.");
            setResult(RESULT_OK, i);
            finish();
        }

}
