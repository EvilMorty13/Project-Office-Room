package com.example.officeroom;

public class AnnouncementsModelClass {
    String Announcements,Title,From;

    public AnnouncementsModelClass(String announcements, String title, String from) {
        Announcements = announcements;
        Title = title;
        From = from;
    }

    public AnnouncementsModelClass() {

    }

    public String getAnnouncements() {
        return Announcements;
    }

    public String getTitle() {
        return Title;
    }

    public String getFrom(){
        return From;
    }
}
