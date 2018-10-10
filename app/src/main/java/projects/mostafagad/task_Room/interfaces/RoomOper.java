package projects.mostafagad.task_Room.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import projects.mostafagad.task_Room.models.PostModel_Room;

@Dao
public interface RoomOper {
    @SuppressWarnings("unchecked")
    @Insert
    public void AddUser(PostModel_Room postModel_room);

    @SuppressWarnings("unchecked")
    @Query("DELETE FROM posts")
    public void Delete();

    @SuppressWarnings("unchecked")
    @Query("select * from posts")
    public List<PostModel_Room> GetData();


    @SuppressWarnings("unchecked")
    @Delete
    public void deletePost(PostModel_Room postModel_room);
}
