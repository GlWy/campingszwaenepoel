/**
 * 
 */
package be.camping.campingzwaenepoel.common.exception;

import be.camping.campingzwaenepoel.common.exception.enumeration.BPSExceptionDiscriminator;
import be.camping.campingzwaenepoel.common.exception.enumeration.BPSExceptionLayer;
import be.camping.campingzwaenepoel.common.exception.enumeration.BPSExceptionType;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.InvalidObjectException;

import static be.camping.campingzwaenepoel.common.exception.enumeration.BPSExceptionLayer.DOMAIN_LAYER;
import static be.camping.campingzwaenepoel.common.exception.enumeration.BPSExceptionLayer.PRESENTATION_FAT_CLIENT_LAYER;
import static be.camping.campingzwaenepoel.common.exception.enumeration.BPSExceptionLayer.SERVICES_LAYER;
import static be.camping.campingzwaenepoel.common.exception.enumeration.BPSExceptionType.CODING_EXCEPTION_TYPE;
import static be.camping.campingzwaenepoel.common.exception.enumeration.BPSExceptionType.SYSTEM_EXCEPTION_TYPE;
import static be.camping.campingzwaenepoel.common.exception.enumeration.BPSExceptionType.VALIDATION_EXCEPTION_TYPE;

/**
 * @author craig.moore@cropdesign.com
 */
public final class BPSExceptionFactory {

	/**
	 * A builder for creating {@link BPSException}s.
	 * 
	 * @author craig.moore@cropdesign.com
	 */
	public static class Builder {

		// Required fields
		private final BPSExceptionType bpsExceptionType;
		private final BPSExceptionLayer bpsExceptionLayer;
		private final String id;
		private final Throwable rootCause;
		private final String fullStackTrace;

		// Optional fields
		private BPSExceptionDiscriminator bpsExceptionDiscriminator;
		private String userIdentifier;
		private Object[] parameters;


		/**
		 * Create an exception builder with the required fields.
		 * 
		 * @param bpsExceptionType
		 *            the {@link BPSExceptionType} for the {@link BPSException}
		 * @param bpsExceptionLayer
		 *            the {@link BPSExceptionLayer} for the {@link BPSException}
		 * @param id
		 *            the id of the {@link BPSException}
		 * @param cause
		 *            the cause of the {@link BPSException}
		 */
		public Builder(BPSExceptionType bpsExceptionType, BPSExceptionLayer bpsExceptionLayer, String id,
				Throwable cause) {
			this.bpsExceptionType = bpsExceptionType;
			this.bpsExceptionLayer = bpsExceptionLayer;
			this.id = id;
			final Throwable rootCause = ExceptionUtils.getRootCause(cause);
			this.rootCause = rootCause == null ? cause : rootCause;
			this.fullStackTrace = ExceptionUtils.getStackTrace(cause);
		}


		/**
		 * @param userIdentifier
		 *            the userIdentifier to set.
		 * @return the {@link Builder}
		 */
		public Builder setUserIdentifier(String userIdentifier) {
			this.userIdentifier = userIdentifier;
			return this;
		}


		/**
		 * @param bpsExceptionDiscriminator
		 *            the discriminator to set
		 * @return the {@link Builder}
		 */
		public Builder setBpsExceptionDiscriminator(BPSExceptionDiscriminator bpsExceptionDiscriminator) {
			this.bpsExceptionDiscriminator = bpsExceptionDiscriminator;
			return this;
		}


		/**
		 * @param parameters
		 *            the parameters to set
		 * @return the {@link Builder}
		 */
		public Builder setParameters(Object... parameters) {
			this.parameters = parameters;
			return this;
		}


		/**
		 * @return a new BPSException based on the current values in the {@link Builder}.
		 */
		public BPSException build() {
			return new BPSException(bpsExceptionDiscriminator, bpsExceptionType, bpsExceptionLayer, id, userIdentifier,
					fullStackTrace, rootCause, parameters);
		}
	}


	/**
	 * Creates a BPSException where the cause of the exception is an {@link IllegalArgumentException} with the given
	 * exceptionMessage. <b>NOTE</b> This should only be used when creating exceptions where there is no known cause,
	 * like when performing validation on the arguments to a method. If there is a known cause, then you should use the
	 * {@link #createNestedException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, Object...)
	 * createNestedException()} method instead.
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param bpsExceptionType
	 *            The {@link BPSExceptionType type} of the exception
	 * @param bpsExceptionLayer
	 *            the {@link BPSExceptionLayer layer} the exception is from
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link IllegalArgumentException} with the given exceptionMessage
	 */
	public static BPSException createIllegalArgumentException(BPSExceptionDiscriminator bpsExceptionDiscriminator,
                                                              BPSExceptionType bpsExceptionType, BPSExceptionLayer bpsExceptionLayer, String id, String userIdentifier,
                                                              String exceptionMessage, Object... parameters) {
		return new Builder(bpsExceptionType, bpsExceptionLayer, id, new IllegalArgumentException(exceptionMessage))
				.setUserIdentifier(userIdentifier).setBpsExceptionDiscriminator(bpsExceptionDiscriminator)
				.setParameters(parameters).build();
	}


	/**
	 * Creates a BPSException where the cause of the exception is an {@link IllegalArgumentException} with the given
	 * exceptionMessage. Same as calling
	 * {@link #createIllegalArgumentException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createIllegalArgumentException(null, bpsExceptionType, bpsExceptionLayer, exceptionId, userIdentifier,
	 * exceptionMessage)}
	 * 
	 * @param bpsExceptionType
	 *            The {@link BPSExceptionType type} of the exception
	 * @param bpsExceptionLayer
	 *            the {@link BPSExceptionLayer layer} the exception is from
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link IllegalArgumentException} with the given exceptionMessage
	 */
	public static BPSException createIllegalArgumentException(BPSExceptionType bpsExceptionType,
                                                              BPSExceptionLayer bpsExceptionLayer, String id, String userIdentifier, String exceptionMessage,
                                                              Object... parameters) {
		return createIllegalArgumentException(null, bpsExceptionType, bpsExceptionLayer, id, userIdentifier,
				exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException where the cause of the exception is an {@link InvalidObjectException} with the given
	 * exceptionMessage. <b>NOTE</b> This should only be used when creating exceptions where there is no known cause,
	 * like when performing validation on the arguments to a method. If there is a known cause, then you should use the
	 * {@link #createNestedException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, Object...)
	 * createNestedException()} method instead.
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param bpsExceptionType
	 *            The {@link BPSExceptionType type} of the exception
	 * @param bpsExceptionLayer
	 *            the {@link BPSExceptionLayer layer} the exception is from
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link IllegalArgumentException} with the given exceptionMessage
	 */
	public static BPSException createInvalidObjectException(BPSExceptionDiscriminator bpsExceptionDiscriminator,
                                                            BPSExceptionType bpsExceptionType, BPSExceptionLayer bpsExceptionLayer, String id, String userIdentifier,
                                                            String exceptionMessage, Object... parameters) {
		return new Builder(bpsExceptionType, bpsExceptionLayer, id, new InvalidObjectException(exceptionMessage))
				.setUserIdentifier(userIdentifier).setBpsExceptionDiscriminator(bpsExceptionDiscriminator)
				.setParameters(parameters).build();
	}


	/**
	 * Creates a BPSException where the cause of the exception is an {@link IllegalArgumentException} with the given
	 * exceptionMessage. Same as calling
	 * {@link #createInvalidObjectException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createIllegalArgumentException(null, bpsExceptionType, bpsExceptionLayer, exceptionId, userIdentifier,
	 * exceptionMessage)}
	 * 
	 * @param bpsExceptionType
	 *            The {@link BPSExceptionType type} of the exception
	 * @param bpsExceptionLayer
	 *            the {@link BPSExceptionLayer layer} the exception is from
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link IllegalArgumentException} with the given exceptionMessage
	 */
	public static BPSException createInvalidObjectException(BPSExceptionType bpsExceptionType,
                                                            BPSExceptionLayer bpsExceptionLayer, String id, String userIdentifier, String exceptionMessage,
                                                            Object... parameters) {
		return createInvalidObjectException(null, bpsExceptionType, bpsExceptionLayer, id, userIdentifier,
				exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException where the cause of the exception is an {@link IllegalArgumentException} with the given
	 * exceptionMessage. <b>NOTE</b> This should only be used when creating exceptions where there is no known cause,
	 * like when performing validation on the arguments to a method. If there is a known cause, then you should use the
	 * {@link #createNestedException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, Object...)
	 * createNestedException()} method instead.
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param bpsExceptionType
	 *            The {@link BPSExceptionType type} of the exception
	 * @param bpsExceptionLayer
	 *            the {@link BPSExceptionLayer layer} the exception is from
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link IllegalArgumentException} with the given exceptionMessage
	 */
	public static BPSException createIllegalStateException(BPSExceptionDiscriminator bpsExceptionDiscriminator,
                                                           BPSExceptionType bpsExceptionType, BPSExceptionLayer bpsExceptionLayer, String id, String userIdentifier,
                                                           String exceptionMessage, Object... parameters) {
		return new Builder(bpsExceptionType, bpsExceptionLayer, id, new IllegalStateException(exceptionMessage))
				.setUserIdentifier(userIdentifier).setBpsExceptionDiscriminator(bpsExceptionDiscriminator)
				.setParameters(parameters).build();
	}


	/**
	 * Creates a BPSException where the cause of the exception is an {@link IllegalArgumentException} with the given
	 * exceptionMessage. Same as calling
	 * {@link #createIllegalArgumentException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createIllegalArgumentException(null, bpsExceptionType, bpsExceptionLayer, exceptionId, userIdentifier,
	 * exceptionMessage)}
	 * 
	 * @param bpsExceptionType
	 *            The {@link BPSExceptionType type} of the exception
	 * @param bpsExceptionLayer
	 *            the {@link BPSExceptionLayer layer} the exception is from
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link IllegalArgumentException} with the given exceptionMessage
	 */
	public static BPSException createIllegalStateException(BPSExceptionType bpsExceptionType,
                                                           BPSExceptionLayer bpsExceptionLayer, String id, String userIdentifier, String exceptionMessage,
                                                           Object... parameters) {
		return createIllegalStateException(null, bpsExceptionType, bpsExceptionLayer, id, userIdentifier,
				exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#VALIDATION_EXCEPTION_TYPE validation} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services} layer where the cause of the exception is an
	 * {@link IllegalArgumentException} with the given exceptionMessage. Same as calling
	 * {@link #createInvalidObjectException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createIllegalArgumentException(bpsExceptionDiscriminator, BPSExceptionType.VALIDATION_EXCEPTION_TYPE,
	 * BPSExceptionLayer.SERVICES_LAYER, id, userIdentifier, exceptionMessage, parameters)}
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link IllegalArgumentException} with the given exceptionMessage
	 */
	public static BPSException createVSInvalidObjectException(BPSExceptionDiscriminator bpsExceptionDiscriminator,
                                                              String id, String userIdentifier, String exceptionMessage, Object... parameters) {
		return createInvalidObjectException(bpsExceptionDiscriminator, VALIDATION_EXCEPTION_TYPE,
				SERVICES_LAYER, id, userIdentifier, exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#VALIDATION_EXCEPTION_TYPE validation} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services} layer where the cause of the exception is an
	 * {@link InvalidObjectException} with the given exceptionMessage. Same as calling
	 * {@link #createInvalidObjectException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createInvalidObjectException(null, BPSExceptionType.VALIDATION_EXCEPTION_TYPE, BPSExceptionLayer.SERVICES_LAYER,
	 * exceptionId, userIdentifier, exceptionMessage, parameters)}
	 * 
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link InvalidObjectException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link InvalidObjectException} with the given exceptionMessage
	 */
	public static BPSException createVSInvalidObjectException(String id, String userIdentifier,
                                                              String exceptionMessage, Object... parameters) {
		return createInvalidObjectException(null, VALIDATION_EXCEPTION_TYPE,
				SERVICES_LAYER, id, userIdentifier, exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#VALIDATION_EXCEPTION_TYPE validation} in the
	 * {@link BPSExceptionLayer#DOMAIN_LAYER domain} layer where the cause of the exception is an
	 * {@link InvalidObjectException} with the given exceptionMessage. Same as calling
	 * {@link #createInvalidObjectException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createInvalidObjectException(bpsExceptionDiscriminator, BPSExceptionType.VALIDATION_EXCEPTION_TYPE,
	 * BPSExceptionLayer.DOMAIN_LAYER, id, userIdentifier, exceptionMessage, parameters)}
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link InvalidObjectException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link InvalidObjectException} with the given exceptionMessage
	 */
	public static BPSException createVDInvalidObjectException(BPSExceptionDiscriminator bpsExceptionDiscriminator,
                                                              String id, String userIdentifier, String exceptionMessage, Object... parameters) {
		return createInvalidObjectException(bpsExceptionDiscriminator, VALIDATION_EXCEPTION_TYPE, DOMAIN_LAYER, id,
				userIdentifier, exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#VALIDATION_EXCEPTION_TYPE validation} in the
	 * {@link BPSExceptionLayer#DOMAIN_LAYER domain} layer where the cause of the exception is an
	 * {@link InvalidObjectException} with the given exceptionMessage. Same as calling
	 * {@link #createInvalidObjectException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createIllegalArgumentException(null, BPSExceptionType.VALIDATION_EXCEPTION_TYPE, BPSExceptionLayer.DOMAIN_LAYER,
	 * exceptionId, userIdentifier, exceptionMessage, parameters)}
	 * 
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link InvalidObjectException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link InvalidObjectException} with the given exceptionMessage
	 */
	public static BPSException createVDInvalidObjectException(String id, String userIdentifier,
                                                              String exceptionMessage, Object... parameters) {
		return createInvalidObjectException(null, VALIDATION_EXCEPTION_TYPE, DOMAIN_LAYER, id, userIdentifier,
				exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#VALIDATION_EXCEPTION_TYPE validation} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services} layer where the cause of the exception is an
	 * {@link IllegalArgumentException} with the given exceptionMessage. Same as calling
	 * {@link #createIllegalArgumentException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createIllegalArgumentException(bpsExceptionDiscriminator, BPSExceptionType.VALIDATION_EXCEPTION_TYPE,
	 * BPSExceptionLayer.SERVICES_LAYER, id, userIdentifier, exceptionMessage, parameters)}
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link IllegalArgumentException} with the given exceptionMessage
	 */
	public static BPSException createVSIllegalArgumentException(BPSExceptionDiscriminator bpsExceptionDiscriminator,
                                                                String id, String userIdentifier, String exceptionMessage, Object... parameters) {
		return createIllegalArgumentException(bpsExceptionDiscriminator, VALIDATION_EXCEPTION_TYPE,
				SERVICES_LAYER, id, userIdentifier, exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#VALIDATION_EXCEPTION_TYPE validation} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services} layer where the cause of the exception is an
	 * {@link IllegalArgumentException} with the given exceptionMessage. Same as calling
	 * {@link #createIllegalArgumentException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createIllegalArgumentException(null, BPSExceptionType.VALIDATION_EXCEPTION_TYPE,
	 * BPSExceptionLayer.SERVICES_LAYER, exceptionId, userIdentifier, exceptionMessage, parameters)}
	 * 
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link IllegalArgumentException} with the given exceptionMessage
	 */
	public static BPSException createVSIllegalArgumentException(String id, String userIdentifier,
                                                                String exceptionMessage, Object... parameters) {
		return createIllegalArgumentException(null, VALIDATION_EXCEPTION_TYPE,
				SERVICES_LAYER, id, userIdentifier, exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#VALIDATION_EXCEPTION_TYPE validation} in the
	 * {@link BPSExceptionLayer#DOMAIN_LAYER domain} layer where the cause of the exception is an
	 * {@link IllegalArgumentException} with the given exceptionMessage. Same as calling
	 * {@link #createIllegalArgumentException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createIllegalArgumentException(bpsExceptionDiscriminator, BPSExceptionType.VALIDATION_EXCEPTION_TYPE,
	 * BPSExceptionLayer.DOMAIN_LAYER, id, userIdentifier, exceptionMessage, parameters)}
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link IllegalArgumentException} with the given exceptionMessage
	 */
	public static BPSException createVDIllegalArgumentException(BPSExceptionDiscriminator bpsExceptionDiscriminator,
                                                                String id, String userIdentifier, String exceptionMessage, Object... parameters) {
		return createIllegalArgumentException(bpsExceptionDiscriminator, VALIDATION_EXCEPTION_TYPE,
				DOMAIN_LAYER, id, userIdentifier, exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#VALIDATION_EXCEPTION_TYPE validation} in the
	 * {@link BPSExceptionLayer#DOMAIN_LAYER domain} layer where the cause of the exception is an
	 * {@link IllegalArgumentException} with the given exceptionMessage. Same as calling
	 * {@link #createIllegalArgumentException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createIllegalArgumentException(null, BPSExceptionType.VALIDATION_EXCEPTION_TYPE, BPSExceptionLayer.DOMAIN_LAYER,
	 * exceptionId, userIdentifier, exceptionMessage, parameters)}
	 * 
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link IllegalArgumentException} with the given exceptionMessage
	 */
	public static BPSException createVDIllegalArgumentException(String id, String userIdentifier,
                                                                String exceptionMessage, Object... parameters) {
		return createIllegalArgumentException(null, VALIDATION_EXCEPTION_TYPE,
				DOMAIN_LAYER, id, userIdentifier, exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException with the given parameters, where the cause of the {@link BPSException} will be the root
	 * cause of the given {@link Throwable} and the full stack trace from the {@link Throwable} will be stored as a
	 * String in the {@link BPSException}.
	 * 
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param bpsExceptionType
	 *            The {@link BPSExceptionType type} of the exception
	 * @param bpsExceptionLayer
	 *            the {@link BPSExceptionLayer layer} the exception is from
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param e
	 *            The cause of the {@link BPSException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the root cause of the given {@link Throwable} and the full stack trace from the {@link Throwable} will be
	 *         stored as a String in the {@link BPSException}.
	 */
	public static BPSException createNestedException(BPSExceptionDiscriminator bpsExceptionDiscriminator,
                                                     BPSExceptionType bpsExceptionType, BPSExceptionLayer bpsExceptionLayer, String id, String userIdentifier,
                                                     Throwable e, Object... parameters) {
		return new BPSExceptionFactory.Builder(bpsExceptionType, bpsExceptionLayer, id, e)
				.setUserIdentifier(userIdentifier).setBpsExceptionDiscriminator(bpsExceptionDiscriminator)
				.setParameters(parameters).build();
	}


	/**
	 * Creates a BPSException with the given parameters, where the cause of the {@link BPSException} will be the root
	 * cause of the given {@link Throwable} and the full stack trace from the {@link Throwable} will be stored as a
	 * String in the {@link BPSException}. Same as calling
	 * {@link #createNestedException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, Object...)
	 * createNestedException(null, bpsExceptionType, bpsExceptionLayer, exceptionId, userIdentifier, cause, parameters)}
	 * 
	 * @param bpsExceptionType
	 *            The {@link BPSExceptionType type} of the exception
	 * @param bpsExceptionLayer
	 *            the {@link BPSExceptionLayer layer} the exception is from
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param e
	 *            The cause of the {@link BPSException}
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the root cause of the given {@link Throwable} and the full stack trace from the {@link Throwable} will be
	 *         stored as a String in the {@link BPSException}.
	 */
	public static BPSException createNestedException(BPSExceptionType bpsExceptionType,
                                                     BPSExceptionLayer bpsExceptionLayer, String id, String userIdentifier, Throwable e, Object... parameters) {
		return createNestedException(null, bpsExceptionType, bpsExceptionLayer, id, userIdentifier, e, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#BUSINESS_EXCEPTION_TYPE business} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services} layer with the given parameters, where the cause of the
	 * {@link BPSException} will be the root cause of the given {@link Throwable} and the full stack trace from the
	 * {@link Throwable} will be stored as a String in the {@link BPSException}. Same as calling
	 * {@link #createNestedException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, Object...)
	 * createNestedException(bpsExceptionDiscriminator, BPSExceptionType.BUSINESS_EXCEPTION_TYPE,
	 * BPSExceptionLayer.SERVICES_LAYER, exceptionId, userIdentifier, cause, parameters)}
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param e
	 *            The cause of the {@link BPSException}
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the root cause of the given {@link Throwable} and the full stack trace from the {@link Throwable} will be
	 *         stored as a String in the {@link BPSException}.
	 */
	public static BPSException createBSException(BPSExceptionDiscriminator bpsExceptionDiscriminator, String id,
                                                 String userIdentifier, Throwable e, Object... parameters) {
		return createNestedException(bpsExceptionDiscriminator, BPSExceptionType.BUSINESS_EXCEPTION_TYPE,
				SERVICES_LAYER, id, userIdentifier, e, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#BUSINESS_EXCEPTION_TYPE business} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services} layer with the given parameters, where the cause of the
	 * {@link BPSException} will be the root cause of the given {@link Throwable} and the full stack trace from the
	 * {@link Throwable} will be stored as a String in the {@link BPSException}. Same as calling
	 * {@link #createNestedException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, Object...)
	 * createNestedException(null, BPSExceptionType.BUSINESS_EXCEPTION_TYPE, BPSExceptionLayer.SERVICES_LAYER,
	 * exceptionId, userIdentifier, cause, parameters)}
	 * 
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param e
	 *            The cause of the {@link BPSException}
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the root cause of the given {@link Throwable} and the full stack trace from the {@link Throwable} will be
	 *         stored as a String in the {@link BPSException}.
	 */
	public static BPSException createBSException(String id, String userIdentifier, Throwable e, Object... parameters) {
		return createNestedException(null, BPSExceptionType.BUSINESS_EXCEPTION_TYPE, SERVICES_LAYER,
				id, userIdentifier, e, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#SYSTEM_EXCEPTION_TYPE system} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services} layer with the given parameters, where the cause of the
	 * {@link BPSException} will be the root cause of the given {@link Throwable} and the full stack trace from the
	 * {@link Throwable} will be stored as a String in the {@link BPSException}. Same as calling
	 * {@link #createNestedException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, Object...)
	 * createNestedException(bpsExceptionDiscriminator, BPSExceptionType.SYSTEM_EXCEPTION_TYPE,
	 * BPSExceptionLayer.SERVICES_LAYER, exceptionId, userIdentifier, cause, parameters)}
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param e
	 *            The cause of the {@link BPSException}
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the root cause of the given {@link Throwable} and the full stack trace from the {@link Throwable} will be
	 *         stored as a String in the {@link BPSException}.
	 */
	public static BPSException createSSException(BPSExceptionDiscriminator bpsExceptionDiscriminator, String id,
                                                 String userIdentifier, Throwable e, Object... parameters) {
		return createNestedException(bpsExceptionDiscriminator, SYSTEM_EXCEPTION_TYPE,
				SERVICES_LAYER, id, userIdentifier, e, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#SYSTEM_EXCEPTION_TYPE system} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services} layer with the given parameters, where the cause of the
	 * {@link BPSException} will be the root cause of the given {@link Throwable} and the full stack trace from the
	 * {@link Throwable} will be stored as a String in the {@link BPSException}. Same as calling
	 * {@link #createNestedException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, Object...)
	 * createNestedException(null, BPSExceptionType.SYSTEM_EXCEPTION_TYPE, BPSExceptionLayer.SERVICES_LAYER,
	 * exceptionId, userIdentifier, cause, parameters)}
	 * 
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param e
	 *            The cause of the {@link BPSException}
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the root cause of the given {@link Throwable} and the full stack trace from the {@link Throwable} will be
	 *         stored as a String in the {@link BPSException}.
	 */
	public static BPSException createSSException(String id, String userIdentifier, Throwable e, Object... parameters) {
		return createNestedException(null, SYSTEM_EXCEPTION_TYPE, SERVICES_LAYER,
				id, userIdentifier, e, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#SYSTEM_EXCEPTION_TYPE system} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services} layer with the given parameters, where the cause of the
	 * {@link BPSException} will be an {@link IllegalStateException} with the given exceptionMessage as its message.
	 * Same as calling
	 * {@link #createIllegalStateException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createIllegalStateException(bpsExceptionDiscriminator, BPSExceptionType.SYSTEM_EXCEPTION_TYPE,
	 * BPSExceptionLayer.SERVICES_LAYER, id, userIdentifier, exceptionMessage, parameters)}
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the an {@link IllegalStateException} with the given exception message.
	 */
	public static BPSException createSSIllegalStateException(BPSExceptionDiscriminator bpsExceptionDiscriminator,
                                                             String id, String userIdentifier, String exceptionMessage, Object... parameters) {
		return createIllegalStateException(bpsExceptionDiscriminator, SYSTEM_EXCEPTION_TYPE,
				SERVICES_LAYER, id, userIdentifier, exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#SYSTEM_EXCEPTION_TYPE system} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services} layer with the given parameters, where the cause of the
	 * {@link BPSException} will be an {@link IllegalStateException} with the given exceptionMessage as its message.
	 * Same as calling
	 * {@link #createSSIllegalStateException(BPSExceptionDiscriminator, String, String, String, Object...)
	 * createSSIllegalStateException(null, id, userIdentifier, exceptionMessage, parameters)}
	 * 
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalStateException}
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the an {@link IllegalStateException} with the given exception message.
	 */
	public static BPSException createSSIllegalStateException(String id, String userIdentifier, String exceptionMessage,
                                                             Object... parameters) {
		return createSSIllegalStateException(null, id, userIdentifier, exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#SYSTEM_EXCEPTION_TYPE system} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services} layer with the given parameters, where the cause of the
	 * {@link BPSException} will be an {@link IllegalArgumentException} with the given exceptionMessage as its message.
	 * Same as calling
	 * {@link #createIllegalArgumentException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createIllegalStateException(bpsExceptionDiscriminator, SYSTEM_EXCEPTION_TYPE, SERVICES_LAYER, id, userIdentifier,
	 * exceptionMessage, parameters)}
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message to use for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the an {@link IllegalArgumentException} with the given exception message.
	 */
	public static BPSException createSSIllegalArgumentException(BPSExceptionDiscriminator bpsExceptionDiscriminator,
                                                                String id, String userIdentifier, String exceptionMessage, Object... parameters) {
		return createIllegalArgumentException(bpsExceptionDiscriminator, SYSTEM_EXCEPTION_TYPE, SERVICES_LAYER, id,
				userIdentifier, exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#SYSTEM_EXCEPTION_TYPE system} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services} layer with the given parameters, where the cause of the
	 * {@link BPSException} will be an {@link IllegalArgumentException} with the given exceptionMessage as its message.
	 * Same as calling
	 * {@link BPSExceptionFactory#createSSIllegalArgumentException(BPSExceptionDiscriminator, String, String, String, Object...)
	 * createSSIllegalArgumentException(null, id, userIdentifier, exceptionMessage, parameters)}
	 * 
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the an {@link IllegalArgumentException} with the given exception message.
	 */
	public static BPSException createSSIllegalArgumentException(String id, String userIdentifier,
                                                                String exceptionMessage, Object... parameters) {
		return createSSIllegalArgumentException(null, id, userIdentifier, exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#CODING_EXCEPTION_TYPE validation} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services} layer where the cause of the exception is an
	 * {@link IllegalArgumentException} with the given exceptionMessage. Same as calling
	 * {@link #createIllegalArgumentException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createIllegalArgumentException(bpsExceptionDiscriminator, BPSExceptionType.CODING_EXCEPTION_TYPE,
	 * BPSExceptionLayer.SERVICES_LAYER, id, userIdentifier, exceptionMessage, parameters)}
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link IllegalArgumentException} with the given exceptionMessage
	 */
	public static BPSException createCSIllegalArgumentException(BPSExceptionDiscriminator bpsExceptionDiscriminator,
                                                                String id, String userIdentifier, String exceptionMessage, Object... parameters) {
		return createIllegalArgumentException(bpsExceptionDiscriminator, CODING_EXCEPTION_TYPE,
				SERVICES_LAYER, id, userIdentifier, exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#CODING_EXCEPTION_TYPE validation} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services} layer where the cause of the exception is an
	 * {@link IllegalArgumentException} with the given exceptionMessage. Same as calling
	 * {@link #createIllegalArgumentException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createIllegalArgumentException(null, BPSExceptionType.CODING_EXCEPTION_TYPE, BPSExceptionLayer.SERVICES_LAYER,
	 * exceptionId, userIdentifier, exceptionMessage, parameters)}
	 * 
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link IllegalArgumentException} with the given exceptionMessage
	 */
	public static BPSException createCSIllegalArgumentException(String id, String userIdentifier,
                                                                String exceptionMessage, Object... parameters) {
		return createIllegalArgumentException(null, CODING_EXCEPTION_TYPE,
				SERVICES_LAYER, id, userIdentifier, exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#VALIDATION_EXCEPTION_TYPE validation} in the
	 * {@link BPSExceptionLayer#PRESENTATION_FAT_CLIENT_LAYER} layer where the cause of the exception is an
	 * {@link IllegalArgumentException} with the given exceptionMessage. Same as calling
	 * {@link #createIllegalArgumentException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createIllegalArgumentException(bpsExceptionDiscriminator, BPSExceptionType.VALIDATION_EXCEPTION_TYPE,
	 * BPSExceptionLayer.PRESENTATION_FAT_CLIENT_LAYER, id, userIdentifier, exceptionMessage, parameters)}
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link IllegalArgumentException} with the given exceptionMessage
	 */
	public static BPSException createVPFCIllegalArgumentException(BPSExceptionDiscriminator bpsExceptionDiscriminator,
                                                                  String id, String userIdentifier, String exceptionMessage, Object... parameters) {
		return createIllegalArgumentException(bpsExceptionDiscriminator, VALIDATION_EXCEPTION_TYPE,
				PRESENTATION_FAT_CLIENT_LAYER, id, userIdentifier, exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#VALIDATION_EXCEPTION_TYPE validation} in the
	 * {@link BPSExceptionLayer#PRESENTATION_FAT_CLIENT_LAYER} layer where the cause of the exception is an
	 * {@link IllegalArgumentException} with the given exceptionMessage. Same as calling
	 * {@link #createIllegalArgumentException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createIllegalArgumentException(null, BPSExceptionType.VALIDATION_EXCEPTION_TYPE,
	 * BPSExceptionLayer.PRESENTATION_FAT_CLIENT_LAYER, id, userIdentifier, exceptionMessage, parameters)}
	 * 
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link IllegalArgumentException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link IllegalArgumentException} with the given exceptionMessage
	 */
	public static BPSException createVPFCIllegalArgumentException(String id, String userIdentifier,
                                                                  String exceptionMessage, Object... parameters) {
		return createIllegalArgumentException(null, VALIDATION_EXCEPTION_TYPE, PRESENTATION_FAT_CLIENT_LAYER, id,
				userIdentifier, exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#VALIDATION_EXCEPTION_TYPE validation} in the
	 * {@link BPSExceptionLayer#PRESENTATION_FAT_CLIENT_LAYER} layer where the cause of the exception is an
	 * {@link InvalidObjectException} with the given exceptionMessage. Same as calling
	 * {@link #createInvalidObjectException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, String, Object...)
	 * createInvalidObjectException(bpsExceptionDiscriminator, VALIDATION_EXCEPTION_TYPE, PRESENTATION_FAT_CLIENT_LAYER,
	 * id, userIdentifier, exceptionMessage, parameters)}
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param exceptionMessage
	 *            the message that will be used as the message for the {@link InvalidObjectException}
	 * @param parameters
	 *            The parameters to insert into the exception message
	 * @return the {@link BPSException} with the given parameters, where the cause of the exception is an
	 *         {@link InvalidObjectException} with the given exceptionMessage
	 */
	public static BPSException createVPFCInvalidObjectException(BPSExceptionDiscriminator bpsExceptionDiscriminator,
                                                                String id, String userIdentifier, String exceptionMessage, Object... parameters) {
		return createInvalidObjectException(bpsExceptionDiscriminator, VALIDATION_EXCEPTION_TYPE,
				PRESENTATION_FAT_CLIENT_LAYER, id, userIdentifier, exceptionMessage, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#SYSTEM_EXCEPTION_TYPE system} in the
	 * {@link BPSExceptionLayer#PRESENTATION_FAT_CLIENT_LAYER services} layer with the given parameters, where the cause
	 * 
	 * of the {@link BPSException} will be the root cause of the given {@link Throwable} and the full stack trace from
	 * the {@link Throwable} will be stored as a String in the {@link BPSException}. Same as calling
	 * {@link #createNestedException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, Object...)
	 * createNestedException(null, BPSExceptionType.SYSTEM_EXCEPTION_TYPE,
	 * BPSExceptionLayer.PRESENTATION_FAT_CLIENT_LAYER, exceptionId, userIdentifier, cause, parameters)}
	 * 
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param e
	 *            The cause of the {@link BPSException}
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the root cause of the given {@link Throwable} and the full stack trace from the {@link Throwable} will be
	 *         stored as a String in the {@link BPSException}.
	 */
	public static BPSException createSPFCException(String id, String userIdentifier, Throwable e, Object... parameters) {
		return createNestedException(null, SYSTEM_EXCEPTION_TYPE, PRESENTATION_FAT_CLIENT_LAYER, id, userIdentifier, e,
				parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#SYSTEM_EXCEPTION_TYPE system} in the
	 * {@link BPSExceptionLayer#PRESENTATION_FAT_CLIENT_LAYER presentation fat client layer} layer with the given
	 * parameters, where the cause of the {@link BPSException} will be the root cause of the given {@link Throwable} and
	 * the full stack trace from the {@link Throwable} will be stored as a String in the {@link BPSException}. Same as
	 * calling
	 * {@link #createNestedException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, Object...)
	 * createNestedException(null, BPSExceptionType.SYSTEM_EXCEPTION_TYPE,
	 * BPSExceptionLayer.PRESENTATION_FAT_CLIENT_LAYER, exceptionId, userIdentifier, cause, parameters)}
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param e
	 *            The cause of the {@link BPSException}
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the root cause of the given {@link Throwable} and the full stack trace from the {@link Throwable} will be
	 *         stored as a String in the {@link BPSException}.
	 */
	public static BPSException createSPFCException(BPSExceptionDiscriminator bpsExceptionDiscriminator, String id,
                                                   String userIdentifier, Throwable e, Object... parameters) {
		return createNestedException(bpsExceptionDiscriminator, SYSTEM_EXCEPTION_TYPE, PRESENTATION_FAT_CLIENT_LAYER,
				id, userIdentifier, e, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#CODING_EXCEPTION_TYPE coding} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services layer} layer with the given parameters, where the cause of the
	 * {@link BPSException} will be the root cause of the given {@link Throwable} and the full stack trace from the
	 * {@link Throwable} will be stored as a String in the {@link BPSException}. Same as calling
	 * {@link #createNestedException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, Object...)
	 * createNestedException(bpsExceptionDiscriminator, CODING_EXCEPTION_TYPE, SERVICES_LAYER, id, userIdentifier, e,
	 * parameters)}
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param e
	 *            The cause of the {@link BPSException}
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the root cause of the given {@link Throwable} and the full stack trace from the {@link Throwable} will be
	 *         stored as a String in the {@link BPSException}.
	 */
	public static BPSException createCSException(BPSExceptionDiscriminator bpsExceptionDiscriminator, String id,
                                                 String userIdentifier, Throwable e, Object... parameters) {
		return createNestedException(bpsExceptionDiscriminator, CODING_EXCEPTION_TYPE, SERVICES_LAYER, id,
				userIdentifier, e, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#CODING_EXCEPTION_TYPE coding} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services layer} layer with the given parameters, where the cause of the
	 * {@link BPSException} will be the root cause of the given {@link Throwable} and the full stack trace from the
	 * {@link Throwable} will be stored as a String in the {@link BPSException}. Same as calling
	 * {@link #createNestedException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, Object...)
	 * createNestedException(null, CODING_EXCEPTION_TYPE, SERVICES_LAYER, id, userIdentifier, e, parameters)}
	 * 
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param e
	 *            The cause of the {@link BPSException}
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the root cause of the given {@link Throwable} and the full stack trace from the {@link Throwable} will be
	 *         stored as a String in the {@link BPSException}.
	 */
	public static BPSException createCSException(String id, String userIdentifier, Throwable e, Object... parameters) {
		return createNestedException(null, CODING_EXCEPTION_TYPE, SERVICES_LAYER, id, userIdentifier, e, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#CODING_EXCEPTION_TYPE coding} in the
	 * {@link BPSExceptionLayer#PRESENTATION_FAT_CLIENT_LAYER presentation fat client} layer with the given parameters,
	 * where the cause of the {@link BPSException} will be the root cause of the given {@link Throwable} and the full
	 * stack trace from the {@link Throwable} will be stored as a String in the {@link BPSException}. Same as calling
	 * {@link #createNestedException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, Object...)
	 * createNestedException(bpsExceptionDiscriminator, CODING_EXCEPTION_TYPE, SERVICES_LAYER, id, userIdentifier, e,
	 * parameters)}
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param e
	 *            The cause of the {@link BPSException}
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the root cause of the given {@link Throwable} and the full stack trace from the {@link Throwable} will be
	 *         stored as a String in the {@link BPSException}.
	 */
	public static BPSException createCPFCException(BPSExceptionDiscriminator bpsExceptionDiscriminator, String id,
                                                   String userIdentifier, Throwable e, Object... parameters) {
		return createNestedException(bpsExceptionDiscriminator, CODING_EXCEPTION_TYPE, PRESENTATION_FAT_CLIENT_LAYER,
				id, userIdentifier, e, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#CODING_EXCEPTION_TYPE coding} in the
	 * {@link BPSExceptionLayer#PRESENTATION_FAT_CLIENT_LAYER presentation fat client} layer with the given parameters,
	 * where the cause of the {@link BPSException} will be the root cause of the given {@link Throwable} and the full
	 * stack trace from the {@link Throwable} will be stored as a String in the {@link BPSException}. Same as calling
	 * {@link #createNestedException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, Object...)
	 * createNestedException(null, CODING_EXCEPTION_TYPE, SERVICES_LAYER, id, userIdentifier, e, parameters)}
	 * 
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param e
	 *            The cause of the {@link BPSException}
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the root cause of the given {@link Throwable} and the full stack trace from the {@link Throwable} will be
	 *         stored as a String in the {@link BPSException}.
	 */
	public static BPSException createCPFCException(String id, String userIdentifier, Throwable e, Object... parameters) {
		return createNestedException(null, CODING_EXCEPTION_TYPE, PRESENTATION_FAT_CLIENT_LAYER, id, userIdentifier, e,
				parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#VALIDATION_EXCEPTION_TYPE validation} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services} layer with the given parameters, where the cause of the
	 * {@link BPSException} will be the root cause of the given {@link Throwable} and the full stack trace from the
	 * {@link Throwable} will be stored as a String in the {@link BPSException}. Same as calling
	 * {@link #createNestedException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, Object...)
	 * createNestedException(null, VALIDATION_EXCEPTION_TYPE, SERVICES_LAYER, id, userIdentifier, e, parameters)}
	 * 
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param e
	 *            The cause of the {@link BPSException}
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the root cause of the given {@link Throwable} and the full stack trace from the {@link Throwable} will be
	 *         stored as a String in the {@link BPSException}.
	 */
	public static BPSException createVSException(String id, String userIdentifier, Throwable e, Object... parameters) {
		return createNestedException(null, VALIDATION_EXCEPTION_TYPE, SERVICES_LAYER, id, userIdentifier, e, parameters);
	}


	/**
	 * Creates a BPSException of type {@link BPSExceptionType#VALIDATION_EXCEPTION_TYPE validation} in the
	 * {@link BPSExceptionLayer#SERVICES_LAYER services} layer with the given parameters, where the cause of the
	 * {@link BPSException} will be the root cause of the given {@link Throwable} and the full stack trace from the
	 * {@link Throwable} will be stored as a String in the {@link BPSException}. Same as calling
	 * {@link #createNestedException(BPSExceptionDiscriminator, BPSExceptionType, BPSExceptionLayer, String, String, Throwable, Object...)
	 * createNestedException(null, VALIDATION_EXCEPTION_TYPE, SERVICES_LAYER, id, userIdentifier, e, parameters)}
	 * 
	 * @param bpsExceptionDiscriminator
	 *            the {@link BPSExceptionDiscriminator discriminator} for the {@link BPSException}
	 * @param id
	 *            the identifier for the exception message
	 * @param userIdentifier
	 *            the identifier of the user the exception was created for. Normally this the guid or id of the user,
	 *            but if that is not available then the simple class name where the exception was thrown is also
	 *            acceptable.
	 * @param e
	 *            The cause of the {@link BPSException}
	 * @param parameters
	 *            The parameters that will be used to insert arguments into the exception message from the resource
	 *            bundle.
	 * @return the {@link BPSException} with the given parameters, where the cause of the {@link BPSException} will be
	 *         the root cause of the given {@link Throwable} and the full stack trace from the {@link Throwable} will be
	 *         stored as a String in the {@link BPSException}.
	 */
	public static BPSException createVSException(BPSExceptionDiscriminator bpsExceptionDiscriminator, String id,
                                                 String userIdentifier, Throwable e, Object... parameters) {
		return createNestedException(bpsExceptionDiscriminator, VALIDATION_EXCEPTION_TYPE, SERVICES_LAYER, id,
				userIdentifier, e, parameters);
	}
}
