import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Connect extends JFrame {
	private Connection conn;
	private Statement st;
	private ResultSet rs;
	private String header[] = { "ID", "HoTen","DiaChi", "Luong" };
	private DefaultTableModel tblModel = new DefaultTableModel(header, 0);
	private JTable tb;
	
	// URL jdbc:sqlserver://DESKTOP-D9J383E\\SQLEXPRESS:1433;DatabaseName=KhachHang
	
	public Connect() {
		JFrame fr = new JFrame("Thong Tin");
		fr.setBounds(300, 200, 500, 200);
		fr.setLayout(new BorderLayout());
		JPanel pn1 = new JPanel();
		
		fr.add(pn1, BorderLayout.NORTH);
		pn1.setLayout(new FlowLayout());
		
		pn1.add(new JLabel("Nhap Thong tin"));	
		JTextField tfURL = new JTextField(10);
		pn1.add(tfURL);
		pn1.add(new JLabel("Nhap cau lenh sql"));	
		JTextField tfSql = new JTextField(10);
		pn1.add(tfSql);
		
		JPanel pnButton = new JPanel();
		fr.add(pnButton, BorderLayout.SOUTH);
		JButton btSubmit = new JButton("Submit");
		pnButton.add(btSubmit);
		JButton btReset = new JButton("Reset");
		pnButton.add(btReset);
		JButton btExit = new JButton("Exit");
		pnButton.add(btExit);
		
		
		JPanel pn2 = new JPanel();
		fr.add(pn2, BorderLayout.CENTER);
		pn2.setLayout(new BorderLayout());
		
		JPanel pn4 = new JPanel();
		pn2.add(pn4, BorderLayout.CENTER);

		tb = new JTable();
		tb.setVisible(true);
		pn4.add(tb);

		fr.setVisible(true);
		fr.setDefaultCloseOperation(EXIT_ON_CLOSE);

		btSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String query = tfSql.getText();
				String URL = tfURL.getText();
				loadTable(URL,query);
			}
		});

		btReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tfURL.setText("Nhap thong tin can tim");
				tfSql.setText("Nhap cau lenh");
			}
		});

		btExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fr.dispose();
			}
		});
	}

	public void loadTable(String URL, String query) {
		ConnectCSDL lcdb = new ConnectCSDL();
		conn = lcdb.getConnectMySQL(URL);
		tblModel.addRow(header);
		try {
			String sql = query;
			st = conn.createStatement();
			
			rs = st.executeQuery(sql);
			Vector data = null;
			tblModel.setRowCount(1);
			while (rs.next()) {
				data = new Vector();
				data.add(rs.getInt("id"));
				data.add(rs.getString("TenKH"));
				data.add(rs.getString("DiaChi"));
				data.add(rs.getDouble("Luong"));
				tblModel.addRow(data);
			}
			tb.setModel(tblModel);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (st != null) {
					st.close();
				}
				if (rs != null) {
					rs.close();
					
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Connect demo = new Connect();
	}
}
