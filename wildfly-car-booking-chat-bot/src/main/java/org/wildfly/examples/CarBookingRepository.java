package org.wildfly.examples;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CarBookingRepository {
    private static final Map<String, Booking> BOOKINGS = new HashMap<>();

    static {
        BOOKINGS.put(
                "123-456",
                new Booking(
                        "123-456",
                        LocalDate.now().plusDays(1),
                        LocalDate.now().plusDays(7),
                        new Customer("James", "Bond"),
                        false,
                        "Aston Martin")); // Not cancelable: too late
        BOOKINGS.put(
                "234-567",
                new Booking(
                        "234-567",
                        LocalDate.now().plusDays(10),
                        LocalDate.now().plusDays(12),
                        new Customer("James", "Bond"),
                        false,
                        "Renault")); // Not cancelable: too short
        BOOKINGS.put(
                "345-678",
                new Booking(
                        "345-678",
                        LocalDate.now().plusDays(14),
                        LocalDate.now().plusDays(20),
                        new Customer("James", "Bond"),
                        false,
                        "Porsche")); // Cancelable
    }

    public Booking findBookingByNumberAndCustomer(String bookingNumber, Customer customer) {
        Booking booking = findBookingByNumber(bookingNumber);
        if (booking == null || !booking.customer().equals(customer)) {
            throw new BookingException("Booking not found for booking number: " + bookingNumber + " and customer: " + customer.name() + " " + customer.surname());
        }
        return booking;
    }

    public List<Booking> findBookingByCustomer(Customer customer) {
        return BOOKINGS.values().stream()
                .filter(booking -> booking.customer().equals(customer))
                .collect(Collectors.toList());
    }

    public Booking findBookingByNumber(String bookingNumber) {
        return BOOKINGS.get(bookingNumber);
    }

    public void saveBooking(Booking booking) {
        BOOKINGS.put(booking.bookingNumber(), booking);
    }
}