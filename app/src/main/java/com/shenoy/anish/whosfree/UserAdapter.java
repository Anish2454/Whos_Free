package com.shenoy.anish.whosfree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by owner on 8/17/17.
 */

public class UserAdapter extends ArrayAdapter<User> {

    private Context mContext;
    private List<User> mUsers;


    public UserAdapter(Context context, List<User> users) {
        super(context, R.layout.item_user, users);
        mContext = context;
        mUsers = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_user, null);
            holder = new ViewHolder();
            holder.userImageView = (ImageView)convertView.findViewById(R.id.userImageView);
            holder.nameLabel = (TextView)convertView.findViewById(R.id.nameLabel);
            holder.checkImageView = (ImageView)convertView.findViewById(R.id.checkImageView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        User user = mUsers.get(position);
        String email = user.getEmail().toLowerCase();

        if (!user.hasProfilePic()) {
            holder.userImageView.setImageResource(R.drawable.avatar_empty);
        }
        else {
            Picasso.with(mContext)
                    .load(user.getmProfilePicLink())
                    .placeholder(R.drawable.avatar_empty)
                    .into(holder.userImageView);
        }

        holder.nameLabel.setText(user.getFirstName() + " " + user.getLastName());

        GridView gridView = (GridView)parent;
        if (gridView.isItemChecked(position)) {
            holder.checkImageView.setVisibility(View.VISIBLE);
        }
        else {
            holder.checkImageView.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView userImageView;
        ImageView checkImageView;
        TextView nameLabel;
    }

    public void refill(List<User> users) {
        mUsers.clear();
        mUsers.addAll(users);
        notifyDataSetChanged();
    }

}
