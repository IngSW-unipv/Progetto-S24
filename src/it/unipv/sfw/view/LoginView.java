package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Classe che crea la view della pagina di login usata dagli utenti per accedere
 * al sistema.
 */
public class LoginView extends AbsView {

    private JPanel mainContainer, logoPanel, loginPanel, btnPanel, errorPanel;

    private JTextField username;

    private JPasswordField pwd;

    private JLabel imgBackgroundLabel, imgLogoLabel, errLabel;

    private JButton loginBtn;

    private ImageIcon imgBackground, imgLogo;

    /**
     * Costruttore della classe LoginView.
     * Inizializza e posiziona i componenti grafici per la pagina di login.
     */
    public LoginView() {

        System.out.println("ora sono qui - @LOGINVIEW");

        // Caricamento immagine di sfondo
        try {
            imgBackground = new ImageIcon(this.getClass().getResource("/f1_login.jpg"));
            System.out.println("immagine wallpaper caricata");
        } catch (Exception e) {
            System.out.println(e);
        }

        imgBackgroundLabel = new JLabel(imgBackground);

        // Pannello principale
        mainContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Disegna l'immagine di sfondo
                g.drawImage(imgBackground.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(imgBackgroundLabel);

        // Prima sezione: Pannello del logo
        logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setBackground(Color.blue);

        // Caricamento e ridimensionamento immagine del logo
        try {
            imgLogo = new ImageIcon(this.getClass().getResource("/F1-Logo.png"));
            imgLogo = new ImageIcon(imgLogo.getImage().getScaledInstance(300, 100, java.awt.Image.SCALE_SMOOTH));
            System.out.println("immagine logo caricata");
        } catch (Exception e) {
            System.out.println(e);
        }

        imgLogoLabel = new JLabel(imgLogo);
        imgLogoLabel.setOpaque(false);
        imgLogoLabel.setPreferredSize(new Dimension(300, 200));
        imgLogoLabel.setHorizontalAlignment(JLabel.CENTER);
        imgLogoLabel.setIcon(imgLogo);

        // Seconda sezione: Pannello di login
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // Spaziatura interna
        gbc.insets = new Insets(10, 10, 10, 10);

        Dimension dimField = new Dimension(250, 50);

        // Campo username
        gbc.gridx = 1;
        gbc.gridy = 0;
        username = new JTextField("Insert ID");
        username.setPreferredSize(dimField);
        loginPanel.add(username, gbc);

        // Campo password
        gbc.gridx = 1;
        gbc.gridy = 1;
        pwd = new JPasswordField("Password");
        pwd.setPreferredSize(dimField);
        loginPanel.add(pwd, gbc);

        // Pulsante di login
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Occupa entrambe le colonne
        gbc.fill = GridBagConstraints.HORIZONTAL;
        btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setOpaque(false);
        Dimension dimBtn = new Dimension(100, 40);
        loginBtn = new JButton("LOGIN");
        loginBtn.setPreferredSize(dimBtn);
        btnPanel.add(loginBtn);
        loginPanel.add(btnPanel, gbc);

        // Terza sezione: Pannello di errore
        errorPanel = new JPanel();
        errLabel = new JLabel("Username o Password errati!");
        errLabel.setForeground(Color.RED);
        errorPanel.add(errLabel);
        errorPanel.setVisible(false);
        errorPanel.setBackground(Color.BLACK);

     // Aggiunta dei FocusListener per i campi di testo
     		// FocusListener per il campo username (JTextField)
     		username.addFocusListener((FocusListener) new FocusListener() {
     			@Override
     			public void focusGained(FocusEvent e) {
     				if (username.getText().equals("Insert ID")) {
     					username.setText(""); // Rimuove il testo predefinito
     				}
     			}

     			@Override
     			public void focusLost(FocusEvent e) {
     				if (username.getText().isEmpty()) {
     					username.setText("Insert ID"); // Ripristina il testo predefinito se vuoto
     				}
     			}
     		});

     		// FocusListener per il campo pwd (JPasswordField)
     		pwd.addFocusListener(new FocusListener() {
     			@Override
     			public void focusGained(FocusEvent e) {
     				String pwdText = new String(pwd.getPassword());
     				if (pwdText.equals("Password")) {
     					pwd.setText(""); // Rimuove il testo predefinito
     					pwd.setEchoChar('●'); // Imposta il carattere di mascheramento della password
     				}
     			}

     			@Override
     			public void focusLost(FocusEvent e) {
     				String pwdText = new String(pwd.getPassword());
     				if (pwdText.isEmpty()) {
     					pwd.setText("Password"); // Ripristina il testo predefinito se vuoto
     					pwd.setEchoChar((char) 0); // Rimuove il mascheramento della password
     				}
     			}
     		});

        // Assemblaggio dei pannelli
        logoPanel.add(imgLogoLabel);
        mainContainer.add(logoPanel, BorderLayout.NORTH);
        mainContainer.add(loginPanel, BorderLayout.CENTER);
        mainContainer.add(errorPanel, BorderLayout.SOUTH);

        this.setLayout(new BorderLayout()); // Imposta il layout di LoginView come BorderLayout
        this.add(mainContainer, BorderLayout.CENTER);
    }

    /**
     * Restituisce il pulsante di login.
     * @return Il pulsante di login.
     */
    public JButton getAccediButton() {
        return loginBtn;
    }

    /**
     * Restituisce il campo password.
     * @return Il campo password.
     */
    public JPasswordField getPasswordField() {
        return pwd;
    }

    /**
     * Restituisce il campo username.
     * @return Il campo username.
     */
    public JTextField getUsernameField() {
        return username;
    }

    /**
     * Metodo chiamato al caricamento della vista.
     * Nasconde il pannello di errore.
     */
    @Override
    public void onLoad() {
        errorPanel.setVisible(false);
    }

    /**
     * Mostra il messaggio di errore.
     */
    public void upError() {
        errorPanel.setVisible(true);
        mainContainer.repaint();
    }

}