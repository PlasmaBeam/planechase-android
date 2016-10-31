package com.ernestosalazar.planechaseandroid;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ernestosalazar.planechaseandroid.Fragments.PhenomenonFragment;
import com.ernestosalazar.planechaseandroid.Logic.Controller;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullscreenActivity extends AppCompatActivity {

    @BindView(R.id.content_view)
    View mContentView;

    @BindView(R.id.image_container)
    ImageView mImageContainer;

    @BindView(R.id.start_deck)
    Button mStartButton;

    @BindView(R.id.next_button)
    Button mNextButton;

    @BindView(R.id.previous_button)
    Button mPreviousButton;

    @BindView(R.id.plus_button)
    Button mPlusButton;

    @BindView(R.id.minus_button)
    Button mMinusButton;

    @BindView(R.id.counter_text)
    TextView mCounterText;

    private Controller mDeckController;
    private int mCounter;

    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mDeckController = new Controller();

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCounter = 0;
                mDeckController.shuffleDeck();
                int card = mDeckController.getFirstCard();
                loadImage(card);
                enableButtons();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int card = mDeckController.nextCard();
                loadImage(card);
            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int card = mDeckController.previousCard();
                loadImage(card);
            }
        });

        mPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusCounter();
            }
        });

        mMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minusCounter();
            }
        });


        loadImage("base");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mImageContainer.getDrawable() == null) {
            loadImage("base");
        }
    }

    private void loadImage(Object value) {
        mCounter = 0;
        mCounterText.setText(String.valueOf(mCounter));
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int id;
        if (value instanceof Integer) {
            Picasso.with(this)
                    .load(this.getResources().getIdentifier("p" + value, "drawable", this.getPackageName()))
                    .resize(width, height)
                    .placeholder(R.drawable.loading)
                    .into(mImageContainer);

        } else {
            Picasso.with(this)
                    .load(R.drawable.basewp)
                    .resize(width, height)
                    .into(mImageContainer);
        }


    }

    private void plusCounter() {
//        DialogFragment newFragment = PhenomenonFragment.newInstance(110);
//        newFragment.show(getFragmentManager(), "dialog");
        mCounter++;
        mCounterText.setText(String.valueOf(mCounter));
    }

    private void minusCounter() {
        if (mCounter > 0) {
            mCounter--;
        } else {
            mCounter = 0;
        }
        mCounterText.setText(String.valueOf(mCounter));
    }

    private void enableButtons() {
        mMinusButton.setEnabled(true);
        mPlusButton.setEnabled(true);
        mPreviousButton.setEnabled(true);
        mNextButton.setEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    /*
    * Start Effects section.
    * 1-8 phenomena
    */

    public void isEffectCard(int card){
        if(card == 2 || card == 7 || card == 79){
            DialogFragment newFragment = PhenomenonFragment.newInstance(110);
            newFragment.show(getFragmentManager(), "dialog");
        }
    }

    private void effect2(){
        //show top 5 plane cards. pick one and put on top, rest on the bottom.

    }

    private void effect7(){
        //planes walk to next two planes
    }

    private void effect34(){
        //show next 3 planes. trigger chaos of each of them
    }

    private void effect79(){
        //scry 1 planar deck
    }
}