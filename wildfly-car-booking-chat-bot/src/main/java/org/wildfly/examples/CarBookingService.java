package org.wildfly.examples;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import dev.langchain4j.agent.tool.Tool;

@ApplicationScoped
public class CarBookingService {
    private static final Logger LOGGER = Logger.getLogger(CarBookingService.class.getName());

    @Inject
    CarBookingRepository repository;

    @Tool("Get booking details given a booking number and customer name and surname")
    public Booking getBookingDetails(String bookingNumber, String name, String surname) {
        LOGGER.info("DEMO: Calling Tool-getBookingDetails: " + bookingNumber + " and customer: " + name + " " + surname);
        return repository.findBookingByNumberAndCustomer(bookingNumber, new Customer(name, surname));
    }

    @Tool("Get all booking ids for a customer given his name and surname")
    public List<String> getBookingsForCustomer(String name, String surname) {
        LOGGER.info("DEMO: Calling Tool-getBookingsForCustomer: " + name + " " + surname);
        return repository.findBookingByCustomer(new Customer(name, surname))
                .stream()
                .map(Booking::bookingNumber)
                .collect(Collectors.toList());
    }

    @Tool("Cancel a booking given its booking number and customer name and surname")
    public Booking cancelBooking(String bookingNumber, String name, String surname) {
        LOGGER.info("DEMO: Calling Tool-cancelBooking " + bookingNumber + " for customer: " + name + " " + surname);

        Booking booking = repository.findBookingByNumberAndCustomer(bookingNumber, new Customer(name, surname));

        if (booking.canceled()) throw new BookingException("Booking with number " + bookingNumber + " is already canceled.");

        checkCancelPolicy(booking);

        Booking canceledBooking = new Booking(booking.bookingNumber(), booking.start(), booking.end(), booking.customer(), true, booking.carModel());
        repository.saveBooking(canceledBooking);

        return canceledBooking;
    }

    public void checkCancelPolicy(Booking booking) {
        // Reservations can be canceled up to 7 days prior to the start of the booking period
        if (LocalDate.now().plusDays(7).isAfter(booking.start())) {
            throw new BookingException("Booking with number " + booking.bookingNumber() + " cannot be canceled less than 7 days before the start of the booking period.");
        }
        // If the booking period is less than 3 days, cancellations are not permitted.
        if (booking.end().isBefore(booking.start().plusDays(3))) {
            throw new BookingException("Booking with number " + booking.bookingNumber() + " cannot be canceled because the booking period is less than 3 days.");
        }
    }
}