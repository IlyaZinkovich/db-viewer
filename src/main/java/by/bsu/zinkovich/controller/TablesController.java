package by.bsu.zinkovich.controller;

import by.bsu.zinkovich.ScreensConfig;
import by.bsu.zinkovich.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.net.URL;
import java.util.ResourceBundle;

import static by.bsu.zinkovich.util.TableUtil.getTableNames;

public class TablesController implements Initializable
{
    @FXML
    VBox vBox;
    @Resource
    DataSource dataSource;
    @Resource
    Session session;
    @Resource
    ScreensConfig screens;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        getTableNames(dataSource, session).stream().forEach(i -> vBox.getChildren().add(vBox.getChildren().size() - 1, createButton(i)));
    }

    @FXML
    public void onBackButtonPressed(javafx.event.ActionEvent event) {
        session.setTable(null);
        screens.toMenuPage();
    }

    private Button createButton(String table) {
        Button button = new Button(table);
        button.setOnAction(e -> {
            session.setTable(table);
            screens.toResultPage();
        });
        return button;
    }

}
