package com.example.deardoctor.constants;

import android.content.Context;
import android.view.WindowManager;
import android.widget.PopupWindow;

public abstract class SettingPopUpWindow extends PopupWindow {
    public SettingPopUpWindow(Context context) {
        super(context);
        setOutsideTouchable(true);
        setWidth(Util.Dp2px(context, 320));
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    }
}