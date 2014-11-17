package test.src.test;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

import javax.vecmath.Vector2d;

public class Bezier extends Applet implements AdjustmentListener {
	BezierCanvas drawZone;

	boolean debugging = false;
	Scrollbar hsbNsteps;

	public void init() {
		setLayout(new BorderLayout(2, 2));

		drawZone = new BezierCanvas();
		Panel controlPanel = new Panel();
		add("South", controlPanel);
		add("Center", drawZone);

		int nsteps = 20;
		// Adjustment the bar. Scrollbar(Scrollbar.HORIZONTAL, 20, x, y, z). 
		hsbNsteps = new Scrollbar(Scrollbar.HORIZONTAL, 20, 59, 60, 60); 
		hsbNsteps.addAdjustmentListener(this);
		controlPanel.setLayout(new GridLayout(2, 1, 2, 2));
		controlPanel.add(hsbNsteps);

		setBackground(Color.black);
	}

	// ////////////////////////////////////////
	// AdjustmentListener actions
	// ////////////////////////////////////////
	public synchronized void adjustmentValueChanged(AdjustmentEvent e) {
		drawZone.setSteps(e.getValue());// e.getValue () : edge number of line
		drawZone.repaint();
		// try {wait ();}catch (InterruptedException ex) {}
	}

};

class BezierCanvas extends Canvas implements MouseListener, MouseMotionListener {
	Dimension boardSize;
	Vector2d[] ctrlPts = new Vector2d[3];
	int nsteps;
	Image Buffer = null;
	Graphics bufGraphics = null;

	public BezierCanvas() {
		nsteps = 20;

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);

		boardSize = new Dimension(w, h);
		Buffer = createImage(w, h);
		bufGraphics = Buffer.getGraphics();

		for (int i = 0; i < 3; i++) {
			ctrlPts[i] = new Vector2d((i + 1) * (boardSize.width / 5),
					boardSize.height / 2);
		}
	}

	public void setSteps(int n) {
		nsteps = n;
	}

	
	
	
	public void drawOffscreen() {
		// Fill in the background
		bufGraphics.setColor(Color.black);
		bufGraphics.fillRect(0, 0, boardSize.width, boardSize.height);

		// Draw the four points
		bufGraphics.setColor(Color.white);
		String d = "";
		for (int i = 0; i < 3; i++) {
			bufGraphics.fillRect((int) ctrlPts[i].x - 4,(int) ctrlPts[i].y - 2, 5, 5);
			d = d + "x[" + i + "] = " + (int) ctrlPts[i].x + ", y[" + i + "] = " + (int) ctrlPts[i].y + ";   ";
			System.out.format("d = %s \n", d);
		}
		// debugMsg (d);

		// Draw the Bezier curve
		bufGraphics.setColor(Color.blue);

		double[] B = new double[3];
		Vector2d lastPt = new Vector2d();
		for (int step = 0; step <= nsteps; step++) {
			double t = (double) step / (double) nsteps;
			double at = 1. - t;
			B[0] = at * at * at;
			B[1] = 3. * t  * at;
			//B[2] = 3. * t * t * at;
			B[2] = t * t * t;

			Vector2d nextPt = new Vector2d();
			nextPt.set(0., 0.);
			d = "";
			for (int p = 0; p < 3; p++) {
				nextPt.x += ctrlPts[p].x * B[p];
				nextPt.y += ctrlPts[p].y * B[p];
			}
			if (step > 0) {
				bufGraphics.drawLine((int) (lastPt.x + .5),
						(int) (lastPt.y + .5), (int) (nextPt.x + .5),
						(int) (nextPt.y + .5));
			}
			lastPt.set(nextPt.x, nextPt.y);
		}
	}

	
	
	
	
	
	
	
	public synchronized void paint(Graphics g) {
		drawOffscreen();
		g.drawImage(Buffer, 0, 0, null);
		notifyAll();
	}

	public final void update(Graphics g) {
		paint(g);
	}

	// ////////////////////////////////////////
	// MouseListener actions
	// ////////////////////////////////////////
	int dragging = -1;

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < 3; i++) {
			if (Math.abs(e.getX() - ctrlPts[i].x) < 5
					&& Math.abs(e.getY() - ctrlPts[i].y) < 5)
				dragging = i;
		}
	}

	public void mouseReleased(MouseEvent e) {
		dragging = -1;
	}

	public void mouseClicked(MouseEvent e) {
	}

	// ////////////////////////////////////////
	// MouseEventListener actions
	// ////////////////////////////////////////
	public void mouseMoved(MouseEvent e) {
	}

	public synchronized void mouseDragged(MouseEvent e) {
		if (dragging >= 0) {
			// statusMsg ("Dragged dot #" + dragging + " to ("
			// + e.getX () + ", " + e.getY () + ")");
			ctrlPts[dragging].set(e.getX(), e.getY());
		}
		repaint();
		// try {wait ();}catch (InterruptedException ex) {}
	}
}