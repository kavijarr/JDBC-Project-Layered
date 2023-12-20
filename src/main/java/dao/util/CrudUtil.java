package dao.util;

import db.DBConection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    public static <T>T execute(String sql, Object...args) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DBConection.getInstance().getConnection().prepareStatement(sql);
        if (sql.startsWith("SELECT")||sql.startsWith("select")){
            ResultSet resultSet = pstm.executeQuery();
            return (T)pstm.executeQuery();
        }
        for (int i = 0; i < args.length; i++) {
            pstm.setObject((i+1),args[i]);
        }
        return (T)(Boolean)(pstm.executeUpdate()>0);
    }

    public static <T>T search(String sql,Object  value) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DBConection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,value);
        return (T)pstm.executeQuery();
    }
}
