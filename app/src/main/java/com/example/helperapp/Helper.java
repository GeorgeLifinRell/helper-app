package com.example.helperapp;

public class Helper {
    private String name;
    private String jobTitle;
    private String farePerHour;
    public Helper () {}

    public Helper(String name, String jobTitle, String farePerHour) {
        this.name = name;
        this.jobTitle = jobTitle;
        this.farePerHour = farePerHour;
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
}
