package com.bilaldjago.homekode.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 2123644716438583867L;

	private JPanel mainPanel = new JPanel(new GridLayout(3, 3, 10, 10));
	private JPanel infoPanel = new JPanel(new GridLayout(2, 3, 10, 2));

	private JTextField playerOneTF = new JTextField("Player 1");
	private JTextField playerTwoTF = new JTextField("Player 2");

	private JLabel drawLabel = new JLabel("Draw Count", JLabel.CENTER);
	private JLabel drawCountLabel = new JLabel("0", JLabel.CENTER);
	private JLabel playerOneWinCount = new JLabel("0", JLabel.CENTER);
	private JLabel playerTwoWinCount = new JLabel("0", JLabel.CENTER);

	private JButton[] buttons = new JButton[9];

	private boolean turn = true;
	private int turnCount = 0;
	private boolean againstComputer = false;

	public MainGUI() {
		super("Tic Tac Toe");
		initComponents();
	}

	private void initComponents() {
		preparMainPanel();
		preparInfoPanel();

		this.add("Center", mainPanel);
		this.add("South", infoPanel);

		setResizable(false);
		setSize(300, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		initMenu();
	}

	private void initMenu() {

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");

		JMenuItem newGameItem = new JMenuItem("New Game");
		JMenuItem resetItem = new JMenuItem("Reset");
		JCheckBoxMenuItem againstComputerItem = new JCheckBoxMenuItem(
				"Play against computer");
		JMenuItem exitItem = new JMenuItem("Exit");

		JMenuItem aboutItem = new JMenuItem("About");

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		fileMenu.add(newGameItem);
		fileMenu.add(resetItem);
		fileMenu.add(againstComputerItem);
		fileMenu.add(exitItem);

		helpMenu.add(aboutItem);

		// set actionlisteners
		newGameItem.addActionListener(e -> newGame());
		againstComputerItem.addActionListener(e -> {
			if (againstComputerItem.isSelected()) {
				againstComputer = true;
				playerTwoTF.setText("Computer");
				playerTwoTF.setEditable(false);
				if(!turn)
					makeComputerMove();
			} else {
				againstComputer = false;
				playerTwoTF.setText("Player 2");
				playerTwoTF.setEditable(true);
			}
		});
		resetItem.addActionListener(e -> resetGame());
		exitItem.addActionListener(e -> System.exit(0));
		aboutItem.addActionListener(e -> about());

		this.setJMenuBar(menuBar);

	}

	private void preparMainPanel() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton();
		}

		for (final JButton b : buttons) {
			b.setFont(new Font("Microsoft sans serif", Font.BOLD, 28));

			// The action when Click one of the buttons
			b.addActionListener((e) -> {
				checkTurn(b);
				b.setEnabled(false);
				turn = !turn;
				turnCount++;
				checkWinner();

				if (!turn && againstComputer) {
					makeComputerMove();
				}

			});

			// The action when the mouse hover one of the buttons
			b.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseEntered(MouseEvent e) {
					super.mouseEntered(e);
					checkTurn(b);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					super.mouseExited(e);
					if (b.isEnabled())
						b.setText("");
				}

			});

			mainPanel.add(b);

		}

		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	private void preparInfoPanel() {

		drawLabel.setFont(new Font("", Font.BOLD, 12));

		infoPanel.add(playerOneTF);
		infoPanel.add(drawLabel);
		infoPanel.add(playerTwoTF);
		infoPanel.add(playerOneWinCount);
		infoPanel.add(drawCountLabel);
		infoPanel.add(playerTwoWinCount);

		infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	}

	private void checkWinner() {
		boolean winner = false;

		// Horizontal check
		if (checkWinner(0, 1, 2))
			winner = true;
		if (checkWinner(3, 4, 5))
			winner = true;
		if (checkWinner(6, 7, 8))
			winner = true;

		// Vertical check
		if (checkWinner(0, 3, 6))
			winner = true;
		if (checkWinner(1, 4, 7))
			winner = true;
		if (checkWinner(2, 5, 8))
			winner = true;

		// Diagonal check
		if (checkWinner(0, 4, 8))
			winner = true;
		if (checkWinner(2, 4, 6))
			winner = true;

		if (winner) {
			if (turn) {
				JOptionPane.showMessageDialog(this, playerTwoTF.getText()
						+ " wins!", "There is a winner",
						JOptionPane.INFORMATION_MESSAGE);
				addNewWin("O");
			} else {
				JOptionPane.showMessageDialog(this, playerOneTF.getText()
						+ " wins!", "There is a winner",
						JOptionPane.INFORMATION_MESSAGE);
				addNewWin("X");
			}

			disableButtons();

		} else {
			if (turnCount == 9) {
				JOptionPane.showMessageDialog(this, "Draw!", "Result",
						JOptionPane.INFORMATION_MESSAGE);
				addNewWin("Draw");
				disableButtons();
			}
		}

	}

	private boolean checkWinner(int c1, int c2, int c3) {
		if (buttons[c1].getText().equals(buttons[c2].getText())
				&& buttons[c1].getText().equals(buttons[c3].getText())
				&& !buttons[c1].isEnabled())
			return true;
		return false;
	}

	private void addNewWin(String mark) {
		if (mark.equals("X")) {
			playerOneWinCount.setText(String.valueOf(Integer
					.parseInt(playerOneWinCount.getText()) + 1));
		} else if (mark.equals("O")) {
			playerTwoWinCount.setText(String.valueOf(Integer
					.parseInt(playerTwoWinCount.getText()) + 1));
		} else {
			drawCountLabel.setText(String.valueOf(Integer
					.parseInt(drawCountLabel.getText()) + 1));
		}
	}

	private void disableButtons() {

		for (JButton b : buttons) {
			b.setEnabled(false);
		}

	}

	private void newGame() {
		turn = true;
		turnCount = 0;
		for (JButton b : buttons) {
			b.setText("");
			b.setEnabled(true);
		}
	}

	private void about() {
		JOptionPane.showMessageDialog(this, "Created by Bilal Djago");
	}

	private void resetGame() {
		newGame();
		playerOneWinCount.setText("0");
		playerTwoWinCount.setText("0");
		drawCountLabel.setText("0");
	}

	private void checkTurn(final JButton b) {
		if (b.isEnabled()) {
			if (turn) {
				b.setText("X");
				b.setForeground(Color.red);
			} else {
				b.setText("O");
				b.setForeground(Color.green);
			}
		}
	}

	private void makeComputerMove() {

		int move = -1;

		move = lookForWinOrBlock("O");
		if (move == -1) {
			move = lookForWinOrBlock("X");
			if (move == -1) {
				move = lookForCorners();
				if (move == -1) {
					move = lookForAvailableSpace();
				}
			}
		}

		if (move != -1) {
			buttons[move].doClick();
		}

	}

	private int lookForWinOrBlock(String mark) {

		// Horizontal Check
		if (checkCell(0, 1, 2, mark) != -1)
			return checkCell(0, 1, 2, mark);
		if (checkCell(1, 2, 0, mark) != -1)
			return checkCell(1, 2, 0, mark);
		if (checkCell(2, 0, 1, mark) != -1)
			return checkCell(2, 0, 1, mark);

		if (checkCell(3, 4, 5, mark) != -1)
			return checkCell(3, 4, 5, mark);
		if (checkCell(4, 5, 3, mark) != -1)
			return checkCell(4, 5, 3, mark);
		if (checkCell(5, 3, 4, mark) != -1)
			return checkCell(5, 3, 4, mark);

		if (checkCell(6, 7, 8, mark) != -1)
			return checkCell(6, 7, 8, mark);
		if (checkCell(7, 8, 6, mark) != -1)
			return checkCell(7, 8, 6, mark);
		if (checkCell(8, 6, 7, mark) != -1)
			return checkCell(8, 6, 7, mark);

		// Vertical check
		if (checkCell(0, 3, 6, mark) != -1)
			return checkCell(0, 3, 6, mark);
		if (checkCell(3, 6, 0, mark) != -1)
			return checkCell(3, 6, 0, mark);
		if (checkCell(6, 0, 3, mark) != -1)
			return checkCell(6, 0, 3, mark);
		
		if (checkCell(1, 4, 7, mark) != -1)
			return checkCell(1, 4, 7, mark);
		if (checkCell(4, 7, 1, mark) != -1)
			return checkCell(4, 7, 1, mark);
		if (checkCell(7, 1, 4, mark) != -1)
			return checkCell(7, 1, 4, mark);
		
		if (checkCell(2, 5, 8, mark) != -1)
			return checkCell(2, 5, 8, mark);
		if (checkCell(5, 8, 2, mark) != -1)
			return checkCell(5, 8, 2, mark);
		if (checkCell(8, 2, 5, mark) != -1)
			return checkCell(8, 2, 5, mark);
		
		// Diagonal check
		if (checkCell(0, 4, 8, mark) != -1)
			return checkCell(0, 4, 8, mark);
		if (checkCell(4, 8, 0, mark) != -1)
			return checkCell(4, 8, 0, mark);
		if (checkCell(8, 0, 4, mark) != -1)
			return checkCell(8, 0, 4, mark);
		
		if (checkCell(2, 4, 6, mark) != -1)
			return checkCell(2, 4, 6, mark);
		if (checkCell(4, 6, 2, mark) != -1)
			return checkCell(4, 6, 2, mark);
		if (checkCell(6, 2, 4, mark) != -1)
			return checkCell(6, 2, 4, mark);
		
		return -1;
	}

	private int lookForCorners() {
		if(buttons[0].getText().equals("O")) {
			if(buttons[2].getText().equals(""))
				return 2;
			if(buttons[6].getText().equals(""))
				return 6;
			if(buttons[8].getText().equals(""))
				return 8;
		}
		
		if(buttons[2].getText().equals("O")) {
			if(buttons[0].getText().equals(""))
				return 0;
			if(buttons[6].getText().equals(""))
				return 6;
			if(buttons[8].getText().equals(""))
				return 8;
		}
		
		if(buttons[6].getText().equals("O")) {
			if(buttons[0].getText().equals(""))
				return 0;
			if(buttons[2].getText().equals(""))
				return 2;
			if(buttons[8].getText().equals(""))
				return 8;
		}
		
		if(buttons[8].getText().equals("O")) {
			if(buttons[0].getText().equals(""))
				return 0;
			if(buttons[2].getText().equals(""))
				return 2;
			if(buttons[6].getText().equals(""))
				return 6;
		}
		
		if (buttons[0].getText().equals(""))
            return 0;
        if (buttons[2].getText().equals(""))
            return 2;
        if (buttons[6].getText().equals(""))
            return 6;
        if (buttons[8].getText().equals(""))
            return 8;
        
		return -1;
	}
	
	private int lookForAvailableSpace() {
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].getText().equals("")) {
				return i;
			}
		}
		return -1;
	}
	
	private int checkCell(int c1, int c2, int c3, String mark) {
		if (buttons[c1].getText().equals(mark)
				&& buttons[c2].getText().equals(mark)
				&& buttons[c3].getText().equals("")) {
			return c3;
		}
		return -1;
	}

}
