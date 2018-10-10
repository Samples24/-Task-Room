package projects.mostafagad.task_Room.interfaces;

import java.io.IOException;
import java.util.List;

import projects.mostafagad.task_Room.models.PostModel;

public interface Posts_Interface extends Global_interface {
    public void onSuccess(List<PostModel> postModels) throws IOException;
}
