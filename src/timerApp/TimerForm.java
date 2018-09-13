package timerApp;

import java.awt.EventQueue;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.Timer;
import javax.xml.bind.JAXB;

import timerApp.TimeButton.STATE;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.Toolkit;

public class TimerForm {

	private final String FILENAME = "setting.dat";
	private final String SOUND_FILENAME_END = "Alarm10.wav";
	private final String SOUND_FILENAME_START = "Alarm07.wav";
	
	private JFrame frame;
	private TimeButton button;
	private ActionListener lsn;
	private Timer mainLoop;
	private List<Data> setting = new ArrayList<>();
	private Data target = null;

	private Clip clipEnd;
	private Clip clipStart;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimerForm window = new TimerForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TimerForm() {
		readSetting();
		initialize();
		calcTarget();
		
		clipEnd = initSound( SOUND_FILENAME_END );
		clipStart = initSound( SOUND_FILENAME_START );
				
		lsn = new UpdateActionListener();
		mainLoop = new Timer(500, lsn);
		mainLoop.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(TimerForm.class.getResource("/timer_sample/icon.png")));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 361, 83);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		button = new TimeButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stopSounds();
			}
		});
		button.setFont(new Font("メイリオ", Font.PLAIN, 22));
		frame.getContentPane().add(button);
		
		//------------------------------------------------------------------
		// popup menu 
		//------------------------------------------------------------------
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(button, popupMenu);
		
		JMenuItem mntmSetting = new JMenuItem("Setting...");
		mntmSetting.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingDlg dlg = new SettingDlg(frame, new TableModel(setting));
				dlg.setVisible(true);
				if( dlg.isChange() ) {
					// 変更時は更新
					writeSetting();
				} 
				// 再読み込み
				readSetting();
				calcTarget();
				dlg = null;
			}
		});
		mntmSetting.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, InputEvent.ALT_MASK));
		popupMenu.add(mntmSetting);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		popupMenu.add(mntmExit);
		//------------------------------------------------------------------
	}
	/**
	 * 終了時刻用 SE鳴動
	 */
	private void playEndSE() {
		stopSounds();
		if( clipEnd != null ) {
			clipEnd.setFramePosition(0);
			clipEnd.start();		
		}
	}
	/**
	 * 開始時刻用 SE鳴動
	 */
	private void playStartSE() {
		stopSounds();
		if( clipStart != null ) {
			clipStart.setFramePosition(0);
			clipStart.start();	
		}
	}
	/**
	 * 鳴動の強制停止
	 */
	private void stopSounds() {
		if( clipEnd != null ) {
			if( clipEnd.isRunning() ) {
				clipEnd.stop();
			}			
		}
		if( clipStart != null ) {
			if( clipStart.isRunning() ) {
				clipStart.stop();
			}
		}
	}
	/**
	 * 現在の時刻から直近の未達スケジュールを検索
	 */
	private void calcTarget() {
		Calendar cal = Calendar.getInstance();
		// 初期化
		target = setting.get(0);
		button.setStat(STATE.OFF);
		
		// 次のアクティブなスケジュールを検索
		while( target.isOver(cal) || !target.isEnable() ) {
			int index = setting.indexOf(target);
			if( setting.size() - 1 > index ) {
				target = setting.get(index + 1);
			}else {
				// すべてのスケジュール時刻終了
				button.setStat(STATE.OUT);
				target = null;
				break;
			}
		}
		if( target != null ) {
			System.out.println(target.getCaption());
			System.out.println(target.getStartTime());
			System.out.println(target.getEndTime());
		}
		updateState();
	}
	/**
	 * 状態・描画更新
	 * @author Med.
	 */
	public class UpdateActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			updateState();
			updateDraw();
			if( !frame.isActive() || !frame.isVisible() ) {
				if( 	button.getStat() == STATE.LIMIT 
					|| 	button.getStat() == STATE.READY 
				) {
					// 一定条件の場合は最前面でウィンドウを表示
					frame.setExtendedState(JFrame.NORMAL);
					frame.setVisible(true);
					frame.toFront();
				}
			}
		}
	}
	/**
	 * ポップアップメニューの展開
	 * @param component 対象となるコンポーネント
	 * @param popup ポップアップメニュー
	 */
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	/**
	 * 定期的に描画更新を行います。
	 */
	public void updateDraw() {
		button.repaint();
	}
	/**
	 * 定期的に状態を更新します。
	 */
	public void updateState() {
		if( target == null) {
			return;
		}
		
		Calendar cal = Calendar.getInstance();
		switch( button.getStat() ){
		case OFF:{
			if( target.isReady( cal ) ) {
				button.setStat( STATE.READY );
				System.out.println("OFF > READY");
				frame.setTitle( "もうすぐ " + target.getCaption() + " スタート" );
			}
		} 	//break;
		case READY:{
			if( target.inTime(cal) ) {
				button.setStat(STATE.ON);
				System.out.println("READY > ON");
				frame.setTitle( target.getCaption() );
				playStartSE();
			}
		}	break;
		case ON:{
			if( target.isLimit(cal) ) {
				button.setStat(STATE.LIMIT);
				System.out.println("ON > LIMIT");
				frame.setTitle( "まもなく " + target.getCaption()+ " 終了" );
			}
		}	break;
		case LIMIT:{
			if( target.isOver(cal)){
				button.setStat(STATE.OFF);
				System.out.println("LIMIT > OFF");
				frame.setTitle("");
				
				playEndSE();
				
				calcTarget();
			}
		}	break;
		case OUT:{
			if( target.inTime(cal) ) {
				button.setStat(STATE.ON);
				System.out.println("OUT > ON");
				frame.setTitle( target.getCaption() );
				playStartSE();
			}
//			if( !target.isOver(cal)){
//				button.setStat(STATE.OFF);
//				System.out.println("OUT > OFF");
//			}
		}	break;
		default:{
			target = null;
			button.setStat(STATE.OUT);
		}	break;
		}
	}
	
	
	// #region 外部ファイル読み込み ---------------------------------
	/**
	 * 鳴音するファイル名を指定して サウンドファイルを準備済みにします。
	 * @param filename サウンドファイル名
	 * @return 読み込み済みのサウンドクリップ 
	 */
	private Clip initSound( String filename ) {
		try {
			URL url = getClass().getResource(filename);
			AudioInputStream sound = AudioSystem.getAudioInputStream(url);
			AudioFormat format = sound.getFormat();
			DataLine.Info di = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip)AudioSystem.getLine(di);
			clip.open(sound);
			return clip;
		}catch (Exception e) {
			System.err.println( "sound file not init...");
		}
		return null;
	}
	/**
	 * 設定ファイルの読み込み
	 */
	private void readSetting() {
		try {
			File file = new File(FILENAME+"x");
			Setting temp = JAXB.unmarshal(file, Setting.class);
			setting = temp.getSetting();
		}catch (javax.xml.bind.DataBindingException e) {
			createDefault();
			writeSetting();
		}
	}
	/**
	 * 設定ファイルの書き込み
	 */
	private void writeSetting() {
		try {
			File file = new File(FILENAME+"x");
			JAXB.marshal(new Setting(setting), file);
		}catch (javax.xml.bind.DataBindingException e) {
			createDefault();
		}
	}
	/**
	 * 設定ファイルの読み込み(バイナリ版)
	 */
	@SuppressWarnings("unchecked")
	private void readSetting_b() {
		FileInputStream inFile = null;
		ObjectInputStream inStm = null;
		try {
			inFile = new FileInputStream(FILENAME);
			inStm = new ObjectInputStream(inFile);
			
			setting = (List<Data>)inStm.readObject();
			
			System.out.println("readSetting");
			
		}catch (FileNotFoundException e) {
			System.err.println("ファイルがないので新規作成します");
			createDefault();
			writeSetting();
		}catch (ClassCastException e) {
			System.err.println("読み込みデータのクラスタイプが異なります");
			createDefault();
			writeSetting();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if( inStm != null ) {
		           inStm.close(); 
		           inStm = null;
				}
				if( inFile != null ) {
					inFile.close();
					inFile = null;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 設定ファイルの保存(バイナリ版)
	 */
	private void writeSetting_b() {
		
		FileOutputStream outFile = null;
		ObjectOutputStream outStm = null;
		try {
			outFile = new FileOutputStream(FILENAME);
			outStm = new ObjectOutputStream(outFile);
			outStm.writeObject(setting);
			
			System.out.println("writeSetting");
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if( outStm != null ) {
		           outStm.close(); 
		           outStm = null;
				}
				if( outFile != null ) {
					outFile.close();
					outFile = null;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	/**
	 * 未定義時の初期データの作成
	 */
	private void createDefault() {
		setting = new ArrayList<>();
		setting.add( new Data("time 1", new Time( 10, 10), new Time( 11,  0), true));
		setting.add( new Data("time 2", new Time( 11, 10), new Time( 12,  0), true));
		setting.add( new Data("time 3", new Time( 12, 10), new Time( 13,  0), true));
		setting.add( new Data("time 4", new Time( 14, 00), new Time( 14, 50), true));
		setting.add( new Data("time 5", new Time( 15, 00), new Time( 15, 50), true));
		setting.add( new Data("time 6", new Time( 16, 00), new Time( 16, 50), true));
		setting.add( new Data("time 7", new Time( 17, 00), new Time( 17, 50), true));
	}
	// #endregion ----------------------------------------------
}
