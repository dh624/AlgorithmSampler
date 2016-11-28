import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DisplayFrame extends JFrame implements ActionListener{
	private StringBuilder output = new StringBuilder();
	private int col_index;
	private int row_index;
	private JTextArea area;
	private JScrollPane pane;
	private JButton next = new JButton("Next");
	private JPanel buttonpanel = new JPanel();
	double[][]tableau;
	
	public DisplayFrame(double[][] tableau){
		output.append("Tableau from Original LP \n");
		for(int i = 0; i < tableau.length;i++){
			for(int k =0; k < tableau[0].length; k++){
				if(k == (tableau[0].length-1)){
					output.append(tableau[i][k] + "\n");
					continue;
				}
				output.append(tableau[i][k] + " ");
			}
		}
		this.tableau = tableau;
		this.setTitle("Algorithm Results");
		buttonpanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		buttonpanel.add(next);
		next.addActionListener(this);
		area = new JTextArea(50,50);
		area.setText(output.toString());
		pane = new JScrollPane(area);
		this.setLayout(new BorderLayout(10,10));
		this.add(pane,BorderLayout.CENTER);
		this.add(buttonpanel,BorderLayout.SOUTH);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//simplex algorithm step
		output.append("-----Next Step-----\n");
		if(!isOptimal()){
			if(pivot_element()){
				tableau = gauss_Elim(row_index,col_index);
				output.append("Tableau after row operations \n");
				for(int i = 0; i < tableau.length;i++){
					for(int k =0; k < tableau[0].length; k++){
						if(k == (tableau[0].length-1)){
							output.append(tableau[i][k] + "\n");
							continue;
						}
						output.append(tableau[i][k] + " ");
					}
				}
				area.setText(output.toString());
			}			
		}
		
		
	}
	public  double[][] gauss_Elim(int i,int j){			
		double temp=tableau[i][j];
		//divide coefficients of row of pivot element by the pivot element//
		for(int l=0;l<tableau[0].length;l++){			
			tableau[i][l]=tableau[i][l]/temp;
		}					
		//perform Gaussian elimination on other rows//
	    for(int k=0;k<tableau.length;k++){    
	    	if(k==i){
	    		continue;
	    	}
	    	double temp1=tableau[k][j];
	    	for(int l=0;l<tableau[0].length;l++){	    	
	    		tableau[k][l]=tableau[k][l]-temp1*tableau[i][l];	    		
	    	}	    		
	    }	    
	  //array after elimination//
	    String output = "";
	  	for(int k=0;k<tableau.length;k++){
	  		for(int l=0;l<tableau[0].length;l++){
	  			if(l == tableau[0].length-1){
	  				output += tableau[k][l] + "\n";
	  				continue;
	  			}
	  			output += tableau[k][l] + " ";
	  		}	  			
	  	}
	  	System.out.println(output);
	  	return tableau;		
	}	
	public boolean pivot_element(){
		double elem=tableau[tableau.length-1][0];
		int row_index=0,col_index=0;
		for(int k=1;k<tableau[0].length-1;k++){
			if(tableau[tableau.length-1][k] < elem){
				elem=tableau[tableau.length-1][k];
				col_index=k;
			}
		}
		output.append("Entering variable is x" + col_index + "\n");
		area.setText(output.toString());
		if(elem>0){
			System.out.println("Solution is already optimal");
		}
		double min=-1.0;
		for(int l=0;l<tableau.length-1;l++){
			if(tableau[l][col_index]<=0){
				continue;
			}
		    else if(min==-1.0){
				min=(tableau[l][tableau[0].length-1]/tableau[l][col_index]);
				row_index=l;
			}
		    if(min==0 && tableau[l][tableau[0].length-1]!=0){ 
		    	min=tableau[l][tableau[0].length-1]/tableau[l][col_index];
		    	row_index=l;
		    }else if((min!=-1||min!=0) && tableau[l][tableau[0].length-1]!=0 && min>(tableau[l][tableau[0].length-1]/tableau[l][col_index])){
				min=tableau[l][tableau[0].length-1]/tableau[l][col_index];
				row_index=l;
			}			
		}
		if(min==-1.0){
			System.out.println("There is no optimal solution");
			output.append("No Optimal Solution exists \n");
			area.setText(output.toString());
			return false;
		}else{
			System.out.println(row_index);
			System.out.print(col_index);
			this.row_index = row_index;
			this.col_index = col_index;
			output.append("Entering variable is x" + col_index + "\n");
			output.append("Variable replacing row " + row_index + "\n");
			area.setText(output.toString());
			return true;
		}		
	}
	public boolean isOptimal(){
		 int m=tableau.length-1;
		 for (int i=0;i<tableau[m].length-1;i++){
			 if(tableau[m][i]<0){
				 output.append("Optimality Criterion not yet satisfied \n");
				 area.setText(output.toString());
				 return false;	
			 }
		 }
		 output.append("Optimality Criterion satisfied \n");
		 area.setText(output.toString());
		 return true;
	}
}
