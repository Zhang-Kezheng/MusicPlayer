package com.example.musicplayer.util;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author 章可政
 * @date 2021/1/30 1:35
 */
public class ImageUtils {
    public static void setImageCircle(Context context, ImageView view,String url){
        final RequestOptions mRequestOptions = RequestOptions.circleCropTransform();
        Glide.with(context)
                .load(url)
                .apply(mRequestOptions)
                .into(view);

    }
}
