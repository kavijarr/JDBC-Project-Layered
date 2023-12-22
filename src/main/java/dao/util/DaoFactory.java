package dao.util;

import dao.SuperDao;
import dao.custom.impl.CustomerDaoImpl;
import dao.custom.impl.ItemDaoImpl;

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
        }
        return null;
    }
}
