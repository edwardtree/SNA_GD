package Draw_graph_edges_color_change;

/*
 * Give test_input.txt
 * n m // n is vertices number, m is edges number
 * vertices
 * edges
 * 
 * Output Graph
 * 
 */


import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.Buffer;
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

class edge_have_bundling_number {
	public int a, b ,c;
	public edge_have_bundling_number(int A, int B ,int C) {
		a = A;
		b = B;
		c = C;
	}
}



public class Draw_graph_edges_color_change extends Applet implements MouseListener,
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
	edge_have_bundling_number[] edges_bundling_number;
	
    Graphics bufGraphics = null;
	

	File f = new File("C:/Users/firestation/workspace/Graph drawing/src/Draw_graph_edges_color_change/Edges_bundling_test3.txt");
	
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
			
			System.out.printf("vertices.x = %f vertices.y = %f vertices.z = %f\n",vertices[i].x,vertices[i].y,vertices[i].z);
			
		}
		edges = new edge[m];
		
		int a, b, c;
		
		for (i = 0; i < m; i++) {
			
			a = input.nextInt() ;
			b = input.nextInt() ;
			System.out.printf("edges.a = %d edges.b = %d \n",a,b);
			
			edges[i] = new edge(a , b);

		}
		
		edges_bundling_number = new edge_have_bundling_number[m];
		
		
		for (i = 0; i < m; i++) {
			
			a = input.nextInt() ;
			b = input.nextInt() ;
			c = input.nextInt() ;
			System.out.printf("edges.a = %d edges.b = %d edges_bundling_number.c = %d\n",a,b,c);
			
			edges_bundling_number[i] = new edge_have_bundling_number(a , b ,c);

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
		int scaleFactor = width/3; // fix the size of graph
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
		//g.setColor(new Color(255,255,255));
		
		for (j = 0; j < edges.length; ++j) {
			i = 0;
			for(int k = 0 ; k< edges.length ; k++)
			{
				if(i < edges_bundling_number[k].c)
					i = edges_bundling_number[k].c;
			}
			g.setColor(new Color((255-(255/i)*edges_bundling_number[j].c),0,((255/i)*edges_bundling_number[j].c)));
			Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(edges_bundling_number[j].c));
			g.drawLine(points[edges[j].a].x, points[edges[j].a].y,points[edges[j].b].x, points[edges[j].b].y);
			
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
	
	double length(double a,double b)
	{
		return Math.sqrt(a*a+b*b);	
	}
}