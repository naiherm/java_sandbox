package timerApp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;

public class SettingDlg extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private boolean change = false;
	public boolean isChange() { return this.change; }
	
	/**
	 * Launch the application. 動作確認用に使用します。
	 */
	public static void main(String[] args) {
		try {
			SettingDlg dialog = new SettingDlg();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * デザイナー用デフォルトコンストラクタ
	 */
	public SettingDlg() {
		initDefault();
		initTable( new TableModel( null ) );
	}
	/**
	 * プログラム制御用コンストラクタ(モーダルダイアログ)
	 * @param owner ダイアログオーナーウィンドウ
	 * @param model 表示されるテーブルモデル 
	 */
	public SettingDlg( Frame owner, TableModel model ) {
		super(owner, true);
		initDefault();
		initTable( model );
	}

	/**
	 * テーブル表現を定義
	 * @param model 表示されるテーブルモデル
	 */
	private void initTable(TableModel model) {
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				table = new JTable();
				table.setFont(new Font("メイリオ", Font.PLAIN, 16));
				table.setRowHeight(24);
				table.setModel( model );
			}
			scrollPane.setViewportView(table);
		}
	}

	/**
	 * ダイアログレイアウト を定義
	 */
	private void initDefault() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				{
					JButton cancelButton = new JButton("Cancel");
					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							change = false;
							setVisible(false);
						}
					});
					cancelButton.setActionCommand("Cancel");
					buttonPane.add(cancelButton);
				}
				{
					JButton okButton = new JButton("OK");
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							change = true;
							setVisible(false);
						}
					});
					okButton.setActionCommand("OK");
					buttonPane.add(okButton);
					getRootPane().setDefaultButton(okButton);
				}
			}
		}
	}
}
