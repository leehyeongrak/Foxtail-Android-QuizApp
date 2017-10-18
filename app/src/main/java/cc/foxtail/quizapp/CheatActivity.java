package cc.foxtail.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by rak on 2017. 10. 14..
 */

public class CheatActivity extends AppCompatActivity {

    private boolean isAnswerTrue;
    private boolean isShown;

    private TextView cheatAnswerText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        cheatAnswerText = (TextView) findViewById(R.id.cheatAnswerText);

        Button showAnswerButton = (Button) findViewById(R.id.showAnswerButton);

        isAnswerTrue = getIntent().getBooleanExtra("ANSWER", false);
        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnswerText();

                isShown = true;
                setShownResult();
            }
        });


        if (savedInstanceState != null) {
            isShown = savedInstanceState.getBoolean("ISSHOWN", false);
        }
        if (isShown){
            setAnswerText();
        }
        setShownResult();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("ISSHOWN", isShown);
    }

    private void setShownResult(){
        Intent intent = new Intent();
        intent.putExtra("SHOWN", isShown);
        setResult(RESULT_OK, intent);
    }

    private void setAnswerText(){
        if(isAnswerTrue){
            cheatAnswerText.setText(R.string.text_true);
        }else{
            cheatAnswerText.setText(R.string.text_false);
        }
    }

}
