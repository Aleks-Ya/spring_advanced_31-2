package booking.service;

import booking.service.aspects.AspectConfig;
import booking.service.impl.*;
import booking.service.impl.discount.DiscountConfig;
import org.springframework.context.annotation.Import;

@Import({DiscountConfig.class, AspectConfig.class, TicketServiceImpl.class, AccountServiceImpl.class,
        AuditoriumServiceImpl.class, BookingServiceImpl.class, EventServiceImpl.class, UserServiceImpl.class})
public class ServiceConfig {
}
