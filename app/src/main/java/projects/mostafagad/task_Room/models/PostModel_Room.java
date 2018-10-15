package projects.mostafagad.task_Room.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "posts")
public class PostModel_Room {
    private static PostModel_Room instance;
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "UserId")
    private int UserId;
    @ColumnInfo(name = "Title")
    private String Title;
    @ColumnInfo(name = "Body")
    private String Body;

    public PostModel_Room() {
    }

    public PostModel_Room(int id, int userId, String title, String body) {
        this.id = id;
        this.UserId = userId;
        this.Title = title;
        this.Body = body;
    }

    public static PostModel_Room getInstance(Integer userId, Integer id, String title, String body) {
        if (instance == null) {
            synchronized (PostModel.class) {
                if (instance == null) {
                    System.out.println("getInstance(): First time getInstance was invoked!");
                    instance = new PostModel_Room(id, userId, title, body);
                }
            }
        }

        return instance;
    }

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
