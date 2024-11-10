package it.unipv.sfw.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class McGraphicTimePsView extends JPanel {

	private int width = 800;
	private int height = 400;
	private int padding = 25;
	private int labelPadding = 25;
	private Color lineColor = new Color(255, 255, 0, 180);
	private Color pointColor = new Color(255, 0, 0, 180);
	private Color gridColor = new Color(50, 50, 50, 200);
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	private int pointWidth = 8;
	private int numberYDivisions = 10;
	private ArrayList<Integer> scores;
	private JFrame frame;
	private JLabel messageLabel;
	private JPanel messagePanel;

	public McGraphicTimePsView(ArrayList<Integer> scores, ArrayList<String> labelTime) {
		this.scores = scores;
		
		// Inizializza la JFrame
		frame = new JFrame("GRAFICO TEMPI PIT STOP");
		frame.setPreferredSize(new Dimension(900,700));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		ImageIcon icona = new ImageIcon(getClass().getResource("/F1-Logo.png"));
		frame.setIconImage(icona.getImage());
		frame.setLayout(new GridLayout(2, 1));

		/*
		 *  Creo un JPanel anonimo con il metodo paintComponent sovrascritto.
		 *  L'uso di una classe anonima serve a implementare velocemente il metodo paintComponent()
		 *  per il pannello del grafico senza bisogno di definire una classe separata solo per il rendering grafico. 
		 *  Questo è utile soprattutto quando la classe viene utilizzata solo in un unico punto, come in questo caso.
		 */

		JPanel graphPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (scores.size() - 1);
				double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

				List<Point> graphPoints = new ArrayList<>();
				for (int i = 0; i < scores.size(); i++) {
					int x1 = (int) (i * xScale + padding + labelPadding);
					int y1 = (int) ((getMaxScore() - scores.get(i)) * yScale + padding);
					graphPoints.add(new Point(x1, y1));
				}

				// Disegna sfondo nero
				g2.setColor(Color.BLACK);
				g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding,
						getHeight() - 2 * padding - labelPadding);

				// Crea linee di griglia per l'asse y
				for (int i = 0; i < numberYDivisions + 1; i++) {
					int x0 = padding + labelPadding;
					int x1 = pointWidth + padding + labelPadding;
					int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions
							+ padding + labelPadding);
					int y1 = y0;
					if (scores.size() > 0) {
						g2.setColor(gridColor);
						g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
						g2.setColor(Color.BLACK);

						double yValue = getMinScore()
								+ (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions);
						yValue /= 1000.0;
						String yLabel = String.format("%.3f", yValue);

						FontMetrics metrics = g2.getFontMetrics();
						int labelWidth = metrics.stringWidth(yLabel);
						g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
					}
					g2.drawLine(x0, y0, x1, y1);
				}

				// Crea linee di griglia per l'asse x
				for (int i = 0; i < scores.size(); i++) {
					if (scores.size() > 1) {
						int x0 = i * (getWidth() - padding * 2 - labelPadding) / (scores.size() - 1) + padding
								+ labelPadding;
						int x1 = x0;
						int y0 = getHeight() - padding - labelPadding;
						int y1 = y0 - pointWidth;
						if ((i % ((int) ((scores.size() / 20.0)) + 1)) == 0) {
							g2.setColor(gridColor);
							g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
							g2.setColor(Color.BLACK);
							String xLabel = i + "";
							FontMetrics metrics = g2.getFontMetrics();
							int labelWidth = metrics.stringWidth(xLabel);
							g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
						}
						g2.drawLine(x0, y0, x1, y1);
					}
				}

				// Disegna assi x e y
				g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding,
						padding);
				g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding,
						getHeight() - padding - labelPadding);

				Stroke oldStroke = g2.getStroke();
				g2.setColor(lineColor);
				g2.setStroke(GRAPH_STROKE);

				// Disegna linee del grafico
				for (int i = 0; i < graphPoints.size() - 1; i++) {
					int x1 = graphPoints.get(i).x;
					int y1 = graphPoints.get(i).y;
					int x2 = graphPoints.get(i + 1).x;
					int y2 = graphPoints.get(i + 1).y;
					g2.drawLine(x1, y1, x2, y2);
				}

				g2.setStroke(oldStroke);
				g2.setColor(pointColor);

				// Disegna i punti e le etichette
				for (int i = 0; i < graphPoints.size(); i++) {
					int x = graphPoints.get(i).x - pointWidth / 2;
					int y = graphPoints.get(i).y - pointWidth / 2;
					int ovalW = pointWidth;
					int ovalH = pointWidth;
					g2.fillOval(x, y, ovalW, ovalH);

					String label = labelTime.get(i);
					FontMetrics metrics = g2.getFontMetrics();
					int labelWidth = metrics.stringWidth(label);
					g2.setColor(Color.RED);
					g2.drawString(label, x - labelWidth, y + metrics.getHeight() + 5);
					g2.setColor(pointColor);
				}
			}
		};

		graphPanel.setPreferredSize(new Dimension(width, height));
		this.add(graphPanel);
		
		// Pannello per il messaggio
	    messagePanel = new JPanel();
		messagePanel.setSize(new Dimension(80, 30));
		
		messageLabel = new JLabel("ABNORMAL PIT STOP TIMES: ", SwingConstants.LEFT);
		messageLabel.setSize(new Dimension(80, 30));
		
		messagePanel.add(messageLabel);
		frame.add(messagePanel);
		
		frame.repaint();
		
	}

	// Metodi per ottenere min e max punteggi
	private double getMinScore() {
		double minScore = Double.MAX_VALUE;
		for (Integer score : scores) {
			minScore = Math.min(minScore, score);
		}
		return minScore;
	}

	private double getMaxScore() {
		double maxScore = Double.MIN_VALUE;
		for (Integer score : scores) {
			maxScore = Math.max(maxScore, score);
		}
		return maxScore;
	}
	
	public void show() {
		frame.setVisible(true);
	}

}
