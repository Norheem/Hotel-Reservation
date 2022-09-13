package model;

import java.util.Objects;
import java.util.regex.Pattern;

import static api.HotelResource.customerService;

public class Customer {

    private final String firstName;

    private final String lastName;

    private final String email;

    String emailRegEx = "^(.+)@(.+).(.+)$";
    Pattern pattern = Pattern.compile(emailRegEx);

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Customer(String firstName, String lastName, String email) {
        super();
        for (Customer customer : customerService.getAllCustomers()) {
            if (customer.getEmail().equals(email)) {
                throw new IllegalArgumentException("Your email have been used before by another customer, kindly " +
                        "enter another email");
            }
        }
        this.firstName = firstName;
        this.lastName = lastName;
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email entered, kindly enter a valid email");
        }
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email) && Objects.equals(emailRegEx, customer.emailRegEx) && Objects.equals(pattern, customer.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, emailRegEx, pattern);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
