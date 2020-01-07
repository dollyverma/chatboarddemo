package com.example.chatdemo.Activity;

import android.app.Activity;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chatdemo.Adapter.MyRecyclerViewAdapter_customer;
import com.example.chatdemo.Adapter.MyRecyclerViewAdapter_demo;
import com.example.chatdemo.Adapter.MyRecyclerViewAdapter_question;
import com.example.chatdemo.Api_Responce.ConversationApi;
import com.example.chatdemo.Constants.ConstantUrl;
import com.example.chatdemo.Models.MessageModel;
import com.example.chatdemo.Models.QuestionModel;
import com.example.chatdemo.NetworkLibrary.SingletonRequestQueue;
import com.example.chatdemo.R;
import com.example.chatdemo.utils.UIUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ChatBoardActivity extends AppCompatActivity implements MyRecyclerViewAdapter_demo.ItemClickListener
{

    RelativeLayout relaytiveLayout_message_box;
    EditText edit_message_box;
    Button Btn_submit;

    RecyclerView recycleview_user;
    RecyclerView recycleview_customer_service;
    MyRecyclerViewAdapter_customer obj_MyRecyclerViewAdapter_customer;
    MyRecyclerViewAdapter_question obj_MyRecyclerViewAdapter_question;

    ConversationApi conversation_api;
    String s_message;
    List<MessageModel> obj_messageModel = null;

    List<MessageModel> obj_messageModel2 = null;

    List<QuestionModel> obj_questionModel = null;

    FrameLayout dots;

    int int_flagLowerDataClick = 1;

    Gson gson;

    Handler  handler;


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

        getHomeBannerAndCategorylist();


       //  count_down_timer("greeting1");

        initAdapterCustomer();

        //  initAdapterUser();

//        handler = new Handler();
//
//
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//
//                obj_MyRecyclerViewAdapter_customer.add();
//                handler.postDelayed(this, 2000);
//            }
//        };
//
//        handler.postDelayed(runnable, 2000);


        Btn_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                hideKeyboard(ChatBoardActivity.this);
                if (!edit_message_box.getText().toString().equals(""))
                {

                    s_message = edit_message_box.getText().toString();

                    addUserResponce(s_message);

                    edit_message_box.setText("");

                }
            }
        });

    }


    private void initAdapterCustomer()
    {

        obj_MyRecyclerViewAdapter_customer = new MyRecyclerViewAdapter_customer(this, obj_messageModel);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleview_customer_service.setLayoutManager(layoutManager);
        recycleview_customer_service.setAdapter(obj_MyRecyclerViewAdapter_customer);

    }

    private void initAdapterUser()
    {
        // lower recycle view staggered layout with 2 span count
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, GridLayoutManager.HORIZONTAL);
        recycleview_user.setLayoutManager(staggeredGridLayoutManager);


        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        // recyclerView.setLayoutManager(layoutManager2);
        obj_MyRecyclerViewAdapter_question = new MyRecyclerViewAdapter_question(this, obj_questionModel);
        obj_MyRecyclerViewAdapter_question.setClickListener(ChatBoardActivity.this);
        recycleview_user.setAdapter(obj_MyRecyclerViewAdapter_question);
    }


    public void addUserResponce(String s)
    {


        MessageModel card = new MessageModel();

        card.setMessage(s);
        card.setUsertype("U");
        card.setEnd_flag("");

        obj_messageModel.add(card);

        obj_MyRecyclerViewAdapter_customer.notifyDataSetChanged();

        // count_down_timer("load_seen1");

        relaytiveLayout_message_box.setVisibility(View.GONE);
        getHomeBannerAndCategorylist2();


    }


    public void initialize()
    {

        edit_message_box = (EditText) findViewById(R.id.edit_messagebox);
        Btn_submit = (Button) findViewById(R.id.button_submit);
        relaytiveLayout_message_box = (RelativeLayout) findViewById(R.id.relativeLayout_message_box);
        recycleview_user = findViewById(R.id.recycleview_user);
        recycleview_customer_service = findViewById(R.id.recycleview_customer_service);
        dots = (FrameLayout) findViewById(R.id.frameLayout_dots);

        obj_messageModel = new ArrayList<>();
        obj_messageModel2 = new ArrayList<>();

        obj_questionModel = new ArrayList<>();
        gson = new Gson();


    }

    public void load_greetings()
    {

        VolleyLog.DEBUG = true;
        RequestQueue queue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        String uri_page_one = String.format(ConstantUrl.api_greeting);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri_page_one, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                Log.d("responce" , response);
                
                VolleyLog.wtf(response, "utf-8");
                //   conversation_api = new Gson().fromJson(response, ConversationApi.class);

                conversation_api = gson.fromJson(response, ConversationApi.class);

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

                        dots.setVisibility(View.GONE);

                        add_data_into_list();

                    }
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }

            }
        });


    }


    public void load_seen1()
    {


        VolleyLog.DEBUG = true;
        RequestQueue queue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        String uri_page_one = String.format(ConstantUrl.api_seen1);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri_page_one, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                VolleyLog.wtf(response, "utf-8");

                conversation_api = gson.fromJson(response, ConversationApi.class);
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

        //count_down_timer("load_seen2");
    }


    public void add_data_into_list()
    {

        List<ConversationApi.message_convo_list> DataObject = conversation_api.getmessageConvoList_function();

        if (DataObject.size() > 0)
        {
            for (ConversationApi.message_convo_list Object : DataObject)
            {
                MessageModel card = new MessageModel();

                card.setMessage(Object.getMessage());
                card.setUsertype(Object.getType());
                card.setEnd_flag(Object.getEnd_flag());

                obj_messageModel.add(card);


            }

            loadAnimation();


        }


        //_____________________________________________________________________________________________


        List<ConversationApi.question_list> DataObject2 = conversation_api.getquestion_list_function();

        if (DataObject2.size() > 0)
        {
            relaytiveLayout_message_box.setVisibility(View.GONE);
            for (ConversationApi.question_list Object : DataObject2)
            {
                QuestionModel card = new QuestionModel();

                card.setQuestion(Object.getQuestion());
                card.setQuestionid(Object.getQuestionid());

                obj_questionModel.add(card);

            }

        }


        initAdapterUser();


    }


    public void add_data_lower_list()
    {

        int_flagLowerDataClick = 2;

        VolleyLog.DEBUG = true;
        RequestQueue queue = SingletonRequestQueue.getInstance(this).getRequestQueue();
        String uri_page_one = String.format(ConstantUrl.api_seen1);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri_page_one, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                VolleyLog.wtf(response, "utf-8");
                conversation_api = new Gson().fromJson(response, ConversationApi.class);

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


        if (int_flagLowerDataClick == 1)
        {
            String s = obj_MyRecyclerViewAdapter_question.getItem(position);

            addUserResponce(s);

            obj_questionModel.clear();
            obj_MyRecyclerViewAdapter_question.notifyDataSetChanged();

            add_data_lower_list();
        }


    }


    public void count_down_timer(String Token)
    {

        final String s_token = Token;
        new CountDownTimer(3000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {


            }

            @Override
            public void onFinish()
            {


                if (s_token.equals("greeting1"))
                {


                    load_greetings();

                    relaytiveLayout_message_box.setVisibility(View.VISIBLE);


                }


                if (s_token.equals("load_seen1"))
                {

                    relaytiveLayout_message_box.setVisibility(View.GONE);
                    load_seen1();

                }


            }
        }.start();

    }


    public void loadAnimation()
    {
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_slide_right);

        recycleview_customer_service.setLayoutAnimation(controller);
        obj_MyRecyclerViewAdapter_customer.notifyDataSetChanged();
        recycleview_customer_service.scheduleLayoutAnimation();


    }


    public void getHomeBannerAndCategorylist()
    {
        try
        {
            if (UIUtils.isNetworkAvailable(ChatBoardActivity.this))
            {
                try
                {
                    final String URL = ConstantUrl.api_greeting;
                    System.out.println("url---->" + URL);
                    // Initialize a new RequestQueue instance
                    RequestQueue requestQueue = Volley.newRequestQueue(ChatBoardActivity.this);
                    JSONObject body = new JSONObject();

                    System.out.println("homepage  Body " + body);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, body,
                            new Response.Listener<JSONObject>()
                            {
                                @Override
                                public void onResponse(JSONObject response)
                                {
                                    int count = 0;
                                    System.out.println("response--->" + response);
                                    try
                                    {
//                                        if (response.has("message_convo"))
//                                        {


                                        JSONArray Array_vedio = response.getJSONArray("message_convo");

                                        if (!Array_vedio.equals(""))
                                        {


                                            for (int k = 0; k < Array_vedio.length(); k++)
                                            {

                                                JSONObject object_video = Array_vedio.getJSONObject(k);


                                                String message = object_video.has("message") ? object_video.getString("message") : "";

                                                String typeOfMessage = object_video.has("type") ? object_video.getString("type") : "";

                                                MessageModel card = new MessageModel();

                                                card.setMessage(message);
                                                card.setUsertype(typeOfMessage);
                                                obj_messageModel.add(card);

//
//                                                long initialTime = System.currentTimeMillis();
//                                                long now = System.currentTimeMillis();
//                                                while(now - initialTime < 3000){
//                                                    now = System.currentTimeMillis();
//                                                }
//
//                                                  obj_MyRecyclerViewAdapter_customer.notifyDataSetChanged();


                                                //  obj_MyRecyclerViewAdapter_customer.add(card);

                                            }




                                            obj_MyRecyclerViewAdapter_customer.notifyDataSetChanged();

                                         //   loadAnimation();
                                            relaytiveLayout_message_box.setVisibility(View.VISIBLE);





                                        }

                                        JSONArray Array_question = response.getJSONArray("question");

                                        if (!Array_question.equals(""))
                                        {
                                            for (int k = 0; k < Array_question.length(); k++)
                                            {

                                                JSONObject object_video = Array_question.getJSONObject(k);


                                                String question = object_video.has("question") ? object_video.getString("question") : "";

                                                String questionid = object_video.has("questionid") ? object_video.getString("questionid") : "";


                                                QuestionModel card = new QuestionModel();

                                                card.setQuestion(question);
                                                card.setQuestionid(questionid);
                                                //  card.setEnd_flag(Object.getEnd_flag());

                                                obj_questionModel.add(card);

                                            }

                                            initAdapterUser();


                                        }


                                        //  }


                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    Log.e("error", error.toString());
                                }
                            }
                    )
                    {

                    };

                    requestQueue.add(jsonObjectRequest);
                    jsonObjectRequest.setRetryPolicy(new
                            DefaultRetryPolicy(5000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                }
                catch (Exception e)
                {
                    e.printStackTrace();

                }
            } else
            {

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void getHomeBannerAndCategorylist2()
    {
        try
        {
            if (UIUtils.isNetworkAvailable(ChatBoardActivity.this))
            {
                try
                {
                    final String URL = ConstantUrl.api_seen1;
                    System.out.println("url---->" + URL);
                    // Initialize a new RequestQueue instance
                    RequestQueue requestQueue = Volley.newRequestQueue(ChatBoardActivity.this);
                    JSONObject body = new JSONObject();

                    System.out.println("homepage  Body " + body);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, body,
                            new Response.Listener<JSONObject>()
                            {
                                @Override
                                public void onResponse(JSONObject response)
                                {
                                    int count = 0;
                                    System.out.println("response--->" + response);
                                    try
                                    {
//                                        if (response.has("message_convo"))
//                                        {


                                        JSONArray Array_vedio = response.getJSONArray("message_convo");

                                        if (!Array_vedio.equals(""))
                                        {
                                            for (int k = 0; k < Array_vedio.length(); k++)
                                            {

                                                JSONObject object_video = Array_vedio.getJSONObject(k);


                                                String message = object_video.has("message") ? object_video.getString("message") : "";

                                                String typeOfMessage = object_video.has("type") ? object_video.getString("type") : "";


                                                MessageModel card = new MessageModel();

                                                card.setMessage(message);
                                                card.setUsertype(typeOfMessage);
                                                //  card.setEnd_flag(Object.getEnd_flag());

                                                obj_messageModel.add(card);

                                            }

                                            obj_MyRecyclerViewAdapter_customer.notifyDataSetChanged();
                                             loadAnimation();
                                            //relaytiveLayout_message_box.setVisibility(View.VISIBLE);


                                        }

                                        JSONArray Array_question = response.getJSONArray("question");

                                        if (!Array_question.equals(""))
                                        {
                                            for (int k = 0; k < Array_question.length(); k++)
                                            {

                                                JSONObject object_video = Array_question.getJSONObject(k);


                                                String question = object_video.has("question") ? object_video.getString("question") : "";

                                                String questionid = object_video.has("questionid") ? object_video.getString("questionid") : "";


                                                QuestionModel card = new QuestionModel();

                                                card.setQuestion(question);
                                                card.setQuestionid(questionid);
                                                //  card.setEnd_flag(Object.getEnd_flag());

                                                obj_questionModel.add(card);

                                            }

                                            initAdapterUser();


                                        }


                                        //  }


                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    Log.e("error", error.toString());
                                }
                            }
                    )
                    {

                    };

                    requestQueue.add(jsonObjectRequest);
                    jsonObjectRequest.setRetryPolicy(new
                            DefaultRetryPolicy(5000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                }
                catch (Exception e)
                {
                    e.printStackTrace();

                }
            } else
            {

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
