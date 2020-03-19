package com.example.deardoctor.base;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

public abstract class FragmentBase extends Fragment {
    private Unbinder unbinder;
    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutRes(), container, false);
        unbinder = ButterKnife.bind(this, view);
        onViewBound(view);
        return view;
    }

    @Override
    public void onDestroyView() {

        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    protected void onViewBound(View view) {


    }

    protected void backClicked() {
    }
    protected Disposable[] subscriptions() {
        return new Disposable[0];
    }

    @LayoutRes
    protected abstract int layoutRes();

    @Override
    public void onResume() {

        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    backClicked();
                    return true;
                }
                return false;
            }
        });
    }
}

