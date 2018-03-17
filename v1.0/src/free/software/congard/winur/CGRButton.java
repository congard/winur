package free.software.congard.winur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class CGRButton extends JButton {
	private int id = -1;
	private ButtonActionListener bal;
	
	public CGRButton(String text) {
		super(text);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (bal != null) bal.onActionCatched(CGRButton.this, arg0);
			}});
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setActionListener(ButtonActionListener bal) {
		this.bal = bal;
	}
	
	public static interface ButtonActionListener {
		public void onActionCatched(CGRButton btn, ActionEvent event);
	}
}
