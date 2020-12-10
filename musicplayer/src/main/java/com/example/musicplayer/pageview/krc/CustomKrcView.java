package com.example.musicplayer.pageview.krc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.OverScroller;
import android.widget.Scroller;
import androidx.annotation.Nullable;
import com.example.musicplayer.pageview.krc.model.KRCLyrics;
import com.example.musicplayer.pageview.krc.model.KRCLyricsChar;
import com.example.musicplayer.pageview.krc.model.KRCLyricsLine;
import com.example.musicplayer.pageview.krc.model.KRCUtil;

import java.util.List;
import java.util.TreeMap;

/**
 * @author 章可政
 * @date 2020/11/6 14:32
 */
public class CustomKrcView extends View {
    /**
     * 歌词文件读取器
     */
    private KrcReader krcReader;
    /**
     * 非高亮画笔
     */
    private Paint paint;
    /**
     * 高亮画笔
     */
    private Paint paintH;

    /**
     * 设置普通字体的大小，默认50
     */
    private float paintSize = 50f;

    /**
     * 设置普通字体的颜色,默认白色
     */
    private int paintColor = Color.WHITE;
    /**
     * 设置高亮字体的大小，默认50
     */
    private float paintHSize = 50f;

    /**
     * 设置高亮字体的颜色，默认蓝色
     */
    private int paintHColor = Color.BLUE;
    /**
     * 行间距。默认50
     */
    private float lineSpacing = 50f;
    /**
     * 当前歌曲时间
     */
    private int time;
    /**
     * 当前时间所对应的歌词行的下标
     */
    private int index;
    /**
     * 视图宽度
     */
    private float width;
    /**
     * 视图高度
     */
    private float height;
    /**
     * 歌词在Y轴的偏移量
     */
    private float offsetY;
    /**
     *歌词滚动器
     */
    private Scroller mScroller;
    //用于判断拦截
    private int mInterceptX = 0;
    private int mInterceptY = 0;
    /**
     * 触摸最后一次的坐标
     */
    private int mLastY;

    private static  final int RESETLRCVIEW=1;
    /**
     * 设置歌词对齐方式
     */
    private Alignment alignment = Alignment.CENTER;
    int lyricsWordIndex = 0;
    private enum Alignment {
        LEFT, CENTER, RIGHT
    }

    public CustomKrcView(Context context) {
        super(context);
        mScroller = new Scroller(context, new LinearInterpolator());
        init();
    }

    public CustomKrcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context, new LinearInterpolator());
        init();
    }

    public CustomKrcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context, new LinearInterpolator());
        init();
    }

    public CustomKrcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mScroller = new Scroller(context, new LinearInterpolator());
        init();
    }
    /**
     * Handler处理滑动指示器隐藏和歌词滚动到当前播放的位置
     */
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case RESETLRCVIEW:
                    if (mScroller.computeScrollOffset()) {
                        //发送还原
                        mHandler.sendEmptyMessageDelayed(RESETLRCVIEW, 3000);
                    } else {

//                        mIsTouchIntercept = false;
//                        mTouchEventStatus = TOUCHEVENTSTATUS_INIT;
                        int deltaY = getLineAtHeightY(index) - mScroller.getFinalY();
                        mScroller.startScroll(0, mScroller.getFinalY(), 0, deltaY, 3000);
                        invalidateView();
                    }
                    break;
            }
        }
    };
    private int getLineAtHeightY(int lyricsLineNum) {
        int lineAtHeightY = 0;
        for (int i = 0; i < lyricsLineNum; i++) {
//            KRCLyricsLine krcLyricsLine = krcReader.getKrc().getLines().get(i);
            lineAtHeightY += (KRCUtil.getRealTextHeight(paint) + lineSpacing) * krcReader.getKrc().getLines().size();
        }
        return lineAtHeightY;
    }
    /**
     * 设置歌词文件读取器
     *
     * @param krcReader 歌词文件读取器，用于将歌词文件格式化
     */
    public void setKrcReader(KrcReader krcReader) {
        this.krcReader = krcReader;
        index = 0;
        lyricsWordIndex=0;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        //画当前行的歌词
        List<KRCLyricsChar> chars = krcReader.getKrc().getLines().get(index).getChars();
        StringBuilder text = new StringBuilder();
        for (KRCLyricsChar aChar : chars) {
            text.append(aChar.getContent());
        }
        float lyricsWordHTime = time - krcReader.getKrc().getLines().get(index).getStart() - chars.get(lyricsWordIndex).getStart();
        float lineLyricsHLWidth = KRCUtil.getLineLyricsHLWidth(paint, krcReader.getKrc().getLines().get(index), lyricsWordIndex, lyricsWordHTime);
        drawDynamicText(canvas, text.toString(), width / 2, height / 2 - offsetY, lineLyricsHLWidth);
        //画当前行歌词之前的歌词，超出视图边界取消绘画
        for (int i = index - 1; i >= 0; i--) {
            if (height / 2 - lineSpacing * (index - i) < 0) break;
            chars = krcReader.getKrc().getLines().get(i).getChars();
            text = new StringBuilder();
            for (KRCLyricsChar aChar : chars) {
                text.append(aChar.getContent());
            }
            canvas.drawText(text.toString(), width / 2, height / 2 - (lineSpacing + paintSize) * (index - i) - offsetY, paint);
        }
        //画当前行之后的歌词，超出视图边界取消绘画
        for (int i = index + 1; i < krcReader.getKrc().getLines().size(); i++) {
            if (height / 2 + lineSpacing * (i - index) > height) break;
            chars = krcReader.getKrc().getLines().get(i).getChars();
            text = new StringBuilder();
            for (KRCLyricsChar aChar : chars) {
                text.append(aChar.getContent());
            }
            canvas.drawText(text.toString(), width / 2, height / 2 + (lineSpacing + paintSize) * (i - index) - offsetY, paint);
        }
        super.onDraw(canvas);
    }

    private void init() {
        paint = new Paint();
        paint.setColor(paintColor);
        paint.setTextSize(paintSize);
        paintH = new Paint();
        paintH.setColor(paintHColor);
        paintH.setTextSize(paintHSize);
        switch (alignment){
            case CENTER:
                paint.setTextAlign(Paint.Align.CENTER);
                paintH.setTextAlign(Paint.Align.CENTER);
                break;
            case LEFT:
                paint.setTextAlign(Paint.Align.LEFT);
                paintH.setTextAlign(Paint.Align.LEFT);
                break;
            case RIGHT:
                paint.setTextAlign(Paint.Align.RIGHT);
                paintH.setTextAlign(Paint.Align.RIGHT);
                break;
        }
    }

    /**
     * 传递时间，用于更新歌词视图
     *
     * @param time 时间。单位毫秒
     */
    public synchronized void seekTo(int time) {
        this.time = time;
        //获取当前时间所对应的歌词行的下标
        for (int i = 0; i < krcReader.getKrc().getLines().size(); i++) {
            KRCLyricsLine line = krcReader.getKrc().getLines().get(i);
            if (time >= line.getStart() && time <= (line.getStart() + line.getDuring())) {
                if (index == i) {
                    int lineStartTime = krcReader.getKrc().getLines().get(index).getStart();
                    List<KRCLyricsChar> chars = krcReader.getKrc().getLines().get(index).getChars();
                    for ( i = 0; i < chars.size(); i++) {
                        if ( time>=lineStartTime+ chars.get(i).getStart() && time <=lineStartTime+chars.get(i).getStart() + chars.get(i).getDuring()) {
                            lyricsWordIndex = i;
                            break;
                        }
                    }
                    return;
                }else {
                    index = i;
                    mScroller.abortAnimation();
                    mScroller.startScroll(0, 0, 0, (int) (paintSize + lineSpacing), 500);
                    break;
                }
            }
        }
        invalidateView();
    }

    private void drawDynamicText(Canvas canvas, String krcText, float x, float y, float hlWidth) {
        canvas.save();
        canvas.drawText(krcText, x, y, paint);
        //设置动感歌词过渡效果
        canvas.clipRect(x-paint.measureText(krcText)/2, y -KRCUtil.getRealTextHeight(paint), x-paint.measureText(krcText)/2 + hlWidth,
                y + KRCUtil.getRealTextHeight(paint));
        canvas.drawText(krcText, x, y, paintH);
        canvas.restore();
    }

    /**
     * 设置歌词对齐方式
     *
     * @param alignment 对齐方式的枚举类
     */
    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            offsetY = mScroller.getCurrY();
            invalidateView();
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionId = event.getAction();
        switch (actionId) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) event.getY();
                mInterceptX = (int) event.getX();
                mInterceptY = (int) event.getY();

                //发送还原
                mHandler.removeMessages(RESETLRCVIEW);
                break;
            case MotionEvent.ACTION_MOVE:
                int curX = (int) event.getX();
                int curY = (int) event.getY();
                int deltaX = mInterceptX - curX;
                int deltaY = mInterceptY - curY;
                int dy = mLastY - curY;
                //创建阻尼效果
                float finalY = offsetY + dy;
                dy = dy / 2;
                mScroller.startScroll(0, mScroller.getFinalY(), 0, dy, 0);
                invalidateView();
                mLastY = curY;
                break;
                }

        return true;
    }
    public synchronized void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            //  当前线程是主UI线程，直接刷新。
            invalidate();
        } else {
            //  当前线程是非UI线程，post刷新。
            postInvalidate();
        }
    }
}
