package com.company;

import java.sql.*;

public class Main {
    private static Statement statement;
    private static Connection connection;
    private static String createDatabase = "CREATE TABLE IF NOT EXISTS Cat " +
            "(CatID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "Name TEXT NOT NULL, Age INTEGER NOT NULL)";
 //   private static String updateCat = "insert into Cat if not exists (Name, Age) values ('Dusya', 2), ('Vasya', 4)";
 private static PreparedStatement ps;
 private static String createCat;
 private static String updateCat;
 private static String deleteCat;
//    private static String dl = "delete from Cat where name = 'Vasya'";
//private static String up = "update  Cat set Name = 'Tom' where Name = ?";


    private static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:homework.db");
    }

    private static void close() throws SQLException {
        connection.close();
    }

    private static void createCats(Cat cat) throws SQLException { //it's work
        createCat = "insert into Cat (Name, Age) values (?, ?)";
        ps = connection.prepareStatement(createCat);
        ps.setString(1, cat.getName());
        ps.setInt(2, cat.getAge());
        ps.executeUpdate();
    }

//    private static void deleteCats(Cat cat) throws SQLException {//so-so
//        deleteCat = "delete from Cat where name = ? ";
//        ps = connection.prepareStatement(deleteCat);
//        ps.setString(1, cat.getName());
//        ps.executeUpdate();
//    }
    private static void deleteCats(String s) throws SQLException {//best

        deleteCat = "delete from Cat where name = ? ";
        ps = connection.prepareStatement(deleteCat);
        ps.setString(1, s);
        ps.executeUpdate();
    }

    private static void updateCats(Cat cat, String s, int i) throws SQLException {// it is work
        updateCat = "update Cat set (Name, Age) = (?,?) where (Name, Age) = (?, ?) ";
        ps = connection.prepareStatement(updateCat);
        ps.setString(1, s);
        ps.setInt(2, i);
        ps.setString(3,cat.getName());
        ps.setInt(4,cat.getAge());
        ps.executeUpdate();
    }



    private static void printCats(Cat cat) throws SQLException { //it is work
        ResultSet rs = statement.executeQuery("select CatID, Name, Age from Cat");
        while (rs.next()){
            System.out.println("Cat name - " + cat.getName() + " Cat age - " + cat.getAge());
        }
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connect();
        statement = connection.createStatement();
        statement.execute(createDatabase);
//        statement.executeUpdate(up);
        ResultSet rs = statement.executeQuery("select CatID, Name, Age from Cat");



        Cat c = new Cat("Gosha", 7);
        Cat c2 = new Cat("Bob", 2);
        Cat c3 = new Cat("Lulu", 4);
//        createCats(c3);
//        deleteCats(c);
//        printCats(c2);
        updateCats(c2, "kimito-chan", 12);
        deleteCats("op");



        while (rs.next()){
            System.out.println("Cat name - " + rs.getString("Name") + " Cat age - " + rs.getInt(3));
        }
        close();

    }
}
