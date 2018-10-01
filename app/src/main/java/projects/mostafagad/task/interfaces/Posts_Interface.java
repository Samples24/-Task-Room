package projects.mostafagad.task.interfaces ;

import java.io.IOException;
import java.util.List;

import projects.mostafagad.task.models.PostModel;

public interface Posts_Interface extends Global_interface {
    public void onSuccess(List<PostModel> postModels) throws IOException;
}
