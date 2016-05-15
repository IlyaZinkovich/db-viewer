package by.bsu.zinkovich;

import by.bsu.zinkovich.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import java.net.URL;

@Configuration
@Lazy
public class ScreensConfig {

    private Stage stage;
    private Scene scene;
    private StackPane root;

    public void setPrimaryStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void showMainScreen() {
        root = new StackPane();
        stage.setTitle("DB Viewer");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void toTop(Node node) {
        root.getChildren().setAll(node);
    }

    public void toMenuPage() {
        toTop(getNode(menuController(), "menu.fxml"));
    }

    public void toQueriesPage() {
        toTop(getNode(queriesController(), "queries.fxml"));
    }

    public void toResultPage() {
        toTop(getNode(resultController(), "result.fxml"));
    }

    public void toTablesPage() {
        toTop(getNode(tablesController(), "tables.fxml"));
    }

    public void toAddEntryPage() {
        toTop(getNode(addEntryController(), "add.fxml"));
    }

    public void toUpdateEntryPage() {
        toTop(getNode(updateEntryController(), "update.fxml"));
    }


    @Bean
    @Scope("prototype")
    MenuController menuController() {
        return new MenuController();
    }

    @Bean
    @Scope("prototype")
    TablesController tablesController() {
        return new TablesController();
    }

    @Bean
    @Scope("prototype")
    QueriesController queriesController() {
        return new QueriesController();
    }

    @Bean
    @Scope("prototype")
    ResultController resultController() {
        return new ResultController();
    }

    @Bean
    @Scope("prototype")
    AddEntryController addEntryController() {
        return new AddEntryController();
    }

    @Bean
    @Scope("prototype")
    UpdateEntryController updateEntryController() {
        return new UpdateEntryController();
    }

    private Node getNode(Object controller, String view) {
        URL location = this.getClass().getClassLoader().getResource(view);
        FXMLLoader loader = new FXMLLoader(location);
        loader.setControllerFactory(clazz -> controller);
        try {
            return (Node) loader.load();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
