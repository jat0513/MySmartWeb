package com.smartmvc.service;

import com.smartmvc.annotation.Service;
import com.smartmvc.annotation.Transaction;
import com.smartmvc.helper.DatabaseHelper;
import com.smartmvc.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 提供客户数据服务
 */
@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    /**
     * 获取客户列表
     *
     * @return
     */
    public List<Customer> getCustomerList() {
        String sql = "SELECT * FROM customer";
        return DatabaseHelper.queryEntityList(Customer.class, sql);
    }


    /**
     * 获取客户
     *
     * @param id
     * @return
     */
    @Transaction
    public Customer getCustomer(Integer id) {
        String sql = "SELECT * FROM customer WHERE id=?";
        return DatabaseHelper.queryEntity(Customer.class, sql, id);
    }

    /**
     * 创建客户
     *
     * @param fieldMap
     * @return
     */
    @Transaction
    public boolean createCustomer(Map<String,Object> fieldMap) {
        return DatabaseHelper.insertEntity(Customer.class, fieldMap);
    }

    /**
     *
     * 更新客户
     *
     * @param id
     * @param fieldMap
     * @return
     */
    @Transaction
    public boolean updateCustomer(Long id, Map<String,Object> fieldMap) {
        return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
    }

    /**
     * 删除客户
     *
     * @param id
     * @return
     */
    @Transaction
    public boolean deleteCustomer(long id) {
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }

    public Customer getTestCustomer() {
        Customer customer = new Customer();
        customer.setId(12306L);
        customer.setName("test");
        customer.setEmail("test@smartmvc.com");
        customer.setTelephone("13888888888");
        return customer;
    }


}
