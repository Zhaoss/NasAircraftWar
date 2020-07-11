package com.zhaoshuang.nasaircraftwar.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by zhaoshuang on 2018/6/1.
 */

public class RankingBean {

    @Ignore
    public final static String SCORE = "score";

    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("has")
    private String has;
    @Column("date")
    private String date;
    @Column(SCORE)
    private String score;
    @Column("name")
    private String name;

    public RankingBean(String date, String has, String score, String name) {
        this.date = date;
        this.has = has;
        this.score = score;
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHas() {
        return has;
    }

    public void setHas(String has) {
        this.has = has;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
