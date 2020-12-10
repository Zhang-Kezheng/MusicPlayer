package com.example.musicplayer.pageview.krc.model;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 章可政
 * @date 2020/11/6 15:10
 */
public class KRCLyrics {
    /**
     * 歌词文本
     */
    private String krcText;
    /**
     * ID （总是$00000000，意义未知）
     */
    private String id;

    /**
     * 艺术家
     */
    private String ar;
    /**
     * 标题
     */
    private String ti;

    /**
     * 歌词作者
     */
    private String by;
    /**
     * 歌曲hash值
     */
    private String hash;
    /**
     * 总时长
     */
    private int total;

    /**
     * 歌词行集合
     */
    private List<KRCLyricsLine> krcLines;
    public KRCLyrics(String krcText) {
        this.krcText = krcText;
        loadLines();
    }

    /**
     * 加载所有行数据
     */
    private void loadLines(){
        krcLines=new ArrayList<>();
        String reg="\\[\\d+,\\d+\\]";
        initKrcTag(krcText);
        String[] lines = krcText.split("\r\n");
        for (String line : lines) {
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()){
                KRCLyricsLine krcLyricsLine=new KRCLyricsLine(line);
                krcLines.add(krcLyricsLine);
            }
        }

    }
    private void initKrcTag(String krcText){
        String[] krcLines = krcText.split("\r\n");
        for (String line : krcLines) {
            if (line.contains("[id:")){
                id=line.split("\\[id:")[1].replace("]","");
                continue;
            }
            if (line.contains("[ar:")){
                ar=line.split("\\[ar:")[1].replace("]","");
                continue;
            }
            if (line.contains("[ti:")){
                ti=line.split("\\[ti:")[1].replace("]","");
                continue;
            }
            if (line.contains("[by:")){
                by=line.split("\\[by:")[1].replace("]","");
                continue;
            }
            if (line.contains("[hash:")){
                hash=line.split("\\[hash:")[1].replace("]","");
                continue;
            }
            if (line.contains("[total:")){
                total=Integer.parseInt(line.split("\\[total:")[1].replace("]",""));
            }
        }
    }

    public String getKrcText() {
        return krcText;
    }

    public String getId() {
        return id;
    }

    public String getAr() {
        return ar;
    }

    public String getTi() {
        return ti;
    }

    public String getBy() {
        return by;
    }

    public String getHash() {
        return hash;
    }

    public int getTotal() {
        return total;
    }

    public List<KRCLyricsLine> getLines() {
        return krcLines;
    }
}
