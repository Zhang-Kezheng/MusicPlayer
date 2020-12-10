package com.example.musicplayer.pojo;



import java.io.Serializable;

/**
 * @author 章可政
 * @date 2020/10/16 14:27
 */
public class User implements Serializable {
    private Integer id;
    private Integer informationId;
    private Information information;

    public User(Integer id, Integer informationId, Information information) {
        this.id = id;
        this.informationId = informationId;
        this.information = information;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInformationId() {
        return informationId;
    }

    public void setInformationId(Integer informationId) {
        this.informationId = informationId;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }
}
