package android.example.todo_assignment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.UUID;

/**
 * View which used when creating a new task or editing an existing task
 */
public class TodoFragment extends Fragment {

    private static final String ARG_TODO_ID = "todo_id";

    private Todo aTodo;

    public static TodoFragment newInstance(UUID todoId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_TODO_ID, todoId);

        TodoFragment fragment = new TodoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // stores data for todo being made or edited
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        UUID todoId = (UUID) getArguments().getSerializable(ARG_TODO_ID);

        aTodo = TodoModel.get(getActivity()).getTodo(todoId);
    }

    // used to assign variables to graphical items
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        EditText aEditTitle = (EditText) view.findViewById(R.id.todo_title);
        aEditTitle.setText(aTodo.getTitle());
        aEditTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                aTodo.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EditText aEditDesc = (EditText) view.findViewById(R.id.todo_desc);
        aEditDesc.setText(aTodo.getDetail());
        aEditDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                aTodo.setDetail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // button for date picker
        Button aButtonDate = (Button) view.findViewById(R.id.todo_date);
        aButtonDate.setEnabled(true);
        aButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }

            // shows calender
            public void showDatePicker(){
                DatePickerFragment dateFragment = new DatePickerFragment();
                Calendar calendar = Calendar.getInstance();
                Bundle args = new Bundle();
                args.putInt("year",calendar.get(Calendar.YEAR));
                args.putInt("month",calendar.get(Calendar.MONTH));
                args.putInt("day",calendar.get(Calendar.DAY_OF_MONTH));

                dateFragment.setCallBack(ondate);
                dateFragment.show(getFragmentManager(), "datePicker");
            }

            // sets chosen date and saves it
            DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    aButtonDate.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear) + "/" + String.valueOf(year));
                    final String date = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear) + "/" + String.valueOf(year);
                    aTodo.setDate(date);
                }

            };

        });

        CheckBox aCompleateBox = (CheckBox) view.findViewById(R.id.todo_complete);
        aCompleateBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                Log.d("DEBUG **** TodoFragment","called onCheckedChanged");
                aTodo.setComplete(isChecked);
            }
        });

        //delete button functions
       Button aButtonDelete = (Button) view.findViewById(R.id.del_btn);
       aButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UUID todoId = (UUID) getArguments().getSerializable(ARG_TODO_ID);
                aTodo = TodoModel.get(getActivity()).getTodo(todoId);
                TodoModel.get(getActivity()).removeTodo(aTodo);
                Intent intent = new Intent (view.getContext(),TodoListActivity.class);
                startActivity(intent);
            }
        });

       // save button functions
        Button aButtonSave = (Button) view.findViewById(R.id.save_btn);
        aButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (view.getContext(),TodoListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return view;
    }

    // class for setting the date using the calender
    public static class DatePickerFragment extends DialogFragment {

        DatePickerDialog.OnDateSetListener ondateSet;
        private int year, month, day;

        public DatePickerFragment(){}

        public void setCallBack(DatePickerDialog.OnDateSetListener ondate){
            ondateSet = ondate;
        }

        @SuppressLint("NewApi")
        @Override
        public void setArguments(Bundle args){
            super.setArguments(args);
            year = args.getInt("year");
            month = args.getInt("month");
            day = args.getInt("day");
        }

        // gets current date
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), ondateSet,year,month,day);
        }

    }


}
