package com.example.hasoo.studentinformation;

public class Student {

    private String name;
    private String stdno;

    Student(String name, String stdno){
        this.name = name;
        this.stdno = stdno;
    }

    public String getName(){
        return name;
    }

    public String getStdno() {
        return stdno;
    }
}
