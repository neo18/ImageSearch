package com.naveen.android.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Naveen Rawat on 01-06-2016.
 */
public class PageDetail {

    @SerializedName("pageid")
    long pageid;

    @SerializedName("title")
    String title;

    @SerializedName("index")
    int index;

    @SerializedName("thumbnail")
    ThumbDetail thumbnail;

    public long getPageid() {
        return pageid;
    }

    public String getTitle() {
        return title;
    }

    public int getIndex() {
        return index;
    }

    public ThumbDetail getThumbnail() {
        return thumbnail;
    }

    @Override
    public String toString() {
        return "PageDetail{" +
                "pageid=" + pageid +
                ", title='" + title + '\'' +
                ", index=" + index +
                ", thumbnail=" + thumbnail +
                '}';
    }
}
