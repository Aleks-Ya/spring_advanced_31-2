package booking.service.impl;

import booking.domain.Ticket;
import booking.repository.TicketDao;
import booking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource({"classpath:strategies/booking.properties"})
public class TicketServiceImpl implements TicketService {

    private final TicketDao ticketDao;

    @Autowired
    public TicketServiceImpl(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public Ticket create(Ticket ticket) {
        return ticketDao.create(ticket);
    }

    @Override
    public List<Ticket> getAll() {
        return ticketDao.getAll();
    }

    @Override
    public Ticket getTicketById(Long ticketId) {
        return ticketDao.getTicketById(ticketId).orElse(null);
    }

    @Override
    public void delete(long ticketId) {
        ticketDao.delete(ticketId);
    }
}
