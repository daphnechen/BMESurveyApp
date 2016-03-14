package codepath.apps.demointroandroid;

/**
 * Created by Yeshy on 3/9/2016.
 */
public class surveyquestions {
    private String numQuestions;
    private String question;
    public surveyquestions() {
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }
    public String getNumQuestionsuestions() {
        return numQuestions;
    }
    public String getQuestion() {
        return question;
    }
}
