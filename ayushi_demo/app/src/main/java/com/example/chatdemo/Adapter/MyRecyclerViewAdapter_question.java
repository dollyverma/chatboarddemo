package com.example.chatdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.chatdemo.Models.QuestionModel;
import com.example.chatdemo.R;

import java.util.List;

public class MyRecyclerViewAdapter_question extends RecyclerView.Adapter<MyRecyclerViewAdapter_question.ViewHolder> {

private List<String> mData;

    private static final int FOOTER_VIEW = 1;
    List<QuestionModel> DataObject  ;

private LayoutInflater mInflater;

Context context ;
    QuestionModel item ;

    private MyRecyclerViewAdapter_demo.ItemClickListener mClickListener;


        // data is passed into the constructor
        public MyRecyclerViewAdapter_question(Context context, List<QuestionModel> DataObject  ) {

        this.mInflater = LayoutInflater.from(context);
        this.DataObject = DataObject;
        this.context = context ;



        }

// inflates the row layout from xml when needed
@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
{


        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);



        }

    


@Override
public void onBindViewHolder(final ViewHolder holder, final int position) {

    item = DataObject.get(position);



    holder.tv_question.setText(item.getQuestion());
    holder.tv_question_id.setText(item.getQuestionid());




        }




@Override
public int getItemCount() {


          return DataObject.size();
        }


    // stores and recycles views as they are scrolled off screen
public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener
{
    TextView tv_question, tv_question_id;

    ViewHolder(View itemView)
    {
        super(itemView);

        tv_question = itemView.findViewById(R.id.question);
        tv_question_id = itemView.findViewById(R.id.questionid);

        itemView.setOnClickListener(this);


    }

    public void bindView(int position) {
        // bindView() method to implement actions
    }




    // convenience method for getting data at click position
    public String getItem(int id)
    {

        item = DataObject.get(id);

        return item.getQuestion();
    }


    Response.ErrorListener errorListener = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error)
        {
            if (error instanceof NetworkError)
            {
                Toast.makeText(context, "No network available", Toast.LENGTH_LONG).show();
            } else
            {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        }
    };





    @Override
    public void onClick(View view) {
        if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
    }
}

    // convenience method for getting data at click position
    public String getItem(int id) {
        item = DataObject.get(id);

        return item.getQuestion();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == DataObject.size()) {
            // This is where we'll add footer.
            return FOOTER_VIEW;
        }

        return super.getItemViewType(position);
    }


    // allows clicks events to be caught
    public void setClickListener(MyRecyclerViewAdapter_demo.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}