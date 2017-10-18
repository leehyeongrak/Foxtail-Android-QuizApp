package cc.foxtail.quizapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Quiz[] quizArray = new Quiz[]{
            new Quiz(R.string.question_likelion, true),
            new Quiz(R.string.question_foxtail, false),
            new Quiz(R.string.question_naver, true)
    };

    private int currentIndex = 0;
    private boolean isCheat = false;

    private TextView questionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = (TextView)findViewById(R.id.questionText);
        Button trueButton = (Button)findViewById(R.id.trueButton);
        Button falseButton = (Button)findViewById(R.id.falseButton);
        Button nextButton = (Button)findViewById(R.id.nextButton);
        Button preButton = (Button)findViewById(R.id.preButton);

        Button cheatButton = (Button) findViewById(R.id.cheatButton);


        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % quizArray.length;
                updateQuestion();
            }
        });

        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex - 1) % quizArray.length;

                if(currentIndex < 0){
                    currentIndex = quizArray.length + currentIndex;
                }
                updateQuestion();
            }
        });

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt("INDEX", 0);
            isCheat = savedInstanceState.getBoolean("ISCHEAT", false);
        }

        updateQuestion();

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheatActivity.class);
                intent.putExtra("ANSWER", quizArray[currentIndex].isAnswer());
                startActivityForResult(intent, 999);

            }
        });
    }

    private void updateQuestion(){
        questionText.setText(quizArray[currentIndex].getResourceId());
    }

    private void checkAnswer(boolean answer){

        if(isCheat){
            Toast.makeText(MainActivity.this, "컨닝쟁이는 답을 확인못해요~", Toast.LENGTH_SHORT).show();
        } else {
            if(quizArray[currentIndex].isAnswer() == answer){
                Toast.makeText(this, R.string.text_true, Toast.LENGTH_SHORT).show();
            }else if(quizArray[currentIndex].isAnswer() != answer){
                Toast.makeText(this, R.string.text_false, Toast.LENGTH_SHORT).show();
            }
        }
        System.out.println(isCheat);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("INDEX",currentIndex);
        outState.putBoolean("ISCHEAT", isCheat);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)
            return;
        if(requestCode == 999){
            if(data == null)
                return;
            isCheat = data.getBooleanExtra("SHOWN", false);
        }
    }
}


