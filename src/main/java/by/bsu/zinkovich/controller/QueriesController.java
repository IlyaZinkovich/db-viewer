package by.bsu.zinkovich.controller;


import by.bsu.zinkovich.ScreensConfig;
import by.bsu.zinkovich.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.annotation.Resource;

public class QueriesController {

    @Resource
    Session session;
    @Resource
    ScreensConfig screens;
    @FXML
    TextField musicianName;

    @FXML
    public void onFirstQueryButtonPressed(ActionEvent event) {
        session.setQuery("SELECT * FROM ALBUM A WHERE (EXTRACT(year FROM A.YEAR)) = extract(year from sysdate) - 1 AND (EXTRACT(month FROM A.YEAR)) < 7");
        screens.toResultPage();
    }

    @FXML
    public void onSecondQueryButtonPressed(ActionEvent event) {
        session.setQuery("SELECT B.BAND_ID, B.BAND_NAME, B.BAND_FORMATION, B.BAND_DISBANDING " +
                "FROM BAND B INNER JOIN (MUSICIAN M INNER JOIN INBAND_MUSICIAN IM ON M.MUSICIAN_ID = IM.MUSICIAN_ID) ON B.BAND_ID = IM.BAND_ID " +
                "WHERE (((M.MUSICIAN_NAME)='" + musicianName.getText() + "'))");
        screens.toResultPage();
    }

    @FXML
    public void onThirdQueryButtonPressed(ActionEvent event) {
        session.setQuery("SELECT A.ALBUM_ID, A.ALBUM_NAME, A.YEAR, A.TYPE " +
                "FROM (MUSICIAN M " +
                "INNER JOIN INVITED_MUSICIAN IM " +
                "ON M.MUSICIAN_ID = IM.MUSICIAN_ID) " +
                "INNER JOIN ALBUM A " +
                "ON IM.ALBUM_ID = A.ALBUM_ID " +
                "WHERE M.MUSICIAN_NAME = '" + musicianName.getText() + "'");
        screens.toResultPage();
    }

    @FXML
    public void onFourthQueryButtonPressed(ActionEvent event) {
        session.setQuery("SELECT B.BAND_ID, B.BAND_NAME, B.BAND_FORMATION, B.BAND_DISBANDING " +
                "FROM BAND B WHERE B.BAND_ID NOT IN (" +
                "SELECT B1.BAND_ID FROM " +
                "(" +
                "MUSICIAN M INNER JOIN INBAND_MUSICIAN IM ON M.MUSICIAN_ID = IM.MUSICIAN_ID " +
                ")" +
                "INNER JOIN BAND B1 ON IM.BAND_ID = B1.BAND_ID " +
                "WHERE IM.END_YEAR IS NOT NULL " +
                ")");
        screens.toResultPage();
    }

    @FXML
    public void onBackButtonPressed(ActionEvent event) {
        screens.toMenuPage();
    }
}
