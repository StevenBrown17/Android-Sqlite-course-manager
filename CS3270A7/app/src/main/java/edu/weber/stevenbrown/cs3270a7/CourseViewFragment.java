package edu.weber.stevenbrown.cs3270a7;


import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseViewFragment extends Fragment {

    EditText etIdView, etNameView, etCourseView, etStartView, etEndView;


    public CourseViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.fragment_course_view,container,false);

        DatabaseHelper dbHelper = new DatabaseHelper(getActivity(),"courses",null,1);

        etIdView = (EditText)view.findViewById(R.id.etIdView);
        etIdView.setEnabled(false);
        etNameView=(EditText)view.findViewById(R.id.etNameView);
        etNameView.setEnabled(false);
        etCourseView = (EditText)view.findViewById(R.id.etCourseView);
        etCourseView.setEnabled(false);
        etStartView = (EditText)view.findViewById(R.id.etStartView);
        etStartView.setEnabled(false);
        etEndView = (EditText) view.findViewById(R.id.etEndView);
        etEndView.setEnabled(false);

        setHasOptionsMenu(true);

        MainActivity mainActivity = (MainActivity)getActivity();

//        //Cursor cursor = dbHelper.getSingleCourse(mainActivity.index+1);
//        Cursor cursor = dbHelper.getAllCourses();
//        cursor.moveToFirst();
//        for(int i=0; i < mainActivity.index; i++)
//            cursor.moveToNext();
//        mainActivity._id = cursor.getString(cursor.getColumnIndex("_id"));
//        String id = cursor.getString(cursor.getColumnIndex("id"));
//        String name = cursor.getString(cursor.getColumnIndex("name"));
//        String course = cursor.getString(cursor.getColumnIndex("course_code"));
//        String start = cursor.getString(cursor.getColumnIndex("start_at"));
//        String end = cursor.getString(cursor.getColumnIndex("end_at"));
//
//        etIdView.setText(id);
//        etNameView.setText(name);
//        etCourseView.setText(course);
//        etStartView.setText(start);
//        etEndView.setText(end);

        new getAllCourses().execute("");


        return view;
    }



    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_course_view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.edit:
                    MainActivity mainActivity = (MainActivity)getActivity();
                    mainActivity.openEditFragment();
                    return true;
            case R.id.delete:
                    showNoticeDialog();
                    return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showNoticeDialog(){
        //LayoutInflater inflater = LayoutInflater.from(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity mainActivity = (MainActivity)getActivity();
                        DatabaseHelper dbHelper = new DatabaseHelper(getActivity(),"courses",null,1);
                        dbHelper.deleteCourse(Long.parseLong(mainActivity._id));
                        mainActivity.openListFragment();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity mainActivity = (MainActivity)getActivity();
                        mainActivity.openListFragment();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

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

            MainActivity mainActivity = (MainActivity)getActivity();

            cursor.moveToFirst();
            for(int i=0; i < mainActivity.index; i++)
                cursor.moveToNext();
            mainActivity._id = cursor.getString(cursor.getColumnIndex("_id"));
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String course = cursor.getString(cursor.getColumnIndex("course_code"));
            String start = cursor.getString(cursor.getColumnIndex("start_at"));
            String end = cursor.getString(cursor.getColumnIndex("end_at"));

            etIdView.setText(id);
            etNameView.setText(name);
            etCourseView.setText(course);
            etStartView.setText(start);
            etEndView.setText(end);

            Log.d("test","_id "+mainActivity._id + "\nid "+id+"\nname "+name+"\ncourse "+course+"\nstart_at "+start+"\nend_at "+end);

        }
    }





}
