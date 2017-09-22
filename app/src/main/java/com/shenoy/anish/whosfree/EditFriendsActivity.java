package com.shenoy.anish.whosfree;

/**
 * Created by owner on 7/30/17.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditFriendsActivity extends AppCompatActivity{

    public static final String TAG = EditFriendsActivity.class.getSimpleName();
    protected List<User> mUsers;
    protected FirebaseUser mCurrentUser;
    protected DatabaseReference mDatabase;
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friends);

        mGridView = (GridView)findViewById(R.id.friendsGrid);
        mGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView checkImageView = (ImageView)view.findViewById(R.id.checkImageView);

                if (mGridView.isItemChecked(position)) {
                    mDatabase.child("users").child(mCurrentUser.getUid()).child("friends").push().setValue(mUsers.get(position));
                    checkImageView.setVisibility(View.VISIBLE);
                } else {
                    mDatabase.child("users").child(mCurrentUser.getUid()).child("friends").orderByChild("email").equalTo(mUsers.get(position).getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String key = "";
                            for(DataSnapshot snap: dataSnapshot.getChildren()) key = snap.getKey();
                            Log.e(TAG, key);
                            mDatabase.child("users").child(mCurrentUser.getUid()).child("friends").child(key).removeValue();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG, databaseError.getMessage());
                        }
                    });
                    checkImageView.setVisibility(View.INVISIBLE);
                }
            }
        });

        TextView emptyTextView = (TextView)findViewById(android.R.id.empty);
        mGridView.setEmptyView(emptyTextView);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUsers = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Query query = mDatabase.child("users").orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot user : dataSnapshot.getChildren()){
                    if(!user.getKey().equals(mCurrentUser.getUid())){
                        mUsers.add(user.getValue(User.class));
                    }
                }
                if (mGridView.getAdapter() == null) {
                    UserAdapter adapter = new UserAdapter(EditFriendsActivity.this, mUsers);
                    mGridView.setAdapter(adapter);
                }
                else {
                    ((UserAdapter)mGridView.getAdapter()).refill(mUsers);
                }
                addFriendCheckmarks();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_friends, menu);
        return true;
    }

    private void addFriendCheckmarks() {
        Query query = mDatabase.child("users").child(mCurrentUser.getUid()).child("friends");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot friend: dataSnapshot.getChildren()){
                    User friend1 = friend.getValue(User.class);
                    int i =0;
                    for(User user: mUsers) {
                        if (friend1.getEmail().equals(user.getEmail()))
                           mGridView.setItemChecked(i, true);
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
    }
}