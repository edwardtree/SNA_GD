package Count_crossing;

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
		
		
		File f = new File("C:/Users/firestation/workspace/Graph drawing/src/Count_crossing/10_vertices_9_edges.txt");
		
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
			
			//lat = 0;
			//lng = (6.28 * i) / n;
			
			/*
			lat_radian = Math.toRadians(lat);
 			lng_radian = Math.toRadians(lng);
			*/
 			//lat_radian = lat;
 			//lng_radian = lng;
			
 			/*
 			x = R * Math.cos(lat_radian) * Math.cos(lng_radian);
 			y = R * Math.cos(lat_radian) * Math.sin(lng_radian);
 			z = 0;
 			*/
 			//x = R *Math.cos(lng_radian);
 			//y = R *Math.sin(lng_radian);
 			//z = 0;
 			
 			x = input.nextDouble();
 			y = input.nextDouble();
 			z = input.nextDouble();
 			
 			vertice_arr[i] = new vertice3D(x, y, z);
 			
 			System.out.printf("vertice_arr[%d].x = %f	vertice_arr[%d] = %f\n",i ,vertice_arr[i].x ,i ,vertice_arr[i].y );
			 
 			
		}
		
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
				
		for (i = 0 ; i < m ; i++ ) {

			a = input.nextInt();
			b = input.nextInt();
			// System.out.printf("edges.a = %d edges.b = %d \n", a, b);
			
			graph[a][b] = 1;
			graph[b][a] = 1;
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


		int [][] ordering = new int[2][m]; 
		
		for( i=0 ; i<m ; i++ )
		{
			ordering[0][i] = graph_edges_numbers[0][i];
			ordering[1][i] = graph_edges_numbers[1][i];
			
			System.out.printf("i = %d 	%d	%d\n",i,ordering[0][i],ordering[1][i]);
		}
		
		int [] arr_A = new int[m];
		int [] arr_B = new int[m];
		
		 // store return x and y.
		double [][] Crossing_vertices = new double[2][10000];
		
		for( i=0 ; i<10000 ; i++ )
		{
			Crossing_vertices[0][i] = -2;
			Crossing_vertices[1][i] = -2;
		}
		
		int sum = 0, Crossing_vertices_tmp=0,tmp = 0;
		
		double [] Return_cossing_vertices_arr = new double[2];
		
		int edge_A_vertex_A = 0, edge_A_vertex_B = 0, edge_B_vertex_A = 0, edge_B_vertex_B = 0;
		
		for ( i = 0 ;  i< m ; i++)
		{
			edge_A_vertex_A = graph_edges_numbers[0][i];
			edge_A_vertex_B = graph_edges_numbers[1][i];
			
			for( j=i+1 ; j<m ; j++)
			{
				if(graph_edges_numbers[0][j] != graph_edges_numbers[0][i])
				{
					edge_B_vertex_A = graph_edges_numbers[0][j];
					edge_B_vertex_B = graph_edges_numbers[1][j];
					
					Return_cossing_vertices_arr = Geometry_edgeCrossing(edge_A_vertex_A, edge_A_vertex_B, edge_B_vertex_A, edge_B_vertex_B, vertice_arr);
					
					Return_cossing_vertices_arr[0] = (int)(Math.floor(Return_cossing_vertices_arr[0]*10))/10.0 ;
					Return_cossing_vertices_arr[1] = (int)(Math.floor(Return_cossing_vertices_arr[1]*10))/10.0 ;
					
					
					for( int k =0 ; k < Crossing_vertices[0].length ; k++)
					{
						if(Return_cossing_vertices_arr[0] == Crossing_vertices[0][k] && Return_cossing_vertices_arr[1] == Crossing_vertices[1][k])
						{
							tmp =1;
						}
					}
					if( tmp == 0)
					{
						 Crossing_vertices[0][Crossing_vertices_tmp] = Return_cossing_vertices_arr[0];
						 Crossing_vertices[1][Crossing_vertices_tmp] = Return_cossing_vertices_arr[1];
						 System.out.printf("%f %f\n",Crossing_vertices[0][Crossing_vertices_tmp],Crossing_vertices[1][Crossing_vertices_tmp]);
						 
						 System.out.printf("coordinat : edge_A_vertex_A.x = %f	edge_A_vertex_A.y = %f edge_A_vertex_B.x = %f	edge_A_vertex_B.y = %f\n",vertice_arr[edge_A_vertex_A].x ,vertice_arr[edge_A_vertex_A].y ,vertice_arr[edge_A_vertex_B].x ,vertice_arr[edge_A_vertex_B].y);
						 System.out.printf("coordinat : edge_B_vertex_A.x = %f	edge_B_vertex_A.y = %f edge_B_vertex_B.x = %f	edge_B_vertex_B.y = %f\n\n",vertice_arr[edge_B_vertex_A].x ,vertice_arr[edge_B_vertex_A].y ,vertice_arr[edge_B_vertex_B].x ,vertice_arr[edge_B_vertex_B].y);
						 
						 
						 Crossing_vertices_tmp++;
					}
						
					tmp = 0;
				}
			}
			
			
		}
		
		System.out.printf("%d\n",Crossing_vertices_tmp);
		
	}
	
	static double[] Geometry_edgeCrossing(int edge_A_vertex_A ,int edge_A_vertex_B ,int edge_B_vertex_A ,int edge_B_vertex_B , vertice3D[] vertice_arr)
	{
		int i,j;
		
		double A_x1=0,A_x2=0,A_y1=0,A_y2=0,B_x1=0,B_x2=0,B_y1=0,B_y2=0;
		double A_x_max = 0 , A_x_min = 0 , A_y_max = 0 , A_y_min = 0 , B_x_max = 0 , B_x_min = 0 , B_y_max = 0 , B_y_min = 0;
		
		
				A_x1 = vertice_arr[edge_A_vertex_A].x;
				A_y1 = vertice_arr[edge_A_vertex_A].y;
				A_x2 = vertice_arr[edge_A_vertex_B].x;
				A_y2 = vertice_arr[edge_A_vertex_B].y;
				//System.out.printf("A_x1 = %f	A_y1 = %f	A_x2 = %f 	A_y2 = %f\n",A_x1,A_y1,A_x2,A_y2);

				
				B_x1 = vertice_arr[edge_B_vertex_A].x;
				B_y1 = vertice_arr[edge_B_vertex_A].y;
				B_x2 = vertice_arr[edge_B_vertex_B].x;
				B_y2 = vertice_arr[edge_B_vertex_B].y;

				
				//System.out.printf("B_x1 = %f	B_y1 = %f	B_x2 = %f 	B_y2 = %f\n",B_x1,B_y1,B_x2,B_y2);
		
		if( A_x1 > A_x2 )
		{
			A_x_max = A_x1;
			A_x_min = A_x2;
		}
		else
		{
			A_x_max = A_x2;
			A_x_min = A_x1;
		}
		
		if( A_y1 > A_y2 )
		{
			A_y_max = A_y1;
			A_y_min = A_y2;
		}
		else
		{
			A_y_max = A_y2;
			A_y_min = A_y1;
		}
		if( B_x1 > B_x2 )
		{
			B_x_max = B_x1;
			B_x_min = B_x2;
		}
		else
		{
			B_x_max = B_x2;
			B_x_min = B_x1;
		}
		if( B_y1 > B_y2 )
		{
			B_y_max = B_y1;
			B_y_min = B_y2;
		}
		else
		{
			B_y_max = B_y2;
			B_y_min = B_y1;
		}
				
		if( (A_x2 == B_x1 && A_y2 == B_y1) || (A_x2 == B_x2 && A_y2 == B_y2))
		{
			double [] final_arr = {-2,-2};
			//System.out.printf("Not in circular X = %f	Y = %f\n\n",X,Y);
			//System.out.printf("\n\n\n");
			return final_arr;
		}
		
		
		
		
		double delta_A_x = A_x1 - A_x2 ,delta_A_y = A_y1 - A_y2, delta_B_x = B_x1 - B_x2, delta_B_y = delta_B_y = B_y1 - B_y2;
		
		double A_a = dev(delta_A_y,delta_A_x), B_a = dev(delta_B_y,delta_B_x);
		
		//System.out.printf("S = %f	T = %f\n",A_a,B_a);
		
		double A_b=0,B_b=0,X,Y;
		
		A_b = A_y1 - A_a * A_x1;
		B_b = B_y1 - B_a * B_x1;
		
		X = dev((B_b-A_b),(A_a-B_a));
		
		Y = A_a * X + A_b;		
	
		if( ( X < A_x_max && X > A_x_min) && ( Y < A_y_max && Y > A_y_min) && ( X < B_x_max && X > B_x_min) && ( Y < B_y_max && Y > B_y_min) )
		{
			 System.out.printf("\nGeometry_edgeCrossing : coordinat : edge_A_vertex_A.x = %f	edge_A_vertex_A.y = %f edge_A_vertex_B.x = %f	edge_A_vertex_B.y = %f\n",vertice_arr[edge_A_vertex_A].x ,vertice_arr[edge_A_vertex_A].y ,vertice_arr[edge_A_vertex_B].x ,vertice_arr[edge_A_vertex_B].y);
			 System.out.printf("Geometry_edgeCrossing : coordinat : edge_B_vertex_A.x = %f	edge_B_vertex_A.y = %f edge_B_vertex_B.x = %f	edge_B_vertex_B.y = %f\n",vertice_arr[edge_B_vertex_A].x ,vertice_arr[edge_B_vertex_A].y ,vertice_arr[edge_B_vertex_B].x ,vertice_arr[edge_B_vertex_B].y);
			 System.out.printf("Geometry_edgeCrossing : X = %f Y = %f\n",X,Y);
			 
			System.out.printf("edge_A_vertex_A = %d	edge_A_vertex_B = %d edge_B_vertex_A = %d	edge_B_vertex_B = %d\n",edge_A_vertex_A ,edge_A_vertex_B ,edge_B_vertex_A ,edge_B_vertex_B);
			
			
			
			double [] final_arr = {X,Y};
			//System.out.printf("In circular  X = %f	Y = %f\n",X,Y);
			//System.out.printf("\n\n\n");
			return final_arr;
		}
		else
		{
			double [] final_arr = {-2,-2};
			//System.out.printf("Not in circular X = %f	Y = %f\n\n",X,Y);
			//System.out.printf("\n\n\n");
			return final_arr;
		}
	}
	
	
	static int EdgeCrossing(int m, int [] arr_A ,int [] arr_B, int [][] ordering)
	{
		int i,j;
		for( i=0 ; i<m ; i++ )
		{
			if( arr_A[i] == 1)
			{
				for( j=0 ; j<m ; j++ )
				{
					if( arr_B[j] == 1)
					{
						//System.out.printf("i = " + i + "	j = " + j +"\n\n");
						if(ordering[0][i] == ordering[0][j] || ordering[1][i] == ordering[1][j]||ordering[0][i] == ordering[1][j]||ordering[1][i] == ordering[0][j])
						{
							return 0;
						}						
						if( ordering[0][i] < ordering[0][j] && ordering[0][j] < ordering[1][i] && ordering[1][i]< ordering[1][j]) 
						{
							//System.out.printf("i = " + i + "j = " + j +"\n");
							return 1;
						}
						if( ordering[0][j] < ordering[0][i] && ordering[0][i] < ordering[1][j] && ordering[1][j]< ordering[1][i])
						{
							//System.out.printf("i = " + i + "j = " + j +"\n");
							return 1;
						}
					}
					
				}
			}
		}
		return 0;
	}

	static double dev( double a , double b )
	{
		if(b==0)
			return a;
		else if( a<0 && b>0)
		{
			return (-1)*((a*(-1))/b);
		}
		else if( a>0 && b<0)
		{
			return (-1)*(a/((-1)*b));
		}
		else if( a<0 && b<0)
		{
			return (-1)*((a*(-1))/(b*(-1)));
		}
		else
			return a/b;
		
	}

	
}

