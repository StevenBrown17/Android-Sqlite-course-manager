package edu.weber.stevenbrown.cs3270a7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Owner on 6/24/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;


    public DatabaseHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    public SQLiteDatabase open(){
        database = getWritableDatabase();
        return database;
    }

    public void close(){
        if(database != null)
            database.close();
    }

    public long insertCourse(String id, String name, String course_code, String start_at, String end_at){
        Log.d("test","DatabaseHelper insertCourse()");
        long rowID= -1;

        ContentValues newCourse = new ContentValues();
        newCourse.put("id", id);
        newCourse.put("name", name);
        newCourse.put("course_code", course_code);
        newCourse.put("start_at", start_at);
        newCourse.put("end_at", end_at);

        if(open() !=null){
            rowID = database.insert("courses", null, newCourse);
            close();
        }

        return rowID;
    }

    public long updateCourse(long _id, String id, String name, String course_code, String start_at, String end_at){

        long rowID= -1;

        ContentValues newCourse = new ContentValues();
        newCourse.put("id", id);
        newCourse.put("name", name);
        newCourse.put("course_code", course_code);
        newCourse.put("start_at", start_at);
        newCourse.put("end_at", end_at);

        if(open() !=null){
            rowID = database.update("courses", newCourse , "_id=" + _id, null);
            close();
        }

        return rowID;
    }

    public Cursor getAllCourses(){
        Log.d("test","DataBaseHelper getAllCourses()");
        Cursor cursor = null;
        if(open()!=null){
            cursor = database.rawQuery("Select * FROM courses",null);
            Log.d("test","All Courses Queried");
        }

        return cursor;
    }

    public Cursor getSingleCourse(long _id){
        Log.d("test","DataBaseHelper getSingleCourse()");
        String[] params = new String[1];
        params[0] = ""+_id;
        Cursor cursor = null;
        if(open()!=null){
            cursor = database.rawQuery("Select * FROM courses WHERE _id = ?", params);
            Log.d("test", _id+" Queried");}

        return cursor;
    }

    public void deleteCourse(long _id){

        SQLiteDatabase db = this.getWritableDatabase();

        String[] params = new String[1];
        params[0] = ""+_id;
        if(open()!=null){

            db.delete("courses","_id = "+_id,null);

            //database.rawQuery("DELETE FROM courses WHERE _id = '"+_id+"'", null);
            Log.d("test", _id+" Deleted");}
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("test","Database Created");
        String createQuery = "CREATE TABLE courses "+
                             "(_id integer primary key autoincrement, "+
                             "id TEXT, name TEXT, course_code TEXT, start_at TEXT, end_at TEXT)";
        db.execSQL(createQuery);
    }

    

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("test","Database onUpgrade()");
        db.execSQL("DROP TABLE IF EXISTS courses");
        onCreate(db);
    }
}
