package com.example.aditya.crud_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditya on 31-05-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "StudentManager.db";

    // User table name
    private static final String TABLE_STUDENT = "student";

    // User Table Columns names
    private static final String COLUMN_STUDENT_ID = "student_id";
    private static final String COLUMN_STUDENT_NAME = "student_name";
    private static final String COLUMN_STUDENT_EMAIL = "student_email";
    private static final String COLUMN_STUDENT_PASSWORD = "student_password";
    private static final String COLUMN_STUDENT_COLLEGE = "student_college";
    private static final String COLUMN_STUDENT_BRANCH = "student_branch";
    private static final String COLUMN_STUDENT_ENROLLMENT_YEAR = "student_enrollment_year";

    // create table sql query
    private String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT + "("
            + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_STUDENT_NAME + " TEXT,"
            + COLUMN_STUDENT_COLLEGE + " TEXT," + COLUMN_STUDENT_BRANCH + " TEXT," + COLUMN_STUDENT_EMAIL + " TEXT,"  + COLUMN_STUDENT_ENROLLMENT_YEAR+ " TEXT," + COLUMN_STUDENT_PASSWORD + " TEXT" + ")";
    // drop table sql query
    private String DROP_STUDENT_TABLE = "DROP TABLE IF EXISTS " + TABLE_STUDENT;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_STUDENT_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(Student user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_NAME, user.getName());
        values.put(COLUMN_STUDENT_COLLEGE, user.GetCollege());
        values.put(COLUMN_STUDENT_BRANCH, user.GetBranch());
        values.put(COLUMN_STUDENT_EMAIL, user.getEmail());
        values.put(COLUMN_STUDENT_ENROLLMENT_YEAR, user.GetEnrolllment_year());
        values.put(COLUMN_STUDENT_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<Student> getAllUser(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_STUDENT_ID,
                COLUMN_STUDENT_NAME,
                COLUMN_STUDENT_COLLEGE,
                COLUMN_STUDENT_BRANCH,
                COLUMN_STUDENT_EMAIL,
                COLUMN_STUDENT_ENROLLMENT_YEAR,
                COLUMN_STUDENT_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_STUDENT_NAME + " ASC";
        List<Student> userList = new ArrayList<Student>();

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String sel = COLUMN_STUDENT_EMAIL + " = ?";

        // selection argument
        String[] sArgs = {email};

        Cursor cursor1 = db.query(TABLE_STUDENT, //Table to query
                columns,    //columns to return
                sel,        //columns for the WHERE clause
                sArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        String current_clg = null,current_branch= null,current_ey= null;
        if (cursor1.moveToFirst()) {
            do {
              current_clg =  cursor1.getString(cursor1.getColumnIndex(COLUMN_STUDENT_COLLEGE));
             current_branch =    cursor1.getString(cursor1.getColumnIndex(COLUMN_STUDENT_BRANCH));
              current_ey =  cursor1.getString(cursor1.getColumnIndex(COLUMN_STUDENT_ENROLLMENT_YEAR));
            } while (cursor1.moveToNext());
        }
        cursor1.close();
        // selection criteria
        String selection = COLUMN_STUDENT_COLLEGE+ " = ? and "+COLUMN_STUDENT_BRANCH+" = ? and "+COLUMN_STUDENT_ENROLLMENT_YEAR+" = ? and "+COLUMN_STUDENT_EMAIL+" != ?";

        // selection argument
        String[] selectionArgs = {current_clg,current_branch,current_ey,email};
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_STUDENT, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Student user = new Student(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ID))),cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_NAME)),cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_COLLEGE)),cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_BRANCH)),cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_EMAIL)),cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ENROLLMENT_YEAR)),cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_PASSWORD)));
                //user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                //user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                //user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                //user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }
    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_STUDENT_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_STUDENT_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_STUDENT, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_STUDENT_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_STUDENT_EMAIL + " = ?" + " AND " + COLUMN_STUDENT_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_STUDENT, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


}
