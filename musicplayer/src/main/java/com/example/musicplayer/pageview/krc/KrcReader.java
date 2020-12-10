package com.example.musicplayer.pageview.krc;

import com.example.musicplayer.pageview.krc.model.KRCLyrics;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author 章可政
 * @date 2020/11/6 14:33
 */
public class KrcReader {
    /**
     * 歌词对象
     */
    private KRCLyrics krc;
    private static final char[] miarry = {'@', 'G', 'a', 'w', '^', '2', 't',
            'G', 'Q', '6', '1', '-', 'Î', 'Ò', 'n', 'i'};

    public KrcReader(File krcFile) {
        String krcText = parseKrcFile(krcFile);
        krc=parseKrc(krcText);
    }

    /**
     * 封装歌词
     * @param krcText 歌词文件的字符串
     * @return 返回歌词对象
     */
    private KRCLyrics parseKrc(String krcText){
        return new KRCLyrics(krcText);
    }

    /**
     * 获取歌词对象
     * @return 歌词对象
     */
    public KRCLyrics getKrc() {
        return krc;
    }

    /**
     * 解析歌词文件
     * @param krcFile 歌词文件
     * @return 返回歌词文件里的字符串
     */
    private String parseKrcFile(File krcFile) {
        byte[] zip_byte = new byte[(int) krcFile.length()];
        FileInputStream fileinstrm = null;
        String krc_text = null;
        try {
            fileinstrm = new FileInputStream(krcFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            byte[] top = new byte[4];
            assert fileinstrm != null;
            fileinstrm.read(top);
            fileinstrm.read(zip_byte);
            int j = zip_byte.length;
            for (int k = 0; k < j; k++) {
                int l = k % 16;
                zip_byte[k] = (byte) (zip_byte[k] ^ miarry[l]);
            }
            krc_text = new String(KrcUtils.decompress(zip_byte), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return krc_text;

    }

}
