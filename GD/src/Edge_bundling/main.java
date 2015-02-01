package Edge_bundling;


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
		
		
		File f = new File("C:/Users/firestation/workspace/Graph drawing/src/Edge_bundling/test_output1.txt");
		
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
		
		
		int [][] Edges_set = new int[m][m];// record edge set. Just 0 and 1;
		
		int [][] graph_edges_numbers = new int[2][m];//record edge number;
				
		for (i = 0 ; i < 2*m ; i++ ) {

			a = input.nextInt();
			b = input.nextInt();
			// System.out.printf("edges.a = %d edges.b = %d \n", a, b);
			
			graph[a][b] = 1;
		}
		
		for (i = 0 ; i < m ; i++ )
		{
			Edges_set[i][i] = 1;
		}
		
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
		
		try {
		    PrintStream out = new PrintStream(new FileOutputStream("C:/Users/firestation/workspace/Graph drawing/src/Edge_bundling/graph_edges_numbers111111.txt"));
		    for( i=0 ; i<m ; i++) 
		    	out.println(graph_edges_numbers[0][i] + "     graph_edges_numbers[ " + 1 + "][" + i +  "] = " + graph_edges_numbers[1][i] +"\n");
		    out.close();

		  } catch (FileNotFoundException e) {
		    e.printStackTrace();
		  }
		

		int [][] ordering = new int[2][m]; 
		
		for( i=0 ; i<m ; i++ )
		{
			ordering[0][i] = graph_edges_numbers[0][i];
			ordering[1][i] = graph_edges_numbers[1][i];
		}
		
		
		
		//System.out.printf("tmp =" + tmp + "\n");
		
		
		AgglomerativeBundling Edge_bundling = new AgglomerativeBundling();
		
/*
 * n is vertices number;
 * graph G(V,E);
 * vertice_arr is vertices coordination;
 * 0 is right_most_vertex;
 * 5 is left_most_vertex ;
 * ordering is :
 * 	0 to 99 . we assume closer 0 is right and closer 99 is left.
 */
		int [][] final_bundling_Edges_set = Edge_bundling.AgglomerativeBundling_edges(n,m,graph,Edges_set,vertice_arr,ordering); // replace vertices 0 to 5; 
		
		
		
		
		
		try {
		    PrintStream out = new PrintStream(new FileOutputStream("C:/Users/firestation/workspace/Graph drawing/src/Edge_bundling/test2.txt"));

		    for( i=0 ; i<m ; i++)
		    	for( j=0 ; j<m ;j++)
		    	{
		    		out.print(final_bundling_Edges_set[i][j] );		    
		    		if( j == m-1 )out.println("\n" );
		    	}
		    out.close();

		  } catch (FileNotFoundException e) {
		    e.printStackTrace();
		  }
		  
		
		int sum = 0 , draft_number = 0;
		
		int [][] test_final_bundling_Edges_set = new int[m][m];
		
		for( i=0 ; i<m ; i++)
		{
			for( j=0 ; j<m ; j++)
			{
				test_final_bundling_Edges_set[i][j] = final_bundling_Edges_set[i][j];

			}
		}
		
		for( i=0 ; i<m ; i++)
		{
			for( j=0 ; j<m ; j++)
			{
				sum += test_final_bundling_Edges_set[i][j];
			}
			
			if(sum >1) 
			{
				for( j=0 ; j<m ; j++)
				{
					if (test_final_bundling_Edges_set[i][j] == 1 && j>i)
					{
						for(int k = 0 ; k < m ; k++)
						{
							test_final_bundling_Edges_set[j][k] = 0;
						}
					}
				}	
				draft_number += 2;
			}
			sum = 0;
			
		}
		
		int [] final_bundling_Edges_set_element_sum = new int[m];
		
		for( i=0 ; i<m ; i++)
		{
			for( j=0 ; j<m ; j++)
			{
				if( test_final_bundling_Edges_set[i][j]== 1)final_bundling_Edges_set_element_sum[i]++;
			}
		}

		System.out.printf("draft_number =" + draft_number + "\n");
				
		/*
		try {
		    PrintStream out = new PrintStream(new FileOutputStream("C:/Users/firestation/workspace/Graph drawing/src/Edge_bundling/test_final_bundling_Edges_set3.txt"));

		    for( i=0 ; i<m ; i++)
		    	for( j=0 ; j<m ;j++)
		    	{
		    		out.print(test_final_bundling_Edges_set[i][j] );		    
		    		if( j == m-1 )out.println("\n" );
		    	}
		    out.close();

		  } catch (FileNotFoundException e) {
		    e.printStackTrace();
		  }
		*/
		
		
		int [][] final_graph = new int[n+draft_number][n+draft_number]; 
		
		for( i=0 ; i<n ; i++)
		{
			for( j=0 ; j<n ; j++)
			{
				if( graph[i][j]== 1)
					final_graph[i][j] = graph[i][j];
				
			}
		}
		
		
		vertice3D[] vertice_and_draft_arr = new vertice3D[n+draft_number];
		
		double x1, y1, z1;
		
		for( i=0 ; i<n+draft_number ; i++ )
		{	
			
			if(i < n)
				vertice_and_draft_arr[i] = new vertice3D(vertice_arr[i].x, vertice_arr[i].y, vertice_arr[i].z);
			else
				vertice_and_draft_arr[i] = new vertice3D(0, 0, 0);
 			
		}
		
		int draft_count = 0;
		
				
		for( i=0 ; i<m ; i++ )
		{
			if(final_bundling_Edges_set_element_sum[i] > 1)
			{	
				int [] arr_A = new int[m];
				double [] arr_B = new double[4];
				
				for( j=0 ; j<m ; j++ )
				{	
					arr_A[j] = test_final_bundling_Edges_set[i][j];
					if( test_final_bundling_Edges_set[i][j] == 1 )
					{
						final_graph[ordering[0][j]][ordering[1][j]] = 0;
						final_graph[ordering[1][j]][ordering[0][j]] = 0;
						final_graph[ordering[0][j]][n+draft_count] = 1;
						final_graph[n+draft_count][ordering[0][j]] = 1;
						final_graph[ordering[1][j]][n+draft_count+1] = 1;
						final_graph[n+draft_count+1][ordering[1][j]] = 1;		
					}				
				}
				arr_B = Algo_bundling_edge_coordinate(m,arr_A , vertice_arr , graph_edges_numbers);
				
				vertice_and_draft_arr[n+draft_count].x = arr_B[0];
				vertice_and_draft_arr[n+draft_count].y = arr_B[1];
				vertice_and_draft_arr[n+draft_count+1].x = arr_B[2];
				vertice_and_draft_arr[n+draft_count+1].y = arr_B[3];
				
				final_graph[n+draft_count][n+draft_count+1] = 1;
				final_graph[n+draft_count+1][n+draft_count] = 1;
				final_graph[n+draft_count][n+draft_count] = 1;
				final_graph[n+draft_count+1][n+draft_count+1] = 1;
				draft_count+=2;
				
			}
		}
		
		
		
		// count new edges total number
		int total_edges_number = 0;
		
		for (i = 0 ; i < n+draft_count ; i++ )
		{
			for( j=0 ; j<n+draft_count ; j++)
			{
				if(final_graph[i][j] == 1 && i!=j)
				{
					total_edges_number++;
				}
			}
		}
		
		total_edges_number /= 2;
				
		aa = 0;
		
		
		int [][] final_graph_edges_numbers = new int[2][total_edges_number]; 
		int [][] every_bundling_edge_number = new int[3][total_edges_number];
		
		for (i = 0 ; i < n+draft_count ; i++ )
		{
			for( j=0 ; j<n+draft_count ; j++)
			{
				if(final_graph[i][j] == 1  && i!=j && i<j)
				{
					final_graph_edges_numbers[0][aa] = i;
					final_graph_edges_numbers[1][aa] = j;
					every_bundling_edge_number[0][aa] = i;
					every_bundling_edge_number[1][aa] = j;					
					aa++;
				}
			}
		}
		
		int very_bundling_edge_number_tmp = 0;
		
		for (i = 0 ; i < total_edges_number ; i++ )
		{
			every_bundling_edge_number[2][i] = 1;
			if(every_bundling_edge_number[0][i] > n-1 && every_bundling_edge_number[1][i] > n-1)
			{
				for( j=0 ; j<n+draft_count ; j++)
				{
					if(final_graph[every_bundling_edge_number[0][i]][j] == 1)
						very_bundling_edge_number_tmp++;
				}
				every_bundling_edge_number[2][i] = very_bundling_edge_number_tmp-2;
				very_bundling_edge_number_tmp = 0;
			}
		}
		
		
		
		
		
		
		
		
		
		int n_draft_number_tmp = n+draft_number;
		
		
		System.out.printf("n+draft_number =" + n_draft_number_tmp + "  total_edges_number =" + total_edges_number + "  m =" + m + "  aa =" + aa +"\n");
		
		try {
		    PrintStream out = new PrintStream(new FileOutputStream("C:/Users/firestation/workspace/Graph drawing/src/Edge_bundling/Edges_bundling_test1.txt"));
		    
		    out.println( n_draft_number_tmp +  " " + total_edges_number + "\n");	
		    
		    out.println("\n");
		    
		    for( i=0 ; i<n_draft_number_tmp ; i++)
		    {
		    		out.println(vertice_and_draft_arr[i].x +  " " + vertice_and_draft_arr[i].y + " " + 0 +"\n");		     		
		    }
		    
		    out.println("\n");
		    
		    for( i=0 ; i<total_edges_number ; i++) 
		    	out.println(final_graph_edges_numbers[0][i] + " " + final_graph_edges_numbers[1][i] +"\n");
		    
		    out.println("\n");
		    
		    for( i=0 ; i<total_edges_number ; i++) 
		    	out.println(every_bundling_edge_number[0][i] + " " + every_bundling_edge_number[1][i] + " " +every_bundling_edge_number[2][i] +"\n");
		    
		    out.close();

		  } catch (FileNotFoundException e) {
		    e.printStackTrace();
		  }
		
		
		
		
		
		
		
		
		
		
		
	
	}
	
	static double[] Algo_bundling_edge_coordinate(int m,int [] arr_A , vertice3D [] vertice_arr , int [][] graph_edges_numbers)
	{
		
 		double centroid_x1 = 0 , centroid_y1 = 0 , centroid_x2 = 0 , centroid_y2 = 0;
		
		int total_edge_sum = 0;
		
		double [] arr_B = new double[4];
		
		for( int i =0 ; i <m ; i++)
		{
			if(arr_A[i] == 1)
			{
				centroid_x1 += vertice_arr[graph_edges_numbers[0][i]].x;
				centroid_y1 += vertice_arr[graph_edges_numbers[0][i]].y;
				centroid_x2 += vertice_arr[graph_edges_numbers[1][i]].x;
				centroid_y2 += vertice_arr[graph_edges_numbers[1][i]].y;
				
				//System.out.printf("vertice_arr[graph_edges_numbers[" + 0 + "][" + i + "]].x = " + vertice_arr[graph_edges_numbers[0][i]].x + "\n");
				//System.out.printf("vertice_arr[graph_edges_numbers[" + 0 + "][" + i + "]].y = " + vertice_arr[graph_edges_numbers[0][i]].y + "\n");
				//System.out.printf("vertice_arr[graph_edges_numbers[" + 1 + "][" + i + "]].x = " + vertice_arr[graph_edges_numbers[1][i]].x + "\n");
				//System.out.printf("vertice_arr[graph_edges_numbers[" + 1 + "][" + i + "]].y = " + vertice_arr[graph_edges_numbers[1][i]].y + "\n");				
				
				total_edge_sum++;
			}
		}
		
		//System.out.printf("centroid_x1 = " + centroid_x1 + "\n");
		//System.out.printf("centroid_y1 = " + centroid_y1 + "\n");
		//System.out.printf("centroid_x2 = " + centroid_x2 + "\n");
		//System.out.printf("centroid_y2 = " + centroid_y2 + "\n");
		//System.out.printf("total_edge_sum = " + total_edge_sum + "\n");
		
		
		
		
		centroid_x1 /= total_edge_sum;
		centroid_y1 /= total_edge_sum;
		centroid_x2 /= total_edge_sum;
		centroid_y2 /= total_edge_sum;
		
		//System.out.printf("centroid_x1 = " + centroid_x1 + "\n");
		//System.out.printf("centroid_y1 = " + centroid_y1 + "\n");
		//System.out.printf("centroid_x2 = " + centroid_x2 + "\n");
		//System.out.printf("centroid_y2 = " + centroid_y2 + "\n");
		
		arr_B[0] = centroid_x1 ;
		arr_B[1] = centroid_y1 ;
		arr_B[2] = centroid_x2 ;
		arr_B[3] = centroid_y2 ;
		
		return arr_B;
	}
	
	
	
	
	

}

