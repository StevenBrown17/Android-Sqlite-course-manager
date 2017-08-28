package edu.weber.stevenbrown.cs3270a7;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    long index=0;
    String _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.listContainer, new CourseListFragment(), "CL").addToBackStack(null).commit();
        Log.d("test","MainActivity onCreate()");

        DatabaseHelper dbHelper = new DatabaseHelper(this,"courses",null,1);
        //dbHelper.insertCourse("A","A","A","A","A");

    }

    public void openEditFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.listContainer, new CourseEditFragment(), "CE").addToBackStack("CE").commit();
    }

    public void openListFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.listContainer, new CourseListFragment(), "CL").addToBackStack("CL").commit();
    }

    public void openViewFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.listContainer, new CourseViewFragment(), "CV").addToBackStack("CV").commit();
    }




}
