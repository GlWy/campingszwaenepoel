/**
 *
 */
package be.camping.campingzwaenepoel.common.exception.enumeration;

/**
 * Class for storing all Discriminators that are possible, which includes the project specific prefixes
 *
 * @author craig.moore@cropdesign.com
 */
public enum BPSExceptionDiscriminator {

	/**
	 * Discriminator code for the BPS Data Exchange project: 'DE'.
	 */
	DATA_EXCHANGE("DE", "BPS DataExchange"),

	/**
	 * Discriminator code for the BPS Entities project: 'E'.
	 */
	ENTITIES("E", "BPS Entities"),

	/**
	 * Discriminator code for the BPS Handshakes project: 'H'.
	 */
	HANDSHAKES("H", "BPS Handshakes"),

	/**
	 * Discriminator code for the LIMS Molecular Analysis project: 'MA'.
	 */
	MOLECULAR_ANALYSIS("MA", "LIMS Molecular Analysis"),

	/**
	 * Discriminator code for the BPS Requests project: 'RQ'.
	 */
	REQUESTS("RQ", "BPS Requests"),

	/**
	 * Discriminator code for the BPS Location project: 'L'.
	 */
	LOCATION("L", "BPS Location"),

	/**
	 * Discriminator code for the LIMS Commons project: 'LC'.
	 */
	LIMSCOMMONS("LC", "Lims Commons"),

	/**
	 * Discriminator code for the BPS Commons project: '' (none).
	 */
	COMMONS("", "BPS Commons");

	/**
	 * The abbreviation of the BPSExceptionDiscriminator.
	 */
	private final String abbreviation;

	/**
	 * The description of the BPSExceptionDiscriminator.
	 */
	private final String description;


	/**
	 * Constructor for the different enum-values.
	 *
	 * @param abbreviation
	 *            The abbreviation of the {@link BPSExceptionDiscriminator}
	 * @param description
	 *            The description of the {@link BPSExceptionDiscriminator}
	 */
	private BPSExceptionDiscriminator(final String abbreviation, final String description) {
		this.abbreviation = abbreviation;
		this.description = description;
	}


	public String getAbbreviation() {
		return abbreviation;
	}


	public String getDescription() {
		return description;
	}

}
