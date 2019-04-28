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
		String array[] = new String[x]; // 暫存一行一行讀取值的陣列
		int graphtable[][] = new int[x][x]; // 此為圖論基礎流進流出圖形
		int unitgraph[][] = new int[x][x]; // 此為單位矩陣
		double newsim[][] = new double[x][x]; // 為一個新的相關矩陣
		double oldsim[][] = new double[x][x]; // 為一個舊的相關矩陣
		int inlineNum[][] = new int[x][x]; // 為儲存各點流入的點有哪些
		int tempsaving[] = new int[x]; // 為了計算node數量 佔存的矩陣
		double finaldistance = 2147483647; // 平方相加開根號的距離
		double convergenumber = 0.00005; // 收斂數字設計
		int Outgoing[] = new int[x]; // 點i流出去的個數
		int Ingoing[] = new int[x]; // 點i流近來的個數
		int maxstep = 10000;
		double c = 0.8; // 為一個固定常數

		/* 讀檔案 */ FileReader fr = new FileReader("C:\\Users\\jerry\\Desktop\\關聯產生的資料要丟入pagerank.txt"); // 建立儲存緩衝區
		// BufferedReader br = new BufferedReader(fr);
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
		String delimiter = " ";
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

		for (int i = 1; i < x; i++) { // 初始化每個點的進入點

			for (int j = 1; j < x; j++) {
				inlineNum[i][j] = 0;
				// System.out.print (graphtable[i][j]);
			}
			// System.out.println ();
		}

		/* 做出單位矩陣 */

		for (int i = 1; i <= Nodenumber; i++) { // 先將矩陣全部給0
			for (int j = 1; j <= Nodenumber; j++) {

				unitgraph[i][j] = 0;
			}
		}

		for (int i = 1; i <= Nodenumber; i++) // 在將對稱線 給1
		{
			unitgraph[i][i] = 1;
		}

		for (int i = 1; i <= Nodenumber; i++) { // 將單位矩陣的值 給oldsim[][]
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

		/* 算流出的數量 */
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
		 * for (int i = 1; i < x; i++) { 測驗流出數量是否正確 System.out.println (Outgoing[i]); }
		 */

		/* 算流入的數量 */
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
		 * for (int i = 1; i < x; i++) { // 測驗流入數量是否正確 System.out.println (Ingoing[i]);
		 * }
		 */

		/*
		 * for(int i=0; i<x; i++) { // 測試流入點的表格正確性
		 * 
		 * for(int j=0; j<x ; j++) { System.out.print (inlineNum[i][j]); }
		 * System.out.println (); }
		 */

		int step = 0;
		double distance2 = 0; // 平方相加的距離
		double distance6 = 0;
		double finalvalue = 0;

		while (convergenumber < finaldistance) { // 一欄一欄找出射入的點 抓出流出數量 做數學運算

			finaldistance = 0;
			step++;

			for (int i = 1; i <= Nodenumber; i++) { // 由左到右 由上到下 一格一格去找SIM
				for (int j = 1; j <= Nodenumber; j++) {
					if (i == j) {
						newsim[i][j] = 1;
					}
					if (i != j) {

						int countA = Ingoing[i]; // 算兩個射入數量的值
						int countB = Ingoing[j];

						if (countA == 0 || countB == 0) { // 判斷若其中一個點沒有入射點 則相關為0
							newsim[i][j] = 0;
							continue;
						}

						double totalValue = 0; // 算射入點都沒有零的相關 ( 算兩個Sigma的值)
						for (int k = 0; k <= Nodenumber; k++) {
							for (int q = 0; q <= Nodenumber; q++) {

								if (inlineNum[i][k] != 0 && inlineNum[j][q] != 0) {

									int u = inlineNum[i][k];
									int v = inlineNum[j][q];
									totalValue = totalValue + oldsim[v][u]; // totalValue為Sigma的加總
									
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

			for (int i = 1; i <= Nodenumber; i++) { // 找出新表格與舊表格的距離差距

				for (int j = 1; j <= Nodenumber; j++) {

					double m = newsim[i][j] - oldsim[i][j];
					double v = m * m;
					distance2 = distance2 + v;

				}

			}

			/* 將新報表覆蓋舊報表 */ for (int i = 1; i <= Nodenumber; i++) {

				for (int j = 1; j <= Nodenumber; j++) {

					oldsim[i][j] = newsim[i][j];

				}
			}

			finaldistance = Math.sqrt(distance2); // 算出最後距離
			// System.out.println(finaldistance);
			distance2 = 0;

		}

		for (int i = 1; i <= Nodenumber; i++) {
			for (int j = 1; j <= Nodenumber; j++) {
				System.out.println("Vertex" + "[ " + i + " ]" + "&" + "Vertex" + "[ " + j + " ]"
						+ "  Relation value -> " + newsim[i][j]);
				
			}
		}
		System.out.println(" 共使用了"+ step + "次疊代");
		System.out.println(" Using Time:" + (System.currentTimeMillis() - startTime) + " ms");
	}
}
