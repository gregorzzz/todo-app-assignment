package android.example.todo_assignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;
// class for swiping between todo tasks

public class TodoPagerActivity extends AppCompatActivity {

    private static final String EXTRA_TODO_ID = "todo_id";

    private List<Todo> aTodos;

    public static Intent newIntent(Context packageContext, UUID todoId){
        Intent intent = new Intent(packageContext, TodoPagerActivity.class);
        intent.putExtra(EXTRA_TODO_ID, todoId);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_pager);

        UUID todoId = (UUID) getIntent().getSerializableExtra(EXTRA_TODO_ID);

        ViewPager aViewPager = findViewById(R.id.todo_view_pager);

        aTodos = TodoModel.get(this).getTodos();

        FragmentManager fragmentManager = getSupportFragmentManager();
        aViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Todo todo = aTodos.get(position);
                return TodoFragment.newInstance(todo.getId());
            }

            @Override
            public int getCount() {
                return aTodos.size();
            }
        });

        for(int i = 0; i < aTodos.size(); i++){
            if (aTodos.get(i).getId().equals(todoId)){
                aViewPager.setCurrentItem(i);
                break;
            }
        }


    }
}
