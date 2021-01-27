package com.example.musicplayer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author 章可政
 * @date 2020/11/5 22:45
 */
public class CustomTextView extends View {
    private Paint paint;
    private Paint paintH;
    private String text;

    public CustomTextView(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init(){
        paint=new Paint();
        paint.setTextSize(50);
        paint.setColor(android.R.color.secondary_text_dark);
        paintH=new Paint();
        paintH.setTextSize(50);
        paintH.setColor(Color.BLUE);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if (text==null){
            super.onDraw(canvas);
            return;
        }
        if (!text.contains("<em>")){
            super.onDraw(canvas);
            return;
        }
        String text1 = text.split("<em>")[0];
        String text2=text.split("<em>")[1].split("</em>")[0];
        String text3="";
        if (text.split("<em>")[1].split("</em>").length>1){
            text3=text.split("<em>")[1].split("</em>")[1];
        }
        Rect bound1=new Rect();
        Rect bound2=new Rect();
        Rect bound3=new Rect();
        paint.getTextBounds(text1, 0, text1.length(), bound1);
        canvas.drawText(text1, 0,  bound1.height() / 2f, paint);
        paintH.getTextBounds(text2, 0,text2.length(), bound2);
        canvas.drawText(text2, bound1.width(),  bound2.height() / 2f, paintH);
        paint.getTextBounds(text3, 0, text3.length(), bound3);
        canvas.drawText(text3,bound2.width(),bound3.height()/2f,paint);
    }
    public void setText(String text){
        this.text=text;
    }
}
