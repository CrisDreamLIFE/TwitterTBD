package com.tbd.twitter;

import org.springframework.data.mongodb.core.mapping.Document;
import twitter4j.GeoLocation;

import java.util.Date;

@Document(collection="tweet")
public class Tweet {

    private long _id;
    private Date createdAt;
    //private GeoLocation geoLocation;
	private String text;
    //private int retweetCount;
    //private int favoriteCount;
    private boolean retweet;
    private long userId;
    private String userScreenName;
    private boolean userVerified;
    private int userFollowersCount;
    private int userStatusesCount;
    private int userFriendsCount;
    //private double positivePorcent;
    //private double negativePorcent;
    //private int positiveScore;
    //private int negativeScore;
    private String analysis;
    private String region;
    
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    /*
    public double getPositivePercent() {
        return positivePorcent;
    }

    public void setPositivePercent(double positivePorcent) {
        this.positivePorcent = positivePorcent;
    }
    
    public double getNegativePercent() {
        return negativePorcent;
    }

    public void setNegativePercent(double negativePorcent) {
        this.negativePorcent = negativePorcent;
    }
    public int getPositiveScore() {
        return positiveScore;
    }

    public void setPositiveScore(int positiveScore) {
        this.positiveScore = positiveScore;
    }
    
    public int getNegativeScore() {
        return negativeScore;
    }

    public void setNegativeScore(int negativeScore) {
        this.negativeScore = negativeScore;
    }
    */
    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
    
    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
/*
    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }
*/
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
/*
    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }
*/
    public boolean isRetweet() {
        return retweet;
    }

    public void setRetweet(boolean retweet) {
        this.retweet = retweet;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserScreenName() {
        return userScreenName;
    }

    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public boolean isUserVerified() {
        return userVerified;
    }

    public void setUserVerified(boolean userVerified) {
        this.userVerified = userVerified;
    }

    public int getUserFollowersCount() {
        return userFollowersCount;
    }

    public void setUserFollowersCount(int userFollowersCount) {
        this.userFollowersCount = userFollowersCount;
    }

    public int getUserStatusesCount() {
        return userStatusesCount;
    }

    public void setUserStatusesCount(int userStatusesCount) {
        this.userStatusesCount = userStatusesCount;
    }

    public int getUserFriendsCount() {
        return userFriendsCount;
    }

    public void setUserFriendsCount(int userFriendsCount) {
        this.userFriendsCount = userFriendsCount;
    }
}
