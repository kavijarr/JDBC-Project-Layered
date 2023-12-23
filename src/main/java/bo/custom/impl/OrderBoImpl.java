package bo.custom.impl;

import bo.custom.OrderBo;
import dao.custom.OrderDao;
import dao.util.CrudUtil;
import dao.util.DaoFactory;
import dao.util.DaoType;
import dto.ItemDto;
import dto.OrderDetailsDto;
import dto.Orderdto;
import entity.Item;
import entity.OrderDetail;
import entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBoImpl implements OrderBo {

    OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);
    @Override
    public Orderdto lastOrder() throws SQLException, ClassNotFoundException {
        Orders entity = orderDao.lastOrder();
        return new Orderdto(entity.getOrderId(),entity.getDate(),entity.getCustomerId(),null);
    }

    @Override
    public Boolean saveOrder(Orderdto dto) throws SQLException, ClassNotFoundException {
        return orderDao.saveOrder(new Orders(dto.getOrderId(),dto.getDate(),dto.getCustId(),null));
    }

    @Override
    public Boolean removeFromStock(List<OrderDetailsDto> detailsList, List<ItemDto> itemList) throws SQLException, ClassNotFoundException {

        List<OrderDetail>orderDetailsEntity=new ArrayList();
        List<Item>itemEntity=new ArrayList();
        for(OrderDetailsDto dto:detailsList){
            orderDetailsEntity.add(new OrderDetail(
                    dto.getOrderID(),
                    dto.getItemCode(),
                    dto.getQty(),
                    dto.getUnitPrice()
            ));
        }

        for (ItemDto dto:itemList){
            itemEntity.add(new Item(
                    dto.getCode(),
                    dto.getDesc(),
                    dto.getUnitPrice(),
                    dto.getQty()
            ));
        }

        return orderDao.removeFromStock(orderDetailsEntity,itemEntity);
    }
}
