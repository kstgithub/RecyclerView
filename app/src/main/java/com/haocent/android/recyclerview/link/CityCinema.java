package com.haocent.android.recyclerview.link;

import android.os.Parcel;
import android.os.Parcelable;

public class CityCinema implements Parcelable {

    private String name;

    private String titleName;

    private String tag;

    private boolean isTitle;

    private String imgsrc;

    public CityCinema(String name) {
        this.name = name;
    }

    protected CityCinema(Parcel in) {
        name = in.readString();
        titleName = in.readString();
        tag = in.readString();
        isTitle = in.readByte() != 0;
        imgsrc = in.readString();
    }

    public static final Creator<CityCinema> CREATOR = new Creator<CityCinema>() {
        @Override
        public CityCinema createFromParcel(Parcel in) {
            return new CityCinema(in);
        }

        @Override
        public CityCinema[] newArray(int size) {
            return new CityCinema[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(titleName);
        dest.writeString(tag);
        dest.writeByte((byte) (isTitle ? 1 : 0));
        dest.writeString(imgsrc);
    }
}
