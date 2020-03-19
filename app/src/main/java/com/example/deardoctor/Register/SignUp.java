package com.example.deardoctor.Register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.deardoctor.BasicActivity;
import com.example.deardoctor.Constant;
import com.example.deardoctor.R;
import com.example.deardoctor.Register.Login.Login;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUp extends BasicActivity {



    @BindView(R.id.signup_layout)
    LinearLayout signupLayout;
    @BindView(R.id.clickName)
    EditText clickName;
    @BindView(R.id.clinicNameError)
    TextInputLayout clinicNameError;
    @BindView(R.id.mobilrNumberSIgnup)
    EditText mobilrNumberSIgnup;
    @BindView(R.id.mobileNumberError)
    TextInputLayout mobileNumberError;
    @BindView(R.id.emailSignUp)
    EditText emailSignUp;
    @BindView(R.id.signUpEmailError)
    TextInputLayout signUpEmailError;
    @BindView(R.id.passwordSignup)
    EditText passwordSignup;
    @BindView(R.id.signPasswordError)
    TextInputLayout signPasswordError;
    @BindView(R.id.signUp)
    LinearLayout signUp;
    @BindView(R.id.loginFromSignup)
    TextView loginFromSignup;



    @Override
    protected void backClick() {
        View view = getCurrentFocus();
        if (view != null) {
            view.clearFocus();
        }
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onViewBound(Bundle savedInstanceState) {
        LinearLayout.LayoutParams marginLayoutParams = new LinearLayout.LayoutParams(signupLayout.getLayoutParams());
        marginLayoutParams.setMargins(0, Constant.getStatusBarHeight(Objects.requireNonNull(getApplicationContext())), 0, 0);
        signupLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    View view = getCurrentFocus();
                    if (view != null) {
                        view.clearFocus();
                    }
                }
                return false;
            }
        });
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.i("TAG", "Back pressed");
        super.onWindowFocusChanged(hasFocus);
    }


    @Override
    protected int layout() {
        return R.layout.activity_sign_up;
    }


    @OnClick({R.id.signUp, R.id.loginFromSignup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signUp:
                break;
            case R.id.loginFromSignup:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }
}
