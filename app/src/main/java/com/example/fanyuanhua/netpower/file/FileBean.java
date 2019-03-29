package com.example.fanyuanhua.netpower.file;

import android.support.annotation.NonNull;

public class FileBean implements Comparable<FileBean> {
    private String name;
    private String path;
    // 0 是音频  1 是txt
    private int type;
    private long updateTime;
    private String length;
    private long duration;

    public FileBean() {
    }


    public FileBean(String name, String path, int type, long updateTime, String length) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.updateTime = updateTime;
        this.length = length;
    }

    public FileBean(String name, String path, int type, long updateTime, String length, long duration) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.updateTime = updateTime;
        this.length = length;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }


    @Override
    public int compareTo(@NonNull FileBean o) {
        return (int)(o.getUpdateTime() - this.getUpdateTime());
    }
}
