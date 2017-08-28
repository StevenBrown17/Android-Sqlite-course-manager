package edu.weber.stevenbrown.cs3270a7;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;


public class CourseListFragment extends Fragment {

    ListView courseList;
    FloatingActionButton btnAdd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("test","ListFragment onCreateView()");

        View view = (View)inflater.inflate(R.layout.fragment_course_list, container, false);
        courseList = (ListView)view.findViewById(R.id.courseList);

        new getAllCourses().execute("");



        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.index = (long)position;
                mainActivity.openViewFragment();
            }
        });
        btnAdd = (FloatingActionButton)view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.index=-1;
                mainActivity.openEditFragment();
            }
        });



        return view;
    }



    public class getAllCourses extends AsyncTask<String, Integer, Cursor> {

        @Override
        protected Cursor doInBackground(String... params) {
            DatabaseHelper dbHelper = new DatabaseHelper(getActivity(),"courses",null,1);
            Cursor cursor = dbHelper.getAllCourses();
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            String[] name = new String[] {"name"};
            int[] views = new int[] {android.R.id.text1};

            try {
                SimpleCursorAdapter adapter =
                        new SimpleCursorAdapter(
                                getActivity(), android.R.layout.simple_list_item_1, cursor, name, views, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
                        );


                //setListAdapter(adapter);
                courseList.setAdapter(adapter);
            }catch(Exception e) {
                Log.d("test", "List update failed");
                e.printStackTrace();}

        }
    }




}
