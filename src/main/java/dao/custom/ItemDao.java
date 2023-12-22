package dao.custom;

import dao.SuperDao;
import dto.ItemDto;
import entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao  extends SuperDao {
   List<Item> allItems() throws SQLException, ClassNotFoundException;
   boolean deleteItem(String code) throws SQLException, ClassNotFoundException;
   boolean saveItem(Item entity) throws SQLException, ClassNotFoundException;
   boolean updateItem(Item entity) throws SQLException, ClassNotFoundException;
   List<Item> searchItem(String code) throws SQLException, ClassNotFoundException;
   Item getItem(String code) throws SQLException, ClassNotFoundException;
}
