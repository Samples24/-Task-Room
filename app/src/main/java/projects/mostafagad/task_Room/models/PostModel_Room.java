package projects.mostafagad.task_Room.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "posts")
public class PostModel_Room
{

    @PrimaryKey
    private int id ;

    @ColumnInfo(name = "UserId")
    private int UserId ;

    @ColumnInfo(name = "Title")
    private String Title ;

    @ColumnInfo(name = "Body")
    private String Body ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }
}
