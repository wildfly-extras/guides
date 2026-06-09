package org.wildfly.examples;

import java.time.LocalDate;

/**
 * Represents a car rental booking.
 */
public record Booking(
        String bookingNumber,
        LocalDate start,
        LocalDate end,
        Customer customer,
        boolean canceled,
        String carModel) {
}