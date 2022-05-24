package be.camping.campingzwaenepoel.presentation.dialog.foto;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang.StringUtils;

import be.camping.campingzwaenepoel.common.constants.FotoConstant;
import be.camping.campingzwaenepoel.common.constants.PathConstant;
import be.camping.campingzwaenepoel.presentation.dialog.directorychooser.DirectoryChooser;
import be.camping.campingzwaenepoel.presentation.ui.layout.GridLayout2;
import be.camping.campingzwaenepoel.presentation.util.SwingUtilities;
import be.camping.campingzwaenepoel.service.ConfiguratieService;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.foto.FotoService;
import be.camping.campingzwaenepoel.service.transfer.ConfiguratieDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class FotoOptiesPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3953216915060206205L;
	
	private JButton jBtnPhotoLocation;
	private JButton jBtnImportPhoto;
	private JButton jBtnSavelocation;

	private DirectoryChooser directoryChooser;
	private DirectoryChooser importDirectoryChooser;
	
	private JPanel jPanelPhotoLocation;
	private JPanel jPanelImportPhoto;
	
	private Dimension dimButton = new Dimension(150, 28);
	Dimension panelGroundDim = new Dimension(120, 56);
	Font labelFont = new Font("SansSerif", Font.BOLD, 36);
	
	private JComponent jComponent;
	
	public JComponent getjComponent() {
		return jComponent;
	}

	public void setjComponent(JComponent jComponent) {
		this.jComponent = jComponent;
	}

	@Autowired
	private StandplaatsService standplaatsService;

	public StandplaatsService getStandplaatsService() {
		return standplaatsService;
	}
	
	public void setStandplaatsService(StandplaatsService standplaatsService) {
		this.standplaatsService = standplaatsService;
	}

	@Autowired
	private ConfiguratieService configuratieService;
	
	public ConfiguratieService getConfiguratieService() {
		return configuratieService;
	}

	public void setConfiguratieService(ConfiguratieService configuratieService) {
		this.configuratieService = configuratieService;
	}
	
	private FotoService fotoService;

	public FotoService getFotoService() {
		return fotoService;
	}

	public void setFotoService(FotoService fotoService) {
		this.fotoService = fotoService;
	}

	public DirectoryChooser getDirectoryChooser() {
		if (directoryChooser == null) {
			directoryChooser = new DirectoryChooser();
		}
		return directoryChooser;
	}

	public void setDirectoryChooser(DirectoryChooser directoryChooser) {
		this.directoryChooser = directoryChooser;
		directoryChooser.getLog().setEditable(false);
	}

	public DirectoryChooser getImportDirectoryChooser() {
		if (importDirectoryChooser == null) {
			importDirectoryChooser = new DirectoryChooser("Selecteer de locatie van de te importeren foto's");
		}
		return importDirectoryChooser;
	}

	public void setImportDirectoryChooser(DirectoryChooser importDirectoryChooser) {
		this.importDirectoryChooser = importDirectoryChooser;
	}

	private ConfiguratieDTO configuratieDTO;

	public ConfiguratieDTO getConfiguratieDTO() {
		if (configuratieDTO == null) {
			configuratieDTO = getConfiguratieService().getConfiguratie(PathConstant.getFotoDirectoryPath());
			if (configuratieDTO == null) {
				configuratieDTO = new ConfiguratieDTO();
				configuratieDTO.setNaam(PathConstant.getFotoDirectoryPath()); 
			} else if (StringUtils.isEmpty(configuratieDTO.getNaam())) {
				configuratieDTO.setNaam(PathConstant.getFotoDirectoryPath());
			}				
		}
		return configuratieDTO;
	}

	public void setConfiguratieDTO(ConfiguratieDTO configuratieDTO) {
		this.configuratieDTO = configuratieDTO;
	}
	
	public FotoOptiesPanel() {
		
	}
	
	public FotoOptiesPanel(JComponent jComponent) {
		this.jComponent = jComponent;
		initComponents();
	}

	public JButton getjBtnPhotoLocation() {
		if (jBtnPhotoLocation == null) {
			jBtnPhotoLocation = new JButton();
			jBtnPhotoLocation.setPreferredSize(dimButton);
			jBtnPhotoLocation.addActionListener(this);
		}
		return jBtnPhotoLocation;
	}

	public JButton getjBtnImportPhoto() {
		if (jBtnImportPhoto == null) {
			jBtnImportPhoto = new JButton("importeren");
			jBtnImportPhoto.setPreferredSize(dimButton);
			jBtnImportPhoto.addActionListener(this);
		}
		return jBtnImportPhoto;
	}
	
	public JButton getjBtnSavelocation() {
		if (jBtnSavelocation == null) {
			jBtnSavelocation = new JButton("opslaan");
			jBtnSavelocation.setPreferredSize(dimButton);
			jBtnSavelocation.addActionListener(this);
		}
		return jBtnSavelocation;
	}
	
	public JPanel getjPanelPhotoLocation() {
		if (jPanelPhotoLocation == null) {
			jPanelPhotoLocation = new JPanel();
			jPanelPhotoLocation.setLayout(new GridLayout2(1, 2));
			jPanelPhotoLocation.setBorder(new EmptyBorder(10, 5, 10, 5));
			jPanelPhotoLocation.add(getDirectoryChooser());
			jPanelPhotoLocation.add(getjBtnSavelocation());
		}
		return jPanelPhotoLocation;
	}
	
	public JPanel getjPanelImportPhoto() {
		if (jPanelImportPhoto == null) {
			jPanelImportPhoto = new JPanel();
			jPanelImportPhoto.setLayout(new GridLayout2(1, 2));
			jPanelImportPhoto.setBorder(new EmptyBorder(10, 5, 10, 5));
			jPanelImportPhoto.add(getImportDirectoryChooser());
			jPanelImportPhoto.add(getjBtnImportPhoto());
		}
		return jPanelImportPhoto;
	}
	
	public void initComponents() {
		setLayout(new GridLayout2(4, 1));
		setPreferredSize(new Dimension(1200, 150));
		add(getjPanelPhotoLocation());
		add(SwingUtilities.getDivider(1150));
		add(getjPanelImportPhoto());
		add(SwingUtilities.getDivider(1150));
		
		if (getConfiguratieDTO() != null && !StringUtils.isEmpty(getConfiguratieDTO().getWaarde())) {
			getDirectoryChooser().getLog().setText(getConfiguratieDTO().getWaarde());
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == getjBtnSavelocation()) {
			configuratieDTO.setWaarde(getDirectoryChooser().getDirectory().toString());
			configuratieDTO = getConfiguratieService().storeConfiguratie(configuratieDTO);
		} else if (evt.getSource() == getjBtnImportPhoto()) {
			String message = "";
			if (getConfiguratieDTO().getId() == null) {
				if (StringUtils.isEmpty(getDirectoryChooser().getLog().getText())) {
					message = "Gelieve eerst de map te kiezen waar de foto's moeten opgeslagen worden en op te slaan.";
				} else {
					message = "Gelieve eerst de map te kiezen waar de foto's moeten opgeslagen worden op te slaan.";
				}
			}
			if (StringUtils.isEmpty(getImportDirectoryChooser().getLog().getText())) {
				if (message.length() > 0) {
					message += "\n ";
				}
				message += "Gelieve eerst de map te kiezen van waar de foto's moeten opgeladen worden.";

			}
			if (message.length() > 0) {
				JOptionPane.showMessageDialog(this, message, "Niet alle gegevens ingevuld.", JOptionPane.WARNING_MESSAGE);
			} else {
				message = "WEET U ZEKER DAT U DE FOTO'S WIL IMPORTEREN?";
				String[] choices = {"JA", "NEE"};
				int result = JOptionPane.showOptionDialog(this, message, "Waarschuwing", JOptionPane.YES_NO_CANCEL_OPTION, 
								JOptionPane.WARNING_MESSAGE, null, choices, choices[0]);
				if (result == 0) {
					ConfiguratieDTO importDirectoryConfiguratieDTO = getConfiguratieService().getConfiguratie(FotoConstant.getImportDirectory());
					if (null == importDirectoryConfiguratieDTO) {
						importDirectoryConfiguratieDTO = new ConfiguratieDTO();
						importDirectoryConfiguratieDTO.setNaam(FotoConstant.getImportDirectory());
					}
					importDirectoryConfiguratieDTO.setWaarde(getImportDirectoryChooser().getLog().getText());
					getConfiguratieService().storeConfiguratie(importDirectoryConfiguratieDTO);
		        	ConfiguratieDTO configuratie = getConfiguratieService().getConfiguratie(FotoConstant.getImportedFotos());
		        	if (null == configuratie) {
		        		configuratie = new ConfiguratieDTO();
		        		configuratie.setNaam(FotoConstant.getImportedFotos());
		        	}
		        	configuratie.setWaarde("");
		        	configuratieService.storeConfiguratie(configuratie);
		        	
		        	ConfiguratieDTO configuratieImportDone = getConfiguratieService().getConfiguratie(FotoConstant.getImportDone());
		    		if (null == configuratieImportDone) {
		    			configuratieImportDone = new ConfiguratieDTO();
		    			configuratieImportDone.setNaam(FotoConstant.getImportDone());
		    		}
		    		configuratieImportDone.setWaarde("false");
		    		getConfiguratieService().storeConfiguratie(configuratieImportDone);
					
					boolean ok = getFotoService().importFoto(getImportDirectoryChooser().getLog().getText(), configuratieDTO.getWaarde());
					if (!ok) {
						JOptionPane.showMessageDialog(this, "Er is een fout opgetreden bij het opstarten van de import", "Fout", JOptionPane.ERROR_MESSAGE);
					} else {
						List<JComponent> components = new ArrayList<JComponent>();
						components.add(getjComponent());
						components.add(getjBtnImportPhoto());
						new ImportFotoBusyThread(components);
						getjBtnImportPhoto().setEnabled(false);
					}
				}
			}
		}
	}
}
