package ParmecyManagement;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.*;

public class SplashScreen extends JFrame implements Runnable {

	Thread thred;

	SplashScreen() {
		setSize(360, 260);
		setDefaultCloseOperation(3);	
		setResizable(false);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setTitle("Pharmecy Maneger");

		ImageIcon imag = new ImageIcon(getClass().getResource("pharaa.png"));
		Image imgScaled = imag.getImage().getScaledInstance(380, 280, Image.SCALE_DEFAULT);
		ImageIcon splash = new ImageIcon(imgScaled);
		JLabel image = new JLabel(splash);
		add(image);

		thred = new Thread(this);
		thred.start();

		setVisible(true);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(4000);
			dispose();
			new LoginFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
