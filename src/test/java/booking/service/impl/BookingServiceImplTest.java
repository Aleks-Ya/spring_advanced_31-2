package booking.service.impl;

import booking.BaseServiceTest;
import booking.domain.Booking;
import booking.domain.Event;
import booking.domain.Ticket;
import booking.domain.User;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro_Babichev
 * Date: 06/2/16
 * Time: 8:28 PM
 */
@Ignore("fix it") //TODO
public class BookingServiceImplTest extends BaseServiceTest {

    @Test(expected = RuntimeException.class)
    public void testBookTicket_NotRegistered() {
        Ticket ticket = testObjects.createTicketToParty();
        User user = testObjects.createJohn();
        Booking expBooking = bookingService.create(user.getId(), ticket);
        assertThat(bookingService.getById(expBooking.getId()), equalTo(expBooking));
    }

    @Test(expected = RuntimeException.class)
    public void testBookTicket_AlreadyBooked() {
        Ticket newTicket = testObjects.createTicketToParty();
        User user = newTicket.getUser();
        bookingService.create(user.getId(), newTicket);
        bookingService.create(user.getId(), newTicket);
    }

    @Test
    public void getAll() {
        Ticket newTicket = testObjects.createTicketToHackathon();
        User user = newTicket.getUser();
        Booking booking = bookingService.create(user.getId(), newTicket);

        assertThat(bookingService.getAll(), containsInAnyOrder(booking));
    }

    @Test
    public void testGetTicketPrice_DiscountsForTicketsAndForBirthday() {
        Ticket ticket = testObjects.createTicketToParty();
        Ticket ticket2 = testObjects.createTicketToHackathon();
        User testUser = ticket.getUser();
        User registeredUser = testObjects.createJohn();
        bookingService.create(registeredUser.getId(), ticket);
        bookingService.create(ticket2.getUser().getId(), ticket2);
        Event event = ticket.getEvent();
        double ticketPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(),
                event.getDateTime(), Arrays.asList(5, 6, 7, 8),
                registeredUser);
        assertEquals("Price is wrong", 260.4, ticketPrice, 0.00001);
    }

    @Test
    public void testGetTicketPrice_DiscountsForTicketsAndForBirthday_MidRate() {
        Ticket ticket = testObjects.createTicketToParty();
        User testUser = ticket.getUser();
        bookingService.create(testUser.getId(), ticket);
        bookingService.create(testUser.getId(), ticket);
        Event event = ticket.getEvent();
        double ticketPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(),
                event.getDateTime(), Arrays.asList(5, 6, 7), testUser);
        assertEquals("Price is wrong", 525, ticketPrice, 0.00001);
    }

    @Test
    public void testGetTicketPrice() {
        Ticket ticket = testObjects.createTicketToParty();
        Event event = ticket.getEvent();
        double ticketPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(),
                event.getDateTime(), ticket.getSeatsList(),
                ticket.getUser());
        assertEquals("Price is wrong", 297.6, ticketPrice, 0.00001);
    }

    @Test
    public void testGetTicketPrice_WithoutDiscount() {
        Ticket ticket = testObjects.createTicketToParty();
        User user = ticket.getUser();
        Event event = ticket.getEvent();
        double ticketPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(),
                event.getDateTime(), ticket.getSeatsList(), user);
        assertEquals("Price is wrong", 595.2, ticketPrice, 0.00001);
    }
}
