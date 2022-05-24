package be.camping.campingzwaenepoel.common.exception;

import be.camping.campingzwaenepoel.common.exception.enumeration.BPSExceptionDiscriminator;
import be.camping.campingzwaenepoel.common.exception.enumeration.BPSExceptionLayer;
import be.camping.campingzwaenepoel.common.exception.enumeration.BPSExceptionType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static be.camping.campingzwaenepoel.common.exception.enumeration.BPSExceptionDiscriminator.COMMONS;

/**
 * Base class for the exception handling mechanism to use in future projects in Spring-enabled BPS-projects. This class
 * will hold the cause as the exception that was firstly raised(and catched). It will contain a Also remark that there
 * are no setters defined for the different members of the exception.
 * 
 * @author MeyersB
 */
@Component
public final class BPSException extends RuntimeException implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(BPSException.class);

	private static final long serialVersionUID = 4453364545985234801L;

	/**
	 * The optional discriminator to which the exception can be related. Detail : solely "Type & Layer & Number" can
	 * overlap for multiple projects. This extra discriminator gives you the possibility to provide an extra
	 * 'discrimination'. Example of mapping to key : B_S_0001 (no discriminator) --> MA_B_S_0001, QC_B_S_0001
	 */
	private final BPSExceptionDiscriminator bpsExceptionDiscriminator;

	/**
	 * The layer where the exeption was raised.
	 */
	private final BPSExceptionLayer bpsExceptionLayer;

	/**
	 * The type of exception that was raised.
	 */
	private final BPSExceptionType bpsExceptionType;

	/**
	 * The full stack trace for the cause of this exception.
	 */
	private final String fullStackTrace;

	/**
	 * The id of the exception - in combination of the exceptionType and exceptionLayer, this should be an unique
	 * combination.
	 */
	private final String id;

	/**
	 * The parameters the application can provide in order to get the correct error-message.
	 */
	private Object[] parameters = null;

	/**
	 * The DateTime when this exception was created.
	 */
	private final DateTime timeStamp;

	/**
	 * The translation-key. Used to lookup the translation in the resource-bundle(or database).
	 */
	private final String translationKey;

	/**
	 * The identifier of the user that was interacting with the application when the exception popped up.
	 */
	private String userIdentifier = null;


	/**
	 * This should never be used and is strictly for spring to have a default constructor to use
	 */
	@SuppressWarnings("unused")
	private BPSException() { // set final fields to null...
		this.timeStamp = null;
		this.bpsExceptionLayer = null;
		this.bpsExceptionType = null;
		this.bpsExceptionDiscriminator = null;
		this.id = null;
		this.translationKey = null;
		this.fullStackTrace = null;
	}


	/**
	 * The main constructor for {@link BPSException}s, contains the parameters for all fields in the {@link BPSException}
	 * 
	 * @param bpsExceptionDiscriminator
	 *          The {@link BPSExceptionDiscriminator discriminator} that is used when generating the translation key
	 * @param bpsExceptionType
	 *          The {@link BPSExceptionType type} of the exception, which is used when generating the translation key
	 * @param bpsExceptionLayer
	 *          the {@link BPSExceptionLayer layer} the exception is from, which is used when generating the translation
	 *          key
	 * @param id
	 *          the identifier for the exception message in the resource bundle. The id must be a string that is 4 numeric
	 *          digits.
	 * @param userIdentifier
	 *          the identifier of the user the exception was created for. Normally this the guid or id of the user, but if
	 *          that is not available then the simple class name where the exception was thrown is also acceptable.
	 * @param rootCause
	 *          The {@link Throwable cause} for why the exception was created, this should be the root cause of the
	 *          exception.
	 * @param fullStackTrace
	 *          The full stack trace that the cause is the root of, if null then the stack trace from the cause will be
	 *          used.
	 * @param parameters
	 *          The parameters that will be used to insert arguments into the exception message from the resource bundle.
	 */
	// TODO remove public scope (make package private so exceptions can only be created using BPSExceptionFactory)
	public BPSException(final BPSExceptionDiscriminator bpsExceptionDiscriminator,
                        final BPSExceptionType bpsExceptionType, final BPSExceptionLayer bpsExceptionLayer, final String id,
                        final String userIdentifier, final String fullStackTrace, final Throwable rootCause, Object... parameters) {
		this.timeStamp = new DateTime();
		this.bpsExceptionDiscriminator = bpsExceptionDiscriminator == null ? COMMONS : bpsExceptionDiscriminator;
		this.bpsExceptionLayer = bpsExceptionLayer;
		this.bpsExceptionType = bpsExceptionType;
		this.userIdentifier = userIdentifier;
		this.id = id;
		initCause(rootCause);
		this.fullStackTrace = fullStackTrace;
		this.parameters = parameters;
		this.translationKey = this.generateTranslationKey();
		this.validateException();
	}


	/**
	 * Same as calling
	 * {@link #BPSException (BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, String, Object...)}
	 * BPSException(bpsExceptionDiscriminator, bpsExceptionType, bpsExceptionLayer, id, userIdentifier, cause, null,
	 * parameters)}
	 * 
	 * @param bpsExceptionDiscriminator
	 *          The {@link BPSExceptionDiscriminator discriminator} of the exception, which is used when generating the
	 *          translation key
	 * @param bpsExceptionType
	 *          The {@link BPSExceptionType type} of the exception, which is used when generating the translation key
	 * @param bpsExceptionLayer
	 *          the {@link BPSExceptionLayer layer} the exception is from, which is used when generating the translation
	 *          key
	 * @param id
	 *          the identifier for the exception message in the resource bundle. The id must be a string that is 4 numeric
	 *          digits.
	 * @param userIdentifier
	 *          the identifier of the user the exception was created for. Normally this the guid or id of the user, but if
	 *          that is not available then the simple class name where the exception was thrown is also acceptable.
	 * @param rootCause
	 *          The {@link Throwable cause} for why the exception was created, this should be the root cause of the
	 *          exception.
	 * @param parameters
	 *          The parameters that will be used to insert arguments into the exception message from the resource bundle.
	 */
	public BPSException(final BPSExceptionDiscriminator bpsExceptionDiscriminator,
                        final BPSExceptionType bpsExceptionType, final BPSExceptionLayer bpsExceptionLayer, final String id,
                        final String userIdentifier, final Throwable rootCause, final Object... parameters) {
		this(bpsExceptionDiscriminator, bpsExceptionType, bpsExceptionLayer, id, userIdentifier, null, rootCause,
				parameters);
	}


	/**
	 * Same as calling
	 * {@link #BPSException{(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, String, Object...)}
	 * BPSException(null, bpsExceptionType, bpsExceptionLayer, id, userIdentifier, rootCause, fullStackTrace, parameters)}
	 * 
	 * @param bpsExceptionType
	 *          The {@link BPSExceptionType type} of the exception, which is used when generating the translation key
	 * @param bpsExceptionLayer
	 *          the {@link BPSExceptionLayer layer} the exception is from, which is used when generating the translation
	 *          key
	 * @param id
	 *          the identifier for the exception message in the resource bundle. The id must be a string that is 4 numeric
	 *          digits.
	 * @param userIdentifier
	 *          the identifier of the user the exception was created for. Normally this the guid or id of the user, but if
	 *          that is not available then the simple class name where the exception was thrown is also acceptable.
	 * @param rootCause
	 *          The {@link Throwable cause} for why the exception was created, this should be the root cause of the
	 *          exception.
	 * @param fullStackTrace
	 *          The full stack trace that the cause is the root of, if null then the stack trace from the cause will be
	 *          used.
	 * @param parameters
	 *          The parameters that will be used to insert arguments into the exception message from the resource bundle.
	 */
	public BPSException(final BPSExceptionType bpsExceptionType, final BPSExceptionLayer bpsExceptionLayer,
                        final String id, final String userIdentifier, final String fullStackTrace, final Throwable rootCause,
                        final Object... parameters) {
		this(COMMONS, bpsExceptionType, bpsExceptionLayer, id, userIdentifier, fullStackTrace, rootCause, parameters);
	}


	/**
	 * 
	 * Same as calling
	 * {@link #BPSException{(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, String, Object...)}
	 * BPSException(null, bpsExceptionType, bpsExceptionLayer, id, userIdentifier, rootCause, null, parameters)}
	 * 
	 * @param bpsExceptionType
	 *          The {@link BPSExceptionType type} of the exception, which is used when generating the translation key
	 * @param bpsExceptionLayer
	 *          the {@link BPSExceptionLayer layer} the exception is from, which is used when generating the translation
	 *          key
	 * @param id
	 *          the identifier for the exception message in the resource bundle. The id must be a string that is 4 numeric
	 *          digits.
	 * @param userIdentifier
	 *          the identifier of the user the exception was created for. Normally this the guid or id of the user, but if
	 *          that is not available then the simple class name where the exception was thrown is also acceptable.
	 * @param rootCause
	 *          The {@link Throwable cause} for why the exception was created, this should be the root cause of the
	 *          exception.
	 * @param parameters
	 *          The parameters that will be used to insert arguments into the exception message from the resource bundle.
	 */
	public BPSException(final BPSExceptionType bpsExceptionType, final BPSExceptionLayer bpsExceptionLayer,
                        final String id, final String userIdentifier, final Throwable rootCause, final Object... parameters) {
		this(COMMONS, bpsExceptionType, bpsExceptionLayer, id, userIdentifier, null, rootCause, parameters);
	}


	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		BPSException exception = (BPSException) obj;
		return new EqualsBuilder().append(this.id, exception.getId()).append(this.timeStamp, exception.getTimeStamp())
				.append(this.translationKey, exception.getTranslationKey())
				.append(this.userIdentifier, exception.getUserIdentifier())
				.append(this.bpsExceptionLayer, exception.getBpsExceptionLayer())
				.append(this.bpsExceptionType, exception.getBpsExceptionType())
				.append(this.bpsExceptionDiscriminator, exception.getBpsExceptionDiscriminator()).isEquals();
	}


	/**
	 * Method that will generate a key, so the exception can be translated into a useful name for the end-user and in such
	 * a way the user can easily get it out of the log-files.
	 */
	private String generateTranslationKey() {
		StringBuilder translationKeyBuilder = new StringBuilder();

		if (this.bpsExceptionDiscriminator != null
				&& !StringUtils.isBlank(this.bpsExceptionDiscriminator.getAbbreviation())) {
			translationKeyBuilder.append(this.bpsExceptionDiscriminator.getAbbreviation());
			translationKeyBuilder.append("_");
		}
		translationKeyBuilder.append(this.bpsExceptionType.getAbbreviation());
		translationKeyBuilder.append("_");
		translationKeyBuilder.append(this.bpsExceptionLayer.getAbbreviation());
		translationKeyBuilder.append("_");
		translationKeyBuilder.append(this.id);
		return translationKeyBuilder.toString();
	}


	/**
	 * @return The optional discriminator to which the exception can be related. Detail : solely "Type & Layer & Number"
	 *         can overlap for multiple projects. This extra discriminator gives you the possibility to provide an extra
	 *         'discrimination'. Example of mapping to key : B_S_0001 (no discriminator) --> MA_B_S_0001, QC_B_S_0001
	 */
	public BPSExceptionDiscriminator getBpsExceptionDiscriminator() {
		return bpsExceptionDiscriminator;
	}


	/**
	 * @return the bpsExceptionLayer.
	 */
	public BPSExceptionLayer getBpsExceptionLayer() {
		return bpsExceptionLayer;
	}


	/**
	 * @return the bpsExceptionType.
	 */
	public BPSExceptionType getBpsExceptionType() {
		return bpsExceptionType;
	}


	/**
	 * @return The full stack trace for the cause of this exception.
	 */
	public String getFullStackTrace() {
		return fullStackTrace;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @return the parameters
	 */
	public Object[] getParameters() {
		return parameters;
	}


	/**
	 * @return the timeStamp
	 */
	public DateTime getTimeStamp() {
		return timeStamp;
	}


	/**
	 * @return the translationKey
	 */
	public String getTranslationKey() {
		return translationKey;
	}


	/**
	 * @return the userIdentifier
	 */
	public String getUserIdentifier() {
		return userIdentifier;
	}


	@Override
	public int hashCode() {
		return new HashCodeBuilder(89, 13).append(this.id).append(this.timeStamp).append(this.translationKey)
				.append(this.userIdentifier).append(this.bpsExceptionLayer).append(this.bpsExceptionType)
				.append(this.bpsExceptionDiscriminator).toHashCode();
	}


	/**
	 * @param parameters
	 *          the parameters to set
	 */
	public void setParameters(final Object[] parameters) {
		this.parameters = parameters;
	}


	/**
	 * @param userIdentifier
	 *          the userIdentifier to set
	 */
	public void setUserIdentifier(final String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}


	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", this.getId())
				.append("exceptionDiscriminator", this.getBpsExceptionDiscriminator())
				.append("exceptionType", this.getBpsExceptionType()).append("exceptionLayer", this.getBpsExceptionLayer())
				.append("parameters", parameters).append("userIdentifier", this.getUserIdentifier())
				.append("timestamp", this.getTimeStamp()).toString();
	}


	/**
	 * Validates the defined exception. Both Type and Layer are mandatory. User and cause are not mandatory. Parameters
	 * certainly not.
	 */
	private void validateException() {
		if (StringUtils.isEmpty(this.id)) {
			LOGGER.error("A BPSExceptioin was defined without id");
			throw new IllegalArgumentException("A BPSException needs to have an id where it is raised");
		}
		if (this.id.length() < 4) {
			LOGGER.error("A BPSException was defined with an id with less then 4 characters");
			throw new IllegalArgumentException("A BPSException needs to be at lease 4 chars long");
		}
		if (!StringUtils.isAlphanumeric(this.id)) {
			LOGGER.error("A BPSException was defined with and id that is not an alphanumeric");
			throw new IllegalArgumentException("A BPSException needs to be an alphanumeric");
		}
		if (this.bpsExceptionLayer == null) {
			LOGGER.error("A BPSException was defined without a Layer");
			throw new IllegalArgumentException("A BPSException needs to have a Layer where it is raised");
		}
		if (this.bpsExceptionType == null) {
			LOGGER.error("A BPSException was defined without a Type");
			throw new IllegalArgumentException("A BPSException needs to have a Type");
		}
		if (StringUtils.isEmpty(this.userIdentifier)) {
			LOGGER.warn("A BPSException was defined without a user identification"
					+ " - one should define a technical user if no user can be used");
		}
	}
}
