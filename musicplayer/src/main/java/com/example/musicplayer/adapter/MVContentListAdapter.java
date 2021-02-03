package com.example.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicplayer.R;

import java.util.List;

/**
 * @author 章可政
 * @date 2020/11/1 20:11
 */
public class MVContentListAdapter extends RecyclerView.Adapter<MVContentListAdapter.ViewHolder>{
    private final List<com.example.musicplayer.model.mv.comment.List> data;
    private Context context;
    @NonNull
    @Override
    public MVContentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mv_comment_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        context=parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MVContentListAdapter.ViewHolder holder, int position) {
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform();
        Glide.with(context).load(data.get(position).getUserPic()).apply(mRequestOptions).into(holder.mv_comment_user_img);
        holder.mv_comment_user_name.setText(data.get(position).getUserName());
        holder.mv_comment_update_date.setText(data.get(position).getAddtime());
        holder.mv_comment_content.setText(data.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mv_comment_user_img;
        TextView mv_comment_user_name;
        TextView mv_comment_update_date;
        TextView mv_comment_content;

        public ViewHolder (View view)
        {
            super(view);
            mv_comment_user_img=view.findViewById(R.id.mv_comment_user_img);
            mv_comment_user_name=view.findViewById(R.id.mv_comment_user_name);
            mv_comment_update_date=view.findViewById(R.id.mv_comment_update_date);
            mv_comment_content=view.findViewById(R.id.mv_comment_content);
        }

    }

    public MVContentListAdapter(List<com.example.musicplayer.model.mv.comment.List> data) {
        this.data = data;
    }
}
