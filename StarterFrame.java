import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class StarterFrame extends JFrame implements ActionListener{
	private JLabel variables = new JLabel("Enter the number of variables");
	private JLabel constraints = new JLabel("Enter the number of constraints");
	private JTextField numOfVar = new JTextField(2);
	private JTextField numOfCon = new JTextField(2);
	private JPanel center = new JPanel();
	private JPanel bottom = new JPanel();
	private JButton start = new JButton("Start");

	public StarterFrame(){
		this.setTitle("Initialize LP");
		this.setLayout(new BorderLayout(10,10));
		center.setLayout(new GridLayout(2,2));
		center.add(variables);
		center.add(numOfVar);
		center.add(constraints);
		center.add(numOfCon);
		bottom.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		bottom.add(start);
		this.add(center, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
		start.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(numOfCon.getText().equals("") && numOfVar.getText().equals("")){
			JOptionPane.showMessageDialog(this, "The number of variables and constraints are missing");
			return;
		}
		if(numOfVar.getText().equals("")){
			JOptionPane.showMessageDialog(this, "The number of variables is missing");
			return;
		}
		if(numOfCon.getText().equals("")){
			JOptionPane.showMessageDialog(this, "The number of constraints is missing");
			return;
		}
		try{
	        Integer.parseInt(numOfVar.getText());
	    }catch(NumberFormatException e){
	        JOptionPane.showMessageDialog(this, numOfVar.getText() + " isnt a valid integer for the number of variables");
	        return;
	    }
		try{
	        Integer.parseInt(numOfCon.getText());
	    }catch(NumberFormatException e){
	        JOptionPane.showMessageDialog(this, numOfCon.getText() + " isnt a valid integer for the number of constraints");
	        return;
	    }
		int var = Integer.parseInt(numOfVar.getText());
		int con = Integer.parseInt(numOfCon.getText());
		MyFrame frame = new MyFrame(var, con);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
	}
}
