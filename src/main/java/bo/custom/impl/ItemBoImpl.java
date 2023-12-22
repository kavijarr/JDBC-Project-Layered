package bo.custom.impl;

import bo.custom.ItemBo;
import dao.custom.ItemDao;
import dao.util.DaoFactory;
import dao.util.DaoType;
import dto.CustomerDto;
import dto.ItemDto;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo {

    private ItemDao itemDao = DaoFactory.getInstance().getDao(DaoType.ITEM);
    @Override
    public List<ItemDto> allItems() throws SQLException, ClassNotFoundException {
        List<ItemDto> dtoList =new ArrayList();
        List<Item> list = itemDao.allItems();

        for(Item entity: list){
            dtoList.add(new ItemDto(
                    entity.getCode(),
                    entity.getDescription(),
                    entity.getUnitPrice(),
                    entity.getQtyOnHand()
            ));
        }
        return dtoList;
    }

    @Override
    public Boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDao.saveItem(new Item(
                dto.getCode(),
                dto.getDesc(),
                dto.getUnitPrice(),
                dto.getQty()
        ));
    }

    @Override
    public Boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDao.updateItem(new Item(
                dto.getCode(),
                dto.getDesc(),
                dto.getUnitPrice(),
                dto.getQty()
        ));
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDao.deleteItem(code);
    }

    @Override
    public List<ItemDto> searchItem(String code) throws SQLException, ClassNotFoundException {
        List<ItemDto> dtoList = new ArrayList();
        List<Item> list = itemDao.searchItem(code);

        for(Item entity:list){
            dtoList.add(new ItemDto(
                    entity.getCode(),
                    entity.getDescription(),
                    entity.getUnitPrice(),
                    entity.getQtyOnHand()
            ));
        }
        return dtoList;
    }

    @Override
    public ItemDto getItem(String code) throws SQLException, ClassNotFoundException {
        Item entity = itemDao.getItem(code);
        return new ItemDto(entity.getCode(),entity.getDescription(), entity.getQtyOnHand(), entity.getQtyOnHand());
    }
}
