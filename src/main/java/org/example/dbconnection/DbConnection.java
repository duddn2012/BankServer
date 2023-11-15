package org.example.dbconnection;

import org.example.common.PropertyUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DbConnection {

    PropertyUtil propertyUtil = PropertyUtil.getInstance();

    private String url;
    private String username;
    private String password;

    public Connection getConnection() {
        try {
            url = propertyUtil.getPropertyByString("db.connection.url");
            username = propertyUtil.getPropertyByString("db.connection.username");
            password = propertyUtil.getPropertyByString("db.connection.password");
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeQuery(String query)  {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()){
            System.out.println(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 요청한 쿼리의 응답을 반환하는 메서드
     * @param query 조회 할 쿼리
     * @return 쿼리 결과 ArrayList<Map<String,Object>>로 반환, 테이블 구조와 동일함.
     * ArrayList는 Row, Map은 Column을 저장. Map<String,Object>는 컬럼명, 값이다.
     */
    public ArrayList<Map<String, Object>> getDataByQuery(String query)  {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()){

            ArrayList<Map<String, Object>> resultList = new ArrayList<>();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> rowData = new HashMap<>();
                for(int i=1;i<=columnCount;i++){
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    rowData.put(columnName,columnValue);
                }
                resultList.add(rowData);
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}