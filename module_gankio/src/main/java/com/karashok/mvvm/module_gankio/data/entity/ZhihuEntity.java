package com.karashok.mvvm.module_gankio.data.entity;

import java.util.List;

/**
 * @author KaraShok(zhangyaozhong)
 * @name ZhihuEntity
 * DESCRIPTION
 * @date 2018/06/27/上午11:48
 */

public class ZhihuEntity {

    private String date;
    private List<ZhihuStoriesEntity> stories;
    private List<ZhihuTopStoriesEntity> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhihuStoriesEntity> getStories() {
        return stories;
    }

    public void setStories(List<ZhihuStoriesEntity> stories) {
        this.stories = stories;
    }

    public List<ZhihuTopStoriesEntity> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<ZhihuTopStoriesEntity> top_stories) {
        this.top_stories = top_stories;
    }
}
