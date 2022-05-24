/*
 * OpmerkingenPanel.java
 *
 * Created on __DATE__, __TIME__
 */

package be.camping.campingzwaenepoel.presentation.ui.panels;

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;
import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.ui.panels.interfaces.PanelInterface;
import be.camping.campingzwaenepoel.service.StamboomService;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.transfer.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Glenn Wybo
 */
public class OpmerkingenPanel extends javax.swing.JPanel implements PanelInterface {

	@Autowired
	private StandplaatsService standplaatsService;

    @Autowired
    private StamboomService stamboomService;

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Color colorErrorTab = Constant.ERROR_COLOR;
	private JButton btnOpslaan = null;

	private JLabel lblBetalerOpmerking = null;
	private JLabel lblFotoOpmerking = null;
	private JLabel lblAlgemeneOpmerking = null;
	private JTextArea jTextAreaBetalerOpmerking = null;
	private JScrollPane jScrollPaneFotoOpmerking = null;
	private JTextArea jTextAreaFotoOpmerking = null;
	private JTextArea jTextAreaAlgemeneOpmerking = null;
	private JScrollPane jScrollPaneAlgemeneOpmerking = null;
	private JScrollPane jScrollPanePersonen = null;
	private JList jListPersonen = null;
	private JScrollPane jScrollPaneStamboom = null;
	private JList jListStamboom = null;

	private JPanel jPanelNorth = null;
	private JPanel jPanelCenter = null;
	private JPanel jPanelPersonen = null;
	private JPanel jPanelStamboom = null;

	private final Insets insetslabel = new Insets(0, 10, 0, 0); // @jve:decl-index=0:
	private final Insets insetsTArea = new Insets(0, 10, 20, 10); // @jve:decl-index=0:
	private final Font labelFont = new Font("SansSerif", Font.BOLD, 24);
	private final Font font = new Font("Times New Roman", Font.BOLD, 18);

	private Controller controller;

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public StandplaatsDTO getStandplaatsDTO() {
		return getController().getStandplaatsDTO();
	}

	public JScrollPane getjScrollPanePersonen() {
		if (jScrollPanePersonen == null) {
			jScrollPanePersonen = new JScrollPane();
			jScrollPanePersonen.setViewportView(getjPanelPersonen());

		}
		return jScrollPanePersonen;
	}

	public void setjScrollPanePersonen(JScrollPane jScrollPanePersonen) {
		this.jScrollPanePersonen = jScrollPanePersonen;
	}

	public JList getjListPersonen() {
		if (jListPersonen == null) {
			jListPersonen = new JList();
		}
		return jListPersonen;
	}

	public void setjListPersonen(JList jListPersonen) {
		this.jListPersonen = jListPersonen;
	}

	public JScrollPane getjScrollPaneStamboom() {
		if (jScrollPaneStamboom == null) {
			jScrollPaneStamboom = new JScrollPane(jListStamboom);
		}
		return jScrollPaneStamboom;
	}

	public void setjScrollPaneStamboom(JScrollPane jScrollPaneStamboom) {
		this.jScrollPaneStamboom = jScrollPaneStamboom;
	}

	public JList getjListStamboom() {
		if (jListStamboom == null) {
			jListStamboom = new JList();
		}
		return jListStamboom;
	}

	public void setjListStamboom(JList jListStamboom) {
		this.jListStamboom = jListStamboom;
	}

	public JPanel getjPanelNorth() {
		if (jPanelNorth == null) {
			jPanelNorth = new JPanel();
			jPanelNorth.setBorder(new LineBorder(Color.BLACK, 1));
			jPanelNorth.setLayout(new GridBagLayout());
			GridBagConstraints gridBagConstraintsLblAlgemeneOpm = new GridBagConstraints();
			gridBagConstraintsLblAlgemeneOpm.gridx = 0;
			gridBagConstraintsLblAlgemeneOpm.gridy = 0;
			gridBagConstraintsLblAlgemeneOpm.insets = insetslabel;
			gridBagConstraintsLblAlgemeneOpm.anchor = GridBagConstraints.EAST;
			lblAlgemeneOpmerking = new JLabel();
			lblAlgemeneOpmerking.setText("Algemene opmerking: ");
			lblAlgemeneOpmerking.setFont(labelFont);
			lblAlgemeneOpmerking.setForeground(Color.BLUE);

			GridBagConstraints gridBagConstraintsTextAlgemeneOpm = new GridBagConstraints();
			gridBagConstraintsTextAlgemeneOpm.gridx = 1;
			gridBagConstraintsTextAlgemeneOpm.gridy = 0;
			gridBagConstraintsTextAlgemeneOpm.gridheight = 2;
			Insets insetsTAlg = new Insets(10, 10, 10, 10); // @jve:decl-index=0:
			gridBagConstraintsTextAlgemeneOpm.insets = insetsTAlg;

			GridBagConstraints gridBagConstraintsBtnOpslaan = new GridBagConstraints();
			gridBagConstraintsBtnOpslaan.gridx = 0;
			gridBagConstraintsBtnOpslaan.gridy = 1;
			gridBagConstraintsBtnOpslaan.insets = insetslabel;
			gridBagConstraintsBtnOpslaan.anchor = GridBagConstraints.WEST;

			jPanelNorth.add(lblAlgemeneOpmerking, gridBagConstraintsLblAlgemeneOpm);
			jPanelNorth.add(getJScrollPaneAlgemeneOpmerking(), gridBagConstraintsTextAlgemeneOpm);
			jPanelNorth.add(getBtnOpslaan(), gridBagConstraintsBtnOpslaan);

		}
		return jPanelNorth;
	}

	public JPanel getjPanelCenter() {
		if (jPanelCenter == null) {
			jPanelCenter = new JPanel();
			jPanelCenter.setLayout(new GridBagLayout());

			boolean betalerOpmerking = false;
			for (BetalerDTO tmpBetaler : controller.getStandplaatsDTO().getBetalers()) {
				if (tmpBetaler.getDatumTot() == null && !StringUtils.isEmpty(tmpBetaler.getOpmerking())) {
					betalerOpmerking = true;
				}
			}

			List<InschrijvingDTO> inschrijvingen = standplaatsService
					.getInschrijvingen(getController().getStandplaatsDTO().getId(), null);
			List<Integer> ingeschrevenPersoonIds = new ArrayList<Integer>();
			List<PersoonDTO> personen = new ArrayList<PersoonDTO>();
			for (InschrijvingDTO tmpInschrijving : inschrijvingen) {
				if (SoortHuurderEnum.VAST.equals(tmpInschrijving.getSoorthuurder())) {
					for (InschrijvingPersoonDTO inschrijvingpersoon : tmpInschrijving.getInschrijvingPersonen()) {
						if (!ingeschrevenPersoonIds.contains(inschrijvingpersoon.getPersoon().getId())) {
							personen.add(inschrijvingpersoon.getPersoon());
							ingeschrevenPersoonIds.add(inschrijvingpersoon.getPersoon().getId());
						}
					}
				}
			}

			int teller = 0;
			if (betalerOpmerking) {
				GridBagConstraints gridBagConstraintsLblBetalerOpm = new GridBagConstraints();
				gridBagConstraintsLblBetalerOpm.gridx = 0;
				gridBagConstraintsLblBetalerOpm.gridy = teller;
				gridBagConstraintsLblBetalerOpm.insets = insetslabel;
				gridBagConstraintsLblBetalerOpm.gridwidth = 2;
				gridBagConstraintsLblBetalerOpm.anchor = GridBagConstraints.WEST;
				lblBetalerOpmerking = new JLabel();
				lblBetalerOpmerking.setText("Opmerking bij de betaler: ");
				lblBetalerOpmerking.setFont(labelFont);
				lblBetalerOpmerking.setForeground(Color.BLUE);

				GridBagConstraints gridBagConstraintsTextBetalerOpm = new GridBagConstraints();
				gridBagConstraintsTextBetalerOpm.gridx = 2;
				gridBagConstraintsTextBetalerOpm.gridy = teller;
				gridBagConstraintsTextBetalerOpm.insets = insetsTArea;
				gridBagConstraintsTextBetalerOpm.anchor = GridBagConstraints.WEST;
				gridBagConstraintsTextBetalerOpm.gridwidth = 2;

				jPanelCenter.add(lblBetalerOpmerking, gridBagConstraintsLblBetalerOpm);
				jPanelCenter.add(getJTextAreaBetalerOpmerking(), gridBagConstraintsTextBetalerOpm);

				teller++;
			}

			if (!StringUtils.isEmpty(getController().getStandplaatsDTO().getFotoOpmerking())) {
				GridBagConstraints gridBagConstraintsLblBetalerOpm = new GridBagConstraints();
				gridBagConstraintsLblBetalerOpm.gridx = 0;
				gridBagConstraintsLblBetalerOpm.gridy = teller;
				gridBagConstraintsLblBetalerOpm.insets = insetslabel;
				gridBagConstraintsLblBetalerOpm.gridwidth = 2;
				gridBagConstraintsLblBetalerOpm.anchor = GridBagConstraints.WEST;
				lblFotoOpmerking = new JLabel();
				lblFotoOpmerking.setText("Opmerking bij de foto's: ");
				lblFotoOpmerking.setFont(labelFont);
				lblFotoOpmerking.setForeground(Color.BLUE);

				GridBagConstraints gridBagConstraintsTextBetalerOpm = new GridBagConstraints();
				gridBagConstraintsTextBetalerOpm.gridx = 2;
				gridBagConstraintsTextBetalerOpm.gridy = teller;
				gridBagConstraintsTextBetalerOpm.insets = insetsTArea;
				gridBagConstraintsTextBetalerOpm.anchor = GridBagConstraints.WEST;
				gridBagConstraintsTextBetalerOpm.gridwidth = 2;
				getjTextAreaFotoOpmerking().setText(getController().getStandplaatsDTO().getFotoOpmerking());

				jPanelCenter.add(lblFotoOpmerking, gridBagConstraintsLblBetalerOpm);
				jPanelCenter.add(getjScrollPaneFotoOpmerking(), gridBagConstraintsTextBetalerOpm);

				teller++;
			}

			boolean persoonLabel = false;
			// for (InschrijvingPersoonDTO inschrijvingPersoon : inschrijving.getInschrijvingPersonen()) {
			for (PersoonDTO persoon : personen) {
				// if (!StringUtils.isEmpty(inschrijvingPersoon.getPersoon().getOpmerking())) {
				if (!StringUtils.isEmpty(persoon.getOpmerking())) {
					GridBagConstraints gridBagConstraintsLblPersoon = new GridBagConstraints();
					gridBagConstraintsLblPersoon.gridx = 0;
					gridBagConstraintsLblPersoon.gridy = teller;
					gridBagConstraintsLblPersoon.anchor = GridBagConstraints.WEST;
					gridBagConstraintsLblPersoon.gridwidth = 2;
					gridBagConstraintsLblPersoon.insets = insetslabel;
					JLabel jLabelPersoon = new JLabel();
					jLabelPersoon.setFont(labelFont);
					jPanelCenter.add(jLabelPersoon, gridBagConstraintsLblPersoon);
					teller++;

					GridBagConstraints gridBagConstraintsLbl = new GridBagConstraints();
					gridBagConstraintsLbl.gridx = 0;
					gridBagConstraintsLbl.gridy = teller;
					if (!persoonLabel) {
						jLabelPersoon.setText("Opmerkingen bij personen: ");
						jLabelPersoon.setForeground(Color.BLUE);
						Insets tmpInsets = new Insets(0, 0, 10, 0);
						gridBagConstraintsLbl.insets = tmpInsets;
						persoonLabel = true;
					} else {
						gridBagConstraintsLbl.insets = insetslabel;
					}
					gridBagConstraintsLbl.anchor = GridBagConstraints.EAST;
					gridBagConstraintsLbl.gridwidth = 2;
					// String tekst = inschrijvingPersoon.getPersoon().getNaam() + " " +
					// inschrijvingPersoon.getPersoon().getVoornaam() + ":";
					String tekst = persoon.getNaam() + " " + persoon.getVoornaam() + ":";
					JLabel jLabel = new JLabel(tekst);
					jLabel.setFont(labelFont);
					jPanelCenter.add(jLabel, gridBagConstraintsLbl);

					JTextArea jTextArea = new JTextArea();
					jTextArea.setRows(4);
					jTextArea.setColumns(40);
					jTextArea.setMinimumSize(new Dimension(700, 100));
					jTextArea.setEditable(false);
					jTextArea.setBorder(new LineBorder(Color.BLACK, 1));
					jTextArea.setFont(font);
					// jTextArea.setText(inschrijvingPersoon.getPersoon().getOpmerking());
					jTextArea.setText(persoon.getOpmerking());
					jTextArea.setLineWrap(true);
					jTextArea.setWrapStyleWord(true);
					GridBagConstraints gridBagConstraintsTextArea = new GridBagConstraints();
					gridBagConstraintsTextArea.gridx = 2;
					gridBagConstraintsTextArea.gridy = teller - 1;
					gridBagConstraintsTextArea.gridheight = 2;
					gridBagConstraintsTextArea.gridwidth = 2;
					gridBagConstraintsTextArea.insets = insetsTArea;
					jPanelCenter.add(jTextArea, gridBagConstraintsTextArea);
					teller++;
				}
			}

			// Wagens
			boolean wagenLabel = false;
			// for (InschrijvingPersoonDTO inschrijvingPersoon : inschrijving.getInschrijvingPersonen()) {
			for (PersoonDTO persoon : personen) {
				// for (WagenDTO wagen : inschrijvingPersoon.getPersoon().getWagens()) {
				for (WagenDTO wagen : persoon.getWagens()) {
					if (StringUtils.isEmpty(wagen.getOpmerking())) {
						continue;
					}
					GridBagConstraints gridBagConstraintsLblWagen = new GridBagConstraints();
					gridBagConstraintsLblWagen.gridx = 0;
					gridBagConstraintsLblWagen.gridy = teller;
					gridBagConstraintsLblWagen.insets = insetslabel;
					gridBagConstraintsLblWagen.anchor = GridBagConstraints.WEST;
					gridBagConstraintsLblWagen.gridwidth = 2;
					JLabel jLabelWagen = new JLabel();
					jLabelWagen.setFont(labelFont);
					jPanelCenter.add(jLabelWagen, gridBagConstraintsLblWagen);
					teller++;

					GridBagConstraints gridBagConstraintsLbl = new GridBagConstraints();
					gridBagConstraintsLbl.gridx = 0;
					gridBagConstraintsLbl.gridy = teller;
					if (!wagenLabel) {
						jLabelWagen.setText("Opmerkingen bij wagens: ");
						jLabelWagen.setForeground(Color.BLUE);
						Insets tmpInsets = new Insets(0, 0, 10, 0);
						gridBagConstraintsLbl.insets = tmpInsets;
						wagenLabel = true;
					} else {
						gridBagConstraintsLbl.insets = insetslabel;
					}
					gridBagConstraintsLbl.anchor = GridBagConstraints.EAST;
					gridBagConstraintsLbl.gridwidth = 2;
					// String tekst = inschrijvingPersoon.getPersoon().getNaam() + " " +
					// inschrijvingPersoon.getPersoon().getVoornaam() + " - "
					// + wagen.getMerk() + " - " + wagen.getNummerplaat() + ":";
					String tekst = persoon.getNaam() + " " + persoon.getVoornaam() + " - ";
					if (!StringUtils.isEmpty(wagen.getMerk())) {
						tekst += wagen.getMerk() + " - ";
					}
					tekst += wagen.getNummerplaat() + ":";
					JLabel jLabel = new JLabel(tekst);
					jLabel.setFont(labelFont);
					jPanelCenter.add(jLabel, gridBagConstraintsLbl);

					JTextArea jTextArea = new JTextArea();
					jTextArea.setRows(4);
					jTextArea.setColumns(40);
					jTextArea.setMinimumSize(new Dimension(700, 100));
					jTextArea.setEditable(false);
					jTextArea.setBorder(new LineBorder(Color.BLACK, 1));
					jTextArea.setFont(font);
					jTextArea.setText(wagen.getOpmerking());
					jTextArea.setLineWrap(true);
					jTextArea.setWrapStyleWord(true);
					GridBagConstraints gridBagConstraintsTextArea = new GridBagConstraints();
					gridBagConstraintsTextArea.gridx = 2;
					gridBagConstraintsTextArea.gridy = teller - 1;
					gridBagConstraintsTextArea.gridheight = 2;
					gridBagConstraintsTextArea.gridwidth = 2;
					gridBagConstraintsTextArea.insets = insetsTArea;
					jPanelCenter.add(jTextArea, gridBagConstraintsTextArea);
					teller++;
				}
			}

			List<StamboomDTO> stamboom = stamboomService.getStamboom(
					getController().getStandplaatsDTO().getId());
			List<Integer> persoonIds = new ArrayList<Integer>();
			boolean stamboomlabel = false;
			for (StamboomDTO persoon : stamboom) {
				if (!StringUtils.isEmpty(persoon.getOpmerking()) && !persoonIds.contains(persoon.getId())) {
					GridBagConstraints gridBagConstraintsLblPersoon = new GridBagConstraints();
					gridBagConstraintsLblPersoon.gridx = 0;
					gridBagConstraintsLblPersoon.gridy = teller;
					gridBagConstraintsLblPersoon.insets = insetslabel;
					gridBagConstraintsLblPersoon.anchor = GridBagConstraints.WEST;
					gridBagConstraintsLblPersoon.gridwidth = 3;
					JLabel jLabelStamboom = new JLabel();
					jLabelStamboom.setFont(labelFont);
					teller++;
					GridBagConstraints gridBagConstraintsLbl = new GridBagConstraints();
					gridBagConstraintsLbl.gridx = 0;
					gridBagConstraintsLbl.gridy = teller;
					if (!stamboomlabel) {
						jLabelStamboom.setText("Opmerkingen bij stamboom: ");
						jLabelStamboom.setForeground(Color.BLUE);
						Insets tmpInsets = new Insets(0, 0, 10, 0);
						gridBagConstraintsLbl.insets = tmpInsets;
						stamboomlabel = true;
					} else {
						gridBagConstraintsLbl.insets = insetslabel;
					}
					gridBagConstraintsLbl.gridwidth = 3;
					gridBagConstraintsLbl.anchor = GridBagConstraints.WEST;
					String tekst = persoon.getNaam();
					persoonIds.add(persoon.getId());
					StamboomDTO partner = getPartnerVanStamboom(stamboom, persoon.getGeneratie(), persoon.getRang());
					if (partner != null) {
						tekst += " en " + partner.getNaam();
						persoonIds.add(partner.getId());
					}
					JLabel jLabel = new JLabel(tekst + ":");
					jLabel.setFont(labelFont);

					JTextArea jTextArea = new JTextArea();
					jTextArea.setRows(4);
					jTextArea.setColumns(40);
					jTextArea.setMinimumSize(new Dimension(700, 100));
					jTextArea.setEditable(false);
					jTextArea.setFont(font);
					jTextArea.setText(persoon.getOpmerking());
					jTextArea.setBorder(new LineBorder(Color.BLACK, 1));
					jTextArea.setLineWrap(true);
					jTextArea.setWrapStyleWord(true);
					GridBagConstraints gridBagConstraintsTextArea = new GridBagConstraints();
					gridBagConstraintsTextArea.gridx = 3;
					gridBagConstraintsTextArea.gridy = teller - 1;
					gridBagConstraintsTextArea.insets = insetsTArea;
					// gridBagConstraintsTextArea.gridwidth = 2;
					gridBagConstraintsTextArea.gridheight = 2;

					jPanelCenter.add(jLabelStamboom, gridBagConstraintsLblPersoon);
					jPanelCenter.add(jLabel, gridBagConstraintsLbl);
					jPanelCenter.add(jTextArea, gridBagConstraintsTextArea);
					teller++;
				}
			}

			boolean factuurlabel = false;
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			StandplaatsDTO standplaatsDTOFactuur = standplaatsService
					.getStandplaatsWithFacturenAndFactuurDetails(getController().getStandplaatsDTO().getId());
			for (FactuurDetailDTO factuurDetail : standplaatsDTOFactuur.getFactuurDetails()) {
				if (!StringUtils.isEmpty(factuurDetail.getOpmerking())) {
					GridBagConstraints gridBagConstraintsLblBetaling = new GridBagConstraints();
					gridBagConstraintsLblBetaling.gridx = 0;
					gridBagConstraintsLblBetaling.gridy = teller;
					gridBagConstraintsLblBetaling.insets = insetslabel;
					gridBagConstraintsLblBetaling.anchor = GridBagConstraints.WEST;
					gridBagConstraintsLblBetaling.gridwidth = 3;
					JLabel jLabelBetaling = new JLabel();
					jLabelBetaling.setFont(labelFont);
					teller++;
					GridBagConstraints gridBagConstraintsLbl = new GridBagConstraints();
					gridBagConstraintsLbl.gridx = 0;
					gridBagConstraintsLbl.gridy = teller;
					if (!factuurlabel) {
						jLabelBetaling.setText("Opmerkingen bij betalingen: ");
						jLabelBetaling.setForeground(Color.BLUE);
						Insets tmpInsets = new Insets(0, 0, 10, 0);
						gridBagConstraintsLbl.insets = tmpInsets;
						factuurlabel = true;
					} else {
						gridBagConstraintsLbl.insets = insetslabel;
					}
					gridBagConstraintsLbl.gridwidth = 3;
					gridBagConstraintsLbl.anchor = GridBagConstraints.EAST;
					String formattedDate = formatter.format(factuurDetail.getDatum());
					formattedDate += " - " + factuurDetail.getAardBetaling().getTranslationKey() + ": ";
					JLabel jLabel = new JLabel(formattedDate);
					jLabel.setFont(labelFont);

					JTextArea jTextArea = new JTextArea();
					jTextArea.setRows(4);
					jTextArea.setColumns(40);
					jTextArea.setMinimumSize(new Dimension(700, 30));
					jTextArea.setEditable(false);
					jTextArea.setFont(font);
					jTextArea.setText(factuurDetail.getOpmerking());
					jTextArea.setBorder(new LineBorder(Color.BLACK, 1));
					jTextArea.setLineWrap(true);
					jTextArea.setWrapStyleWord(true);
					GridBagConstraints gridBagConstraintsTextArea = new GridBagConstraints();
					gridBagConstraintsTextArea.gridx = 3;
					gridBagConstraintsTextArea.gridy = teller - 1;
					gridBagConstraintsTextArea.insets = insetsTArea;
					// gridBagConstraintsTextArea.gridwidth = 2;
					gridBagConstraintsTextArea.gridheight = 2;

					jPanelCenter.add(jLabelBetaling, gridBagConstraintsLblBetaling);
					jPanelCenter.add(jLabel, gridBagConstraintsLbl);
					jPanelCenter.add(jTextArea, gridBagConstraintsTextArea);
					teller++;
				}
			}

			// jPanelCenter.add(getjScrollPanePersonen());
			// jPanelCenter.add(getjPanelStamboom());
		}
		return jPanelCenter;
	}

	private StamboomDTO getPartnerVanStamboom(List<StamboomDTO> stamboom, int generatie, int rang) {
		StamboomDTO persoon = null;
		for (StamboomDTO partner : stamboom) {
			if (partner.getGeneratie() == generatie) {
				if ((generatie == partner.getGeneratie())
						&& (((rang % 2 != 0) && (partner.getRang() == rang + 1)) || ((rang % 2 == 0) && (partner
								.getRang() == rang - 1)))) {

					persoon = partner;
					break;
				}
			}
		}
		return persoon;
	}

	public JPanel getjPanelPersonen() {
		if (jPanelPersonen == null) {
			jPanelPersonen = new JPanel();
			InschrijvingDTO inschrijving = null;
			int id = 0;
			for (InschrijvingDTO tmpInschrijving : getController().getStandplaatsDTO().getInschrijvingen()) {
				if (tmpInschrijving.getId() > id) {
					id = tmpInschrijving.getId();
					inschrijving = tmpInschrijving;
				}
			}
			jPanelPersonen.setLayout(new GridBagLayout());
			int teller = 0;
			for (InschrijvingPersoonDTO inschrijvingPersoon : inschrijving.getInschrijvingPersonen()) {
				if (!StringUtils.isEmpty(inschrijvingPersoon.getPersoon().getOpmerking())) {
					GridBagConstraints gridBagConstraintsLbl = new GridBagConstraints();
					gridBagConstraintsLbl.gridx = 0;
					gridBagConstraintsLbl.gridy = teller;
					gridBagConstraintsLbl.insets = insetslabel;
					gridBagConstraintsLbl.anchor = GridBagConstraints.EAST;
					JLabel jLabel = new JLabel(inschrijvingPersoon.getPersoon().getNaam() + " "
							+ inschrijvingPersoon.getPersoon().getVoornaam());
					jLabel.setFont(labelFont);

					JTextArea jTextArea = new JTextArea();
					jTextArea.setRows(4);
					jTextArea.setColumns(50);
					jTextArea.setMinimumSize(new Dimension(800, 100));
					jTextArea.setEditable(false);
					jTextArea.setFont(font);
					jTextArea.setText(inschrijvingPersoon.getPersoon().getOpmerking());
					jTextArea.setLineWrap(true);
					jTextArea.setWrapStyleWord(true);
					GridBagConstraints gridBagConstraintsTextArea = new GridBagConstraints();
					gridBagConstraintsTextArea.gridx = 1;
					gridBagConstraintsTextArea.gridy = teller;
					gridBagConstraintsTextArea.insets = insetsTArea;

					jPanelPersonen.add(jLabel, gridBagConstraintsLbl);
					jPanelPersonen.add(jTextArea, gridBagConstraintsTextArea);
					teller++;
				}
			}

		}
		return jPanelPersonen;
	}

	public JPanel getjPanelStamboom() {
		return jPanelStamboom;
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
	 * content of this method is always regenerated by the Form Editor.
	 */
	public void initComponents() {
	}

	/**
	 * This method initializes jTAOpmerking
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextAreaAlgemeneOpmerking() {
		if (jTextAreaAlgemeneOpmerking == null) {
			jTextAreaAlgemeneOpmerking = new JTextArea();
			jTextAreaAlgemeneOpmerking.setRows(5);
			jTextAreaAlgemeneOpmerking.setColumns(40);
			jTextAreaAlgemeneOpmerking.setBorder(new LineBorder(Color.BLACK, 1));
			jTextAreaAlgemeneOpmerking.setForeground(Color.RED);
			jTextAreaAlgemeneOpmerking.setFont(font);
			jTextAreaAlgemeneOpmerking.getDocument().addDocumentListener(new AlgemeneOpmerkingListener());
			jTextAreaAlgemeneOpmerking.setLineWrap(true);
			jTextAreaAlgemeneOpmerking.setWrapStyleWord(true);
		}
		return jTextAreaAlgemeneOpmerking;
	}

	private JScrollPane getJScrollPaneAlgemeneOpmerking() {
		if (jScrollPaneAlgemeneOpmerking == null) {
			jScrollPaneAlgemeneOpmerking = new JScrollPane(getJTextAreaAlgemeneOpmerking());
		}
		return jScrollPaneAlgemeneOpmerking;
	}

	public class AlgemeneOpmerkingListener implements DocumentListener {

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

	/**
	 * This method initializes jTAOpmerking
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextAreaBetalerOpmerking() {
		if (jTextAreaBetalerOpmerking == null) {
			jTextAreaBetalerOpmerking = new JTextArea();
			jTextAreaBetalerOpmerking.setRows(4);
			jTextAreaBetalerOpmerking.setColumns(40);
			jTextAreaBetalerOpmerking.setMinimumSize(new Dimension(700, 100));
			jTextAreaBetalerOpmerking.setBorder(new LineBorder(Color.BLACK, 1));
			jTextAreaBetalerOpmerking.setFont(font);
			jTextAreaBetalerOpmerking.setEditable(false);
			jTextAreaBetalerOpmerking.setLineWrap(true);
			jTextAreaBetalerOpmerking.setWrapStyleWord(true);
		}
		return jTextAreaBetalerOpmerking;
	}

	public JScrollPane getjScrollPaneFotoOpmerking() {
		if (jScrollPaneFotoOpmerking == null) {
			jScrollPaneFotoOpmerking = new JScrollPane(getjTextAreaFotoOpmerking());
		}
		return jScrollPaneFotoOpmerking;
	}

	public JTextArea getjTextAreaFotoOpmerking() {
		if (jTextAreaFotoOpmerking == null) {
			jTextAreaFotoOpmerking = new JTextArea();
			jTextAreaFotoOpmerking.setRows(5);
			jTextAreaFotoOpmerking.setColumns(40);
			jTextAreaFotoOpmerking.setMinimumSize(new Dimension(700, 100));
			jTextAreaFotoOpmerking.setBorder(new LineBorder(Color.BLACK, 1));
			jTextAreaFotoOpmerking.setFont(font);
			jTextAreaFotoOpmerking.setEditable(false);
			jTextAreaFotoOpmerking.setLineWrap(true);
			jTextAreaFotoOpmerking.setWrapStyleWord(true);
		}
		return jTextAreaFotoOpmerking;
	}

	/**
	 * This method initializes btnWis
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOpslaan() {
		if (btnOpslaan == null) {
			btnOpslaan = new JButton();

			Dimension dim = new Dimension(145, 40);
			btnOpslaan.setSize(dim);
			btnOpslaan.setPreferredSize(dim);
			btnOpslaan.setText("OPSLAAN");

			String actionKey = "opslaan";
			KeyStroke stroke = KeyStroke.getKeyStroke("ctrl S");
			Action action = new ButtonActionListener("Opslaan");
			InputMap inputMap = btnOpslaan.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			inputMap.put(stroke, actionKey);
			ActionMap actionMap = btnOpslaan.getActionMap();
			actionMap.put(actionKey, action);
			btnOpslaan.addActionListener(new OpslaanHandler());

		}
		return btnOpslaan;
	}

	class ButtonActionListener extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		ButtonActionListener(String s) {
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

	@Override
	public boolean checkDataChanged() {
		boolean ret = true;
		StandplaatsDTO grondDTO = controller.getStandplaatsDTO();
		String textGrondDTO = (grondDTO.getAlgemeneOpmerking() == null) ? "" : grondDTO.getAlgemeneOpmerking();
		String textOpmerking = (getJTextAreaAlgemeneOpmerking().getText() == null) ? ""
				: getJTextAreaAlgemeneOpmerking().getText();
		if (grondDTO != null && (textGrondDTO.equals(textOpmerking))) {
			ret = false;
		}
		return ret;
	}

	@Override
	public Object getDataFromGUI() {
		StandplaatsDTO standplaatsDTO = getStandplaatsDTO();
		if (getJTextAreaAlgemeneOpmerking().getText() != null) {
			standplaatsDTO.setAlgemeneOpmerking(getJTextAreaAlgemeneOpmerking().getText());
		} else {
			standplaatsDTO.setAlgemeneOpmerking("");
		}

		return standplaatsDTO;
	}

	@Override
	public void save() {
		StandplaatsDTO grondDTO = (StandplaatsDTO) getDataFromGUI();
		standplaatsService.storeStandplaats(grondDTO);
	}

	@Override
	public void setDataInGUI() {
		this.removeAll();
		reset();
		this.setBorder(new LineBorder(Color.BLACK, 2));
		this.setLayout(new BorderLayout());
		this.add(getjPanelNorth(), BorderLayout.NORTH);
		JScrollPane jScrollPane = new JScrollPane();
		Border paddingBorder = BorderFactory.createLineBorder(this.getBackground(), 10);
		jScrollPane.setBorder(paddingBorder);
		jScrollPane.setViewportView(getjPanelCenter());
		this.add(jScrollPane, BorderLayout.CENTER);

		StandplaatsDTO grondDTO = standplaatsService
				.getStandplaatsMetInschrijvingenEnPersonen(getController().getStandplaatsDTO().getId());
		getController().setStandplaatsDTO(grondDTO);
		if (grondDTO.getAlgemeneOpmerking() != null) {
			getJTextAreaAlgemeneOpmerking().setText(grondDTO.getAlgemeneOpmerking());
			getJTextAreaAlgemeneOpmerking().setCaretPosition(0);
		} else {
			getJTextAreaAlgemeneOpmerking().setText("");
		}
		for (BetalerDTO tmpBetaler : grondDTO.getBetalers()) {
			if (tmpBetaler.getDatumTot() == null && !StringUtils.isAlpha(tmpBetaler.getOpmerking())) {
				getJTextAreaBetalerOpmerking().setText(tmpBetaler.getOpmerking());
			}
		}
	}

	@Override
	public boolean checkData() {
		boolean ret = false;
		if (StringUtils.isEmpty(getJTextAreaAlgemeneOpmerking().getText())) {
			ret = true;
		}
		return ret;
	}

	@Override
	public boolean checkDataForParent() {
		boolean ret = false;
		if (StringUtils.isEmpty(getStandplaatsDTO().getAlgemeneOpmerking())) {
			ret = true;
		}
		return ret;
	}

	@Override
	public void setRemarksInGui() {
		boolean b = checkData();
		controller.updateDataForPanel(b, 1);
	}

	private void reset() {
		getJTextAreaAlgemeneOpmerking().setText("");
		getJTextAreaBetalerOpmerking().setText("");
		jPanelNorth = null;
		jPanelCenter = null;
		jPanelPersonen = null;
		jPanelStamboom = null;
		jScrollPanePersonen = null;
		jScrollPaneStamboom = null;
	}
}