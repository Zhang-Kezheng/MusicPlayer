package com.example.musicplayer.pageview.krc.model;

import android.graphics.Paint;
import android.util.Log;

import java.util.List;

/**
 * @author 章可政
 * @date 2020/11/7 11:20
 */
public class KRCUtil {

    public static int getRealTextHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) (-fm.leading - fm.ascent + fm.descent);
    }
    public static float getLineLyricsHLWidth(Paint paint, KRCLyricsLine krcLyricsLine, int lyricsWordIndex, float lyricsWordHLTime) {
        float lineLyricsHLWidth = 0;
        // 当行歌词
        StringBuilder curLyrics = new StringBuilder();
        for (KRCLyricsChar aChar : krcLyricsLine.getChars()) {
            curLyrics.append(aChar.getContent());
        }
        if (lyricsWordIndex != -1) {
            List<KRCLyricsChar> chars = krcLyricsLine.getChars();
            // 当前歌词之前的歌词
            StringBuilder lyricsBeforeWord = new StringBuilder();
            for (int i = 0; i < lyricsWordIndex; i++) {
                lyricsBeforeWord.append(chars.get(i).getContent());
            }
            // 当前歌词字
            String lrcNowWord = chars.get(lyricsWordIndex).getContent().trim();// 去掉空格

            Log.i("lrcNowWord",lrcNowWord);
            // 当前歌词之前的歌词长度
            float lyricsBeforeWordWidth = paint
                    .measureText(lyricsBeforeWord.toString());
            // 当前歌词长度
            float lyricsNowWordWidth = paint.measureText(lrcNowWord);
            float len = lyricsNowWordWidth
                    / chars.get(lyricsWordIndex).getDuring()
                    * lyricsWordHLTime;
            lineLyricsHLWidth = lyricsBeforeWordWidth + len;
        }
        return lineLyricsHLWidth;
    }
}
