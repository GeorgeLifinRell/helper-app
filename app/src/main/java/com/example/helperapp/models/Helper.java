package com.example.helperapp.models;

public class Helper {
    private String id;



    private String name;
    private String jobTitle;
    private String farePerHour;
    private String profilePictureURL;
    public Helper () {}

    public Helper(String name, String jobTitle, String farePerHour, String profilePictureURL) {
        this.name = name;
        this.jobTitle = jobTitle;
        this.farePerHour = farePerHour;
        this.profilePictureURL = profilePictureURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getFarePerHour() {
        return farePerHour;
    }

    public void setFarePerHour(String farePerHour) {
        this.farePerHour = farePerHour;
    }
    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }
}
