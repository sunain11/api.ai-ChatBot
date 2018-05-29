package com.sunain.helperbot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sunain on 12/31/2017.
 */

public class Recycleradapternew extends RecyclerView.Adapter<chat_rec> {
    private List<ChatMessage> chatlist;
    private Context c;
    public Recycleradapternew(Context c, List<ChatMessage> chatlist)
    {
        this.c=c;
        this.chatlist = chatlist;
    }
    @Override
    public chat_rec onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.msglist, parent, false);

        return new chat_rec(itemView);
    }

    @Override
    public void onBindViewHolder(chat_rec holder, int position) {
        final ChatMessage chat = chatlist.get(position);
        if(chat.getMsgUser().equals("user"))
        {
            holder.rightText.setText(chat.getMsgText());
            holder.rightText.setVisibility(View.VISIBLE);
            holder.leftText.setVisibility(View.GONE);
        }
        if(chat.getMsgUser().equals("bot"))
        {
            holder.leftText.setText(chat.getMsgText());
            holder.rightText.setVisibility(View.GONE);
            holder.leftText.setVisibility(View.VISIBLE);

            if(isValid(chat.getMsgText()))
            {
                holder.leftText.setTextColor(Color.BLUE);
                holder.leftText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = chat.getMsgText();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        c.startActivity(i);
                    }
                });
            }
        }
    }
    public static boolean isValid(String url)
    {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    public boolean checkifurl(String url)
    {
        String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(url);//replace with string to compare
        if(m.find())
        {
            return true;
        }
        else
            {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }
}
