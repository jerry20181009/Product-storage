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
		
		String array[] = new String[x]; // 暫存一行一行讀取值的陣列                                      
		int graphtable[][] = new int[x][x]; // 此為圖論基礎流進流出圖形
		double oldscores[] = new double[x]; // 舊的pagerank的值
		double newscores[] = new double[x]; // 新的pagerank的值
		int tempsaving[] = new int[x]; // 為了計算node數量 佔存的矩陣
		double distance1 = 100; // 起始距離設定
		double distance2 = 0; // 平方相加的距離
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

		/* 賦予表格初始值 */ 
		for (int i = 1; i < Nodenumber + 1; i++) {
			oldscores[i] = 1.0 / Nodenumber;

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

		double temp = 0;
		double finalvalue = 0;
		int step = 0;
		while (convergenumber < finaldistance) // 一欄一欄找出射入的點 抓出流出數量 做數學運算
		{
			step++;
			for (int i = 1; i <= Nodenumber; i++) // 一次更新
			{
				for (int j = 1; j <= Nodenumber; j++) {
					if (graphtable[j][i] == 1) {
						temp += oldscores[j] / Outgoing[j];
					}
				}
				finalvalue = temp * d + (1 - d) / Nodenumber; // 算出第i個點的新的值
				newscores[i] = finalvalue;
				temp = 0;
				finalvalue = 0;
			}
			if (step > maxstep)
				break;

			for (int v = 1; v <= Nodenumber; v++) // 找出新表格與舊表格的距離差距
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
		System.out.println(" 共使用了"+ step + "次疊代");
		System.out.println(" Using Time:" + (System.currentTimeMillis() - startTime) + " ms");
        
	}
}

