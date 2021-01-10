
package com.example.musicplayer.model.music.searchmusicplayurl;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Author  implements Serializable {

    @SerializedName("author_id")
    private String authorId;
    @SerializedName("author_name")
    private String authorName;
    @Expose
    private String avatar;
    @SerializedName("is_publish")
    private String isPublish;
    @SerializedName("sizable_avatar")
    private String sizableAvatar;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(String isPublish) {
        this.isPublish = isPublish;
    }

    public String getSizableAvatar() {
        return sizableAvatar;
    }

    public void setSizableAvatar(String sizableAvatar) {
        this.sizableAvatar = sizableAvatar;
    }

}
