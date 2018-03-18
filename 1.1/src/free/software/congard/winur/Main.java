package free.software.congard.winur;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import free.software.congard.winur.CGRButton.ButtonActionListener;

/**
 * t.me/congard
 * github.com/congard
 * @author congard
 *
 */
public class Main {
	long totalSize = 0;
	
	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setTitle("Winur v1.1");
		window.setBounds(64, 64, 768, 512);
		window.setVisible(true);
		// label here
		CGRButton btnDeleteAll = new CGRButton("Delete all updates");
		btnDeleteAll.setId(0);
		CGRButton btnReboot = new CGRButton("Reboot without installation updates");
		btnReboot.setId(1);
		CGRButton btnShutdown = new CGRButton("Shutdown without installation updates");
		btnShutdown.setId(2);
		
		ButtonActionListener bal = new ButtonActionListener() {
			@Override
			public void onActionCatched(CGRButton btn, ActionEvent event) {
				if (btn.getId() == 0) removeAll();
				else if (btn.getId() == 1) exec("shutdown /r");
				else exec("shutdown");
			}};

	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    Container container = window.getContentPane();
	    container.setLayout(new VerticalLayout());
	    container.add(new JLabel("<html>Windows Update Remover - Winur<br>"
	    		+ "This application must be running with superuser privileges!<br>"
				+ "This utility can remove downloaded updates avoiding installation<br>"
				+ "Developer: Congard<br>"
				+ "Links: dbcongard@gmail.com *** t.me/congard *** github.com/congard<br><br>"
				+ "</html>"));
	    
	    btnDeleteAll.setActionListener(bal);
	    container.add(btnDeleteAll);
	    btnReboot.setActionListener(bal);
	    container.add(btnReboot);
	    btnShutdown.setActionListener(bal);
	    container.add(btnShutdown);
	    window.revalidate();
	}
	
	private static void exec(String s) {
		try {
			Runtime.getRuntime().exec(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void removeAll() {
		JFrame window = new JFrame();
		window.setTitle("Working");
		window.setBounds(256, 256, 256, 128);
		window.setVisible(true);
	    Container container = window.getContentPane();
	    container.setLayout(new VerticalLayout());
	    container.add(new JLabel("Removing windows updates... Please, wait"));
	    
		Main m = new Main();
		m.deleteRecurs(new File(System.getenv("WINDIR") + "\\SoftwareDistribution"));
		System.out.println("Job done, total size=" + m.totalSize + ", " + (m.totalSize/1024f/1024f) + "mb");
		window.dispose();
		JOptionPane.showMessageDialog(null,
				"Job done, total size " + m.totalSize + "b " + (m.totalSize/1024f/1024f) + "mb",
	    		"Result",
	    	    JOptionPane.PLAIN_MESSAGE);
	}
	
	private void deleteRecurs(File dir) {
		File f[] = dir.listFiles();
		for (int i = 0; i<f.length; i++) {
			if (f[i].isDirectory()) {
				deleteRecurs(f[i]);
				f[i].delete();
				System.out.println("Folder " + f[i] + " deleted");
			} else {
				totalSize+=f[i].length();
				f[i].delete();
				System.out.println("File " + f[i] + " deleted");
			}
		}
	}
}