package be.migalski.bbdcry.babydontcry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Arrays;


public class theQuize extends AppCompatActivity {

    int numberOfQuestions = 5;
    int score = 0;
    boolean[] answers = new boolean[numberOfQuestions];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_quize);
    }

    /**
     * Check question and update score
     * @param  question Number of question
     * @param answer The correct answer
     * @param type type of question has to be radio, checkbox, number of text
     *
     */
    public void checkQuestion(int question, String answer, String type){

        boolean answeredCorrectly = true;
        int resID;

        switch (type){

            case "radio":

                answeredCorrectly = false;
                RadioButton correctRadioButton;
                RadioGroup radioButtonGroup;
                int answerId = Integer.parseInt(answer);

                //Check if correct options is chosen
                String correctRadioButtonId = "Q"+question+"O"+(answerId+1);
                resID = getResources().getIdentifier(correctRadioButtonId, "id", getPackageName());
                correctRadioButton = (RadioButton) findViewById(resID);

                if(correctRadioButton.isChecked()){
                    answeredCorrectly = true;
                }

                // If correct answer update score
                if(answeredCorrectly){
                    score++;
                }

                // Check if questions is answered.
                String radioButtonGroupId = "Q"+question+"RadioButtonGroup";
                resID = getResources().getIdentifier(radioButtonGroupId, "id", getPackageName());
                radioButtonGroup = (RadioGroup) findViewById(resID);

                if(radioButtonGroup.getCheckedRadioButtonId() != -1) {
                    answers[question - 1] = true;
                }

                break;

            case "checkbox":

                int numberOfOptions = 4;
                boolean[] options = new boolean[numberOfOptions];
                String[] answersArray = answer.split(",");

                // Get checked options
                for( int i = 0; i < numberOfOptions; i++){

                    CheckBox option;

                    String optionId = "Q"+question+"O"+(i+1);
                    resID = getResources().getIdentifier(optionId, "id", getPackageName());
                    option = (CheckBox) findViewById(resID);
                    options[i] = option.isChecked();

                    // Check if option is correct
                    if(Arrays.asList(answersArray).contains(Integer.toString(i))){
                        if(!options[i]){
                            answeredCorrectly = false;
                        }
                    }else{
                        if(options[i]){
                            answeredCorrectly = false;
                        }
                    }

                }

                // If correct answer update score
                if(answeredCorrectly == true){
                    score++;
                }

                // Check if questions is answered.
                // Checkbox questions are always answered (No answer is also valid).
                answers[question-1] = true;

                break;

            case "number":

                answeredCorrectly = false;
                EditText EditTextField;

                //Check if correct options is chosen
                String EditTextId = "Q"+question+"AnswerField";
                resID = getResources().getIdentifier(EditTextId, "id", getPackageName());
                EditTextField = (EditText) findViewById(resID);

                String submittedAnswer = EditTextField.getText().toString();
                if(!submittedAnswer.matches("")) {
                    if (Integer.parseInt(submittedAnswer) == Integer.parseInt(answer)) {
                        answeredCorrectly = true;
                    }
                }

                // If correct answer update score
                if(answeredCorrectly){
                    score++;
                }

                // Check if questions is answered.
                if(EditTextField.getText().toString() == ""){
                    answers[question-1] = false;
                }else{
                    answers[question-1] = true;
                }

                break;

            case "text":

                answeredCorrectly = false;
                EditText EditTextFieldText;

                //Check if correct options is chosen
                String EditTextIdText = "Q"+question+"AnswerField";
                resID = getResources().getIdentifier(EditTextIdText, "id", getPackageName());
                EditTextFieldText = (EditText) findViewById(resID);

                if(EditTextFieldText.getText().toString() == answer){
                    answeredCorrectly = true;
                }

                // If correct answer update score
                if(answeredCorrectly){
                    score++;
                }

                // Check if questions is answered.
                if(EditTextFieldText.getText().toString() == ""){
                    answers[question-1] = false;
                }else{
                    answers[question-1] = true;
                }


                break;

            default:
                displayToast(getResources().getString(R.string.error_NoValidQuestionType) + question);

        }

    }


    /**
     * Submit answers
     */
    public void submit(View view){

        checkQuestion(1,"0", "radio");
        checkQuestion(2,"1", "radio");
        checkQuestion(3,"8", "number");
        checkQuestion(4,"1", "radio");
        checkQuestion(5,"1,3", "checkbox");

        displayToast("Your score is: " + score);

        if(checkIfAllQuestionsHaveAnswers()){
            Intent i = new Intent(this, theScore.class);
            i.putExtra("score",score);
            i.putExtra("numberOfQuestions",numberOfQuestions);
            startActivity(i);
        }else{
            displayToast(getResources().getString(R.string.error_pleaseFillInAllQuestion));
        }

        score = 0;

    }

    /**
     * Display a toast message
     * @param msg Message to display
     */

    private void displayToast(String msg) {
        Toast toastMsg = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toastMsg.show();
    }


    /**
     * Check if all questions have answers
     * @return true if all questions have answers
     */
    private boolean checkIfAllQuestionsHaveAnswers() {

        for (boolean answer : answers) {
            if (answer == false) {
                return false;
            }
        }
        return true;
    }
}
