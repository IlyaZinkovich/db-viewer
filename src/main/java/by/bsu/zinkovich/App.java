package by.bsu.zinkovich;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        loadApplicationContext(stage);
    }

    private void loadApplicationContext(Stage stage) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ScreensConfig screens = ctx.getBean(ScreensConfig.class);
        screens.setPrimaryStage(stage);
        screens.showMainScreen();
        screens.toMenuPage();
    }
}
