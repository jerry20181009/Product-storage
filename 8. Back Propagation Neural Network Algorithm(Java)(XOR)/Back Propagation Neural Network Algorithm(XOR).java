package NN;
import java.io.FileReader;
import java.io.IOException;
public class BPNHW1 {
	public static void main(String [] args) throws IOException {
		
		double XkDk [][]= new double [4][5];        // �u�Ȫ�
		double ooldWih[]=  new double [5];          // �W�@�����v��
		double oldWih[]=  new double [5]; 	        // �������v��
		double newWih[]=  new double [5]; 		    // �s���v��    
		double ooldWho[]=  new double [3];          // �W�@�����v��
		double oldWho[]=  new double [3];           // �������v��
		double newWho[]=  new double [3];           // �s���v��  
		double initialwih[]=  new double [3];
		double oldinitialwih[]=  new double [3];
		double ooldinitialwih[]=  new double [3];
		double initialwho[]=  new double [3];
		double oldinitialwho[]=  new double [3];
		double ooldinitialwho[]=  new double [3];
		int count =0;
		
		
        double avgerror = 10 ;
        double learningrate =0.05;
        double momentum =0;
        double error[]=  new double [5];            //�@��pattern���|��error���O�x�s
	        
		XkDk [1][1]=0;            // ��l��XkDk�����
		XkDk [1][2]=0;            // �Ĥ@�C��X1����  �ĤG�C��X2����   �ĤT�C��D����
		XkDk [1][3]=1;            // �Ĥ@�欰�Ĥ@��input �̦�����
		XkDk [1][4]=1;		
		XkDk [2][1]=0;
		XkDk [2][2]=1;
		XkDk [2][3]=0;
		XkDk [2][4]=1;		
		XkDk [3][1]=0;
		XkDk [3][2]=1;
		XkDk [3][3]=1;
		XkDk [3][4]=0;
		
		
		
		/*for( int i=1 ; i<=3 ; i++) {                      //��������XKDK�x�}�O�_�����T
			for( int j=1 ; j<=4 ; j++)
				{System.out.print(XkDk [i][j]);}	
			  	System.out.println();	
		}*/				
		       
			java.util.Random r = new java.util.Random();
			for( int j=1 ; j<=4 ; j++) {                   //�N�Ҧ���Wih�Ȱ���l�Ƭ�0
				ooldWih[j]=0;
				oldWih[j]=r.nextDouble();
				newWih[j]=0;						
			}
			
			for( int j=1 ; j<=2 ; j++) {                   //�N�Ҧ���Who�Ȱ���l�Ƭ�0	
				ooldWho[j]=0;
				oldWho[j]=r.nextDouble();
				newWho[j]=0;
				initialwih[j]=r.nextDouble();
				oldinitialwih[j]=0;
				ooldinitialwih[j]=0;
				initialwho[j]=r.nextDouble();
				oldinitialwho[j]=0;
				ooldinitialwho[j]=0;
			}
			
			
		int round = 0;							
		do {                                              // �����@���~��error�i�H���B��
			
			System.out.println("�����|�N����{" + count + "}��")  ;
			System.out.println("avgERROR= "+ avgerror);
			System.out.println("�ҫ���  Xor ���w���Ȩ̧Ǭ�:");	
			
			round++;
			double Z1[]=  new double [5];
			double Z2[]=  new double [5];
			double Y1[]=  new double [5];
			double Zsig1[]=  new double [5];
			double Zsig2[]=  new double [5];
			double Ysig1[]=  new double [5];
			double corehj[]=  new double [5];
			double coreih1[]=  new double [5];  
			double coreih2[]=  new double [5];
			double XkDkinitial[]=  new double [5];
			
			for( int j=1 ; j<=4 ; j++) {                   //�N�Ҧ��������g�Ȱ���l�Ƭ�0
				Z1[j]=0;
				Z2[j]=0;
				Y1[j]=0;	
				Zsig1[j]=0;	
				Zsig2[j]=0;	
				Ysig1[j]=0;	
				corehj[j]=0;
				coreih1[j]=0;
				coreih2[j]=0;
				XkDkinitial[j]=1;
			}	
	
			double totalerror=0;
			
		for(int i=1 ; i<=4 ; i++) {                       // �}�l�B��C�@�������g����
			
			Z1[i]= ((XkDk[1][i]*oldWih[1])+(XkDk[2][i]*oldWih[2]))+XkDkinitial[i]*initialwih[1];
			Z2[i]= ((XkDk[1][i]*oldWih[3])+(XkDk[2][i]*oldWih[4]))+XkDkinitial[i]*initialwih[2];
			Zsig1[i]=  1 / (1 + Math.exp(-Z1[i]));
			Zsig2[i] = 1 / (1 + Math.exp(-Z2[i]));		
			Y1[i] = ((Zsig1[i]*oldWho[1])+(Zsig2[i]*oldWho[2]))+XkDkinitial[i]*initialwho[1];
			//System.out.println(XkDk[3][i]);	
			//System.out.println(Y1[i]);
			Ysig1[i]=  1 / (1 + Math.exp(-Y1[i]));
		
			error[i]= (XkDk[3][i]-Ysig1[i])*(XkDk[3][i]-Ysig1[i])/2;
			//System.out.println(XkDk[3][i]);	
			//System.out.println(error[i]);	
			
			corehj[i]=(XkDk[3][i]-Ysig1[i])*(1-Ysig1[i])*Ysig1[i];     // ��X�̥~�h���ץ���
			
			newWho[1]=oldWho[1]+(corehj[i]*learningrate*Zsig1[i])+momentum*ooldWho[1];     // �­� �[�W�ץ��ȵ���s��
			newWho[2]=oldWho[2]+(corehj[i]*learningrate*Zsig2[i])+momentum*ooldWho[2];	
			initialwho[1]=oldinitialwho[1]+(corehj[i]*learningrate*XkDkinitial[i])+momentum*ooldinitialwho[1];
			
			
			coreih1[i]=corehj[i]*oldWho[1]*Zsig1[i]*(1-Zsig1[i]);    //��X��h���h���ץ���
			coreih2[i]=corehj[i]*oldWho[2]*Zsig2[i]*(1-Zsig2[i]);	
			
			newWih[1]=oldWih[1]+(coreih1[i]*learningrate*XkDk[1][i])+momentum*ooldWih[1];            //�­ȥ[�W�ץ��ȵ���s��
			newWih[2]=oldWih[2]+(coreih1[i]*learningrate*XkDk[2][i])+momentum*ooldWih[2];
			newWih[3]=oldWih[3]+(coreih2[i]*learningrate*XkDk[1][i])+momentum*ooldWih[3];	
			newWih[4]=oldWih[4]+(coreih2[i]*learningrate*XkDk[2][i])+momentum*ooldWih[4];
			initialwih[1]=oldinitialwih[1]+(coreih2[i]*learningrate*XkDkinitial[i])+momentum*oldinitialwih[1];
			initialwih[2]=oldinitialwih[2]+(coreih2[i]*learningrate*XkDkinitial[i])+momentum*oldinitialwih[2];
			
			
			ooldWho[1]=oldWho[1];                        //�N�ª��ȩ�J�w�s
			ooldWho[2]=oldWho[2];
			ooldWih[1]=oldWih[1];                 
			ooldWih[2]=oldWih[2]; 
			ooldWih[3]=oldWih[3]; 
			ooldWih[4]=oldWih[4]; 
			ooldinitialwih[1]=oldinitialwih[1];
			ooldinitialwih[2]=oldinitialwih[2];
			ooldinitialwho[1]=oldinitialwho[1];
			
			
			
			
			oldWho[1]=newWho[1];                          //�~�h���v���ץ�
			oldWho[2]=newWho[2];			
			oldWih[1]=newWih[1];                          //���h���v���ץ�
			oldWih[2]=newWih[2];
			oldWih[3]=newWih[3];
			oldWih[4]=newWih[4];
			oldinitialwih[1]=initialwih[1];
			oldinitialwih[2]=initialwih[2];
			oldinitialwho[1]=initialwho[1];
			
			
	
			System.out.println(Ysig1[i]);	
			totalerror=totalerror+error[i];			
		}
		System.out.println("-------------------------------------------------------");		
		avgerror = totalerror/4;

		totalerror=0;	
		count++;
		
	
		//System.out.println(avgerror);
		
		
		}while(avgerror>0.0001);
		
		System.out.println("���epoch�`���Ƭ�"+ round +"����");
			
			
			
			
	
			
			
			
			
	}		

}
