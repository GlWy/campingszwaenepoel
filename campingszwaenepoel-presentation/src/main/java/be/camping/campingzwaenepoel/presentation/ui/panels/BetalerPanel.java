package be.camping.campingzwaenepoel.presentation.ui.panels;

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.common.enums.ContactgegevenTypeEnum;
import be.camping.campingzwaenepoel.common.enums.GebruikerEnum;
import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;
import be.camping.campingzwaenepoel.common.enums.TaalEnum;
import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.autocomplete.Java2sAutoComboBox;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDateComponentFactory;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDatePicker;
import be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface;
import be.camping.campingzwaenepoel.service.BadgeService;
import be.camping.campingzwaenepoel.service.DTOFactory;
import be.camping.campingzwaenepoel.service.PersoonService;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.transfer.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class BetalerPanel extends JPanel implements PanelInterface, ActionListener {

    @Autowired
    private StandplaatsService standplaatsService;

    private static final long serialVersionUID = 1L;

	private JPanel panelHead = null;
	private JLabel lblNr1 = null;
	private JLabel lblNr2 = null;
	private JLabel lblOpmerking = null;
	private JTextArea txtOpmerking = null;
	private JPanel panelReglement = null;
	private JLabel lblReglUitleg = null;
	private JLabel lblReglGetekend = null;
	private JLabel lblBareelUitleg = null;
	private JLabel lblBareelGetekend = null;
	private final Font font = new Font("SansSerif", Font.BOLD, 18);
	private JPanel panelTaal = null;
	private JRadioButton rbtnNederlands = null;
	private JRadioButton rbtnFrans = null;
	private JPanel panelMan = null;
	private JPanel panelVrouw = null;
	Insets insets = new Insets(10, 0, 0, 10);
	Insets insetsBorder = new Insets(10, 20, 0, 5);
	private JButton btnOpslaan = null;

	private JPanel panelCenter = null;
	private JPanel panelContact = null;

	private JDatePicker reglementUitlegDate = null;
	private JDatePicker reglementGetekendDate = null;
	private JDatePicker bareelUitlegDate = null;
	private JDatePicker bareelGetekendDate = null;

	private JButton jBtnResetReglementUitlegDate;
	private JButton jBtnResetReglementGetekendDate;
	private JButton jBtnResetBareelUitlegDate;
	private JButton jBtnResetBareelGetekendDate;
	private final Dimension dimButton = new Dimension(20, 20); // @jve:decl-index=0:

	private JComboBox namesUitlegBar = null;
	private JComboBox namesUitlegRegl = null;
	private JComboBox namesGetekBar = null;
	private JComboBox namesGetekRegl = null;

	private JTextField jTxtBadgenummer = null;

	private final Dimension txtFieldDim = new Dimension(200, 30);

	private Java2sAutoComboBox jComboBoxBetaler;
	private Java2sAutoComboBox jComboBoxEchtgenote;

	private Controller controller;
	@Autowired
	private PersoonService persoonService;
	@Autowired
	private BadgeService badgeService;

	private BetalerDTO betalerDTO;

	private DateListener dateListener;

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public PersoonService getPersoonService() {
		return persoonService;
	}

	public void setPersoonService(PersoonService persoonService) {
		this.persoonService = persoonService;
	}

	public BadgeService getBadgeService() {
		return badgeService;
	}

	public void setBadgeService(BadgeService badgeService) {
		this.badgeService = badgeService;
	}

	public StandplaatsDTO getStandplaatsDTO() {
		return getController().getStandplaatsDTO();
	}

	public List<String> getList() {
		List<String> list = getController().getPersonen();
		if (list == null) {
			list = getPersoonService().getPersoonNamen();
			getController().setPersonen(list);
		}
		return list;
	}

	public void updateList() {
		getJCboBetaler().setDataList(getList());
		getJCboEchtgenote().setDataList(getList());
	}

	@Override
	public boolean checkData() {
		boolean ret = true;

		if (StringUtils.isEmpty(getJTxtBadgenummer().getText()) || !validateBadge(getJTxtBadgenummer().getText())) {
			ret = false;
		}

		if (namesGetekBar.getSelectedIndex() == 0) {
			ret = false;
		}
		if (namesUitlegBar.getSelectedIndex() == 0) {
			ret = false;
		}
		if (namesGetekRegl.getSelectedIndex() == 0) {
			ret = false;
		}
		if (namesUitlegRegl.getSelectedIndex() == 0) {
			ret = false;
		}

		if (getBareelGetekendDateChooser().getJFormattedTextField().getText().equals("")) {
			ret = false;
		}
		if (getBareelUitlegDateChooser().getJFormattedTextField().getText().equals("")) {
			ret = false;
		}
		if (getReglementGetekendDateChooser().getJFormattedTextField().getText().equals("")) {
			ret = false;
		}
		if (getReglementUitlegDateChooser().getJFormattedTextField().getText().equals("")) {
			ret = false;
		}
		if (StringUtils.isEmpty(getBareelUitlegDateChooser().getJFormattedTextField().getText())) {
			ret = false;
		}
		if (StringUtils.isEmpty(getBareelGetekendDateChooser().getJFormattedTextField().getText())) {
			ret = false;
		}
		if (StringUtils.isEmpty(getReglementUitlegDateChooser().getJFormattedTextField().getText())) {
			ret = false;
		}
		if (StringUtils.isEmpty(getReglementGetekendDateChooser().getJFormattedTextField().getText())) {
			ret = false;
		}
		return ret;
	}

	@Override
	public boolean checkDataChanged() {
		boolean ret = false;
		BetalerDTO betaler = getBetalerDTO();
		if (betaler != null) {
			if (getNamesUitlegBar().getSelectedIndex() != betaler.getBareelUitlegNaam()) {
				ret = true;
			}
			if (getNamesUitlegRegl().getSelectedIndex() != betaler.getReglementUitlegNaam()) {
				ret = true;
			}
			if (getNamesGetekBar().getSelectedIndex() != betaler.getBareelGetekendNaam()) {
				ret = true;
			}
			if (getNamesGetekRegl().getSelectedIndex() != betaler.getReglementGetekendNaam()) {
				ret = true;
			}

			String s1 = (getReglementUitlegDateChooser().getJFormattedTextField().getText() == null) ? ""
					: getReglementUitlegDateChooser().getJFormattedTextField().getText();
			String s2 = (betaler.getReglementUitlegDatum() == null) ? "" : betaler.getReglementUitlegDatum().toString();
			if ((StringUtils.isEmpty(s1) && !StringUtils.isEmpty(s2))
					|| (StringUtils.isEmpty(s2) && !StringUtils.isEmpty(s1))
					|| (betaler.getReglementUitlegDatum() == null && !StringUtils.isEmpty(s2))) {
				ret = true;
			} else if (!StringUtils.isEmpty(s1)) {
				Calendar calChooser = Calendar.getInstance();
				String sDate = getReglementUitlegDateChooser().getModel().getDay() + "-"
						+ (getReglementUitlegDateChooser().getModel().getMonth() + 1) + "-"
						+ getReglementUitlegDateChooser().getModel().getYear();
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd-MM-yyyy");
				Date date;
				try {
					date = formatter.parse(sDate);
					calChooser.setTime(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					System.err.println("date not correctly parsed");
				}
				Calendar calAdmin = Calendar.getInstance();
				calAdmin.setTime(betaler.getReglementUitlegDatum());
				int jaar1 = calAdmin.get(Calendar.YEAR);
				int jaar2 = calChooser.get(Calendar.YEAR);
				int maand1 = calAdmin.get(Calendar.MONTH);
				int maand2 = calChooser.get(Calendar.MONTH);
				int dag1 = calAdmin.get(Calendar.DATE);
				int dag2 = calChooser.get(Calendar.DATE);
				if (jaar1 != jaar2 || maand1 != maand2 || dag1 != dag2) {
					ret = true;
				}
			}

			s1 = (getReglementGetekendDateChooser().getJFormattedTextField().getText() == null) ? ""
					: getReglementGetekendDateChooser().getJFormattedTextField().getText();
			s2 = (betaler.getReglementGetekendDatum() == null) ? "" : betaler.getReglementGetekendDatum().toString();
			if ((StringUtils.isEmpty(s1) && !StringUtils.isEmpty(s2))
					|| (StringUtils.isEmpty(s2) && !StringUtils.isEmpty(s1))
					|| (betaler.getReglementGetekendDatum() == null && !StringUtils.isEmpty(s2))) {
				ret = true;
			} else if (!StringUtils.isEmpty(s1)) {
				Calendar calChooser = Calendar.getInstance();
				String sDate = getReglementGetekendDateChooser().getModel().getDay() + "-"
						+ (getReglementGetekendDateChooser().getModel().getMonth() + 1) + "-"
						+ getReglementGetekendDateChooser().getModel().getYear();
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd-MM-yyyy");
				Date date;
				try {
					date = formatter.parse(sDate);
					calChooser.setTime(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					System.err.println("date not correctly parsed");
				}
				Calendar calAdmin = Calendar.getInstance();
				calAdmin.setTime(betaler.getReglementGetekendDatum());
				int jaar1 = calAdmin.get(Calendar.YEAR);
				int jaar2 = calChooser.get(Calendar.YEAR);
				int maand1 = calAdmin.get(Calendar.MONTH);
				int maand2 = calChooser.get(Calendar.MONTH);
				int dag1 = calAdmin.get(Calendar.DATE);
				int dag2 = calChooser.get(Calendar.DATE);
				if (jaar1 != jaar2 || maand1 != maand2 || dag1 != dag2) {
					ret = true;
				}
			}

			s1 = (getBareelUitlegDateChooser().getJFormattedTextField().getText() == null) ? ""
					: getBareelUitlegDateChooser().getJFormattedTextField().getText();
			s2 = (betaler.getBareelUitlegDatum() == null) ? "" : betaler.getBareelUitlegDatum().toString();
			if ((StringUtils.isEmpty(s1) && !StringUtils.isEmpty(s2))
					|| (StringUtils.isEmpty(s2) && !StringUtils.isEmpty(s1))
					|| (betaler.getBareelUitlegDatum() == null && !StringUtils.isEmpty(s2))) {
				ret = true;
			} else if (!StringUtils.isEmpty(s1)) {
				Calendar calChooser = Calendar.getInstance();
				String sDate = getBareelUitlegDateChooser().getModel().getDay() + "-"
						+ (getBareelUitlegDateChooser().getModel().getMonth() + 1) + "-"
						+ getBareelUitlegDateChooser().getModel().getYear();
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd-MM-yyyy");
				Date date;
				try {
					date = formatter.parse(sDate);
					calChooser.setTime(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					System.err.println("date not correctly parsed");
				}
				Calendar calAdmin = Calendar.getInstance();
				calAdmin.setTime(betaler.getBareelUitlegDatum());
				int jaar1 = calAdmin.get(Calendar.YEAR);
				int jaar2 = calChooser.get(Calendar.YEAR);
				int maand1 = calAdmin.get(Calendar.MONTH);
				int maand2 = calChooser.get(Calendar.MONTH);
				int dag1 = calAdmin.get(Calendar.DATE);
				int dag2 = calChooser.get(Calendar.DATE);
				if (jaar1 != jaar2 || maand1 != maand2 || dag1 != dag2) {
					ret = true;
				}
			}

			s1 = (getBareelGetekendDateChooser().getJFormattedTextField().getText() == null) ? ""
					: getBareelGetekendDateChooser().getJFormattedTextField().getText();
			s2 = (betaler.getBareelGetekendDatum() == null) ? "" : betaler.getBareelGetekendDatum().toString();
			if ((StringUtils.isEmpty(s1) && !StringUtils.isEmpty(s2))
					|| (StringUtils.isEmpty(s2) && !StringUtils.isEmpty(s1))
					|| (betaler.getBareelGetekendDatum() == null && !StringUtils.isEmpty(s2))) {
				ret = true;
			} else if (!StringUtils.isEmpty(s1)) {
				Calendar calChooser = Calendar.getInstance();
				String sDate = getBareelGetekendDateChooser().getModel().getDay() + "-"
						+ (getBareelGetekendDateChooser().getModel().getMonth() + 1) + "-"
						+ getBareelGetekendDateChooser().getModel().getYear();
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd-MM-yyyy");
				Date date;
				try {
					date = formatter.parse(sDate);
					calChooser.setTime(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					System.err.println("date not correctly parsed");
				}

				Calendar calAdmin = Calendar.getInstance();
				calAdmin.setTime(betaler.getBareelGetekendDatum());
				int jaar1 = calAdmin.get(Calendar.YEAR);
				int jaar2 = calChooser.get(Calendar.YEAR);
				int maand1 = calAdmin.get(Calendar.MONTH);
				int maand2 = calChooser.get(Calendar.MONTH);
				int dag1 = calAdmin.get(Calendar.DATE);
				int dag2 = calChooser.get(Calendar.DATE);
				if (jaar1 != jaar2 || maand1 != maand2 || dag1 != dag2) {
					ret = true;
				}
			}

			s1 = (getTxtOpmerking().getText() == null) ? "" : getTxtOpmerking().getText();
			s2 = (betaler.getOpmerking() == null) ? "" : betaler.getOpmerking();
			if (!s1.equals(s2)) {
				ret = true;
			}
		} else {
			if (getJCboBetaler().getSelectedIndex() != 0) {
				ret = true;
			}
			if (getJCboEchtgenote().getSelectedIndex() != 0) {
				ret = true;
			}
		}

		PersoonDTO hoofdBetaler = null;
		PersoonDTO partnerBetaler = null;

		if (betaler != null) {
			hoofdBetaler = betaler.getHoofdbetaler();
			partnerBetaler = betaler.getBetaler();
		}

		if (hoofdBetaler != null) {
			String naam = hoofdBetaler.getNaam() + " " + hoofdBetaler.getVoornaam();
			if (!naam.equals(getJCboBetaler().getSelectedItem())) {
				ret = true;
			}
			TaalEnum taal = null;
			if (rbtnNederlands.isSelected()) {
				taal = TaalEnum.NL;
			} else if (rbtnFrans.isSelected()) {
				taal = TaalEnum.FR;
			}
			if (!hoofdBetaler.getTaal().equals(taal)) {
				ret = true;
			}
		} else if (getJCboBetaler().getSelectedIndex() != 0) {
			ret = true;
		}

		if (partnerBetaler != null) {
			String naam = partnerBetaler.getNaam() + " " + partnerBetaler.getVoornaam();
			if (!naam.equals(getJCboEchtgenote().getSelectedItem())) {
				ret = true;
			}
		} else if (getJCboEchtgenote().getSelectedIndex() != 0) {
			ret = true;
		}

		if (!ret) {
			ret = checkBadgeChanged();
		}

		return ret;
	}

	private boolean checkBadgeChanged() {
		boolean ret = false;
		if (getStandplaatsDTO().getBadge() == null && !StringUtils.isEmpty(getJTxtBadgenummer().getText())) {
			ret = true;
		}
		if (getStandplaatsDTO().getBadge() != null) {
			String s1 = getStandplaatsDTO().getBadge().getBadgenummer();
			String s2 = getJTxtBadgenummer().getText();
			s2 = s2.replace(" ", "");
			if (!s1.equals(s2)) {
				ret = true;
			}
		}
		return ret;
	}

    /**
     * @description		Deze methode overloopt de betaler objecten van deze standplaats en
     * 					zal de recentste retourneren.
     * @return			BetalerDTO: recentste betaler object
     */
    public BetalerDTO getHuidigeBetaler() {
        BetalerDTO betaler = null;
        Calendar cal = null;
        for (BetalerDTO betalerDTO : getStandplaatsDTO().getBetalers()) {
            if (cal != null) {
                Calendar tmpCal = Calendar.getInstance();
                tmpCal.setTime(betalerDTO.getDatumVan());
                if (tmpCal.after(cal)) {
                    cal = tmpCal;
                    betaler = betalerDTO;
                }
            } else {
                cal = Calendar.getInstance();
                cal.setTime(betalerDTO.getDatumVan());
                betaler = betalerDTO;
            }
        }
        return betaler;
    }

    /**
	 * TODO : deze methode is nog niet ok, geStandplaatsDTO() is hier null, moet nog opgehaald worden.
	 */
	@Override
	public boolean checkDataForParent() {
		boolean ret = true;
		BetalerDTO betaler = getHuidigeBetaler();
		if (betaler == null || betaler.getBareelGetekendNaam() == GebruikerEnum.NIEMAND.value()
				|| betaler.getBareelUitlegNaam() == GebruikerEnum.NIEMAND.value()
				|| betaler.getReglementGetekendNaam() == GebruikerEnum.NIEMAND.value()
				|| betaler.getReglementUitlegNaam() == GebruikerEnum.NIEMAND.value()
				|| betaler.getBareelGetekendDatum() == null || betaler.getBareelUitlegDatum() == null
				|| betaler.getReglementGetekendDatum() == null || betaler.getReglementUitlegDatum() == null
				|| betaler.getHoofdbetaler() == null || getStandplaatsDTO().getBadge() == null) {
			ret = false;
		}
		return ret;
	}

	@Override
	public Object getDataFromGUI() {
		PersoonDTO hoofdbetaler = null;
		PersoonDTO partnerBetaler = null;

		// Bepaal de hoofdbetaler. Bepaal de naam en voornaam uit de combobox en gebruik vervolgens de methode
		// zoekPersonen.
		if (!StringUtils.isEmpty((String) getJCboBetaler().getSelectedItem())) {
			String[] namen = ((String) getJCboBetaler().getSelectedItem()).split(" ");
			String achternaam = "";
			if (namen.length >= 2) {
				for (int i = 0; i < namen.length - 1; i++) {
					if (i > 0) {
						achternaam += " ";
					}
					achternaam += namen[i];
				}
				hoofdbetaler = zoekPersonen(achternaam, namen[namen.length - 1]);
			}
		}

		// Bepaal de betaler. Bepaal de naam en voornaam uit de combobox en gebruik vervolgens de methode zoekPersonen.
		if (!StringUtils.isEmpty((String) getJCboEchtgenote().getSelectedItem())) {
			String[] namen = ((String) getJCboEchtgenote().getSelectedItem()).split(" ");
			String achternaam = "";
			if (namen.length >= 2) {
				for (int i = 0; i < namen.length - 1; i++) {
					if (i > 0) {
						achternaam += " ";
					}
					achternaam += namen[i];
				}
				partnerBetaler = zoekPersonen(achternaam, namen[namen.length - 1]);
			}
		}

		BetalerDTO betaler = getHuidigeBetaler();

		if (hoofdbetaler == null && partnerBetaler == null) {
			if (betaler != null) {
				removeBetaler(betaler.getId());
			}
		} else {
			if (betaler == null) {
				betaler = DTOFactory.getBetalerDTO();
				betaler.setDatumVan(Calendar.getInstance().getTime());
			}
			// Update de betaler(s) en taal.
			if (getRbtnFrans().isSelected()) {
				if (hoofdbetaler != null) {
					hoofdbetaler.setTaal(TaalEnum.FR);
				}
				if (partnerBetaler != null) {
					partnerBetaler.setTaal(TaalEnum.FR);
				}
			} else if (getRbtnNederlands().isSelected()) {
				if (hoofdbetaler != null) {
					hoofdbetaler.setTaal(TaalEnum.NL);
				}
				if (partnerBetaler != null) {
					partnerBetaler.setTaal(TaalEnum.NL);
				}
			}

			// update de inschrijvingDTO.
			betaler.setHoofdbetaler(hoofdbetaler);
			betaler.setBetaler(partnerBetaler);

			betaler.setOpmerking(getTxtOpmerking().getText());
			// update de AdministratieDTO
			updateAdministratie(betaler);
		}

		return betaler;
	}

	private void removeBetaler(int betalerId) {
		for (BetalerDTO betaler : getStandplaatsDTO().getBetalers()) {
			if (betaler.getId() == betalerId) {
				getStandplaatsDTO().getBetalers().remove(betaler);
			}
		}
	}

	private void updateAdministratie(BetalerDTO betaler) {
		betaler.setBareelGetekendNaam(namesGetekBar.getSelectedIndex());
		betaler.setBareelUitlegNaam(namesUitlegBar.getSelectedIndex());
		betaler.setReglementGetekendNaam(namesGetekRegl.getSelectedIndex());
		betaler.setReglementUitlegNaam(namesUitlegRegl.getSelectedIndex());

		// JSpinner.DefaultEditor ed = (JSpinner.DefaultEditor)getBareelGetekendDateChooser().getSpinner().getEditor();
		// if (ed.getTextField().getText().equals("")) {
		if (getBareelGetekendDateChooser().getJFormattedTextField().getText().equals("")) {
			betaler.setBareelGetekendDatum(null);
		} else {
			// administratieDTO.setBareelGetekendDatum(getBareelGetekendDateChooser().getDate());
			Calendar cal = Calendar.getInstance();
			cal.set(getBareelGetekendDateChooser().getModel().getYear(), getBareelGetekendDateChooser().getModel()
					.getMonth(), getBareelGetekendDateChooser().getModel().getDay());
			betaler.setBareelGetekendDatum(cal.getTime());
		}

		// ed = (JSpinner.DefaultEditor)getBareelUitlegDateChooser().getSpinner().getEditor();
		// if (ed.getTextField().getText().equals("")) {
		if (getBareelUitlegDateChooser().getJFormattedTextField().getText().equals("")) {
			betaler.setBareelUitlegDatum(null);
		} else {
			// administratieDTO.setBareelUitlegDatum(getBareelUitlegDateChooser().getDate());
			Calendar cal = Calendar.getInstance();
			cal.set(getBareelUitlegDateChooser().getModel().getYear(), getBareelUitlegDateChooser().getModel()
					.getMonth(), getBareelUitlegDateChooser().getModel().getDay());
			betaler.setBareelUitlegDatum(cal.getTime());
		}

		// ed = (JSpinner.DefaultEditor)getReglementGetekendDateChooser().getSpinner().getEditor();
		// if (ed.getTextField().getText().equals("")) {
		if (getReglementGetekendDateChooser().getJFormattedTextField().getText().equals("")) {
			betaler.setReglementGetekendDatum(null);
		} else {
			// administratieDTO.setReglementGetekendDatum(getReglementGetekendDateChooser().getDate());
			Calendar cal = Calendar.getInstance();
			cal.set(getReglementGetekendDateChooser().getModel().getYear(), getReglementGetekendDateChooser()
					.getModel().getMonth(), getReglementGetekendDateChooser().getModel().getDay());
			betaler.setReglementGetekendDatum(cal.getTime());
		}

		// ed = (JSpinner.DefaultEditor)getReglementUitlegDateChooser().getSpinner().getEditor();
		// if (ed.getTextField().getText().equals("")) {
		if (getReglementUitlegDateChooser().getJFormattedTextField().getText().equals("")) {
			betaler.setReglementUitlegDatum(null);
		} else {
			// administratieDTO.setReglementUitlegDatum(getReglementUitlegDateChooser().getDate());
			Calendar cal = Calendar.getInstance();
			cal.set(getReglementUitlegDateChooser().getModel().getYear(), getReglementUitlegDateChooser().getModel()
					.getMonth(), getReglementUitlegDateChooser().getModel().getDay());
			betaler.setReglementUitlegDatum(cal.getTime());
		}

		if (StringUtils.isEmpty(txtOpmerking.getText())) {
			betaler.setOpmerking("");
		} else {
			betaler.setOpmerking(txtOpmerking.getText());
		}
	}

	private boolean validateBadge(String badgenummer) {
		boolean ret = false;
		badgenummer = badgenummer.replace(" ", "");
		badgenummer = badgenummer.trim();
		if (badgenummer.contains("H") && badgenummer.length() == 4)
			ret = true;
		else if (!badgenummer.contains("H") && badgenummer.length() == 8) {
			try {
				Integer.parseInt(badgenummer);
				ret = true;
			} catch (Exception e) {
			}
		}
		return ret;
	}

	/**
	 * TODO: bug: zelfde objecten in betaler als in inschrijving
	 */
	@Override
	public void save() {
		BetalerDTO betaler = (BetalerDTO) getDataFromGUI();
		if (betaler.getId() == null || betaler.getId() == 0) {
			getStandplaatsDTO().getBetalers().add(betaler);
		}
		if (checkBadgeChanged()) {
			String currentBadgeNumber = (getStandplaatsDTO().getBadge() != null) ? getStandplaatsDTO().getBadge().getBadgenummer() : "";
			String newBadgeNumber = getJTxtBadgenummer().getText();
			String sBadge = newBadgeNumber.replace(" ", "");
			sBadge = sBadge.trim();
			if (sBadge.length() > 0 && !validateBadge(sBadge)) {
				String alert = "Gelieve een geldig badgenummer in te geven";
				JOptionPane.showMessageDialog(null, alert, "Badge nummer niet correct", JOptionPane.ERROR_MESSAGE);
				getJTxtBadgenummer().requestFocusInWindow();
				return;
			}
			if (sBadge.length() > 0) {
				Optional<BadgeDTO> badgeOptional = getBadgeService().findBadge(sBadge);
				if (!badgeOptional.isPresent()) {
					Object[] arr = { "Dit badgenummer bestaat nog niet. Wenst u die aan te maken?" };
					int ret = JOptionPane.showConfirmDialog(null, arr, "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (ret == 0) {
						BadgeDTO badge = new BadgeDTO();
						badge.setBadgenummer(sBadge);
						if (!sBadge.contains("H")) {
							badge.setBadgetype(SoortHuurderEnum.VAST);
							if (sBadge.length() == 8) {
								String tmpBadge = sBadge.substring(0, 3) + " " + sBadge.substring(3, 4) + " "
										+ sBadge.substring(4, 6) + " " + sBadge.substring(6);
								getJTxtBadgenummer().setText(tmpBadge);
							}
						} else {
							badge.setBadgetype(SoortHuurderEnum.LOS);
						}
						badge = getBadgeService().createBadge(badge);
						badgeOptional = Optional.of(badge);
						getStandplaatsDTO().setBadge(badgeOptional.orElse(null));
						getJTxtBadgenummer().setText(newBadgeNumber);
					} else {
						getJTxtBadgenummer().setText(currentBadgeNumber);
					}
				}
			} else {
				getStandplaatsDTO().setBadge(null);
			}
		}
		getController().getHeader().setStandplaatsDTO(standplaatsService.storeStandplaats(getStandplaatsDTO()));
		betaler = getHuidigeBetaler();
		setBetalerDTO(betaler);
		PersoonDTO hoofdbetaler = betaler.getHoofdbetaler();
		PersoonDTO betalerPartner = betaler.getBetaler();
		String betaler1 = "";
		String betaler2 = "";
		if (hoofdbetaler != null) {
			betaler1 = hoofdbetaler.getNaam() + " " + hoofdbetaler.getVoornaam();
		}
		if (betalerPartner != null) {
			betaler2 = betalerPartner.getNaam() + " " + betalerPartner.getVoornaam();
		}
		getController().setBetalersInHeader(betaler1, betaler2);
		// if ("Hippodroom".equals(getController().getHeader().getCamping()) ||
		// "Capri".equals(getController().getHeader().getCamping())) {
		getController().getPersoonPanel().checkDataChanged(true);
		if (hoofdbetaler != null) {
			getController().setPersoonInPersoonPanel(hoofdbetaler);
		} else if (betalerPartner != null) {
			getController().setPersoonInPersoonPanel(betalerPartner);
		}
		// }
	}

	@Override
	public void setDataInGUI() {
		wis();
		getJCboBetaler().setDataList(getList());
		getJCboEchtgenote().setDataList(getList());
		setBetalerDTO(getHuidigeBetaler());
		if (getBetalerDTO() != null) {
			PersoonDTO hoofdbetaler = getBetalerDTO().getHoofdbetaler();
			PersoonDTO partner = getBetalerDTO().getBetaler();

			if (hoofdbetaler != null) {
				String naam = hoofdbetaler.getNaam() + " " + hoofdbetaler.getVoornaam();
				getJCboBetaler().setSelectedItem(naam);
			}

			if (partner != null) {
				String naam = partner.getNaam() + " " + partner.getVoornaam();
				getJCboEchtgenote().setSelectedItem(naam);
			}

			if (hoofdbetaler != null && hoofdbetaler.getTaal() != null) {
				if (hoofdbetaler.getTaal().equals(TaalEnum.NL)) {
					rbtnNederlands.doClick();
				} else {
					rbtnFrans.doClick();
				}
			} else if (partner != null && partner.getTaal() != null) {
				if (partner.getTaal().equals(TaalEnum.NL)) {
					rbtnNederlands.doClick();
				} else {
					rbtnFrans.doClick();
				}
			}

			getTxtOpmerking().setText(getBetalerDTO().getOpmerking());

			setAdministratieData();
			setContactData();

			String badgenummer = "";
			if (getStandplaatsDTO().getBadge() != null) {
				String tmpBadgenummer = getStandplaatsDTO().getBadge().getBadgenummer();
				if (tmpBadgenummer.length() == 8) {
					badgenummer = tmpBadgenummer.substring(0, 3) + " " + tmpBadgenummer.substring(3, 4) + " "
							+ tmpBadgenummer.substring(4, 6) + " " + tmpBadgenummer.substring(6);
				} else {
					badgenummer = tmpBadgenummer;
				}
			}
			getJTxtBadgenummer().setText(badgenummer);
		}
	}

	private void wis() {
		getJCboBetaler().setSelectedIndex(0);
		getJCboEchtgenote().setSelectedIndex(0);
		getNamesGetekBar().setSelectedIndex(0);
		getNamesGetekRegl().setSelectedIndex(0);
		getNamesUitlegBar().setSelectedIndex(0);
		getNamesUitlegRegl().setSelectedIndex(0);
		getBareelGetekendDateChooser().getJFormattedTextField().setText("");
		getBareelUitlegDateChooser().getJFormattedTextField().setText("");
		getReglementGetekendDateChooser().getJFormattedTextField().setText("");
		getReglementUitlegDateChooser().getJFormattedTextField().setText("");
		getRbtnFrans().setSelected(false);
		getRbtnNederlands().setSelected(false);
		getTxtOpmerking().setText("");
		setBetalerDTO(null);
		getJTxtBadgenummer().setText("");
	}

	@Override
	public void setRemarksInGui() {
		boolean b = checkData();
		controller.updateDataForPanel(b, 2);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initComponents() {
		// this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,
		// Toolkit.getDefaultToolkit().getScreenSize().height-100);
		this.setLayout(new BorderLayout());
		dateListener = new DateListener();
		this.add(getPanelHead(), BorderLayout.NORTH);
		this.add(getPanelCenter(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes panelHead
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelHead() {
		if (panelHead == null) {

			panelHead = new JPanel();
			GridBagLayout gridBagLayout = new GridBagLayout();
			// int[] columnWidths = {30, 200, 80, 300, 100};
			// gridBagLayout.columnWidths = columnWidths;
			panelHead.setLayout(gridBagLayout);
			panelHead.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, 150);
			panelHead.setBorder(new LineBorder(Color.BLACK, 1));

			// label naam man
			GridBagConstraints gridBagConstraintsMan = new GridBagConstraints();
			gridBagConstraintsMan.gridx = 0;
			gridBagConstraintsMan.gridy = 0;
			gridBagConstraintsMan.fill = GridBagConstraints.BOTH;

			// label naam vrouw
			GridBagConstraints gridBagConstraintsVrouw = new GridBagConstraints();
			gridBagConstraintsVrouw.gridx = 0;
			gridBagConstraintsVrouw.gridy = 1;
			gridBagConstraintsVrouw.fill = GridBagConstraints.BOTH;

			// label opmerking
			GridBagConstraints gridBagConstraintsOpmerking = new GridBagConstraints();
			gridBagConstraintsOpmerking.gridx = 1;
			gridBagConstraintsOpmerking.gridy = 0;
			lblOpmerking = new JLabel();
			lblOpmerking.setText("   Opmerking:  ");
			lblOpmerking.setFont(font);
			lblOpmerking.setHorizontalAlignment(SwingConstants.CENTER);
			lblOpmerking.setHorizontalTextPosition(SwingConstants.CENTER);
			lblOpmerking.setBorder(new LineBorder(Color.BLACK, 1));
			gridBagConstraintsOpmerking.gridheight = 2;
			gridBagConstraintsOpmerking.fill = GridBagConstraints.BOTH;
			panelHead.add(lblOpmerking, gridBagConstraintsOpmerking);

			// tekst opmerking
			GridBagConstraints gridBagConstraintsOpmTxt = new GridBagConstraints();
			gridBagConstraintsOpmTxt.fill = GridBagConstraints.BOTH;
			gridBagConstraintsOpmTxt.gridy = 0;
			gridBagConstraintsOpmTxt.gridheight = 2;
			gridBagConstraintsOpmTxt.weightx = 1.0;
			gridBagConstraintsOpmTxt.gridx = 2;
			panelHead.add(getTxtOpmerking(), gridBagConstraintsOpmTxt);

			// radiobutton Nederlands - Frans
			GridBagConstraints gridBagConstraintsTaal = new GridBagConstraints();
			gridBagConstraintsTaal.gridx = 3;
			gridBagConstraintsTaal.gridheight = 2;
			gridBagConstraintsTaal.fill = GridBagConstraints.BOTH;
			gridBagConstraintsTaal.gridy = 0;
			panelHead.add(getPanelMan(), gridBagConstraintsMan);
			panelHead.add(getPanelVrouw(), gridBagConstraintsVrouw);
			panelHead.add(getPanelTaal(), gridBagConstraintsTaal);

		}
		return panelHead;
	}

	/**
	 * This method initializes panelReglement
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelReglement() {
		if (panelReglement == null) {

			Insets insets = new Insets(5, 5, 10, 30);
			Insets insetsDatum = new Insets(5, 50, 10, 30);

			panelReglement = new JPanel();
			GridBagLayout gridBagLayout = new GridBagLayout();
			int[] columnWidths = { 200, 200, 100 };
			gridBagLayout.columnWidths = columnWidths;
			panelReglement.setLayout(gridBagLayout);
			// panelReglement.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, 400);

			// label Reglement Uitleg
			GridBagConstraints gridBagConstraintsReglUitleg = new GridBagConstraints();
			gridBagConstraintsReglUitleg.gridx = 0;
			gridBagConstraintsReglUitleg.gridy = 0;
			gridBagConstraintsReglUitleg.anchor = GridBagConstraints.EAST;
			gridBagConstraintsReglUitleg.insets = insets;
			lblReglUitleg = new JLabel();
			lblReglUitleg.setText("Reglement uitleg = ");
			panelReglement.add(lblReglUitleg, gridBagConstraintsReglUitleg);

			// label reglement Getekend
			GridBagConstraints gridBagConstraintsRegGetekend = new GridBagConstraints();
			gridBagConstraintsRegGetekend.gridx = 0;
			gridBagConstraintsRegGetekend.gridy = 1;
			gridBagConstraintsRegGetekend.anchor = GridBagConstraints.EAST;
			gridBagConstraintsRegGetekend.insets = insets;
			lblReglGetekend = new JLabel();
			lblReglGetekend.setText("Reglement getekend = ");
			panelReglement.add(lblReglGetekend, gridBagConstraintsRegGetekend);

			// tekst Reglement uitleg
			GridBagConstraints gridBagConstraintsTxtUitleg = new GridBagConstraints();
			gridBagConstraintsTxtUitleg.gridy = 0;
			gridBagConstraintsTxtUitleg.gridx = 1;
			gridBagConstraintsTxtUitleg.fill = GridBagConstraints.HORIZONTAL;
			namesUitlegRegl = getNamesUitlegRegl();
			panelReglement.add(namesUitlegRegl, gridBagConstraintsTxtUitleg);

			// tekst Reglement Getekend
			GridBagConstraints gridBagConstraintstxtGet = new GridBagConstraints();
			gridBagConstraintstxtGet.gridy = 1;
			gridBagConstraintstxtGet.gridx = 1;
			gridBagConstraintstxtGet.fill = GridBagConstraints.HORIZONTAL;
			namesGetekRegl = getNamesGetekRegl();
			panelReglement.add(namesGetekRegl, gridBagConstraintstxtGet);

			// label datum regelment uitleg
			GridBagConstraints gridBagConstraintsDateUitleg = new GridBagConstraints();
			gridBagConstraintsDateUitleg.gridx = 2;
			gridBagConstraintsDateUitleg.gridy = 0;
			gridBagConstraintsDateUitleg.insets = insetsDatum;
			panelReglement.add((JComponent) getReglementUitlegDateChooser(), gridBagConstraintsDateUitleg);

			// label datum regelment getekend
			GridBagConstraints gridBagConstraintsDatumGet = new GridBagConstraints();
			gridBagConstraintsDatumGet.gridx = 2;
			gridBagConstraintsDatumGet.gridy = 1;
			gridBagConstraintsDatumGet.insets = insetsDatum;
			panelReglement.add((JComponent) getReglementGetekendDateChooser(), gridBagConstraintsDatumGet);

			// label Bareel Uitleg
			GridBagConstraints gridBagConstraintsBareelUitleg = new GridBagConstraints();
			gridBagConstraintsBareelUitleg.gridx = 0;
			gridBagConstraintsBareelUitleg.gridy = 3;
			gridBagConstraintsBareelUitleg.anchor = GridBagConstraints.EAST;
			gridBagConstraintsBareelUitleg.insets = insets;
			lblBareelUitleg = new JLabel();
			lblBareelUitleg.setText("Bareel uitleg = ");
			panelReglement.add(lblBareelUitleg, gridBagConstraintsBareelUitleg);

			// label reglement Getekend
			GridBagConstraints gridBagConstraintsBareelGetekend = new GridBagConstraints();
			gridBagConstraintsBareelGetekend.gridx = 0;
			gridBagConstraintsBareelGetekend.gridy = 4;
			gridBagConstraintsBareelGetekend.anchor = GridBagConstraints.EAST;
			gridBagConstraintsBareelGetekend.insets = insets;
			lblBareelGetekend = new JLabel();
			lblBareelGetekend.setText("Bareel getekend = ");
			panelReglement.add(lblBareelGetekend, gridBagConstraintsBareelGetekend);

			// tekst Reglement uitleg
			GridBagConstraints gridBagConstraintsTxtBareelUitleg = new GridBagConstraints();
			gridBagConstraintsTxtBareelUitleg.gridy = 3;
			gridBagConstraintsTxtBareelUitleg.gridx = 1;
			gridBagConstraintsTxtBareelUitleg.fill = GridBagConstraints.HORIZONTAL;
			namesUitlegBar = getNamesUitlegBar();
			panelReglement.add(namesUitlegBar, gridBagConstraintsTxtBareelUitleg);

			// tekst Reglement Getekend
			GridBagConstraints gridBagConstraintsTxtBareelGet = new GridBagConstraints();
			gridBagConstraintsTxtBareelGet.gridy = 4;
			gridBagConstraintsTxtBareelGet.gridx = 1;
			gridBagConstraintsTxtBareelGet.fill = GridBagConstraints.HORIZONTAL;
			namesGetekBar = getNamesGetekBar();
			panelReglement.add(namesGetekBar, gridBagConstraintsTxtBareelGet);

			// datum regelment uitleg
			GridBagConstraints gridBagConstraintsDateBareelUitleg = new GridBagConstraints();
			gridBagConstraintsDateBareelUitleg.gridx = 2;
			gridBagConstraintsDateBareelUitleg.gridy = 3;
			gridBagConstraintsDateBareelUitleg.insets = insetsDatum;
			panelReglement.add((JComponent) getBareelUitlegDateChooser(), gridBagConstraintsDateBareelUitleg);

			// datum regelment getekend
			GridBagConstraints gridBagConstraintsDatumBareelGet = new GridBagConstraints();
			gridBagConstraintsDatumBareelGet.gridx = 2;
			gridBagConstraintsDatumBareelGet.gridy = 4;
			gridBagConstraintsDatumBareelGet.insets = insetsDatum;
			panelReglement.add((JComponent) getBareelGetekendDateChooser(), gridBagConstraintsDatumBareelGet);

			// reset datum regelment getekend
			GridBagConstraints gridBagConstraintsResetDatumReglUitleg = new GridBagConstraints();
			gridBagConstraintsResetDatumReglUitleg.gridx = 3;
			gridBagConstraintsResetDatumReglUitleg.gridy = 0;
			gridBagConstraintsResetDatumReglUitleg.insets = insetsDatum;
			panelReglement.add(getjBtnResetReglementUitlegDate(), gridBagConstraintsResetDatumReglUitleg);

			// reset datum regelment getekend
			GridBagConstraints gridBagConstraintsResetDatumReglGet = new GridBagConstraints();
			gridBagConstraintsResetDatumReglGet.gridx = 3;
			gridBagConstraintsResetDatumReglGet.gridy = 1;
			gridBagConstraintsResetDatumReglGet.insets = insetsDatum;
			panelReglement.add(getjBtnResetReglementGetekendDate(), gridBagConstraintsResetDatumReglGet);

			// reset datum regelment getekend
			GridBagConstraints gridBagConstraintsResetDatumBareelUitleg = new GridBagConstraints();
			gridBagConstraintsResetDatumBareelUitleg.gridx = 3;
			gridBagConstraintsResetDatumBareelUitleg.gridy = 3;
			gridBagConstraintsResetDatumBareelUitleg.insets = insetsDatum;
			panelReglement.add(getjBtnResetBareelUitlegDate(), gridBagConstraintsResetDatumBareelUitleg);

			// reset datum regelment getekend
			GridBagConstraints gridBagConstraintsResetDatumBareelGet = new GridBagConstraints();
			gridBagConstraintsResetDatumBareelGet.gridx = 3;
			gridBagConstraintsResetDatumBareelGet.gridy = 4;
			gridBagConstraintsResetDatumBareelGet.insets = insetsDatum;
			panelReglement.add(getjBtnResetBareelGetekendDate(), gridBagConstraintsResetDatumBareelGet);

			// badge label
			GridBagConstraints gridBagConstraintsBadgelabel = new GridBagConstraints();
			gridBagConstraintsBadgelabel.gridx = 0;
			gridBagConstraintsBadgelabel.gridy = 5;
			gridBagConstraintsBadgelabel.insets = insets;
			gridBagConstraintsBadgelabel.anchor = GridBagConstraints.EAST;
			JLabel jLblBadge = new JLabel("Badgenummer = ");
			panelReglement.add(jLblBadge, gridBagConstraintsBadgelabel);

			// badge
			GridBagConstraints gridBagConstraintsBadge = new GridBagConstraints();
			gridBagConstraintsBadge.gridx = 1;
			gridBagConstraintsBadge.gridy = 5;
			gridBagConstraintsBadge.insets = insets;
			panelReglement.add(getJTxtBadgenummer(), gridBagConstraintsBadge);

			// btn save
			GridBagConstraints gridBagConstraintsOpslaan = new GridBagConstraints();
			gridBagConstraintsOpslaan.gridx = 0;
			gridBagConstraintsOpslaan.gridy = 6;
			gridBagConstraintsOpslaan.insets = insets;
			panelReglement.add(getBtnSave(), gridBagConstraintsOpslaan);

		}
		return panelReglement;
	}

	/**
	 * This method initializes txtOpmerking
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextArea getTxtOpmerking() {
		if (txtOpmerking == null) {
			txtOpmerking = new JTextArea();
			txtOpmerking.setFont(font);
			txtOpmerking.setBorder(new LineBorder(Color.BLACK, 1));
			txtOpmerking.setLineWrap(true);
			txtOpmerking.setWrapStyleWord(true);
		}
		return txtOpmerking;
	}

	private JPanel getPanelTaal() {
		if (panelTaal == null) {
			panelTaal = new JPanel();
			panelTaal.setLayout(new GridLayout(2, 1));
			panelTaal.setBorder(new LineBorder(Color.BLACK));

			setGeslachtButtons();
			panelTaal.add(getRbtnNederlands());
			panelTaal.add(getRbtnFrans());
		}
		return panelTaal;
	}

	private ButtonGroup setGeslachtButtons() {
		ButtonGroup group = new ButtonGroup();
		group.add(getRbtnNederlands());
		group.add(getRbtnFrans());
		return group;
	}

	/**
	 * This method initializes txtVoornaam
	 * 
	 * @return javax.swing.JTextField
	 */
	private JRadioButton getRbtnNederlands() {
		if (rbtnNederlands == null) {
			rbtnNederlands = new JRadioButton("Nederlands");
			rbtnNederlands.setActionCommand("Nederlands");
			rbtnNederlands.setFont(font);
		}
		return rbtnNederlands;
	}

	/**
	 * This method initializes txtVoornaam
	 * 
	 * @return javax.swing.JTextField
	 */
	private JRadioButton getRbtnFrans() {
		if (rbtnFrans == null) {
			rbtnFrans = new JRadioButton("Frans");
			rbtnFrans.setActionCommand("Frans");
			rbtnFrans.setFont(font);
		}
		return rbtnFrans;
	}

	public Java2sAutoComboBox getJCboBetaler() {
		if (jComboBoxBetaler == null) {
			jComboBoxBetaler = new Java2sAutoComboBox(getList());
			jComboBoxBetaler.setSize(new Dimension(250, 34));
			jComboBoxBetaler.setPreferredSize(new Dimension(250, 34));
		}
		return jComboBoxBetaler;
	}

	public Java2sAutoComboBox getJCboEchtgenote() {
		if (jComboBoxEchtgenote == null) {
			jComboBoxEchtgenote = new Java2sAutoComboBox(getList());
			jComboBoxEchtgenote.setSize(new Dimension(250, 34));
			jComboBoxEchtgenote.setPreferredSize(new Dimension(250, 34));
		}
		return jComboBoxEchtgenote;
	}

	private JPanel getPanelMan() {
		if (panelMan == null) {
			panelMan = new JPanel();
			panelMan.setLayout(new BorderLayout());
			panelMan.setBorder(new LineBorder(Color.BLACK));

			lblNr1 = new JLabel();
			lblNr1.setText("   1   ");
			lblNr1.setFont(font);
			panelMan.add(lblNr1, BorderLayout.WEST);
			panelMan.add(getJCboBetaler(), BorderLayout.CENTER);
		}
		return panelMan;
	}

	private JPanel getPanelVrouw() {
		if (panelVrouw == null) {
			panelVrouw = new JPanel();
			panelVrouw.setLayout(new BorderLayout());
			panelVrouw.setBorder(new LineBorder(Color.BLACK));

			lblNr2 = new JLabel();
			lblNr2.setText("   2   ");
			lblNr2.setFont(font);
			panelVrouw.add(lblNr2, BorderLayout.WEST);
			panelVrouw.add(getJCboEchtgenote(), BorderLayout.CENTER);
			// panelVrouw.add(getTxtNaamVrouw(), BorderLayout.CENTER);
		}
		return panelVrouw;
	}

	private JComboBox getCmbNames() {
		String[] names = Constant.NAMES;
		JComboBox jNames = new JComboBox(names);

		return jNames;
	}

	public JComboBox getNamesUitlegBar() {
		if (namesUitlegBar == null) {
			namesUitlegBar = getCmbNames();
			namesUitlegBar.addActionListener(new NamesUitlegBarHandler());
		}
		return namesUitlegBar;
	}

	public class NamesUitlegBarHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setRemarksInGui();
		}
	}

	public JComboBox getNamesUitlegRegl() {
		if (namesUitlegRegl == null) {
			namesUitlegRegl = getCmbNames();
			namesUitlegRegl.addActionListener(new NamesUitlegReglHandler());
		}
		return namesUitlegRegl;
	}

	public class NamesUitlegReglHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setRemarksInGui();
		}
	}

	public JComboBox getNamesGetekBar() {
		if (namesGetekBar == null) {
			namesGetekBar = getCmbNames();
			namesGetekBar.addActionListener(new NamesGetekendBarHandler());
		}
		return namesGetekBar;
	}

	public class NamesGetekendBarHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setRemarksInGui();
		}
	}

	public JComboBox getNamesGetekRegl() {
		if (namesGetekRegl == null) {
			namesGetekRegl = getCmbNames();
			namesGetekRegl.addActionListener(new NamesGetekendReglHandler());
		}
		return namesGetekRegl;
	}

	public JTextField getJTxtBadgenummer() {
		if (jTxtBadgenummer == null) {
			jTxtBadgenummer = new JTextField();
			jTxtBadgenummer.setPreferredSize(new Dimension(200, 36));
			jTxtBadgenummer.setFont(new Font("Times New Roman", Font.BOLD, 24));
			jTxtBadgenummer.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void removeUpdate(DocumentEvent e) {
					setRemarksInGui();
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					setRemarksInGui();
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					setRemarksInGui();
				}
			});
		}
		return jTxtBadgenummer;
	}

	public class NamesGetekendReglHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setRemarksInGui();
		}
	}

	public JDatePicker getReglementUitlegDateChooser() {
		if (reglementUitlegDate == null) {
			reglementUitlegDate = JDateComponentFactory.createJDatePicker();
			reglementUitlegDate.setTextEditable(true);
			reglementUitlegDate.setShowYearButtons(true);
			reglementUitlegDate.clearTextField();
			reglementUitlegDate.getJFormattedTextField().setFont(font);
			reglementUitlegDate.getJFormattedTextField().setPreferredSize(txtFieldDim);
			reglementUitlegDate.getJFormattedTextField().getDocument().addDocumentListener(dateListener);
		}
		return reglementUitlegDate;
	}

	public class ReglementDateListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			setRemarksInGui();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			setRemarksInGui();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			setRemarksInGui();
		}
	}

	public class ReglementUitlegDateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (getReglementUitlegDateChooser() != null) {
				setRemarksInGui();
			}
		}

	}

	public JDatePicker getReglementGetekendDateChooser() {
		if (reglementGetekendDate == null) {
			reglementGetekendDate = JDateComponentFactory.createJDatePicker();
			reglementGetekendDate.setTextEditable(true);
			reglementGetekendDate.setShowYearButtons(true);
			reglementGetekendDate.clearTextField();
			reglementGetekendDate.getJFormattedTextField().setPreferredSize(txtFieldDim);
			reglementGetekendDate.getJFormattedTextField().setFont(font);
			reglementGetekendDate.getJFormattedTextField().getDocument().addDocumentListener(dateListener);
		}
		return reglementGetekendDate;
	}

	public class ReglementGetekendDateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (getReglementGetekendDateChooser() != null) {
				setRemarksInGui();
			}
		}

	}

	public JDatePicker getBareelUitlegDateChooser() {
		if (bareelUitlegDate == null) {
			bareelUitlegDate = JDateComponentFactory.createJDatePicker();
			bareelUitlegDate.setTextEditable(true);
			bareelUitlegDate.setShowYearButtons(true);
			bareelUitlegDate.clearTextField();
			bareelUitlegDate.getJFormattedTextField().setPreferredSize(txtFieldDim);
			bareelUitlegDate.getJFormattedTextField().setFont(font);
			bareelUitlegDate.getJFormattedTextField().getDocument().addDocumentListener(dateListener);
		}
		return bareelUitlegDate;
	}

	public class BareelUitlegDateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (getBareelUitlegDateChooser() != null) {
				setRemarksInGui();
			}
		}

	}

	public JDatePicker getBareelGetekendDateChooser() {
		if (bareelGetekendDate == null) {
			bareelGetekendDate = JDateComponentFactory.createJDatePicker();
			bareelGetekendDate.setTextEditable(true);
			bareelGetekendDate.setShowYearButtons(true);
			bareelGetekendDate.clearTextField();
			bareelGetekendDate.getJFormattedTextField().setPreferredSize(txtFieldDim);
			bareelGetekendDate.getJFormattedTextField().setFont(font);
			bareelGetekendDate.getJFormattedTextField().getDocument().addDocumentListener(dateListener);
		}
		return bareelGetekendDate;
	}

	public class BareelGetekendDateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (getBareelGetekendDateChooser() != null) {
				setRemarksInGui();
			}
		}

	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnOpslaan == null) {
			btnOpslaan = new JButton();
			btnOpslaan.setText("OPSLAAN");
			btnOpslaan.setPreferredSize(new Dimension(145, 40));

			String actionKey = "opslaan";
			KeyStroke stroke = KeyStroke.getKeyStroke("ctrl S");
			Action action = new OpslaanActionListener("Opslaan");
			InputMap inputMap = btnOpslaan.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			inputMap.put(stroke, actionKey);
			ActionMap actionMap = btnOpslaan.getActionMap();
			actionMap.put(actionKey, action);
			btnOpslaan.addActionListener(new OpslaanHandler());
		}
		return btnOpslaan;
	}

	class OpslaanActionListener extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		OpslaanActionListener(String s) {
			super(s);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			btnOpslaan.doClick();
		}
	}

	public class OpslaanHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			save();
		}
	}

	private void setAdministratieData() {
		namesGetekBar.setSelectedIndex(getIndexForName(getBetalerDTO().getBareelGetekendNaam()));
		namesUitlegBar.setSelectedIndex(getIndexForName(getBetalerDTO().getBareelUitlegNaam()));
		namesGetekRegl.setSelectedIndex(getIndexForName(getBetalerDTO().getReglementGetekendNaam()));
		namesUitlegRegl.setSelectedIndex(getIndexForName(getBetalerDTO().getReglementUitlegNaam()));

		if (getBetalerDTO().getBareelGetekendDatum() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getBetalerDTO().getBareelGetekendDatum());
			getBareelGetekendDateChooser().getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
			// getBareelGetekendDateChooser().setDate(administratieDTO.getBareelGetekendDatum());
			// JSpinner.DefaultEditor ed =
			// (JSpinner.DefaultEditor)getBareelGetekendDateChooser().getSpinner().getEditor();
			/*
			 * if (ed.getTextField().getText().equals("")) { Date date = administratieDTO.getBareelGetekendDatum();
			 * SimpleDateFormat sdf = new SimpleDateFormat("E dd-MM-yyyy"); ed.getTextField().setText(sdf.format(date));
			 * }
			 */
		} else {
			getBareelGetekendDateChooser().clearTextField();
		}

		if (getBetalerDTO().getBareelUitlegDatum() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getBetalerDTO().getBareelUitlegDatum());
			getBareelUitlegDateChooser().getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
			/*
			 * getBareelUitlegDateChooser().setDate(administratieDTO.getBareelUitlegDatum()); JSpinner.DefaultEditor ed
			 * = (JSpinner.DefaultEditor)getBareelUitlegDateChooser().getSpinner().getEditor(); if
			 * (ed.getTextField().getText().equals("")) { Date date = administratieDTO.getBareelUitlegDatum();
			 * SimpleDateFormat sdf = new SimpleDateFormat("E dd-MM-yyyy"); ed.getTextField().setText(sdf.format(date));
			 * }
			 */
		} else {
			getBareelUitlegDateChooser().clearTextField();
		}

		if (getBetalerDTO().getReglementGetekendDatum() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getBetalerDTO().getReglementGetekendDatum());
			getReglementGetekendDateChooser().getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
			/*
			 * getReglementGetekendDateChooser().setDate(administratieDTO.getReglementGetekendDatum());
			 * JSpinner.DefaultEditor ed =
			 * (JSpinner.DefaultEditor)getReglementGetekendDateChooser().getSpinner().getEditor(); if
			 * (ed.getTextField().getText().equals("")) { Date date = administratieDTO.getReglementGetekendDatum();
			 * SimpleDateFormat sdf = new SimpleDateFormat("E dd-MM-yyyy"); ed.getTextField().setText(sdf.format(date));
			 * }
			 */
		} else {
			getReglementGetekendDateChooser().clearTextField();
		}

		if (getBetalerDTO().getReglementUitlegDatum() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getBetalerDTO().getReglementUitlegDatum());
			getReglementUitlegDateChooser().getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
			/*
			 * getReglementUitlegDateChooser().setDate(administratieDTO.getReglementUitlegDatum());
			 * JSpinner.DefaultEditor ed =
			 * (JSpinner.DefaultEditor)getReglementUitlegDateChooser().getSpinner().getEditor(); if
			 * (ed.getTextField().getText().equals("")) { Date date = administratieDTO.getReglementUitlegDatum();
			 * SimpleDateFormat sdf = new SimpleDateFormat("E dd-MM-yyyy"); ed.getTextField().setText(sdf.format(date));
			 * }
			 */
		} else {
			getReglementUitlegDateChooser().clearTextField();
		}

		getTxtOpmerking().setText(getBetalerDTO().getOpmerking());
	}

	private void setContactData() {
		getPanelCenter().remove(getPanelContact());
		setPanelContact(null);
		getPanelCenter().add(getPanelContact(), BorderLayout.NORTH);
	}

	private int getIndexForName(int i) {
		int index = 0;
		if (i == GebruikerEnum.IVAN.value())
			index = GebruikerEnum.IVAN.value();
		if (i == GebruikerEnum.PASCAL.value())
			index = GebruikerEnum.PASCAL.value();
		if (i == GebruikerEnum.PATRICK.value())
			index = GebruikerEnum.PATRICK.value();
		if (i == GebruikerEnum.KRISTOF.value())
			index = GebruikerEnum.KRISTOF.value();
		if (i == GebruikerEnum.FRANK.value())
			index = GebruikerEnum.FRANK.value();
		if (i == GebruikerEnum.IGNAS.value())
			index = GebruikerEnum.IGNAS.value();
		return index;
	}

	private PersoonDTO zoekPersonen(String naam, String voornaam) {
		Map<String, Object> zoekCriteria = new HashMap<String, Object>();
		zoekCriteria.put("naam", naam);
		zoekCriteria.put("voornaam", voornaam);
		PersoonDTO persoon = null;
		if (zoekCriteria.size() > 0) {
			List<Object[]> gezochtePersonen = persoonService.zoekPersonen(zoekCriteria);
			if (gezochtePersonen.size() == 0) {
				String[] namen = naam.split(" ");
				zoekCriteria.remove("voornaam");
				zoekCriteria.put("naam", namen[0]);
				gezochtePersonen = persoonService.zoekPersonen(zoekCriteria);
			}

			if (gezochtePersonen.size() > 0) {
				for (Object[] o : gezochtePersonen) {
					String tmpNaam = o[1] + " " + o[2];
					String tmpNaam2 = naam + " " + voornaam;
					tmpNaam = tmpNaam.replace(" ", "");
					tmpNaam2 = tmpNaam2.replace(" ", "");
					if (tmpNaam.equals(tmpNaam2)) {
						persoon = persoonService.findPersoonById((Integer) o[0]);
						break;
					}
				}
			}
		}
		return persoon;
	}

	public class DateListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			setRemarksInGui();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			setRemarksInGui();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			setRemarksInGui();
		}
	}

	public BetalerDTO getBetalerDTO() {
		return betalerDTO;
	}

	public void setBetalerDTO(BetalerDTO betalerDTO) {
		this.betalerDTO = betalerDTO;
	}

	public JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new BorderLayout());
			panelCenter.setBorder(new LineBorder(Color.BLACK, 2));
			panelCenter.add(getPanelContact(), BorderLayout.NORTH);
			panelCenter.add(getPanelReglement(), BorderLayout.CENTER);
		}
		return panelCenter;
	}

	public JPanel getPanelContact() {
		if (panelContact == null) {
			panelContact = new JPanel();
			// Insets insets = new Insets(100, 0, 0, 0);
			panelContact.setBorder(new EmptyBorder(30, 0, 0, 0));
			Font fontContact = new Font("SansSerif", Font.BOLD, 24);
			Insets insets = new Insets(10, 10, 0, 10);
			JPanel jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setBorder(new LineBorder(Color.BLACK, 1));
			int teller = 0;
			if (getBetalerDTO() != null) {
				boolean contactTonen = false;
				PersoonDTO persoon = null;
				if (getBetalerDTO().getHoofdbetaler() != null
						&& getBetalerDTO().getHoofdbetaler().getAdresDTO() != null) {
					contactTonen = true;
					persoon = getBetalerDTO().getHoofdbetaler();
				} else if (getBetalerDTO().getBetaler() != null && getBetalerDTO().getBetaler().getAdresDTO() != null) {
					contactTonen = true;
					persoon = getBetalerDTO().getBetaler();
				}
				if (contactTonen) {
					GridBagConstraints gbcLblStraatNr = new GridBagConstraints();
					gbcLblStraatNr.gridx = 0;
					gbcLblStraatNr.gridy = teller;
					gbcLblStraatNr.insets = insets;
					gbcLblStraatNr.anchor = GridBagConstraints.EAST;
					GridBagConstraints gbcLblStraatNrWaarde = new GridBagConstraints();
					gbcLblStraatNrWaarde.gridx = 1;
					gbcLblStraatNrWaarde.gridy = teller;
					gbcLblStraatNrWaarde.insets = insets;
					gbcLblStraatNrWaarde.anchor = GridBagConstraints.WEST;
					String straat = persoon.getAdresDTO().getStraat();
					straat += " " + persoon.getAdresDTO().getHuisnummer();
					JLabel jLabelStraatNr = new JLabel("Straat: ");
					JLabel jLabelWaarde = new JLabel(straat);
					jLabelStraatNr.setFont(fontContact);
					jLabelWaarde.setFont(fontContact);
					teller++;

					GridBagConstraints gbcLblPlaats = new GridBagConstraints();
					gbcLblPlaats.gridx = 0;
					gbcLblPlaats.gridy = teller;
					gbcLblPlaats.insets = insets;
					gbcLblPlaats.anchor = GridBagConstraints.EAST;
					GridBagConstraints gbcLblPlaatsWaarde = new GridBagConstraints();
					gbcLblPlaatsWaarde.gridx = 1;
					gbcLblPlaatsWaarde.gridy = teller;
					gbcLblPlaatsWaarde.insets = insets;
					gbcLblPlaatsWaarde.anchor = GridBagConstraints.WEST;
					String plaats = persoon.getAdresDTO().getPlaats();
					JLabel jLabelPlaats = new JLabel("Woonplaats: ");
					JLabel jLabelPlaatsWaarde = new JLabel(plaats);
					jLabelPlaats.setFont(fontContact);
					jLabelPlaatsWaarde.setFont(fontContact);
					teller++;

					GridBagConstraints gbcLblPostcode = new GridBagConstraints();
					gbcLblPostcode.gridx = 0;
					gbcLblPostcode.gridy = teller;
					gbcLblPostcode.insets = insets;
					gbcLblPostcode.anchor = GridBagConstraints.EAST;
					GridBagConstraints gbcLblPostcodeWaarde = new GridBagConstraints();
					gbcLblPostcodeWaarde.gridx = 1;
					gbcLblPostcodeWaarde.gridy = teller;
					gbcLblPostcodeWaarde.insets = insets;
					gbcLblPostcodeWaarde.anchor = GridBagConstraints.WEST;
					String postcode = persoon.getAdresDTO().getPostcode();
					JLabel jLabelPostcode = new JLabel("Postcode: ");
					JLabel jLabelPostcodeWaarde = new JLabel(postcode);
					jLabelPostcode.setFont(fontContact);
					jLabelPostcodeWaarde.setFont(fontContact);

					jPanel.add(jLabelStraatNr, gbcLblStraatNr);
					jPanel.add(jLabelWaarde, gbcLblStraatNrWaarde);
					jPanel.add(jLabelPlaats, gbcLblPlaats);
					jPanel.add(jLabelPlaatsWaarde, gbcLblPlaatsWaarde);
					jPanel.add(jLabelPostcode, gbcLblPostcode);
					jPanel.add(jLabelPostcodeWaarde, gbcLblPostcodeWaarde);
					teller++;
				}
				boolean telefoon = false;
				for (ContactgegevenDTO contactgegeven : persoon.getContactgegevens()) {
					GridBagConstraints gbcLblContactgegeven = new GridBagConstraints();
					gbcLblContactgegeven.gridx = 0;
					gbcLblContactgegeven.gridy = teller;
					gbcLblContactgegeven.insets = insets;
					gbcLblContactgegeven.anchor = GridBagConstraints.EAST;
					GridBagConstraints gbcLblWaarde = new GridBagConstraints();
					gbcLblWaarde.gridx = 1;
					gbcLblWaarde.gridy = teller;
					gbcLblWaarde.insets = insets;
					gbcLblWaarde.anchor = GridBagConstraints.WEST;
					String key = contactgegeven.getContactgegevenType().getTranslationKey() + ": ";
					String waarde = contactgegeven.getWaarde();
					JLabel jLabelKey = new JLabel(key);
					jLabelKey.setFont(fontContact);
					JLabel jLabelWaarde = new JLabel(waarde);
					jLabelWaarde.setFont(fontContact);
					if (!telefoon && ContactgegevenTypeEnum.TELEFOON.equals(contactgegeven.getContactgegevenType())) {
						jPanel.add(jLabelKey, gbcLblContactgegeven);
						telefoon = true;
					} else if (!ContactgegevenTypeEnum.TELEFOON.equals(contactgegeven.getContactgegevenType())) {
						jPanel.add(jLabelKey, gbcLblContactgegeven);
					}
					jPanel.add(jLabelWaarde, gbcLblWaarde);
					teller++;
				}
			}
			panelContact.add(jPanel);
		}
		return panelContact;
	}

	public void setPanelContact(JPanel jPanel) {
		this.panelContact = jPanel;
	}

	public JButton getjBtnResetReglementUitlegDate() {
		if (jBtnResetReglementUitlegDate == null) {
			jBtnResetReglementUitlegDate = new JButton();
			jBtnResetReglementUitlegDate.setPreferredSize(dimButton);
			jBtnResetReglementUitlegDate.addActionListener(this);
		}
		return jBtnResetReglementUitlegDate;
	}

	public JButton getjBtnResetReglementGetekendDate() {
		if (jBtnResetReglementGetekendDate == null) {
			jBtnResetReglementGetekendDate = new JButton();
			jBtnResetReglementGetekendDate.setPreferredSize(dimButton);
			jBtnResetReglementGetekendDate.addActionListener(this);
		}
		return jBtnResetReglementGetekendDate;
	}

	public JButton getjBtnResetBareelUitlegDate() {
		if (jBtnResetBareelUitlegDate == null) {
			jBtnResetBareelUitlegDate = new JButton();
			jBtnResetBareelUitlegDate.setPreferredSize(dimButton);
			jBtnResetBareelUitlegDate.addActionListener(this);
		}
		return jBtnResetBareelUitlegDate;
	}

	public JButton getjBtnResetBareelGetekendDate() {
		if (jBtnResetBareelGetekendDate == null) {
			jBtnResetBareelGetekendDate = new JButton();
			jBtnResetBareelGetekendDate.setPreferredSize(dimButton);
			jBtnResetBareelGetekendDate.addActionListener(this);
		}
		return jBtnResetBareelGetekendDate;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getjBtnResetReglementUitlegDate()) {
			Calendar now = Calendar.getInstance();
			getReglementUitlegDateChooser().getModel().setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH),
					now.get(Calendar.DATE));
		} else if (e.getSource() == getjBtnResetReglementGetekendDate()) {
			Calendar now = Calendar.getInstance();
			getReglementGetekendDateChooser().getModel().setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH),
					now.get(Calendar.DATE));
		} else if (e.getSource() == getjBtnResetBareelUitlegDate()) {
			Calendar now = Calendar.getInstance();
			getBareelUitlegDateChooser().getModel().setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH),
					now.get(Calendar.DATE));
		} else if (e.getSource() == getjBtnResetBareelGetekendDate()) {
			Calendar now = Calendar.getInstance();
			getBareelGetekendDateChooser().getModel().setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH),
					now.get(Calendar.DATE));
		}
	}

}
