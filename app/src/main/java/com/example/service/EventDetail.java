package com.example.service;

public class EventDetail {

    String Collegename;
    String Department;
    String Eventname;

    String Discription;
    String Eventdate;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    String Name;
    String Email;

    public EventDetail(String collegename, String department, String eventname, String discription, String eventdate, String lastreg, String city, String name, String email) {
        Collegename = collegename;
        Department = department;
        Eventname = eventname;
        Discription = discription;
        Eventdate = eventdate;
        Lastreg = lastreg;
        City = city;
        this.latt = latt;
        this.longt = longt;
        Name=name;
        Email=email;

    }

    String Lastreg;
    String City;
    String latt;

    EventDetail()
    {

    }

    public String getCollegename() {
        return Collegename;
    }

    public void setCollegename(String collegename) {
        Collegename = collegename;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getEventname() {
        return Eventname;
    }

    public void setEventname(String eventname) {
        Eventname = eventname;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public String getEventdate() {
        return Eventdate;
    }

    public void setEventdate(String eventdate) {
        Eventdate = eventdate;
    }

    public String getLastreg() {
        return Lastreg;
    }

    public void setLastreg(String lastreg) {
        Lastreg = lastreg;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getLatt() {
        return latt;
    }

    public void setLatt(String latt) {
        this.latt = latt;
    }

    public String getLongt() {
        return longt;
    }

    public void setLongt(String longt) {
        this.longt = longt;
    }

    String longt;


}
