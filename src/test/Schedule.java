package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Schedule {

	public static Scanner input = new Scanner(System.in);
	
	private String[] filename = new String[2];
	private int sHour = 0;
	private int sMinu = 0;
	private int eHour = 0;
	private int eMinu = 0;
	private int span = 0;
	
	private List<Work> works = new ArrayList<Work>();
	
	public static void main(String[] args) {
		Schedule s = new Schedule();
		s.Run();
	}

	private void Run() 
	{
		DoSetFileName();
		DoSetBaseTime();
		DoSetLastTime();
		DoSetSpanMinu();
		CreateSchedule();
		do 
		{
			ShowSchedule();
		}while( EditSchedule() );
	}

	/**
	 * 変更、削除、追加 
	 */
	private boolean EditSchedule() 
	{
		System.out.println( "E:Edit");
		System.out.println( "D:Delete");
		System.out.println( "A:Add");
		System.out.println( "Q:Shotdown");
		String select = input.nextLine();
		
		switch( select ) 
		{
		case "e":
		case "E":	
		{
			Edit();
		}	break;
		case "d":
		case "D":
		{
			Delet();
		}	break;
		case "a":
		case "A":
		{
			Add();
		}	break;
		default:
		{
			return false;	
		}	//break;
		}
			
		return true;
	}
	/**
	 * 追加
	 */
	private void Add()
	{
		System.out.println("select file ID");
		System.out.println("0:" + filename[0]);
		System.out.println("1:" + filename[1]);
		int f = input.nextInt();
		
		System.out.println("input hour");
		int h = input.nextInt();	
		System.out.println("input minu");
		int m = input.nextInt();
		input.nextLine();
		
		works.add(new Work( h, m, filename[f]));
		works.sort( new Comp() );
	}
	/**
	 * 削除
	 */
	private void Delet() {
		System.out.println("delete work id");
		int id = input.nextInt();
		input.nextLine();

		works.remove(id);
	}
	/**
	 * 編集
	 */
	private void Edit() {
		System.out.println("edit work id");
		int id = input.nextInt();
		Work w = works.get(id);
		System.out.println();
		System.out.println( id + "\t" +
				w.hour + "\t" + 
				w.minute + "\t" +
				w.file );
		System.out.println();
		
		System.out.println("select file ID");
		System.out.println("0:" + filename[0]);
		System.out.println("1:" + filename[1]);
		int f = input.nextInt();
		
		System.out.println("input hour");
		int h = input.nextInt();
		System.out.println("input minu");
		int m = input.nextInt();
		input.nextLine();
		
		w.hour = h;
		w.minute = m;
		w.file = filename[f];
		
		works.sort( new Comp() );
	}

	/**
	 * ファイル名登録 
	 */
	private void DoSetFileName() 
	{
		System.out.println("input fileA name");
		filename[0] = input.nextLine();
		System.out.println("input fileB name");
		filename[1] = input.nextLine();
	}
	/**
	 * 初回上映時刻
	 */
	private void DoSetBaseTime() 
	{
		System.out.println("input start hour");
		sHour= input.nextInt();
		System.out.println("input start minu");
		sMinu = input.nextInt();
		input.nextLine();
	}
	/**
	 * 上映終了時刻
	 */
	private void DoSetLastTime() 
	{
		System.out.println("input end hour");
		eHour= input.nextInt();
		System.out.println("input end minu");
		eMinu = input.nextInt();
		input.nextLine();
	}
	/**
	 * 何分置きにするか 
	 */
	private void DoSetSpanMinu() 
	{
		System.out.println("input span (per min)");
		span = input.nextInt();
		input.nextLine();
	}
	/**
	 * スケジュール生成
	 */
	private void CreateSchedule() 
	{
		int total =  sHour * 60 + sMinu;
		int end = eHour * 60 + eMinu;
		int id = 0;
		do
		{
			int h = total / 60;
			int m = total % 60;
			
			works.add( new Work(h, m, filename[id%2]) );
			
			total += span;
			id++;
			
		}while(total < end); 
	}
	/**
	 * 結果
	 */
	private void ShowSchedule()
	{
		int i = 0;
		for( Work w : works) 
		{
			System.out.println( i + "\t" +
								w.hour + "\t" + 
								w.minute + "\t" +
								w.file );
			i++;
			
		}
	}

}

