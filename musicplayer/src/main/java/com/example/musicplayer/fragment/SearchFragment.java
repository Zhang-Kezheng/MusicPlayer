package com.example.musicplayer.fragment;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.musicplayer.Observer;
import com.example.musicplayer.R;
import com.example.musicplayer.activity.HomePageActivity;
import com.example.musicplayer.adapter.RecentSingleMusicAdapter;
import com.example.musicplayer.adapter.SearchMusicAdapter;
import com.example.musicplayer.adapter.SearchTipListAdapter;
import com.example.musicplayer.commons.MusicPlayerApplication;
import com.example.musicplayer.model.music.recommend.RecommendMusicData;
import com.example.musicplayer.model.music.searchmusicinfo.SearchMusicInfoData;
import com.example.musicplayer.model.music.searchmusicplayurl.SearchMusicPlayUrlData;
import com.example.musicplayer.model.music.tips.SeachTips;
import com.example.musicplayer.model.user.MusicInfo;
import com.example.musicplayer.util.HttpUtil;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.musicplayer.commons.MusicPlayerApplication.*;

public class SearchFragment extends Fragment implements View.OnClickListener, Observer {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MusicPlayerApplication application;//当前应用
    private EditText keyword;//关键字
    private SearchMusicInfoData searchMusicInfoData;//搜索出来的音乐数据
    private MusicInfo musicInfo;//被选中的歌曲信息
    private FlexboxLayout flexboxLayout;//历史搜索
    private ProgressBar progressBar;//正在搜索
    private LinearLayout search_music;//搜索出来的音乐列表
    private LinearLayout recommend;//推荐歌曲模块
    private LinearLayout search_recommend;//搜索推荐模块
    private NestedScrollView music_recommend;
    private ConstraintLayout search_tips;
    private NestedScrollView music_list;
    private List<View> views;
    private ListView search_online;//在线搜索提示
    private ListView search_local;//本地搜索提示
    private TextView search_local_title;
    private TextView search_online_title;
    private RecentSingleMusicAdapter localTipsAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

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
        showOrHide(getContext());
        search_music = inflate.findViewById(R.id.search_music);
        LinearLayout backs = inflate.findViewById(R.id.backs);
        flexboxLayout = inflate.findViewById(R.id.search_history);
        progressBar = inflate.findViewById(R.id.progress_bar);
        recommend = inflate.findViewById(R.id.recommend);
        search_recommend = inflate.findViewById(R.id.search_recommend);
        music_recommend = inflate.findViewById(R.id.music_recommend);
        search_tips = inflate.findViewById(R.id.search_tips);
        music_list = inflate.findViewById(R.id.music_list);
        search_online_title=inflate.findViewById(R.id.search_online_title);
        search_local_title=inflate.findViewById(R.id.search_local_title);
        search_local_title.setOnClickListener(this);
        search_online_title.setOnClickListener(this);
        search_online = inflate.findViewById(R.id.search_online);
        search_local=inflate.findViewById(R.id.search_local);
        search_online.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tip = view.findViewById(R.id.tip);
                keyword.setText(tip.getText());
                searchMusic();
            }
        });
        search_local.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                localTipsAdapter.setIndex(position);
                localTipsAdapter.notifyDataSetChanged();
            }
        });
        views = new ArrayList<>();
        views.add(music_recommend);
        views.add(search_tips);
        views.add(progressBar);
        views.add(music_list);
        showView(0);
        backs.setOnClickListener(this);
        keyword = inflate.findViewById(R.id.keyword);
        keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    showView(0);
                } else {
                    showView(1);
                    getSearchTips(s.toString());
                    initLocalTips(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        keyword.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                flexboxLayout.removeAllViews();
                List<String> search_history = application.appSet.getSearch_history();
                if (search_history == null) return;
                for (String s : search_history) {
                    View view = inflater.inflate(R.layout.mv_tags, null);
                    TextView tag = view.findViewById(R.id.mv_tag);
                    tag.setText(s);
                    tag.setOnClickListener(this);
                    flexboxLayout.addView(view);
                }
            } else {

            }
        });
        keyword.setOnKeyListener(onKeyListener);
        Button search_button = inflate.findViewById(R.id.search_button);
        search_button.setOnClickListener(this);
        getSearch_hot();
        return inflate;

    }

    public void showOrHide(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void getSearchTips(String key) {
        URL url = null;
        try {
            url = new URL("http://searchtipretry.kugou.com/getSearchTip?AlbumTipCount=0&userid=701316656&keyword=" + key + "&student=0&MusicTipCount=10&MVTipCount=0");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpUtil.sendGetRequest(url, handler, SEARCH_TIPS);
    }

    /**
     * 展示页面
     *
     * @param index 下标
     */
    private void showView(int index) {
        for (int i = 0; i < views.size(); i++) {
            if (i == index) {
                views.get(i).setVisibility(View.VISIBLE);
            } else {
                views.get(i).setVisibility(View.GONE);
            }
        }
    }

    /**
     * 获取热搜
     */
    private void getSearch_hot() {
        URL url = null;
        try {
            url = new URL("http://msearchcdn.kugou.com/api/v3/search/hot_tab?navid=1&signature=60f456d41253cd32379b07f7bc9fb4da&appid=1005&apiver=4&clientver=10409&user_labels=val3%253A270596100%252Cval1%253A51204773%252Cval4%253A9%252Cval2%253A281280904&plat=0&osversion=10");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpUtil.sendGetRequest(url, handler, SEARCH_HOT);
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
                    SearchMusicAdapter searchMusicAdapter = new SearchMusicAdapter(application, getContext(), searchMusicInfoData);
                    loadSearchMusicList(searchMusicAdapter);
                    showView(3);
                    break;
                case SEARCH_MUSIC_PLAY_URL:
                    data = data.replace("jQuery19108335867548501714_1603904292877(", "");
                    int s = data.lastIndexOf(";");
                    data = data.substring(0, s);
                    int j = data.lastIndexOf(")");
                    data = data.substring(0, j);
                    SearchMusicPlayUrlData currentMusic = gson.fromJson(data, SearchMusicPlayUrlData.class);
                    musicInfo.setMusicPlayUrlData(currentMusic);
                    View childAt = search_music.getChildAt(index);
                    ImageView singer_head = childAt.findViewById(R.id.singer_head);
                    Glide.with(getContext())
                            .load(musicInfo.getMusicPlayUrlData().getData().getAuthors().get(0).getAvatar())
                            .into(singer_head);
                    playMusic(musicInfo);
                    break;
                case SEARCH_HOT:
                    RecommendMusicData recommendMusicData = gson.fromJson(data, RecommendMusicData.class);
                    initRecommendList(recommendMusicData);
                    break;
                case SEARCH_TIPS:
                    SeachTips tips = gson.fromJson(data, SeachTips.class);
                    initTipList(tips);
                    break;
            }

        }
    };

    /**
     * 初始化本地搜索列表
     */
    private void initLocalTips(String key){
        List<MusicInfo> musicInfos = searchLocal(key);
        localTipsAdapter = new RecentSingleMusicAdapter(getContext(),musicInfos);
        search_local.setAdapter(localTipsAdapter);
        localTipsAdapter.notifyDataSetChanged();

    }

    /**
     * 初始化在线搜索列表
     * @param tips 提示
     */
    private void initTipList(SeachTips tips) {
        SearchTipListAdapter searchTipListAdapter = new SearchTipListAdapter(getContext(), tips);
        search_online.setAdapter(searchTipListAdapter);
        searchTipListAdapter.notifyDataSetChanged();
    }

    private void initRecommendList(RecommendMusicData recommendMusicData) {
        List<com.example.musicplayer.model.music.recommend.List> list = recommendMusicData.getData().getList();
        for (com.example.musicplayer.model.music.recommend.List list1 : list) {
            View view = View.inflate(getContext(), R.layout.recommend_music_block, null);
            TextView title = view.findViewById(R.id.title);
            title.setText(list1.getName());
            LinearLayout linearLayout = view.findViewById(R.id.list);
            for (int i = 0; i < list1.getKeywords().size(); i++) {
                View view1 = View.inflate(getContext(), R.layout.resoubang_item, null);
                int finalI = i;
                view1.setOnClickListener(v -> {
                    showView(2);
                    keyword.setText(list1.getKeywords().get(finalI).getKeyword());
                    searchMusic();
                });
                TextView id = view1.findViewById(R.id.id);
                id.setText("" + (i + 1));
                if (i < 3) {
                    id.setTextColor(Color.RED);
                } else {
                    id.setTextColor(Color.parseColor("#808080"));
                }
                TextView music_name = view1.findViewById(R.id.music_name);
                music_name.setText(list1.getKeywords().get(i).getKeyword());
                linearLayout.addView(view1);
            }
            recommend.addView(view);
        }
    }

    int index;

    private void loadSearchMusicList(SearchMusicAdapter searchMusicAdapter) {
        search_music.removeAllViews();
        for (int i1 = 0; i1 < searchMusicInfoData.getData().getLists().size(); i1++) {
            View view = searchMusicAdapter.getView(i1);
            view.setId(i1);
            search_music.addView(view);
            view.setOnClickListener(v -> {
                index = v.getId();
                searchMusicAdapter.setIndex(v.getId());
                URL url = null;
                try {
                    url = new URL("https://wwwapi.kugou.com/yy/index.php?r=play/getdata&callback=jQuery19108335867548501714_1603904292877&hash=" + searchMusicInfoData.getData().getLists().get(index).getFileHash() + "&album_id=" + searchMusicInfoData.getData().getLists().get(index).getAlbumID() + "&dfid=1tgQ7z1EVOHH196APV4Tfoja&mid=f2eed631d990ecd18a86cc6e39867770&platid=4&_=1603943086161");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                String mvHash = searchMusicInfoData.getData().getLists().get(index).getMvHash();
                musicInfo = new MusicInfo();
                musicInfo.setMvHash(mvHash);
                HttpUtil.sendGetRequest(url, handler, SEARCH_MUSIC_PLAY_URL);
                loadSearchMusicList(searchMusicAdapter);
            });
        }
    }

    View.OnKeyListener onKeyListener = (v, keyCode, event) -> {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            showView(2);
            searchMusic();
            return true;
        }
        return false;
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
            if (application.musicInfos != null) {
                for (MusicInfo musicinfo : application.musicInfos) {
                    if (musicinfo.getMusicPlayUrlData().getData().getHash().equals(currentMusic.getMusicPlayUrlData().getData().getHash())) {
                        flag = true;
                        application.appSet.setCurrentPlayPosition(application.musicInfos.indexOf(musicinfo));
                        break;
                    }
                }
            }
            if (!flag) {
                if (application.musicInfos == null) application.musicInfos = new ArrayList<>();//如果歌单为空的话，新建
                application.musicInfos.add(currentMusic);//将歌曲信息添加进歌单中
                application.appSet.setCurrentPlayPosition(application.musicInfos.size() - 1);
            }
            List<MusicInfo> recentPlay = application.appSet.getRecentPlay();
            if (recentPlay == null) {
                recentPlay = new ArrayList<>();
                recentPlay.add(currentMusic);
                application.appSet.setRecentPlay(recentPlay);//将当前歌曲添加进最近播放列表中
            } else {
                boolean isExist = false;
                for (MusicInfo musicinfo : recentPlay) {
                    if (musicinfo.getMusicPlayUrlData().getData().getHash().equals(currentMusic.getMusicPlayUrlData().getData().getHash())) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    recentPlay.add(currentMusic);
                    application.appSet.setRecentPlay(recentPlay);//将当前歌曲添加进最近播放列表中
                }
            }
            HomePageActivity activity = (HomePageActivity) getActivity();
            assert activity != null;
            activity.getConnection().getMusicControl().changeMusic(application.appSet.getCurrentPlayPosition());
            MusicPlayerApplication.serialization(application.appSet);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backs:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            case R.id.search_button:
                showView(2);
                String text = keyword.getText().toString();
                if (text.equals("")) {
                    Toast.makeText(SearchFragment.this.getContext(), "关键词不能为空,请输入关键词", Toast.LENGTH_LONG).show();
                    return;
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
            case R.id.mv_tag:
                TextView textView = (TextView) v;
                keyword.setText(textView.getText());
                searchMusic();
                break;
            case R.id.search_online_title:
                search_online_title.setBackgroundResource(R.drawable.border_left_selected);
                search_online_title.setTextColor(Color.WHITE);
                search_local_title.setBackgroundResource(R.drawable.border_right);
                search_local_title.setTextColor(Color.BLUE);
                search_online.setVisibility(View.VISIBLE);//在线搜索可见
                search_local.setVisibility(View.GONE);//本地搜索不可见
                break;
            case R.id.search_local_title:
                search_online_title.setBackgroundResource(R.drawable.border_left);
                search_online_title.setTextColor(Color.BLUE);
                search_local_title.setBackgroundResource(R.drawable.border_right_selected);
                search_local_title.setTextColor(Color.WHITE);
                search_online.setVisibility(View.GONE);//在线搜索不可见
                search_local.setVisibility(View.VISIBLE);//本地搜索可见
                break;
        }

    }

    /**
     * 搜索歌曲
     */
    private void searchMusic() {
        String text = keyword.getText().toString();
        if (text.equals("")) {
            Toast.makeText(SearchFragment.this.getContext(), "关键词不能为空,请输入关键词", Toast.LENGTH_LONG).show();
            return;
        }
        showView(2);
        keyword.clearFocus();
        URL url = null;
        try {
            url = new URL("https://songsearch.kugou.com/song_search_v2?callback=jQuery191034642999175022426_1489023388639&keyword=" + text
                    + "&page=1&pagesize=30&userid=-1&clientver=&platform=WebFilter&tag=em&filter=2&iscorrection=1&privilege_filter=0&_=1489023388641");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        boolean isExit = false;
        List<String> search_history = application.appSet.getSearch_history();
        for (String s : search_history) {
            if (s.equals(text)) {
                isExit = true;
            }
        }
        if (!isExit) {
            search_history.add(text);
            application.appSet.setSearch_history(search_history);
            MusicPlayerApplication.serialization(application.appSet);
        }
        HttpUtil.sendGetRequest(url, handler, SEARCH_MUSIC_KEYWORD);
    }

    @Override
    public void update(int command) {

    }

    /**
     * 本地搜索
     * @param key 关键词
     * @return 音乐集合
     */
    private List<MusicInfo> searchLocal(String key){
        List<MusicInfo> recentPlay = application.appSet.getRecentPlay();
        List<MusicInfo> local=new ArrayList<>();
        for (MusicInfo info : recentPlay) {
            if (info.getMusicPlayUrlData().getData().getSongName().contains(key)||info.getMusicPlayUrlData().getData().getAuthorName().contains(key)) {
                local.add(info);
            }
        }
        return local;
    }
}
