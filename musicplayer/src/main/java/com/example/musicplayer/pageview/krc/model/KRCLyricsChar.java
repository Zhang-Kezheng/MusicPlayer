package com.example.musicplayer.pageview.krc.model;

/**
 * @author 章可政
 * @date 2020/11/6 16:36
 */
public class KRCLyricsChar {
    /**
     * 此字的所有字符串
     */
    private String krcChar;
    /**
     * 此字的距离它所在行开始的时间，单位为毫秒
     */
    private int start;
    /**
     * 此字的持续时间
     */
    private int during;
    /**
     * 此字的内容
     */
    private String content;

    /**
     *构造方法
     * @param krcChar
     */
    public KRCLyricsChar(String krcChar) {
        this.krcChar = krcChar;
        loadKrcChar();
    }
    private void loadKrcChar(){
        System.out.println(krcChar);
        if (krcChar.split(">").length<2)
            content="";
        else
            content = krcChar.split(">")[1];
        String krcCharTime=krcChar.split(">")[0].replace("<","");
        start=Integer.parseInt(krcCharTime.split(",")[0]);
        during=Integer.parseInt(krcCharTime.split(",")[1]);
    }

    public String getKrcChar() {
        return krcChar;
    }

    public int getStart() {
        return start;
    }

    public int getDuring() {
        return during;
    }

    public String getContent() {
        return content;
    }
}
