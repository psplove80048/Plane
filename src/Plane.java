import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Plane extends JFrame {
	Image icon, dart, back;	
	JPanel pm = new JPanel(null);
	int where;
	int speed;
	int play = 0;
	Point[] p = new Point[60];
	int timer;
	int arrow;
	int go;
	int attact;
	int high = 0;
	float t;

	public static void main(String[] args) {
		Plane p = new Plane();
		p.setResizable(false);
		p.setSize(640, 640);
		p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p.getContentPane().add(p.pm);
		p.setVisible(true);
		p.setLocationRelativeTo(null);
		p.setFocusable(true);
		p.repaint();
		p.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				p.requestFocus();
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_LEFT)
					p.go = -1;
				if (key == KeyEvent.VK_RIGHT)
					p.go = 1;
				if (key == KeyEvent.VK_SPACE)
					p.play = 1;

			}

			public void keyReleased(KeyEvent k) {
				p.go = 0;
			}

			public void keyTyped(KeyEvent k) {
			}

		});
		try {
			p.icon = ImageIO.read(new File("icon.png"));
			p.dart = ImageIO.read(new File("1.png"));
			p.back = ImageIO.read(new File("back.jpg"));
		} catch (Exception ex) {
			System.out.println("No example.jpg!!");
		}
		
		p.where = 330;
		p.speed = 0;
		p.timer = 0;
		p.arrow = 0;
		p.go = 0;
		p.attact = 20;
		for (int i = 0; i < 60; i++) {
			p.p[i] = new Point(0, 700);
		}
		
		
		p.repaint();
		while (p.play == 0) {
			try {

				Thread.sleep(120);

			} catch (InterruptedException e) {
			}
		}
        
		
		while (p.play == 1) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			p.timer++;
			if (p.timer % p.attact == 0) {
				p.t = (float) p.timer / 1000;
				if (p.t > 2)
					p.t = 2;
				for (int i = 0; i < Math.random() * (1 + p.t); i++) {
					p.p[p.arrow].setLocation(Math.random() * 610, 0);
					p.arrow++;
					if (p.arrow == 59)
						p.arrow = 0;
				}
			}
			if (p.timer % 100 == 0 && p.attact > 4)
				p.attact--;
			p.repaint();
			p.setTitle("存活時間:" + p.timer / 20 + "秒");
		}
		p.gameover();
	}



	public void gameover() {
		if (timer / 20 > high)
			high = timer / 20;
		repaint();
		while (play == 2) {
			try {

				Thread.sleep(120);

			} catch (InterruptedException e) {
			}
		}
		where = 330;
		speed = 0;
		timer = 0;
		arrow = 0;
		go = 0;
		attact = 20;
		for (int i = 0; i < 60; i++) {
			p[i] = new Point(0, 700);
		}
		while (play == 1) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			timer++;
			if (timer % attact == 0) {
				t = (float) timer / 1000;
				if (t > 2)
					t = 2;
				for (int i = 0; i < Math.random() * (1 + t); i++) {
					p[arrow].setLocation(Math.random() * 610, 0);
					arrow++;
					if (arrow == 59)
						arrow = 0;
				}
			}
			if (timer % 100 == 0 && attact > 4)
				attact--;
			repaint();
			setTitle("存活時間:" + timer / 20 + "秒");
		}
		gameover();
	}

	public void paint(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		Graphics2D g2 = (Graphics2D) g;

		if (play == 1) {
			g.drawImage(back, 0, 0, null);
			if (go == 0)
				speed *= 0.5;
			if (go == 1)
				speed += 5;
			if (go == -1)
				speed -= 5;
			if (speed > 30)
				speed = 30;
			if (speed < -30)
				speed = -30;
			where += speed;
			if (where < 0)
				where = 0;
			if (where > 590)
				where = 590;
			g.drawImage(icon, where, 550, null);
			for (int i = 0; i < 60; i++) {
				p[i].setLocation(p[i].getX(), p[i].getY() + 10);
				if (p[i].getY() <= 640)
					g.drawImage(dart, (int) p[i].getX(), (int) p[i].getY(), null);
				if (where + 50 > p[i].getX() && where - 30 < p[i].getX() && p[i].getY() + 50 > 550 && p[i].getY() < 600)
					play = 2;
			}
		}
		if (play == 2) {
			g.drawImage(back, 0, 0, null);
			g2.drawString("你撐了" + timer / 20 + "秒", 200, 200);
			g2.drawString("按空白鍵重新", 175, 400);
			g2.drawString("最高分：" + high + "秒", 175, 600);
		}
		if (play == 0) {
			g.drawImage(back, 0, 0, null);
			g2.drawString("飛機閃飛彈遊戲", 150, 200);
			g2.drawString("按空白鍵開始", 175, 400);
		}

	}


}
