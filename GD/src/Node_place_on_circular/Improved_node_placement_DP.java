package Node_place_on_circular;

/*
 * n is vertices number;
 * graph G(V,E);
 * vertice_arr is vertices coordination;
 * 0 is right_most_vertex;
 * 5 is left_most_vertex ;
 * ordering is :
 * 	0 to 99 . we assume closer 0 is right and closer 99 is left.
 */


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

class table {
	public double cost, cut;
	int right_vtx;
	public int [] element;
	

	public table(double cost1, double cut1, int right_vtx1 , int i , int vertice_arr_size) {
		cost = cost1;
		cut = cut1;
		right_vtx = right_vtx1;
		
		int tmp = i;
		
		element = new int[vertice_arr_size];
		
		for( int j = 0 ; j<vertice_arr_size ; j++)
		{
		element[j] = tmp%2;
		tmp = tmp/2;
		
		}
	}
}




public class Improved_node_placement_DP {
	
	int i = 0 , j = 0 , k ;
	
	int vertice_arr_size  ;
	
	int [][] left ;
	int [][] right ;
	
	int j_is_right = 0, j_is_left = 0;
	
	double j_to_v0_length , j_to_vk_length ;
	int left_index , right_index ;
	
	int[] MinCA_DP(int n, int m, int[][] graph ,vertice3D [] vertice_arr, int [] ordering_V)
	{	
    	for( j=0 ; j<ordering_V.length ; j++ )
		{
			System.out.printf("Improved_node_placement_DP	x%d = %d \n",j,ordering_V[j]);
		}
		
		
/*
 * initial left vertices and right vertices
 */
		vertice_arr_size = ordering_V.length;
		
		j_to_v0_length = 0;
		j_to_vk_length = 0;
		left_index = 0;
		right_index = 0;
		
		left = new int [2][1000] ;
		right = new int [2][1000] ;
		
		int tmp =0 , tmp1=0;
		int k_mod_tmp =0;
		
		int Before_d_i_j_sum = 0, After_d_i_j_sum = 0;
		
		
		for( tmp1=ordering_V[0] ; tmp1<ordering_V[0]+ordering_V.length ; tmp1++ )
		{
			if(tmp1>n-1) 
				i = tmp1 %n;
			else 
				i = tmp1; 
			
			for( j=0 ; j<n; j++ )
			{	
				for( k_mod_tmp=ordering_V[0] ;k_mod_tmp<ordering_V[0]+ordering_V.length;k_mod_tmp++)
				{
					if(k_mod_tmp>n-1) 
						k = k_mod_tmp %n;
					else 
						k = k_mod_tmp;
					
					if(j == k)tmp=1;					
				}
				
				
				if( graph[i][j] == 1 && i!=j && tmp == 0)
				{					
					j_to_v0_length = d_i_j(ordering_V[0],j,n);
					j_to_vk_length = d_i_j(ordering_V[ordering_V.length-1],j,n);
					System.out.printf("j_to_v0_length = %f	j_to_vk_length = %f \n",j_to_v0_length,j_to_vk_length);
					
					
					
				/*
				System.out.print("i = " + i + "\n");
				System.out.print("j = " + j + "\n");
				System.out.print("vertice_arr[j].x = " + vertice_arr[j].x + "\n");
				System.out.print("vertice_arr[ordering_V[0]].x = " + vertice_arr[ordering_V[0]].x + "\n");
				System.out.print("vertice_arr[j].y = " + vertice_arr[j].y + "\n");
				System.out.print("vertice_arr[ordering_V[0]].y = " + vertice_arr[ordering_V[0]].y + "\n");
				
				System.out.print(j_to_v0_length + "\n");
				System.out.print(j_to_vk_length + "\n\n\n");
				*/
					if( j_to_v0_length < j_to_vk_length)
					{
						left[0][left_index] = i;
						left[1][left_index] = j;
					//System.out.print("left[0][" + left_index + " ] = " + left[0][left_index] + "\n");
					//System.out.print("left[1][" + left_index + " ] = " + left[1][left_index] + "\n");
					//System.out.print(length(vertice_arr[left[0][left_index]].x-vertice_arr[left[1][left_index]].x, vertice_arr[left[0][left_index]].y - vertice_arr[left[1][left_index]].y));
					//System.out.print("\n\n");
						left_index++;
						Before_d_i_j_sum += j_to_v0_length;
					}
					else
					{
						right[0][right_index] = i;
						right[1][right_index] = j;
						right_index++;
						Before_d_i_j_sum += j_to_vk_length;
					}		
				}
				tmp=0;
			}
		}
		
		for(i = 0 ; i<left_index  ; i++)
			System.out.printf("%d	left = %d	%d \n",i,left[0][i],left[1][i]);
		
		System.out.printf("\n\n\n");
		
		for(i = 0 ; i<right_index  ; i++)
			System.out.printf("right = %d	%d \n",right[0][i],right[1][i]);
		System.out.printf("\n\n\n");
		
		for(i = 0 ; i<n  ; i++)
			for( j=0 ; j<n  ; j++)
			{
				//System.out.printf("%d",graph[i][j]);
				//if(j==n-1)System.out.printf("\n");
			}
		//System.out.printf("\n\n\n");
		
					
/*
 * initialize table
 */
		
		table [] table_arr = new table[(int) Math.pow(2,vertice_arr_size)];
		
		//System.out.print(Math.pow(2,vertice_arr_size));
		
		for( i=0 ; i<(int) Math.pow(2,vertice_arr_size) ; i++ )
		{
			table_arr[i] = new table(1000000, 0, 0 , i ,vertice_arr_size);

			//System.out.print(table_arr[i].cost + "\n");
		}
		
		
		try {
	        PrintStream out = new PrintStream(new FileOutputStream("C:/Users/firestation/workspace/Graph drawing/src/Node_place_on_circular/table_arr.txt"));
				
	        for( i=0 ; i<(int) Math.pow(2,vertice_arr_size) ; i++ )
			{

	        		out.printf("%f	%d	%f\n",table_arr[i].cost,table_arr[i].right_vtx,table_arr[i].cut);

	        	out.println("\n\n\n");
				//System.out.print(table_arr[i].cost + "\n");
			}
	        
	        
	        
        out.close();

		} catch (FileNotFoundException e) {
        e.printStackTrace();
		}
		
		
		
		
		
		
		table_arr[0].cost = 0;
		
		int table_arr_cut_tmp = 0;
		
		for( i=0 ; i<left_index ; i++)
		{
				table_arr_cut_tmp++;
			//System.out.print(table_arr_cut_tmp + "\n");
		}
		
		//System.out.printf("table_arr_cut_tmp = %d \n",table_arr_cut_tmp );
		
		table_arr[0].cut = table_arr_cut_tmp;
		//System.out.printf("table_arr[0].cut = %f \n",table_arr[0].cut );
		
/*
 * Fill table
 */
		
		int vertice_arr_sum = 0;
		double total_edges_number = 0 , new_cost = 0;
		int [] Set_S = new int[vertice_arr_size]; // == table
		int [] Set_S_not_in_Set_V = new int[vertice_arr_size]; // == table
		int [] unit_Set_S_and_vertex_j = new int[vertice_arr_size]; 
		
		int unit_Set_S_and_vertex_j_element_sum = 0;
		
		//int permutation_index = 0;
	
		int yy=0,zz=0;
		
		for( k =0 ; k<vertice_arr_size ; k++ )
		{
			//Get the Set S in the Set_V , |S| = i - 1.
			//permutation_index = permutation( vertice_arr_size , k);
			
			for( i=0 ; i<(int) Math.pow(2,vertice_arr_size) ; i++ ) //i means which Set. 
			{	
				//System.out.print((i+100000) + "\n");
				for( j=0 ; j<vertice_arr_size ; j++) 
				{
					Set_S[j] = table_arr[i].element[j];
					if( table_arr[i].element[j] == 0 ) 
						Set_S_not_in_Set_V[j] = 1;
					else
						Set_S_not_in_Set_V[j] = 0;
					vertice_arr_sum += table_arr[i].element[j];
					//if( j == vertice_arr_size-1)System.out.print(vertice_arr_sum + "\n");
				}
				
				for( j=0 ; j<vertice_arr_size ; j++) 
				{
					//System.out.printf("%d " ,Set_S[j]);
				}
				//System.out.printf("\n%d" ,vertice_arr_sum);
				//System.out.printf("\n");
				
				
				if( vertice_arr_sum == k ) 
				{	
					System.out.print("\n\n" + "k = " + (k) + ", vertice_arr_sum = " + vertice_arr_sum + "\n");
					//Set i in the S.
					
					total_edges_number = table_arr[i].cut;
					
					System.out.print("total_edges_number = " + total_edges_number + "\n");
					
					new_cost = table_arr[i].cost + total_edges_number;
					
					System.out.print("new_cost = " + new_cost + "\n");
					
					for( j=0 ; j<vertice_arr_size; j++) 
					{	
						
						if(Set_S[j] == 0) // if vertex is not in the S.
						{
							System.out.print("j = " + j + "\n");
							
							
							for( int a=0 ; a<vertice_arr_size ; a++)
							unit_Set_S_and_vertex_j[a] = Set_S[a];
							
							unit_Set_S_and_vertex_j[j] = 1;
							
							for( int a=0 ; a<vertice_arr_size ; a++)
							System.out.print("Set_S[" + a + "] = " + Set_S[a] + "\n");
							
							//for( int a=0 ; a<vertice_arr_size ; a++)
								//System.out.print("unit_Set_S_and_vertex_j[" + a + "] = " + unit_Set_S_and_vertex_j[a] + "\n");
							
							for( int a=0 ; a<vertice_arr_size ; a++)
							{
								unit_Set_S_and_vertex_j_element_sum += (unit_Set_S_and_vertex_j[a])*Math.pow(2,a);

							}
							//System.out.print("unit_Set_S_and_vertex_j_element_sum " + unit_Set_S_and_vertex_j_element_sum + "\n");
							
							//System.out.print("table_arr[" + unit_Set_S_and_vertex_j_element_sum + "].cost = " +table_arr[unit_Set_S_and_vertex_j_element_sum].cost + " new_cost = " + new_cost + "\n");
						/*
							if(Set_S[0] == 0 && Set_S[1] == 1 && Set_S[2] == 1)
								{
									double qwe = table_arr[unit_Set_S_and_vertex_j_element_sum].cost;
									//System.out.printf(" qwe= %f , new_cost = %f\n",qwe,new_cost);
								}
							*/	
							if(table_arr[unit_Set_S_and_vertex_j_element_sum].cost > new_cost)
							{
								table_arr[unit_Set_S_and_vertex_j_element_sum].cost = new_cost;
								
								System.out.print("table_arr[unit_Set_S_and_vertex_j_element_sum].cost = " + table_arr[unit_Set_S_and_vertex_j_element_sum].cost +  "\n");
								
								table_arr[unit_Set_S_and_vertex_j_element_sum].right_vtx = j;
																
								//System.out.print("table_arr[" + unit_Set_S_and_vertex_j_element_sum + "].right_vtx = " +table_arr[unit_Set_S_and_vertex_j_element_sum].right_vtx + "\n");
								
								//System.out.print("j_length(graph,vertice_arr,  0, " + j + ") = "+ j_length(graph,vertice_arr,  0,  j) + "\n");
								
								System.out.print("table_arr[unit_Set_S_and_vertex_j_element_sum].right_vtx = " + table_arr[unit_Set_S_and_vertex_j_element_sum].right_vtx +  "\n");
								
								int left_j_number = 0, right_j_number = 0;
								
								for(int b = 0; b< left_index ; b++)
								{
									if(left[0][b] == ordering_V[j])	left_j_number++;
								}
								//System.out.printf("left_j_number = %d \n",left_j_number );
								for(int b = 0; b< right_index ; b++)
								{
									if(right[0][b] == ordering_V[j])
										right_j_number++;
								}
								System.out.printf("right_j_number = %d \n",right_j_number );
								//System.out.printf("j = %d \n",j );
								table_arr[unit_Set_S_and_vertex_j_element_sum].cut = total_edges_number - left_j_number + right_j_number - Cut(n,graph,j,Set_S,ordering_V) + Cut(n,graph, j ,Set_S_not_in_Set_V,ordering_V );
								System.out.print("table_arr[unit_Set_S_and_vertex_j_element_sum].cut = " + table_arr[unit_Set_S_and_vertex_j_element_sum].cut +  "\n");
								yy=1;
								//System.out.printf("right_vtx = %d \n",table_arr[unit_Set_S_and_vertex_j_element_sum].right_vtx );
							}
						}
						if(yy==1)
						{
						//System.out.printf("table_arr[%d].right_vtx = %d \n",unit_Set_S_and_vertex_j_element_sum,table_arr[unit_Set_S_and_vertex_j_element_sum].right_vtx );
						//System.out.printf("\n");
						yy=0;
						zz++;
						//System.out.printf("zz = %d \n\n",zz);
						}
						
						
						unit_Set_S_and_vertex_j_element_sum = 0;
					}
					zz=0;
				}	
					//System.out.print("Set_S[" + S_index +  "] = " + Set_S[S_index] + "\n");
					vertice_arr_sum = 0;
					//when attain the number of S element then break;
					//if( k == 1 || k == vertice_arr_size) break;
					//if( permutation_index == i) break;
				
			}		
		}	
		
		
/*
 * Get opt ordering
 */
		
		for( i=1 ; i<(int) Math.pow(2,vertice_arr_size) ; i++ )
		{
			//System.out.print("table_arr[" + i +  "].right_vtx = " + table_arr[i].right_vtx + "\n");
		}
		
		//System.out.print("\n\n");
		
		int [] test_array = new int[ordering_V.length];
		
		int [] True_right_vtx_set = new int[ordering_V.length];
		
		for( i=0 ; i<ordering_V.length ; i++)
		{
			True_right_vtx_set[i] = 1 ;
		}
		
		k = 0;
		
		for( i=ordering_V.length-1 ; i>=0 ; i--)
		{
			for( j=0 ; j<ordering_V.length ; j++)
			{
				k += True_right_vtx_set[j] * (int) Math.pow(2,j);
			}
			//System.out.print("k = " + k + "\n");
			test_array[i] = table_arr[k].right_vtx;
			True_right_vtx_set[table_arr[k].right_vtx] = 0;
			k=0;
		}
	
		
		int [] final_order_arr = new int[ordering_V.length];
		
		for( i=0 ; i<ordering_V.length ; i++ )
		{
			final_order_arr[i] = ordering_V[test_array[i]];
			System.out.print("final_order_arr[" + i + "] = " + final_order_arr[i] +"\n");
		}
		
		
		
		
		left_index=0;
		right_index=0;
		
		int aa=0;
		
		for( tmp1=ordering_V[0] ; tmp1<ordering_V[0]+ordering_V.length ; tmp1++ )
		{
			if(tmp1>n-1) 
				i = tmp1 %n;
			else 
				i = tmp1; 
			
			for( j=0 ; j<n; j++ )
			{	
				
				while(aa<final_order_arr.length)
				{
					 k_mod_tmp=final_order_arr[aa];
						if(k_mod_tmp>n-1) 
							k = k_mod_tmp %n;
						else 
							k = k_mod_tmp;
						
						if(j == k)tmp=1;
						aa++;
				}
								
				
				if( graph[i][j] == 1 && i!=j && tmp == 0)
				{					
					j_to_v0_length = d_i_j(final_order_arr[0],j,n);
					j_to_vk_length = d_i_j(final_order_arr[final_order_arr.length-1],j,n);
					System.out.printf("j_to_v0_length = %f	j_to_vk_length = %f \n",j_to_v0_length,j_to_vk_length);
					
					
					
				/*
				System.out.print("i = " + i + "\n");
				System.out.print("j = " + j + "\n");
				System.out.print("vertice_arr[j].x = " + vertice_arr[j].x + "\n");
				System.out.print("vertice_arr[ordering_V[0]].x = " + vertice_arr[ordering_V[0]].x + "\n");
				System.out.print("vertice_arr[j].y = " + vertice_arr[j].y + "\n");
				System.out.print("vertice_arr[ordering_V[0]].y = " + vertice_arr[ordering_V[0]].y + "\n");
				
				System.out.print(j_to_v0_length + "\n");
				System.out.print(j_to_vk_length + "\n\n\n");
				*/
					if( j_to_v0_length < j_to_vk_length)
					{
						left[0][left_index] = i;
						left[1][left_index] = j;
					//System.out.print("left[0][" + left_index + " ] = " + left[0][left_index] + "\n");
					//System.out.print("left[1][" + left_index + " ] = " + left[1][left_index] + "\n");
					//System.out.print(length(vertice_arr[left[0][left_index]].x-vertice_arr[left[1][left_index]].x, vertice_arr[left[0][left_index]].y - vertice_arr[left[1][left_index]].y));
					//System.out.print("\n\n");
						left_index++;
						After_d_i_j_sum += j_to_v0_length;
					}
					else
					{
						right[0][right_index] = i;
						right[1][right_index] = j;
						right_index++;
						After_d_i_j_sum += j_to_vk_length;
					}		
				}
				tmp=0;
			}
		}
		
		
		
		
		
		
		System.out.printf("Before_d_i_j_sum	=	%d		After_d_i_j_sum = %d	\n",Before_d_i_j_sum,After_d_i_j_sum);
		
		
		for(i = 0 ; i<left_index  ; i++)
			System.out.printf("%d	left = %d	%d \n",i,left[0][i],left[1][i]);
		
		System.out.printf("\n\n\n");
		
		for(i = 0 ; i<right_index  ; i++)
			System.out.printf("right = %d	%d \n",right[0][i],right[1][i]);
		System.out.printf("\n\n\n");
		
		
		
		
		
		
		
		
	
		return final_order_arr;
	
	}
	
	int Cut(int n, int [][] graph, int i , int [] Set_S, int [] ordering_V)
	{	
		int sum = 0;

		for( int a = 0 ; a<vertice_arr_size ; a++)
		{
			if(Set_S[a] == 1 && a!=i)
			{
				//System.out.printf("ordering_V[%d] = %d	ordering_V[%d] = %d\n",i,ordering_V[i],a,ordering_V[a]);
				if(graph[ordering_V[i]][ordering_V[a]] == 1 && ordering_V[i] != ordering_V[a])sum++;
			}
		}
		//System.out.printf("sum = %d\n",sum);
		return sum;
	}
	
	
	
	int d_i_j(int i,int j,int n)
	{
		
		int min = (i - j);
		int min1 = ( j-i );
		
		if(min < 0)
		{
			while( min<0 )
			{
				min += n;
			}
		}
		
		if(min1 < 0)
		{
			while( min1<0 )
			{
				min1 += n;
			}
		}
		
		if( min > min1 )
			min = min1;

		return min;
	}
	
}

	
