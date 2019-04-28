package homework3;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class PagerankFinish {
	static long startTime = System.currentTimeMillis();
	static int x = 2000;
	
	

	public static void main(String[] argv) throws IOException {
		
		try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace(); 
        }
		
		String array[] = new String[x]; // �Ȧs�@��@��Ū���Ȫ��}�C                                      
		int graphtable[][] = new int[x][x]; // �����Ͻװ�¦�y�i�y�X�ϧ�
		double oldscores[] = new double[x]; // �ª�pagerank����
		double newscores[] = new double[x]; // �s��pagerank����
		int tempsaving[] = new int[x]; // ���F�p��node�ƶq ���s���x�}
		double distance1 = 100; // �_�l�Z���]�w
		double distance2 = 0; // ����ۥ[���Z��
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

		/* �ᤩ����l�� */ 
		for (int i = 1; i < Nodenumber + 1; i++) {
			oldscores[i] = 1.0 / Nodenumber;

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

		double temp = 0;
		double finalvalue = 0;
		int step = 0;
		while (convergenumber < finaldistance) // �@��@���X�g�J���I ��X�y�X�ƶq ���ƾǹB��
		{
			step++;
			for (int i = 1; i <= Nodenumber; i++) // �@����s
			{
				for (int j = 1; j <= Nodenumber; j++) {
					if (graphtable[j][i] == 1) {
						temp += oldscores[j] / Outgoing[j];
					}
				}
				finalvalue = temp * d + (1 - d) / Nodenumber; // ��X��i���I���s����
				newscores[i] = finalvalue;
				temp = 0;
				finalvalue = 0;
			}
			if (step > maxstep)
				break;

			for (int v = 1; v <= Nodenumber; v++) // ��X�s���P�ª�檺�Z���t�Z
			{
				double m = newscores[v] - oldscores[v];
				double n = m * m;

				distance2 = distance2 + n;
			}

			for (int v = 1; v <= Nodenumber; v++) {
				oldscores[v] = newscores[v];
			}

			finaldistance = Math.sqrt(distance2);

			distance2 = 0;

			
		}

		for (int i = 1; i <= Nodenumber; i++) {

			System.out.println("Vertex" + " [ " + i + " ]" + "  pagevalue -> " + newscores[i]);

		}
		System.out.println(" �@�ϥΤF"+ step + "���|�N");
		System.out.println(" Using Time:" + (System.currentTimeMillis() - startTime) + " ms");
        
	}
}

