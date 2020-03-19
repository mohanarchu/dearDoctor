package com.example.deardoctor.Register.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.deardoctor.BasicActivity;
import com.example.deardoctor.R;
import com.example.deardoctor.Register.SignUp;
import com.example.deardoctor.main.BottomNavigator;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends BasicActivity {


    @BindView(R.id.emialLogin)
    EditText emialLogin;
    @BindView(R.id.passwordLogin)
    EditText passwordLogin;
    @BindView(R.id.forgotPassword)
    TextView forgotPassword;
    @BindView(R.id.login)
    LinearLayout login;
    @BindView(R.id.newSignUp)
    TextView newSignUp;
    @BindView(R.id.signup_layout)
    LinearLayout signupLayout;
    @BindView(R.id.emailError)
    TextInputLayout emailError;
    @BindView(R.id.passwordError)
    TextInputLayout passwordError;

    @Override
    protected void backClick() {

    }

    @Override
    protected void onViewBound(Bundle savedInstanceState) {

        emialLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                emailError.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected int layout() {
        return R.layout.login_fragment;
    }

    private boolean isValidEmail(String email) {
        return  Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean emptyChecker(String email,String password){
        return (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password));
    }



    @OnClick({R.id.forgotPassword, R.id.login, R.id.newSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forgotPassword:
                break;
            case R.id.login:
                if (emptyChecker(emialLogin.getText().toString(),passwordLogin.getText().toString())){
                    startActivity(new Intent(this, BottomNavigator.class));
                }else {
                    if (isValidEmail(emialLogin.getText().toString()))
                        startActivity(new Intent(this, BottomNavigator.class));
                    else
                        emailError.setError(getString(R.string.valid_email));
                }

                    break;
            case R.id.newSignUp:
                finish();

                break;
        }
    }


}
