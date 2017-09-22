package com.shenoy.anish.whosfree;

import android.support.v4.app.ListFragment;

public class ChatFragment extends ListFragment {
    /*
    public static final String TAG = FriendsFragment.class.getSimpleName();
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected ParseUser mCurrentUser;
    private List<ParseObject> mChats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat_fragments, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.chatSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveChats();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        retrieveChats();
    }

    private void retrieveChats() {
        mCurrentUser = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_GROUP_CHAT);
        query.whereEqualTo(ParseConstants.KEY_ATTENDEES, mCurrentUser.getObjectId());
        query.addAscendingOrder("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if (e == null) {
                    mChats = list;
                    if (getListView().getAdapter() == null) {
                        GroupChatAdapter adapter = new GroupChatAdapter(
                                getListView().getContext(),
                                mChats);
                        setListAdapter(adapter);
                    } else {
                        ((GroupChatAdapter) getListView().getAdapter()).refill(mChats);
                    }
                }
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), MessagingActivity.class);
        intent.putExtra("objectId", mChats.get(position).getObjectId());
        startActivity(intent);
    }*/
}

