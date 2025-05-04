package com.mbgm.Rent_Manage_System.controller;

import com.mbgm.Rent_Manage_System.entity.Rental;
import com.mbgm.Rent_Manage_System.service.CustomerService;
import com.mbgm.Rent_Manage_System.service.ProductService;
import com.mbgm.Rent_Manage_System.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rentals")
public class RentalController {


    private final RentalService rentalService;

    private final ProductService productService;

    private final CustomerService customerService;

    @GetMapping
    public String listRentals(Model model) {
        model.addAttribute("rentals", rentalService.getAll());
        return "layout-rental";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("rental", new Rental());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("customers", customerService.getAll());
        return "layout-form-rental";
    }

    @PostMapping("/save")
    public String saveRental(@ModelAttribute Rental rental) {
        rentalService.save(rental);
        return "redirect:/rentals";
    }

    @GetMapping("/edit/{id}")
    public String editRental(@PathVariable Long id, Model model) {
        Rental rental = rentalService.getById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
        model.addAttribute("rental", rental);
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("customers", customerService.getAll());
        return "layout-form-rental";
    }

    @GetMapping("/delete/{id}")
    public String deleteRental(@PathVariable Long id) {
        rentalService.delete(id);
        return "redirect:/rentals";
    }
}

