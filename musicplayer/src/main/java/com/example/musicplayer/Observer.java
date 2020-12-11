package com.example.musicplayer;

public interface Observer {
    /**
     * 更新视图
     * @param command 命令
     */
    void update(int command);
}
