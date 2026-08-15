import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
  
public class CarMaintenanceGUI extends JFrame {
 
	private static final Color BG = new Color(0xF3, 0xF5, 0xF8);
	private static final Color CARD_BG = Color.WHITE;
	private static final Color BORDER_COLOR = new Color(0xE0, 0xE3, 0xE8);
	private static final Color ACCENT = new Color(0x2D, 0x6C, 0xDF);
	private static final Color DUE_COLOR = new Color(0xD6, 0x45, 0x45);
	private static final Color OK_COLOR = new Color(0x2E, 0x9E, 0x5B);
	private static final Color TEXT_MUTED = new Color(0x6B, 0x72, 0x80);
	private static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 22);
	private static final Font FONT_CARD_TITLE = new Font("SansSerif", Font.BOLD, 16);
	private static final Font FONT_BODY = new Font("SansSerif", Font.PLAIN, 13);
	private static final Font FONT_STATUS = new Font("SansSerif", Font.BOLD, 13);
 
	private final Info info;
	private final Car car;
 
	private JLabel odometerValueLabel;
	private JTextField odometerField;
 
	private ServiceCard oilCard;
	private ServiceCard rotationCard;
	private ServiceCard registrationCard;
 
	public CarMaintenanceGUI() {
		this.info = new Info();
		this.car = new Car(info);
 
		setTitle("Car Maintenance Tracker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(480, 620));
		getContentPane().setBackground(BG);
		setLayout(new BorderLayout());
 
		add(buildHeader(), BorderLayout.NORTH);
		add(buildMainPanel(), BorderLayout.CENTER);
 
		refreshAll();
 
		pack();
		setLocationRelativeTo(null);
	}
 
	
 
	private JComponent buildHeader() {
		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
		header.setBackground(ACCENT);
		header.setBorder(new EmptyBorder(20, 24, 20, 24));
 
		JLabel title = new JLabel("Car Maintenance Tracker");
		title.setFont(FONT_TITLE);
		title.setForeground(Color.WHITE);
		title.setAlignmentX(Component.LEFT_ALIGNMENT);
 
		JLabel subtitle = new JLabel("Keep track of oil changes, tyre rotations, and registration renewal");
		subtitle.setFont(FONT_BODY);
		subtitle.setForeground(new Color(0xE3, 0xEC, 0xFF));
		subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
 
		header.add(title);
		header.add(Box.createVerticalStrut(4));
		header.add(subtitle);
		return header;
	}
 
	private JComponent buildMainPanel() {
		JPanel main = new JPanel();
		main.setBackground(BG);
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		main.setBorder(new EmptyBorder(20, 24, 24, 24));
 
		main.add(buildOdometerPanel());
		main.add(Box.createVerticalStrut(18));
 
		oilCard = new ServiceCard("Engine Oil", "\uD83D\uDEE2");
		oilCard.markDoneButton.addActionListener(e -> {
			info.setLastMileForOil(info.getOdometer());
			refreshAll();
		});
		main.add(oilCard);
		main.add(Box.createVerticalStrut(14));
 
		rotationCard = new ServiceCard("Tyre Rotation", "\u2699");
		rotationCard.markDoneButton.addActionListener(e -> {
			info.setLastMileForRotation(info.getOdometer());
			refreshAll();
		});
		main.add(rotationCard);
		main.add(Box.createVerticalStrut(14));
 
		registrationCard = new ServiceCard("Registration Renewal", "\uD83D\uDCC4");
		registrationCard.markDoneButton.addActionListener(e -> {
			String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			info.setLastDateForRegistration(today);
			refreshAll();
		});
		main.add(registrationCard);
 
		main.add(Box.createVerticalGlue());
 
		JScrollPane scroll = new JScrollPane(main);
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.setBackground(BG);
		scroll.getViewport().setBackground(BG);
		return scroll;
	}
 
	private JComponent buildOdometerPanel() {
		JPanel card = cardPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
 
		JLabel label = new JLabel("Current Odometer");
		label.setFont(FONT_CARD_TITLE);
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
 
		odometerValueLabel = new JLabel("0 mi");
		odometerValueLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
		odometerValueLabel.setForeground(ACCENT);
		odometerValueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
 
		JPanel inputRow = new JPanel();
		inputRow.setOpaque(false);
		inputRow.setLayout(new BoxLayout(inputRow, BoxLayout.X_AXIS));
		inputRow.setAlignmentX(Component.LEFT_ALIGNMENT);
 
		odometerField = new JTextField();
		odometerField.setFont(FONT_BODY);
		odometerField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
		odometerField.setToolTipText("Enter new odometer reading");
 
		JButton updateButton = new JButton("Update");
		styleButton(updateButton, ACCENT);
		updateButton.addActionListener(e -> handleOdometerUpdate());
		odometerField.addActionListener(e -> handleOdometerUpdate());
 
		inputRow.add(odometerField);
		inputRow.add(Box.createHorizontalStrut(8));
		inputRow.add(updateButton);
 
		card.add(label);
		card.add(Box.createVerticalStrut(4));
		card.add(odometerValueLabel);
		card.add(Box.createVerticalStrut(10));
		card.add(inputRow);
		return card;
	}
 
	private void handleOdometerUpdate() {
		String text = odometerField.getText().trim();
		if (text.isEmpty()) {
			return;
		}
		try {
			int reading = Integer.parseInt(text);
			boolean ok = info.setOdometer(reading);
			if (!ok) {
				JOptionPane.showMessageDialog(this,
						"Odometer reading can't be lower than the last recorded oil change or tyre rotation mileage.",
						"Invalid reading", JOptionPane.WARNING_MESSAGE);
				return;
			}
			odometerField.setText("");
			refreshAll();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this,
					"Please enter a whole number for the odometer reading.",
					"Invalid input", JOptionPane.WARNING_MESSAGE);
		}
	}
 
	private void refreshAll() {
		odometerValueLabel.setText(info.getOdometer() + " mi");
 
		oilCard.setHistory(car.EngineOilHistory());
		oilCard.setStatus(car.EngineOilIsDue());
 
		rotationCard.setHistory(car.TyreRotationHistory());
		rotationCard.setStatus(car.TyreRotationIsDue());
 
		registrationCard.setHistory(car.RegistationRenewalHistory());
		registrationCard.setStatus(car.RegistationRenewalIsDue());
	}
 

 
	private JPanel cardPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(CARD_BG);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getMaximumSize().height));
		panel.setBorder(new CompoundBorder(
				new LineBorder(BORDER_COLOR, 1, true),
				new EmptyBorder(16, 18, 16, 18)));
		return panel;
	}
 
	private void styleButton(JButton button, Color bg) {
		button.setFont(FONT_STATUS);
		button.setForeground(Color.WHITE);
		button.setBackground(bg);
		button.setFocusPainted(false);
		button.setBorder(new EmptyBorder(8, 16, 8, 16));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setOpaque(true);
		button.setBorderPainted(false);
	}
 

	private class ServiceCard extends JPanel {
		private final JLabel statusLabel;
		private final JLabel historyLabel;
		private final JButton markDoneButton;
 
		ServiceCard(String title, String icon) {
			setBackground(CARD_BG);
			setAlignmentX(Component.LEFT_ALIGNMENT);
			setLayout(new BorderLayout(12, 0));
			setBorder(new CompoundBorder(
					new LineBorder(BORDER_COLOR, 1, true),
					new EmptyBorder(16, 18, 16, 18)));
 
			JLabel iconLabel = new JLabel(icon);
			iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 26));
			iconLabel.setVerticalAlignment(SwingConstants.TOP);
 
			JPanel textPanel = new JPanel();
			textPanel.setOpaque(false);
			textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
 
			JLabel titleLabel = new JLabel(title);
			titleLabel.setFont(FONT_CARD_TITLE);
			titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
 
			historyLabel = new JLabel(" ");
			historyLabel.setFont(FONT_BODY);
			historyLabel.setForeground(TEXT_MUTED);
			historyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
 
			statusLabel = new JLabel(" ");
			statusLabel.setFont(FONT_STATUS);
			statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
 
			textPanel.add(titleLabel);
			textPanel.add(Box.createVerticalStrut(4));
			textPanel.add(historyLabel);
			textPanel.add(Box.createVerticalStrut(4));
			textPanel.add(statusLabel);
 
			markDoneButton = new JButton("Mark as Done");
			styleButton(markDoneButton, OK_COLOR);
 
			JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
			rightPanel.setOpaque(false);
			rightPanel.add(markDoneButton);
 
			add(iconLabel, BorderLayout.WEST);
			add(textPanel, BorderLayout.CENTER);
			add(rightPanel, BorderLayout.EAST);
		}
 
		void setHistory(String text) {
			historyLabel.setText(text);
		}
 
		void setStatus(String text) {
			statusLabel.setText(text);
			boolean due = text.toLowerCase().contains("due") && !text.toLowerCase().contains("next");
			statusLabel.setForeground(due ? DUE_COLOR : OK_COLOR);
		}
	}
}