import javafx.application.Application;
import javafx.stage.Stage;
import model.PMSModel;
import model.PMSModelManager;
import view.ViewHandler;

public class MyApplication extends Application {
    @Override
    public void start(Stage stage) {
        PMSModel model = new PMSModelManager();
        ViewHandler view = new ViewHandler(model);
        view.start(stage);
    }
}
