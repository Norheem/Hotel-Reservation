package service;

import model.Customer;

import java.util.*;

public class CustomerService {

    private static CustomerService customerService = null;

    Collection<Customer> customers = new HashSet<>();

    private CustomerService() {}


        static public CustomerService getInstance() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    /**
     *
     * @param email customer's email
     * @param firstName customer's first name
     * @param lastName customer's last name
     */

    public void addCustomer(String email, String firstName, String lastName) {
        customers.add(new Customer(firstName, lastName, email));
        System.out.println(customers);
    }


    public Customer getCustomer(String customerEmail) {

//        Customer findCustomer = null;
//        for (Customer customer : customers) {
//            if (customer.getEmail().equals(customerEmail))
//                findCustomer =customer;
//        }
//        return findCustomer;
        for (Customer customer : customers) {
            if (customerEmail.equals(customer.getEmail())) {
                System.out.println(customerEmail);
                return customer;
            }
        }
        return null;
    }

    public Collection<Customer> getAllCustomers() {
        if (!customers.isEmpty()) {
            customers.forEach(System.out::println);
        }
        return customers;
    }

}
