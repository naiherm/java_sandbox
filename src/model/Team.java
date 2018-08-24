package model;

public class Team {

	private int id;
	private String caption;
	
	public Team( int id, String caption ) {
		this.id = id;
		this.caption = caption;
	}
	public int getId() {
		return this.id;
	}
	public String getCaption() {
		return this.caption;
	}
}
