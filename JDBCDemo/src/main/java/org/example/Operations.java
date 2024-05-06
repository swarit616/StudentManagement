package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Operations {
    public void fetchdata(ResultSet resultSet) throws SQLException {
        while(resultSet.next()){
            System.out.println(resultSet.getInt("employee_id"));
            System.out.println(resultSet.getInt("salary"));
            System.out.println(resultSet.getString("employee_name"));
            System.out.println(resultSet.getString("position"));

        }

    }
}
