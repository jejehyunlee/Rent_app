
/*Product JS*/
$(document).ready(function () {
    $('#productTable').DataTable({
        language: {
            search: "Cari:",
            lengthMenu: "Tampilkan _MENU_ data",
            info: "Menampilkan _START_ sampai _END_ dari _TOTAL_ data",
            paginate: {
                first: "Awal",
                last: "Akhir",
                next: "→",
                previous: "←"
            }
        }
    });
});

/*Customer JS*/
$(document).ready(function () {
    $('#customerTable').DataTable({
        language: {
            search: "Cari:",
            lengthMenu: "Tampilkan _MENU_ data",
            info: "Menampilkan _START_ sampai _END_ dari _TOTAL_ data",
            paginate: {
                first: "Awal",
                last: "Akhir",
                next: "→",
                previous: "←"
            }
        }
    });
});

/*rental JS*/

$(document).ready(function () {
    $('#rentalTable').DataTable({
        language: {
            search: "Cari:",
            lengthMenu: "Tampilkan _MENU_ data",
            info: "Menampilkan _START_ sampai _END_ dari _TOTAL_ data",
            paginate: {
                first: "Awal",
                last: "Akhir",
                next: "→",
                previous: "←"
            }
        }
    });
});

/*payment JS*/

$(document).ready(function () {
    $('#paymentTable').DataTable({
        language: {
            search: "Cari:",
            lengthMenu: "Tampilkan _MENU_ data",
            info: "Menampilkan _START_ sampai _END_ dari _TOTAL_ data",
            paginate: {
                first: "Awal",
                last: "Akhir",
                next: "→",
                previous: "←"
            }
        }
    });
});

/*form rental*/
document.addEventListener("DOMContentLoaded", function () {
    const productSelect = document.querySelector('select[name="product"]');
    const startDateInput = document.querySelector('input[name="startDate"]');
    const endDateInput = document.querySelector('input[name="endDate"]');
    const totalPriceInput = document.querySelector('input[name="totalPrice"]');

    function calculatePrice() {
        const productOption = productSelect.options[productSelect.selectedIndex];
        const pricePerDay = parseFloat(productOption.getAttribute('data-price')) || 0;

        const startDate = new Date(startDateInput.value);
        const endDate = new Date(endDateInput.value);

        if (startDateInput.value && endDateInput.value && !isNaN(pricePerDay)) {
            const diffTime = endDate - startDate;
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) || 1;

            if (diffDays > 0) {
                const total = pricePerDay * diffDays;
                totalPriceInput.value = total.toFixed(2);
            } else {
                totalPriceInput.value = '0.00';
            }
        }
    }

    productSelect.addEventListener('change', calculatePrice);
    startDateInput.addEventListener('input', calculatePrice);
    endDateInput.addEventListener('input', calculatePrice);
});

/*form payment*/
document.addEventListener("DOMContentLoaded", function () {
    const rentalSelect = document.getElementById("rentalSelect");
    const amountField = document.getElementById("amountField");
    const emailCustomerField = document.getElementById("emailCustomer");
    const paymentDateField = document.getElementById("paymentDate");

    function updateFieldsFromSelectedRental() {
        const selectedOption = rentalSelect.options[rentalSelect.selectedIndex];
        const price = selectedOption.getAttribute("data-price");
        const email = selectedOption.getAttribute("data-email");
        const date = selectedOption.getAttribute("data-date");

        if (amountField && price) {
            amountField.value = parseFloat(price).toFixed(2);
        }

        if (emailCustomerField && email) {
            emailCustomerField.value = email;
        }
        if (paymentDateField && date) {
            paymentDateField.value = date;
        }
    }

    rentalSelect.addEventListener("change", updateFieldsFromSelectedRental);

    // Jalankan juga saat halaman dimuat jika ada rental yang sudah dipilih
    if (rentalSelect.value) {
        updateFieldsFromSelectedRental();
    }
});