package android.example.todo_assignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays todo tasks and handles data for each task
 * Shows each fragment in as a list
 */
public class TodoListFragment extends Fragment {

    private RecyclerView aTodoRecyclerView;
    TodoAdapter tAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        aTodoRecyclerView = (RecyclerView) view.findViewById(R.id.todo_recycler_view);
        aTodoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    //displays add button in menu bar
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_todo_list, menu);
    }

    // create's a new todo task when add button is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.new_todo:

                Todo todo = new Todo();
                TodoModel.get(getActivity()).addTodo(todo);


                Intent intent = TodoPagerActivity.newIntent(getActivity(), todo.getId());
                startActivity(intent);

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    // updates list of todos
    private void updateUI(){
        TodoModel todoModel = TodoModel.get(getContext());
        ArrayList<Todo> todos = todoModel.getTodos();

        if(tAdapter == null){
            tAdapter = new TodoAdapter(todos);
            aTodoRecyclerView.setAdapter(tAdapter);
        }else{
            tAdapter.notifyDataSetChanged();
        }
    }

    // class for how each todo is displayed in the list
    // gets todo data for list
    public class TodoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Todo aTodo;
        private final TextView aTextTitle;
        private final TextView aTextDesc;
        private final TextView aTextDate;

        // data displayed in todo list
        public TodoHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_todo, parent, false));

            itemView.setOnClickListener(this);

            aTextTitle = (TextView) itemView.findViewById(R.id.todo_title);
            aTextDesc = (TextView) itemView.findViewById(R.id.todo_desc);
            aTextDate = (TextView) itemView.findViewById(R.id.todo_date);

        }

        @Override
        public void onClick(View view){
            Toast.makeText(getActivity(), aTodo.getTitle()+"clicked", Toast.LENGTH_SHORT)
                    .show();

            Intent intent = TodoPagerActivity.newIntent(getActivity(),aTodo.getId());

            startActivity(intent);

        }

        //displays set value in list
        public void bind(Todo todo){
            aTodo = todo;
            aTextTitle.setText(aTodo.getTitle());
            aTextDesc.setText(aTodo.getDetail());
            aTextDate.setText(aTodo.getDate());
        }
    }

    // makes a view for todo item
    public class TodoAdapter extends RecyclerView.Adapter<TodoListFragment.TodoHolder>{

        private final List<Todo> aTodos;

        public TodoAdapter(List<Todo> todos){
            aTodos = todos;
        }

        @NonNull
        @Override
        public TodoListFragment.TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new TodoHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TodoHolder holder, int position) {
            Todo todo = aTodos.get(position);
            holder.bind(todo);
        }

        @Override
        public int getItemCount() {
            return aTodos.size();
        }



    }

}
