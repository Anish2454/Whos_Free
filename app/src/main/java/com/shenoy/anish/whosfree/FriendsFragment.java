package com.shenoy.anish.whosfree;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by ANISH on 8/7/2015.
 */
public class FriendsFragment extends Fragment{

    public static final String TAG = FriendsFragment.class.getSimpleName();

    protected FirebaseUser mCurrentUser;
    private DatabaseReference mDatabase;
    private ImageView mProfilePic;
    private TextView mName;
    private CheckBox mCheckBox;
    private User mUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        mProfilePic = (ImageView) rootView.findViewById(R.id.profilePicView);
        mCheckBox = (CheckBox) rootView.findViewById(R.id.checkBox);
        mName = (TextView) rootView.findViewById(R.id.nameTextView);
        mUser = null;
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase.child("users").child(mCurrentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUser = dataSnapshot.getValue(User.class);
                mName.setText(mUser.getFirstName() + " " + mUser.getLastName());
                if(mUser.hasProfilePic()) Picasso.with(getActivity()).load(mUser.getmProfilePicLink()).into(mProfilePic);
                mCheckBox.setChecked(mUser.getFree());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDatabase.child("users").child(mCurrentUser.getUid()).child("free").setValue(isChecked);
            }
        });
    }


}
