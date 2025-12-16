
package edu.ilstu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;



/**
 * Board game that allows players to roll a die and move the number of squares they get on the die
 */
public class BoardGame {
	Random random = new Random();
	int player1Position=0;
	int player2Position=0;
	JLabel player1Label, player2Label ;
	JFrame frame;
	JButton rollButton;
	JPanel boardPanel = new JPanel();
	ArrayList<JLabel> cells = new ArrayList<>();
	ArrayList<String> history = new ArrayList<>();
	int turnNumber = 0;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new BoardGame());
    }
	
	public BoardGame(){
		frame = new JFrame("Board Game");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        boardPanel.setLayout(new GridLayout(10, 10));
     
        for (int i = 0; i < 100; i++) {
            
            JLabel cellLabel = new JLabel("", SwingConstants.CENTER);
            cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cellLabel.setOpaque(true);
        

            cells.add(cellLabel);
            boardPanel.add(cellLabel);
        }
       updateBoard();
        
        frame.add(boardPanel, BorderLayout.CENTER);
        
        JPanel statusBar = new JPanel();
        player1Label = new JLabel("Player 1: Position " + player1Position);
        player2Label = new JLabel("Player 2: Position " + player2Position);
        statusBar.add(player1Label);
        statusBar.add(player2Label);
      
        JMenuBar menuBar = new JMenuBar();
        JButton reset = new JButton("Reset Game");
        
        reset.addActionListener(e -> {
        	
        	int choice = JOptionPane.showConfirmDialog(
        	        frame,                                      
        	        "Are you sure you want to start a new game?\nThis will clear your current progress.", 
        	        "Reset Game",                         
        	                         
        	        JOptionPane.WARNING_MESSAGE                 
        	    );
        	if (choice == JOptionPane.YES_OPTION) {
                resetGame();
            }
        });
        
        
        JButton help = new JButton("Help");
        JButton historyButton = new JButton("History");
        rollButton = new JButton("Roll Dice");
        
        frame.getRootPane().setDefaultButton(rollButton);
        
        rollButton.addActionListener(new RollDice());
    
        historyButton.addActionListener(e -> {
        	
        showHistory();
        });
      
        help.addActionListener(e -> {
        	showGameRules();
        });

        menuBar.add(reset);
        menuBar.add(help);
        menuBar.add(rollButton);
        menuBar.add(historyButton);
       
        frame.add(menuBar, BorderLayout.NORTH);
     
        frame.add(statusBar, BorderLayout.SOUTH);
        
        frame.setVisible(true);
        showGameRules();

	}
		 private void updateBoard() {
		        for (int i = 0; i < cells.size(); i++) {
		            JLabel cell = cells.get(i);

		            cell.setText(""+i);
		            cell.setBackground(Color.WHITE);
		            cell.setOpaque(true);

		           
		            if (i == player1Position) {
		                cell.setText(cell.getText() + " |P1");
		                cell.setBackground(Color.RED);
		            }
		            if (i == player2Position) {
		              
		                cell.setText(cell.getText() + " |P2");
		                cell.setBackground(Color.BLUE);
		            }
		            
		        }
		       	        
		        cells.get(0).setText("START");
		        cells.get(99).setText("END");
		        boardPanel.revalidate();
		        boardPanel.repaint();
		    }
		 private void resetGame() {
			    
			    history.clear(); 
			    
	            player1Position = 0;
	            player2Position = 0;
	         
	            player1Label.setText("Player 1: Position " + player1Position + ";");
	            player2Label.setText("Player 2: Position " + player2Position);
	          
	            updateBoard();
	            turnNumber = 0;
			    JOptionPane.showMessageDialog(frame, "Game reset! Ready for a new game.");
			}
		 
	private void showGameRules() {
        JOptionPane.showMessageDialog(
            frame,
            "Game Rules:\n" +
            "This is a 2-player game on a 10x10 board (100 squares).\n" +
            "Players start on square 0 and the goal is to get to 99.\n" +
            "Click the Roll Dice button or press Enter and you move forward 1 - 6 steps based on what you get on the Dice.\n" +
            "You cannot move past the final square.\n" +
            "If both players finish the game on the same turn, Whoever has the higher postion that is outside of the board wins.\n" +
            "First to reach the final square wins; if both reach on the same turn, it's a draw.\n" +
            "Click the Help button to show these rules again.\n" +
            "Click the History button to show game history\n"+
            "Click the Reset button to Restart the game\n"+
            "\n                                 Enjoy the game!                             ",
            "Game Rules",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
	  private void showHistory() {
		  
		  if (history.isEmpty()) {
		        JOptionPane.showMessageDialog(frame, "No moves have been made yet.");
		    } else {
		        StringBuilder sb = new StringBuilder();
		        for (String move : history) {
		            sb.append(move).append("\n");
		        }

		        JOptionPane.showMessageDialog(
		            frame,
		            sb.toString(),
		            "Game History", 
		            JOptionPane.INFORMATION_MESSAGE
		        );
		    }

	    }
	
	private class RollDice implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        
            int diceRoll1 = random.nextInt(6) + 1;
            int diceRoll2 = random.nextInt(6) + 1;

            
            player1Position = player1Position + diceRoll1;
            player2Position = player2Position + diceRoll2;
         
            player1Label.setText("Player 1: Position " + player1Position + ";");
            player2Label.setText("Player 2: Position " + player2Position);
            updateBoard();
            
            turnNumber++;
        	
        	String turn =
        			"Turn " + turnNumber + ": Player 1 rolled a "  + diceRoll1 + " and moved to square " + player1Position 
                    +"; Player 2 rolled a " + diceRoll2 + " and moved to square " + player2Position;
                history.add(turn);
            
            JOptionPane.showMessageDialog(
                    frame,
                    "Player 1 rolled a "  + diceRoll1 + " and is now on square " + player1Position 
                    +"\nPlayer 2 rolled a " + diceRoll2 + " and is now on square " + player2Position,
                    "Next Turn",
                    JOptionPane.INFORMATION_MESSAGE
                );
            
            if (player1Position >= 99 || player2Position >= 99) {
                String message;
                if (player1Position > player2Position) {
                    message = "Player 1 is the winner! \nClick OK to close the game.";
                } else if (player2Position > player1Position) {
                    message = "Player 2 is the winner! \nClick OK to close the game.";
                } else {
                    message = "It's a tie! \nClick OK to close the game.";
                }

                JOptionPane.showMessageDialog(
                    frame,
                    message,
                    "Game Over!",
                    JOptionPane.INFORMATION_MESSAGE
                );

                rollButton.setEnabled(false);
                frame.dispose();
            }
         
}
}}
