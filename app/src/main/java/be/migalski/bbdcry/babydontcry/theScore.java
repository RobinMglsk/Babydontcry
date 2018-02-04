package be.migalski.bbdcry.babydontcry;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;

public class theScore extends AppCompatActivity {

    int score;
    int numberOfQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_score);

        Intent intent = getIntent();
        score = intent.getExtras().getInt("score");
        numberOfQuestions = intent.getExtras().getInt("numberOfQuestions");

        TextView scoreTextField = (TextView) findViewById(R.id.score);
        scoreTextField.setText(score + "/" + numberOfQuestions);

        TextView msgTextField = (TextView) findViewById(R.id.score_text);


        if(score != numberOfQuestions){
            msgTextField.setText(getResources().getString(R.string.msg_failed));
            startSleepWithBabySimulator();
        }else{
            msgTextField.setText(getResources().getString(R.string.msg_success));
        }
    }

    private void startSleepWithBabySimulator(){

        int hour = ThreadLocalRandom.current().nextInt(0,6+1);
        int minutes = ThreadLocalRandom.current().nextInt(0,59+1);

        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, getResources().getString(R.string.bbsim_msg))
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                .putExtra(AlarmClock.EXTRA_RINGTONE,"android.resource://be.migalski.bbdcry.babydontcry/raw/bbsim")
                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

}
