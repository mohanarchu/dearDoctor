package com.example.deardoctor.Register;

import android.animation.AnimatorSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import com.example.deardoctor.Constant;
import com.example.deardoctor.R;
import com.example.deardoctor.base.FragmentBase;
import com.github.angads25.toggle.widget.LabeledSwitch;

import java.util.Objects;

import butterknife.BindView;

public class Doctor_Register extends FragmentBase {

    @BindView(R.id.switchForLogin)
    LabeledSwitch switchForLogin;
    @BindView(R.id.docregister_layout)
    CardView docregisterLayout;
    @BindView(R.id.logoImage)
    ImageView logoImage;


    @Override
    protected void onViewBound(View view) {
        CardView.LayoutParams marginLayoutParams = new CardView.LayoutParams(docregisterLayout.getLayoutParams());
        marginLayoutParams.setMargins(0, Constant.getStatusBarHeight(Objects.requireNonNull(getActivity())), 0, 0);
        Animation img1_Anim = AnimationUtils.loadAnimation(getActivity(),
                R.anim.img1_animation);
        logoImage.startAnimation(img1_Anim);

    }


    @Override
    protected int layoutRes() {
        return R.layout.activity_doctor__register;
    }
}
