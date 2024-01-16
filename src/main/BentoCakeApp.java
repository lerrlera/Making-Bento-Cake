package main;

import javax.swing.JFrame;

public class BentoCakeApp extends JFrame {

	private static final long serialVersionUID = 1L;

	public BentoCakeApp(String title) {
		super(title);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BentoCakePanel bpnl = new BentoCakePanel(this);
		this.add(bpnl); 
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main (String[] args){
		new BentoCakeApp("Making a Bento Cake");
		
	}
}
