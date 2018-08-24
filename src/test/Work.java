package test;

import java.util.Comparator;

public class Work {
	public int hour;
	public int minute;
	public String file;
	
	public Work( int hour, int minute, String file ) 
	{
		this.hour = hour;
		this.minute = minute;
		this.file = file;
	}
}
class Comp implements Comparator<Work>{
	public int compare(Work l, Work r) {
		return (l.hour * 60 + l.minute) - (r.hour * 60 + r.minute);
	}
}	
