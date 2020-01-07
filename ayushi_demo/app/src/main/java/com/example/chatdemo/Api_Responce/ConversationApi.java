package com.example.chatdemo.Api_Responce;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ConversationApi implements Serializable
{

    @SerializedName("message")
    public String message;

    public List<ConversationApi.message_convo_list> getmessageConvoList_function()
    {
        return obj_messageConvoList;
    }

    public List<ConversationApi.question_list> getquestion_list_function()
    {
        return obj_question_list;
    }


    @SerializedName("flag")
    public String Flag_user_type;



    @SerializedName("message_convo")
    public List<message_convo_list> obj_messageConvoList;

    @SerializedName("question")
    public List<question_list> obj_question_list;





    //_________________________________________________________________________________

    public static class message_convo_list  implements Serializable
    {
        public String getMessage()
        {
            return message;
        }

        public void setMessage(String message)
        {
            this.message = message;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        @SerializedName("message")
        public String message;

        @SerializedName("type")
        public String type;


        public String getEnd_flag()
        {
            return end_flag;
        }

        public void setEnd_flag(String end_flag)
        {
            this.end_flag = end_flag;
        }

        @SerializedName("end_flag")
        public String end_flag;



    }




    //_________________________________________________________________________________

    public static class question_list  implements Serializable
    {

        public String getQuestion()
        {
            return question;
        }

        public void setQuestion(String question)
        {
            this.question = question;
        }

        public String getQuestionid()
        {
            return questionid;
        }

        public void setQuestionid(String questionid)
        {
            this.questionid = questionid;
        }

        @SerializedName("question")
        public String question;

        @SerializedName("questionid")
        public String questionid;

    }




}
