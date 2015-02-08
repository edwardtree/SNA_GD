package Count_graph_total_length;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


/*
 * final length array [100 12 100 2 32]
 * edges [{1 0 0}{1 0 0}{1 1 0}]
 * Some edges set and length are the same.
 * 
 */




class Edges_most_decreasing_length {
	public double length; // record  length.
	public int [] edges;
	

	public Edges_most_decreasing_length(double input_length , int m) {

		length = input_length;
		
		edges = new int [m];
		
		for( int i = 0 ; i<m ; i++)
		{
			/*
			 * Look at ordering array.
			 * every edges index i ordering are the same at  ordering[][].
			 * If the total length decrease than update edges[].
			 */
			
			edges[i] = 0; 
		
		}

	}
}








public class AgglomerativeBundling {

		
		int i, j, k;

		double [] final_edges_length;
		
		Edges_most_decreasing_length Edges_min_length;
		
		
	
	int[][] AgglomerativeBundling_edges(int n, int m,int [][] graph ,int [][] Edges_set, vertice3D[] vertice_arr, int [][] ordering)
	{
		
		final_edges_length = new double[m];
		
		Edges_min_length = new Edges_most_decreasing_length(100, m); // To record the min bundling length.
		
		double [] Every_edges_length = new double[m];
		
		int [][] graph_edges_numbers = new int[2][m]; 
		
		for( i=0 ; i<m ; i++) 
			Every_edges_length[i] = 0;
		
		for( i=0 ; i<n ; i++)
			System.out.println("vertice_arr [" + i +  "].x = " + vertice_arr[i].x + "\n");
		
		int tmp1 = 0;
		
		for (i = 0 ; i < n ; i++ )
		{
			for( j=0 ; j<n ; j++)
			{
				if(graph[i][j] == 1  && i!=j && i<j)
				{
					graph_edges_numbers[0][tmp1] = i;
					graph_edges_numbers[1][tmp1] = j;				
					tmp1++;
				}
			}
		}
		
		/*
		for (i = 0 ; i < m ; i++ )
		{
			System.out.println("graph_edges_numbers[" + 0 +  "][" + i + "] = " +  graph_edges_numbers[0][i] + "  graph_edges_numbers[" + 1 +  "][" + i + "] = " +  graph_edges_numbers[1][i] +"\n");
		}
		*/
		
		/*
		 *  Initial the min length and edge.
		 */
		
		int Edges_min_tmp = 0 ;
		
		for( i=0 ; i<m ; i++)
		{
				Every_edges_length[i] = length(vertice_arr[graph_edges_numbers[0][i]].x-vertice_arr[graph_edges_numbers[1][i]].x, vertice_arr[graph_edges_numbers[0][i]].y - vertice_arr[graph_edges_numbers[1][i]].y);

				if( Edges_min_length.length > length(vertice_arr[graph_edges_numbers[0][i]].x-vertice_arr[graph_edges_numbers[1][i]].x, vertice_arr[graph_edges_numbers[0][i]].y - vertice_arr[graph_edges_numbers[1][i]].y))
				{
					Edges_min_length.length = length(vertice_arr[graph_edges_numbers[0][i]].x-vertice_arr[graph_edges_numbers[1][i]].x, vertice_arr[graph_edges_numbers[0][i]].y - vertice_arr[graph_edges_numbers[1][i]].y);
					
					Edges_min_tmp = i;
					//System.out.printf("Edges_min_length.length =" + Edges_min_length.length + "\n");
				}
				
				
		}
		
		for( i=0 ; i<m ; i++)
		{
			if( Edges_min_tmp == i) 
				Edges_min_length.edges[i] = 1;
			else
				Edges_min_length.edges[i] = 0;
		}
	
		
		
		
		
		//record two to compare crossing and calculate bundling length. 
		
		int [] arr_A = new int[m];
		int [] arr_B = new int[m];
		
		
		
		//ordering definition. index i is edges ordering
				
		
		double decrease_max_length = 0;
		double BundlingGain_length;
		int tmp = 0;
		
		double [] test = new double [100];
		int test_count = 0;
		
		
		
		int k = 0;
		
		double courrent_decrease_max_length = 0 , before_decrease_max_length = 0 , compare_number = 0;
		
		double [][] determin_whether_length_decrease = new double[m][m];
		
		for( i =0 ; i < m ; i++)
			for(j =0 ; j < m ; j++)
				determin_whether_length_decrease[i][j] =0;
		
		try {
		    PrintStream out = new PrintStream(new FileOutputStream("C:/Users/firestation/workspace/Graph drawing/src/Edge_bundling/test.txt"));
		
		while(compare_number != -1)
		{
			for ( int a = 0 ; a < m ; a++)
			{
				for(int aa = 0 ; aa < m ; aa++ )
				{
					arr_A[aa] = Edges_set[a][aa];
				}
				for( int b = a+1 ; b < m ; b++ )
				{
					for( int aa = 0 ; aa < m ; aa++)
					{
						arr_B[aa] = Edges_set[b][aa];
					}
					BundlingGain_length = BundlingGain(m, n, arr_A , arr_B ,vertice_arr,graph_edges_numbers,ordering); 
					
					tmp++;
					//System.out.printf("BundlingGain_length = " + BundlingGain_length + "\ntmp = " + tmp +"\n");
				
					if( BundlingGain_length > 0 && BundlingGain_length > determin_whether_length_decrease[a][b] && decrease_max_length < BundlingGain_length)
					{

						decrease_max_length = BundlingGain_length;
					
						test[test_count] = decrease_max_length;
						test_count++;
					
						System.out.printf("decrease_max_length = " + decrease_max_length + " tmp = " +tmp +"\n\n");
						System.out.printf("a = " + a + " b = " + b +"\n\n");
					
							
						for( i=0 ; i<m ;i++)
						{
							Edges_min_length.edges[i] = arr_A[i] | arr_B[i];
						}
						courrent_decrease_max_length = decrease_max_length;
					}
				}
			}
			
			if( decrease_max_length == 0) compare_number = -1 ;
			
			for(  int q = 0 ; q < m; q++ )
			{
				if( Edges_min_length.edges[q] == 1)
				{
					for(int w=0 ; w <m ; w++)
					{
						Edges_set[q][w] = Edges_min_length.edges[w];
						if( Edges_min_length.edges[w] == 1 ) determin_whether_length_decrease[q][w] = courrent_decrease_max_length;
					}
				}			
			}
			
			
			
			before_decrease_max_length = courrent_decrease_max_length ; 
			//k++;
			for( i=0 ; i<test_count ; i++)
			{
		    	out.println("test[" + i +  "] = " + test[i] +"\n");
		    	if( i == test_count-1)
		    		out.println("\n\n\n\n");
			}
		    test_count = 0;
			decrease_max_length = 0;
			System.out.printf("k = " + k +"\n\n");
		}
		
	    out.close();

	  } catch (FileNotFoundException e) {
	    e.printStackTrace();
	  }
		
		
		try {
		    PrintStream out = new PrintStream(new FileOutputStream("C:/Users/firestation/workspace/Graph drawing/src/Edge_bundling/test1.txt"));

		    for( i=0 ; i<m ; i++)
		    	for( j=0 ; j<m ;j++)
		    	{
		    		out.print(Edges_set[i][j] );		    
		    		if( j == m-1 )out.println("\n" );
		    	}
		    out.close();

		  } catch (FileNotFoundException e) {
		    e.printStackTrace();
		  }
		
	
		
		
		
		
		return Edges_set;
	}
	
	
	double length(double a,double b)
	{
		return Math.sqrt(a*a+b*b);	
	}
	
	
	double BundlingGain(int m,int n, int [] arr_A , int [] arr_B ,vertice3D [] vertice_arr , int [][] graph_edges_numbers , int [][] ordering )
	{
		if(EdgeCrossing(m, arr_A , arr_B,ordering) == 1)
			return -1;
		
				
		int [] arr_C = new int [m];
				
		for( i=0 ; i<m ; i++)
		{
			arr_C[i] = arr_A[i] | arr_B[i];
		}
		
		double arr_A_length = Ink(m, n, arr_A ,vertice_arr,graph_edges_numbers);
		double arr_B_length = Ink(m, n, arr_B ,vertice_arr,graph_edges_numbers);
		double arr_C_length = Ink(m, n, arr_C ,vertice_arr,graph_edges_numbers);
		
		if( arr_A_length + arr_B_length > arr_C_length)
			return Ink(m, n, arr_A ,vertice_arr,graph_edges_numbers) + Ink(m, n, arr_B ,vertice_arr,graph_edges_numbers) - Ink(m, n, arr_C ,vertice_arr,graph_edges_numbers);
		else
			return -2;

	}
	
	
	int EdgeCrossing(int m, int [] arr_A ,int [] arr_B, int [][] ordering)
	{
		
		for( i=0 ; i<m ; i++ )
		{
			if( arr_A[i] == 1)
			{
				for( j=0 ; j<m ; j++ )
				{
					if( arr_B[j] == 1)
					{
						if(ordering[0][i] == ordering[0][j] || ordering[1][i] == ordering[1][j]||ordering[0][i] == ordering[1][j]||ordering[1][i] == ordering[0][j])
						{
							return 1;
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
	
	
	
	double Ink(int m,int n, int [] arr_A , vertice3D [] vertice_arr , int [][] graph_edges_numbers)
	{
		
		int tmp = 0 , index = 0;
		
		for( i=0 ; i<m ; i++)
		{
			if( arr_A[i] == 1)
			{
				tmp++;
				index = i; // goal is that if tmp = 1 means arr_A only one edge
			}
		}
		
		//System.out.printf("tmp = " + tmp + " index = " + index + "\n");
		//System.out.printf("length = " + length(vertice_arr[graph_edges_numbers[0][i]].x-vertice_arr[graph_edges_numbers[1][i]].x, vertice_arr[graph_edges_numbers[0][i]].y - vertice_arr[graph_edges_numbers[1][i]].y) + "\n");
		
		
		if( tmp == 1) 
		return length(vertice_arr[graph_edges_numbers[0][index]].x-vertice_arr[graph_edges_numbers[1][index]].x, vertice_arr[graph_edges_numbers[0][index]].y - vertice_arr[graph_edges_numbers[1][index]].y);
		else
		return  Algo_bundling_edge_length(m,n,arr_A , vertice_arr ,graph_edges_numbers);

		
	}
	
	
	double Algo_bundling_edge_length(int m,int n, int [] arr_A , vertice3D [] vertice_arr , int [][] graph_edges_numbers)
	{
		
 		double centroid_x1 = 0 , centroid_y1 = 0 , centroid_x2 = 0 , centroid_y2 = 0;
		
		int total_edge_sum = 0;
		
		
		for( i =0 ; i <m ; i++)
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
		
		
		double total_length = 0;
		
		for( i =0 ; i <m ; i++)
		{
			if(arr_A[i] == 1)
			{
				total_length += length(vertice_arr[graph_edges_numbers[0][i]].x-centroid_x1, vertice_arr[graph_edges_numbers[0][i]].y - centroid_y1);
				//System.out.printf(length(vertice_arr[graph_edges_numbers[0][i]].x-centroid_x1, vertice_arr[graph_edges_numbers[0][i]].y - centroid_y1) + "\n");
				total_length += length(vertice_arr[graph_edges_numbers[1][i]].x-centroid_x2, vertice_arr[graph_edges_numbers[1][i]].y - centroid_y2);
				//System.out.printf(length(vertice_arr[graph_edges_numbers[1][i]].x-centroid_x2, vertice_arr[graph_edges_numbers[1][i]].y - centroid_y2) + "\n");		
			}
		}
		
		total_length += length(centroid_x1 - centroid_x2, centroid_y1 - centroid_y2);
	
		return total_length;
	}
}


