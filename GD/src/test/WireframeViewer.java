package test;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class vertice3D {
	public double x, y, z;
	public vertice3D(double X, double Y, double Z) {
		x = X;
		y = Y;
		z = Z;
		
	}
}

class edge {
	public int a, b;
	public edge(int A, int B) {
		a = A;
		b = B;
	}
}





public class WireframeViewer extends Applet implements MouseListener,
		MouseMotionListener {

	int width, height;
	int mx, my; // the most recently recorded mouse coordinates

	int i, j;
	int count_vertice, count_edges;

	Image backbuffer;
	Graphics backg;

	int azimuth = 30, elevation = 35;

	vertice3D[] vertices;
	edge[] edges;


	File f = new File("C:/Users/firestation/workspace/Graph drawing/src/test/src/test/input.txt");
	
	public void init() {
		
		
		
		//File f = new File("C:\Users\firestation\workspace\Graph drawing\src\test\src\test/input.txt");
		Scanner input = null;
		try {
			input = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// backspace size
		width = 1680;
		height = 980;

		int n = input.nextInt(), m = input.nextInt(); // n is number of vertices. m is number of edges.
		
		System.out.printf("n = %d m = %d\n",n,m);
		

		vertices = new vertice3D[n];
		
		double x,y,z;
		
		for (i = 0; i < n; i++) {
			
			/*
			lat_radian = Math.toRadians(lat);
			lng_radian = Math.toRadians(lng);
			
			// System.out.format("lat_radian = %.4f lng_radian = %.4f %n",lat_radian,lng_radian);
			x_coordinate = R * Math.cos(lat_radian) * Math.cos(lng_radian);
			y_coordinate = R * Math.cos(lat_radian) * Math.sin(lng_radian);
			z_coordinate = R * Math.sin(lat_radian);
			*/
			
			x = input.nextDouble();
			y = input.nextDouble();
			z = input.nextDouble();			
			
			//System.out.printf("vertices.x = %f vertices.y = %f vertices.y = %f\n",x,y,z);
			
			
			vertices[i] = new vertice3D(x, y, z);
			
			System.out.printf("vertices.x = %f vertices.y = %f vertices.y = %f\n",vertices[i].x,vertices[i].y,vertices[i].z);
			
		}
		edges = new edge[m];
		
		int a, b;
		
		for (i = 0; i < m; i++) {
			
			a = input.nextInt() ;
			b = input.nextInt() ;
			System.out.printf("edges.a = %d edges.b = %d ",a,b);
			
			edges[i] = new edge(a , b);

		}
		
		backbuffer = createImage(width, height);
		backg = backbuffer.getGraphics();
		drawWireframe(backg);

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	
	
	void drawWireframe(Graphics g) {

		// compute coefficients for the projection
		double theta = Math.PI * azimuth / 180.0;
		double phi = Math.PI * elevation / 180.0;
		float cosT = (float) Math.cos(theta), sinT = (float) Math.sin(theta);
		float cosP = (float) Math.cos(phi), sinP = (float) Math.sin(phi);
		float cosTcosP = cosT * cosP, cosTsinP = cosT * sinP, sinTcosP = sinT
				* cosP, sinTsinP = sinT * sinP;

		// project vertices onto the 2D viewport
		Point[] points;
		points = new Point[vertices.length];
		int j;
		int scaleFactor = width / 30; // fix the size of graph
		float near = 3; // distance from eye to near plane
		float nearToObj = 1.5f; // distance from near plane to center of object
		for (j = 0; j < vertices.length; ++j) {
			double x0 = vertices[j].x;
			double y0 = vertices[j].y;
			double z0 = vertices[j].z;

			// compute an orthographic projection
			double x1 = cosT * x0 + sinT * z0;
			double y1 = -sinTsinP * x0 + cosP * y0 + cosTsinP * z0;

			// now adjust things to get a perspective projection
			double z1 = cosTcosP * z0 - sinTcosP * x0 - sinP * y0;
			x1 = x1 * near / (z1 + near + nearToObj);
			y1 = y1 * near / (z1 + near + nearToObj);

			// the 0.5 is to round off when converting to int
			points[j] = new Point((int) (width / 2 + scaleFactor * x1 + 0.5),
					(int) (height / 2 - scaleFactor * y1 + 0.5));
		}

		// draw the wireframe
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		for (j = 0; j < edges.length; ++j) {
			g.drawLine(points[edges[j].a].x, points[edges[j].a].y,
					points[edges[j].b].x, points[edges[j].b].y);
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		e.consume();
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		// get the latest mouse position
		int new_mx = e.getX();
		int new_my = e.getY();

		// adjust angles according to the distance travelled by the mouse
		// since the last event
		azimuth -= new_mx - mx;
		elevation += new_my - my;

		// update the backbuffer
		drawWireframe(backg);

		// update our data
		mx = new_mx;
		my = new_my;

		repaint();
		e.consume();
	}

	public void update(Graphics g) {
		g.drawImage(backbuffer, 0, 0, this);
		showStatus("Elev: " + elevation + " deg, Azim: " + azimuth + " deg");
	}

	public void paint(Graphics g) {
		update(g);
	}
}
