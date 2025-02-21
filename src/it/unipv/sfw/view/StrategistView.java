package it.unipv.sfw.view;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Vista per lo stratega.
 * Permette allo stratega di visualizzare i tempi sul giro, il meteo, il circuito
 * e di creare una strategia.
 */
public class StrategistView extends AbsView {

    private JFrame frame;
    private JPanel mainContainer, titlePanel, lapPanel, timePanel,addVehiclePanel,sendBtnPanel, GetTimebtnPanel;
    private JButton getTimeButton, createStrategyButton, sendButton;
    private JLabel countLapLabel, imgWeatherLabel, imgCircuitLabel, dataLabel, msnLabel, mex;
    private JTextField vehicleField;
	private ImageIcon imgWeather, imgCircuit;
    private DefaultTableModel tabTime;
    private JTable tab;

    /**
     * Costruttore della vista per lo stratega.
     * Inizializza e posiziona i componenti grafici.
     */
    public StrategistView() {
        // Setup base frame
        frame = new JFrame("STRATEGIST");
        frame.setSize(1100, 800); // Frame più grande per gestire le immagini
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        ImageIcon icona = new ImageIcon(getClass().getResource("/F1-Logo.png"));
        frame.setIconImage(icona.getImage());

        // Main container setup
        mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setBackground(Color.BLACK);

        // Title panel setup
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(1100, 350)); // Dimensione adattata
        titlePanel.setOpaque(false);

        try {
            imgWeather = new ImageIcon(this.getClass().getResource("/meteo.png"));
            imgWeather = new ImageIcon(imgWeather.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

            imgCircuit = new ImageIcon(this.getClass().getResource("/circuit_img.png"));
            imgCircuit = new ImageIcon(imgCircuit.getImage().getScaledInstance(600, 300, java.awt.Image.SCALE_SMOOTH));
        } catch (Exception e) {
            System.out.println(e);
        }

        imgWeatherLabel = new JLabel(imgWeather);
        imgWeatherLabel.setPreferredSize(new Dimension(150, 150));

        imgCircuitLabel = new JLabel(imgCircuit);
        imgCircuitLabel.setPreferredSize(new Dimension(600, 300));

        titlePanel.add(imgWeatherLabel, BorderLayout.WEST);
        titlePanel.add(imgCircuitLabel, BorderLayout.CENTER);

        // Lap panel setup
        lapPanel = new JPanel();
        lapPanel.setPreferredSize(new Dimension(250, 300));
        lapPanel.setLayout(new GridBagLayout());
        lapPanel.setOpaque(false);

        GridBagConstraints gbcLap = new GridBagConstraints();
        gbcLap.insets = new Insets(10, 10, 20, 10);
        gbcLap.anchor = GridBagConstraints.CENTER;

        gbcLap.gridx = 0;
        gbcLap.gridy = 0;
        dataLabel = new JLabel("TOTAL LAP");
        dataLabel.setForeground(Color.WHITE);
        lapPanel.add(dataLabel, gbcLap);

        gbcLap.gridy = 1;
        countLapLabel = new JLabel();
        countLapLabel.setForeground(Color.WHITE);
        lapPanel.add(countLapLabel, gbcLap);

        titlePanel.add(lapPanel, BorderLayout.EAST);
        
        addVehiclePanel = new JPanel(new GridBagLayout());
        addVehiclePanel.setPreferredSize(new Dimension(700,350));
        addVehiclePanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // Spaziatura interna
        gbc.insets = new Insets(10, 10, 10, 10);

        Dimension dimField = new Dimension(100, 30);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        msnLabel = new JLabel("Insert MSN");
        msnLabel.setForeground(Color.WHITE);
        msnLabel.setPreferredSize(dimField);
        addVehiclePanel.add(msnLabel, gbc);   
        
        gbc.gridx = 2;
        gbc.gridy = 0;
        vehicleField = new JTextField("MSN");
        vehicleField.setPreferredSize(dimField);
        addVehiclePanel.add(vehicleField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Occupa entrambe le colonne
        gbc.fill = GridBagConstraints.HORIZONTAL;
        sendBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sendBtnPanel.setOpaque(false);
        Dimension dimBtn = new Dimension(100, 30);
        sendButton = new JButton("SEND");
        sendButton.setPreferredSize(dimBtn);
        sendBtnPanel.add(sendButton);
        addVehiclePanel.add(sendBtnPanel, gbc);
        
        gbc.gridy = 1;
        mex = new JLabel("", SwingConstants.CENTER);
        addVehiclePanel.add(mex,gbc);
        
        // Time panel setup
        timePanel = new JPanel(new BorderLayout());
        timePanel.setPreferredSize(new Dimension(700, 350));
        timePanel.setBackground(Color.BLACK);

        String[] column = {"TIME SECTOR 1", "TIME SECTOR 2", "TIME SECTOR 3", "TIME LAP"};
        tabTime = new DefaultTableModel(column, 0);
        tab = new JTable(tabTime);
        JScrollPane scroll = new JScrollPane(tab);
        scroll.getViewport().setBackground(Color.BLACK);
        scroll.setBackground(Color.BLACK);

        timePanel.add(scroll, BorderLayout.CENTER);

        // Button panel setup
        GetTimebtnPanel = new JPanel(new GridBagLayout());
        GetTimebtnPanel.setBackground(Color.BLACK);
        GetTimebtnPanel.setPreferredSize(new Dimension(1100, 150));
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.insets = new Insets(10, 10, 20, 10);
        gbcButton.anchor = GridBagConstraints.CENTER;

        gbcButton.gridx = 0;
        gbcButton.gridy = 0;
        getTimeButton = new JButton("GET TIME");
        GetTimebtnPanel.add(getTimeButton, gbcButton);

        gbcButton.gridx = 1;
        createStrategyButton = new JButton("CREATE STRATEGY");
        GetTimebtnPanel.add(createStrategyButton, gbcButton);

        // Assemble panels
        mainContainer.add(titlePanel, BorderLayout.NORTH);
        mainContainer.add(timePanel, BorderLayout.CENTER);
        mainContainer.add(addVehiclePanel, BorderLayout.CENTER);
        mainContainer.add(GetTimebtnPanel, BorderLayout.SOUTH);

        frame.add(mainContainer);
        frame.setVisible(true);
        frame.validate();
        frame.repaint();
        
        vehicleField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (vehicleField.getText().equals("MSN")) {
					vehicleField.setText(""); // Rimuove il testo predefinito
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (vehicleField.getText().isEmpty()) {
					vehicleField.setText("MSN"); // Ripristina il testo predefinito se vuoto
				}
			}
		});
    }

	/**
     * Aggiunge una riga alla tabella dei tempi.
     * @param t1 Tempo settore 1
     * @param t2 Tempo settore 2
     * @param t3 Tempo settore 3
     * @param t4 Tempo sul giro
     */
    public void addRow(String t1, String t2, String t3, String t4) {
        Object[] newRow = {t1, t2, t3, t4};
        tabTime.addRow(newRow);
        tab.revalidate();
        tab.repaint();
    }

    /**
     * Colora le celle della tabella in base a delle soglie.
     * @param thresholdSector1 Soglia per il settore 1
     * @param thresholdSector2 Soglia per il settore 2
     * @param thresholdSector3 Soglia per il settore 3
     */
    public void colorCell(int thresholdSector1, int thresholdSector2, int thresholdSector3) {
        tab.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (value != null) {
                    try {
                        String[] parts = value.toString().split("[:.]");
                        int minutes = Integer.parseInt(parts[0]);
                        int seconds = Integer.parseInt(parts[1]);
                        int milliseconds = Integer.parseInt(parts[2]);
                        int totalMillis = (minutes * 60 * 1000) + (seconds * 1000) + milliseconds;

                        if (column == 0 && totalMillis <= thresholdSector1 ||
                                column == 1 && totalMillis <= thresholdSector2 ||
                                column == 2 && totalMillis <= thresholdSector3) {
                            cell.setBackground(Color.MAGENTA);
                            cell.setForeground(Color.WHITE);
                        } else if (column == 3) {
                            cell.setBackground(Color.GRAY);
                            cell.setForeground(Color.WHITE);
                        } else {
                            cell.setBackground(Color.BLACK);
                            cell.setForeground(Color.WHITE);
                        }
                    } catch (Exception e) {
                        cell.setBackground(Color.BLACK);
                        cell.setForeground(Color.WHITE);
                    }
                } else {
                    cell.setBackground(Color.BLACK);
                    cell.setForeground(Color.WHITE);
                }

                return cell;
            }
        });
    }
    
    public void showElement() {
        msnLabel.setEnabled(false);
        msnLabel.setVisible(false);
        
        vehicleField.setEnabled(false);
        vehicleField.setVisible(false);
        
        sendButton.setEnabled(false);
        sendButton.setVisible(false);
        
        mex.setEnabled(false);
        mex.setVisible(false);
        
    	addVehiclePanel.setEnabled(false);
    	addVehiclePanel.setVisible(false);
    	
        tab.setEnabled(true);
        tab.setVisible(true);
        
       createStrategyButton.setEnabled(true);
       createStrategyButton.setVisible(true);       
        
       getTimeButton.setEnabled(true);
       getTimeButton.setVisible(true);
    }
    
    public JPanel getAddVehiclePanel() {
		return addVehiclePanel;
	}

	public void setAddVehiclePanel(JPanel addVehiclePanel) {
		this.addVehiclePanel = addVehiclePanel;
	}

	public void mex() {
    	mex.setText("ERROR VEHICLE NOT FOUND");
    	mex.setForeground(Color.RED);
    }
    
    public JTextField getVehicleField() {
		return vehicleField;
	}

	public void setVehicleField(JTextField vehicleField) {
		this.vehicleField = vehicleField;
	}
    
    /**
     * Restituisce il bottone per inviare i  dati
     * @return il bottone per inviare i  dati.
     */
    public JButton getSendButton() {
		return sendButton;
	}
    
    /**
     * Imposta il bottone per inviare i  dati
     * @param getTimeButton Il bottone per per inviare i  dati.
     */
	public void setSendButton(JButton sendButton) {
		this.sendButton = sendButton;
	}
    
    /**
     * Restituisce il label per il conteggio dei giri.
     * @return Il label per il conteggio dei giri.
     */
    public JLabel getCountLapLabel() {
        return countLapLabel;
    }

    /**
     * Imposta il conteggio dei giri nel label.
     */
    public void setCountLapLabel(JLabel countLapLabel) {
        int cLap = tab.getRowCount();
        countLapLabel.setText(String.valueOf(cLap));
    }

    /**
     * Restituisce la tabella dei tempi.
     * @return La tabella dei tempi.
     */
    public JTable getTab() {
        return tab;
    }

    /**
     * Imposta la tabella dei tempi.
     * @param tab La tabella dei tempi da impostare.
     */
    public void setTab(JTable tab) {
        this.tab = tab;
    }

    /**
     * Restituisce il bottone per ottenere i tempi.
     * @return Il bottone per ottenere i tempi.
     */
    public JButton getGetTimeButton() {
        return getTimeButton;
    }

    /**
     * Imposta il bottone per ottenere i tempi.
     * @param getTimeButton Il bottone per ottenere i tempi da impostare.
     */
    public void setGetTimeButton(JButton getTimeButton) {
        this.getTimeButton = getTimeButton;
    }

    /**
     * Restituisce il bottone per creare una strategia.
     * @return Il bottone per creare una strategia.
     */
    public JButton getCreateStrategyButton() {
        return createStrategyButton;
    }

    /**
     * Imposta il bottone per creare una strategia.
     * @param createStrategyButton Il bottone per creare una strategia da impostare.
     */
    public void setCreateStrategyButton(JButton createStrategyButton) {
        this.createStrategyButton = createStrategyButton;
    }
    
    
}
