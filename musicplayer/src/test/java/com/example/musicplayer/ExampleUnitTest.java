package com.example.musicplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import com.example.musicplayer.pageview.krc.KrcReader;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public static void main(String[] args) {
        KrcReader krcReader=new KrcReader(new File("D:\\KuGou\\Lyric\\任然 - 山外小楼夜听雨-7f066ff03c4ac954e25570c4622c228a-35087571-00000000.krc"));
        System.out.println(krcReader);
    }
}