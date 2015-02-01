package input_rome_graph_data_output_input.txt;

/*
 * Give test_input.txt
 * n m // n is vertices number, m is edges number
 * edges
 * 
 * Output test_input.txt
 * n m // n is vertices number, m is edges number
 * vertices coordination
 * edges
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

class vertice3D {
	public double x, y, z;

	public vertice3D(double X, double Y, double Z) {
		x = X;
		y = Y;
		z = Z;

	}
}

public class input_romegraph_txt_output_input_txt {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int i;
		
		
		File f = new File("C:/Users/firestation/workspace/Graph drawing/src/input_rome_graph_data_output_input/txt/test_input1.txt");
		
		Scanner input = null;
		try {
			input = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int n = input.nextInt(), m = input.nextInt(); // n is number of
														// vertices. m is number
														// of edges.

		//System.out.printf("n = %d m = %d\n", n, m);
		
		/*
		 * Do vertices coordination.
		 */
		
		double lat,lng;
		double lat_radian, lng_radian;
		double R = 1;
		
		vertice3D [] vertice_arr ;
		
		vertice_arr = new vertice3D[n];
				
		double x, y, z;
		
		for( i=0 ; i<n ; i++ )
		{	
			
			lat = 0;
			lng = (6.28 * i) / n;
			
			/*
			lat_radian = Math.toRadians(lat);
 			lng_radian = Math.toRadians(lng);
			*/
 			lat_radian = lat;
 			lng_radian = lng;
			
 			
 			x = R * Math.cos(lat_radian) * Math.cos(lng_radian);
 			y = R * Math.cos(lat_radian) * Math.sin(lng_radian);
 			z = 0;
 			
 			vertice_arr[i] = new vertice3D(x, y, z);
 			
 			
		}
		/*
		try {
	        PrintStream out = new PrintStream(new FileOutputStream("C:/Users/firestation/workspace/Graph drawing/src/test4/output.txt"));
	        for ( i = 0; i < n; i++)
	            	
	        //out.println("vertice" + i + "	x =		" + vertice_arr[i].x + "	y =		" + vertice_arr[i].y + "	z =		" + 0);
	          out.println(vertice_arr[i].x + " " + vertice_arr[i].y + " " + 0);

	        out.close();

	      } catch (FileNotFoundException e) {
	        e.printStackTrace();
	      }
	      */
		
		/*
		 * Do Grapg(V,E)
		 */
		
		int j;
		int a , b;
		
		int[][] graph = new int[n][n];

		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				graph[i][j] = 0;
			}
		}
		
		
		for (i = 0; i < m; i++) {

			a = input.nextInt();
			b = input.nextInt();
			// System.out.printf("edges.a = %d edges.b = %d \n", a, b);
			
			graph[a][b] = graph[b][a] = 1;

		}
		
		
		try {
	        PrintStream out = new PrintStream(new FileOutputStream("C:/Users/firestation/workspace/Graph drawing/src/input_rome_graph_data_output_input/txt/test_output1.txt"));
	        out.println(n + " " + m);
	        
	        out.println("\n");
	        
	        for ( i = 0; i < n; i++){
	            for( j=0 ; j<n ; j++ ){
	        //out.println("vertice" + i + "	x =		" + vertice_arr[i].x + "	y =		" + vertice_arr[i].y + "	z =		" + 0);
	             if(graph[i][j] == 1)
	            	out.println(i + " " + j);
	        
	            }
	        }
	        out.close();

	      } catch (FileNotFoundException e) {
	        e.printStackTrace();
	      }
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		Improved_node_placement_DP draw_3D_graph = new Improved_node_placement_DP();
		
		draw_3D_graph.placenode(f);
		*/
	}

}

