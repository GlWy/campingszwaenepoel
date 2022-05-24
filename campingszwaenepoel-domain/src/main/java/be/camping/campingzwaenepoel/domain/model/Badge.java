package be.camping.campingzwaenepoel.domain.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;

@Entity
@Table(name = "badge", uniqueConstraints = @UniqueConstraint(columnNames = "BADGENUMMER"))
@NamedQuery(name = Badge.Queries.FindBadgeByNummer.NAME, query = Badge.Queries.FindBadgeByNummer.QUERY)
public class Badge implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	@Column(name = "BADGENUMMER", unique = true, nullable = false, length = 8)
	private String badgenummer;
	@Enumerated(EnumType.STRING)
	@Column(name = "BADGETYPE", nullable = false, length = 4)
	private SoortHuurderEnum badgetype;

	/** default constructor */
	public Badge() {
	}

	/** full constructor */
	public Badge(String badgenummer, SoortHuurderEnum badgetype) {
		this.badgenummer = badgenummer;
		this.badgetype = badgetype;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBadgenummer() {
		return this.badgenummer;
	}

	public void setBadgenummer(String badgenummer) {
		this.badgenummer = badgenummer;
	}

	public SoortHuurderEnum getBadgetype() {
		return this.badgetype;
	}

	public void setBadgetype(SoortHuurderEnum badgetype) {
		this.badgetype = badgetype;
	}

	public interface Queries {

		class FindBadgeByNummer {
            public static final String NAME = "Badge.FindBadgeByNummer";
            public static final String BADGE_NUMMER = "badgenummer";
            static final String QUERY = "select b from Badge b where b.badgenummer = :" + BADGE_NUMMER;
        }
	}
}