package Node_place_on_circular;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import test.Node_placement_on_circular;

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


public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int i;
		
		
		File f = new File("C:/Users/firestation/workspace/Graph drawing/src/Node_place_on_circular/test_input.txt");
		
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
				if( i==j ) graph[i][j] = 1;
			}
		}
		
		
		for (i = 0; i < 2*m; i++) {

			a = input.nextInt();
			b = input.nextInt();
			// System.out.printf("edges.a = %d edges.b = %d \n", a, b);
			
			graph[a][b] = 1;

		}
		
		int [][] graph_edges_numbers = new int[2][m];
		int aa = 0;
		
		for (i = 0 ; i < n ; i++ )
		{
			for( j=0 ; j<n ; j++)
			{
				if(graph[i][j] == 1  && i!=j && i<j)
				{
					graph_edges_numbers[0][aa] = i;
					graph_edges_numbers[1][aa] = j;				
					aa++;
				}
			}
		}
		
		
		
		
/*
 * n is vertices number;
 * graph G(V,E);
 * vertice_arr is vertices coordination;
 * 0 is right_most_vertex;
 * 5 is left_most_vertex ;
 * ordering is :
 * 	0 to 99 . we assume closer 0 is right and closer 99 is left.
 */
		
		Improved_node_placement_DP node_placement = new Improved_node_placement_DP();
		
	
		
		int [] improved_ordering_V ;
		
		int x0,x1,x2,x3,x4,x5,x6;
		
		int [] vertices_orderiing = new int[n];
		
		
		for( j=0 ; j<n ; j++ )
		{
			vertices_orderiing[j] = j; 
		}
		
		try {
	        PrintStream out = new PrintStream(new FileOutputStream("C:/Users/firestation/workspace/Graph drawing/src/Node_place_on_circular/test_output1.txt"));
	        
	        
	        for( i=0 ; i<n ; i++)
	        {
	        	x0 = vertices_orderiing[i] ;
	        	x1 = vertices_orderiing[(i+1)%n];
	        	x2 = vertices_orderiing[(i+2)%n];
	        	x3 = vertices_orderiing[(i+3)%n];
	        	x4 = vertices_orderiing[(i+4)%n];
	        					
	        	
	        	
	        	int [] ordering_V =  {x0,x1,x2,x3,x4};
	        	
	        	for( j=0 ; j<ordering_V.length ; j++ )
				{
        			System.out.printf("x%d = %d \n",j,ordering_V[j]);
				}
	        	
	        	improved_ordering_V = new int[ordering_V.length];
		
	        	improved_ordering_V  = node_placement.MinCA_DP(n,m,graph,vertice_arr,ordering_V); // replace vertices 0 to 5; 
			
	        	for( j=0 ; j<ordering_V.length ; j++ )
				{
        			System.out.println("improved_ordering_V[" + (i+j) +  "] = " + improved_ordering_V[j] + "\n");
				}
	        	int k = 0;
	        	int tmp1 = 0;
	        	
	        	for( j=0 ; j<ordering_V.length ; j++ )
				{
	        		if( j+i>n-1)
	        		tmp1 = (j+i)%n;
	        		else
	        			tmp1 = j+i;
	        		
	        		vertices_orderiing[tmp1] = improved_ordering_V[k];
	        		k++;
				}
	        	/*
	        	for( j=0 ; j<ordering_V.length ; j++ )
					{
	        		if( j+i>n-1)
		        		tmp1 = (j+i)%n;
		        		else
		        			tmp1 = j+i;
	        		out.println("vertices_orderiing[" + (tmp1) +  "] = " + vertices_orderiing[tmp1] + "\n");
					}
	        	
	        	out.println("\n");
	        	*/
	        }
	        /*
	        for( j=0 ; j<n ; j++ )
			{
    		out.println("vertices_orderiing[" + j +  "] = " + vertices_orderiing[j] + "\n");
			}
	        */

	        
	        out.println( n +  " " + m + "\n");	
		    
		    out.println("\n");
		    
		    for( i=0 ; i<n ; i++)
		    {
		    		out.println(vertice_arr[vertices_orderiing[i]].x +  " " + vertice_arr[vertices_orderiing[i]].y + " " + 0 +"\n");		     		
		    }
		    
		    out.println("\n");
		    
		    int tmp = 0, print_k=0;
		    
		    for( i=0 ; i<n ; i++)
		    	for( j=0 ; j<m ; j++ )
		    	{	
		    		if(graph_edges_numbers[0][j] == vertices_orderiing[i])
		    		{	
		    			
		    			out.print(i + " ");
		    			
		    			for( int k = 0 ; k<n ; k++ )
		    			{
		    				if(graph_edges_numbers[1][j] == vertices_orderiing[k]) print_k = k;
		    			}
		    			out.println(print_k +"\n");
		    		}
		    	}
		    
		    out.println("\n");
		    
		    for( i=0 ; i<n ; i++)
		    	for( j=0 ; j<m ; j++ )
		    	{	
		    		if(graph_edges_numbers[0][j] == vertices_orderiing[i])
		    		{	
		    			
		    			out.print(i + " ");
		    			
		    			for( int k = 0 ; k<n ; k++ )
		    			{
		    				if(graph_edges_numbers[1][j] == vertices_orderiing[k]) print_k = k;
		    			}
		    			out.println(print_k + " 1" +"\n");
		    		}
		    	}
		    
        out.close();

		} catch (FileNotFoundException e) {
        e.printStackTrace();
		}
		
		

	}

}

