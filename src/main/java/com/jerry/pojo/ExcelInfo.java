package com.jerry.pojo;

/**
 * Created by yingmule on 2018/7/6.
 */
public class ExcelInfo {
    private int novelId;
    private int userId;
    private String content;
    private String subject;

    public int getNovelId() {
        return novelId;
    }

    public void setNovelId(int novelId) {
        this.novelId = novelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "ExcelInfo{" +
                "novelId=" + novelId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}