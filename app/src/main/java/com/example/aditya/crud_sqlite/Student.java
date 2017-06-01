package com.example.aditya.crud_sqlite;

/**
 * Created by aditya on 31-05-2017.
 */

public class Student {
    private int id;
    private String name;
    private String email;
    private String college;
    private String branch;
    private String enrollment_year;
    private String password;
    public Student(int id,String name,String college,String branch,String email,String enrollment_year,String password)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.college = college;
        this.branch = branch;
        this.enrollment_year = enrollment_year;
        this.password = password;
    }
    public Student(String name,String college,String branch,String email,String enrollment_year,String password)
    {

        this.name = name;
        this.email = email;
        this.college = college;
        this.branch = branch;
        this.enrollment_year = enrollment_year;
        this.password = password;
    }
    public Student()
    {


    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String GetCollege() {
        return college ;
    }

    public String getEmail() {
        return email;
    }

    public String GetBranch() {
        return branch;
    }

    public String getPassword() {
        return password;
    }

    public String GetEnrolllment_year() {
        return enrollment_year;
    }

}
