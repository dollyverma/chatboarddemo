package com.example.chatdemo.DataInterface;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface ResponseCarry
{
    void APIResponse(JSONObject Response);

    void ErrorResponse(VolleyError Error);
}
