package n.gui;
import javax.swing.JPanel;

import n.db.DataSource;


public class Chart extends JPanel {

	public static final String TAB_NAME = "Chart";
	private static final long serialVersionUID = 1L;
	DataSource fileStore;
	
	public Chart(DataSource fileStore) {
		this.fileStore = fileStore;
	}

	
}