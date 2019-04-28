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
		String array[] = new String[x]; // �Ȧs�@��@��Ū���Ȫ��}�C                                      
		int graphtable[][] = new int[x][x]; // �����Ͻװ�¦�y�i�y�X�ϧ�	
		double Huboldscores[]= new double [x];        // �ª�pagerank����
	    double Hubnewscores[]= new double [x];        // �s��pagerank����
	    double Auoldscores[]= new double [x];
	    double Aunewscores[]= new double [x];
		int tempsaving[] = new int[x]; // ���F�p��node�ƶq ���s���x�}
		double distance1 = 100; // �_�l�Z���]�w		
		double finaldistance = 2147483647; // ����ۥ[�}�ڸ����Z��
		double d = 0.85; // ���������Y��
		double convergenumber = 0.00005; // ���ļƦr�]�p
		int Outgoing[] = new int[x]; // �Ii�y�X�h���Ӽ�
		int Ingoing[] = new int[x]; // �Ii�y��Ӫ��Ӽ�
		int maxstep = 10000;

		/* Ū�ɮ� */ FileReader fr = new FileReader("C:\\Users\\jerry\\Desktop\\graph_3.txt"); // �إ��x�s�w�İ�
		//BufferedReader br = new BufferedReader(fr);
		Scanner s = new Scanner(fr);

		int row;
		int colum;
		int Nodenumber = 0; // �`�I���ƶq
		int a = 0; // ����Ȧs�Ŷ�������

		/* ��l�Ȧs�}�C��0 */ 
		for (int i = 0; i < x; i++) {
			tempsaving[i] = 0;
		}

		/* Ū���ɮץB��X�I���ƶq */
		String delimiter = ",";
		while (s.hasNext()) // ��br�٦��U�@��
		{
			array = s.nextLine().split(delimiter);

			row = Integer.parseInt(array[0], 10);
			int see = 0; // ���X�@�ӹϽׯx�} �H�Φ��X��node
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

			/* ���X�Ͻ� */ 
			graphtable[row][colum] = 1;
		}
		
		
		/* for(int i=1; i<7 ; i++)
        { 
        	for(int j=1; j<7 ; j++)
        	{  System.out.print (graphtable[i][j]);}
        	System.out.println ();
        }*/
		
		
		

		/* �ᤩ����l�� */ 
		for (int i = 1; i < Nodenumber + 1; i++) {
			Huboldscores[i] = 1 ;
            Auoldscores[i]  = 1 ;

		}
		for(int i=1; i<7 ; i++) {
			
			//System.out.println(Huboldscores[i]+","+  Auoldscores[i]);
	
			
		}
	

		int Innumber = 0;
		int Outnumber = 0;

		/* ��y�X���ƶq */ 
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < x; j++) {
				if (graphtable[i][j] == 1) {
					Outnumber++;
				}
			}
			Outgoing[i] = Outnumber;
			Outnumber = 0;
		}

		

		/* ��y�J���ƶq */ 
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
		   double distance2 = 0; // ����ۥ[���Z��
		   double distance6 = 0;
		   
		while (convergenumber < finaldistance) { // �@��@���X�g�J���I ��X�y�X�ƶq ���ƾǹB��
			
			finaldistance=0;
			step++;
			tempau =0;
			temphub =0;
			distanceHub=0 ;
			distanceAu=0 ;
			
			
															
			for (int i = 1; i <= Nodenumber; i++) {                // �@����s��Au			
				for (int j = 1; j <= Nodenumber; j++) {
					if (graphtable[j][i] == 1) {
						
						Autemp = Autemp + Huboldscores[j] ;
						
					}
				}
				//System.out.println (Autemp);
				Aunewscores[i] = Autemp;			         	    		     
		          Autemp = 0; 
			}
			
			
			for(int i=1; i<=Nodenumber;i++) {                         // ��@��Hub
		    	 for(int j=1; j<=Nodenumber;j++) {	
		    	 
		    		 if(graphtable[i][j]==1) {
		    		 			    			 
		    		   Hubtemp = Hubtemp + Auoldscores[j] ;			    						    			 
		    		 }			    		 			    		 
		    	  }
		    	  //System.out.println (Hubtemp);
		          Hubnewscores[i] = Hubtemp ;			         	    		     
		          Hubtemp =0;    		     
    		                                                                           
		     }  
			
			
			for (int i = 1; i <= Nodenumber; i++) {                // ��Au�����Z��
			      tempau =  tempau +  Aunewscores[i]*Aunewscores[i] ;
				}
				   distanceAu =  Math.sqrt(tempau);
				   
				 for (int i = 1; i <= Nodenumber; i++) {                // ��Hub�����Z��
					 temphub =  temphub +  Hubnewscores[i]*Hubnewscores[i] ;
				}
				     distanceHub =  Math.sqrt(temphub);   
				     //System.out.print (step);
				     
				     
				     for(int i=1; i<=Nodenumber;i++) {                         // �N�x�}���зǤƧ�s				    	 				    		 			    			 
				    	Hubnewscores[i] = Hubnewscores[i]/distanceHub ;	
				    	Aunewscores[i]  = Aunewscores[i] / distanceAu ;				    		 		    		 			    		 
				    	  }
			
			
			if (step > maxstep)
				break;
			
						
			
			for (int v = 1; v <= Nodenumber; v++) // ��X�s���P�ª�檺�Z���t�Z
			{
				double m = Aunewscores[v] - Auoldscores[v] ; 
	    		double n = m*m;
	    		
	    		double b = Hubnewscores[v] - Huboldscores[v] ; 
	    		double c = b*b;
	    	
	    		distance2 = distance2 + n ;	
	    		distance6 = distance6 + c ;
			}
			
			
/*�N�s�����л\�³���*/	 for(int v=1; v<=Nodenumber;v++) {		        
		    	        Auoldscores[v] = Aunewscores[v] ;
		    	        Huboldscores[v]= Hubnewscores[v];
		              }
		      
		      finaldistance = Math.sqrt(distance2)+ Math.sqrt(distance6);	   	      // ��X�̫�Z��  	
		      //System.out.println(finaldistance);
		      distance2=0;
		      distance6=0;
		      			
		}

		
		
		for (int i = 1; i <= Nodenumber; i++) {

			System.out.println("Vertex" + " [ " + i + " ]" + "  Hubvalue -> " + Hubnewscores[i] + "  Auvalue -> " + Aunewscores[i]);

		}
		System.out.println(" �@�ϥΤF"+ step + "���|�N");
		System.out.println(" Using Time:" + (System.currentTimeMillis() - startTime) + " ms");

	}
}

