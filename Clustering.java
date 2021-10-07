import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Clustering {
	
	//二点間距離の計算
	static double dis(double[] x1, double [] x2){
		double sum = 0;
		for(int k=0;k<x1.length;k++){
			sum += (x1[k]-x2[k])*(x1[k]-x2[k]);
		}
		return Math.sqrt(sum);
	}
	
	static double [][] readData(String fileName, int n, int m) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("iris.data.txt"));
		
		 
		String buffer;
		double [][] elements = new double[n][m];
		int i=0;
		
		//要素をメモリに格納 and id付け
		while((buffer = br.readLine()) != null){
			String [] s= buffer.split(",");
			for(int j=0;j<m;j++){
				elements[i][j] = Double.parseDouble(s[j]);
			}
			i++;
		}
		
		br.close(); 

		
		return elements;
	}
	
	static int [] assignInitialClusterIDs(int length) {
		int[] id = new int[length];
		for(int i=0;i<length;i++)
			id[i]=i;
		
		return id;
	}
	
	static int [] findNearestPoints(double [][] elements, int [] id) {
		
		int length=elements.length;
		
		double min= Double.MAX_VALUE; 
		int k=0, l=0;

		for(int i=0;i<length;i++){
			for(int j=i+1;j<length;j++){
				double distatnce =dis(elements[i],elements[j]);
				if(min>distatnce && id[i] != id[j]){
					min = distatnce;
					k=i;
					l=j;
				}
			}
		}
		
		int [] pair= {k,l};
		
		return pair;

		
	}
	
	static void margeClusters(int [] id, int k, int l) {
		for(int m=0;m<id.length;m++){ //スワップ
			if(id[m] == l){
				id[m] = k;
			}
		}
	}
	
	//すべてのidが一致していることの確認
	static boolean isSameIDs(int [] id) {
		for(int s=1;s<id.length;s++){
			if(id[0] != id[s]){
				return  false;
			}
		}
		
		return true;

	}
	
	public static void main(String[] args) throws IOException {
		
		String fileName="iris.data.txt";
		int n=100;
		int m=4;
		
		double [][] elements =readData(fileName,n,m);
		int [] id=assignInitialClusterIDs(elements.length);
		
		
		while(!isSameIDs(id)){
			int [] pair=findNearestPoints(elements, id);
			
			margeClusters(id, id[pair[0]], id[pair[1]]);		
			System.out.println(Arrays.toString(id)); //idの表示
		}
	}
}
