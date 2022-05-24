package be.camping.campingzwaenepoel.common.constants;

import java.io.Serializable;

/**
 * Some constants used by the translationservices. At this moment, we only use this for the exception-translations.
 * 
 * @author meyersb
 */
public final class TranslationConstants implements Serializable {

	/**
	 * Generated serial version id.
	 */
	private static final long serialVersionUID = 508534451144743449L;


	/**
	 * No instantiations possible.
	 */
	private TranslationConstants() {
	}

	/**
	 * NOT_AVAILABLE.
	 */
	public static final String NOT_AVAILABLE = "not_available";
	/**
	 * NO_SUCH_MESSAGE.
	 */
	public static final String NO_SUCH_MESSAGE = "no_such_message";

}
