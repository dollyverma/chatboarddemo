package com.example.chatdemo.Adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chatdemo.Models.MessageModel;
import com.example.chatdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyRecyclerViewAdapter_customer extends RecyclerView.Adapter<MyRecyclerViewAdapter_customer.ViewHolder> {
    

    List<MessageModel> DataObject  ;
    private static int FOOTER_VIEW = 1;

    private static int LEFT_VIEW = 2;

    private static int RIGHT_VIEW = 3;



    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    FrameLayout dots ;


    String s="";

    private TimerTask NoticeTimerTask;
    private final Handler handler = new Handler();
    Timer timer = new Timer();

    

    public MyRecyclerViewAdapter_customer(Context context,  List<MessageModel> DataObject ) {

        this.mInflater = LayoutInflater.from(context);
        this.DataObject = DataObject;

    }




    // inflates the row layout from xml when needed
    @Override
    public ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType)
    {


        if (viewType == FOOTER_VIEW)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_layout, parent, false);
            return new FooterViewHolder(view);
        }
        else if (viewType == LEFT_VIEW)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card2, parent, false);
            return new LeftViewHolder(view);
        }
        else if (viewType == RIGHT_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
            return new RightViewHolder(view);
        }
        else
            {
            throw new RuntimeException("The type has to be ONE or TWO");
        }


    }


    public void setFadeAnimation(View view)
    {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(2000);
        view.startAnimation(anim);
    }






    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        try {

                  //setFadeAnimation(holder.itemView);

            if (holder instanceof LeftViewHolder) {



                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        LeftViewHolder vh = (LeftViewHolder) holder;

                        vh.bindView(position);

                        MessageModel item = DataObject.get(position);
                        holder.myTextView.setText(item.getMessage());



                    }
                }, 3000);





            }

            else if (holder instanceof RightViewHolder) {
                RightViewHolder vh = (RightViewHolder) holder;
                vh.bindView(position);

                MessageModel item = DataObject.get(position);
                holder.myTextView.setText(item.getMessage());



//
//                if (item.getUsertype().equals("C"))
//                {
//
//
//                    holder.myTextView.setVisibility(View.GONE);
//                    holder.myTextView2.setVisibility(View.VISIBLE);
//                    holder.myTextView2.setText(item.getMessage());
//
//                    s = item.getEnd_flag() ;
//
//
//                }
//                else
//                {
//                    holder.myTextView2.setVisibility(View.GONE);
//                    holder.myTextView.setVisibility(View.VISIBLE);
//                    holder.myTextView.setText(item.getMessage());
//
//                    s = item.getEnd_flag() ;
//
//                }





                
            }




            else if (holder instanceof FooterViewHolder) {
                FooterViewHolder vh = (FooterViewHolder) holder;



                if (!s.equals(""))
                {
                    dots.setVisibility(View.GONE);
                }
                else
                {
                    dots.setVisibility(View.VISIBLE);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public class FooterViewHolder extends ViewHolder
    {
        public FooterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do whatever you want on clicking the item
                }
            });
        }
    }

    // Now define the viewholder for Normal list item
    public class LeftViewHolder extends ViewHolder
    {
        public LeftViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do whatever you want on clicking the normal items
                }
            });
        }
    }

    // Now define the viewholder for Normal list item
    public class RightViewHolder extends ViewHolder
    {
        public RightViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do whatever you want on clicking the normal items
                }
            });
        }
    }








    @Override
    public int getItemCount() {
        if (DataObject == null) {
            return 0;
        }



        if (DataObject.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return DataObject.size() ;

        //return DataObject.size();

    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView  ;
        FrameLayout dots2 ;



        ViewHolder(View itemView) {
            super(itemView);

            myTextView = itemView.findViewById(R.id.message_body);

            dots = (FrameLayout)itemView.findViewById(R.id.dots);

            dots2 = (FrameLayout)itemView.findViewById(R.id.dots);


            itemView.setOnClickListener(this);



        }

        public void bindView(int position) {
            // bindView() method to implement actions
        }




        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

   

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }



    @Override
    public int getItemViewType(int position) {
        if (position == DataObject.size()) {
            // This is where we'll add footer.
            return FOOTER_VIEW;
        }

        if (DataObject.size()>0)
        {
            MessageModel item = DataObject.get(position);
            if (item.getUsertype().equals("C"))
            {
                return LEFT_VIEW;
            }
            else
            {
                return RIGHT_VIEW;
            }
        }else
        {
             return super.getItemViewType(position);
        }


       


    }


    public void add(MessageModel listItem) {



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                DataObject.add(listItem);
                notifyDataSetChanged();

            }
        }, 3000);




    }




}