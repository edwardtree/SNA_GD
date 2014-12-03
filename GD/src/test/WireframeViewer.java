package test;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import java.io.*;
import java.nio.charset.Charset;
import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

import static java.lang.System.*;

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

	vertice3D[] init_vertices;
	edge[] init_edges;

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
		
		count_vertice = n;
		count_edges = m; // In order to declare vertice[] and edge[] to draw graph.
		
		double add_lng = 45;

		init_vertices = new vertice3D[n];

		double lat = 0, lng = 0, lat_radian, lng_radian, x_coordinate, y_coordinate, z_coordinate; // initial
																									// angel

		double R = 3;

		for (i = 0; i < n; i++) {
			lat_radian = Math.toRadians(lat);
			lng_radian = Math.toRadians(lng);

			// System.out.format("lat_radian = %.4f lng_radian = %.4f %n",lat_radian,lng_radian);
			x_coordinate = R * Math.cos(lat_radian) * Math.cos(lng_radian);
			y_coordinate = R * Math.cos(lat_radian) * Math.sin(lng_radian);
			z_coordinate = R * Math.sin(lat_radian);

			init_vertices[i] = new vertice3D(x_coordinate, y_coordinate,
					z_coordinate);

			// lat+=10;
			lng += add_lng;

		}
		init_edges = new edge[m];

		for (i = 0, j = n; i < m | j > 0; i++, j = j - 2) {
			System.out.format("lat_radian = %d lng_radian = %d %n", i, j);
			init_edges[i] = new edge(i * 2, j - 1);

		}
		
		//double length = 0.0;
		
		
				
		
		
		/* finish calculation and store value to the vertices and edges. */

		vertices = new vertice3D[count_vertice];

		edges = new edge[count_edges];

		for (i = 0; i < count_vertice; i++) {

			vertices[i] = new vertice3D(init_vertices[i].x, init_vertices[i].y,
					init_vertices[i].z);
		}
		for (i = 0, j = n; i < count_edges | j > 0; i++, j = j - 2) {
			edges[i] = new edge(init_edges[i].a, init_edges[i].b);
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
		int scaleFactor = width / 20; // fix the size of graph
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
