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

    OrderDao orderDao = DaoFactory.getInstance(). getDao(DaoType.ORDER);

    @Override
    public String genarateId() throws SQLException, ClassNotFoundException {
        try {
            Orderdto dto = orderDao.lastOrder();
            if(dto!=null) {
                String id = dto.getOrderId();
                int num = Integer.parseInt(id.split("[D]")[1]);
                num++;
                return (String.format("D%03d",num));
            }else{
                return ("D001");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean saveOrder(Orderdto dto) throws SQLException, ClassNotFoundException {
        return orderDao.save(dto);
    }

//    @Override
//    public Boolean removeFromStock(List<OrderDetailsDto> detailsList, List<ItemDto> itemList) throws SQLException, ClassNotFoundException {
//
//        List<OrderDetail>orderDetailsEntity=new ArrayList();
//        List<Item>itemEntity=new ArrayList();
//        for(OrderDetailsDto dto:detailsList){
//            orderDetailsEntity.add(new OrderDetail(
//                    dto.getOrderID(),
//                    dto.getItemCode(),
//                    dto.getQty(),
//                    dto.getUnitPrice()
//            ));
////        }
//
//        for (ItemDto dto:itemList){
//            itemEntity.add(new Item(
//                    dto.getCode(),
//                    dto.getDesc(),
//                    dto.getUnitPrice(),
//                    dto.getQty()
//            ));
//        }
//
//        return orderDao.removeFromStock(orderDetailsEntity,itemEntity);
//    }
}
