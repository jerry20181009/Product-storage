package homework3;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class HITSFinish {
	
	static long startTime = System.currentTimeMillis();
	static int x = 5001;

	public static void main(String[] argv) throws IOException {
		
		try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace(); 
        }
		String array[] = new String[x]; // 暫存一行一行讀取值的陣列                                      
		int graphtable[][] = new int[x][x]; // 此為圖論基礎流進流出圖形	
		double Huboldscores[]= new double [x];        // 舊的pagerank的值
	    double Hubnewscores[]= new double [x];        // 新的pagerank的值
	    double Auoldscores[]= new double [x];
	    double Aunewscores[]= new double [x];
		int tempsaving[] = new int[x]; // 為了計算node數量 佔存的矩陣
		double distance1 = 100; // 起始距離設定		
		double finaldistance = 2147483647; // 平方相加開根號的距離
		double d = 0.85; // 此為阻尼係數
		double convergenumber = 0.00005; // 收斂數字設計
		int Outgoing[] = new int[x]; // 點i流出去的個數
		int Ingoing[] = new int[x]; // 點i流近來的個數
		int maxstep = 10000;

		/* 讀檔案 */ FileReader fr = new FileReader("C:\\Users\\jerry\\Desktop\\graph_3.txt"); // 建立儲存緩衝區
		//BufferedReader br = new BufferedReader(fr);
		Scanner s = new Scanner(fr);

		int row;
		int colum;
		int Nodenumber = 0; // 節點的數量
		int a = 0; // 控制暫存空間的順序

		/* 初始暫存陣列為0 */ 
		for (int i = 0; i < x; i++) {
			tempsaving[i] = 0;
		}

		/* 讀取檔案且算出點的數量 */
		String delimiter = ",";
		while (s.hasNext()) // 當br還有下一行
		{
			array = s.nextLine().split(delimiter);

			row = Integer.parseInt(array[0], 10);
			int see = 0; // 做出一個圖論矩陣 以及有幾個node
			for (int i = 0; i < x; i++) {
				if (tempsaving[i] == row) {
					see = 1;
				}
			}
			if (see == 0) {
				tempsaving[a] = row;
				Nodenumber++;
				a++;
			}
			see = 0; //

			colum = Integer.parseInt(array[1], 10);
			int saw = 0; //
			for (int i = 0; i < x; i++) {
				if (tempsaving[i] == colum) {
					saw = 1;
				}
			}
			if (saw == 0) {
				tempsaving[a] = colum;
				Nodenumber++;
				a++;
			}
			saw = 0; //

			/* 做出圖論 */ 
			graphtable[row][colum] = 1;
		}
		
		
		/* for(int i=1; i<7 ; i++)
        { 
        	for(int j=1; j<7 ; j++)
        	{  System.out.print (graphtable[i][j]);}
        	System.out.println ();
        }*/
		
		
		

		/* 賦予表格初始值 */ 
		for (int i = 1; i < Nodenumber + 1; i++) {
			Huboldscores[i] = 1 ;
            Auoldscores[i]  = 1 ;

		}
		for(int i=1; i<7 ; i++) {
			
			//System.out.println(Huboldscores[i]+","+  Auoldscores[i]);
	
			
		}
	

		int Innumber = 0;
		int Outnumber = 0;

		/* 算流出的數量 */ 
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < x; j++) {
				if (graphtable[i][j] == 1) {
					Outnumber++;
				}
			}
			Outgoing[i] = Outnumber;
			Outnumber = 0;
		}

		

		/* 算流入的數量 */ 
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < x; j++) {
				if (graphtable[j][i] == 1) {
					Innumber++;
				}

			}
			Ingoing[i] = Innumber++;
			Innumber = 0;
		}
		
		   double tempau =0;
		   double temphub =0;
		   double distanceHub=0 ;
           double distanceAu=0 ;
		   double Autemp =0;
		   double Hubtemp =0;
		   double finalvalue =0;
		   int step =0;
		   double distance2 = 0; // 平方相加的距離
		   double distance6 = 0;
		   
		while (convergenumber < finaldistance) { // 一欄一欄找出射入的點 抓出流出數量 做數學運算
			
			finaldistance=0;
			step++;
			tempau =0;
			temphub =0;
			distanceHub=0 ;
			distanceAu=0 ;
			
			
															
			for (int i = 1; i <= Nodenumber; i++) {                // 一次更新算Au			
				for (int j = 1; j <= Nodenumber; j++) {
					if (graphtable[j][i] == 1) {
						
						Autemp = Autemp + Huboldscores[j] ;
						
					}
				}
				//System.out.println (Autemp);
				Aunewscores[i] = Autemp;			         	    		     
		          Autemp = 0; 
			}
			
			
			for(int i=1; i<=Nodenumber;i++) {                         // 算一次Hub
		    	 for(int j=1; j<=Nodenumber;j++) {	
		    	 
		    		 if(graphtable[i][j]==1) {
		    		 			    			 
		    		   Hubtemp = Hubtemp + Auoldscores[j] ;			    						    			 
		    		 }			    		 			    		 
		    	  }
		    	  //System.out.println (Hubtemp);
		          Hubnewscores[i] = Hubtemp ;			         	    		     
		          Hubtemp =0;    		     
    		                                                                           
		     }  
			
			
			for (int i = 1; i <= Nodenumber; i++) {                // 算Au的單位距離
			      tempau =  tempau +  Aunewscores[i]*Aunewscores[i] ;
				}
				   distanceAu =  Math.sqrt(tempau);
				   
				 for (int i = 1; i <= Nodenumber; i++) {                // 算Hub的單位距離
					 temphub =  temphub +  Hubnewscores[i]*Hubnewscores[i] ;
				}
				     distanceHub =  Math.sqrt(temphub);   
				     //System.out.print (step);
				     
				     
				     for(int i=1; i<=Nodenumber;i++) {                         // 將矩陣做標準化更新				    	 				    		 			    			 
				    	Hubnewscores[i] = Hubnewscores[i]/distanceHub ;	
				    	Aunewscores[i]  = Aunewscores[i] / distanceAu ;				    		 		    		 			    		 
				    	  }
			
			
			if (step > maxstep)
				break;
			
						
			
			for (int v = 1; v <= Nodenumber; v++) // 找出新表格與舊表格的距離差距
			{
				double m = Aunewscores[v] - Auoldscores[v] ; 
	    		double n = m*m;
	    		
	    		double b = Hubnewscores[v] - Huboldscores[v] ; 
	    		double c = b*b;
	    	
	    		distance2 = distance2 + n ;	
	    		distance6 = distance6 + c ;
			}
			
			
/*將新報表覆蓋舊報表*/	 for(int v=1; v<=Nodenumber;v++) {		        
		    	        Auoldscores[v] = Aunewscores[v] ;
		    	        Huboldscores[v]= Hubnewscores[v];
		              }
		      
		      finaldistance = Math.sqrt(distance2)+ Math.sqrt(distance6);	   	      // 算出最後距離  	
		      //System.out.println(finaldistance);
		      distance2=0;
		      distance6=0;
		      			
		}

		
		
		for (int i = 1; i <= Nodenumber; i++) {

			System.out.println("Vertex" + " [ " + i + " ]" + "  Hubvalue -> " + Hubnewscores[i] + "  Auvalue -> " + Aunewscores[i]);

		}
		System.out.println(" 共使用了"+ step + "次疊代");
		System.out.println(" Using Time:" + (System.currentTimeMillis() - startTime) + " ms");

	}
}

