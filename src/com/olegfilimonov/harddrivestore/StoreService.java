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

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SQL Connector
 *
 * @author Oleg Filimonov
 */

@ApplicationScoped
@ManagedBean(name = "storeService")
public class StoreService {

    /**
     * This string is hidden from VCS
     * Replace this string with your connection string for Azure if your using this class
     */
    private static final String CONNECTION_STRING = Hidden.CONNECTION_STRING;

    public List<HardDrive> getAllHardDrives() {

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
                    " LEFT JOIN dbo.storage ON dbo.storage.hard_drive_id = dbo.hard_drive.hard_drive_id";

            // Create and execute a SELECT SQL statement.
            statement = connection.createStatement();
            resultSet = statement.executeQuery(request);

            List<HardDrive> hardDrives = new ArrayList<>();

            if (resultSet == null) return null;

            while (resultSet.next()) {

                HardDrive hardDrive = new HardDrive(
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

                hardDrives.add(hardDrive);

            }

            return hardDrives;

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

    public List<Order> getAllOrders() {
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
                    "[ORDER].quantity," +
                    "order_date," +
                    "first_name," +
                    "last_name," +
                    "NAME," +
                    "manufacturer," +
                    "retail_price" +
                    " FROM dbo.[ORDER]" +
                    " JOIN dbo.hard_drive ON dbo.[ORDER].hard_drive_id = dbo.hard_drive.hard_drive_id" +
                    " JOIN dbo.customer ON dbo.[ORDER].customer_id = dbo.customer.customer_id" +
                    " JOIN dbo.manufacturer ON dbo.hard_drive.manufacturer_id = dbo.manufacturer.manufacturer_id" +
                    " JOIN dbo.storage ON dbo.hard_drive.hard_drive_id = dbo.storage.hard_drive_id";

            // Create and execute a SELECT SQL statement.
            statement = connection.createStatement();
            resultSet = statement.executeQuery(request);

            List<Order> orders = new ArrayList<>();

            if (resultSet == null) return null;

            while (resultSet.next()) {

                Order order = new Order(
                        resultSet.getString("manufacturer") + " " + resultSet.getString("name"),
                        resultSet.getString("last_name") + " " + resultSet.getString("first_name"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("retail_price"),
                        resultSet.getDate("order_date")
                );

                orders.add(order);

            }

            return orders;

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

    public boolean executeSql(String request) {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Declare the JDBC objects.
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement prepsInsertProduct = null;

        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);


            prepsInsertProduct = connection.prepareStatement(
                    request,
                    Statement.RETURN_GENERATED_KEYS);
            prepsInsertProduct.execute();

            // Retrieve the generated key from the insert.
            resultSet = prepsInsertProduct.getGeneratedKeys();

            // Print the ID of the inserted row.
            while (resultSet.next()) {
                System.out.println("Generated: " + resultSet.getString(1));
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the connections after the data has been handled.
            if (prepsInsertProduct != null) try {
                prepsInsertProduct.close();
            } catch (Exception e) {
            }
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
            }
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
            }
        }

        return false;

    }
}