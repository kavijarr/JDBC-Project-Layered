package bo.custom;

import bo.SuperBo;
import dto.CustomerDto;
import dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemBo extends SuperBo {

    public List<ItemDto> allItems() throws SQLException, ClassNotFoundException;
    public Boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException;
    public Boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException;
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException;
    public List<ItemDto> searchItem(String Code) throws SQLException, ClassNotFoundException;
    public ItemDto getItem(String code) throws SQLException, ClassNotFoundException;
}
