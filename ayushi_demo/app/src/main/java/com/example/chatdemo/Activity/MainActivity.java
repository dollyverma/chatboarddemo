package com.example.chatdemo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.chatdemo.Adapter.MyRecyclerViewAdapter_demo;
import com.example.chatdemo.R;
import com.example.chatdemo.Models.User;
import com.example.chatdemo.Adapter.UsersAdapter;
import com.example.chatdemo.Constants.KeyValue;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements MyRecyclerViewAdapter_demo.ItemClickListener
{

    RelativeLayout message_box  ;
    private ListView lv;
    EditText  edit_message_box ;
    Button Btn_submit ;
    String s_message ;

    ArrayList<User> arrayOfUsers;
    UsersAdapter adapter ;
    User newUser ;

    int last_position_of_list = 0 ;      

    String username = "";

    String list_umm_why [] = {"People who save towards a goal save more." , "Almost 15 % more, accprding to research." } ;
    String list_1 [] = {"Hello! I'm ACC." , "what's your name ?" } ;
    String list_2 [] = {"Great to meet you, " , "I can help you save regularly, without hassle." , "Lets start by setting a saving goals."} ;

    MyRecyclerViewAdapter_demo token_adapter;
    RecyclerView recyclerView ;

    public int counter;
    FrameLayout dots ;

    KeyValue key_ ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ini();
        initialize()  ;
        setListView();
       

        edit_message_box.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (edit_message_box.getText().toString().equals(""))
                {
                    Btn_submit.setTextColor(getResources().getColor(R.color.gray));
                }
                else
                {
                    Btn_submit.setTextColor(getResources().getColor(R.color.colorPrimary));
                }


            }
        });

        
        
        Btn_submit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    hideKeyboard(MainActivity.this);
                    if (! edit_message_box.getText().toString().equals(""))
                    {
                       

                        s_message = edit_message_box.getText().toString();

                        newUser = new User(1 , s_message , "U" , "");
                        adapter.add(newUser);


                        last_position_of_list =  adapter.return_last_position_of_list();
                        if (last_position_of_list == 1)
                        {
                            username =  edit_message_box.getText().toString();

                        }

                        edit_message_box.setText("");
                      //  set_next_data();
                        dots.setVisibility(View.VISIBLE);
                        count_down_timer("Token1");



                    }

                }
            });



    }



    public void ini()
    {

        lv = (ListView) findViewById(R.id.l1);
        edit_message_box = (EditText)findViewById(R.id.edit_messagebox);
        Btn_submit = (Button)findViewById(R.id.button_submit);
        message_box = (RelativeLayout)findViewById(R.id.message_box);
        recyclerView = findViewById(R.id.rvAnimals);


    }

    public void initialize()
    {
        key_ = new KeyValue() ;
        arrayOfUsers = new ArrayList<User>();
        // Create the adapter to convert the array to views
        adapter = new UsersAdapter(this, arrayOfUsers);

    
        count_down_timer("start");


    }

    public void setListView()
    {

        lv.setAdapter(adapter);
        View footerView = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
        lv.addFooterView(footerView);
        dots = (FrameLayout)footerView.findViewById(R.id.dots);

    }

    public void LoadData()
    {
        
    }
    public void set_next_data()
    {
        last_position_of_list =  adapter.return_last_position_of_list();


        if(last_position_of_list == 2)
        {

            int i ;
            for (i = 0 ; i<3 ; i++)
            {

                if(i == 0)
                {
                    newUser = new User(0 , list_2[0]+username, "" , "C");
                }
                else
                {
                    newUser = new User(0 , list_2[i], "" , "C");
                }

                adapter.add(newUser);

            }
            if(i == 3)
            {
                message_box.setVisibility(View.GONE);
                setTokan_1();

            }
        }
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        scrollMyListViewToBottom();


    }


    private void scrollMyListViewToBottom() {
        lv.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                lv.setSelection(adapter.getCount() - 1);
            }
        });
    }

    public void setTokan_1()
    {

        recyclerView.setVisibility(View.VISIBLE);

        // data to populate the RecyclerView with
        ArrayList<String > animalNames = new ArrayList<>();
        animalNames.add(key_.Key_Okay );
        animalNames.add(key_.Key_umm_why);
        animalNames.add(key_.Key_i_dont_have_goal);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3 ,GridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        token_adapter = new MyRecyclerViewAdapter_demo(MainActivity.this, animalNames);
        token_adapter.setClickListener(MainActivity.this);
        recyclerView.setAdapter(token_adapter);


    }

    public  void set_token_for_okay()
    {
        recyclerView.setVisibility(View.VISIBLE);

        // data to populate the RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Emergency fund");          //0
        animalNames.add("Vacation");                   //1
        animalNames.add("Home");                          //2
        animalNames.add("Bike");                             //3
        animalNames.add("Car");                                 //4
        animalNames.add("I don't have a goal.");                   //5
        animalNames.add("Phone");                                     //6

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3 ,GridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        token_adapter = new MyRecyclerViewAdapter_demo(MainActivity.this, animalNames);
        token_adapter.setClickListener(MainActivity.this);
        recyclerView.setAdapter(token_adapter);
    }
    public  void set_token_for_Umm_why()
    {

        token_adapter.notifyDataSetChanged();


        // data to populate the RecyclerView with
        ArrayList<String> animalNames2 = new ArrayList<>();

        animalNames2.add(key_.Key_Okay);                                 //4
        animalNames2.add(key_.Key_i_dont_have_goal);                   //5


        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2 ,GridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        token_adapter = new MyRecyclerViewAdapter_demo(MainActivity.this, animalNames2);
        token_adapter.setClickListener(MainActivity.this);
        recyclerView.setAdapter(token_adapter);

        recyclerView.setVisibility(View.VISIBLE);
        
    }

    public void count_down_timer(String Token)
    {

        final String s_token = Token ;
        new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

               // Toast.makeText(MainActivity.this, ""+counter, Toast.LENGTH_SHORT).show();
                //  counttime.setText(String.valueOf(counter));
                counter++;
            }
            @Override
            public void onFinish() {
                // counttime.setText("Finished");

                dots.setVisibility(View.GONE);


                if (s_token.equals("start"))
                {
                    for (int i = 0 ; i<2 ; i++)
                    {

                        newUser = new User(0 , list_1[i], "" , "C");
                        adapter.add(newUser);

                    }
                }

                if(s_token.equals("Token1"))
                {

                    set_next_data();
                }

                if(s_token.equals(key_.Key_Okay))
                {
                    newUser = new User(0 , "Select a goal. Or make your own", "" , "C");
                    adapter.add(newUser);


                    set_token_for_okay();

                }

                if(s_token.equals(key_.Key_umm_why))
                {
                 

                    for (int i = 0 ; i<2 ; i++)
                    {

                        newUser = new User(0 , list_umm_why[i], "" , "C");
                        adapter.add(newUser);

                    }


                    set_token_for_Umm_why();

                }







            }
        }.start();

    }


    @Override
    public void onItemClick(View view, int position)
    {
//        Toast.makeText(this, "You clicked " + token_adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

        if(position == 0 && token_adapter.getItem(position).equals(key_.Key_Okay))
        {


            newUser = new User(1 , key_.Key_Okay , "U" , "");
            adapter.add(newUser);


            recyclerView.setVisibility(View.GONE);
            dots.setVisibility(View.VISIBLE);
            count_down_timer(key_.Key_Okay);



        }

        if(token_adapter.getItem(position).equals(key_.Key_umm_why))
        {

            newUser = new User(1 , key_.Key_umm_why , "U" , "");
            adapter.add(newUser);





            recyclerView.setVisibility(View.GONE);
            dots.setVisibility(View.VISIBLE);
            count_down_timer(key_.Key_umm_why);




        }

        if(token_adapter.getItem(position).equals(key_.Key_i_dont_have_goal))
        {
          //  set_token_for_okay();

        }



    }

}
