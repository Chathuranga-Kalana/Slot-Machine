package slotMachineGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimerTask;
import java.util.Date;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Slot {
	private static final String GAME_NAME = "Slot Machine";
	static {
		try {
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", GAME_NAME);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private JFrame mainFrame;
	private JLabel creditLabel;
	private JButton btnSpin;
	private JButton btnBetMax;
	private JButton btnBetOne;
	private JButton btnReset;
	private JButton btnAddCredits;
	private JLabel betAmountLabel;
	private int bet = 0;
	private int credit = 10;
	public JLabel lblReel;
	public JLabel lblReel2;
	public JLabel lblReel3;
	private boolean mouseclick1 = false;
	private boolean reelSpinning = false;
	private int labelValue1;
	private int labelValue2;
	private int labelValue3;
	private int amountWon = 0;
	private int wins = 0;
	private int loses = 0;
	private int totalBets = 0;
	private float averageCredits = 0;
	private JLabel winner;
	private Reel newReel;
	private Timer time;
	private TimerTask timeTask;

	public Slot() throws InterruptedException {

		// create the mainFrame
		createMainFrame();
		createReelPanel();
		createSidebarPanel();

		// create and show the gui
		mainFrame.setMinimumSize(new Dimension(500, 350));
		mainFrame.pack();
		mainFrame.setVisible(true);

	}

	private static final Border UNSELECTED_BORDER = new EmptyBorder(5, 5, 5, 5);
	private static final Color LABEL_COLOR = new Color(33, 39, 33);
	private static final Font MED_LABEL_FONT = new Font("Calibri", Font.BOLD, 20);
	private static final Font LG_LABEL_FONT = new Font("Calibri", Font.BOLD, 32);
	private static final Border SELECTED_BORDER = new CompoundBorder(new LineBorder(new Color(255, 255, 0, 191)),
			new EmptyBorder(2, 2, 2, 2));

	private void createSidebarPanel() {
		// wrap every thing to south side
		JPanel sidePanel = new JPanel(new BorderLayout());
		sidePanel.setBorder(new EmptyBorder(10, 10, 10, 64));
		sidePanel.setOpaque(false);
		mainFrame.add(sidePanel, BorderLayout.SOUTH);

		// the bet one button
		btnBetOne = new JButton("Bet One");
		btnBetOne.setSize(50, 50);
		btnBetOne.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				if(reelSpinning)
					JOptionPane.showMessageDialog(null, "Cant bet when the reel is spinning!");
				else
					addBet(1);
			}

			public void mouseExited(MouseEvent e) {
				btnBetOne.setBorder(UNSELECTED_BORDER);
			}

			public void mouseEntered(MouseEvent e) {
				btnBetOne.setBorder(SELECTED_BORDER);
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});

		btnBetOne.setOpaque(true);
		btnBetOne.setBorder(UNSELECTED_BORDER);

		JPanel betLabelPanel = new JPanel();
		betLabelPanel.setOpaque(false);
		betLabelPanel.setBorder(new EmptyBorder(0, 12, 0, 12));
		betLabelPanel.add(this.btnBetOne, BorderLayout.CENTER);
		sidePanel.add(betLabelPanel, BorderLayout.NORTH);

		// the bet max
		btnBetMax = new JButton("Bet Max");
		btnBetMax.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				if(reelSpinning)
					JOptionPane.showMessageDialog(null, "Cant bet when the reel is spinning!");
				else
					addBet(3);
			}

			public void mouseExited(MouseEvent e) {
				btnBetMax.setBorder(UNSELECTED_BORDER);
			}

			public void mouseEntered(MouseEvent e) {
				btnBetMax.setBorder(SELECTED_BORDER);
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});

		btnBetMax.setOpaque(true);
		btnBetMax.setBorder(UNSELECTED_BORDER);
		JPanel betMaxLabelPanel = new JPanel();
		betMaxLabelPanel.setOpaque(false);
		betMaxLabelPanel.setBorder(new EmptyBorder(0, 12, 0, 12));
		betLabelPanel.add(this.btnBetMax, BorderLayout.CENTER);

		// reset the bet amount
		btnReset = new JButton("Reset");
		btnReset.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				if(reelSpinning)
					JOptionPane.showMessageDialog(null, "Cant reset when the reel is spinning!");
				else 
					betReset();
			}

			public void mouseExited(MouseEvent e) {
				btnReset.setBorder(UNSELECTED_BORDER);
			}

			public void mouseEntered(MouseEvent e) {
				btnReset.setBorder(SELECTED_BORDER);
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});

		btnReset.setOpaque(true);
		btnReset.setBorder(UNSELECTED_BORDER);
		betLabelPanel.add(this.btnReset, BorderLayout.CENTER);

		// the play
		btnSpin = new JButton("Spin");
		btnSpin.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				try {
					if(bet<=0){
						JOptionPane.showMessageDialog(null, "Please bet an amount first!");
					} else {
					setReel();
					totalBets = totalBets + bet;
					mouseclick1 = false;
					reelSpinning = true;
					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			}

			public void mouseExited(MouseEvent e) {
				btnSpin.setBorder(UNSELECTED_BORDER);
			}

			public void mouseEntered(MouseEvent e) {
				btnSpin.setBorder(SELECTED_BORDER);
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});
		btnSpin.setOpaque(true);
		btnSpin.setBorder(UNSELECTED_BORDER);
		betLabelPanel.add(this.btnSpin, BorderLayout.CENTER);

		JButton btnStatistic = new JButton();
		btnStatistic.setText("Statistic");
		btnStatistic.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				statsButtonClicked();
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		betLabelPanel.add(btnStatistic, BorderLayout.CENTER);

		btnAddCredits = new JButton("Add Credits");
		btnAddCredits.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				addCredit(1);

			}

			public void mouseExited(MouseEvent e) {
				btnAddCredits.setBorder(UNSELECTED_BORDER);
			}

			public void mouseEntered(MouseEvent e) {
				btnAddCredits.setBorder(SELECTED_BORDER);
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e)

			{
			}
		});
		betLabelPanel.add(this.btnAddCredits, BorderLayout.CENTER);

		// title wrapped panel for the number of credits remaining
		TitledBorder cardsLeftTitle = new TitledBorder(new LineBorder(LABEL_COLOR, 2), "Credits");
		cardsLeftTitle.setTitleFont(MED_LABEL_FONT);
		cardsLeftTitle.setTitleColor(LABEL_COLOR);
		cardsLeftTitle.setTitleJustification(TitledBorder.CENTER);
		this.creditLabel = new JLabel();
		this.creditLabel.setText("10");
		this.creditLabel.setFont(LG_LABEL_FONT);
		this.creditLabel.setForeground(LABEL_COLOR);
		JPanel creditPanel = new JPanel();
		creditPanel.setBorder(cardsLeftTitle);
		creditPanel.setOpaque(false);
		creditPanel.add(this.creditLabel);

		// title wrapped panel for the bet
		TitledBorder scoreTitle = new TitledBorder(new LineBorder(LABEL_COLOR, 2), "Bet");
		scoreTitle.setTitleFont(MED_LABEL_FONT);
		scoreTitle.setTitleColor(LABEL_COLOR);
		scoreTitle.setTitleJustification(TitledBorder.CENTER);
		this.betAmountLabel = new JLabel();
		this.betAmountLabel.setText("0");
		this.betAmountLabel.setFont(LG_LABEL_FONT);
		this.betAmountLabel.setForeground(LABEL_COLOR);
		JPanel betAmountPanel = new JPanel();
		betAmountPanel.setBorder(scoreTitle);
		betAmountPanel.setOpaque(false);
		betAmountPanel.add(this.betAmountLabel);

		// wrap cards left & score panels and add to side pane
		JPanel bottomWrapperPanel = new JPanel(new BorderLayout());
		bottomWrapperPanel.setOpaque(false);
		bottomWrapperPanel.add(creditPanel, BorderLayout.NORTH);
		bottomWrapperPanel.add(betAmountPanel, BorderLayout.SOUTH);
		sidePanel.add(bottomWrapperPanel, BorderLayout.SOUTH);

		// winner bar
		winner = new JLabel("Please Bet an amount and press spin", SwingConstants.CENTER);
		winner.setForeground(Color.YELLOW);
		winner.setOpaque(false);
		sidePanel.add(winner, BorderLayout.CENTER);
	}

	// reels
	public void createReelPanel() {

		JPanel panel = new JPanel();

		lblReel = new JLabel();
		lblReel.setIcon(new ImageIcon(getClass().getResource("Images/redseven.png")));
		lblReel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				stop();
				iswinner(labelValue1, labelValue2, labelValue3);
				reelSpinning = false;
				System.out.println(labelValue1);
				System.out.println(labelValue2);
				System.out.println(labelValue3);

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		lblReel2 = new JLabel();
		lblReel2.setIcon(new ImageIcon(getClass().getResource("Images/redseven.png")));
		lblReel3 = new JLabel();
		lblReel3.setIcon(new ImageIcon(getClass().getResource("Images/redseven.png")));
		panel.setOpaque(false);
		panel.setLayout(new GridLayout(1, 3));
		panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel.add(lblReel);
		panel.add(lblReel2);
		panel.add(lblReel3);

		mainFrame.add(panel, BorderLayout.CENTER);

	}

	// creating main method
	private void createMainFrame() {
		mainFrame = new JFrame(GAME_NAME);
		mainFrame.getContentPane().setBackground(new Color(0, 154, 206));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());
	}

	// main method
	public static void main(String[] args) throws InterruptedException {
		new Slot();

	}

	// adding bets
	public void addBet(int bet) {
		if (credit >= bet) {
			this.bet += bet;
			credit -= bet;
			betAmountLabel.setText("" + this.bet);
			creditLabel.setText("" + credit);
		} else {
			JOptionPane.showMessageDialog(null, "Not Enough Credit");

		}
	}

	// adding credits
	public void addCredit(int credit) {
		this.credit += credit;
		creditLabel.setText("" + this.credit);
	}

	// resting the bet
	public void betReset() {
		credit += bet;
		bet = 0;
		betAmountLabel.setText("" + bet);
		creditLabel.setText(" " + credit);

	}

	// set winner label
	public void iswinner(int labelValue1, int labelValue2, int labelValue3) {
		if (labelValue1 == labelValue2 || labelValue1 == labelValue3 || labelValue2 == labelValue3) {
			winner.setText("You have won "+winnerCredit(labelValue1, labelValue2, labelValue3)+" credits");
			wins++;
			bet = 0;
			betAmountLabel.setText("" + bet);
			

		} else {
			winner.setText("You lose!");
			bet = 0;
			loses++;
			betAmountLabel.setText("" + bet);
		}

	}

	// animation of the reel
	public void setReel() throws InterruptedException {

		newReel = new Reel();

		timeTask = new TimerTask() {

			@Override
			public void run() {

				for (int i = 0; i < 50; i++) {

					Symbol[] lblArray1 = newReel.spin();
					Symbol[] lblArray2 = newReel.spin();
					Symbol[] lblArray3 = newReel.spin();

					for (int x = 0; x < 6; x++) {
						if (mouseclick1)
							break;

						lblReel.setIcon(new ImageIcon(getClass().getResource(lblArray1[x].getImage())));
						lblReel2.setIcon(new ImageIcon(getClass().getResource(lblArray2[x].getImage())));
						lblReel3.setIcon(new ImageIcon(getClass().getResource(lblArray3[x].getImage())));

						labelValue1 = lblArray1[x].getValue();
						labelValue2 = lblArray2[x].getValue();
						labelValue3 = lblArray3[x].getValue();

					}

				}

			}

		};

		time = new Timer();
		time.schedule(timeTask, 1, 1000);

	}

	public void stop() {
		if (timeTask != null)
			timeTask.cancel();
		mouseclick1 = true;
	}
// calculate winning credit
	public int winnerCredit(int lbl1, int lbl2, int lbl3) {
		//System.out.println(amountWon+" "+lbl1+" "+lbl2+" "+lbl3);
		if (lbl1 == lbl2) {
			amountWon = bet * lbl1;	

		} else if (lbl1 == lbl3) {
			amountWon = bet * lbl1;
			
		} else if (lbl2 == lbl3) {
			amountWon = bet * lbl2;
			
		}
		
		//System.out.println("You have won " + amountWon);
		credit = credit + amountWon;
		creditLabel.setText(Integer.toString(credit));
		
		return amountWon;
	}
	// finding static of the game write it to txt file
	public void statsButtonClicked(){
		averageCredits = (float)totalBets/(wins+loses);
		int value = JOptionPane.showOptionDialog(null, "Winnings : "+wins+"\nLoses : "+loses+"\nAverage credits spent : "+averageCredits, 
		        "Statistics", 
		        JOptionPane.OK_OPTION, 
		        JOptionPane.PLAIN_MESSAGE, 
		        null, 
		        new String[]{"Write to file"}, 
		        "default");
		if (value == JOptionPane.OK_OPTION){
			try {
			long millis = System.currentTimeMillis() % 1000;
			String filePath = "src/slotMachineGUI/Statistics/"+millis+".txt";
			//System.out.println(filePath);
		    PrintWriter writer;
		    writer = new PrintWriter(filePath);
		    writer.println("Wins : "+wins);
		    writer.println("Loses : "+loses);
		    writer.println("Average credits netted : "+averageCredits);
		    writer.close();
			
			} catch (FileNotFoundException e) {
				System.out.println("File not found!");
			}
		    
		}
	}

}
