package com.naveen.android.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Naveen Rawat on 01-06-2016.
 */
public class ThumbDetail {

    @SerializedName("source")
    String source;

    @SerializedName("width")
    int width;

    @SerializedName("height")
    int height;

    public String getSource() {
        return source;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "ThumbnailDetail{" +
                "source='" + source + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
