package model;

public class Task {

	private String category;
	private String caption;
	private String discription;
	private int priority;
	private int progress;
	
	/**
	 * 個別タスク記録用 コンストラクタ
	 * @param category 大カテゴリ
	 * @param caption 見出し
	 * @param discription 詳細
	 * @param priority 優先度
	 * @param progress 進捗度
	 */
	public Task(String category, String caption, String discription, int priority, int progress) {
		this.category = category;
		this.caption = caption;
		this.discription = discription;
		this.priority = priority;
		this.progress = progress;
	}

	public String getCategory() {
		return this.category;
	}
	
	public String getCaption() {
		return this.caption;
	}

	public String getDiscription() {
		return this.discription;
	}

	public int getPriority() {
		return this.priority;
	}

	public int getProgress() {
		return this.progress;
	}
	
}
