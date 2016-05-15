package by.bsu.zinkovich.controller;

import by.bsu.zinkovich.ScreensConfig;
import by.bsu.zinkovich.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import static by.bsu.zinkovich.util.TableUtil.getTableColumns;
import static java.util.stream.Collectors.joining;

public class ResultController implements Initializable {

    @Resource
    Session session;
    @Resource
    ScreensConfig screens;
    @Resource
    DataSource dataSource;
    @Resource
    JdbcTemplate jdbcTemplate;
    @FXML
    TableView result;
    @FXML
    Button addButton;
    @FXML
    Button updateButton;
    @FXML
    Button deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(session.getQuery() != null) {
            addButton.setVisible(false);
            updateButton.setVisible(false);
            deleteButton.setVisible(false);
        }
        String sql = session.getQuery() != null ? session.getQuery() : "SELECT * FROM " + session.getTable();
        jdbcTemplate.query(sql, (rs) -> {
            ObservableList<ObservableList> data = FXCollections.observableArrayList();
            for(int i = 0 ; i < rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>)
                        param -> new SimpleStringProperty(ObjectUtils.toString(param.getValue().get(j)))
                );
                result.getColumns().addAll(col);
            }
            while(rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 0 ; i < rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i+1));
                }
                data.add(row);
            }
            result.setItems(data);
            return null;
        });
    }

    @FXML
    public void onAddButtonPressed(ActionEvent event) {
        screens.toAddEntryPage();
    }

    @FXML
    public void onUpdateButtonPressed(ActionEvent event) {
        session.setSelected((List) result.getSelectionModel().getSelectedItem());
        screens.toUpdateEntryPage();
    }

    @FXML
    public void onDeleteButtonPressed(ActionEvent event) {
        List<String> columns = getTableColumns(dataSource, session);
        List selected = (List) result.getSelectionModel().getSelectedItem();
        String deleteClause = "DELETE FROM " + session.getTable() + " ";
        String whereClause = "WHERE " + IntStream.range(0, 2)
                .mapToObj(i -> columns.get(i) + " = '" + selected.get(i) + "'").collect(joining(" AND "));
        jdbcTemplate.execute(deleteClause + whereClause);
        screens.toResultPage();
    }

    @FXML
    public void onBackButtonPressed(ActionEvent event) {
        screens.toMenuPage();
        session.setQuery(null);
    }
}
