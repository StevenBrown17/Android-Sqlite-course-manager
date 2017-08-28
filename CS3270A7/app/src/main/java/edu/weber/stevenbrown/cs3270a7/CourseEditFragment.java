package edu.weber.stevenbrown.cs3270a7;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseEditFragment extends Fragment {

    EditText etId, etName, etCourse, etStart, etEnd;
    FloatingActionButton btnSave;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewEdit = (View)inflater.inflate(R.layout.fragment_course_edit, container,false);
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity(),"courses",null,1);

        etId = (EditText)viewEdit.findViewById(R.id.etId);
        etName = (EditText)viewEdit.findViewById(R.id.etName);
        etCourse = (EditText)viewEdit.findViewById(R.id.etCourse);
        etStart = (EditText)viewEdit.findViewById(R.id.etStart);
        etEnd = (EditText)viewEdit.findViewById(R.id.etEnd);
        btnSave = (FloatingActionButton)viewEdit.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity)getActivity();
                DatabaseHelper dbHelper = new DatabaseHelper(getActivity(),"courses",null,1);

                if(mainActivity.index==-1) {
                    long rowID = dbHelper.insertCourse(
                            etId.getText().toString(),
                            etName.getText().toString(),
                            etCourse.getText().toString(),
                            etStart.getText().toString(),
                            etEnd.getText().toString()
                    );
                }else{
                    long rowID = dbHelper.updateCourse(
                            Long.parseLong(mainActivity._id),
                            etId.getText().toString(),
                            etName.getText().toString(),
                            etCourse.getText().toString(),
                            etStart.getText().toString(),
                            etEnd.getText().toString()
                    );
                    mainActivity.index=0;
                }


                mainActivity.openListFragment();
            }
        });

        MainActivity mainActivity = (MainActivity)getActivity();
        if(mainActivity.index!=-1){

            new getSingleCourses().execute("");

//            Cursor cursor = dbHelper.getSingleCourse(Long.parseLong(mainActivity._id));
//            cursor.moveToFirst();
//            mainActivity._id = cursor.getString(cursor.getColumnIndex("_id"));
//            String id = cursor.getString(cursor.getColumnIndex("id"));
//            String name = cursor.getString(cursor.getColumnIndex("name"));
//            String course = cursor.getString(cursor.getColumnIndex("course_code"));
//            String start = cursor.getString(cursor.getColumnIndex("start_at"));
//            String end = cursor.getString(cursor.getColumnIndex("end_at"));
//
//            etId.setText(id);
//            etName.setText(name);
//            etCourse.setText(course);
//            etStart.setText(start);
//            etEnd.setText(end);
        }


        return viewEdit;
    }



    public class getSingleCourses extends AsyncTask<String, Integer, Cursor> {

        @Override
        protected Cursor doInBackground(String... params) {
            MainActivity mainActivity = (MainActivity)getActivity();
            DatabaseHelper dbHelper = new DatabaseHelper(getActivity(),"courses",null,1);
            Cursor cursor = dbHelper.getSingleCourse(Long.parseLong(mainActivity._id));
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            MainActivity mainActivity = (MainActivity)getActivity();
            cursor.moveToFirst();
            mainActivity._id = cursor.getString(cursor.getColumnIndex("_id"));
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String course = cursor.getString(cursor.getColumnIndex("course_code"));
            String start = cursor.getString(cursor.getColumnIndex("start_at"));
            String end = cursor.getString(cursor.getColumnIndex("end_at"));

            etId.setText(id);
            etName.setText(name);
            etCourse.setText(course);
            etStart.setText(start);
            etEnd.setText(end);

        }
    }



}
