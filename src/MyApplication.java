import javafx.application.Application;
import javafx.stage.Stage;
import model.ProjectListModel;
import model.ProjectListModelManager;
import view.ViewHandler;

public class MyApplication extends Application {
    @Override
    public void start(Stage stage) {
        ProjectListModel model = new ProjectListModelManager();
        ViewHandler view = new ViewHandler(model);
        view.start(stage);
    }
}
