package com.example.chatdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chatdemo.Models.Message_model;
import com.example.chatdemo.R;

import java.util.List;

public class MyRecyclerViewAdapter_customer extends RecyclerView.Adapter<MyRecyclerViewAdapter_customer.ViewHolder> {

private List<String> mData;


    List<Message_model> DataObject  ;
    private static final int FOOTER_VIEW = 1;


    private LayoutInflater mInflater;
private ItemClickListener mClickListener;

public String flag_user ;

        // data is passed into the constructor
        public MyRecyclerViewAdapter_customer(Context context,  List<Message_model> DataObject ) {

        this.mInflater = LayoutInflater.from(context);
        this.DataObject = DataObject;


        }

// inflates the row layout from xml when needed
@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
{


//        View view = mInflater.inflate(R.layout.card, parent, false);
//        return new ViewHolder(view);

    View v;

    if (viewType == FOOTER_VIEW)
    {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_layout, parent, false);

        FooterViewHolder vh = new FooterViewHolder(v);

        return vh;
    }

    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);

    NormalViewHolder vh = new NormalViewHolder(v);

    return vh;




}




@Override
public void onBindViewHolder(ViewHolder holder, int position) {




    try {
        if (holder instanceof NormalViewHolder) {
            NormalViewHolder vh = (NormalViewHolder) holder;

            vh.bindView(position);

            Message_model item = DataObject.get(position);


            if (item.getUsertype().equals("C"))
            {
                holder.myTextView.setVisibility(View.GONE);
                holder.myTextView2.setVisibility(View.VISIBLE);
                holder.myTextView2.setText(item.getMessage());
            }
            else
            {
                holder.myTextView2.setVisibility(View.GONE);
                holder.myTextView.setVisibility(View.VISIBLE);
                holder.myTextView.setText(item.getMessage());
            }

        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder vh = (FooterViewHolder) holder;
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
    public class NormalViewHolder extends ViewHolder
    {
        public NormalViewHolder(View itemView) {
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
        return DataObject.size() + 1;

        //return DataObject.size();

        }


// stores and recycles views as they are scrolled off screen
public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView myTextView , myTextView2;

    ViewHolder(View itemView) {
        super(itemView);

            myTextView = itemView.findViewById(R.id.message_body);
            myTextView2 = itemView.findViewById(R.id.message_body2);


            
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

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

// parent activity will implement this method to respond to click events
public interface ItemClickListener {
    void onItemClick(View view, int position);
}


public void set_flag_user(String flag_user)
{

    this.flag_user = flag_user ;
}

    @Override
    public int getItemViewType(int position) {
        if (position == DataObject.size()) {
            // This is where we'll add footer.
            return FOOTER_VIEW;
        }

        return super.getItemViewType(position);
    }


}