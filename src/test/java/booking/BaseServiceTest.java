package booking;

import booking.repository.RepositoryConfig;
import booking.service.AccountService;
import booking.service.AuditoriumService;
import booking.service.BookingService;
import booking.service.EventService;
import booking.service.ServiceConfig;
import booking.service.TestObjects;
import booking.service.TicketService;
import booking.service.UserService;
import booking.web.security.PasswordEncoderConfig;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

/**
 * Parent class for unit tests that use repository and service layers.
 * <p>
 * After each test all data from the datasource is removed.
 * In the beginning of any test fill the datasource (e.g. using {@link TestObjects}).
 */
@ContextConfiguration(classes = {RepositoryConfig.class, ServiceConfig.class, TestObjects.class,
        PasswordEncoderConfig.class})
@TestPropertySource(value = "classpath:db-test.properties", properties = "lucky.enabled=false")
public abstract class BaseServiceTest extends BaseTest {
    @Autowired
    protected TestObjects to;

    @Autowired
    protected TicketService ticketService;

    @Autowired
    protected BookingService bookingService;

    @Autowired
    protected AuditoriumService auditoriumService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected EventService eventService;

    @Autowired
    protected AccountService accountService;

    @After
    public void clean() {
        to.cleanup();
    }
}