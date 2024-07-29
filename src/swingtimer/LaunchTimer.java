package swingtimer;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;



public class LaunchTimer  {
	public static void main(String[] args)
	   {
	      EventQueue.invokeLater(() -> {
			SwingTimer frame = new SwingTimer();
	         
	 		try {
	 			java.net.URL icon = LaunchTimer.class.getResource("/resources/timer.png");
				frame.setIconImage(ImageIO.read(icon));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	         frame.setTitle("Timer");
	         
	         frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	 		
	 		 frame.addWindowListener(new WindowAdapter() {
	 		      @Override
	 		      public void windowClosing(WindowEvent e) {
	 		        
	 		        int res = JOptionPane.
	 		         showConfirmDialog(null, "Do you realy want to exit?");
	 		        if ( res == JOptionPane.YES_OPTION )
	 		         System.exit(0);
	 		      }
	 		      });
	 		 
	         frame.setVisible(true);
	      });
	   }

}
