package dao.custom.impl;

import dao.util.CrudUtil;
import db.DBConection;
import dto.ItemDto;
import dao.custom.ItemDao;
import entity.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {
    @Override
    public List<Item> allItems() throws SQLException, ClassNotFoundException {

        List<Item> itemList = new ArrayList<>();
        String sql = "SELECT * FROM item";
        ResultSet rslt = CrudUtil.execute(sql);

        while (rslt.next()){
            itemList.add(new Item(
                    rslt.getString(1),
                    rslt.getString(2),
                    rslt.getDouble(4),
                    rslt.getInt(3)
                    ));
        }
        return itemList;
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM item WHERE code=?";
        return CrudUtil.execute(sql,code);
    }

    @Override
    public boolean saveItem(Item entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO item VALUES(?,?,?,?)";
//        PreparedStatement pstm = DBConection.getInstance().getConnection().prepareStatement(sql);
//        pstm.setString(1,dto.getCode());
//        pstm.setString(2, dto.getDesc());
//        pstm.setDouble(3,dto.getUnitPrice());
//        pstm.setInt(4,dto.getQty());
//        int rst = pstm.executeUpdate();
        return CrudUtil.execute(sql,entity.getCode(),entity.getDescription(),entity.getUnitPrice(),entity.getQtyOnHand());
    }

    @Override
    public boolean updateItem(Item entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?";
//        PreparedStatement pstm = DBConection.getInstance().getConnection().prepareStatement(sql);
//        pstm.setString(1, dto.getDesc());
//        pstm.setDouble(2,dto.getUnitPrice());
//        pstm.setInt(3,dto.getQty());
//        pstm.setString(4, dto.getCode());
//        int rst = pstm.executeUpdate();
        return CrudUtil.execute(sql,entity.getDescription(),entity.getUnitPrice(),entity.getQtyOnHand(),entity.getCode());
    }

    @Override
    public List<Item> searchItem(String code) throws SQLException, ClassNotFoundException {
        List<Item>list = new ArrayList<>();
        String sql = "SELECT * FROM item WHERE code LIKE ? '%'";
//        PreparedStatement pstm = DBConection.getInstance().getConnection().prepareStatement(sql);
//        pstm.setString(1,code);
//        ResultSet rslt = pstm.executeQuery();
        ResultSet rslt = CrudUtil.search(sql, code);
        while (rslt.next()){
            list.add(new Item(rslt.getString(1),
                    rslt.getString(2),
                    rslt.getInt(3),
                    rslt.getInt(4)));
        }
        return list;
    }

    @Override
    public Item getItem(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM item WHERE code=?";
        PreparedStatement pstm = DBConection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, code);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            );
        }
        return null;
    }
}
