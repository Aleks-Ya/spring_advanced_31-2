package booking.repository.config;

import booking.repository.BookingDao;
import booking.repository.TicketDao;
import booking.service.DiscountService;
import booking.service.impl.discount.BirthdayStrategy;
import booking.service.impl.discount.DiscountServiceImpl;
import booking.service.impl.discount.TicketsStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro_Babichev
 * Date: 13/2/16
 * Time: 3:36 PM
 */
@Configuration
@Deprecated
public class TestStrategiesConfig {
    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private BookingDao bookingDao;

    @Bean
    public BirthdayStrategy birthdayStrategy() {
        return new BirthdayStrategy(1.0, 0);
    }

    @Bean
    public TicketsStrategy ticketsStrategy() {
        return new TicketsStrategy(ticketDao, 0.5, 2, 0, bookingDao);
    }

//    @Bean
//    public TicketDao bookingDiscountDAO() {
//        throw new UnsupportedOperationException();
////        return new TicketDaoDiscountMock("Test User", 1);
//    }

    @Bean
    public DiscountService discountServiceImpl() {
        return new DiscountServiceImpl(Arrays.asList(birthdayStrategy(), ticketsStrategy()));
    }
}
