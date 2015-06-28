package me.evgenius.geoquiz;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends Activity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEATED = "cheated";

    private TextView mQuestioTextView;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mPrevButton;
    private ImageButton mNextButton;
    private TextView mVersionTextView;

    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_america, true),
            new TrueFalse(R.string.question_asia, true)
    };
    private boolean[] mIsCheated = new boolean[mQuestionBank.length];
    private int mCurrentIndex;

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestioTextView.setText(question);
    }

    private void nextQuestion(boolean next) {
        if (next) {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        } else {
            if (mCurrentIndex == 0) {
                mCurrentIndex = mQuestionBank.length - 1;
            } else {
                mCurrentIndex--;
            }
        }
        updateQuestion();
    }

    private void checkAnswer(boolean userPressed) {
        boolean answer = mQuestionBank[mCurrentIndex].isTrueQuestion();
        boolean cheated = mIsCheated[mCurrentIndex];
        int messageId;
        if (cheated) {
            messageId = R.string.judgment_toast;
        } else {
            if (userPressed == answer) {
                messageId = R.string.correct_toast;
            } else {
                messageId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(QuizActivity.this, messageId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheated = savedInstanceState.getBooleanArray(KEY_CHEATED);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setSubtitle("XXX");
            }
        }

        mQuestioTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestioTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion(true);
            }
        });

        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answer = mQuestionBank[mCurrentIndex].isTrueQuestion();
                intent.putExtra(CheatActivity.EXTRA_ANSWER, answer);
                intent.putExtra(CheatActivity.EXTRA_IS_CHEATED, mIsCheated[mCurrentIndex]);
//                startActivity(intent);
                startActivityForResult(intent, 0);
            }
        });

        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion(false);
            }
        });

        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion(true);
            }
        });

        mVersionTextView = (TextView)findViewById(R.id.android_version_text_view);
        int version = Build.VERSION.SDK_INT;
        mVersionTextView.setText("API level " + version);

        updateQuestion();
        Log.d(TAG, "onCreate(Bundle)");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }
        mIsCheated[mCurrentIndex] = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
        Log.d(TAG, "onActivityResult(int,int,Intent)");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBooleanArray(KEY_CHEATED, mIsCheated);
        Log.d(TAG, "onSaveInstanceState(Bundle)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }
}
