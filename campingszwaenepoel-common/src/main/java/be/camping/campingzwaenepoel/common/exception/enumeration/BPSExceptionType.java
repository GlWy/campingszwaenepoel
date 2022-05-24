package be.camping.campingzwaenepoel.common.exception.enumeration;

/**
 * Enum that holds the different kinds of exceptions we define in the different layers of a BPS-project.
 *
 * @author MeyersB
 */
public enum BPSExceptionType {
	/**
	 * The exception type for business exceptions: 'B'.
	 */
	BUSINESS_EXCEPTION_TYPE("B", "BusinessExceptionType"),
	/**
	 * The exception type for system exceptions: 'S'.
	 */
	SYSTEM_EXCEPTION_TYPE("S", "SystemExceptionType"),
	/**
	 * The exception type for validation exceptions: 'V'.
	 */
	VALIDATION_EXCEPTION_TYPE("V", "ValidationExceptionType"),
	/**
	 * The exception type for coding exceptions: 'C'.
	 */
	CODING_EXCEPTION_TYPE("C", "CodingExceptionType");

	/**
	 * a String with the abbreviation of the BPSExceptionType.
	 */
	private String abbreviation = null;

	/**
	 * a String with the full description of the BPSExceptionType.
	 */
	private String description = null;


	/**
	 * Constructor for the different enum-values.
	 *
	 * @param abbreviation
	 *            a String with the abbreviation of the BPSExceptionType(mostly a single character)
	 * @param description
	 *            a String with the description of the BPSExceptionType(written out)
	 */
	private BPSExceptionType(final String abbreviation, final String description) {
		this.abbreviation = abbreviation;
		this.description = description;
	}


	/**
	 * @return a String with the abbreviation.
	 */
	public String getAbbreviation() {
		return abbreviation;
	}


	/**
	 * @param abbreviation
	 *            a String with the abbreviation.
	 */
	public void setAbbreviation(final String abbreviation) {
		this.abbreviation = abbreviation;
	}


	/**
	 * @return a String with the description.
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description
	 *            a String with the description.
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

}
