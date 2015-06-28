package me.evgenius.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

    public static final String EXTRA_ANSWER = "me.evgenius.geoquiz.answer";
    public static final String EXTRA_IS_CHEATED = "me.evgenius.geoquiz.is_cheated";
    public static final String EXTRA_ANSWER_SHOWN = "me.evgenius.geoquiz.answer_shown";

    private static final String TAG = "CheatActivity";
    private static final String KEY_SHOWN = "shown";

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    private boolean mAnswer;
//    private boolean mIsCheated;
    private boolean mIsAnswerShown;

    private void showAnswer() {
        if (mIsAnswerShown) {
            if (mAnswer) {
                mAnswerTextView.setText(R.string.true_button);
            } else {
                mAnswerTextView.setText(R.string.false_button);
            }
        }
    }

    private void setAnswerResult() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER_SHOWN, mIsAnswerShown);
        setResult(RESULT_OK, intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState != null) {
            mIsAnswerShown = savedInstanceState.getBoolean(KEY_SHOWN, false);
        }

        mAnswer = getIntent().getBooleanExtra(EXTRA_ANSWER, false);
        mIsAnswerShown |= getIntent().getBooleanExtra(EXTRA_IS_CHEATED, false);

        mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);

        mShowAnswerButton = (Button)findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsAnswerShown = true;
                showAnswer();
                setAnswerResult();
            }
        });

        showAnswer();
        setAnswerResult();
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_SHOWN, mIsAnswerShown);
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
