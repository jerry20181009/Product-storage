package jerry;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ViolentIBM {
	
	static long startTime = System.currentTimeMillis();
		
			int h2 =0;                                        // Used to do a combination of loop operation
			
			static int x=12000;
			
			
			public static void main(String [] argv) throws IOException 
			{				
				try 
				{
	            Thread.sleep(3000);
	        } catch (InterruptedException e) {
	            e.printStackTrace(); 
	        }    
				
				
				    String array[]= new String [50000];       
				    String arrayc[][]= new String [x][x];  
				    int arrayb[][]= new int [x][x];        
				    int Hash[]= new int [500000];               
				    HashSet<Integer>[] hset = new HashSet[50000]; 
				    HashSet<Integer>[] ppset = new HashSet[50000];
				    HashSet<Integer>[] perfectset = new HashSet[50000]; 
				    HashSet<Integer>[] inference = new HashSet[50000];			 
				    HashSet<Integer>[] inf = new HashSet[50000];
				    HashSet<Integer>[] infe = new HashSet[50000];
				    HashSet<Integer>[] totalset = new HashSet[50000]; 
				    HashSet<Integer> tset=new HashSet<Integer>(); 
				    HashSet<Integer> pset=new HashSet<Integer>();
				    int Tash[]= new int [50000];                  
				    int convert[]= new int [50000];             
				    double support = 0.25;
				    double setconfident=0.95;
				   
			
			        FileReader fr = new FileReader("C:\\Users\\jerry\\Desktop\\J2.txt");
			
			        BufferedReader br = new BufferedReader(fr);
			
	//1		        
			        int i=1;		                                         // The read data was cut into two microarrays
			        int row=0;
			        int checknumber=1;
			        String delimiter = " "; 
			        while (br.ready())                                      
			         {
			        	array = br.readLine().split(delimiter);	
			        	int teamnumber;
			        	String ss= array[0];			        	
			        	teamnumber = Integer.parseInt(ss,10);
			        				        	
			        	if(checknumber==teamnumber)
			        	 {			        		
			        		arrayc[teamnumber-1][row]=array[1];
			        		row++;			        					        		
			        	 }
			        	else
			        	{
			        		row=0;	
			        		arrayc[teamnumber-1][row]=array[1];
			        		checknumber++;
			        		row++;
			        		i++;
			        	}			        				        	
			          }
			        
			        
			        
	//2		        
			        for( int a=0 ; a<50000 ; a++)                         // Make each Hset materialized
			        {	
			        hset[a]=new HashSet();
			        totalset[a]=new HashSet();
			        infe[a]=new HashSet();
			        inference[a]=new HashSet();
			        ppset[a]=new HashSet();
			        inf[a]=new HashSet();
			        }
			        
			        
			        for( int a=0 ; a<50000 ; a++)                         // Make each Hset materialized
			        {	
		        	perfectset[a]=new HashSet();
			        
			        }
			        	
			        
			        
	//3		        
			       for( int a=0 ; a<arrayc.length ; a++)                // the data into an integer into the hset
			       {	for(int  b=0 ; b<arrayc[0].length ; b++)		        		
			        		{
			    	   
			    	             String s = arrayc[a][b];
			    	             int m;
			    	             try 
			    	             {	m = Integer.parseInt(s,10);               // The string into integer type
			    	            	hset[a].add(m);                           // Set up to make the form
			    	            	 arrayb[a][b]=m;		    	            	
			    	             }
			    	             
			    	             catch(NumberFormatException e) 
			    	             {
			    	            	 
			    	             }		    	             		        		
			        		 }
			         }
			        
			       
			       
	//4		       
			   		       		    
			    for( int a=0 ; a<Hash.length ; a++)                           // The initial value of the Hash 0 to be used to count the number of occurrences
			    {
			    	Hash[a]=0;
			    }
			    
			    		    
	//5		
		
			double mintimes = i*support;                   // i for a few columns mintimes for the least number of occurrences
			                                               
			    for( int a=0 ; a<arrayb.length ; a++)		 
			    {
			    for( int b=0 ; b<arrayb[0].length ; b++)
			    {		    	
			    	Hash[arrayb[a][b]]++;               
			    }
			    			    		  	
			    	
			    }
			    
			    
	//6		   
			    
			    for( int a=0 ; a<arrayb.length ; a++)		 
			    {
			    for( int b=0 ; b<arrayb[0].length ; b++)
			    {
			    	if( Hash[arrayb[a][b]]<mintimes)
				    {
			    		Hash[arrayb[a][b]]=0;
				    }
			    	if(Hash[arrayb[a][b]]!=0)
			    	{
			    		tset.add(arrayb[a][b]);		   		       // Calculate the coincident initial C1 
			    	    tset.remove(0); 		    			 
			    	}
			    	
			    }
			    			    		  	
			    	
			    } 
			    
			    System.out.println(tset);
			    
			    
	//7		    
			    
			    Object[] tempArray = tset.toArray();                     
			    for (int c = 0; c < tempArray.length; c++)             
			       {             		    	
			    	Tash[c] = (Integer) tempArray[c];                    	    		
			       }
			    
			      int h  =  tset.size();                                 
			      int h2 = h;
			      m=h2;
			      System.out.println(m);
			      

	//8		      
			      
	 int totalperfectset=0;
	 int k =0;
	 int  perfectsetsize=0;
	 
for (int s=1; s < m+1; s++) 	                         // Call function to do the Permutations
{
			    	  n = s;
			    	  ArrayList<Integer[]> round = two();
			    	  
			    	  for( Integer[] temp : round) 
			    	  {   
			    		  		    		 	    		  		    		  		    		 		    		  
			    		  for( Integer d : temp) 
			    		  {
			    			  int c=d-1;
			    			  totalset[k].add(Tash[c]);	                   
			    			 //System.out.print(Tash[c] + " ");
			    		  }
			    		  
			    		  //System.out.println();		    		  
			    		  k++;                                           
			    	  }
			      
			      			     		      		      		      	   		     
	//9  是否超過mintimes做驗證  過得存入perfectset
			      
			   
			      int  testtimes=0;
			      for (int JJ=0; JJ < k; JJ++)     
			      {
			    	  for (int y=0; y < i; y++) 
			    	  {
			    		ppset[y]=hset[y];
			    	    boolean hhh =	ppset[y].containsAll(totalset[JJ]); 		  
			    		if(hhh)
			    		{
			    			testtimes++;
			    			
			    		}		    
			    		
			    	   }
			    	  	
			    	  if( mintimes<=testtimes)
			    	  {
			    		  HashSet<Integer> temp1 = new HashSet<Integer>(totalset[JJ]);	
			    		 // System.out.println(temp1+ "\t " + testtimes + " @"+(perfectsetsize+totalperfectset));
			    		  perfectset[perfectsetsize+totalperfectset]= temp1 ;
			    		  convert[perfectsetsize+totalperfectset]=testtimes;   // Production frequency conversion table		    		
			    		  perfectsetsize++; 
			    		  
			    		  
			    		  
			    	   }
			    	  testtimes=0;
			    	
			    	 // System.out.println(perfectset[s]);
		    	  			    	  
			         }
			      
			      totalperfectset=totalperfectset+perfectsetsize;
			      perfectsetsize=0;
			      
			      
			      
			      for (int y=0; y < k; y++)	
			      {
			    	  totalset[y].clear();  
			      }
			      
			     
			     
			      
k=0;		

}
           			    		      
			   for (int f=0; f <totalperfectset ; f++)   
			      {   if(perfectset[f].isEmpty())
			        {
			    	  
			        }
			      
			      	  else
			      	{	  
			    	  for (int p=0; p <totalperfectset ; p++)
			    	  {
			    		if(perfectset[p].isEmpty())
			    		{	
			    		
			    		}
			    	  
			    		else
			    		{
			    	         infe[p]=perfectset[p];
			    		  boolean kk =	infe[p].containsAll(perfectset[f]);
			    		  
			    		  if(kk)
			    		  {
			    			  double confident = (double) convert[p]/convert[f];
			    			  HashSet<Integer> bigset = new HashSet<Integer>(perfectset[p]);
			    			  HashSet<Integer> smallset = new HashSet<Integer>(perfectset[f]);	    				    			  
			    			  bigset.removeAll(smallset);
			    			  if(!bigset.isEmpty())
			    			  { 
			    				  if(confident>=setconfident)
			    				  {
			    					  //System.out.println(perfectset[f] + "--->"+ bigset+ "的confident" + confident );
			    					  System.out.printf("%s--->%s的confident %.4f\n", perfectset[f], bigset, confident);
			    				  }
			    			  
			    			  }  
			    			 		    			 		    			  
			    		   }
			    		  
			    		} 
			    		  
			    	    }
			      	  }
			    	 		    	  
			      }		 
			      	   		    		       
		        
			    fr.close();
			    
			    System.out.println("Using Time:" + (System.currentTimeMillis() - startTime) + " ms");
			}
			    
														
			public static int n = 0;

			public static int m ;

					
			public static ArrayList<Integer[]> two()
			{

				Integer[] array = new Integer[n];
				return f(array, 0, 1, new ArrayList<Integer[]>());

			}

			public static ArrayList<Integer[]> f(Integer[] array, int now, int start , ArrayList<Integer[]> result)
			{

				if (now >= n)
				{
					Integer[] tempArr = Arrays.copyOf(array,array.length);
					result.add(tempArr);
					return result;
				}
				else
				{
					for (; start <= m; start++)
					{

						array[now] = start;
						if (j(array, now))
						{
							f(array, now + 1, start + 1, result);
						}

					}
					
					return result;
				}
				
				

			}

			public static boolean j(Integer[] array, int now)
			{

				for (int i = 0; i <= now; i++)
				{

					for (int j = i + 1; j <= now; j++)
					{
						if (array[i] == array[j])
						{
							return false;
						}
					}
				}
				return true;
			}
					
				
	    }                                                                                   
		




		
		
		



