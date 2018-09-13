package test;

import java.util.Scanner;

public class Distance {
	
	private int side_l;	//左端計測(実測値) 
	private int side_r;	//右端計測(実測値)
	private int slope;
	
	public final int DEPTH_WIDTH = 640;
	public final int DEPTH_HEIGHT = 480;

	private final float ROOT_3 = 1.732050807568877f;
	private int outsideDistance = 0;
	private int centerDistance = 0;
	public int TargetVolume;
	public int getTargetDistance(){
		return centerDistance;
	}
	public void setTargetDistance(int value)
	{
		centerDistance = value;
		outsideDistance = (int)(value * 2 / ROOT_3);
	}

	public Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		
		Distance d =  new Distance();
		d.init();
		d.update();
	}
	public void update() {
		
		while( true ) {
			System.out.println("point x");
			int x = input.nextInt();
			System.out.println("depth v");
			int v = input.nextInt();
			
			int result = distanceReal( x, v );
			System.out.println( "org" + v );
			System.out.println( "out" + result );
		}
		
	}
	public void init() {
//		TargetDistance	= 12700;		// 127cm
//		TargetVolume	= 500;			// 2cm
		setTargetDistance( 12700 );
		TargetVolume = 10;
		
		side_l = 12018;	//左端計測(実測値) 
	 	side_r = 12877;	//右端計測(実測値)
	 	slope = side_l - side_r;  //センサーーの設置傾斜を補正
		
	}
	public int distanceReal( int x, int input )
	{
		// 最大増減値係数は、√3根拠
		float margin = (outsideDistance - centerDistance) /2;
		int center = centerDistance;
		input -= slope;

		// 水平空間にコンバート
		if( x < DEPTH_WIDTH / 2 ){
			int left_slope = center - side_l;
			// 左
			if( x < DEPTH_WIDTH / 4 ){
				// 外
				float v = margin / (input - left_slope)  * x / DEPTH_WIDTH;
				input -= v;
			}else{
				// 中央
				float v = margin / left_slope  * ( DEPTH_WIDTH / 2 - x) / DEPTH_WIDTH; 
				input -= v;
			}
		}else{
			int right_slope = center - side_r;
			// 右
			if( x < DEPTH_WIDTH * 3 / 4 ){
				// 中央
				float v = margin /right_slope * ( x -  DEPTH_WIDTH / 2 ) / DEPTH_WIDTH;; 
				input -= v;
			}else{
				// 外
				float v = margin / right_slope * ( DEPTH_WIDTH - x ) / DEPTH_WIDTH; 
				input -= v;
			}
		}
		return input;

	}	
}
