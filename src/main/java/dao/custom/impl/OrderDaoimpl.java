package dao.custom.impl;

import dao.util.CrudUtil;
import dao.util.DaoFactory;
import dao.util.DaoType;
import dao.util.HibernateUtil;
import db.DBConection;
import dto.ItemDto;
import dto.OrderDetailsDto;
import dto.Orderdto;
import dao.custom.OrderDetailsDao;
import dao.custom.OrderDao;
import entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoimpl implements OrderDao {

    OrderDetailsDao orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
    @Override
    public boolean save(Orderdto dto) throws SQLException {
//        Connection connection=null;
//        try {
//            connection = DBConection.getInstance().getConnection();
//            connection.setAutoCommit(false);
//            String sql = "INSERT INTO orders VALUES(?,?,?)";
//            PreparedStatement pstm = connection.prepareStatement(sql);
//            pstm.setString(1, dto.getOrderId());
//            pstm.setString(2, dto.getDate());
//            pstm.setString(3, dto.getCustId());
//            if (pstm.executeUpdate() > 0) {
//                boolean isDetailSaved = orderDetailsDao.saveOrderDetails(dto.getList());
//                if (isDetailSaved) {
//                    connection.commit();
//                    return true;
//                }
//            }
//        }catch (SQLException | ClassNotFoundException ex){
//            connection.rollback();
//        }finally {
//            connection.setAutoCommit(true);
//        }
//        return false;
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Orders order = new Orders(dto.getOrderId(),dto.getDate());
        order.setCustomer(session.find(Customer.class,dto.getCustId()));
        session.save(order);

        List<OrderDetailsDto> list = dto.getList();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (OrderDetailsDto orderdto: list){
            OrderDetail orderDetail = new OrderDetail(
                    new OrderDetailsKey(orderdto.getOrderID(), orderdto.getItemCode()),
                    session.find(Item.class, orderdto.getItemCode()),
                    order,
                    orderdto.getUnitPrice(),
                    orderdto.getQty()

            );
            session.save(orderDetail);
            orderDetailList.add(orderDetail);
        }
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Orderdto entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Orderdto> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Orderdto lastOrder() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orders ORDER BY orderId DESC LIMIT 1";
//        PreparedStatement pstm = DBConection.getInstance().getConnection().prepareStatement(sql);
//        ResultSet resultSet = pstm.executeQuery();
        ResultSet resultSet = CrudUtil.execute(sql);

        if(resultSet.next()){
            return new Orderdto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    null
            );
        }
        return null;
    }

//    @Override
//    public boolean removeFromStock(List<OrderDetail> orders, List<Item> itemList) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE item SET qtyOnHand =? WHERE code =?";
//
//        PreparedStatement pstm = DBConection.getInstance().getConnection().prepareStatement(sql);
//            for (OrderDetail order:orders) {
//                for (Item item:itemList) {
//                    if (order.getItemCode().equals(item.getCode())){
//                        pstm.setInt(1,(item.getQtyOnHand()-order.getQty()));
//                        pstm.setString(2,order.getItemCode());
//                        pstm.executeUpdate();
//                    }
//                }
//            }
//        return true;
//    }

}
