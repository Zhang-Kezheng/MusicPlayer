package com.example.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicplayer.R;
import com.example.musicplayer.adapter.MVContentListAdapter;
import com.example.musicplayer.adapter.PageAdapter;
import com.example.musicplayer.model.mv.MV;
import com.example.musicplayer.model.mv.comment.MVCommentData;
import com.example.musicplayer.model.mv.detail.MVDetail;
import com.example.musicplayer.model.mv.greatcount.GreatCount;
import com.example.musicplayer.model.mv.greatcount.GreatStatus;
import com.example.musicplayer.model.mv.playurl.*;
import com.example.musicplayer.model.mv.recommend.MVRecommend;
import com.example.musicplayer.view.WrapContentHeightViewPager;
import com.example.musicplayer.util.HttpUtil;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 章可政
 * @date 2020/10/18 20:27
 */
public class MVActivity extends AppCompatActivity implements View.OnClickListener {
    private JzvdStd mv;//视频组件
    private MVModel mvModel;//mv模型
    private MVDetail mvDetail;//mv详情
    private WrapContentHeightViewPager mv_pager;
    private View mv_detail_page;//mv详情页
    private View mv_comment_page;//mv评论页
    public static final int GET_RECOMMEND_VIDEO = 1;//获取视频推荐
    public static final int Get_Great_Count = 2;//获取点赞数
    public static final int GET_COMMENT_CONTENT = 3;//获取评论内容
    public static final int MV_PLAY_URL = 4;//mv播放url
    public static final int MV_DETAIL = 5;//mv详情
    private final RequestOptions mRequestOptions = RequestOptions.circleCropTransform();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mv_layout);
        Intent intent = getIntent();
        MV mvData = (MV) intent.getSerializableExtra("mv");
        List<View> views = new ArrayList<>();
        LayoutInflater pageInflater = LayoutInflater.from(this);
        mv_detail_page = pageInflater.inflate(R.layout.mv_detail_page, null);
        mv_comment_page = pageInflater.inflate(R.layout.mv_comment_page, null);
        views.add(mv_detail_page);
        views.add(mv_comment_page);
        mv_pager = findViewById(R.id.mv_pager);
        mv_pager.setAdapter(new PageAdapter(views));
        mv_pager.setCurrentItem(0, true);
        mv = findViewById(R.id.mv_view);
        initView(mvData);
        TextView mv_detail_title = findViewById(R.id.mv_detail_title);
        TextView mv_comment_title = findViewById(R.id.mv_comment_title);
        mv_detail_title.setOnClickListener(this);
        mv_comment_title.setOnClickListener(this);
        ImageView mv_page_img = findViewById(R.id.mv_page_img);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mv_page_img.getLayoutParams();
        float horizontalBias = layoutParams.horizontalBias;
        mv_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffsetPixels == 0) return;
                layoutParams.horizontalBias = horizontalBias + positionOffsetPixels / 2160f;
                mv_page_img.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    /**
     * 消息处理
     */
    public Handler handler = new Handler(Looper.getMainLooper()) {
        private final MV mv1 = new MV();
        private String hash;

        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            String data = bundle.getString("data").replace("<!--KG_TAG_RES_START-->", "").replace("<!--KG_TAG_RES_END-->", "");
            Gson gson = new Gson();
            switch (msg.what) {
                case GET_RECOMMEND_VIDEO:
                    LinearLayout mv_recommend = findViewById(R.id.mv_recommend);
                    MVRecommend mvRecommend = gson.fromJson(data, MVRecommend.class);
                    mv_recommend.removeAllViews();
                    LinearLayout mv_related_suggestion_list = mv_detail_page.findViewById(R.id.mv_related_suggestion_list);
                    mv_related_suggestion_list.removeAllViews();
                    for (int i = 0; i < mvRecommend.getData().getInfo().size(); i++) {
                        LayoutInflater inflater = LayoutInflater.from(MVActivity.this);
                        View item = inflater.inflate(R.layout.mv_recommend, null);
                        ImageView mv_img = item.findViewById(R.id.mv_img);
                        Glide.with(MVActivity.this).load(mvRecommend.getData().getInfo().get(i).getImgurl()).into(mv_img);
                        TextView mv_name = item.findViewById(R.id.mv_name);
                        mv_name.setText(mvRecommend.getData().getInfo().get(i).getFilename());
                        TextView singername = item.findViewById(R.id.singername);
                        singername.setText(mvRecommend.getData().getInfo().get(i).getSingername());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.leftMargin = 20;
                        mv_recommend.addView(item, params);
                        View view = inflater.inflate(R.layout.mv_recommend_list_item, null);
                        ImageView mv_recommend_img = view.findViewById(R.id.mv_recommend_img);
                        TextView mv_recommend_videoname = view.findViewById(R.id.mv_recommend_videoname);
                        TextView mv_recommend_singername = view.findViewById(R.id.mv_recommend_singername);
                        RoundedCorners roundedCorners = new RoundedCorners(10);
                        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
                        Glide.with(MVActivity.this).load(mvRecommend.getData().getInfo().get(i).getImgurl()).apply(options).into(mv_recommend_img);
                        mv_recommend_videoname.setText(mvRecommend.getData().getInfo().get(i).getFilename());
                        mv_recommend_singername.setText(mvRecommend.getData().getInfo().get(i).getSingername());
                        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.bottomMargin = 30;
                        mv_related_suggestion_list.addView(view, params);
                        item.setOnClickListener(MVActivity.this);
                        int finalI = i;
                        view.setOnClickListener(v ->
                                {
                                    hash = mvRecommend.getData().getInfo().get(finalI).getHash();
                                    String key = DigestUtils.md5Hex(hash + "kugoumvcloud");
                                    URL url = null;
                                    try {
                                        url = new URL("http://trackermv.kugou.com/interface/index/cmd=100&hash=" + hash +
                                                "&key=" + key + "&pid=6&ext=mp4&ismp3=0");
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                    HttpUtil.sendGetRequest(url, handler, MV_PLAY_URL);
                                }
                        );
                    }
                    break;
                case Get_Great_Count:
                    TextView mv_great_count = mv_detail_page.findViewById(R.id.mv_great_count);
                    GreatCount greatCount = gson.fromJson(data, GreatCount.class);
                    GreatStatus greatStatus = greatCount.getList().get(mvDetail.getData().getInfo().getVideoId() + "");
                    assert greatStatus != null;
                    mv_great_count.setText(greatStatus.getLikenum() + "");
                    break;
                case GET_COMMENT_CONTENT:
                    MVCommentData mvCommentData = gson.fromJson(data, MVCommentData.class);
                    TextView mv_comment_title = findViewById(R.id.mv_comment_title);
                    mv_comment_title.setText("评论" + "(" + mvCommentData.getCount() + ")");
                    TextView mv_comment_first_comment = mv_comment_page.findViewById(R.id.mv_comment_first_comment);
                    if (mvCommentData.getCount() > 999) {
                        mv_comment_first_comment.setText("最新评论" + "(999+)");
                    } else {
                        mv_comment_first_comment.setText("最新评论" + "(" + mvCommentData.getCount() + ")");
                    }
                    RecyclerView mv_comment_list = mv_comment_page.findViewById(R.id.mv_comment_list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MVActivity.this);
                    mv_comment_list.setLayoutManager(layoutManager);
                    mv_comment_list.setAdapter(new MVContentListAdapter(mvCommentData.getList()));
                    break;
                case MV_PLAY_URL:
                    MVModel mvModel = gson.fromJson(data, MVModel.class);
                    mv1.setMvModel(mvModel);
                    URL url = null;
                    try {
                        url = new URL("http://mobilecdn.kugou.com/api/v3/mv/detail?area_code=1&plat=0&mvhash=" + hash + "&with_res_tag=1");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    HttpUtil.sendGetRequest(url, handler, MV_DETAIL);
                    break;
                case MV_DETAIL://请求mv数据返回数据
                    data = data.replace("<!--KG_TAG_RES_START-->", "");
                    data = data.replace("<!--KG_TAG_RES_END-->", "");
                    MVDetail mvDetail = gson.fromJson(data, MVDetail.class);
                    mv1.setMvDetail(mvDetail);
                    initView(mv1);
                    break;
            }
        }
    };

    /**
     * 初始化mv页面的所有数据数据
     *
     * @param mv mv数据
     */
    private void initView(MV mv) {
        mvModel = mv.getMvModel();
        mvDetail = mv.getMvDetail();
        initCommentView();
        initDetailPage();
        initCommentPage();
        initVideoView();
    }

    /**
     * 请求评论页面的数据
     */
    private void initCommentView() {
        Long singerid = mvDetail.getData().getInfo().getAuthors().get(0).getSingerid();
        URL url = null;
        try {
            url = new URL("http://mobilecdnbj.kugou.com/api/v3/singer/mv?singername=" + mvDetail.getData().getInfo().getAuthors().get(0).getSingername() + "&pagesize=20&singerid=" + singerid + "&page=1&with_res_tag=1");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert url != null;
        Log.i("URL", url.toString());
        HttpUtil.sendGetRequest(url, handler, GET_RECOMMEND_VIDEO);
    }


    /**
     * 初始化详情页面
     */
    private void initDetailPage() {
        ImageView mv_great_img = mv_detail_page.findViewById(R.id.mv_great_img);
        ImageView mv_download_img = mv_detail_page.findViewById(R.id.mv_download_img);
        ImageView mv_like_img = mv_detail_page.findViewById(R.id.mv_like_img);
        TextView mv_name = mv_detail_page.findViewById(R.id.mv_name);
        URL getGreatCount = null;
        try {
            getGreatCount = new URL("http://m.comment.service.kugou.com/index.php/commentsv2/getlikenums?code=mvlike&childrenid=1&objects=" + mvDetail.getData().getInfo().getVideoId() + "&kugouid=701316656&appid=1005&clienttoken=00c2c4c3352a75cd78090a65d9eff7e8fb62315070fbe71f727a6cbe0acab328");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpUtil.sendGetRequest(getGreatCount, handler, Get_Great_Count);
        TextView mv_download_count = mv_detail_page.findViewById(R.id.mv_download_count);
        TextView mv_like_count = mv_detail_page.findViewById(R.id.mv_like_count);
        FlexboxLayout mv_tags = mv_detail_page.findViewById(R.id.mv_tags);
        TextView mv_heat = mv_detail_page.findViewById(R.id.mv_heat);
        float historyHeat = mvDetail.getData().getInfo().getHistoryHeat() / 10000f;
        String format = String.format("%.1f", historyHeat);
        mv_heat.setText(format + "万次播放");
        for (int i = 0; i < mvDetail.getData().getInfo().getTags().size(); i++) {
            LayoutInflater inflater = LayoutInflater.from(MVActivity.this);
            View view = inflater.inflate(R.layout.mv_tags, null);
            TextView tag = view.findViewById(R.id.mv_tag);
            tag.setText(mvDetail.getData().getInfo().getTags().get(i).getTagName());
            tag.setOnClickListener(this);
            mv_tags.addView(view);
        }
        TextView mv_public = mv_detail_page.findViewById(R.id.mv_public);
        mv_public.setText("发布：" + mvDetail.getData().getInfo().getUpdate());
        ImageView singer_head_img = mv_detail_page.findViewById(R.id.singer_head_img);
        TextView singername = mv_detail_page.findViewById(R.id.singername);
        Glide.with(this).load(mvDetail.getData().getInfo().getAuthors().get(0).getSingeravatar().replace("{size}", "400")).apply(mRequestOptions).into(singer_head_img);
        singername.setText(mvDetail.getData().getInfo().getAuthors().get(0).getSingername());
        mv_great_img.setOnClickListener(this);
        mv_download_img.setOnClickListener(this);
        mv_like_img.setOnClickListener(this);
        if (!"".equals(mvDetail.getData().getInfo().getRemark())) {
            mv_name.setText(mvDetail.getData().getInfo().getVideoname() + "(" + mvDetail.getData().getInfo().getRemark() + ")");
        } else {
            mv_name.setText(mvDetail.getData().getInfo().getVideoname());
        }
        mv_download_count.setText(mvDetail.getData().getInfo().getDownloadCount() + "");
        mv_like_count.setText(mvDetail.getData().getInfo().getCollectCount() + "");
        ImageView mv_music_img = mv_detail_page.findViewById(R.id.mv_music_img);
        TextView mv_music_musicname = mv_detail_page.findViewById(R.id.mv_music_musicname);
        TextView mv_music_singername = mv_detail_page.findViewById(R.id.mv_music_singername);
        Glide.with(this).load(mvDetail.getData().getInfo().getAudioInfo().getImg().replace("{size}", "150")).into(mv_music_img);
        mv_music_musicname.setText(mvDetail.getData().getInfo().getAudioInfo().getSongname());
        mv_music_singername.setText(mvDetail.getData().getInfo().getAudioInfo().getSingername());
    }

    /**
     * 初始化评论页面，请求所需的所有数据
     */
    private void initCommentPage() {
        URL url = null;
        try {
            url = new URL("http://m.comment.service.kugou.com/index.php?r=comments/getCommentWithLike&code=db3664c219a6e350b00ab08d7f723a79&extdata=" + mvDetail.getData().getInfo().getHash() +
                    "&childrenname=%E5%B0%8F%E6%83%85%E6%AD%8C&p=1&pagesize=20&clienttoken=00c2c4c3352a75cd78090a65d9eff7e8fb62315070fbe71f727a6cbe0acab328&kugouid=701316656&clientver=10309&mid=222509261258459411814045547501909370639&clienttime=1604155163&key=0da0689158348f14c791abb4902a2bdc&appid=1005");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpUtil.sendGetRequest(url, handler, GET_COMMENT_CONTENT);
    }

    /**
     * 初始化视频内容
     */
    private void initVideoView() {
        String imgurl = mvDetail.getData().getInfo().getImgurl();
        imgurl = imgurl.replace("{size}", "400");
        Glide.with(this).load(imgurl).into(mv.posterImageView);
        Hd hd = mvModel.getMvdata().getHd();
        Rq rq = mvModel.getMvdata().getRq();
        Sd sd = mvModel.getMvdata().getSd();
        Sq sq = mvModel.getMvdata().getSq();
        if (hd != null) {
            if (!"".equals(hd.getDownurl()) && hd.getDownurl() != null) {
                mv.setUp(hd.getDownurl(), mvModel.getSongname());
                return;
            }
        }
        if (rq != null) {
            if (!"".equals(rq.getDownurl()) && rq.getDownurl() != null) {
                mv.setUp(rq.getDownurl(), mvModel.getSongname());
                return;
            }
        }
        if (sd != null) {
            if (!"".equals(sd.getDownurl()) && sd.getDownurl() != null) {
                mv.setUp(sd.getDownurl(), mvModel.getSongname());
                return;
            }
        }
        if (sq != null) {
            if (!"".equals(sq.getDownurl()) && sq.getDownurl() != null) {
                mv.setUp(sq.getDownurl(), mvModel.getSongname());
            }
        } else {
            Toast.makeText(this, "暂无资源", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    /**
     * 点击事件
     *
     * @param v 视图
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mv_detail_title://点击详情跳转页面
                mv_pager.setCurrentItem(0, true);
                break;
            case R.id.mv_comment_title://点击评论跳转页面
                mv_pager.setCurrentItem(1, true);
                break;
        }
    }

}
