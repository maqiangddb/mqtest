package com.some.servlet.message.resp;

/**
 * Created by mqddb on 2014/5/12.
 */
public class TextMessage extends BaseMessage {

    private String Content;

    public void setContent(String respContent) {
        Content = respContent;
    }

    public String getContent(String content) {
        return Content;
    }
}
