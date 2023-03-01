package com.example.officeroom;

public class AnnouncementsModelClass {
    String Announcements,Title;

    public AnnouncementsModelClass(String announcements, String title) {
        Announcements = announcements;
        Title = title;
    }

    public AnnouncementsModelClass() {

    }

    public String getAnnouncements() {
        return Announcements;
    }

    public String getTitle() {
        return Title;
    }
}
