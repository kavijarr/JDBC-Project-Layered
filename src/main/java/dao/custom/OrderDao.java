package dao.custom;

import dao.CrudDao;
import dao.SuperDao;
import dto.ItemDto;
import dto.OrderDetailsDto;
import dto.Orderdto;
import entity.Item;
import entity.OrderDetail;
import entity.Orders;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao extends CrudDao<Orderdto> {

    Orderdto lastOrder() throws SQLException, ClassNotFoundException;

//    boolean removeFromStock(List<OrderDetail> orders, List<Item> itemList) throws SQLException, ClassNotFoundException;
}


