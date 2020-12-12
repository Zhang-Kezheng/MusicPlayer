package com.example.musicplayer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import com.example.musicplayer.Observer;
import com.example.musicplayer.R;
import com.example.musicplayer.activity.HomePageActivity;
import com.example.musicplayer.adapter.MusicListAdapter;
import com.example.musicplayer.adapter.SearchListAdapter;
import com.example.musicplayer.commons.MusicPlayerApplication;
import com.example.musicplayer.commons.MusicServiceConnect;
import com.example.musicplayer.model.music.searchmusicinfo.SearchMusicInfoData;
import com.example.musicplayer.model.music.searchmusicplayurl.SearchMusicPlayUrlData;
import com.example.musicplayer.model.user.MusicInfo;
import com.example.musicplayer.util.AnimatorUtil;
import com.example.musicplayer.util.HttpUtil;
import com.google.gson.Gson;
import me.yokeyword.swipebackfragment.SwipeBackFragment;
import me.yokeyword.swipebackfragment.SwipeBackLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static com.example.musicplayer.commons.MusicPlayerApplication.SEARCH_MUSIC_KEYWORD;
import static com.example.musicplayer.commons.MusicPlayerApplication.SEARCH_MUSIC_PLAY_URL;

public class SearchFragment extends Fragment implements View.OnClickListener, Observer {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MusicPlayerApplication application;//当前应用
    private EditText keyword;//关键字
    public ListView search_list;
    private SearchMusicInfoData searchMusicInfoData;
    private SearchListAdapter searchListAdapter;
    private MusicInfo musicInfo;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        application = (MusicPlayerApplication) requireActivity().getApplication();
        View inflate = inflater.inflate(R.layout.fragment_search, container, false);
        search_list = inflate.findViewById(R.id.search_list);
        LinearLayout backs = inflate.findViewById(R.id.backs);
        backs.setOnClickListener(this);
        keyword = inflate.findViewById(R.id.keyword);
        keyword.setOnKeyListener(onKeyListener);
        Button search_button=inflate.findViewById(R.id.search_button);
        search_button.setOnClickListener(this);
        search_list.setOnItemClickListener((parent, view, position, id) -> {
            searchListAdapter.setIndex(position);
            searchListAdapter.notifyDataSetChanged();
            URL url = null;
            try {
                url = new URL("https://wwwapi.kugou.com/yy/index.php?r=play/getdata&callback=jQuery19108335867548501714_1603904292877&hash=" + searchMusicInfoData.getData().getLists().get(position).getFileHash() + "&album_id=" + searchMusicInfoData.getData().getLists().get(position).getAlbumID() + "&dfid=1tgQ7z1EVOHH196APV4Tfoja&mid=f2eed631d990ecd18a86cc6e39867770&platid=4&_=1603943086161");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String mvHash = searchMusicInfoData.getData().getLists().get(position).getMvHash();
            musicInfo = new MusicInfo();
            musicInfo.setMvHash(mvHash);
            HttpUtil.sendGetRequest(url, handler, SEARCH_MUSIC_PLAY_URL);
        });
        return inflate;

    }

    /**
     * 消息处理
     */
    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            String data = bundle.getString("data");
            Gson gson = new Gson();
            switch (msg.what) {
                case SEARCH_MUSIC_KEYWORD:
                    data = data.replace("jQuery191034642999175022426_1489023388639(", "");
                    int i = data.lastIndexOf(")");
                    data = data.substring(0, i);
                    searchMusicInfoData = gson.fromJson(data, SearchMusicInfoData.class);
                    searchListAdapter = new SearchListAdapter(application, SearchFragment.this.getContext(), searchMusicInfoData);
                    search_list.setAdapter(searchListAdapter);
                    searchListAdapter.notifyDataSetChanged();
                    break;
                case SEARCH_MUSIC_PLAY_URL:
                    data = data.replace("jQuery19108335867548501714_1603904292877(", "");
                    int s = data.lastIndexOf(";");
                    data = data.substring(0, s);
                    int j = data.lastIndexOf(")");
                    data = data.substring(0, j);
                    SearchMusicPlayUrlData currentMusic = gson.fromJson(data, SearchMusicPlayUrlData.class);
                    musicInfo.setMusicPlayUrlData(currentMusic);
                    playMusic(musicInfo);
                    break;
            }

        }
    };


    View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                String text = keyword.getText().toString();
                if (text.equals("")) {
                    Toast.makeText(SearchFragment.this.getContext(), "关键词不能为空,请输入关键词", Toast.LENGTH_LONG).show();
                    return true;
                }
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                URL url = null;
                try {
                    url = new URL("https://songsearch.kugou.com/song_search_v2?callback=jQuery191034642999175022426_1489023388639&keyword=" + text
                            + "&page=1&pagesize=30&userid=-1&clientver=&platform=WebFilter&tag=em&filter=2&iscorrection=1&privilege_filter=0&_=1489023388641");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpUtil.sendGetRequest(url, handler, SEARCH_MUSIC_KEYWORD);
                return true;
            }
            return false;
        }
    };

    private void playMusic(MusicInfo currentMusic) {
        if (currentMusic.getMusicPlayUrlData() != null) {
            if ("".equals(currentMusic.getMusicPlayUrlData().getData().getPlayUrl())) {
                Toast.makeText(SearchFragment.this.getContext(), "暂无资源", Toast.LENGTH_SHORT).show();
                return;
            }
            if (currentMusic.getMusicPlayUrlData().getData().getTransParam() != null) {
                Toast.makeText(SearchFragment.this.getContext(), "该歌曲为付费歌曲，暂不支持播放", Toast.LENGTH_SHORT).show();
                return;
            }

            //frag检查该歌曲是否存在于播放列表，若不存在则添加进播放列表，若存在则不添加使用播放列表的歌曲信息
            boolean flag = false;
            if (application.appSet.getMusicInfos() != null) {
                for (MusicInfo musicinfo : application.appSet.getMusicInfos()) {
                    if (musicinfo.getMusicPlayUrlData().getData().getHash().equals(currentMusic.getMusicPlayUrlData().getData().getHash())) {
                        flag = true;
                        application.appSet.setCurrentPlayPosition(application.appSet.getMusicInfos().indexOf(musicinfo));
                    }
                }
            }
            if (!flag) {
                List<MusicInfo> musicInfos = application.appSet.getMusicInfos();
                if (musicInfos == null) musicInfos = new ArrayList<>();
                musicInfos.add(musicInfo);
                application.appSet.setMusicInfos(musicInfos);
                application.appSet.setCurrentPlayPosition(application.appSet.getMusicInfos().size() - 1);
            }
            HomePageActivity activity = (HomePageActivity) getActivity();
            assert activity != null;
            activity.getConnection().getMusicControl().changeMusic(application.appSet.getCurrentPlayPosition());
            MusicPlayerApplication.serialization(application.appSet, MusicPlayerApplication.CONFIG_PATH + "appSet.conf");

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backs:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            case R.id.search_button:
                    String text = keyword.getText().toString();
                    if (text.equals("")) {
                        Toast.makeText(SearchFragment.this.getContext(), "关键词不能为空,请输入关键词", Toast.LENGTH_LONG).show();
                        return ;
                    }
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    URL url = null;
                    try {
                        url = new URL("https://songsearch.kugou.com/song_search_v2?callback=jQuery191034642999175022426_1489023388639&keyword=" + text
                                + "&page=1&pagesize=30&userid=-1&clientver=&platform=WebFilter&tag=em&filter=2&iscorrection=1&privilege_filter=0&_=1489023388641");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    HttpUtil.sendGetRequest(url, handler, SEARCH_MUSIC_KEYWORD);
                break;
        }
    }


    @Override
    public void update(int command) {

    }
}
