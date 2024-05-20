package com.example.helperapp.models;

public class HelperBooking {
    private String bookingId;
    private String serviceStatus;
    private String helperId;
    private String hoursBooked;
    private String bookingTimestamp;
    private String jobTitle;
    private String totalFare;


    public HelperBooking() {
    }

    public HelperBooking(String bookingId, String serviceStatus, String helperId, String hoursBooked, String bookingTimestamp, String jobTitle, String totalFare) {
        this.bookingId = bookingId;
        this.serviceStatus = serviceStatus;
        this.helperId = helperId;
        this.hoursBooked = hoursBooked;
        this.bookingTimestamp = bookingTimestamp;
        this.jobTitle = jobTitle;
        this.totalFare = totalFare;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getHelperId() {
        return helperId;
    }

    public void setHelperId(String helperId) {
        this.helperId = helperId;
    }

    public String getHoursBooked() {
        return hoursBooked;
    }

    public void setHoursBooked(String hoursBooked) {
        this.hoursBooked = hoursBooked;
    }

    public String getBookingTimestamp() {
        return bookingTimestamp;
    }

    public void setBookingTimestamp(String bookingTimestamp) {
        this.bookingTimestamp = bookingTimestamp;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare = totalFare;
    }
}
