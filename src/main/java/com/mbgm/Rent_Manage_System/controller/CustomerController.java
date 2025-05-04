package com.mbgm.Rent_Manage_System.controller;

import com.mbgm.Rent_Manage_System.entity.Customer;
import com.mbgm.Rent_Manage_System.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAll());
        return "layout-customer";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "layout-form-customer";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String editCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerService.getById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerService.updateCustomer(id, customer);
        model.addAttribute("customer", customer);
        return "layout-form-customer";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return "redirect:/customers";
    }
}

