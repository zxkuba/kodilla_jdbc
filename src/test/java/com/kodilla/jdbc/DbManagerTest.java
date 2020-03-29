package com.kodilla.jdbc;

import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class DbManagerTest {

    @Test
    public void testConnect() throws SQLException{
        //Given
        //When
        DbManager dbManager = DbManager.getInstance();
        //Then
        Assert.assertNotNull(dbManager.getConnection());
    }

    @Test
    public void testSelectUsers() throws SQLException{
        //Given
        DbManager dbManager = DbManager.getInstance();
        //When
        String sqlQuery = "SELECT * FROM USERS";
        Statement statement = dbManager.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sqlQuery);
        //Then
        int counter = 0;
        while (rs.next()){
            System.out.println(rs.getInt("ID") + ", " +
                    rs.getString("FIRSTNAME") + ", " +
                    rs.getString("LASTNAME"));
            counter ++;
        }
        rs.close();
        statement.close();
        Assert.assertEquals(5, counter);
    }

    @Test
    public void testSelectUsersAndPosts() throws  SQLException{
        //Given
        DbManager dbManager = DbManager.getInstance();
        //When
        String sqlQuery = "select U.FIRSTNAME, U.LASTNAME, count(*) AS POST_COUNT " +
                "from users U join posts P on U.ID = P.USER_ID " +
                "group by USER_ID " +
                "having count(*) >1";
        Statement statement = dbManager.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sqlQuery);
        //Then
        int counter = 0;
        while (rs.next()){
            System.out.println(rs.getString("FIRSTNAME")+ ", "+
                    rs.getString("LASTNAME")+", "+
                    rs.getInt("POST_COUNT"));
            counter ++;
        }
        rs.close();
        statement.close();
        Assert.assertEquals(1, counter);
    }

}