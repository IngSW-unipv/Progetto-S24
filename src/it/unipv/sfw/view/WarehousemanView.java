package it.unipv.sfw.view;

import java.awt.*;

import javax.swing.*;

import it.unipv.sfw.controller.Observer;

/**
 * Vista per il magazziniere.
 * Permette di visualizzare e gestire le richieste, e aggiornare i componenti.
 */
public class WarehousemanView extends AbsView implements Observer {
    private JFrame frame;
    private JPanel mainContainer, titlePanel, popUpPanel, mexPanel;
    private JButton showRequestButton, deleteRequestButton, updateCompoButton;
    private JComboBox<String> comboBox;
    private JLabel imgLabel, dataLabel, mex;
    private ImageIcon imgUser, imgWllp2;
    private JTextField placeholder;

    private int totalRequests = 0; // Totale richieste
    private String name;          // Nome utente
    private String surname;       // Cognome utente
    
    /**
     * Costruttore della vista per il magazziniere.
     * Inizializza e posiziona i componenti grafici.
     */
    public WarehousemanView() {
        frame = new JFrame("WAREHOUSEMAN");
        frame.setSize(718, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        ImageIcon icona = new ImageIcon(getClass().getResource("/F1-Logo.png"));
        frame.setIconImage(icona.getImage());

        /*
         * Creazione titolo e immagine
         */
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(718, 200));

        try {
            imgWllp2 = new ImageIcon(this.getClass().getResource("/wallpaper.jpg"));
            imgWllp2 = new ImageIcon(imgWllp2.getImage().getScaledInstance(718, 400, java.awt.Image.SCALE_SMOOTH));

            imgUser = new ImageIcon(this.getClass().getResource("/icon.png"));
            imgUser = new ImageIcon(imgUser.getImage().getScaledInstance(320, 370, java.awt.Image.SCALE_SMOOTH));
        } catch (Exception e) {
            System.out.println(e);
        }

        mainContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imgWllp2.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        mainContainer.setLayout(new BorderLayout());
        imgLabel = new JLabel(imgUser);
        titlePanel.add(imgLabel, BorderLayout.WEST);
        titlePanel.setOpaque(false);

        dataLabel = new JLabel();
        dataLabel.setForeground(Color.BLACK);
        titlePanel.add(dataLabel, BorderLayout.CENTER);

        /*
         * Creazione pulsanti e componenti
         */
        popUpPanel = new JPanel();
        popUpPanel.setPreferredSize(new Dimension(718, 200));
        popUpPanel.setLayout(new GridBagLayout());
        popUpPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 50, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        Dimension dim = new Dimension(700, 200);
        gbc.gridx = 0;
        gbc.gridy = 0;

        showRequestButton = new JButton("SHOW REQUESTS");
        showRequestButton.setPreferredSize(dim);
        popUpPanel.add(showRequestButton, gbc);

        gbc.gridx = 1;
        deleteRequestButton = new JButton("DELETE REQUEST");
        deleteRequestButton.setPreferredSize(dim);
        popUpPanel.add(deleteRequestButton, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        updateCompoButton = new JButton("UPDATE COMPONENT");
        updateCompoButton.setPreferredSize(dim);
        popUpPanel.add(updateCompoButton, gbc);

        gbc.gridx = 1;
        String[] option = {
            "RUOTA ANTERIORE SX HARD", "RUOTA ANTERIORE DX HARD",
            "RUOTA POSTERIORE SX HARD", "RUOTA POSTERIORE DX HARD",
            "RUOTA ANTERIORE SX MEDIUM", "RUOTA ANTERIORE DX MEDIUM",
            "RUOTA POSTERIORE SX MEDIUM", "RUOTA POSTERIORE DX MEDIUM",
            "ALA ANTERIORE", "DRS", "MOTORE TERMICO", "ERS", "- ALL"
        };

        comboBox = new JComboBox<>(option);
        comboBox.setPreferredSize(dim);
        comboBox.setEditable(true);
        placeholder = (JTextField) comboBox.getEditor().getEditorComponent();
        placeholder.setText("QUANTITA' COMPONENTI");

        popUpPanel.add(comboBox, gbc);

        /*
         * Creazione sezione messaggi
         */
        mexPanel = new JPanel();
        mexPanel.setOpaque(false);

        mex = new JLabel("SELECT A COMPONENT FROM THE MENU");
        mex.setHorizontalAlignment(SwingConstants.CENTER);
        mex.setPreferredSize(new Dimension(718, 200));
        mex.setForeground(Color.BLACK);

        mexPanel.add(mex);

        mainContainer.add(titlePanel, BorderLayout.NORTH);
        mainContainer.add(popUpPanel, BorderLayout.CENTER);
        mainContainer.add(mexPanel, BorderLayout.SOUTH);

        frame.add(mainContainer);
        frame.setVisible(true);
    }
    
    /**
     * Aggiorna la vista con il numero totale di richieste.
     * @param totalRequests Il numero totale di richieste.
     */
    @Override
    public void update(int totalRequests) {
        this.totalRequests = totalRequests;
        refresh(name, surname, totalRequests);
    }
    
    /**
     * Aggiorna le etichette con i dati dell'utente e il numero di richieste.
     * @param name Il nome dell'utente.
     * @param surname Il cognome dell'utente.
     * @param totalRequests Il numero totale di richieste.
     */
    private void refresh(String name, String surname, int totalRequests) {
    	dataLabel.setText("NAME:        "+ name+ "        SURNAME:        " +surname+ "       TOTAL REQUEST: " +totalRequests);
        System.out.println("Updated view with " + totalRequests + " requests.");
    }
    
    /**
     * Restituisce il pulsante per visualizzare le richieste.
     * @return Il pulsante per visualizzare le richieste.
     */
    public JButton getShowRequestButton() {
        return showRequestButton;
    }
    
    /**
     * Restituisce il pulsante per eliminare le richieste.
     * @return Il pulsante per eliminare le richieste.
     */
    public JButton getDeleteRequestButton() {
        return deleteRequestButton;
    }
    
    /**
     * Restituisce il pulsante per aggiornare i componenti.
     * @return Il pulsante per aggiornare i componenti.
     */
    public JButton getUpdateCompoButton() {
        return updateCompoButton;
    }
    
    /**
     * Restituisce la casella combinata per la selezione dei componenti.
     * @return La casella combinata per la selezione dei componenti.
     */
    public JComboBox<String> getCombobox() {
        return comboBox;
    }
    
    /**
     * Imposta il testo del messaggio principale.
     */
    public void setMex() {
		mex.setText("SELECT A COMPONENT FROM THE MENU");
		mex.setForeground(Color.BLACK);
	}
    
    /**
     * Imposta i dati dell'utente e il numero di richieste.
     * @param name Il nome dell'utente.
     * @param surname Il cognome dell'utente.
     * @param totalRequests Il numero totale di richieste.
     */
    public void data(String name, String surname, int totalRequests) {
        this.name = name;
        this.surname = surname;
        this.totalRequests = totalRequests;
        dataLabel.setText("NAME:        "+ name+ "        SURNAME:        " +surname+ "        TOTAL REQUEST: " +totalRequests);
    }
    
    /**
     * Imposta il testo del messaggio principale con il nome del componente e la quantità.
     * @param quantity La quantità del componente.
     */
	public void mexCombo(int quantity) {
		String currentText = placeholder.getText();
		mex.setText("NAME COMPONENT:  " + currentText + "             QUANTITY:  " + quantity);
		mex.setForeground(Color.BLACK);
	}
}
