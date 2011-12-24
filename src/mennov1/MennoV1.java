package mennov1;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import lib.Looker;
import lib.Looker.NoInputAvailableException;
import lib.Mover;

public class MennoV1{

	static MennoV1 master;
	private Looker l;
	private Mover m;
	private JFrame frame;
	int face_move_interval;

	public static final int IWIDTH = 240;
	public static final int IHEIGHT = 320;

	public static void main(String args[]) {
		MennoV1.getInstance();
	}

	// Het is een singleton pattern
	public static MennoV1 getInstance() {
		if(master == null) master = new MennoV1();
		return master;
	}

	public MennoV1(){

		try {
			l = Looker.getInstance();
			
			frame = new JFrame("Webcam output");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new BorderLayout());
			
			final JPanel panel = new JPanel();			
			panel.setLayout(null);

			final DrawingComponent overlayComponent = new DrawingComponent();
			overlayComponent.setOpaque(false);
			overlayComponent.animate();
			panel.add(overlayComponent);
			
			final Component webcamComponent = l.getComponent();
			//panel.add(webcamComponent);			
			
			frame.setVisible(true);
			frame.setContentPane(panel);
			
			l.setOnResizeListener(new OnResizeListener(){
				public void onResize(int width, int height) {
					Dimension size = new Dimension(width, height);
					frame.setSize(size);
					overlayComponent.setSize(size);
					panel.setSize(size);
					webcamComponent.setSize(size);
				}
			});
		} catch (NoInputAvailableException e) {
			//do nothing
		}
		//m = Mover.getInstance();
		
		// Initialize threads
		//Thread [] clients = new Thread[3];
		//clients[0] = new Thread(new IrcClient());
		//clients[1] = new Thread(new JabberClient());
		//clients[2] = new Thread(new TerminalClient());
		//for(Thread client : clients) {
		//			client.start();
		//}
	}
	
	private class DrawingComponent extends JComponent{
		private static final long serialVersionUID = 1L;
		public static final int ANIMATION_DELAY = 20;
		public void animate(){
			new Timer(ANIMATION_DELAY, new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					l.detectFaces();
					repaint();
				}
			}).start();
		}
		
		public void paintComponent(Graphics g){
			l.drawFaces(g);
		}
	
	}
	public interface OnResizeListener{
		public void onResize(int width, int height);
	}
}

