package com.example.dell.workshopmodule.view.ui.activity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.dell.workshopmodule.R;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView animationView;
    private TextView tvStartEngine,tvStartJourney;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv=(TextView)toolbar.findViewById(R.id.tv_toolbar_title);
        tv.setText(R.string.find_fix);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorBlueLight));




        tvStartEngine=(TextView)findViewById(R.id.tv_start_engine);
        iv=(ImageView)findViewById(R.id.iv_logo);
        tvStartJourney=(TextView)findViewById(R.id.tv_start_journey);
       tvStartEngine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationView.playAnimation();
                animationView.setMinProgress(0.3F);
                animationView.setMaxProgress(1.0F);
            }
        });
       iv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               animationView.playAnimation();
               animationView.setMinProgress(0.3F);
               animationView.setMaxProgress(1.0F);
           }
       });
        // initializeBackground();
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.playAnimation();
        animationView.setMaxProgress(0.3F);



        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width = (displayMetrics.widthPixels);


        // initializeCustomView();
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(animationView.getProgress()==0.3f)
                tvStartEngine.setVisibility(View.VISIBLE);




                if(animationView.getProgress()==1.0f) {
                    tvStartEngine.setVisibility(View.GONE);
                    iv.setVisibility(View.VISIBLE);
                    tvStartJourney.setText(R.string.start_journey);
                    animationView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {


                                if ((int) event.getX() < ((width-24) / 2) && (int) event.getY() < ((height-24) / 4)) {
                                    startActivity(new Intent(MainActivity.this,UrgentRequestActivity.class));
                                } else if ((int) event.getX() < ((width-24) / 2) && (int) event.getY() > ((height-24) / 4)) {

                                    startActivity(new Intent(MainActivity.this,AddAdvertisement.class));
                                } else if ((int) event.getX() > ((width-24) / 2) && (int) event.getY() < ((height-24) / 4)) {
                                    startActivity(new Intent(MainActivity.this,NormalRequestActivity.class));

                                } else if ((int) event.getX() > ((width-24) / 2) && (int) event.getY() > ((height-24) / 4)) {

                                    startActivity(new Intent(MainActivity.this,EditProfileInfoActivity.class));
                                }
                            }
                            return true;
                        }
                    });
                }


            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


    }

}
