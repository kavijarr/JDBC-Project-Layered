package dao.custom.impl;

import dao.util.CrudUtil;
import db.DBConection;
import dto.CustomerDto;
import dao.custom.CustomerDao;
import entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public List<Customer> searchCustomer(String id) throws SQLException, ClassNotFoundException {
        List<Customer> customerList = new ArrayList<>();
        String sql = "SELECT * FROM Customer WHERE id LIKE ? '%'";
        ResultSet rslt = CrudUtil.search(sql,id);
        while (rslt.next()){
            customerList.add(new Customer(rslt.getString(1),
                    rslt.getString(2),
                    rslt.getString(3),
                    rslt.getDouble(4)
            ));
        }
        return customerList;
    }

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {

        Configuration configuration = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;

//        String sql = "INSERT INTO customer VALUES(?,?,?,?)";
//        return CrudUtil.execute(sql,entity.getId(),entity.getName(),entity.getAddress(),entity.getSalary());

    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE customer SET name=?,address=?,salary=? WHERE id=?";
//        return CrudUtil.execute(sql,entity.getName(),entity.getAddress(),entity.getSalary(),entity.getId());
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();
        Customer customer = session.find(Customer.class, entity.getId());
        customer.setName(entity.getName());
        customer.setAddress(entity.getAddress());
        customer.setSalary(entity.getSalary());
        session.save(customer);
        transaction.commit();
        session.close();
        return true;

    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
//        String sql = "DELETE FROM customer WHERE id=?";
//        return CrudUtil.execute(sql,value);

        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();
        session.delete(session.find(Customer.class,value));
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer";
            List<Customer> list = new ArrayList<>();
//        PreparedStatement pstm = DBConection.getInstance().getConnection().prepareStatement(sql);
//        ResultSet resultSet = pstm.executeQuery();
        ResultSet resultSet = CrudUtil.execute(sql);
        while(resultSet.next()){
            list.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return list;
    }
}
