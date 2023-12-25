package dao.custom.impl;

import dao.util.CrudUtil;
import dao.util.HibernateUtil;
import db.DBConection;
import dto.ItemDto;
import dao.custom.ItemDao;
import entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {
    @Override
    public List<Item> getAll() throws SQLException, ClassNotFoundException {

        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Item");
        List<Item> itemList = query.list();
//        String sql = "SELECT * FROM item";
//        ResultSet rslt = CrudUtil.execute(sql);
        return itemList;
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
//        String sql = "DELETE FROM item WHERE code=?";
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.find(Item.class,code));
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
//        String sql = "INSERT INTO item VALUES(?,?,?,?)";
//        PreparedStatement pstm = DBConection.getInstance().getConnection().prepareStatement(sql);
//        pstm.setString(1,dto.getCode());
//        pstm.setString(2, dto.getDesc());
//        pstm.setDouble(3,dto.getUnitPrice());
//        pstm.setInt(4,dto.getQty());
//        int rst = pstm.executeUpdate();
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?";
//        PreparedStatement pstm = DBConection.getInstance().getConnection().prepareStatement(sql);
//        pstm.setString(1, dto.getDesc());
//        pstm.setDouble(2,dto.getUnitPrice());
//        pstm.setInt(3,dto.getQty());
//        pstm.setString(4, dto.getCode());
//        int rst = pstm.executeUpdate();
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Item item = session.find(Item.class, entity.getCode());
        item.setDescription(entity.getDescription());
        item.setUnitPrice(entity.getUnitPrice());
        item.setQtyOnHand(entity.getQtyOnHand());
        session.save(item);
        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public List<Item> searchItem(String value) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM item WHERE code LIKE ? '%'";
//        PreparedStatement pstm = DBConection.getInstance().getConnection().prepareStatement(sql);
//        pstm.setString(1,code);
//        ResultSet rslt = pstm.executeQuery();
//        ResultSet rslt = CrudUtil.search(sql, code);
//        while (rslt.next()){
//            list.add(new Item(rslt.getString(1),
//                    rslt.getString(2),
//                    rslt.getInt(3),
//                    rslt.getInt(4)));
        Session session = HibernateUtil.getSession();
        Query<Item> query = session.createQuery("FROM Item WHERE description LIKE '"+value+"%'");
        List<Item> list = query.list();
        return list;
    }

    @Override
    public Item getItem(String code) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM item WHERE code=?";
//        PreparedStatement pstm = DBConection.getInstance().getConnection().prepareStatement(sql);
//        pstm.setString(1, code);
//        ResultSet resultSet = pstm.executeQuery();
//        if (resultSet.next()) {
//            return new Item(
//                    resultSet.getString(1),
//                    resultSet.getString(2),
//                    resultSet.getDouble(3),
//                    resultSet.getInt(4)
//            );
//        }
        Session session = HibernateUtil.getSession();
        Item item = session.find(Item.class, code);
        session.close();
        return item;
    }
}
