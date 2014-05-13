package com.some.servlet.message.resp;

/**
 * Created by mqddb on 2014/5/12.
 */
public class BaseMessage {

    private String ToUserName;
    private String FromUserName;
    private long CreateTime;
    private String MsgType;
    private int FuncFlag;

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getFromUserName () {
        return FromUserName;
    }

    public void setCreateTime(long time) {
        CreateTime = time;
    }

    public long getCreateTime () {
        return CreateTime;
    }

    public void setMsgType(String respMessageTypeText) {
        MsgType = respMessageTypeText;
    }

    public String getMsgType () {
        return MsgType;
    }

    public void setFuncFlag(int i) {
        FuncFlag = i;
    }

    public int getFuncFlag () {
        return FuncFlag;
    }

}
