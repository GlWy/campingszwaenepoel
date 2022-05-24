package be.camping.campingzwaenepoel.common.exception.enumeration;

/**
 * Enum that holds the different layers where exceptions can be raised in a bps project.
 * 
 * @author MeyersB
 */
public enum BPSExceptionLayer {
	/**
	 * PresentationFatClientLayer.
	 */
	PRESENTATION_FAT_CLIENT_LAYER("PFC", "PresentationFatClientLayer"),
	/**
	 * PresentationThinClientLayer.
	 */
	PRESENTATION_THIN_CLIENT_LAYER("PTC", "PresentationThinClientLayer"),
	/**
	 * WebServicesLayer.
	 */
	WEB_SERVICES_LAYER("WS", "WebServicesLayer"),
	/**
	 * ServicesLayer.
	 */
	SERVICES_LAYER("S", "ServicesLayer"),
	/**
	 * IntegrationRepositoryLayer.
	 */
	INTEGRATION_REPOSITORY_LAYER("IR", "IntegrationRepositoryLayer"),
	/**
	 * IntegrationMessagingLayer.
	 */
	INTEGRATION_MESSAGING_LAYER("IM", "IntegrationMessagingLayer"),
	/**
	 * IntegrationFileSystemLayer.
	 */
	INTEGRATION_FILE_SYSTEM_LAYER("IFS", "IntegrationFileSystemLayer"),
	/**
	 * IntegrationEnterpriseServiceBusLayer.
	 */
	INTEGRATION_ENTERPRISE_SERVICE_BUS_LAYER("IESB", "IntegrationEnterpriseServiceBusLayer"),

	/**
	 * DomainLayer
	 */
	DOMAIN_LAYER("D", "DomainLayer");

	/**
	 * a String with the abbreviation of the BPSExceptionLayer.
	 */
	private String abbreviation = null;

	/**
	 * a String with the full description of the BPSExceptionLayer.
	 */
	private String description = null;


	/**
	 * Constructor for the different enum-values.
	 * 
	 * @param abbreviation
	 *            a String with the abbreviation of the BPSExceptionLayer
	 * @param description
	 *            a String with the description of the BPSExceptionLayer(written out)
	 */
	private BPSExceptionLayer(final String abbreviation, final String description) {
		this.abbreviation = abbreviation;
		this.description = description;
	}


	/**
	 * @return the abbreviation
	 */
	public String getAbbreviation() {
		return abbreviation;
	}


	/**
	 * @param abbreviation
	 *            the abbreviation to set
	 */
	public void setAbbreviation(final String abbreviation) {
		this.abbreviation = abbreviation;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

}
