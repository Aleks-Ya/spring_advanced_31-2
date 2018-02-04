package booking.repository.impl;

import booking.domain.Auditorium;
import booking.repository.AuditoriumDao;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuditoriumDaoImpl extends AbstractDao implements AuditoriumDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<Auditorium> getAll() {
        return ((List<Auditorium>) createBlankCriteria(Auditorium.class).list());
    }

    @Override
    public Auditorium getByName(String auditoriumName) {
        return ((Auditorium) createBlankCriteria(Auditorium.class).add(Restrictions.eq("name", auditoriumName)).uniqueResult());
    }

    @Override
    public Optional<Auditorium> getById(Long auditoriumId) {
        return Optional.ofNullable((Auditorium) createBlankCriteria(Auditorium.class).add(Restrictions.eq("id", auditoriumId)).uniqueResult());
    }

    @Override
    public void delete(Long auditoriumId) {
        Auditorium auditorium = getCurrentSession().get(Auditorium.class, auditoriumId);
        getCurrentSession().delete(auditorium);
    }

    @Override
    public Auditorium add(Auditorium auditorium) {
        Long id = (Long) getCurrentSession().save(auditorium);
        return auditorium.withId(id);
    }
}
