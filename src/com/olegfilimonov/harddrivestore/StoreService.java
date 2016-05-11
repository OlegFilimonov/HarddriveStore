/*
 * Copyright (c) 2016 Oleg Filimonov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.olegfilimonov.harddrivestore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SQL Connector
 *
 * @author Oleg Filimonov
 */

public class StoreService {

    /**
     * This string is hidden from VCS
     * Replace this string with your connection string for Azure if your using this class
     */
    private static final String CONNECTION_STRING = Hidden.CONNECTION_STRING;

    public static List<Harddrive> getAllHardDrives() {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Declare the JDBC objects.
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);

            String request = "SELECT " +
                    "name," +
                    "size," +
                    "interface," +
                    "rotation_speed," +
                    "form_factor," +
                    "manufacturer," +
                    "country," +
                    "retail_price," +
                    "quantity" +
                    " FROM hard_drive" +
                    " JOIN dbo.manufacturer ON dbo.manufacturer.manufacturer_id = dbo.hard_drive.manufacturer_id" +
                    " JOIN dbo.countries ON dbo.countries.country_id = dbo.manufacturer.country_id" +
                    " JOIN dbo.storage ON dbo.storage.hard_drive_id = dbo.hard_drive.hard_drive_id";

            // Create and execute a SELECT SQL statement.
            statement = connection.createStatement();
            resultSet = statement.executeQuery(request);

            List<Harddrive> harddrives = new ArrayList<>();

            if (resultSet == null) return null;

            while (resultSet.next()) {

                Harddrive harddrive = new Harddrive(
                        resultSet.getString("name"),
                        resultSet.getString("manufacturer"),
                        resultSet.getString("country"),
                        resultSet.getString("interface"),
                        resultSet.getString("form_factor"),
                        resultSet.getInt("size"),
                        resultSet.getInt("rotation_speed"),
                        resultSet.getInt("retail_price"),
                        resultSet.getInt("quantity")
                );

                harddrives.add(harddrive);

            }

            return harddrives;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the connections after the data has been handled.
            if (statement != null) try {
                statement.close();
            } catch (Exception ignored) {
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception ignored) {
            }
            if (resultSet != null)
                try {
                    resultSet.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

        return null;
    }
}