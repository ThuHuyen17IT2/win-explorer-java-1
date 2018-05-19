package winexplorer;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



@SuppressWarnings("serial")
public class java extends JFrame {

    
	
	private JPanel contentPane;
	private JTextField txtPath;
	private JTextField textField_1;

	public static String CURRENT_FOLDER = "C:\\";
	public static String PREVIOUS_FOLDER = "C:\\";
	
	public DefaultTableModel TABLE_MODEL = new DefaultTableModel(0, 0);
	public String[] FOLDER_HEADER = new String[] { "Name", "Date modified", "Type",
            "Size"};
	private JTable tblFiles;
	private  void copyFolder(File sourceFolder, File destinationFolder) throws IOException
	    {
	        if (sourceFolder.isDirectory())
	        {
	            if (!destinationFolder.exists())
	            {
	                destinationFolder.mkdir();
	                System.out.println("Directory created :: " + destinationFolder);
	            }
	          
	            String files[] = sourceFolder.list();
	            
	            for (String file : files)
	            {
	                File srcFile = new File(sourceFolder, file);
	                File destFile = new File(destinationFolder, file);
	                 
	                copyFolder(srcFile, destFile);
	            }
	        }
	        else
	        {
	            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
	            System.out.println("File copied :: " + destinationFolder);
	        }
		 }
	    
		public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					java frame = new java();
					frame.setVisible(true);
					 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	

	public java() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 522, 339);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		txtPath = new JTextField();
		panel.add(txtPath);
		txtPath.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.WEST);
		
		JButton btnUndo = new JButton("");
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				CURRENT_FOLDER = PREVIOUS_FOLDER;
				TABLE_MODEL = loadFileInFolder(CURRENT_FOLDER, TABLE_MODEL);
        		txtPath.setText(CURRENT_FOLDER);
			}
		});
		btnUndo.setIcon(new ImageIcon(java.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
		panel_3.add(btnUndo);
		
		JButton btnRedo = new JButton("");
		btnRedo.setIcon(new ImageIcon(java.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		panel_3.add(btnRedo);
		
		JButton copyFolder = new JButton("Copy");
        panel_3.add(copyFolder);
         try {
        	 MouseEvent e = null ;
        	 @SuppressWarnings("null")
			JTable table =(JTable) e.getSource();
				int row = table.getSelectedRow();
		        String name = table.getModel().getValueAt(row, 0).toString();
		        if (e.getClickCount() == 1) {
		           
		        	PREVIOUS_FOLDER = CURRENT_FOLDER;
		        	CURRENT_FOLDER = CURRENT_FOLDER + name + "\\";
		        	File folder = new File(CURRENT_FOLDER);
				copyFolder(folder,folder);}}
				 
			 catch (Exception e) {
				e.printStackTrace();
			}
         
        JTextField txtpath = new JTextField();
		panel.add(textField_1, BorderLayout.EAST);
	    txtpath.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		JTextField txtDetail = new JTextField();
		panel.add(txtDetail);
	    txtDetail.setColumns(80);
		
	    txtDetail.setText("HELLO");
	
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JTree tree = new JTree();
		panel_2.add(tree);
		
		JTree tree_1 = new JTree();
		panel_2.add(tree_1);
		
		JTree tree_2 = new JTree();
		panel_2.add(tree_2);
		
		
		txtPath.setText(CURRENT_FOLDER);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		tblFiles = new JTable();
		tblFiles.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				JTable table =(JTable) e.getSource();
				Point point = e.getPoint();
		      
		        int row = table.getSelectedRow();
		        String name = table.getModel().getValueAt(row, 0).toString();
		        if (e.getClickCount() == 2) {
		          
		        	PREVIOUS_FOLDER = CURRENT_FOLDER;
		        	CURRENT_FOLDER = CURRENT_FOLDER + name + "\\";
		        	File folder = new File(CURRENT_FOLDER);
		        	if (folder.isDirectory()) {
		        		TABLE_MODEL = loadFileInFolder(CURRENT_FOLDER, TABLE_MODEL);
		        		txtPath.setText(CURRENT_FOLDER);
		        	}
		        }

			}
		});
		scrollPane.setViewportView(tblFiles);
		tblFiles.setModel(TABLE_MODEL);
		TABLE_MODEL.setColumnIdentifiers(FOLDER_HEADER);
		
		TABLE_MODEL = loadFileInFolder(CURRENT_FOLDER, TABLE_MODEL);
		tblFiles.setAutoCreateRowSorter(true);
		
		
	}
	

	public DefaultTableModel loadFileInFolder(String path, DefaultTableModel myTableModel) {
		if (myTableModel.getRowCount() > 0) {
		    for (int i = myTableModel.getRowCount() - 1; i > -1; i--) {
		        myTableModel.removeRow(i);
		    }
		}
		
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

	    for (int i = 0; i < listOfFiles.length; i++) {
	    	
	    	//{ "Name", "Date modified", "Type","Size"};
	    	File currentFile = listOfFiles[i];
            String name = currentFile.getName();
            String type = "";
            String size = "";
	    	if (listOfFiles[i].isFile()) {
	    		type = "File";
	    		// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
	    		long fileSizeInBytes = currentFile.length();
	    		long fileSizeInKB = fileSizeInBytes / 1024;
	    		size = "" + fileSizeInKB + " kb";
    		} else if (listOfFiles[i].isDirectory()) {
    			type = "Folder";
    		}
	        	
	    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	    	String dateModified = sdf.format(currentFile.lastModified());
	    	
	    	myTableModel.addRow(new Object[] { name, dateModified, type, size});
	    }
		return myTableModel;
	}

}




