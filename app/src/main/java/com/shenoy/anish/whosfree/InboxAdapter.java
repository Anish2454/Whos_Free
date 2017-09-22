package com.shenoy.anish.whosfree;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by owner on 7/30/17.
 */

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder>{

    private List<User> mFriends;
    private Context context;

    public InboxAdapter(List<User> friends){
        mFriends = friends;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_inbox, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User friend = mFriends.get(position);
        holder.nameTextView.setText(friend.getFirstName() + " " + friend.getLastName());
        if(friend.hasProfilePic()) Picasso.with(context).load(friend.getmProfilePicLink()).into(holder.profilePic);
        else holder.profilePic.setImageResource(R.drawable.avatar_empty);
        holder.freeTextView.setText(friend.freeOrNot());
        holder.freeLabel.setChecked(friend.getFree());
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public ImageView profilePic;
        public TextView freeTextView;
        public TextView nameTextView;
        public RadioButton freeLabel;

        public ViewHolder(View v) {
            super(v);
            profilePic = (ImageView) v.findViewById(R.id.inboxProfilePic);
            freeTextView = (TextView) v.findViewById(R.id.freeOrNotTextView);
            cv = (CardView) v.findViewById(R.id.friendsCardView);
            nameTextView = (TextView) v.findViewById(R.id.usernameTextView);
            freeLabel = (RadioButton) v.findViewById(R.id.freeLabel);
        }
    }


}
