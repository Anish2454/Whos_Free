package com.shenoy.anish.whosfree;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by ANISH on 8/7/2015.
 */
public class InboxFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private LinearLayout empty;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DatabaseReference mDatabase;
    private FirebaseUser mCurrentUser;

    private List<User> mActiveFriends;

    private static final String TAG = InboxFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        empty = (LinearLayout) rootView.findViewById(R.id.empty);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mActiveFriends = new ArrayList<User>();
        mAdapter = new InboxAdapter(mActiveFriends);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update();
            }
        }); 
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
        Log.e(TAG, mActiveFriends.toString());
    }
    private void emptyList(){
        if (mActiveFriends.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
        else {
            mRecyclerView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }
    }

    private void update() {
        DatabaseReference mUsersReference = mDatabase.child("users");
        final HashSet<String> friendsEmails = new HashSet<>();
        mUsersReference.child(mCurrentUser.getUid()).child("friends").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot friend: dataSnapshot.getChildren()){
                    friendsEmails.add(friend.getValue(User.class).getEmail());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
        Query query = mUsersReference.orderByChild("firstName");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mActiveFriends.clear();
                for(DataSnapshot user: dataSnapshot.getChildren()){
                    User activeUser = user.getValue(User.class);
                    if(friendsEmails.contains(activeUser.getEmail())) mActiveFriends.add(activeUser);
                }
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
                emptyList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
                emptyList();
            }
        });
        return;
    }
}
