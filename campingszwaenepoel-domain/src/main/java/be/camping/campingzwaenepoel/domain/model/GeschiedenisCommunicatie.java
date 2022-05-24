package be.camping.campingzwaenepoel.domain.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;

@Entity
@Table(name = "geschiedenis_communicatie")
@NamedQueries({
		@NamedQuery(name = GeschiedenisCommunicatie.Queries.FindAllGeschiedenisCommunicaties.NAME,
				query = GeschiedenisCommunicatie.Queries.FindAllGeschiedenisCommunicaties.QUERY),
		@NamedQuery(name = GeschiedenisCommunicatie.Queries.FindGeschiedenisCommunicatieByCommunicatie.NAME,
				query = GeschiedenisCommunicatie.Queries.FindGeschiedenisCommunicatieByCommunicatie.QUERY)
})
public class GeschiedenisCommunicatie {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	@Column(name = "COMMUNICATIE", nullable = false, length = 45)
	private String communicatie;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCommunicatie() {
		return communicatie;
	}

	public void setCommunicatie(String communicatie) {
		this.communicatie = communicatie;
	}

	public interface Queries {

		class FindAllGeschiedenisCommunicaties {
			public static final String NAME = "GeschiedenisCommunicatie.FindAllGeschiedenisCommunicaties";
			static final String QUERY = "select gc from GeschiedenisCommunicatie gc order by gc.communicatie";
		}

		class FindGeschiedenisCommunicatieByCommunicatie {
			public static final String NAME = "GeschiedenisCommunicatie.FindGeschiedenisCommunicatieByCommunicatie";
			public static final String PARAMETER_COMMUNICATION = "communication";
			static final String QUERY = "select gc from GeschiedenisCommunicatie gc where gc.communicatie = :" + PARAMETER_COMMUNICATION;
		}
	}
}
