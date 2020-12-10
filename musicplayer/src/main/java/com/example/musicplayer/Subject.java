package com.example.musicplayer;


public interface Subject {


    //增加一个观察者
    public void registerObserver(Observer o);

    //删除一个观察者
    public void removeObserver(Observer o);

    //通知所有观察者
    public void notifyObservers(int command);

}