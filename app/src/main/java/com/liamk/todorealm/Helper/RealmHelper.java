package com.liamk.todorealm.Helper;
import com.liamk.todorealm.Model.ToDo;
import io.realm.Realm;

public class RealmHelper {

    public static void addTodo(Realm realm, int id, String title){
        realm.beginTransaction();
        ToDo toDo = realm.createObject(ToDo.class, id);
        toDo.setTitle(title);
        realm.commitTransaction();
    }

    public static void deleteTodo(Realm realm, final long id){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ToDo todoToRemove = realm.where(ToDo.class).equalTo("id", id).findFirst();
                if(todoToRemove != null){
                    todoToRemove.deleteFromRealm();
                }
            }
        });
    }
}
