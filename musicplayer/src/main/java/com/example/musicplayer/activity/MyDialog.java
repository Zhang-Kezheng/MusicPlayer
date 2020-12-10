package com.example.musicplayer.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.musicplayer.R;
import com.example.musicplayer.util.ScreenSizeUtils;

/**
 * @author 章可政
 * @date 2020/10/19 20:03
 */
public class MyDialog {
    private final View view;
    private final Context context;
    public MyDialog(View view, Context context) {
        this.view = view;
        this.context = context;
        init();
    }

    void init(){
        Dialog dialog = new Dialog(context, R.style.NormalDialogStyle);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(context).getScreenHeight() * 0.5f));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ScreenSizeUtils.getInstance(context).getScreenWidth();
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }
}
