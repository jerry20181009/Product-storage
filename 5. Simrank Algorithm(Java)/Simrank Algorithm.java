package homework3;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class SimrankFinish {
	
	static long startTime = System.currentTimeMillis();

	static int x = 2000;

	public static void main(String[] argv) throws IOException {
		
		try {
            Thread.sleep(12);
        } catch (InterruptedException e) {
            e.printStackTrace(); 
        }
		String array[] = new String[x]; // �Ȧs�@��@��Ū���Ȫ��}�C
		int graphtable[][] = new int[x][x]; // �����Ͻװ�¦�y�i�y�X�ϧ�
		int unitgraph[][] = new int[x][x]; // �������x�}
		double newsim[][] = new double[x][x]; // ���@�ӷs�������x�}
		double oldsim[][] = new double[x][x]; // ���@���ª������x�}
		int inlineNum[][] = new int[x][x]; // ���x�s�U�I�y�J���I������
		int tempsaving[] = new int[x]; // ���F�p��node�ƶq ���s���x�}
		double finaldistance = 2147483647; // ����ۥ[�}�ڸ����Z��
		double convergenumber = 0.00005; // ���ļƦr�]�p
		int Outgoing[] = new int[x]; // �Ii�y�X�h���Ӽ�
		int Ingoing[] = new int[x]; // �Ii�y��Ӫ��Ӽ�
		int maxstep = 10000;
		double c = 0.8; // ���@�өT�w�`��

		/* Ū�ɮ� */ FileReader fr = new FileReader("C:\\Users\\jerry\\Desktop\\���p���ͪ���ƭn��Jpagerank.txt"); // �إ��x�s�w�İ�
		// BufferedReader br = new BufferedReader(fr);
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
		String delimiter = " ";
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

		for (int i = 1; i < x; i++) { // ��l�ƨC���I���i�J�I

			for (int j = 1; j < x; j++) {
				inlineNum[i][j] = 0;
				// System.out.print (graphtable[i][j]);
			}
			// System.out.println ();
		}

		/* ���X���x�} */

		for (int i = 1; i <= Nodenumber; i++) { // ���N�x�}������0
			for (int j = 1; j <= Nodenumber; j++) {

				unitgraph[i][j] = 0;
			}
		}

		for (int i = 1; i <= Nodenumber; i++) // �b�N��ٽu ��1
		{
			unitgraph[i][i] = 1;
		}

		for (int i = 1; i <= Nodenumber; i++) { // �N���x�}���� ��oldsim[][]
			for (int j = 1; j <= Nodenumber; j++) {

				oldsim[i][j] = unitgraph[i][j];
			}
		}

		/*
		 * for(int i=1; i<x ; i++) { for(int j=1; j<x ; j++) {
		 * 
		 * System.out.print(oldsim[i][j]);
		 * 
		 * } System.out.println(); }
		 */

		int Innumber = 0;
		int Outnumber = 0;

		/* ��y�X���ƶq */
		for (int i = 1; i < x; i++) {
			for (int j = 1; j < x; j++) {
				if (graphtable[i][j] == 1) {
					Outnumber++;
				}
			}
			Outgoing[i] = Outnumber;
			Outnumber = 0;
		}

		/*
		 * for (int i = 1; i < x; i++) { ����y�X�ƶq�O�_���T System.out.println (Outgoing[i]); }
		 */

		/* ��y�J���ƶq */
		int count = 0;
		for (int i = 1; i < x; i++) {
			for (int j = 1; j < x; j++) {
				if (graphtable[j][i] == 1) {

					inlineNum[i][count] = j;
					count++;
					Innumber++;
				}

			}
			Ingoing[i] = Innumber;
			Innumber = 0;
			count = 0;
		}

		/*
		 * for (int i = 1; i < x; i++) { // ����y�J�ƶq�O�_���T System.out.println (Ingoing[i]);
		 * }
		 */

		/*
		 * for(int i=0; i<x; i++) { // ���լy�J�I����楿�T��
		 * 
		 * for(int j=0; j<x ; j++) { System.out.print (inlineNum[i][j]); }
		 * System.out.println (); }
		 */

		int step = 0;
		double distance2 = 0; // ����ۥ[���Z��
		double distance6 = 0;
		double finalvalue = 0;

		while (convergenumber < finaldistance) { // �@��@���X�g�J���I ��X�y�X�ƶq ���ƾǹB��

			finaldistance = 0;
			step++;

			for (int i = 1; i <= Nodenumber; i++) { // �ѥ���k �ѤW��U �@��@��h��SIM
				for (int j = 1; j <= Nodenumber; j++) {
					if (i == j) {
						newsim[i][j] = 1;
					}
					if (i != j) {

						int countA = Ingoing[i]; // ���Ӯg�J�ƶq����
						int countB = Ingoing[j];

						if (countA == 0 || countB == 0) { // �P�_�Y�䤤�@���I�S���J�g�I �h������0
							newsim[i][j] = 0;
							continue;
						}

						double totalValue = 0; // ��g�J�I���S���s������ ( ����Sigma����)
						for (int k = 0; k <= Nodenumber; k++) {
							for (int q = 0; q <= Nodenumber; q++) {

								if (inlineNum[i][k] != 0 && inlineNum[j][q] != 0) {

									int u = inlineNum[i][k];
									int v = inlineNum[j][q];
									totalValue = totalValue + oldsim[v][u]; // totalValue��Sigma���[�`
									
									// System.out.println (totalValue);
								}
							}
						}
						int y = countA * countB;
						newsim[i][j] = (c / y) * totalValue;
						if(Double.isNaN(newsim[i][j])) {
							System.err.println("NAN!");
							System.err.println("c="+c);
							System.err.println("y="+y);
							System.err.println("totalValue="+totalValue);
						}
						totalValue = 0;
						y = 0;
					}
					// System.out.print (newsim[i][j]+"|");
				}
				// System.out.println();
			}

			if (step > maxstep)
				break;

			for (int i = 1; i <= Nodenumber; i++) { // ��X�s���P�ª�檺�Z���t�Z

				for (int j = 1; j <= Nodenumber; j++) {

					double m = newsim[i][j] - oldsim[i][j];
					double v = m * m;
					distance2 = distance2 + v;

				}

			}

			/* �N�s�����л\�³��� */ for (int i = 1; i <= Nodenumber; i++) {

				for (int j = 1; j <= Nodenumber; j++) {

					oldsim[i][j] = newsim[i][j];

				}
			}

			finaldistance = Math.sqrt(distance2); // ��X�̫�Z��
			// System.out.println(finaldistance);
			distance2 = 0;

		}

		for (int i = 1; i <= Nodenumber; i++) {
			for (int j = 1; j <= Nodenumber; j++) {
				System.out.println("Vertex" + "[ " + i + " ]" + "&" + "Vertex" + "[ " + j + " ]"
						+ "  Relation value -> " + newsim[i][j]);
				
			}
		}
		System.out.println(" �@�ϥΤF"+ step + "���|�N");
		System.out.println(" Using Time:" + (System.currentTimeMillis() - startTime) + " ms");
	}
}
