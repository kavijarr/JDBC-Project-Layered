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
    public boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException {
//        boolean isDetailsSaved = true;
//        for (OrderDetailsDto dto:list) {
//            String sql = "INSERT INTO orderdetail VALUES(?,?,?,?)";
//            PreparedStatement pstm = DBConection.getInstance().getConnection().prepareStatement(sql);
//            pstm.setString(1,dto.getOrderID());
//            pstm.setString(2,dto.getItemCode());
//            pstm.setInt(3,dto.getQty());
//            pstm.setDouble(4,dto.getUnitPrice());
//            if (!(pstm.executeUpdate() >0)){
//                isDetailsSaved = false;
//            }
//        }
//        return isDetailsSaved;
        return true;
    }
}
