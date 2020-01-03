package com.example.chatdemo.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.chatdemo.Adapter.MyRecyclerViewAdapter;
import com.example.chatdemo.Adapter.MyRecyclerViewAdapter_customer;
import com.example.chatdemo.Adapter.MyRecyclerViewAdapter_question;
import com.example.chatdemo.Constants.Constant_url;
import com.example.chatdemo.Models.Message_model;
import com.example.chatdemo.Api_Responce.conversation_api;
import com.example.chatdemo.Models.User;
import com.example.chatdemo.Models.question_model;
import com.example.chatdemo.R;
import com.example.chatdemo.network_library.SingletonRequestQueue;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ChatBoard extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener
{

    RelativeLayout message_box;
    EditText edit_message_box;
    Button Btn_submit;

    RecyclerView recycleview_user;
    RecyclerView recycleview_customer_service;
    MyRecyclerViewAdapter_customer adapter;

    MyRecyclerViewAdapter_question adapter_question;

    //  Greeting_api greeting_api ;
    conversation_api conversation_api;
    String s_message;
    List<Message_model> cardList2 = null;

    List<question_model> cardList = null;

    FrameLayout dots ;

    int flag_lower_data_click = 1;
    Response.ErrorListener errorListener = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error)
        {
            if (error instanceof NetworkError)
            {
                Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
            } else
            {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_board);


        initialize();

        count_down_timer("greeting1");

        setListView();


        Btn_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                hideKeyboard(ChatBoard.this);
                if (!edit_message_box.getText().toString().equals(""))
                {

                    s_message = edit_message_box.getText().toString();

                    add_user_responce(s_message);
                    load_convo();

                    edit_message_box.setText("");

                }


            }
        });


    }

    public void add_user_responce(String s)
    {

        // cardList2 = new ArrayList<>();

        Message_model card = new Message_model();

        card.setMessage(s);
        card.setUsertype("U");

        cardList2.add(card);

        adapter.notifyDataSetChanged();

    }


    public void initialize()
    {

        edit_message_box = (EditText) findViewById(R.id.edit_messagebox);
        Btn_submit = (Button) findViewById(R.id.btn_submit);
        message_box = (RelativeLayout) findViewById(R.id.message_box);
        recycleview_user = findViewById(R.id.recycleview_user);
        recycleview_customer_service = findViewById(R.id.recycleview_customer_service);


        cardList2 = new ArrayList<>();
        cardList = new ArrayList<>();

    }

    public void load_greetings()
    {

        VolleyLog.DEBUG = true;
        RequestQueue queue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        String uri_page_one = String.format(Constant_url.api_greeting);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri_page_one, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                VolleyLog.wtf(response, "utf-8");
                conversation_api = new Gson().fromJson(response, conversation_api.class);

            }
        }, errorListener)
        {

            @Override
            public Priority getPriority()
            {
                return Priority.LOW;
            }

        };

        queue.add(stringRequest);

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>()
        {

            @Override
            public void onRequestFinished(Request<Object> request)
            {
                try
                {
                    if (request.getCacheEntry() != null)
                    {

                        String cacheValue = new String(request.getCacheEntry().data, "UTF-8");
                        Log.d("API123", request.getCacheKey() + " " + cacheValue);

                        add_data_into_list();

                    }
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }

            }
        });

        count_down_timer("greeting2");

        
    }

    public void load_greetings2()
    {

        VolleyLog.DEBUG = true;
        RequestQueue queue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        String uri_page_one = String.format(Constant_url.api_greeting2);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri_page_one, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {


                VolleyLog.wtf(response, "utf-8");
                conversation_api = new Gson().fromJson(response, conversation_api.class);


            }
        }, errorListener)
        {

            @Override
            public Priority getPriority()
            {
                return Priority.LOW;
            }

        };

        queue.add(stringRequest);

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>()
        {

            @Override
            public void onRequestFinished(Request<Object> request)
            {
                try
                {
                    if (request.getCacheEntry() != null)
                    {
                        String cacheValue = new String(request.getCacheEntry().data, "UTF-8");
                        Log.d("API123", request.getCacheKey() + " " + cacheValue);

                       // add_data_into_list();

                    }
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }


            }
        });
    }



    public void load_convo()
    {


        VolleyLog.DEBUG = true;
        RequestQueue queue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        String uri_page_one = String.format(Constant_url.api_seen1);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri_page_one, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                VolleyLog.wtf(response, "utf-8");
                conversation_api = new Gson().fromJson(response, conversation_api.class);


            }
        }, errorListener)
        {

            @Override
            public Priority getPriority()
            {
                return Priority.LOW;
            }

        };


        queue.add(stringRequest);

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>()
        {

            @Override
            public void onRequestFinished(Request<Object> request)
            {
                try
                {
                    if (request.getCacheEntry() != null)
                    {

                        String cacheValue = new String(request.getCacheEntry().data, "UTF-8");
                        Log.d("API123", request.getCacheKey() + " " + cacheValue);


                    }
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }


            }
        });


    }

    public void add_data_into_list()
    {

        List<conversation_api.message_convo_list> DataObject = conversation_api.getmessageConvoList_function();

        if (DataObject.size() > 0)
        {
            for (conversation_api.message_convo_list Object : DataObject)
            {
                Message_model card = new Message_model();

                card.setMessage(Object.getMessage());
                card.setUsertype(Object.getType());

                cardList2.add(card);


            }

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recycleview_customer_service.setLayoutManager(layoutManager);
            recycleview_customer_service.setItemAnimator(new DefaultItemAnimator());


            adapter = new MyRecyclerViewAdapter_customer
                    (this, cardList2);
            recycleview_customer_service.setAdapter(adapter);


        }


        //_____________________________________________________________________________________________


        List<conversation_api.question_list> DataObject2 = conversation_api.getquestion_list_function();

        if (DataObject2.size() > 0)
        {
            message_box.setVisibility(View.GONE);
            for (conversation_api.question_list Object : DataObject2)
            {
                question_model card = new question_model();

                card.setQuestion(Object.getQuestion());
                card.setQuestionid(Object.getQuestionid());

                cardList.add(card);

            }

        }


        // lower recycle view staggered layout with 2 span count
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, GridLayoutManager.HORIZONTAL);
        recycleview_user.setLayoutManager(staggeredGridLayoutManager);


        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        // recyclerView.setLayoutManager(layoutManager2);
        adapter_question = new MyRecyclerViewAdapter_question(this, cardList);
        adapter_question.setClickListener(ChatBoard.this);
        recycleview_user.setAdapter(adapter_question);





    }


    public void add_data_lower_list()
    {

        flag_lower_data_click = 2;

        VolleyLog.DEBUG = true;
        RequestQueue queue = SingletonRequestQueue.getInstance(this).getRequestQueue();
        String uri_page_one = String.format(Constant_url.api_seen2);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri_page_one, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                VolleyLog.wtf(response, "utf-8");
                conversation_api = new Gson().fromJson(response, conversation_api.class);

            }
        }, errorListener)
        {

            @Override
            public Request.Priority getPriority()
            {
                return Request.Priority.LOW;
            }

        };


        queue.add(stringRequest);

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>()
        {

            @Override
            public void onRequestFinished(Request<Object> request)
            {
                try
                {
                    if (request.getCacheEntry() != null)
                    {
                        String cacheValue = new String(request.getCacheEntry().data, "UTF-8");
                        Log.d("API123", request.getCacheKey() + " " + cacheValue);

                    }
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }


            }
        });


    }

    public void hideKeyboard(Activity activity)
    {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null)
        {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }


    @Override
    public void onItemClick(View view, int position)
    {


        if (flag_lower_data_click == 1)
        {
            String s = adapter_question.getItem(position);

            add_user_responce(s);

            cardList.clear();
            adapter_question.notifyDataSetChanged();

            //  recyclerView.scheduleLayoutAnimation();

            add_data_lower_list();
        }


    }


    public void count_down_timer(String Token)
    {

        final String s_token = Token ;
        new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {



            }
            @Override
            public void onFinish() {


              //  dots.setVisibility(View.GONE);

                
                if (s_token.equals("greeting1"))
                {

                    load_greetings();

                }

                if (s_token.equals("greeting2"))
                {

                    load_greetings2();

                }



            }
        }.start();

    }

    public void setListView()
    {

        View footerView = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
        dots = (FrameLayout)footerView.findViewById(R.id.dots);

    }





}
