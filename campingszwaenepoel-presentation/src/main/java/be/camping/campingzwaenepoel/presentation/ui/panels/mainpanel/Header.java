package be.camping.campingzwaenepoel.presentation.ui.panels.mainpanel;

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.common.utils.ImageUtils;
import be.camping.campingzwaenepoel.presentation.Controller;
import be.camping.campingzwaenepoel.presentation.autocomplete.Java2sAutoComboBox;
import be.camping.campingzwaenepoel.presentation.clock.ClockPanel;
import be.camping.campingzwaenepoel.presentation.image.ImageLoader;
import be.camping.campingzwaenepoel.presentation.image.ImagePanel;
import be.camping.campingzwaenepoel.presentation.ui.layout.GridLayout2;
import be.camping.campingzwaenepoel.service.ConfiguratieService;
import be.camping.campingzwaenepoel.service.PersoonService;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.transfer.BetalerDTO;
import be.camping.campingzwaenepoel.service.transfer.ConfiguratieDTO;
import be.camping.campingzwaenepoel.service.transfer.PersoonDTO;
import be.camping.campingzwaenepoel.service.transfer.StandplaatsDTO;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class Header extends javax.swing.JPanel implements FocusListener {

    @Autowired
    private StandplaatsService standplaatsService;

    @Autowired
    private ConfiguratieService configuratieService;

    @Autowired
    private PersoonService persoonService;

    private Dimension dimBtn = new Dimension(30, 60);
    private Font labelFont = new Font("SansSerif", Font.BOLD, 36);

    private JButton buttonUp = null;
    private JButton buttonDown = null;
    private JButton buttonUpOffset = null;
    private JButton buttonDownOffset = null;
    private JPanel panelStandplaatsPhoto = null;
    private JLabel lblNameMan = null;
    private JLabel lblNameWoman = null;
    private JPanel panelDate = null;
    private final JLabel lblTabName = new JLabel();
    private Java2sAutoComboBox jComboBoxGroundNumber;
    private JPanel jPanelOwnerData = null;
    private JLabel jLabelTaalBetaler = null;
    private JLabel jLabelDatumGetekend = null;

    private Border border = new LineBorder(Color.BLACK, 2);
    private JButton btnClear = null;
    private Color colorErrorTab = Constant.ERROR_COLOR;

    private Dimension panelGroundDim = new Dimension(120, 56);

    private StandplaatsDTO standplaatsDTO;
    private Controller controller;

    private boolean buttonUpPressed;
    private boolean buttonUpOffsetPressed;
    private boolean buttonDownPressed;
    private boolean buttonDownOffsetPressed;
    private boolean zoeken;

    private boolean firstClick = false;

    private ImagePanel imagePanel;

    private StandplaatsFocus standplaatsFocus = new StandplaatsFocus();

    private ButtonThread buttonThread = null;

    private final Logger logger = Logger.getLogger(Header.class);

    public StandplaatsDTO getStandplaatsDTO() {
        return standplaatsDTO;
    }

    public void setStandplaatsDTO(StandplaatsDTO standplaatsDTO) {
        this.standplaatsDTO = standplaatsDTO;
    }

    private void setGrondDTO(StandplaatsDTO standplaatsDTO) {
        this.standplaatsDTO = standplaatsDTO;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private String camping;

    private ConfiguratieService getConfiguratieService() {
        return configuratieService;
    }

    public String getCamping() {
        if (StringUtils.isEmpty(camping)) {
            ConfiguratieDTO configuratie = getConfiguratieService().getConfiguratie("camping_naam");
            if (configuratie != null) {
                camping = configuratie.getWaarde();
            } else {
                camping = "";
            }
        }
        return camping;
    }

    public void setCamping(String camping) {
        this.camping = camping;
    }

    public void initComponents() {
        this.setLayout(new BorderLayout());
        this.setBorder(new LineBorder(Color.BLACK));

        initButtons();
        initPanelStandplaatsPhoto();
        initJCboGroundNumber();
        initPanelStandplaatsPhoto();

        this.add(getCentralPanel(), BorderLayout.CENTER);
        this.add(panelStandplaatsPhoto, BorderLayout.WEST);

        zoeken = true;
        jComboBoxGroundNumber.setDataList(standplaatsService.getStandplaatsNamen());
        jComboBoxGroundNumber.setSelectedIndex(1);
        buttonThread = new ButtonThread();
    }

    private void initButtons() {
        buttonUp = initButton(createImageIcon(Constant.ICON_UP_PATH), new ButtonUpListener());
        buttonDown = initButton(createImageIcon(Constant.ICON_DOWN_PATH), new ButtonDownListener());
        buttonUpOffset = initButton(createImageIcon(Constant.BIG_ICON_UP_PATH), new ButtonUpOffsetListener());
        buttonDownOffset = initButton(createImageIcon(Constant.BIG_ICON_DOWN_PATH), new ButtonDownOffsetListener());
        initBtnClear();
    }

    private ImageIcon createImageIcon(String path) {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            return new ImageIcon(classloader.getResource(path));
        } catch (Exception e) {
            return new ImageIcon();
        }
    }

    private JButton initButton(ImageIcon icon, MouseListener mouseListener) {
        JButton jButton = new JButton();
        jButton.setIcon(icon);
        jButton.setSize(dimBtn);
        jButton.setPreferredSize(dimBtn);
        jButton.addMouseListener(mouseListener);
        return jButton;
    }

    private String getFullStandplaatsNaam(StandplaatsDTO standplaatsDTO) {
        String naam;
        int standplaatsnummer = standplaatsDTO.getPlaatsnummer();
        String standplaats = Integer.toString(standplaatsnummer);
        while (standplaats.length() < 3) {
            standplaats = "0" + standplaats;
        }
        naam = standplaatsDTO.getPlaatsgroep() + standplaats;
        return naam;
    }

    public void setPhotoInStandplaats() {
        ConfiguratieDTO configuratie = configuratieService.getFileDirectory();
        if (configuratie != null) {
            try {
                this.remove(((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.WEST));
                initPanelStandplaatsPhoto();
                Dimension dim = new Dimension(170, 116);
                String dir = configuratie.getWaarde() + "/standplaats/" + getFullStandplaatsNaam(getStandplaatsDTO()) + "/hoofdfoto/";
                File file = new File(dir);
                String path = "";
                if (file.isDirectory()) {
                    File[] fotobestanden = file.listFiles();
                    if (fotobestanden != null && fotobestanden.length > 0) {
                        File imagefile = null;
                        for (int i = 0; i < fotobestanden.length; i++) {
                            File tmpFile = fotobestanden[i];
                            if (ImageUtils.isImage(tmpFile.getAbsolutePath())) {
                                imagefile = tmpFile;
                                break;
                            }
                        }
                        if (imagefile != null && imagefile.exists()) {
                            path = imagefile.getAbsolutePath();
                        }
                    }
                }
                if (!StringUtils.isEmpty(path)) {
                    BufferedImage image;
                    try {
                        image = ImageIO.read(new File(path));
                        imagePanel = new ImagePanel(image, dim);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    panelStandplaatsPhoto.setPreferredSize(dim);
                    panelStandplaatsPhoto.add(imagePanel, BorderLayout.CENTER);
                    this.add(panelStandplaatsPhoto, BorderLayout.WEST);
                } else {
                    JPanel jPanel = new JPanel();
                    jPanel.setPreferredSize(dim);
                    jPanel.setBorder(new LineBorder(Color.BLACK));
                    this.add(jPanel, BorderLayout.WEST);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ButtonUpListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            firstClick = true;
            zoeken = false;
            buttonUpPressed = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            buttonUpPressed = false;
            buttonThread.wakeUp();
            zoekStandplaats();
            zoeken = true;
        }
    }

    public class ButtonThread extends Thread implements Runnable {

        private boolean wakeUp = false;

        ButtonThread() {
            start();
        }

        void wakeUp() {
            wakeUp = true;
        }

        @Override
        public void run() {
            while (true) {
                if (jComboBoxGroundNumber != null) {
                    if (buttonUpPressed) {
                        if (jComboBoxGroundNumber.getSelectedIndex() + 1 < jComboBoxGroundNumber.getDataList().size()) {
                            int index = jComboBoxGroundNumber.getSelectedIndex() + 1;
                            jComboBoxGroundNumber.setSelectedIndex(index);
                        } else {
                            int size = jComboBoxGroundNumber.getDataList().size();
                            jComboBoxGroundNumber.setSelectedIndex(size - 1);
                        }
                    } else if (buttonDownPressed) {
                        if (jComboBoxGroundNumber.getSelectedIndex() > 1) {
                            int index = jComboBoxGroundNumber.getSelectedIndex() - 1;
                            jComboBoxGroundNumber.setSelectedIndex(index);
                        } else {
                            jComboBoxGroundNumber.setSelectedIndex(1);
                        }
                    } else if (buttonUpOffsetPressed) {
                        if (jComboBoxGroundNumber.getSelectedIndex() + Constant.STANDPLAATS_OFFSET < jComboBoxGroundNumber.getDataList().size()) {
                            int index = jComboBoxGroundNumber.getSelectedIndex() + Constant.STANDPLAATS_OFFSET;
                            jComboBoxGroundNumber.setSelectedIndex(index);
                        } else {
                            int size = jComboBoxGroundNumber.getDataList().size();
                            jComboBoxGroundNumber.setSelectedIndex(size - 1);
                        }
                    } else if (buttonDownOffsetPressed) {
                        if (jComboBoxGroundNumber.getSelectedIndex() - Constant.STANDPLAATS_OFFSET > 1) {
                            int index = jComboBoxGroundNumber.getSelectedIndex() - Constant.STANDPLAATS_OFFSET;
                            jComboBoxGroundNumber.setSelectedIndex(index);
                        } else {
                            jComboBoxGroundNumber.setSelectedIndex(1);
                        }
                    }

                    try {
                        int i = 0;
                        if (firstClick) {
                            firstClick = false;
                        } else {
                            i += 40;
                        }
                        for (; i < 50; i++) {
                            if (wakeUp) {
                                wakeUp = false;
                                break;
                            }
                            sleep(5);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public class ButtonDownListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            firstClick = true;
            zoeken = false;
            buttonDownPressed = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            buttonDownPressed = false;
            buttonThread.wakeUp();
            zoekStandplaats();
            zoeken = true;
        }

    }

    public class ButtonUpOffsetListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            firstClick = true;
            zoeken = false;
            buttonUpOffsetPressed = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            buttonUpOffsetPressed = false;
            buttonThread.wakeUp();
            zoekStandplaats();
            zoeken = true;
        }

    }

    public class ButtonDownOffsetListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            firstClick = true;
            zoeken = false;
            buttonDownOffsetPressed = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            buttonDownOffsetPressed = false;
            buttonThread.wakeUp();
            zoekStandplaats();
            zoeken = true;
        }

    }

    public class ZoekGrondHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (zoeken) {
                zoekStandplaats();
                jComboBoxGroundNumber.deselect();
            }
        }
    }

    public void zoekStandplaats(String standplaats) {
        jComboBoxGroundNumber.setSelectedItem(standplaats);
        zoekStandplaats();
    }

    public void zoekStandplaats() {
        getController().checkDataSaved();
        String grondNaam = (String) jComboBoxGroundNumber.getSelectedItem();
        if (!StringUtils.isEmpty(grondNaam)) {
            char[] chars = grondNaam.toCharArray();
            String plaatsgroep = "";
            String nummer = "";
            for (char character : chars) {
                if (Character.isDigit(character)) {
                    nummer += Character.toString(character);
                } else {
                    plaatsgroep += Character.toString(character);
                }
            }
            int plaatsnummer = 0;
            if (!StringUtils.isEmpty(nummer)) {
                plaatsnummer = new Integer(nummer);
            }
            StandplaatsDTO nieuweGrondDTO = standplaatsService.zoekStandplaats(plaatsgroep, plaatsnummer);
            setHeaderData(nieuweGrondDTO);
            getController().getPersoonPanel().checkDataChanged(true);
            PersoonDTO betaler = null;
            for (BetalerDTO tmpBetaler : nieuweGrondDTO.getBetalers()) {
                if (tmpBetaler.getHoofdbetaler() != null) {
                    betaler = tmpBetaler.getHoofdbetaler();
                } else if (tmpBetaler.getBetaler() != null) {
                    betaler = tmpBetaler.getBetaler();
                }
            }
            if (betaler != null) {
                getController().setPersoonInPersoonPanel(betaler);
            }
        }
    }

    private void setHeaderData(StandplaatsDTO nieuweStandplaatsDTO) {
        String naamMan = "";
        String naamVrouw = "";
        String taal = "";
        String datum = "";
        boolean changeHeader = false;
        if (nieuweStandplaatsDTO != null && nieuweStandplaatsDTO.getId() != 0) {
            setGrondDTO(nieuweStandplaatsDTO);
            BetalerDTO betaler = getController().getMainNavigationpanel().getBetalerPanel().getHuidigeBetaler();
            PersoonDTO betalerHoofd = null;
            PersoonDTO betalerPartner = null;
            if (betaler != null) {
                betalerHoofd = betaler.getHoofdbetaler();
                betalerPartner = betaler.getBetaler();
            }
            if (betalerHoofd != null) {
                naamMan = betalerHoofd.getNaam().toUpperCase() + " " + betalerHoofd.getVoornaam().toUpperCase();
                try {
                    taal = betalerHoofd.getTaal().toString();
                    DateFormat formatter;
                    formatter = new SimpleDateFormat("dd-MM-yyyy");
                    datum = formatter.format(betaler.getReglementGetekendDatum());
                } catch (Exception e) {
                    logger.error("probleem bij ophalen van taal en datum uit object");
                    logger.error(e);
                    logger.error(e.getMessage());
                }
            }
            if (betalerPartner != null) {
                naamVrouw = betalerPartner.getNaam().toUpperCase() + " " + betalerPartner.getVoornaam().toUpperCase();
            }
            changeHeader = true;
        }
        if (changeHeader) {
            this.lblNameMan.setText(naamMan);
            this.lblNameWoman.setText(naamVrouw);
            getjLabelTaalBetaler().setText(taal);
            getjLabelDatumGetekend().setText(datum);
        }
        setPhotoInStandplaats();
        getController().updateDataForHeader();
    }

    private void initBtnClear() {
        btnClear = new JButton();
        btnClear.setText("Wis");
        Dimension dim = new Dimension(panelGroundDim.width / 2, panelGroundDim.height);
        btnClear.setSize(dim);
        btnClear.setPreferredSize(dim);
        btnClear.addActionListener(new ButtonClearHandler());
    }

    public class ButtonClearHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            jComboBoxGroundNumber.setSelectedItem("");
            jComboBoxGroundNumber.requestFocus();
        }
    }

    private JPanel getClearSearchPanel() {
        JPanel panelClearSearch = new JPanel();
        panelClearSearch.setLayout(new GridLayout(1, 2));
        Dimension dim = new Dimension(panelGroundDim.width / 2, panelGroundDim.height);
        panelClearSearch.setSize(dim);
        panelClearSearch.add(btnClear);
        return panelClearSearch;
    }

    private void setLblNameMan(String nameMan) {
        this.lblNameMan.setText(nameMan);
    }

    private void setLblNameWoman(String nameWoman) {
        this.lblNameWoman.setText(nameWoman);
    }

    public void setTabName(String tabname, boolean ok) {
        lblTabName.setText(tabname);
        if (ok) {
            lblTabName.setForeground(null);
        } else {
            lblTabName.setForeground(colorErrorTab);
        }
    }

    public void setTabNameColor(boolean ok) {
        if (ok) {
            lblTabName.setForeground(null);
        } else {
            lblTabName.setForeground(colorErrorTab);
        }
    }

    public void setBetalersInHeader(String hoofdBetaler, String betaler) {
        if (!StringUtils.isEmpty(hoofdBetaler))
            hoofdBetaler = hoofdBetaler.toUpperCase();
        if (!StringUtils.isEmpty(betaler))
            betaler = betaler.toUpperCase();
        setLblNameMan(hoofdBetaler);
        setLblNameWoman(betaler);
    }

    private JPanel getjPanelOwnerData() {
        if (jPanelOwnerData == null) {
            jPanelOwnerData = new JPanel();
            jPanelOwnerData.setLayout(new BorderLayout());
            jPanelOwnerData.add(getjLabelTaalBetaler(), BorderLayout.WEST);
            jPanelOwnerData.add(getjLabelDatumGetekend(), BorderLayout.EAST);
        }
        return jPanelOwnerData;
    }

    private JLabel getjLabelTaalBetaler() {
        if (jLabelTaalBetaler == null) {
            jLabelTaalBetaler = new JLabel();
            Border paddingBorder = BorderFactory.createEmptyBorder(0, 10, 0, 0);
            jLabelTaalBetaler.setBorder(paddingBorder);
        }
        return jLabelTaalBetaler;
    }

    private JLabel getjLabelDatumGetekend() {
        if (jLabelDatumGetekend == null) {
            jLabelDatumGetekend = new JLabel();
            Border paddingBorder = BorderFactory.createEmptyBorder(0, 0, 0, 10);
            jLabelDatumGetekend.setBorder(paddingBorder);
        }
        return jLabelDatumGetekend;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == jComboBoxGroundNumber) {
            String standplaats = Integer.toString(getStandplaatsDTO().getPlaatsnummer());
            while (standplaats.length() < 3) {
                standplaats = "0" + standplaats;
            }
            standplaats = getStandplaatsDTO().getPlaatsgroep() + standplaats;
            if (!standplaats.equals(jComboBoxGroundNumber.getSelectedItem()))
                zoekStandplaats();
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == jComboBoxGroundNumber) {
            String standplaats = Integer.toString(getStandplaatsDTO().getPlaatsnummer());
            while (standplaats.length() < 3) {
                standplaats = "0" + standplaats;
            }
            standplaats = getStandplaatsDTO().getPlaatsgroep() + standplaats;
            if (!standplaats.equals(jComboBoxGroundNumber.getSelectedItem()))
                zoekStandplaats();
        }
    }

    public class StandplaatsFocus implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (e.getSource() == jComboBoxGroundNumber.getAutoTextFieldEditor().getAutoTextFieldEditor()) {
                String standplaats = Integer.toString(getStandplaatsDTO().getPlaatsnummer());
                while (standplaats.length() < 3) {
                    standplaats = "0" + standplaats;
                }
                standplaats = getStandplaatsDTO().getPlaatsgroep() + standplaats;
                if (!standplaats.equals(jComboBoxGroundNumber.getSelectedItem()))
                    zoekStandplaats();
            }
        }
    }

    private JPanel getPanelSmallButtons() {
        JPanel panelSmallButtons = new JPanel();
        panelSmallButtons.setLayout(new GridLayout(2, 1));
        Dimension dim = new Dimension(30, 120);
        panelSmallButtons.setPreferredSize(dim);
        panelSmallButtons.setMaximumSize(dim);
        panelSmallButtons.add(buttonUpOffset, null);
        panelSmallButtons.add(buttonDownOffset, null);
        panelSmallButtons.setBorder(border);
        return panelSmallButtons;
    }

    private JPanel getPanelGroundNumber() {
        GridLayout gridLayout1 = new GridLayout(2, 1);
        gridLayout1.setRows(2);
        JPanel panelGroundNumber = new JPanel();
        panelGroundNumber.setLayout(gridLayout1);
        panelGroundNumber.setBorder(border);
        Dimension dim = new Dimension(75, 120);
        panelGroundNumber.setPreferredSize(dim);
        panelGroundNumber.setMaximumSize(dim);
        panelGroundNumber.add(jComboBoxGroundNumber);
        panelGroundNumber.add(getClearSearchPanel());
        return panelGroundNumber;
    }

    private JPanel getPanelBigButtons() {
        JPanel panelBigButtons = new JPanel();
        panelBigButtons.setLayout(new GridLayout(2, 1));
        Dimension dim = new Dimension(30, 120);
        panelBigButtons.setPreferredSize(dim);
        panelBigButtons.setMaximumSize(dim);
        panelBigButtons.add(buttonUp, null);
        panelBigButtons.add(buttonDown, null);
        panelBigButtons.setBorder(border);
        return panelBigButtons;
    }

    private JPanel getPanelOwnerNames() {
        JPanel panelOwnerNames = new JPanel();
        panelOwnerNames.setLayout(new GridLayout(3, 1));
        Dimension dim = new Dimension(280, 120);
        panelOwnerNames.setPreferredSize(dim);
        panelOwnerNames.setMinimumSize(dim);
        panelOwnerNames.setBorder(border);
        lblNameMan = new JLabel();
        lblNameMan.setFont(labelFont);
        lblNameMan.setHorizontalAlignment(JLabel.CENTER);
        lblNameWoman = new JLabel();
        lblNameWoman.setHorizontalAlignment(JLabel.CENTER);
        lblNameWoman.setFont(labelFont);
        panelOwnerNames.add(lblNameMan);
        panelOwnerNames.add(lblNameWoman);
        panelOwnerNames.add(getjPanelOwnerData());
        return panelOwnerNames;
    }

    private JPanel getPanelDate() {
        if (panelDate == null) {
            panelDate = new JPanel();
            panelDate.setLayout(new GridLayout(1, 1));
            Dimension dim = new Dimension(185, 120);
            panelDate.setPreferredSize(dim);
            panelDate.setMaximumSize(dim);
            panelDate.setBorder(border);
            ClockPanel clockPanel = new ClockPanel();
            panelDate.add(clockPanel);
        }
        return panelDate;
    }


    private JPanel getCentralPanel() {
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new GridLayout2(1, 6));
        centralPanel.add(getPanelSmallButtons());
        centralPanel.add(getPanelGroundNumber());
        centralPanel.add(getPanelBigButtons());
        centralPanel.add(getPanelOwnerNames());
        centralPanel.add(getPanelDate());
        return centralPanel;
    }

    private JPanel initPanelStandplaatsPhoto() {
        panelStandplaatsPhoto = new JPanel();
        panelStandplaatsPhoto.setLayout(new BorderLayout());
        Dimension dim = new Dimension(138, 120);
        panelStandplaatsPhoto.setPreferredSize(dim);
        panelStandplaatsPhoto.setBorder(new LineBorder(Color.BLACK, 2));
        panelStandplaatsPhoto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getComponent().isEnabled() && e.getClickCount() == 2) {
                    ConfiguratieDTO configuratie = configuratieService.getFileDirectory();
                    String dir = configuratie.getWaarde() + "/standplaats/" + getFullStandplaatsNaam(getStandplaatsDTO());
                    dir += "/hoofdfoto/groot/";
                    File file = new File(dir);
                    File[] fotobestanden = file.listFiles();
                    File orig = fotobestanden[0];
                    Image warnImage = ImageLoader.getImage(orig.getAbsolutePath());
                    Icon warnIcon = new ImageIcon(warnImage);
                    JLabel warnLabel = new JLabel(warnIcon);
                    JPanel jPanel = new JPanel();
                    jPanel.setPreferredSize(new Dimension(933, 700));
                    jPanel.add(warnLabel);
                    Object[] array = {jPanel};
                    JOptionPane.showConfirmDialog(null, array, "", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
                }
            }
        });
        return panelStandplaatsPhoto;
    }

    public Java2sAutoComboBox getjComboBoxGroundNumber() {
        return jComboBoxGroundNumber;
    }

    private void initJCboGroundNumber() {
        jComboBoxGroundNumber = new Java2sAutoComboBox(new ArrayList<>());
        jComboBoxGroundNumber.setSize(panelGroundDim);
        jComboBoxGroundNumber.setPreferredSize(panelGroundDim);
        jComboBoxGroundNumber.setFont(labelFont);
        jComboBoxGroundNumber.getAutoTextFieldEditor().getAutoTextFieldEditor().setHorizontalAlignment(SwingConstants.CENTER);
        jComboBoxGroundNumber.addActionListener(new ZoekGrondHandler());
        jComboBoxGroundNumber.addEditorFocusListener(standplaatsFocus);
    }
}