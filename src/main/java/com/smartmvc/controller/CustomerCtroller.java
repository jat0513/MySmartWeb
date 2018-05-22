package com.smartmvc.controller;

import com.smartmvc.annotation.Action;
import com.smartmvc.annotation.Controller;
import com.smartmvc.annotation.Inject;
import com.smartmvc.bean.Data;
import com.smartmvc.bean.Param;
import com.smartmvc.bean.View;
import com.smartmvc.model.Customer;
import com.smartmvc.service.CustomerService;

@Controller
public class CustomerCtroller {

    @Inject
    private CustomerService customerService;

    @Action(value = "get:/getCustomer")
    public View getCustomer(Param param) {
        Customer customer = customerService.getCustomer(param.getInt("id"));
        View view = new View("customer.jsp");
        view.addModel("customer", customer);
        return view;
    }

    @Action(value = "get:/customer")
    public View index(Param param) {
        return new View("index.jsp");
    }

    @Action(value = "get:/getCustomerJson")
    public Data getCustomerJson(Param param) {
        Customer customer = customerService.getCustomer(param.getInt("id"));
        return new Data(customer);
    }



}
