package be.camping.campingzwaenepoel.presentation.ui.panels;

import be.camping.campingzwaenepoel.common.enums.GezinsPositieEnum;
import be.camping.campingzwaenepoel.common.enums.HuurderPositieEnum;
import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;
import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.autocomplete.Java2sAutoComboBox;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDateComponentFactory;
import be.camping.campingzwaenepoel.presentation.jdatepicker.JDatePicker;
import be.camping.campingzwaenepoel.service.BadgeService;
import be.camping.campingzwaenepoel.service.DTOFactory;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.transfer.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class RegistratiePanel extends JPanel implements ActionListener {

	@Autowired
	private StandplaatsService standplaatsService;

    @Autowired
    private BadgeService badgeService;

    private static final long serialVersionUID = 1L;
	private JButton jBtnHoofd = null;
	private JLabel jLblHoofd = null;
	private JTextField jTxtHoofd = null;

	private final Dimension dimButton = new Dimension(20, 20); // @jve:decl-index=0:
	private final Dimension dimTxtField = new Dimension(200, 28); // @jve:decl-index=0:
	private final Dimension dimTxtSoortHuurder = new Dimension(150, 28);
	private final Dimension dimBtnRemove = new Dimension(250, 28);
	private JButton jBtnPartner = null;
	private JLabel jLblPartner = null;
	private JTextField jTxtPartner = null;
	private JButton jBtnKind = null;
	private JLabel jLblKind = null;
	private JScrollPane jScrollPaneKinderen = null;
	private JList jListKinderen = null;
	private JLabel jLblAankomst = null;
	private JLabel jLblVertrek = null;
	private JDatePicker jDPAankomst = null; // @jve:decl-index=0:
	private JDatePicker jDPVertrek = null; // @jve:decl-index=0:
	private JLabel jLblKaartnummer = null;
	private final Vector<String> kinderen = new Vector<String>();

	private PersoonDTO persoon; // @jve:decl-index=0:
	private SoortHuurderEnum soortHuurder; // @jve:decl-index=0:
	private InschrijvingDTO inschrijving;
	private InschrijvingPersoonDTO inschrijvingPersoon; // @jve:decl-index=0:
	private JTextField jTxtHuurderspositieHoofd = null;
	private JTextField jTxtHuurdersPositiePartner = null;
	private JButton jBtnResetBeginDatum = null;
	private JButton jBtnResetEindDatum = null;
	private JButton jBtnRemoveHoofd = null;
	private JButton jBtnRemoveEchtgenote = null;
	private JButton jBtnRemoveKind = null;

	private List<String> badges;
	private Java2sAutoComboBox j2aKaartnummers;
	private int standplaatsId;

	private Controller controller;

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * This is the default constructor
	 */
	public RegistratiePanel() {
		super();
		initialize();
	}

	public RegistratiePanel(SoortHuurderEnum soortHuurder, InschrijvingDTO inschrijving, List<String> badges,
			Controller controller, int standplaatsId, StandplaatsService standplaatsService, BadgeService badgeService) {
		this.soortHuurder = soortHuurder;
		this.inschrijving = inschrijving;
		this.badges = badges;
		this.standplaatsId = standplaatsId;
		this.controller = controller;
		this.standplaatsService = standplaatsService;
		this.badgeService = badgeService;
		initialize();
	}

	public void setDataInGui() {
		for (InschrijvingPersoonDTO inschrijvingPersoon : getInschrijving().getInschrijvingPersonen()) {
			if (inschrijvingPersoon.getGezinsPositie().equals(GezinsPositieEnum.HOOFD)) {
				getJTxtHoofd().setText(
						inschrijvingPersoon.getPersoon().getNaam() + " "
								+ inschrijvingPersoon.getPersoon().getVoornaam());
				getJTxtHuurderspositieHoofd().setText(inschrijvingPersoon.getHuurdersPositie().getTranslationKey());
			} else if (inschrijvingPersoon.getGezinsPositie().equals(GezinsPositieEnum.ECHTGENOTE)
					&& !HuurderPositieEnum.HUURDER.equals(inschrijvingPersoon.getHuurdersPositie())) {
				getJTxtPartner().setText(
						inschrijvingPersoon.getPersoon().getNaam() + " "
								+ inschrijvingPersoon.getPersoon().getVoornaam());
				getJTxtHuurdersPositiePartner().setText(inschrijvingPersoon.getHuurdersPositie().getTranslationKey());
			} else {
				kinderen.add(inschrijvingPersoon.getPersoon().getNaam() + " "
						+ inschrijvingPersoon.getPersoon().getVoornaam());
				getJListKinderen().setListData(kinderen);
			}
		}
		if (inschrijving.getDateVan() != null && inschrijving.getDateVan().toString() != "") {
			Calendar calVan = Calendar.getInstance();
			calVan.setTime(inschrijving.getDateVan());
			getjDPAankomst().getModel().setDate(calVan.get(Calendar.YEAR), calVan.get(Calendar.MONTH),
					calVan.get(Calendar.DATE));
		}
		if (inschrijving.getDateTot() != null && inschrijving.getDateTot().toString() != "") {
			Calendar calTot = Calendar.getInstance();
			calTot.setTime(inschrijving.getDateTot());
			getjDPVertrek().getModel().setDate(calTot.get(Calendar.YEAR), calTot.get(Calendar.MONTH),
					calTot.get(Calendar.DATE));
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 8;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 7;
		GridBagConstraints gridBagConstraints103 = new GridBagConstraints();
		gridBagConstraints103.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints103.gridy = 1;
		gridBagConstraints103.weightx = 1.0;
		gridBagConstraints103.anchor = GridBagConstraints.WEST;
		gridBagConstraints103.gridx = 3;
		gridBagConstraints103.insets = new Insets(0, 0, 5, 10);
		GridBagConstraints gridBagConstraints102 = new GridBagConstraints();
		gridBagConstraints102.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints102.gridy = 0;
		gridBagConstraints102.anchor = GridBagConstraints.WEST;
		gridBagConstraints102.gridx = 3;
		gridBagConstraints102.insets = new Insets(0, 0, 5, 10);
		GridBagConstraints gridBagConstraints100 = new GridBagConstraints();
		gridBagConstraints100.gridx = 1;
		gridBagConstraints100.gridy = 9;
		jLblKaartnummer = new JLabel();
		if (getSoortHuurder().equals(SoortHuurderEnum.LOS)) {
			jLblKaartnummer.setText("KAARTNUMMER :");
		} else {
			jLblKaartnummer.setVisible(false);
		}
		GridBagConstraints gridBagConstraints99 = new GridBagConstraints();
		gridBagConstraints99.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints99.gridy = 8;
		gridBagConstraints99.weightx = 1.0;
		gridBagConstraints99.anchor = GridBagConstraints.WEST;
		gridBagConstraints99.gridx = 2;
		gridBagConstraints99.insets = new Insets(0, 20, 5, 10);
		GridBagConstraints gridBagConstraints98 = new GridBagConstraints();
		gridBagConstraints98.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints98.gridy = 7;
		gridBagConstraints98.weightx = 1.0;
		gridBagConstraints98.anchor = GridBagConstraints.WEST;
		gridBagConstraints98.gridx = 2;
		gridBagConstraints98.insets = new Insets(0, 20, 5, 10);
		GridBagConstraints gridBagConstraints97 = new GridBagConstraints();
		gridBagConstraints97.gridx = 1;
		gridBagConstraints97.anchor = GridBagConstraints.WEST;
		gridBagConstraints97.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints97.gridy = 8;
		jLblVertrek = new JLabel();
		jLblVertrek.setText("VERTREK :");
		GridBagConstraints gridBagConstraints96 = new GridBagConstraints();
		gridBagConstraints96.gridx = 1;
		gridBagConstraints96.anchor = GridBagConstraints.WEST;
		gridBagConstraints96.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints96.gridy = 7;
		jLblAankomst = new JLabel();
		jLblAankomst.setText("AANKOMST :");
		GridBagConstraints gridBagConstraints95 = new GridBagConstraints();
		gridBagConstraints95.fill = GridBagConstraints.BOTH;
		gridBagConstraints95.gridy = 2;
		gridBagConstraints95.gridheight = 5;
		gridBagConstraints95.insets = new Insets(0, 0, 5, 20);
		gridBagConstraints95.gridx = 2;
		GridBagConstraints gbcBtnRemoveKind = new GridBagConstraints();
		gbcBtnRemoveKind.fill = GridBagConstraints.HORIZONTAL;
		gbcBtnRemoveKind.gridy = 2;
		gbcBtnRemoveKind.anchor = GridBagConstraints.WEST;
		gbcBtnRemoveKind.insets = new Insets(0, 0, 0, 10);
		gbcBtnRemoveKind.gridx = 3;
		GridBagConstraints gridBagConstraints94 = new GridBagConstraints();
		gridBagConstraints94.gridx = 1;
		gridBagConstraints94.anchor = GridBagConstraints.WEST;
		gridBagConstraints94.insets = new Insets(0, 20, 0, 10);
		gridBagConstraints94.gridy = 2;
		jLblKind = new JLabel();
		if (getSoortHuurder().equals(SoortHuurderEnum.VAST)) {
			jLblKind.setText("KIND :");
		} else {
			jLblKind.setText("MEDE HUURDERS :");
		}

		GridBagConstraints gridBagConstraints93 = new GridBagConstraints();
		gridBagConstraints93.gridx = 0;
		gridBagConstraints93.gridy = 2;
		GridBagConstraints gridBagConstraints92 = new GridBagConstraints();
		gridBagConstraints92.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints92.gridy = 1;
		gridBagConstraints92.weightx = 1.0;
		gridBagConstraints92.anchor = GridBagConstraints.WEST;
		gridBagConstraints92.insets = new Insets(0, 0, 5, 10);
		gridBagConstraints92.gridx = 2;
		GridBagConstraints gbcBtnRemovePartner = new GridBagConstraints();
		gbcBtnRemovePartner.fill = GridBagConstraints.HORIZONTAL;
		gbcBtnRemovePartner.gridy = 1;
		gbcBtnRemovePartner.anchor = GridBagConstraints.WEST;
		gbcBtnRemovePartner.insets = new Insets(0, 10, 0, 10);
		gbcBtnRemovePartner.gridx = 4;
		GridBagConstraints gridBagConstraints91 = new GridBagConstraints();
		gridBagConstraints91.gridx = 1;
		gridBagConstraints91.anchor = GridBagConstraints.WEST;
		gridBagConstraints91.insets = new Insets(0, 20, 5, 10);
		gridBagConstraints91.gridy = 1;
		jLblPartner = new JLabel();
		jLblPartner.setText("ECHTGENOTE :");

		GridBagConstraints gridBagConstraints90 = new GridBagConstraints();
		gridBagConstraints90.gridx = 0;
		gridBagConstraints90.gridy = 1;
		GridBagConstraints gridBagConstraints89 = new GridBagConstraints();
		gridBagConstraints89.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints89.gridy = 0;
		gridBagConstraints89.weightx = 1.0;
		gridBagConstraints89.anchor = GridBagConstraints.WEST;
		gridBagConstraints89.insets = new Insets(0, 0, 5, 10);
		gridBagConstraints89.gridx = 2;
		GridBagConstraints gbcBtnRemoveHoofd = new GridBagConstraints();
		gbcBtnRemoveHoofd.fill = GridBagConstraints.HORIZONTAL;
		gbcBtnRemoveHoofd.gridy = 0;
		gbcBtnRemoveHoofd.anchor = GridBagConstraints.WEST;
		gbcBtnRemoveHoofd.insets = new Insets(0, 10, 0, 10);
		gbcBtnRemoveHoofd.gridx = 4;
		GridBagConstraints gridBagConstraints88 = new GridBagConstraints();
		gridBagConstraints88.gridx = 1;
		gridBagConstraints88.anchor = GridBagConstraints.WEST;
		gridBagConstraints88.insets = new Insets(0, 20, 5, 10);
		gridBagConstraints88.gridy = 0;
		jLblHoofd = new JLabel();
		if (getSoortHuurder().equals(SoortHuurderEnum.VAST)) {
			jLblHoofd.setText("HOOFD :");
		} else {
			jLblHoofd.setText("VERANTWOORDELIJKE :");
		}
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridy = 0;
		GridBagConstraints gbcKaartnummer = new GridBagConstraints();
		gbcKaartnummer.fill = GridBagConstraints.VERTICAL;
		gbcKaartnummer.gridy = 9;
		gbcKaartnummer.anchor = GridBagConstraints.WEST;
		gbcKaartnummer.gridx = 2;
		this.setSize(503, 411);
		this.setLayout(new GridBagLayout());
		this.add(getJBtnHoofd(), gridBagConstraints);
		this.add(jLblHoofd, gridBagConstraints88);
		this.add(getJTxtHoofd(), gridBagConstraints89);
		this.add(getjBtnRemoveHoofd(), gbcBtnRemoveHoofd);
		if (getSoortHuurder().equals(SoortHuurderEnum.LOS)) {
			getJBtnPartner().setVisible(false);
			jLblPartner.setVisible(false);
			getJTxtPartner().setVisible(false);
			getjBtnRemoveEchtgenote().setVisible(false);
			getJTxtHuurdersPositiePartner().setVisible(false);
		}
		this.add(getJBtnPartner(), gridBagConstraints90);
		this.add(jLblPartner, gridBagConstraints91);
		this.add(getJTxtPartner(), gridBagConstraints92);
		this.add(getjBtnRemoveEchtgenote(), gbcBtnRemovePartner);
		this.add(getJTxtHuurdersPositiePartner(), gridBagConstraints103);

		this.add(getJBtnKind(), gridBagConstraints93);
		this.add(jLblKind, gridBagConstraints94);
		this.add(getjBtnRemoveKind(), gbcBtnRemoveKind);
		this.add(getJScrollPaneKinderen(), gridBagConstraints95);
		this.add(jLblAankomst, gridBagConstraints96);
		this.add(jLblVertrek, gridBagConstraints97);
		this.add((JComponent) getjDPAankomst(), gridBagConstraints98);
		this.add((JComponent) getjDPVertrek(), gridBagConstraints99);
		this.add(jLblKaartnummer, gridBagConstraints100);
		this.add(getJTxtHuurderspositieHoofd(), gridBagConstraints102);
		this.add(getJBtnResetBeginDatum(), gridBagConstraints1);
		this.add(getJBtnResetEindDatum(), gridBagConstraints2);
		this.add(getJ2aKaartnummers(), gbcKaartnummer);
	}

	/**
	 * This method initializes jBtnHoofd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJBtnHoofd() {
		if (jBtnHoofd == null) {
			jBtnHoofd = new JButton();
			jBtnHoofd.setPreferredSize(dimButton);
			jBtnHoofd.addActionListener(this);
		}
		return jBtnHoofd;
	}

	/**
	 * This method initializes jTxtHoofd
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtHoofd() {
		if (jTxtHoofd == null) {
			jTxtHoofd = new JTextField();
			jTxtHoofd.setPreferredSize(dimTxtField);
			jTxtHoofd.setEditable(false);
		}
		return jTxtHoofd;
	}

	/**
	 * This method initializes jBtnPartner
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJBtnPartner() {
		if (jBtnPartner == null) {
			jBtnPartner = new JButton();
			jBtnPartner.setPreferredSize(dimButton);
			jBtnPartner.addActionListener(this);
		}
		return jBtnPartner;
	}

	/**
	 * This method initializes jTxtPartner
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtPartner() {
		if (jTxtPartner == null) {
			jTxtPartner = new JTextField();
			jTxtPartner.setPreferredSize(dimTxtField);
			jTxtPartner.setEditable(false);
		}
		return jTxtPartner;
	}

	/**
	 * This method initializes jBtnKind
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJBtnKind() {
		if (jBtnKind == null) {
			jBtnKind = new JButton();
			jBtnKind.setPreferredSize(dimButton);
			jBtnKind.addActionListener(this);
		}
		return jBtnKind;
	}

	/**
	 * This method initializes jScrollPaneKinderen
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneKinderen() {
		if (jScrollPaneKinderen == null) {
			jScrollPaneKinderen = new JScrollPane();
			jScrollPaneKinderen.setPreferredSize(new Dimension(200, 200));
			jScrollPaneKinderen.setViewportView(getJListKinderen());
		}
		return jScrollPaneKinderen;
	}

	/**
	 * This method initializes jListKinderen
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJListKinderen() {
		if (jListKinderen == null) {
			jListKinderen = new JList();
		}
		return jListKinderen;
	}

	public PersoonDTO getPersoon() {
		return persoon;
	}

	public void setPersoon(PersoonDTO persoon) {
		this.persoon = persoon;
	}

	public SoortHuurderEnum getSoortHuurder() {
		return soortHuurder;
	}

	public void setSoortHuurder(SoortHuurderEnum soortHuurder) {
		this.soortHuurder = soortHuurder;
	}

	public InschrijvingPersoonDTO getInschrijvingPersoon() {
		return inschrijvingPersoon;
	}

	public void setInschrijvingPersoon(InschrijvingPersoonDTO inschrijvingPersoon) {
		this.inschrijvingPersoon = inschrijvingPersoon;
	}

	public JDatePicker getjDPAankomst() {
		if (jDPAankomst == null) {
			jDPAankomst = JDateComponentFactory.createJDatePicker();
			jDPAankomst.setTextEditable(true);
			Calendar now = Calendar.getInstance();
			jDPAankomst.getModel().setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE));
		}
		return jDPAankomst;
	}

	public JDatePicker getjDPVertrek() {
		if (jDPVertrek == null) {
			jDPVertrek = JDateComponentFactory.createJDatePicker();
			jDPVertrek.setTextEditable(true);
			Calendar now = Calendar.getInstance();
			if (getSoortHuurder().equals(SoortHuurderEnum.VAST)) {
				int jaar = now.get(Calendar.YEAR);
				Calendar cal = Calendar.getInstance();
				cal.set(jaar, 9, 15);
				if (now.before(cal)) {
					jDPVertrek.getModel().setDate(jaar, 9, 15);
				} else {
					jDPVertrek.getModel().setDate(++jaar, 9, 15);
				}
			} else if (getSoortHuurder().equals(SoortHuurderEnum.LOS)) {
				jDPVertrek.getModel().setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE));
			}
		}
		return jDPVertrek;
	}

	public InschrijvingDTO getInschrijving() {
		return inschrijving;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// event na het aanklikken van jBtnHoofd
		if (e.getSource() == getJBtnHoofd()) {
			setInschrijvingPersoon(DTOFactory.getInschrijvingPersoonDTO());
			if (getSoortHuurder().equals(SoortHuurderEnum.VAST)) {
				HuurderPositieEnum huurderpositie = kiesHuurdersPositieVoorVast();
				if (huurderpositie != null) {
					getInschrijvingPersoon().setPersoon(getPersoon());
					getInschrijvingPersoon().setHuurdersPositie(huurderpositie);
					getJTxtHoofd().setText(getPersoon().getNaam() + " " + getPersoon().getVoornaam());
					getJTxtHuurderspositieHoofd().setText(huurderpositie.getTranslationKey());
					getInschrijvingPersoon().setGezinsPositie(GezinsPositieEnum.HOOFD);
				}
			} else {
				getInschrijvingPersoon().setPersoon(getPersoon());
				getInschrijvingPersoon().setHuurdersPositie(HuurderPositieEnum.HUURDER);
				getJTxtHoofd().setText(getPersoon().getNaam() + " " + getPersoon().getVoornaam());
				getJTxtHuurderspositieHoofd().setText("Huurder Hoofd");
				getInschrijvingPersoon().setGezinsPositie(GezinsPositieEnum.HOOFD);
			}
		}

		// event na het aanklikken van jBtnPartner
		if (e.getSource() == getJBtnPartner()) {
			setInschrijvingPersoon(DTOFactory.getInschrijvingPersoonDTO());
			if (getSoortHuurder().equals(SoortHuurderEnum.VAST)) {
				HuurderPositieEnum huurderpositie = kiesHuurdersPositieVoorVast();
				if (huurderpositie != null) {
					getInschrijvingPersoon().setPersoon(getPersoon());
					getInschrijvingPersoon().setHuurdersPositie(huurderpositie);
					getJTxtPartner().setText(getPersoon().getNaam() + " " + getPersoon().getVoornaam());
					getJTxtHuurdersPositiePartner().setText(huurderpositie.getTranslationKey());
					getInschrijvingPersoon().setGezinsPositie(GezinsPositieEnum.ECHTGENOTE);
				}
			} else {
				getInschrijvingPersoon().setPersoon(getPersoon());
				getInschrijvingPersoon().setHuurdersPositie(HuurderPositieEnum.HUURDER);
				getJTxtPartner().setText(getPersoon().getNaam() + " " + getPersoon().getVoornaam());
				getJTxtHuurdersPositiePartner().setText("Huurder Vrouw");
				getInschrijvingPersoon().setGezinsPositie(GezinsPositieEnum.ECHTGENOTE);
			}
		}

		// event na het aanklikken van jBtnKind
		if (e.getSource() == getJBtnKind()) {
			String naam = getPersoon().getNaam() + " " + getPersoon().getVoornaam();
			if (!kinderen.contains(naam)) {
				setInschrijvingPersoon(DTOFactory.getInschrijvingPersoonDTO());
				getInschrijvingPersoon().setPersoon(getPersoon());
				kinderen.add(naam);
				getJListKinderen().setListData(kinderen);
				if (getSoortHuurder().equals(SoortHuurderEnum.VAST)) {
					getInschrijvingPersoon().setGezinsPositie(GezinsPositieEnum.KIND);
					getInschrijvingPersoon().setHuurdersPositie(HuurderPositieEnum.KIND);
				} else {
					getInschrijvingPersoon().setGezinsPositie(GezinsPositieEnum.ANDERE);
					getInschrijvingPersoon().setHuurdersPositie(HuurderPositieEnum.HUURDER);
				}
			}
		}

		// event na het aanklikken van jBtnResetBeginDatum
		if (e.getSource() == getJBtnResetBeginDatum()) {
			Calendar now = Calendar.getInstance();
			getjDPAankomst().getModel()
					.setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE));
		}

		// event na het aanklikken van jBtnResetEindDatum
		if (e.getSource() == getJBtnResetEindDatum()) {
			Calendar now = Calendar.getInstance();
			int jaar = now.get(Calendar.YEAR);
			Calendar cal = Calendar.getInstance();
			cal.set(jaar, 9, 15);
			if (now.before(cal)) {
				getjDPVertrek().getModel().setDate(jaar, 9, 15);
			} else {
				getjDPVertrek().getModel().setDate(++jaar, 9, 15);
			}
		}

		// event na het aanklikken van jBtnRemoveHoofd
		if (e.getSource() == getjBtnRemoveHoofd()) {
			boolean removed = false;
			if (!StringUtils.isEmpty(getJTxtHoofd().getText())) {
				if (getInschrijvingPersoon() != null
						&& getJTxtHoofd().getText().equals(getPersoon().getNaam() + " " + getPersoon().getVoornaam())) {
					setInschrijvingPersoon(null);
				}
				for (InschrijvingPersoonDTO tmpInschrijvingPersoonDTO : getInschrijving().getInschrijvingPersonen()) {
					if (GezinsPositieEnum.HOOFD.equals(tmpInschrijvingPersoonDTO.getGezinsPositie())) {
						getInschrijving().getInschrijvingPersonen().remove(tmpInschrijvingPersoonDTO);
						getJTxtHoofd().setText("");
						getJTxtHuurderspositieHoofd().setText("");
						removed = true;
						break;
					}
				}
				if (!removed
						&& getJTxtHoofd().getText().equals(getPersoon().getNaam() + " " + getPersoon().getVoornaam())) {
					setInschrijvingPersoon(null);
					getJTxtHoofd().setText("");
					getJTxtHuurderspositieHoofd().setText("");
				}
			}
		}

		// event na het aanklikken van jBtnRemoveEchtgenote
		if (e.getSource() == getjBtnRemoveEchtgenote()) {
			boolean removed = false;
			if (getInschrijvingPersoon() != null
					&& !StringUtils.isEmpty(getJTxtPartner().getText())
					&& getJTxtPartner().getText().equals(
							getInschrijvingPersoon().getPersoon().getNaam() + " "
									+ getInschrijvingPersoon().getPersoon().getVoornaam())) {
				setInschrijvingPersoon(null);
			}
			for (InschrijvingPersoonDTO tmpInschrijvingPersoonDTO : getInschrijving().getInschrijvingPersonen()) {
				if (GezinsPositieEnum.ECHTGENOTE.equals(tmpInschrijvingPersoonDTO.getGezinsPositie())) {
					getInschrijving().getInschrijvingPersonen().remove(tmpInschrijvingPersoonDTO);
					setInschrijvingPersoon(null);
					getJTxtPartner().setText("");
					getJTxtHuurdersPositiePartner().setText("");
					removed = true;
					break;
				}
			}
			if (!removed && !StringUtils.isEmpty(getJTxtPartner().getText())
					&& getJTxtPartner().getText().equals(getPersoon().getNaam() + " " + getPersoon().getVoornaam())) {
				setInschrijvingPersoon(null);
				getJTxtPartner().setText("");
				getJTxtHuurdersPositiePartner().setText("");
			}
		}

		// event na het aanklikken van jBtnRemoveKind
		if (e.getSource() == getjBtnRemoveKind()) {
			String tmpNaam = (String) getJListKinderen().getSelectedValue();
			boolean removed = false;
			if (!StringUtils.isEmpty(tmpNaam)) {
				if (getInschrijvingPersoon() != null
						&& tmpNaam.equals(getInschrijvingPersoon().getPersoon().getNaam() + " "
								+ getInschrijvingPersoon().getPersoon().getVoornaam())) {
					setInschrijvingPersoon(null);
				}
				for (InschrijvingPersoonDTO tmpInschrijvingPersoonDTO : getInschrijving().getInschrijvingPersonen()) {
					if (tmpNaam.equals(tmpInschrijvingPersoonDTO.getPersoon().getNaam() + " "
							+ tmpInschrijvingPersoonDTO.getPersoon().getVoornaam())) {
						getInschrijving().getInschrijvingPersonen().remove(tmpInschrijvingPersoonDTO);
						for (String tmpKindNaam : kinderen) {
							if (tmpKindNaam.equals(tmpNaam)) {
								kinderen.remove(tmpKindNaam);
								getJListKinderen().setListData(kinderen);
								break;
							}
						}
						removed = true;
						break;
					}
				}
				if (!removed) {
					for (String tmpKindNaam : kinderen) {
						if (tmpKindNaam.equals(tmpNaam)) {
							kinderen.remove(tmpKindNaam);
							getJListKinderen().setListData(kinderen);
							break;
						}
					}
				}
			}
		}

		// event na select van een badgenummer. Controlle als badge reeds gebruikt voor standplaats
		if (e.getSource() == getJ2aKaartnummers()) {
            Optional<BadgeDTO> badge = badgeService.findBadge((String) getJ2aKaartnummers().getSelectedItem());
            InschrijvingDTO tmpInschrijving = standplaatsService.getInschrijvingVoorBadge(badge.get());
			if (tmpInschrijving != null) {
				Object[] array = { "DEZE BADGE IS IN GEBRUIKT!", "WENST U DEZE BADGE TE VERWIJDEREN?" };
				int ret = JOptionPane.showConfirmDialog(null, array, "", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (ret == 0) {
					tmpInschrijving.setBadge(null);
					StandplaatsDTO tmpStandplaats = standplaatsService.removeBadgeVanInschrijving(tmpInschrijving);
					if (tmpInschrijving.getStandplaatsId() == standplaatsId) {
						getController().getHeader().setStandplaatsDTO(tmpStandplaats);
					}
				}
			}
		}
	}

	private HuurderPositieEnum kiesHuurdersPositieVoorVast() {
		JRadioButton jRBtnHoofd = new JRadioButton("eigenaar HOOFD");
		JRadioButton jRBtnVrouw = new JRadioButton("eigenaar VROUW");
		JRadioButton jRBtnZoon = new JRadioButton("ZOON");
		JRadioButton jRBtnSchoondochter = new JRadioButton("Schoondochter");
		JRadioButton jRBtnDochter = new JRadioButton("DOCHTER");
		JRadioButton jRBtnSchoonzoon = new JRadioButton("Schoonzoon");
		JRadioButton jRBtnOuder = new JRadioButton("Ouders");
		JRadioButton jRBtnChauffeur = new JRadioButton("Chauffeur");
		JRadioButton jRBtnAndere = new JRadioButton("Andere");
		ButtonGroup group = new ButtonGroup();
		group.add(jRBtnHoofd);
		group.add(jRBtnVrouw);
		group.add(jRBtnZoon);
		group.add(jRBtnSchoondochter);
		group.add(jRBtnDochter);
		group.add(jRBtnSchoonzoon);
		group.add(jRBtnOuder);
		group.add(jRBtnChauffeur);
		group.add(jRBtnAndere);

		Object[] array = { "MAAK UW KEUZE", jRBtnHoofd, jRBtnVrouw, jRBtnZoon, jRBtnSchoondochter, jRBtnDochter,
				jRBtnSchoonzoon, jRBtnOuder, jRBtnChauffeur, jRBtnAndere };
		JOptionPane.showConfirmDialog(null, array, "", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);

		HuurderPositieEnum huurdersPositie = null;
		if (jRBtnHoofd.isSelected()) {
			huurdersPositie = HuurderPositieEnum.HOOFD;
		} else if (jRBtnVrouw.isSelected()) {
			huurdersPositie = HuurderPositieEnum.ECHTGENOTE;
		} else if (jRBtnZoon.isSelected()) {
			huurdersPositie = HuurderPositieEnum.ZOON;
		} else if (jRBtnSchoondochter.isSelected()) {
			huurdersPositie = HuurderPositieEnum.SCHOONDOCHTER;
		} else if (jRBtnDochter.isSelected()) {
			huurdersPositie = HuurderPositieEnum.DOCHTER;
		} else if (jRBtnSchoonzoon.isSelected()) {
			huurdersPositie = HuurderPositieEnum.SCHOONZOON;
		} else if (jRBtnOuder.isSelected()) {
			huurdersPositie = HuurderPositieEnum.OUDER;
		} else if (jRBtnChauffeur.isSelected()) {
			huurdersPositie = HuurderPositieEnum.CHAUFFEUR;
		} else if (jRBtnAndere.isSelected()) {
			huurdersPositie = HuurderPositieEnum.ANDERE;
		}

		return huurdersPositie;
	}

	/**
	 * This method initializes jTxtHuurderspositieHoofd
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtHuurderspositieHoofd() {
		if (jTxtHuurderspositieHoofd == null) {
			jTxtHuurderspositieHoofd = new JTextField();
			jTxtHuurderspositieHoofd.setPreferredSize(dimTxtSoortHuurder);
			jTxtHuurderspositieHoofd.setEditable(false);
		}
		return jTxtHuurderspositieHoofd;
	}

	/**
	 * This method initializes jTxtHuurdersPositiePartner
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTxtHuurdersPositiePartner() {
		if (jTxtHuurdersPositiePartner == null) {
			jTxtHuurdersPositiePartner = new JTextField();
			jTxtHuurdersPositiePartner.setPreferredSize(dimTxtSoortHuurder);
			jTxtHuurdersPositiePartner.setEditable(false);
		}
		return jTxtHuurdersPositiePartner;
	}

	/**
	 * This method initializes jBtnResetBeginDatum
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJBtnResetBeginDatum() {
		if (jBtnResetBeginDatum == null) {
			jBtnResetBeginDatum = new JButton();
			jBtnResetBeginDatum.setPreferredSize(dimButton);
			jBtnResetBeginDatum.addActionListener(this);
		}
		return jBtnResetBeginDatum;
	}

	/**
	 * This method initializes jBtnResetEindDatum
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJBtnResetEindDatum() {
		if (jBtnResetEindDatum == null) {
			jBtnResetEindDatum = new JButton();
			jBtnResetEindDatum.setPreferredSize(dimButton);
			jBtnResetEindDatum.addActionListener(this);
		}
		return jBtnResetEindDatum;
	}

	public JButton getjBtnRemoveHoofd() {
		if (jBtnRemoveHoofd == null) {
			jBtnRemoveHoofd = new JButton("Verwijder Hoofd");
			jBtnRemoveHoofd.setSize(dimBtnRemove);
			jBtnRemoveHoofd.setHorizontalAlignment(SwingConstants.CENTER);
			jBtnRemoveHoofd.addActionListener(this);
		}
		return jBtnRemoveHoofd;
	}

	public JButton getjBtnRemoveEchtgenote() {
		if (jBtnRemoveEchtgenote == null) {
			jBtnRemoveEchtgenote = new JButton("Verwijder Echtgenote");
			jBtnRemoveEchtgenote.setSize(dimBtnRemove);
			jBtnRemoveEchtgenote.addActionListener(this);
		}
		return jBtnRemoveEchtgenote;
	}

	public JButton getjBtnRemoveKind() {
		if (jBtnRemoveKind == null) {
			jBtnRemoveKind = new JButton("Verwijder Kind");
			jBtnRemoveKind.setSize(dimTxtField);
			jBtnRemoveKind.addActionListener(this);
		}
		return jBtnRemoveKind;
	}

	public List<String> getBadges() {
		if (badges == null) {
			badges = new ArrayList<String>();
		}
		return badges;
	}

	public void setBadges(List<String> badges) {
		this.badges = badges;
	}

	public Java2sAutoComboBox getJ2aKaartnummers() {
		if (j2aKaartnummers == null) {
			j2aKaartnummers = new Java2sAutoComboBox(getBadges());
			if (!getSoortHuurder().equals(SoortHuurderEnum.LOS)) {
				j2aKaartnummers.setVisible(false);
			} else {
				j2aKaartnummers.addActionListener(this);
			}
		}
		return j2aKaartnummers;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
