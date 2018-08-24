package model;

public class TaskCategory {
	private int id;
	private String caption;
	
	public TaskCategory(int id, String caption) {
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
