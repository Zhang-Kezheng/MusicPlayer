package com.example.musicplayer;


public interface Subject {


    /**
     * 增加观察者
     * @param o 观察者
     */
    public void registerObserver(Observer o);

    /**
     * 删除观察者
     * @param o 观察者
     */
    public void removeObserver(Observer o);

    /**
     * 通知所有观察者
     * @param command 命令
     */
    public void notifyObservers(int command);

}