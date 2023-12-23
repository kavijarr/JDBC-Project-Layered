package dao.custom.impl;

import db.DBConection;
import dto.OrderDetailsDto;
import dao.custom.OrderDetailsDao;
import entity.OrderDetail;
import entity.Orders;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsDaolimpl implements OrderDetailsDao {
    @Override
    public boolean saveOrderDetails(List<OrderDetail> list) throws SQLException, ClassNotFoundException {
        boolean isDetailsSaved = true;
        for (OrderDetail entity:list) {
            String sql = "INSERT INTO orderdetail VALUES(?,?,?,?)";
            PreparedStatement pstm = DBConection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,entity.getOrderId());
            pstm.setString(2,entity.getItemCode());
            pstm.setInt(3,entity.getQty());
            pstm.setDouble(4,entity.getUnitPrice());
            if (!(pstm.executeUpdate() >0)){
                isDetailsSaved = false;
            }
        }
        return isDetailsSaved;
    }
}
