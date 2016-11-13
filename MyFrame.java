import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyFrame extends JFrame implements ActionListener{

	private JLabel objlabel = new JLabel("Objective Function");
	private String[] objboxoptions = {"Minimize", "Maximize"};
	private JComboBox<String> objbox = new JComboBox(objboxoptions);
	private JTextField objtxtfield = new JTextField(40);
	private JLabel subjlabel = new JLabel("Subject to:");
	private JTextField[] leftconstraints = new JTextField[10];
	private JTextField[] rightconstraints = new JTextField[10];
	private String[] inequalities = {"=","<=",">="};
	private JComboBox<String>[] constraintbox = new JComboBox[10];
	private JButton add = new JButton("Add constraint");
	private JButton execute = new JButton("Execute Algorithm");
	private JPanel objpanel = new JPanel();
	private JPanel[] constraintpanel = new JPanel[11];
	private JPanel centerpanel = new JPanel();
	private JPanel bottompanel = new JPanel();

	public MyFrame(){
		super();
		this.setTitle("Simplex Method Algorithm Sampler");
		this.setLayout(new BorderLayout(10,10));
		objpanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		objpanel.add(objlabel);
		objpanel.add(objbox);
		objpanel.add(objtxtfield);
		objtxtfield.setText("Enter Objective Function Here");
		centerpanel.setLayout(new GridLayout(10,1));
		this.add(objpanel, BorderLayout.NORTH);
		for(int i = 0; i < 10; i++){
			leftconstraints[i] = new JTextField(30);
			rightconstraints[i] = new JTextField(4);
			constraintbox[i] = new JComboBox(inequalities);
			constraintpanel[i] = new JPanel();
			constraintpanel[i].setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
			constraintpanel[i].add(leftconstraints[i]);
			constraintpanel[i].add(constraintbox[i]);
			constraintpanel[i].add(rightconstraints[i]);
			centerpanel.add(constraintpanel[i]);
		}
		this.add(centerpanel, BorderLayout.CENTER);
		bottompanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottompanel.add(add);
		bottompanel.add(execute);
		this.add(bottompanel, BorderLayout.SOUTH);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
