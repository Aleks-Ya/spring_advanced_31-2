package booking.beans.daos.db;

import booking.beans.daos.AbstractDAO;
import booking.beans.daos.AuditoriumDAO;
import booking.beans.models.Auditorium;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro_Babichev
 * Date: 20/2/16
 * Time: 4:35 PM
 */
@Repository(value = "auditoriumDAO")
public class AuditoriumDAOImpl extends AbstractDAO implements AuditoriumDAO {

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
    public Auditorium add(Auditorium auditorium) {
        Long id = (Long) getCurrentSession().save(auditorium);
        return auditorium.withId(id);
    }

    @Override
    public void delete(Auditorium auditorium) {
        getCurrentSession().delete(auditorium);
    }
}
