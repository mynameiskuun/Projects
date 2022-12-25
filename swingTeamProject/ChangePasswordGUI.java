package swingTeamProject;

//비밀번호 변경 새 창
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChangePasswordGUI extends JFrame {
	JLabel nowPwL = new JLabel("현재 비밀번호");
	JLabel newPwL = new JLabel("새 비밀번호");
	JLabel pwConL = new JLabel("비밀번호 확인");
	JTextField nowPw = new JTextField("");
	JTextField newPw = new JTextField("");
	JTextField pwCon = new JTextField("");
	JButton jb1 = new JButton("확인");
	JPanel jp = new JPanel();

	JDBC db = new JDBC();

	MemberData st = db.selectUI(LoginGUI.uid);

	String pwCk = st.password;
	String pwRegex = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$";

	ChangePasswordGUI() throws ClassNotFoundException, SQLException {

		setTitle("비밀번호 변경");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = this.getContentPane();
		getContentPane().setLayout(null);
		jp.setBounds(0, 0, 0, 0);
		jp.setLayout(null);
		c.add(jp);
		c.add(nowPw);
		nowPw.setBounds(225, 56, 120, 30);
		c.add(newPw);
		newPw.setBounds(225, 106, 120, 30);
		c.add(pwCon);
		pwCon.setBounds(225, 156, 120, 30);
		c.add(nowPwL);
		nowPwL.setBounds(98, 55, 100, 30);
		c.add(newPwL);
		newPwL.setBounds(98, 105, 100, 30);
		c.add(pwConL);
		pwConL.setBounds(98, 155, 100, 30);
		c.add(jb1);
		jb1.setBounds(245, 242, 100, 30);
		jb1.addActionListener(new ActionListener() { // 비밀번호

			@Override
			public void actionPerformed(ActionEvent e) {
				if (nowPw.getText().equals("") || newPw.getText().equals("") || pwCon.getText().equals("")) { // 3칸 다 공란일 때
					JOptionPane.showMessageDialog(jb1, "빈칸을 입력해주세요");
				} else {
					if (nowPw.getText().equals(pwCk)) { // 3칸 다 채워지고 조건에 맞을 때
						if (newPw.getText().equals(pwCon.getText())) {
							if (newPw.getText().matches(pwRegex) == true) { // 양식에 맞는지
								try {
									db.updatePw(newPw.getText(), LoginGUI.uid);
									JOptionPane.showMessageDialog(jb1, "비밀번호 변경이 완료되었습니다.");
									dispose();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								System.out.println("안됨2");
							} else {
								JOptionPane.showMessageDialog(jb1, "양식에 맞는 비밀번호를 입력 해 주세요.");
								return;
							}
						} else { // 새로 바꿀 비밀번호끼리 일치하지 않을 때
							JOptionPane.showMessageDialog(null, "입력하신 비밀번호와 새로운 비밀번호가 일치하지 않습니다. 다시 확인해주세요", "",
									JOptionPane.ERROR_MESSAGE);
						}
					} else { // 기존 로그인 비빌번호와 일치하지 않을 때
						JOptionPane.showMessageDialog(null, "입력하신 비밀번호가 일치하지 않습니다. 다시 확인해주세요", "",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		this.setVisible(true);
		this.setSize(442, 371);
		this.setLocation(500, 500);
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		new ChangePasswordGUI();
	}
}