package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import it.unipv.sfw.model.component.Components;

/**
 * Vista che mostra i dettagli dei componenti in una tabella.
 * Permette di visualizzare ID, nome e usura dei componenti, con evidenziazione
 * colorata in base al livello di usura.
 */
public class StGraphicDetailsView {
    private JFrame frame;

    private JPanel mainPanel;

    private JScrollPane panel;

    private JTable table;

    /**
     * Costruttore della vista.
     * @param components Insieme di componenti da visualizzare.
     */
    public StGraphicDetailsView(Set<Components> components) {

        frame = new JFrame("COMPONENT");
        frame.setSize(500, 500);
        frame.setBackground(Color.BLACK);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null); // Questo centra la finestra sullo schermo
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ImageIcon icona = new ImageIcon(getClass().getResource("/F1-Logo.png"));
        frame.setIconImage(icona.getImage());

        mainPanel = new JPanel();

        String[] column = { "ID", "NAME", "WEAR" };

        // Converto il set in Object perchè il costruttore della table lo pretende
        Object[][] data = new Object[components.size()][3];

        int row = 0;

        for (Components c : components) {
            data[row][0] = c.getIdComponent();
            data[row][1] = c.getName();
            data[row][2] = c.getWear();
            row++;
        }

        table = new JTable(data, column);

        // render della tabella in base al valore di "wear" cambia colore
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                // TODO Auto-generated method stub
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // CONDIZIONE: se il valore di wear è compreso in un intervallo 80-100 -> verde,
                // sennò arancione
                int wear = (Integer) table.getValueAt(row, 2);

                if (wear >= 80 && wear <= 100) {
                    cell.setBackground(Color.GREEN);
                } else {
                    cell.setBackground(Color.ORANGE);
                }

                cell.setForeground(Color.BLACK);

                return cell;
            }

        });

        panel = new JScrollPane(table);

        mainPanel.add(panel, BorderLayout.CENTER);

        frame.add(mainPanel);

    }

    /**
     * Mostra la finestra.
     */
    public void show() {
        frame.setVisible(true);
    }
}