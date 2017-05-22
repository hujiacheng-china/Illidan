package com.hujiacheng.commonframe.okhttp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuJiaCheng on 2017/3/4.
 */

public class DataBean implements Parcelable {

    @Override
    public String toString() {
        return "DataBean{" +
                "trailers=" + trailers +
                '}';
    }

    private List<TrailersBean> trailers;

    public List<TrailersBean> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<TrailersBean> trailers) {
        this.trailers = trailers;
    }

    public static class TrailersBean implements Parcelable {
        /**
         * id : 64768
         * movieName : 《银河护卫队2》中文预告片
         * coverImg : http://img5.mtime.cn/mg/2017/03/01/164416.40461950.jpg
         * movieId : 216008
         * url : http://vfx.mtime.cn/Video/2017/03/01/mp4/170301133120672165_480.mp4
         * hightUrl : http://vfx.mtime.cn/Video/2017/03/01/mp4/170301133120672165.mp4
         * videoTitle : 银河护卫队2 中文终极版预告
         * videoLength : 143
         * rating : 8.7
         * type : ["动作","科幻"]
         * summary : 废柴英雄斗嘴耍帅两不误
         */

        private int id;
        private String movieName;
        private String coverImg;
        private int movieId;
        private String url;
        private String hightUrl;
        private String videoTitle;
        private int videoLength;
        private double rating;
        private String summary;
        private List<String> type;

        @Override
        public String toString() {
            return "TrailersBean{" +
                    "id=" + id +
                    ", movieName='" + movieName + '\'' +
                    ", coverImg='" + coverImg + '\'' +
                    ", movieId=" + movieId +
                    ", url='" + url + '\'' +
                    ", hightUrl='" + hightUrl + '\'' +
                    ", videoTitle='" + videoTitle + '\'' +
                    ", videoLength=" + videoLength +
                    ", rating=" + rating +
                    ", summary='" + summary + '\'' +
                    ", type=" + type +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHightUrl() {
            return hightUrl;
        }

        public void setHightUrl(String hightUrl) {
            this.hightUrl = hightUrl;
        }

        public String getVideoTitle() {
            return videoTitle;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }

        public int getVideoLength() {
            return videoLength;
        }

        public void setVideoLength(int videoLength) {
            this.videoLength = videoLength;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.movieName);
            dest.writeString(this.coverImg);
            dest.writeInt(this.movieId);
            dest.writeString(this.url);
            dest.writeString(this.hightUrl);
            dest.writeString(this.videoTitle);
            dest.writeInt(this.videoLength);
            dest.writeDouble(this.rating);
            dest.writeString(this.summary);
            dest.writeStringList(this.type);
        }

        public TrailersBean() {
        }

        protected TrailersBean(Parcel in) {
            this.id = in.readInt();
            this.movieName = in.readString();
            this.coverImg = in.readString();
            this.movieId = in.readInt();
            this.url = in.readString();
            this.hightUrl = in.readString();
            this.videoTitle = in.readString();
            this.videoLength = in.readInt();
            this.rating = in.readDouble();
            this.summary = in.readString();
            this.type = in.createStringArrayList();
        }

        public static final Creator<TrailersBean> CREATOR = new Creator<TrailersBean>() {
            @Override
            public TrailersBean createFromParcel(Parcel source) {
                return new TrailersBean(source);
            }

            @Override
            public TrailersBean[] newArray(int size) {
                return new TrailersBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.trailers);
    }

    public DataBean() {
    }

    protected DataBean(Parcel in) {
        this.trailers = new ArrayList<TrailersBean>();
        in.readList(this.trailers, TrailersBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
        @Override
        public DataBean createFromParcel(Parcel source) {
            return new DataBean(source);
        }

        @Override
        public DataBean[] newArray(int size) {
            return new DataBean[size];
        }
    };
}
