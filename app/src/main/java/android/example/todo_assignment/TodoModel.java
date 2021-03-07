package android.example.todo_assignment;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;
// class used for storing data about todos
public class TodoModel {
    private static TodoModel sTodoModel;

    private final ArrayList<Todo> aTodoList; // stores todo data

    public static TodoModel get(Context context) {
        if (sTodoModel == null) {
            sTodoModel = new TodoModel(context);
        }
        return sTodoModel;
    }

    private TodoModel(Context context){
        aTodoList = new ArrayList<>();

        // simulate some data for testing

        for (int i=0; i < 5; i++){
            Todo todo = new Todo();
            todo.setTitle("Todo title " + i);
            todo.setDetail("Detail for task " );
            todo.setComplete(false);

            aTodoList.add(todo);
        }

    }

    public Todo getTodo(UUID todoId) {

        for (Todo todo : aTodoList) {
            if (todo.getId().equals(todoId)){
                return todo;
            }
        }

        return null;
    }

    public ArrayList<Todo> getTodos() {

        return aTodoList;

    }

    public void addTodo(Todo todo){

        aTodoList.add(todo);

    }

    public void removeTodo(Todo todo){

        aTodoList.remove(todo);

    }


}
