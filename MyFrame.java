import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class MyFrame extends JDialog implements ActionListener{
	boolean isValid;
	private String[] objboxoptions = {"Minimize", "Maximize"};
	private String[] inequalities = {"=","<=",">="};
	private JLabel subjlabel = new JLabel("Subject to:");
	private JLabel objlabel = new JLabel("Objective Function");
	
	private JTextField[] objconstraints;
	private JTextField[][] leftconstraints;
	private JTextField[] rightconstraints;
	private JLabel[] variables;
	private JComboBox<String>[] constraintbox;
	private JComboBox<String> objbox = new JComboBox<String>(objboxoptions);
	
	private JButton execute = new JButton("Execute Algorithm");
	private JPanel objpanel = new JPanel();
	private JPanel[] constraintpanel;
	
	
	
	private JPanel centerpanel = new JPanel();
	private JPanel bottompanel = new JPanel();
	private JPanel northpanel = new JPanel();
	private JPanel fillerpanel = new JPanel();

	@SuppressWarnings("unchecked")
	public MyFrame(int var, int con){
		super();
		isValid = true;
		//this.setModal(true);
		System.out.println("var: " + var);
		System.out.println("con: " + con);
		this.setTitle("Simplex Method Algorithm Sampler");
		this.setLayout(new BorderLayout(10,10));
		variables = new JLabel[var];
		constraintbox = new JComboBox[con];
		rightconstraints = new JTextField[con];
		objconstraints = new JTextField[var];
		leftconstraints = new JTextField[con][var];
		//initialize obj function here
		for(int i = 0; i < var; i++){
			variables[i] = new JLabel("x" + i);
			objconstraints[i] = new JTextField(2);
		}
		objpanel = new JPanel();
		objpanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		objpanel.add(objlabel);
		objpanel.add(objbox);
		for(int i = 0; i < var; i++){
			objpanel.add(objconstraints[i]);
			objpanel.add(variables[i]);
			if(i == (var-1)){
				continue;
			}else{
				objpanel.add(new JLabel("+"));
			}
		}
		for(int i = 0; i < con ; i++){
			for(int j = 0; j <var; j++){
				leftconstraints[i][j] = new JTextField(2);
			}
			constraintbox[i] = new JComboBox<String>(inequalities);
			rightconstraints[i] = new JTextField(2);
		}
		constraintpanel = new JPanel[con];
		for(int i = 0; i < constraintpanel.length; i++){
			constraintpanel[i] = new JPanel();
			constraintpanel[i].setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
			for(int j = 0; j < var; j++){
				constraintpanel[i].add(leftconstraints[i][j]);
				constraintpanel[i].add(new JLabel("x" + j));
				if(j == (var-1)){
					constraintpanel[i].add(constraintbox[i]);
					constraintpanel[i].add(rightconstraints[i]);
				}else{
					constraintpanel[i].add(new JLabel("+"));
					constraintpanel[i].add(constraintbox[i]);
					constraintpanel[i].add(rightconstraints[i]);
				}
			}
		}
		centerpanel.setLayout(new GridLayout(constraintpanel.length,1));
		for(int i = 0; i < constraintpanel.length; i++){
			centerpanel.add(constraintpanel[i]);
		}
		bottompanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottompanel.add(execute);
		execute.addActionListener(this);
		
		
		
		northpanel.setLayout(new GridLayout(2,1));
		northpanel.add(objpanel);
		fillerpanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		fillerpanel.add(subjlabel);
		northpanel.add(fillerpanel);
		this.add(northpanel, BorderLayout.NORTH);
		this.add(centerpanel, BorderLayout.CENTER);
		this.add(bottompanel, BorderLayout.SOUTH);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		double[][] tableau = convertToMatrix();
		if(!isValid){
			return;
		}
		DisplayFrame frame = new DisplayFrame(tableau);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
	}
	private double[][] convertToMatrix(){
		int[] slackIndices = new int[rightconstraints.length];
		int numOfSlack = 0;
		for( int i = 0; i < rightconstraints.length;i++){
			if(constraintbox[i].getSelectedItem().toString().equals("<=") || constraintbox[i].getSelectedItem().toString().equals(">=")){
				slackIndices[i] = 1;
				numOfSlack++;
			}
		}
		double[][] tableau = new double[rightconstraints.length+1][variables.length+numOfSlack+1];
		for(int i = 0; i < tableau.length-1;i++){
			int multByNeg = 1;
			if(constraintbox[i].getSelectedItem().toString().equals(">=")){
				multByNeg = -1;
			}
			for(int j = 0; j < variables.length;j++){
				if(leftconstraints[i][j].getText().equals("")){
					tableau[i][j] = 0;
					continue;
				}
				try{
				tableau[i][j] = Double.parseDouble(leftconstraints[i][j].getText())*multByNeg;
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(this, leftconstraints[i][j].getText() + " isnt a valid double for the " + i + " " + j + " index");
			        isValid = false;
			        return tableau;
				}
			}
			if(slackIndices[i] == 1){
				//find slack position
				int slackposition = 0;
				for(int k= 0; k < i; k++){
					if(slackIndices[k] == 1){
						slackposition++;
					}
				}
				tableau[i][variables.length+slackposition] = 1;
			}
			if(rightconstraints[i].getText().equals("")){
				tableau[i][tableau[0].length-1] = 0;
				continue;
			}
			try{
			tableau[i][tableau[0].length-1] = Double.parseDouble(rightconstraints[i].getText())*multByNeg;
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(this, rightconstraints[i].getText() + " isnt a valid double for the " + i + "th constraint ");
				isValid = false;
				return tableau;
			}
			
		}
		int multByNeg = 1;
		if(objbox.getSelectedItem().toString().equals(("Maximize"))){
			multByNeg = -1;
		}
		for(int i = 0; i < variables.length;i++){
			if(objconstraints[i].getText().trim().equals("")){
				tableau[tableau.length-1][i] = 0;
				continue;
			}
			try{
			tableau[tableau.length-1][i] = Double.parseDouble(objconstraints[i].getText()) * multByNeg;
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(this, objconstraints[i].getText() + " isnt a valid double for the " + i + "th  objective constraint ");
				isValid = false;
				return tableau;
			}
		}
		tableau[tableau.length-1][tableau[0].length-1] = 0;
		String output = "";
		for(int i = 0; i < tableau.length;i++){
			for(int k =0; k < tableau[0].length; k++){
				if(k == (tableau[0].length-1)){
					output += tableau[i][k] + "\n";
					continue;
				}
				output += tableau[i][k] + " ";
			}
		}
		System.out.println(output);
		isValid = true;
		return tableau;
	}
}
