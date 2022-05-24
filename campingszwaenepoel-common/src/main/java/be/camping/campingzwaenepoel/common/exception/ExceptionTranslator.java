package be.camping.campingzwaenepoel.common.exception;

import be.camping.campingzwaenepoel.common.constants.GlobalConstants;
import be.camping.campingzwaenepoel.common.constants.TranslationConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Translates the given exception to the message found in the associated resourcebundle.
 * 
 * @author meyersb
 */
@Component("exceptionTranslator")
public final class ExceptionTranslator {

	/**
	 * The logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger("ExceptionTranslator");

	/**
	 * The resourceBundle.
	 */
	@Autowired
	@Qualifier("exceptionResourceBundle")
	private ResourceBundleMessageSource exceptionResourceBundle = null;


	/**
	 * Translates the translationkey in the provided exception.
	 * 
	 * @param e
	 *            the Exception holding the translationkey.
	 * @return the wanted message.
	 */
	public String translate(final BPSException e) {
		if (e == null) {
			throw new IllegalArgumentException("Cannot translate an exception which is null");
		}
		return this.translate(e.getTranslationKey(), e.getParameters(), GlobalConstants.DEFAULT_LOCALE);
	}


	/**
	 * Translates the provided translationkey in the relevant message.
	 * 
	 * @param translationKey
	 *            a String with the key of the message.
	 * @param parameters
	 *            a list of possible parameters for the message.
	 * @return the wanted message.
	 */
	public String translate(final String translationKey, final Object... parameters) {
		String toReturn = null;
		if (StringUtils.isEmpty(translationKey)) {
			LOGGER.error("Cannot translate a null or empty translation key");
			throw new IllegalArgumentException("Cannot translate a translation key that is empty");
		}
		try {
			String[] parametersToString = null;
			if (parameters != null) {
				parametersToString = new String[parameters.length];
				for (int i = 0; i < parametersToString.length; i++) {
					parametersToString[i] = objectToString(parameters[i]);
				}
			}
			toReturn =
					this.exceptionResourceBundle.getMessage(translationKey, parametersToString,
							GlobalConstants.DEFAULT_LOCALE);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Translated key {} into {} ", new Object[] { translationKey, toReturn });
			}
		} catch (NoSuchMessageException nsme) {
			toReturn =
					this.exceptionResourceBundle.getMessage(TranslationConstants.NO_SUCH_MESSAGE,
							new Object[] { translationKey }, GlobalConstants.DEFAULT_LOCALE);
		}

		return toReturn;
	}


	/**
	 * Creates a string representation of the given object array. If any of the arguments in the array are themselves an
	 * array, then a recursive call to this method is made.
	 * 
	 * @param objectArray
	 *            an array of objects to create a {@link String} from
	 * @return a string of the given object array where the string takes the form
	 *         "{object[0].toString(), object[1].toString(), ... and object[N].toString()}"
	 */
	public String arrayToString(Object[] objectArray) {
		final StringBuilder arrayAsString = new StringBuilder("{");
		if (objectArray.length == 1) {
			arrayAsString.append(objectToString(objectArray[0]));
		} else {
			for (int i = 0; i < objectArray.length; i++) {
				if (i == objectArray.length - 1 && objectArray.length == 2) {
					arrayAsString.append(" and ");
				} else if (i == objectArray.length - 1) {
					arrayAsString.append(", and ");
				} else if (i != 0) {
					arrayAsString.append(", ");
				}
				arrayAsString.append(objectToString(objectArray[i]));
			}
		}
		arrayAsString.append("}");
		return arrayAsString.toString();
	}


	/**
	 * Creates a string representation of the given object with the following special considerations:
	 * <ul>
	 * <li>If the object is null, then "null" is returned</li>
	 * <li>If the object is an instance of an Object array, then the {@link #arrayToString(Object[])} method is called
	 * on the array</li>
	 * </ul>
	 * 
	 * @param object
	 *            the object to create the string representation of.
	 * @return a string representation of the given {@link Object}
	 */
	public String objectToString(Object object) {
		if (object == null) {
			return "null";
		} else if (object instanceof Object[]) {
			return arrayToString((Object[]) object);
		} else {
			return object.toString();
		}
	}


	/**
	 * Translates the translationkey in the provided exception.
	 * 
	 * @param bpse
	 *            the Exception holding the translationkey.
	 * @param locale
	 *            the Locale to use.
	 * @return the wanted message.
	 */
	public String translate(final BPSException bpse, Locale locale) {
		if (bpse == null) {
			LOGGER.error("Cannot translate an exception which is null");
			throw new IllegalArgumentException("Cannot translate an exception which is null");
		}
		if (locale == null) {
			locale = GlobalConstants.DEFAULT_LOCALE;
		}

		return this.translate(bpse.getTranslationKey(), bpse.getParameters(), locale);
	}


	/**
	 * Translates the provided translationkey in the relevant message.
	 * 
	 * @param translationKey
	 *            a String with the key of the message.
	 * @param parameters
	 *            a list of possible parameters for the message.
	 * @param locale
	 *            the Locale to use.
	 * @return the wanted message.
	 */
	public String translate(final String translationKey, final Object[] parameters, Locale locale) {
		String toReturn = null;
		if (StringUtils.isEmpty(translationKey)) {
			throw new IllegalArgumentException("Cannot translate a translation key that is empty");
		}
		if (locale == null) {
			locale = GlobalConstants.DEFAULT_LOCALE;
		}
		try {
			String[] parametersToString = null;
			if (parameters != null) {
				parametersToString = new String[parameters.length];
				for (int i = 0; i < parametersToString.length; i++) {
					parametersToString[i] = objectToString(parameters[i]);
				}
			}
			toReturn = this.exceptionResourceBundle.getMessage(translationKey, parametersToString, locale);
		} catch (NoSuchMessageException nsme) {
			toReturn =
					this.exceptionResourceBundle.getMessage(TranslationConstants.NO_SUCH_MESSAGE,
							new Object[] { translationKey }, locale);
		}

		return toReturn;
	}

}
