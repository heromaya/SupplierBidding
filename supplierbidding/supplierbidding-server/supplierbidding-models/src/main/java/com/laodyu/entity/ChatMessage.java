package com.laodyu.entity;

import java.util.Date;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/6/4
 * @Version 1.0
 **/
public class ChatMessage {
    private String from  ;
    private String to;
    private String content;
    private Date date;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
