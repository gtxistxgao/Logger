package SwingStudyExample;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

public class CanvesTest extends Canvas {

	public void paint(Graphics g) {

		Toolkit t = Toolkit.getDefaultToolkit();
		Image i = null;
		try {
			i = t.getImage(new URL("http://www.javatpoint.com/images/swingimage.jpg"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(i, 120, 100, this);

	}

	public static void main(String[] args) {
		CanvesTest m = new CanvesTest();
		JFrame f = new JFrame();
		f.add(m);
		f.setSize(400, 400);
		f.setVisible(true);
	}

}