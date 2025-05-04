package com.mbgm.Rent_Manage_System.controller;

import com.mbgm.Rent_Manage_System.entity.Payment;
import com.mbgm.Rent_Manage_System.entity.Rental;
import com.mbgm.Rent_Manage_System.service.EmailService;
import com.mbgm.Rent_Manage_System.service.PaymentService;
import com.mbgm.Rent_Manage_System.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    private final EmailService emailService;

    private final RentalService rentalService;

    @GetMapping
    public String listPayments(Model model) {
        model.addAttribute("payments", paymentService.getAll());
        return "layout-payment";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("payment", new Payment());
        model.addAttribute("rentals", rentalService.getAll());
        return "layout-form-payment";
    }

    @PostMapping("/save")
    public String createPayment(@ModelAttribute Payment payment) {
        // Ambil rental
        Optional<Rental> rentalOpt = rentalService.getById(payment.getRental().getId());
        if (rentalOpt.isEmpty()) {
            return "redirect:/payments?error=Rental tidak ditemukan";
        }

        payment.setRental(rentalOpt.get());
        payment.setPaymentDate(LocalDate.now());
        payment.setStatus("UNPAID");
        paymentService.save(payment);

        // Kirim email
        String subject = "Invoice Pembayaran";
        String content = String.format("""
        Tagihan anda sebesar Rp %, .2f.
        Tanggal tagihan: %s
        Produk: %s
        Pelanggan: %s
    
        Untuk menyelesaikan pembayaran, silakan klik link di bawah untuk melanjutkan pembayaran via BCA.
    
        Link Pembayaran: http://localhost:8080/payments/paymentForm/%d
    
        Instruksi Pembayaran:
        - Pilih metode pembayaran 'Transfer BCA' di form.
        - Ikuti petunjuk untuk melakukan pembayaran melalui BCA.
    
        Terima kasih atas transaksi Anda.
        """,
                payment.getAmount(),                                    // Rp %, .2f untuk format currency
                payment.getPaymentDate(),                               // %s untuk Date
                payment.getRental().getProduct().getName(),             // %s untuk String (produk)
                payment.getRental().getCustomer().getName(),            // %s untuk String (pelanggan)
                payment.getRental().getId());                           // %d untuk ID rental (int)

        emailService.sendInvoiceEmail(payment.getRental().getCustomer().getEmail(), subject, content);

        return "redirect:/payments?success=Pembayaran berhasil";

    }

    @GetMapping("/paymentForm/{id}")
    public String showPaymentForm(@PathVariable("id") Long rentalId, Model model) {
        Optional<Rental> rentalOpt = rentalService.getById(rentalId);
        if (rentalOpt.isEmpty()) {
            return "redirect:/rentals?error=Rental tidak ditemukan";
        }

        Rental rental = rentalOpt.get();

        Payment payment = new Payment();
        payment.setRental(rental);
        payment.setAmount(rental.getTotalPrice());
        payment.setPaymentDate(LocalDate.now());

        model.addAttribute("payment", payment);
        return "form-payment-user";
    }

    @PostMapping("/paymentUser/save")
    public String updatePayment(
            @RequestParam Long rentalId,
            @RequestParam BigDecimal amount,
            @RequestParam String customerEmail,
            @RequestParam String paymentDate,
            Model model) {

        Optional<Payment> paymentOpt = paymentService.getByRentalId(rentalId);

        if (paymentOpt.isEmpty()) {
            return "redirect:/payments?error=Tagihan tidak ditemukan";
        }

        Payment payment = paymentOpt.get();
        payment.setAmount(amount); // Bisa dikunci kalau tidak boleh berubah
        payment.setPaymentDate(LocalDate.parse(paymentDate));
        payment.setStatus("PAID");

        paymentService.save(payment); // lakukan update, bukan insert baru

        // Kirim email konfirmasi
        String subject = "Konfirmasi Pembayaran";
        String content = String.format("""
        Terima kasih telah melakukan pembayaran sebesar Rp %, .2f.
        Tanggal: %s
    
        Pembayaran Anda sudah kami terima.
        """,
                payment.getAmount(), payment.getPaymentDate());

        emailService.sendInvoiceEmail(customerEmail, subject, content);
        model.addAttribute("payment", payment);
        return "success";
    }


    @GetMapping("/edit/{id}")
    public String editPayment(@PathVariable Long id, Model model) {
        Payment payment = paymentService.getById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = payment.getPaymentDate().format(formatter);  // pastikan getPaymentDate() mengembalikan LocalDate
        model.addAttribute("payment", payment);
        model.addAttribute("formattedPaymentDate", formattedDate); // mengirimkan formattedDate
        model.addAttribute("rentals", rentalService.getAll());
        return "layout-form-payment";
    }

    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id) {
        paymentService.delete(id);
        return "redirect:/payments";
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return ResponseEntity.ok(payment);
    }
}

