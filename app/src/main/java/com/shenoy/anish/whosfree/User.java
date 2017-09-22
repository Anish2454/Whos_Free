package com.shenoy.anish.whosfree;

/**
 * Created by owner on 7/30/17.
 */

public class User {

    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mProfilePicLink;
    private Boolean isFree;


    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) { this.mLastName = mLastName; }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmProfilePicLink() {
        return mProfilePicLink;
    }

    public Boolean getFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }

    public void setmProfilePicLink(String mProfilePicLink) {
        this.mProfilePicLink = mProfilePicLink;
    }

    public User(String firstName, String lastName, String email, String profilePicLink){
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mProfilePicLink = profilePicLink;
        isFree = false;
    }

    public boolean hasProfilePic(){
        return mProfilePicLink != null;
    }

    public String freeOrNot(){
        if(isFree) return "Free";
        return "Not Available";
    }

    public User(){
        super();
    }
}
