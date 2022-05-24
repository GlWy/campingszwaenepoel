package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import be.camping.campingzwaenepoel.domain.model.OpslagGegeven;
import be.camping.campingzwaenepoel.domain.repository.OpslagGegevenRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OpslagGegevenRepository")
public class OpslagGegevenRepositoryHibernate extends AbstractRepositoryJpaImpl<OpslagGegeven, Integer> implements OpslagGegevenRepository {

    @Override
    public OpslagGegeven store(OpslagGegeven opslagGegeven) {
//        opslagGegeven = createOrUpdate(opslagGegeven, opslagGegeven.getNaam());
        return opslagGegeven;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OpslagGegeven> getOpslagGegevens() {
        return findAll(OpslagGegeven.class);
    }
}