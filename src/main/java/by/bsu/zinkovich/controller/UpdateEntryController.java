package by.bsu.zinkovich.controller;


import by.bsu.zinkovich.ScreensConfig;
import by.bsu.zinkovich.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import static by.bsu.zinkovich.util.TableUtil.getTableColumns;
import static java.util.stream.Collectors.joining;

public class UpdateEntryController implements Initializable
{
    @FXML
    VBox vBox;
    @Resource
    Session session;
    @Resource
    ScreensConfig screens;
    @Resource
    JdbcTemplate jdbcTemplate;
    @Resource
    DataSource dataSource;
    List<TextField> textFields;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        textFields = new ArrayList<>();
        List<String> columns = getTableColumns(dataSource, session);
        IntStream.range(0, session.getSelected().size()).forEach(i -> vBox.getChildren().add(vBox.getChildren().size() - 1, createHBox(columns.get(i), session.getSelected().get(i))));
    }

    @FXML
    public void onAddButtonPressed(ActionEvent event) {
        List<String> columns = getTableColumns(dataSource, session);
        String setClause = "UPDATE " + session.getTable() + " SET " + IntStream.range(0, textFields.size()).mapToObj(i -> columns.get(i) + " = '" + textFields.get(i).getText() + "'").collect(joining(" , "));
        String whereClause = "WHERE " + IntStream.range(0, textFields.size())
                .mapToObj(i -> columns.get(i) + " = '" + session.getSelected().get(i) + "'").collect(joining(" AND "));
        jdbcTemplate.execute(setClause + whereClause);
        screens.toResultPage();
    }

    @FXML
    public void onBackButtonPressed(ActionEvent event) {
        screens.toResultPage();
    }

    private HBox createHBox(String column, String value) {
        HBox hBox = new HBox(20);
        Label label = new Label(column);
        label.setPrefWidth(100);
        hBox.getChildren().add(label);
        TextField textField = new TextField(value);
        textFields.add(textField);
        hBox.getChildren().add(textField);
        return hBox;
    }


}
