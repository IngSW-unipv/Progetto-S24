package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class StrategistView extends AbsView {

    private JFrame frame;
    private JPanel mainContainer, titlePanel, lapPanel, timePanel, buttonPanel;
    private JButton getTimeButton, createStrategyButton;
    private JLabel countLapLabel, imgWeatherLabel, imgCircuitLabel, dataLabel;
    private ImageIcon imgWeather, imgCircuit;
    private DefaultTableModel tabTime;
    private JTable tab;
    
    public StrategistView() {
        // Setup base components
        frame = new JFrame("STRATEGIST");
        frame.setSize(900, 800);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.BLACK);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        ImageIcon icona = new ImageIcon(getClass().getResource("/F1-Logo.png"));
        frame.setIconImage(icona.getImage());

        mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setBackground(Color.BLACK);

        // METEO E LAP
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(700, 200));
        titlePanel.setBackground(Color.BLACK);

        try {
            imgWeather = new ImageIcon(this.getClass().getResource("/meteo.png"));
            imgWeather = new ImageIcon(imgWeather.getImage().getScaledInstance(250, 250, java.awt.Image.SCALE_SMOOTH));
        } catch (Exception e) {
            System.out.println(e);
        }

        imgWeatherLabel = new JLabel(imgWeather);
        titlePanel.add(imgWeatherLabel, BorderLayout.WEST);

        lapPanel = new JPanel();
        lapPanel.setPreferredSize(new Dimension(250, 250));
        lapPanel.setLayout(new GridBagLayout());
        lapPanel.setBackground(Color.BLACK);
        lapPanel.setOpaque(true);
        GridBagConstraints gbcs = new GridBagConstraints();
        gbcs.insets = new Insets(20, 20, 60, 20);
        gbcs.anchor = GridBagConstraints.CENTER;
        Dimension dims = new Dimension(300, 200);

        gbcs.gridx = 0;
        gbcs.gridy = 0;
        dataLabel = new JLabel("TOTAL LAP");
        dataLabel.setForeground(Color.WHITE);
        dataLabel.setBackground(Color.BLACK);
        lapPanel.add(dataLabel, gbcs);

        gbcs.gridx = 0;
        gbcs.gridy = 1;
        countLapLabel = new JLabel();
        countLapLabel.setForeground(Color.WHITE);
        countLapLabel.setBackground(Color.BLACK);
        lapPanel.add(countLapLabel, gbcs);
        
        titlePanel.add(lapPanel, BorderLayout.EAST);

        // TABELLA TEMPO
        timePanel = new JPanel();
        timePanel.setLayout(new BorderLayout());
        timePanel.setPreferredSize(new Dimension(700, 200));
        timePanel.setBackground(Color.BLACK);

        String[] column = {"TIME SECTOR 1", "TIME SECTOR 2", "TIME SECTOR 3", "TIME LAP"};
        tabTime = new DefaultTableModel(column, 0);
         tab = new JTable(tabTime);
        JScrollPane scroll = new JScrollPane(tab);
        scroll.getViewport().setBackground(Color.BLACK);
        scroll.setBackground(Color.BLACK);

        timePanel.add(scroll, BorderLayout.CENTER);

        // PANNELLO DEI BOTTONI
        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setPreferredSize(new Dimension(700, 100));
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 50, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        Dimension dim = new Dimension(700, 200);

        gbc.gridx = 0;
        gbc.gridy = 0;
        getTimeButton = new JButton("GET TIME");
        getTimeButton.setPreferredSize(dim);
        buttonPanel.add(getTimeButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        createStrategyButton = new JButton("CREATE STRATEGY");
        createStrategyButton.setPreferredSize(dim);
        buttonPanel.add(createStrategyButton, gbc);

        mainContainer.add(titlePanel, BorderLayout.NORTH);
        mainContainer.add(timePanel, BorderLayout.CENTER);
        mainContainer.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainContainer);
        frame.setVisible(true);
        frame.validate();
        frame.repaint();
    }

 // Metodo per aggiungere righe
    public void addRow(String t1, String t2, String t3, String t4) {
        Object[] newRow = { t1, t2, t3, t4 };
        tabTime.addRow(newRow);

        tab.revalidate(); // Ricalcola la struttura della tabella
        tab.repaint();    // Ridisegna la tabella
        
        
    }

    public void colorCell(int thresholdSector1, int thresholdSector2, int thresholdSector3) {
    	
        tab.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (value != null) {
                    try {
                        // Converte il formato "00:00.000" in millisecondi
                        String[] parts = value.toString().split("[:.]");
                        int minutes = Integer.parseInt(parts[0]);
                        int seconds = Integer.parseInt(parts[1]);
                        int milliseconds = Integer.parseInt(parts[2]);
                        int totalMillis = (minutes * 60 * 1000) + (seconds * 1000) + milliseconds;

                        // Controlla se è l'ultima riga della tabella
                        
                            // Applica la logica in base alla colonna
                            if (column == 0 && totalMillis <= thresholdSector1) {
                                cell.setBackground(Color.MAGENTA);
                                cell.setForeground(Color.WHITE);
                            } else if (column == 1 && totalMillis <= thresholdSector2) {
                                cell.setBackground(Color.MAGENTA);
                                cell.setForeground(Color.WHITE);
                            } else if (column == 2 && totalMillis <= thresholdSector3) {
                                cell.setBackground(Color.MAGENTA);
                                cell.setForeground(Color.WHITE);
                            } else if (column == 3 ) {
                                cell.setBackground(Color.GRAY);
                                cell.setForeground(Color.WHITE);
                            } else {
                                cell.setBackground(Color.BLACK);
                                cell.setForeground(Color.WHITE);
                            }
                       
                    } catch (Exception e) {
                        // Celle vuote o non valide
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

    // Getter e Setter
    public JPanel getTitlePanel() {
        return titlePanel;
    }

    public void setTitlePanel(JPanel titlePanel) {
        this.titlePanel = titlePanel;
    }

    public JPanel getLapPanel() {
		return lapPanel;
	}

	public void setLapPanel(JPanel lapPanel) {
		this.lapPanel = lapPanel;
	}

	public JPanel getTimePanel() {
        return timePanel;
    }

    public void setTimePanel(JPanel timePanel) {
        this.timePanel = timePanel;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public void setButtonPanel(JPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    public JLabel getCountLapLabel() {
		return countLapLabel;
	}

	public void setCountLapLabel(JLabel countLapLabel) {
		int cLap = tab.getRowCount();
		String c = String.valueOf(cLap);
		countLapLabel.setText(c);
	}

	public JButton getGetTimeButton() {
        return getTimeButton;
    }

    public void setGetTimeButton(JButton getTimeButton) {
        this.getTimeButton = getTimeButton;
    }

    public JButton getCreateStrategyButton() {
        return createStrategyButton;
    }

    public void setCreateStrategyButton(JButton createStrategyButton) {
        this.createStrategyButton = createStrategyButton;
    }

    public JLabel getDataLabel() {
        return dataLabel;
    }

    public void setDataLabel(JLabel dataLabel) {
        this.dataLabel = dataLabel;
    }
}
