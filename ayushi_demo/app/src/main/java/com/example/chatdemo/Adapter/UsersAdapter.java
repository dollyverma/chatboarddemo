package com.example.chatdemo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatdemo.R;
import com.example.chatdemo.Models.User;

import java.util.ArrayList;

public class UsersAdapter    extends ArrayAdapter<User>
{

    public  int last_position_of_list = 0 ;

    Context  context ;
    TextView tvName1 ;

    public int counter;


    public UsersAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);

     //   count_down_timer();

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        LayoutInflater messageInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        last_position_of_list = position ;



        if ((user.flag_user).equals("U"))
        {

            // blue
            convertView = messageInflater.inflate(R.layout.card, null);
            TextView  tvName = (TextView) convertView.findViewById(R.id.message_body);
            tvName.setText(user.name);



        }
        if ((user.flag_cutomercare).equals("C"))
        {

            // white
            convertView = messageInflater.inflate(R.layout.card2, null);
            tvName1 = (TextView) convertView.findViewById(R.id.message_body);

            tvName1.setText(user.name);

        }


        return convertView;

    }


    public int return_last_position_of_list ()
    {
        return last_position_of_list ;
    }



    public void count_down_timer()
    {

        new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                Toast.makeText(getContext(), "time"+counter, Toast.LENGTH_SHORT).show();
                //  counttime.setText(String.valueOf(counter));
                counter++;


            }
            @Override
            public void onFinish() {
                // counttime.setText("Finished");



            }
        }.start();

    }





}