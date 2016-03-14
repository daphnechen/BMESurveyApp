package codepath.apps.demointroandroid;

import android.app.Application;

/**
 * Created by Yeshy on 3/11/2016.
 */
public class MyApplication extends Application{
    private String uid;
    private String[] questionArr;
    private String[] questions = new String[10];
    private int questionNum = 0;
    private String[] answers = new String[10];
    private int answerNum = 0;
    private int[] count;
    public int[] getCount() {
        return count;
    }
    public String[] getQuestionArr() {
        return questionArr;
    }
    public int getQuestionsAnswered() {
        int sum = 0;
        int[] count = new int[questionArr.length];
        for(int i = 0; i < questionArr.length; i++) {
            for(int j = 0; j < questionNum; j++) {
                if(questions[j].equalsIgnoreCase(questionArr[i].substring(0,questionArr[i].length()-1))) {
                    if(count[i] == 0) {
                        count[i] = 1;
                    }
                }
            }
            sum = sum + count[i];
        }
        this.count = count;
        return sum;
    }
    public void setQuestionArr(String[] questionArr) {
        this.questionArr = questionArr;
    }

    public String[] getQuestions() {
        return questions;
    }
    public int getQuestionNum() {
        return questionNum;
    }
    public void setQuestions(String question) {
        if(questionNum >= questions.length) {
            String[] questions1 = new String[questions.length*2];
            for(int i = 0; i < questionNum; i++) {
                questions1[i] = questions[i];
                questions = questions1;
            }
        }
        questions[questionNum] = question;
        questionNum++;
    }

    public String[] getAnswers() {
        return answers;
    }
    public int getAnswerNum() {
        return answerNum;
    }

    public void setAnswers(String answer) {
        if(answerNum >= answers.length) {
            String[] answers1 = new String[answers.length*2];
            for(int i = 0; i < answerNum; i++) {
                answers1[i] = answers[i];
                answers = answers1;
            }
        }
        answers[answerNum] = answer;
        answerNum++;
    }

    public String getUID() {
        return uid;
    }

    public void setUID(String uid) {
        this.uid = uid;
    }
}
