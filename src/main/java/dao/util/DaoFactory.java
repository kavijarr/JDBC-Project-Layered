package dao.util;

import dao.SuperDao;
import dao.custom.impl.CustomerDaoImpl;
import dao.custom.impl.ItemDaoImpl;
import dao.custom.impl.OrderDaoimpl;
import dao.custom.impl.OrderDetailsDaolimpl;

public class DaoFactory {

    private  static DaoFactory daoFactory;

    private DaoFactory(){

    }
    public static DaoFactory getInstance(){
        return daoFactory!=null ? daoFactory : (daoFactory=new DaoFactory());
    }

    public <T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case CUSTOMER:return (T)new CustomerDaoImpl();
            case ITEM:return (T)new ItemDaoImpl();
            case ORDER:return (T)new OrderDaoimpl();
            case ORDER_DETAIL:return (T)new OrderDetailsDaolimpl();
        }
        return null;
    }
}
