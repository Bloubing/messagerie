package fr.uga.miashs.dciss.chatservice.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.nio.ByteBuffer;
import java.awt.event.ActionEvent;

public class RenameGroupFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RenameGroupFrame frame = new RenameGroupFrame(new ClientMsg("localHost", 1666));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RenameGroupFrame(ClientMsg c) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel titre = new JLabel("Renommer un groupe");
		titre.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(titre, BorderLayout.NORTH);
		
		JButton ajouter = new JButton("Renommer");
		contentPane.add(ajouter, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel_1.add(panel);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel groupe = new JLabel("Nom du groupe :");
		panel.add(groupe);
		
		JTextArea nom = new JTextArea();
		panel.add(nom);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new GridLayout(1,0 , 0, 0));
		
		JLabel nouveauNomLabel = new JLabel("Nouveau nom");
		panel_2.add(nouveauNomLabel);
		
		JTextArea nouveauNom = new JTextArea();
		panel_2.add(nouveauNom);
		
		ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( nom.getText().length() > 0 && nouveauNom.getText().length()>0) {
					String nomGroupe = nom.getText();
					byte longueur = (byte)nomGroupe.length();
					String nouveauNomString =  nouveauNom.getText();
					ByteBuffer data = ByteBuffer.allocate(5+(nomGroupe.length()*2)+(nouveauNomString.length()*2));
					data.putInt(6);
					data.put(longueur);
					// on met le type de paquet ( 6 pour renommage )
					for(int i = 0; i < nomGroupe.length(); i++) {
						data.putChar(nomGroupe.charAt(i));
					}
					for(int i = 0; i < nouveauNomString.length(); i++) {
						data.putChar(nouveauNomString.charAt(i));
					}
					c.sendPacket(0, data.array());
					// On envoit au destinaire 0 car création groupe, et avec le tableau de bytes;
					JOptionPane.showMessageDialog(null, "groupe renommé");
					fermer();
				}
			}
		});
		
	}
	public void fermer() {
		//fermeture automatique de la frame après creationGroupe
		this.dispose();
	}

}
