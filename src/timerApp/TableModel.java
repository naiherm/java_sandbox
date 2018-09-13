package timerApp;

import java.util.List;
import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	public final int COLUMN_CAPTION = 0;
	public final int COLUMN_START	= 1;
	public final int COLUMN_END		= 2;
	public final int COLUMN_ENABLE	= 3;
	private final String[] COLUMN_NAME = new String[] {
		"キャプション",
		"開始時刻",
		"終了時刻",
		"有効/無効",
	};

	private List<Data> data = null;
	
	/**
	 * コンストラクタ
	 * @param data テーブルに登録されるデータ
	 */
	public TableModel(List<Data> data) {
		this.data = data;
	}
	@Override
	public String getColumnName(int column) {
		return COLUMN_NAME[column];
	}
	@Override
	public int getColumnCount() {
		return COLUMN_NAME.length;
	}
	@Override
	public int getRowCount() {
		if(this.data == null) {
			return 0;
		}
		return data.size();
	}
	@Override
	public Object getValueAt(int row, int column) {
		if(data == null) {
			return null;
		}
		Data target = data.get(row);
		switch( column ) {
		case COLUMN_CAPTION:{
			return target.getCaption();
		}	//break;
		case COLUMN_START:{
			return target.getStartTime();
		}	//break;
		case COLUMN_END:{
			return target.getEndTime();
		}	//break;
		case COLUMN_ENABLE:{
			return target.isEnable();
		}	//break;
		default:{
			return null;
		}	//break;
		} 
	}
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		if(data == null) {
			System.out.println("data is empty");
			return;
		}
		
		Data target = data.get(row);
		switch( column ) {
		case COLUMN_CAPTION:{
			target.setCaption((String)aValue);
		}	break;
		case COLUMN_START:{
			target.setStartTime((String)aValue);
		}	break;
		case COLUMN_END:{
			target.setEndTime((String)aValue);
		}	break;
		case COLUMN_ENABLE:{
			target.setEnable( (boolean)aValue );
		}	break;
		default:{
			super.setValueAt(aValue, row, column);
		}	break;
		}		
	}
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Object o = getValueAt(0, columnIndex);
		if( o == null ) {
			return super.getColumnClass(columnIndex);
		}
		return o.getClass();
	}
	
}
