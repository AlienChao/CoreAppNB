package com.powerrich.corelib.msg;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/30
 * 版权：
 *
 * RxBus 中的消息体
 *
 */
public class ProgressMsg {
    private long progress;
    private long size;
    private String key;

    public ProgressMsg(int progress, String key) {
        this.progress = progress;
        this.key = key;
    }

    public ProgressMsg(long progress, long size, String key) {
        this.progress = progress;
        this.size = size;
        this.key = key;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getProgress() {
        return progress;
    }

    public ProgressMsg setProgress(long progress) {
        this.progress = progress;
        return this;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
