package com.example.musicplayer.set;

import org.litepal.crud.LitePalSupport;

/**
 * @author 章可政
 * @date 2021/1/8 1:12
 */

public class Search_history extends LitePalSupport {
    private String search_history;

    public String getSearch_history() {
        return search_history;
    }

    public void setSearch_history(String search_history) {
        this.search_history = search_history;
    }
}
