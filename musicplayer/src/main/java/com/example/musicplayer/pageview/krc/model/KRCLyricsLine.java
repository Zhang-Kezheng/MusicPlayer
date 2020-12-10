package com.example.musicplayer.pageview.krc.model;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 章可政
 * @date 2020/11/6 15:31
 */
public class KRCLyricsLine {
    /**
     * 该行歌词字符串
     */
    private String krcLine;
    /**
     * 该行歌词距离歌曲开始的时间，单位为毫秒
     */
    private int start;
    /**
     * 该行歌词持续的时间，单位为毫秒
     */
    private int during;
    /**
     * 该行歌词的每个字的集合
     */
    private List<KRCLyricsChar> chars;

    public KRCLyricsLine(String krcLine) {
        this.krcLine = krcLine;
        loadChars();
    }

    private void loadChars() {
        chars=new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[\\d+,\\d+\\]");
        Matcher matcher = pattern.matcher(krcLine);
        if (matcher.find()) {
            int mStartIndex = matcher.start();
            int mEndIndex = matcher.end();
            String[] krcLineTime = krcLine.substring(mStartIndex + 1,
                    mEndIndex - 1).split(",");
            start = Integer.parseInt(krcLineTime[0]);
            during = Integer.parseInt(krcLineTime[1]);
            String KRCLyricsCharBody = krcLine.substring(mEndIndex);
            for (String s : KRCLyricsCharBody.split("<")) {
                if ("".equals(s)) continue;
                KRCLyricsChar krcLyricsChar = new KRCLyricsChar("<" + s);
                chars.add(krcLyricsChar);
            }
        }
    }

    public String getKrcLine() {
        return krcLine;
    }

    public int getStart() {
        return start;
    }

    public int getDuring() {
        return during;
    }

    public List<KRCLyricsChar> getChars() {
        return chars;
    }
}
