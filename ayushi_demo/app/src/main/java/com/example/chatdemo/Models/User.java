package com.example.chatdemo.Models;

public class User
{
    public String name;
    public String hometown;

    public  String flag_user ,      flag_cutomercare ;

    public  int position ;

    public User(int position , String name , String flag_user , String flag_cutomercare) {
        this.name = name;
        this.flag_cutomercare = flag_cutomercare ;
        this . flag_user = flag_user ;
        this . position =  position ;

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getHometown()
    {
        return hometown;
    }

    public void setHometown(String hometown)
    {
        this.hometown = hometown;
    }
}