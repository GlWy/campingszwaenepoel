package be.camping.campingzwaenepoel.domain.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@NamedQuery(name = GeschiedenisThema.Queries.FindGeschiedenisThemaByThema.NAME, query = GeschiedenisThema.Queries.FindGeschiedenisThemaByThema.QUERY)
@Entity
@Table(name = "geschiedenis_thema")
public class GeschiedenisThema implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 5280886751399017306L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	@Column(name = "THEMA", nullable = false, length = 45)
	private String thema;

	// Constructors

	/** default constructor */
	public GeschiedenisThema() {
	}

	/** full constructor */
	public GeschiedenisThema(String thema) {
		this.thema = thema;
	}

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getThema() {
		return this.thema;
	}

	public void setThema(String thema) {
		this.thema = thema;
	}

	public interface Queries {

		class FindGeschiedenisThemaByThema {
			public static final String NAME = "GeschiedenisThema.FindGeschiedenisThemaByThema";
			public static final String GESCHIEDENIS_THEMA = "geschiedenisThema";
			static final String QUERY = "select thema from GeschiedenisThema thema where thema.thema = :" + GESCHIEDENIS_THEMA;
		}
	}
}