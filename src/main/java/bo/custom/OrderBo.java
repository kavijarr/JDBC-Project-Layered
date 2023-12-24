package bo.custom;

import bo.SuperBo;
import dto.ItemDto;
import dto.OrderDetailsDto;
import dto.Orderdto;

import java.sql.SQLException;
import java.util.List;

public interface OrderBo extends SuperBo {
    String genarateId() throws SQLException, ClassNotFoundException;
    Boolean saveOrder(Orderdto dto) throws SQLException, ClassNotFoundException;
    Boolean removeFromStock(List<OrderDetailsDto>detailsList, List<ItemDto> itemList) throws SQLException, ClassNotFoundException;
}
