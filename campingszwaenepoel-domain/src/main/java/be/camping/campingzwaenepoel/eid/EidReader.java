package be.camping.campingzwaenepoel.eid;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/*import be.belgium.eid.BEID_ByteArray;
import be.belgium.eid.BEID_CardType;
import be.belgium.eid.BEID_EIDCard;
import be.belgium.eid.BEID_EId;
import be.belgium.eid.BEID_Exception;
import be.belgium.eid.BEID_ForeignerCard;
import be.belgium.eid.BEID_KidsCard;
import be.belgium.eid.BEID_ReaderContext;
import be.belgium.eid.BEID_ReaderSet;
import be.belgium.eid.BEID_SISCard;
import be.belgium.eid.BEID_SisId;
import be.belgium.eid.BEID_Picture;*/
import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.common.enums.GeslachtEnum;
import be.camping.campingzwaenepoel.common.enums.LandEnum;
import be.camping.campingzwaenepoel.domain.model.Adres;
import be.camping.campingzwaenepoel.domain.model.Persoon;

public class EidReader {
	
	private static Logger logger = Logger.getLogger(EidReader.class);
	
	//*****************************************************************************
	// Get the data and dump to the screen
	// Beware: The data coming from the cards is encoded in UTF8!
	//*****************************************************************************
/*	private static void getSISData(BEID_SISCard card) throws Exception
	{
		BEID_SisId sisId = card.getID();

		System.out.println();

		System.out.println("\tPeronal data:");
		System.out.println( "\t-------------"		     );
		System.out.println( "\tName                 : " + sisId.getName());
		System.out.println( "\tSurname              : " + sisId.getSurname());
		System.out.println( "\tInitials             : " + sisId.getInitials());
		System.out.println( "\tGender               : " + sisId.getGender());
		System.out.println( "\tDateOfBirth          : " + sisId.getDateOfBirth());
		System.out.println( "\tSocialSecurityNumber : " + sisId.getSocialSecurityNumber());

		System.out.println();

		System.out.println( "\tCard data:");
		System.out.println( "\t----------");
		System.out.println( "\tLogicalNumber        : " + sisId.getLogicalNumber());
		System.out.println( "\tDateOfIssue          : " + sisId.getDateOfIssue());
		System.out.println( "\tValidityBeginDate    : " + sisId.getValidityBeginDate());
		System.out.println( "\tValidityEndDate      : " + sisId.getValidityEndDate());
	}

	//*****************************************************************************
	// Get the data from a Belgian SIS card
	//*****************************************************************************
	private static void getSISCardData(BEID_ReaderContext readerContext) throws Exception
	{
		BEID_SISCard card = readerContext.getSISCard();
		getSISData( card );
	}

	//*****************************************************************************
	// Get the data and dump to the screen
	// Beware: The data coming from the cards is encoded in UTF8!
	//*****************************************************************************
	private static BEID_EId getEIDData(BEID_EIDCard card) throws Exception
	{
		BEID_EId	  eid  = card.getID();

		if ( card.isTestCard() )
		{
			card.setAllowTestCard(true);
			logger.info( "" );
			logger.info( "Warning: This is a test card.");
		}

/*		logger.info( "\tDocumentVersion    : " + eid.getDocumentVersion() );
		logger.info( "\tDocumentType       : " + eid.getDocumentType() );

		logger.info( "" );

		logger.info( "\tPeronal data:" );
		logger.info( "\t-------------" );
		logger.info( "\tFirstName          : " + eid.getFirstName()			);
		logger.info( "\tSurname            : " + eid.getSurname()				);
		logger.info( "\tGender             : " + eid.getGender()				);
		logger.info( "\tDateOfBirth        : " + eid.getDateOfBirth()			);
		logger.info( "\tLocationOfBirth    : " + eid.getLocationOfBirth()		);
		logger.info( "\tNobility           : " + eid.getNobility()				);
		logger.info( "\tNationality        : " + eid.getNationality()			);
		logger.info( "\tNationalNumber     : " + eid.getNationalNumber()		);
		logger.info( "\tSpecialOrganization: " + eid.getSpecialOrganization()	);
		logger.info( "\tMemberOfFamily     : " + eid.getMemberOfFamily()		);
		logger.info( "\tAddressVersion     : " + eid.getAddressVersion()		);
		logger.info( "\tStreet             : " + eid.getStreet()				);
		logger.info( "\tZipCode            : " + eid.getZipCode()				);
		logger.info( "\tMunicipality       : " + eid.getMunicipality()			);
		logger.info( "\tCountry            : " + eid.getCountry()				);
		logger.info( "\tSpecialStatus      : " + eid.getSpecialStatus()		);

		logger.info( "" );

		logger.info( "\tCard data:"		       );
		logger.info( "\t----------"		       );
		logger.info( "\tLogicalNumber      : " + eid.getLogicalNumber()		);
		logger.info( "\tChipNumber         : " + eid.getChipNumber()			);
		logger.info( "\tValidityBeginDate  : " + eid.getValidityBeginDate()	);
		logger.info( "\tValidityEndDate    : " + eid.getValidityEndDate()		);
		logger.info( "\tIssuingMunicipality: " + eid.getIssuingMunicipality()	);*/
		
/*		return eid;
	}

	//*****************************************************************************
	// Get the data from a Belgian kids EID card
	//*****************************************************************************
	private static BEID_EId getKidsCardData(BEID_ReaderContext readerContext) throws Exception
	{
		BEID_KidsCard card = readerContext.getKidsCard();
		return getEIDData(card);
	}

	//*****************************************************************************
	// Get the data from a Belgian foreigner EID card
	//*****************************************************************************
	private static BEID_EId getForeignerCardData(BEID_ReaderContext readerContext) throws Exception
	{
		BEID_ForeignerCard card = readerContext.getForeignerCard();
		return getEIDData(card);
	}

	//*****************************************************************************
	// Get the data from a Belgian EID card
	//*****************************************************************************
	private static BEID_EId getEidCardData(BEID_ReaderContext readerContext) throws Exception
	{
		BEID_EIDCard card = readerContext.getEIDCard();
		return getEIDData(card);
	}

	//*****************************************************************************
	// get a string representation of the card type
	//*****************************************************************************
	private static String getCardTypeStr(BEID_ReaderContext readerContext) throws Exception
	{
		String		strCardType="UNKNOWN";
		BEID_CardType	cardType = readerContext.getCardType();

		if (cardType == BEID_CardType.BEID_CARDTYPE_EID)
		{
			strCardType = "BEID_CARDTYPE_EID";
		}
		else if (cardType == BEID_CardType.BEID_CARDTYPE_KIDS)
		{
			strCardType = "BEID_CARDTYPE_KIDS";
		}
		else if (cardType == BEID_CardType.BEID_CARDTYPE_FOREIGNER)
		{
			strCardType = "BEID_CARDTYPE_FOREIGNER";
		}
		else if (cardType == BEID_CardType.BEID_CARDTYPE_SIS)
		{
			strCardType = "BEID_CARDTYPE_SIS";
		}
		else
		{
			strCardType = "BEID_CARDTYPE_UNKNOWN";
		}
		return strCardType;
	}

	//-------------------------------------------------------------
	// get the picture from the card
	//-------------------------------------------------------------
	public static byte[] getPicture(BEID_EIDCard card) throws java.lang.Exception
	{
		if (card == null)
		{
			return null;
		}
		BEID_Picture picture = card.getPicture();
		if (picture == null)
		{
			return null;
		}
		BEID_ByteArray pictureData = picture.getData();
		byte[] pictureBytes = pictureData.GetBytes();
		return pictureBytes;
	}

	//*****************************************************************************
	// Show the info of the card in the reader
	//*****************************************************************************
	private static Persoon showCardInfo(String readerName) throws Exception
	{
		BEID_ReaderContext readerContext = BEID_ReaderSet.instance().getReaderByName( readerName );
		BEID_EId eid = null;
		Persoon persoon = new Persoon();
		
		if ( readerContext.isCardPresent() )
		{
			logger.info("\tType               : " + getCardTypeStr(readerContext));

			BEID_CardType cardType = readerContext.getCardType();

			if (cardType == BEID_CardType.BEID_CARDTYPE_EID)
			{
				eid = getEidCardData(readerContext);
			}
			else if (cardType == BEID_CardType.BEID_CARDTYPE_KIDS)
			{
				eid = getKidsCardData(readerContext);
			}
			else if (cardType == BEID_CardType.BEID_CARDTYPE_FOREIGNER)
			{
				eid = getForeignerCardData(readerContext);
			}
			else if (cardType == BEID_CardType.BEID_CARDTYPE_SIS)
			{
//				return getSISCardData(readerContext);
			}
			else
			{

			}
			
			if (eid != null) {
				Calendar cal = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					String sDate = getDateStringFromEid(eid.getDateOfBirth());
					date = df.parse(sDate);
					cal.setTime(date);
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					logger.error(e2);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					logger.error(e2);
				}
				persoon.setNaam(eid.getSurname());
				persoon.setVoornaam(eid.getFirstName());
				persoon.setGeslacht(GeslachtEnum.getGeslachtEnumForTranslationKey(eid.getGender()));
				persoon.setGeboortedatum(date);
				persoon.setGeboorteplaats(eid.getLocationOfBirth());
				persoon.setNationaliteit(eid.getNationality());
				persoon.setRijksregisternummer(eid.getNationalNumber());
				Adres adres = new Adres();
				adres.setStraat(eid.getStreet());
				adres.setPostcode(eid.getZipCode());
				adres.setPlaats(eid.getMunicipality());
				adres.setLand(LandEnum.getCountry(eid.getCountry()));
				persoon.setAdres(adres);
				persoon.setIdentiteitskaartnummer(eid.getLogicalNumber());
			}
		}

		return persoon;
	}

	//*****************************************************************************
	// Show the reader info an get the data of the card if present
	//*****************************************************************************
	private static boolean isCardPresent(String readerName) throws Exception
	{
		BEID_ReaderContext readerContext = BEID_ReaderSet.instance().getReaderByName( readerName );

		logger.info("Reader: "+readerName);
//		logger.info("\tCard present: " + (readerContext.isCardPresent()? "yes" :"no")); // 4s

//		logger.info("");

		return readerContext.isCardPresent();
	}

	//*****************************************************************************
	// scan all the card readers and if a card is present, show the content of the
	// card.
	//*****************************************************************************
	private static String scanReaders() throws Exception
	{
		long nrReaders  = BEID_ReaderSet.instance().readerCount();
		logger.info("Nr of card readers detected: "+nrReaders);
		String reader = null;
		if (nrReaders > 0) {
			reader = BEID_ReaderSet.instance().getReaderName(0);
		}
		return reader;
	}

	/**
	 * TODO: check if initSDK can be done one during start up.
	 */
	
	/**
	 * @author Glenn Wybo
	 * @return Map with errorcode, errormessage and domain object Persoon
	 * @description errorcode 0:exception, 1:success, 2:no cardreader, 3:no card
	 */
/*	public static Map<String, Object> retrieveEidCardData() {
		Map<String, Object> cardData = new HashMap<String, Object>();

		logger.info("[Info]  eID SDK sample program: read_eid");

		String osName = System.getProperty("os.name");

		if ( -1 != osName.indexOf("Windows") )
		{
			logger.info("[Info]  Windows system!!");
			System.loadLibrary("beid35libJava_Wrapper");
		}
		else
		{
			System.loadLibrary("beidlibJava_Wrapper");
		}

		try
		{
			EidUtils.initSDK();
//			BEID_ReaderSet.initSDK(); // 12s
			
			String readerName = null;
			try {
				readerName = scanReaders();
			} catch (Exception e) {
				EidUtils.setInitialized(false);
				EidUtils.initSDK();
				readerName = scanReaders();
			}
			if (readerName != null) {
				if (isCardPresent(readerName)) {
					Persoon persoon = showCardInfo( readerName );
					cardData.put(Constant.ERRORCODE, 1);
					cardData.put(Constant.ERRORMESSAGE, "");
					cardData.put(Constant.PERSOON, persoon);
					
					BEID_ReaderContext readerContext = BEID_ReaderSet.instance().getReaderByName( readerName );
					BEID_EIDCard card = readerContext.getEIDCard();
					byte[] bytes = getPicture(card);
					cardData.put(Constant.PASFOTO, bytes);
				} else {
					cardData.put(Constant.ERRORCODE, 3);
					cardData.put(Constant.ERRORMESSAGE, "no card detected");
				}
			} else {
				cardData.put(Constant.ERRORCODE, 2);
				cardData.put(Constant.ERRORMESSAGE, "no cardreader detected");
			}
		}
		catch( BEID_Exception e)
		{
			logger.error("[Catch] BEID_Exception:" + e.GetError());
			logger.error(e);
			EidUtils.setRelease(true);
		}
		catch( Exception e)
		{
			logger.info("[Catch] Exception:" + e.getMessage());
			logger.error(e);
		}

		try
		{
//			BEID_ReaderSet.releaseSDK();
			EidUtils.releaseSDK();
		}
/*		catch (BEID_Exception e)
		{
			logger.info("[Catch] BEID_Exception:" + e.GetError());
			logger.error(e);
		}*/
/*		catch (Exception e)
		{
			logger.info("[Catch] Exception:" + e.getMessage());
			logger.error(e);
		}
	
		return cardData;
	}
	
	public static byte[] getPicture() {

		String osName = System.getProperty("os.name");
		
		byte[] bytes = null;

		if ( -1 != osName.indexOf("Windows") )
		{
			logger.info("[Info]  Windows system!!");
			System.loadLibrary("beid35libJava_Wrapper");
		}
		else
		{
			System.loadLibrary("beidlibJava_Wrapper");
		}

		try
		{
			BEID_ReaderSet.initSDK();
			String readerName = scanReaders();
			if (readerName != null) {
				BEID_ReaderContext readerContext = BEID_ReaderSet.instance().getReaderByName( readerName );
				BEID_EIDCard card = readerContext.getEIDCard();
				bytes = getPicture(card);
			}
		}
		catch( BEID_Exception e)
		{
			logger.error("[Catch] BEID_Exception:" + e.GetError());
			logger.error(e);
		}
		catch( Exception e)
		{
			logger.error("[Catch] Exception:" + e.getMessage());
			logger.error(e);
		}

		try
		{
			BEID_ReaderSet.releaseSDK();
		}
		catch (BEID_Exception e)
		{
			logger.error("[Catch] BEID_Exception:" + e.GetError());
			logger.error(e);
		}
		catch (Exception e)
		{
			logger.error("[Catch] Exception:" + e.getMessage());
			logger.error(e);
		}
	
		return bytes;
	}
	
	private static String getDateStringFromEid(String sDate) {
		boolean b = true;
		while (b) {
			if (sDate.contains("  ")) {
				sDate = sDate.replace("  ", " ");
			} else {
				b = false;
			}
		}
		String[] aDate = sDate.split(" ");
		String day = aDate[0];
		day = day.trim();
		String year = aDate[2];
		year = year.trim();
		String month = aDate[1];
		month = month.trim();
		if (month.contains("JA")) month = "01";
		if (month.contains("FE")) month = "02";
		if (month.contains("MAA") || (month.contains("MAR"))) month = "03";
		if (month.contains("AP") || month.contains("AV")) month = "04";
		if (month.contains("ME") || month.contains("MAI")) month = "05";
		if (month.contains("JUN") || month.contains("JUIN")) month = "06";
		if (month.contains("JUL") || month.contains("JUIL")) month = "07";
		if (month.contains("AU") || month.contains("AO")) month = "08";
		if (month.contains("SE")) month = "09";
		if (month.contains("OK") || month.contains("OC")) month = "10";
		if (month.contains("NO")) month = "11";
		if (month.contains("DE")) month = "12";
		return year + "-" + month + "-" + day;
	}*/
}
