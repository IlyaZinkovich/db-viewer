package by.bsu.zinkovich.util;


import by.bsu.zinkovich.Session;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableUtil
{
    private TableUtil() {}

    public static List<String> getTableColumns(DataSource dataSource, Session session) {
        List<String> columns = new ArrayList<>();
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getColumns(null, session.getSchema(), session.getTable(), null);
            while (rs.next()) {
                columns.add(rs.getString(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return columns;
    }


    public static List<String> getTableNames(DataSource dataSource, Session session) {
        List<String> tables = new ArrayList<>();
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, session.getSchema(), "%", null);
            while (rs.next()) {
                tables.add(rs.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tables;
    }
}
