package com.CarManagement.CarUI;

import com.CarManagement.DA.DBconnection;
import com.CarManagement.DBO.Order;
import com.CarManagement.DBO.OrderTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.InputStream;
import java.sql.*;

public class OrderCarDA {
    private DBconnection database = new DBconnection();
    private Connection connection;
    private Statement statement;
    private PreparedStatement stm;
    private ResultSet resultSet;

    public void insertOrder(int cusid,String orderdate,String deliverdate,String address){
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            String sql = "INSERT INTO `carmanagement`.`order` (`cusid`, `orderdate`, `deliverdate`, `address`,`status`) VALUES (?,?,?,?,'hiring');";
            stm= connection.prepareStatement(sql);
            stm.setInt(1,cusid);
            stm.setString(2,orderdate);
            stm.setString(3,deliverdate);
            stm.setString(4,address);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertOrderDetail(int orderid,String pay,int cardid, double price, String cardNum,int cvv,String carddate){
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            String sql = "INSERT INTO `carmanagement`.`orderdetail` (`orderid`,`pay`,`carid`,`Price`,`cardNum`,`cvv`,`carddate`) VALUES (?,?,?,?,?,?,?)";
            stm = connection.prepareStatement(sql);
            stm.setInt(1,orderid);
            stm.setString(2,pay);
            stm.setInt(3,cardid);
            stm.setDouble(4,price);
            stm.setString(5,cardNum);
            stm.setInt(6,cvv);
            stm.setString(7,carddate);
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void updateStock(int carid,int stock){
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            String sql = "UPDATE  `carmanagement`.`car` set availablestock = '"+stock+"' where carid = '"+carid+"'";
            stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void selectOrderid(int cusid){
        try {
            String selectSQL = "SELECT MAX(id) FROM carmanagement.order where cusid = '"+cusid+"'";
            Statement stm = connection.createStatement();
            ResultSet res = stm.executeQuery(selectSQL);
            while(res.next()){
                int orderid = res.getInt("MAX(id)");
                System.out.println(orderid);
                Order order = new Order(orderid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList getOrderInfor(String sql) throws SQLException {
        ObservableList<OrderTable> orderTables = FXCollections.observableArrayList();
        connection = database.getConnection();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            orderTables.add(new OrderTable(
                    resultSet.getInt("id"),
                    resultSet.getInt("cusid"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("carid"),
                    resultSet.getString("orderdate"),
                    resultSet.getString("pay"),
                    resultSet.getString("status")


            ));
        }
        connection.close();
        statement.close();
        resultSet.close();
        return orderTables;
    }
    public void updateOrderCar(String status, int id){
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            String sql = "UPDATE  `carmanagement`.`order` set status = '"+status+"' where id = '"+id+"'";
            stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateFinishedCar(String status, int id){
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            String sql = "UPDATE  `carmanagement`.`order` set notice = 'notyet', status = '"+status+"' where id = '"+id+"'";
            stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateAvaiFinishedCar(int id){
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            String sql = "Update car set availablestock = availablestock +1 where carID = "+id+"";
            stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
