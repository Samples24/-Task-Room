package projects.mostafagad.task_Room.helpers;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import projects.mostafagad.task_Room.interfaces.RoomOper;
import projects.mostafagad.task_Room.models.PostModel_Room;

@Database(entities = {PostModel_Room.class}, version = 1, exportSchema = false)
public abstract class RoomDataBase extends RoomDatabase {

    public abstract RoomOper oper();
}
