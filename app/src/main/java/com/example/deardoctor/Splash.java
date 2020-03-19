package com.example.deardoctor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;


import com.example.deardoctor.Register.Login.Login;
import com.example.deardoctor.Register.SignUp;
import com.example.deardoctor.main.BottomNavigator;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Splash extends AppCompatActivity {

    @BindView(R.id.mainLogo)
    ImageView mainLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        Intent intent = new Intent(Splash.this, BottomNavigator.class);
        startActivity(intent);
//        new Handler().postDelayed(() -> {
//            Intent intent = new Intent(Splash.this, SignUp.class);
//            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    Splash.this,
//                    mainLogo,
//                    Objects.requireNonNull(ViewCompat.getTransitionName(mainLogo)));
//            startActivity(intent,options.toBundle());
//        },300);

      //   start();
    }
    void start(){
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(mainLogo, "scaleX", 0.6f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(mainLogo, "scaleY", 0.6f);
        ObjectAnimator flip = ObjectAnimator.ofFloat(mainLogo, "rotationY", 0f, 720f);
        flip.setDuration(1000);
        flip.start();
        scaleDownX.setDuration(1000);
        scaleDownY.setDuration(1000);
        ObjectAnimator moveUpY = ObjectAnimator.ofFloat(mainLogo, "translationY", -300);
        moveUpY.setDuration(1000);
        AnimatorSet scaleDown = new AnimatorSet();
        AnimatorSet moveUp = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);
        moveUp.play(moveUpY);
         scaleDown.start();
     //   moveUp.start();
        flip.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(Splash.this, SignUp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        Splash.this,
                        mainLogo,
                        Objects.requireNonNull(ViewCompat.getTransitionName(mainLogo)));
                startActivity(intent,options.toBundle());
                super.onAnimationEnd(animation);
            }
        });

//        AnimatorSet moveUps = new AnimatorSet();
//        moveUp.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                ObjectAnimator moveUpY = ObjectAnimator.ofFloat(mainLogo, "translationX", 700);
//                moveUpY.setDuration(500);
//                moveUps.play(moveUpY); moveUps.start();
//
//                super.onAnimationEnd(animation);
//            }
//        });
    }
}
