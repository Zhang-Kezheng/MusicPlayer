
package com.example.musicplayer.model.mv.detail;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Tag implements Serializable {

    @SerializedName("parent_id")
    private String parentId;
    @SerializedName("tag_id")
    private String tagId;
    @SerializedName("tag_name")
    private String tagName;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

}
