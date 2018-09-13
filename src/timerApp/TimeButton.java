package timerApp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;


public class TimeButton extends JButton {

	//-----------------------------------------------
	// 定数
	public enum STATE{
		/**
		 * 時間外
		 */
		OUT,
		/**
		 * 稼働
		 */
		ON,
		/**
		 * 終わりかけ
		 */
		LIMIT,
		/**
		 * もうすぐ開始		
		 */
		READY,
		/**
		 * 休み時間
		 */
		OFF,
	}
	private final Color[] COLOR = new Color[] { 
		new Color(  64,  64,  64 ), 
		new Color( 128, 128, 255 ),
		new Color( 196, 128, 128 ),
		new Color( 128, 196, 196 ),
		new Color( 128, 255, 128 ),
	};
	private final String[] MESSAGE = new String[] {
		"OUT[時間外] ",
		"ON [授業中] ",
		"LIMIT [終了前] ",
		"READY [開始前] ",
		"OFF [休憩中] ",
	};
	//-----------------------------------------------

	/**
	 * ボタンの状態
	 */
	private STATE stat;
	
	
	/**
	 * コンストラクタ
	 */
	public TimeButton() {
		super();
		this.stat = STATE.OUT;
	}

	/**
	 * 状態を設定します
	 * @param ボタンの状態
	 */
	public void setStat( STATE stat ) {
		this.stat = stat;
	}
	/**
	 * 状態を取得します。
	 * @return
	 */
	public STATE getStat( ) {
		return this.stat;
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		
		Calendar cal = Calendar.getInstance();
		String time = MESSAGE[this.stat.ordinal()] + String.format("%tT", cal);
		
		//g.setColor(getBackground());
		g.setColor(COLOR[this.stat.ordinal()]);
		g.fillRect(0, 0, getWidth(), getHeight());

		Font f = getFont();
		FontRenderContext frc = ((Graphics2D)g).getFontRenderContext();
		Rectangle2D tRect = f.getStringBounds(time, frc);

		int _x = (int)((this.getWidth()  - tRect.getWidth() )/ 2);
		int _y = (int)((this.getHeight() )/ 2 + f.getSize() / 2 );
		
		g.setColor(getForeground());
		g.drawString( time, _x, _y);
		
	}
}
