package com.example.chatdemo.Models;

public class MessageModel
{
    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getUsertype()
    {
        return usertype;
    }

    public void setUsertype(String usertype)
    {
        this.usertype = usertype;
    }

    String message;
    String usertype;

    public String getEnd_flag()
    {
        return end_flag;
    }

    public void setEnd_flag(String end_flag)
    {
        this.end_flag = end_flag;
    }

    String end_flag;
}
