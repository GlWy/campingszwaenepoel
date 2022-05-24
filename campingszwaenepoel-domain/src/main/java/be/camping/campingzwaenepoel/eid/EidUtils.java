package be.camping.campingzwaenepoel.eid;

import org.apache.log4j.Logger;

//import be.belgium.eid.BEID_Exception;
//import be.belgium.eid.BEID_ReaderSet;

public class EidUtils {
	
	private static final Logger logger = Logger.getLogger(EidUtils.class);
	private static boolean initialized = false;
	private static boolean release = false;

	public static boolean isInitialized() {
		return initialized;
	}

	public static void setInitialized(boolean initialized) {
		EidUtils.initialized = initialized;
	}

	public static boolean isRelease() {
		return release;
	}

	public static void setRelease(boolean release) {
		EidUtils.release = release;
	}

	public static void initSDK() {
		try {
			if (!initialized) {
//				BEID_ReaderSet.initSDK();
				initialized = true;
			}
/*		} catch( BEID_Exception e) {
			logger.error("[Catch] BEID_Exception:" + e.GetError());
			logger.error(e);
			setRelease(true);*/
		}catch (Exception e) {
			logger.error("error initializing EID");
		}
	}
	
	public static void releaseSDK() {
		try {
			if (release && initialized) {
//				BEID_ReaderSet.releaseSDK();
				initialized = false;
				release = false;
			}
/*		} catch( BEID_Exception e) {
			logger.error("[Catch] BEID_Exception:" + e.GetError());
			logger.error(e);
			setRelease(true);*/
		}catch (Exception e) {
			logger.error("error releasing EID");
		}
	}
}
