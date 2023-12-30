import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TicTacToeGameBoard implements ActionListener {

	Random random = new Random();
	JFrame tictacFrame = new JFrame();
	JPanel title_panel = new JPanel();
	JPanel button_panel = new JPanel();
	JPanel bottom_panel = new JPanel();
	JPanel reset_Panel = new JPanel();
	JLabel textField = new JLabel();
	JButton[] buttons = new JButton[9];
	JPanel resetPanel = new JPanel(new FlowLayout());

	Image icon = new ImageIcon(this.getClass().getResource("/icon.png")).getImage();
	boolean player1_turn;

	TicTacToeGameBoard() {
		tictacFrame.setTitle("TicTacToe GUI: Two player game");
		tictacFrame.setIconImage(icon);
		tictacFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tictacFrame.setSize(600, 600);
		tictacFrame.setResizable(false);
		tictacFrame.getContentPane().setBackground(new Color(50, 50, 50));
		tictacFrame.setLayout(new BorderLayout());
		tictacFrame.setVisible(true);

		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBackground(new Color(25, 25, 25));
		textField.setForeground(Color.WHITE);
		textField.setFont(new Font("Roboto", Font.BOLD, 30));
		textField.setText("TicTacToe");
		textField.setOpaque(true);

		title_panel.setLayout(new BorderLayout());
		title_panel.setBounds(0, 0, 600, 600);

		title_panel.add(resetPanel, BorderLayout.EAST);

		button_panel.setLayout(new GridLayout(3, 3));
		button_panel.setBackground(Color.white);

		JButton resetButton = new JButton("Reset");
		resetButton.setBackground(Color.white);
		resetButton.setFont(new Font("Roboto", Font.BOLD, 15));

		resetButton.addActionListener(e -> {
			for (int i = 0; i < 9; i++) {
				buttons[i].setText("");
				buttons[i].setEnabled(true);
				button_panel.setBackground(new Color(150, 150, 150));
				buttons[i].setBackground(Color.white);
			}
			firstTurn();
		});

		resetPanel.setBackground(Color.black);
		resetPanel.add(resetButton);

		for (int i = 0; i < 9; i++) {
			buttons[i] = new JButton();
			button_panel.add(buttons[i]);
			buttons[i].setFont(new Font("Roboto", Font.BOLD, 90));
			buttons[i].setBackground(Color.white);
			buttons[i].setFocusable(false);
			buttons[i].addActionListener(this);
		}

		title_panel.add(textField);

		tictacFrame.add(title_panel, BorderLayout.NORTH);
		tictacFrame.add(button_panel);

		firstTurn();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (int i = 0; i < 9; i++) {
			if (e.getSource() == buttons[i]) {
				if (player1_turn) {
					if (buttons[i].getText() == "") {
						buttons[i].setForeground(Color.red);
						buttons[i].setText("O");
						player1_turn = false;
						textField.setText("Player 1 turn");
						checkWinner();
					}
				} else {
					if (buttons[i].getText() == "") {
						buttons[i].setForeground(Color.blue);
						buttons[i].setText("X");
						player1_turn = true;
						textField.setText("Player 2 turn");
						checkWinner();
					}
				}
			}
		}
	}

	public void firstTurn() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (random.nextInt(2) == 0) {
			player1_turn = true;
			textField.setText("Player 2 turn");
		} else {
			player1_turn = false;
			textField.setText("Player 1 turn");
		}
	}

	public void checkWinner() {

		int[][] lines = {
				{ 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // horizontal
				{ 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // vertical
				{ 0, 4, 8 }, { 6, 4, 2 } // diagonal
		};

		int numMoves = 0;

		for (int i = 0; i < 9; i++) {
			if (!buttons[i].getText().isEmpty()) {
				numMoves++;
			}
		}

		for (int[] line : lines) {
			String text = buttons[line[0]].getText();
			if (!text.isEmpty() && text.equals(buttons[line[1]].getText()) && text.equals(buttons[line[2]].getText())) {
				if (text.equals("X")) {
					xWins(line[0], line[1], line[2]);
				} else {
					oWins(line[0], line[1], line[2]);
				}
				return;
			}
		}

		if (numMoves == 9) {
			textField.setText("Draw");
		}
	}

	public void xWins(int a, int b, int c) {
		buttons[a].setBackground(new Color(0, 255, 0));
		buttons[b].setBackground(new Color(0, 255, 0));
		buttons[c].setBackground(new Color(0, 255, 0));

		for (int i = 0; i < 9; i++) {
			buttons[i].setEnabled(false);
		}
		textField.setText("Player 1 Wins!");
	}

	public void oWins(int a, int b, int c) {
		buttons[a].setBackground(new Color(255, 0, 0));
		buttons[b].setBackground(new Color(255, 0, 0));
		buttons[c].setBackground(new Color(255, 0, 0));

		for (int i = 0; i < 9; i++) {
			buttons[i].setEnabled(false);
		}
		textField.setText("Player 2 Wins!");
	}

	public void resetButton() {

	}
}
