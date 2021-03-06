package n.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import n.db.DataSource;
import n.models.Area;
import n.models.TravelAreas;
import n.models.Central;
import n.models.North;
import n.models.Qualification;
import n.models.Referee;
import n.models.South;

/**
 * This is the tab that allows the user to search, delete and add referees. 
 */
public class Manage extends JPanel implements ActionListener {
	
	// static variable for tab name within the GUI
	public static final String TAB_NAME = "Manage";
	
	// static variable of serialVersionUID
	private static final long serialVersionUID = 1L;
	
	// static variables for assisting GUI within the Manage tab for providing names for labels
	private static final String REFEREE_EDIT = "Edit";
	private static final String REFEREE_SAVE = "Save";
	private static final String REFEREE_ADD = "Add";
	private static final String REFEREE_CANCEL = "Cancel";
	private static final String REFEREE_DELETE = "Delete";
	
	// instance variables for GUI components
	JPanel editPanel;
	JButton btnFormCancel;
	JTable table;
	
	// instance of Referee, DataSource and Referees are also used within this class
	Referee referee;
	DataSource dataSource;
	Referees refereesTab;

	/**
	 * The constructor takes a reference to the DataSource as well as the
	 * Referees class where a table of referees is displayed that has to be
	 * updated in response to user actions in this class.
	 * 
	 * @param fileStore
	 * @param refereesTab
	 */
	public Manage(DataSource fileStore,Referees refereesTab) {
		this.dataSource = fileStore;
		this.refereesTab = refereesTab;
		this.setLayout(new BorderLayout());
		layoutComponents();
	}
	
	/**
	 * Layouts the main components within the tab.
	 */
	private void layoutComponents() {
		JPanel north = new JPanel(new GridLayout(1, 2));
		JPanel searchSection = new JPanel(new GridLayout(3, 2));

		JLabel lblFirstName = new JLabel("First name");
		searchSection.add(lblFirstName);
		
		JTextField txtFirstName = new JTextField("");
		txtFirstName.setMaximumSize(txtFirstName.getPreferredSize());
		
		JTextField txtLastName = new JTextField("");
		txtLastName.setMaximumSize(txtLastName.getPreferredSize());
		
		JButton btnSearch = new JButton(Manage.TAB_NAME);
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				runSearch(txtFirstName.getText().trim(),txtLastName.getText().trim());
				txtFirstName.setText("");
				txtLastName.setText("");
				btnSearch.setEnabled(false);
			}
		});
		btnSearch.setEnabled(false);
		
		txtFirstName.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {}
			@Override
			public void focusLost(FocusEvent arg0) {
            	if (!txtFirstName.getText().trim().equals("") 
            			&& !txtLastName.getText().trim().equals("")) {
            		btnSearch.setEnabled(true);
            	} else {
            		btnSearch.setEnabled(false);
            	};
			}
        });
		searchSection.add(txtFirstName);

		JLabel lblLastName = new JLabel("Last name");
		searchSection.add(lblLastName);

		txtLastName.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
            	if (!txtLastName.getText().equals("") 
            			&& !txtLastName.getText().equals("")) {
            		btnSearch.setEnabled(true);
            	} else {
            		btnSearch.setEnabled(false);
            	};
            }
        });
		searchSection.add(txtLastName);

		JLabel lblDummy = new JLabel("");
		searchSection.add(lblDummy);

		searchSection.add(btnSearch);
		
		north.add(searchSection);

		JPanel addSection = new JPanel(new BorderLayout());
		JLabel lblAddTitle = new JLabel("Add a new Referee",SwingConstants.CENTER);
		addSection.add(lblAddTitle,BorderLayout.CENTER);
		JButton btnAdd = new JButton(Manage.REFEREE_ADD);
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAddOrEditForm(Manage.REFEREE_ADD);
			}
		});
		
		btnAdd.setEnabled(true);
		addSection.add(btnAdd,BorderLayout.SOUTH);
		
		north.add(addSection);
		this.add(north, BorderLayout.NORTH);
		
		editPanel = new JPanel(new BorderLayout());
		this.add(editPanel);
	}
	
	/**
	 * This is the main method that recreates the form elements on the GUI 
	 * depending on whether the user wants to add a new referee, search or
	 * edit one.
	 * 
	 * In order to minimise the number of instance variables of this class
	 * and promote loose coupling all variables needed to recreate the form
	 * are kept within the method. 
	 * 
	 * The button for Edit and Save uses its label to store the state.
	 *  
	 * @param formAction
	 */
	private void showAddOrEditForm(String formAction) {
		editPanel.removeAll();
		if (formAction == Manage.REFEREE_ADD) {
			referee = new Referee("","");
		} 
		JPanel addFormInput = new JPanel(new GridLayout(Referee.FIELD_NAMES.length, 2));
		
		// --------------------------- ID -------------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[0]));
		JTextField txtEditId = new JTextField(referee.getId());
		txtEditId.setMaximumSize(txtEditId.getPreferredSize());
		txtEditId.setEditable(false);
		addFormInput.add(txtEditId);
		
		// ----------------------- FIRST NAME ---------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[1]));
		JTextField txtEditFirstName = new JTextField(referee.getFirstName());
		txtEditFirstName.setMaximumSize(txtEditFirstName.getPreferredSize());
		txtEditFirstName.setEditable(formAction.equals(Manage.REFEREE_ADD));
		addFormInput.add(txtEditFirstName);
		
		// ------------------------ LAST NAME ---------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[2]));
		JTextField txtEditLastName = new JTextField(referee.getLastName());
		txtEditLastName.setMaximumSize(txtEditLastName.getPreferredSize());
		txtEditLastName.setEditable(formAction.equals(Manage.REFEREE_ADD));
		addFormInput.add(txtEditLastName);
		
		// ---------------------- QUALIFICAITONS ------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[3]));
		JPanel cbQualificationsPanel = new JPanel();
		
		JComboBox<String> cbAwardingBodies = new JComboBox<String>(Qualification.AWARDING_BODIES);
		cbAwardingBodies.setSelectedIndex(referee.getQualification().getAwardingBodyIndex());
		cbAwardingBodies.setMaximumSize(cbAwardingBodies.getPreferredSize());
		cbAwardingBodies.setEnabled(formAction.equals(Manage.REFEREE_ADD));
		cbAwardingBodies.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				referee.getQualification().setAwardingBody(cbAwardingBodies.getSelectedItem().toString());
			}
		});
		cbQualificationsPanel.add(cbAwardingBodies);
		
		JComboBox<Short> cbQualificationLevel = new JComboBox<Short>(Qualification.LEVELS);
		cbQualificationLevel.setSelectedIndex(referee.getQualification().getLevelIndex());
		cbQualificationLevel.setMaximumSize(cbAwardingBodies.getPreferredSize());
		cbQualificationLevel.setEnabled(formAction.equals(Manage.REFEREE_ADD));
		cbQualificationLevel.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				referee.getQualification().setLevel((short)cbQualificationLevel.getSelectedItem());
			}
		});
		cbQualificationsPanel.add(cbQualificationLevel);
		addFormInput.add(cbQualificationsPanel);
		
		// ----------------------- ALLOCATIONS --------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[4]));		
		JTextField txtEditAllocations = new JTextField(Integer.toString(referee.getAllocations()));
		txtEditAllocations.setMaximumSize(txtEditAllocations.getPreferredSize());
		txtEditAllocations.setEditable(formAction.equals(Manage.REFEREE_ADD));
		addFormInput.add(txtEditAllocations);
		
		// ----------------------- HOME AREAS ---------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[5]));
		
		JComboBox<Area> cbHomeAreas = new JComboBox<Area>(TravelAreas.AREAS);
		/*
		 *  The making of the combo box entry editable is temporarily needed
		 *  for the setSelectedItem to take effect. 
		 */
		cbHomeAreas.setEditable(true);
		cbHomeAreas.setSelectedItem(referee.getHomeArea());
		cbHomeAreas.setEditable(false);
		cbHomeAreas.setMaximumSize(cbHomeAreas.getPreferredSize());
		cbHomeAreas.setEnabled(formAction.equals(Manage.REFEREE_ADD));
		addFormInput.add(cbHomeAreas);
		
		// ---------------------- TRAVEL AREAS --------------------------------
		addFormInput.add(new JLabel(Referee.FIELD_NAMES[6]));
		JPanel cbPanel = new JPanel();
		JCheckBox cbNorth = new JCheckBox();
		cbNorth.setText(referee.getTravelAreas().getNorth().toString());
		cbNorth.setSelected(referee.getTravelAreas().getNorth().getTravel());
		if (referee.getHomeArea() instanceof North) {
			cbNorth.setEnabled(false);
		} else {
			cbNorth.setEnabled(formAction.equals(Manage.REFEREE_ADD));
		}
		cbNorth.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				referee.getTravelAreas().getNorth().setTravel(cbNorth.isSelected());
			}
		});
		cbPanel.add(cbNorth);
		
		JCheckBox cbCentral = new JCheckBox();
		cbCentral.setText(referee.getTravelAreas().getCentral().toString());
		cbCentral.setSelected(referee.getTravelAreas().getCentral().getTravel());
		if (referee.getHomeArea() instanceof Central) {
			cbCentral.setEnabled(false);
		} else {
			cbCentral.setEnabled(formAction.equals(Manage.REFEREE_ADD));
		}
		cbCentral.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				referee.getTravelAreas().getCentral().setTravel(cbCentral.isSelected());
			}
		});
		cbPanel.add(cbCentral);
		
		JCheckBox cbSouth = new JCheckBox();
		cbSouth.setText(referee.getTravelAreas().getSouth().toString());
		cbSouth.setSelected(referee.getTravelAreas().getSouth().getTravel());
		if (referee.getHomeArea() instanceof South) {
			cbSouth.setEnabled(false);
		} else {
			cbSouth.setEnabled(formAction.equals(Manage.REFEREE_ADD));
		}
		cbSouth.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				referee.getTravelAreas().getSouth().setTravel(cbSouth.isSelected());;
			}
		});
		cbPanel.add(cbSouth);
		
		cbPanel.setMaximumSize(cbPanel.getPreferredSize());
		addFormInput.add(cbPanel);
		
		// ---- home areas listener
		cbHomeAreas.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				Area selected = (Area)cbHomeAreas.getSelectedItem();
				referee.setHomeArea(selected);
				cbNorth.setEnabled(true);
				cbCentral.setEnabled(true);
				cbSouth.setEnabled(true);
				if (selected instanceof North) { 
					cbNorth.setSelected(true);
					cbCentral.setSelected(false);
					cbSouth.setSelected(false);
					cbNorth.setEnabled(false);
				}
				if (selected instanceof Central) {
					cbCentral.setSelected(true);
					cbNorth.setSelected(false);
					cbSouth.setSelected(false);
					cbCentral.setEnabled(false);
				}
				if (selected instanceof South) {
					cbSouth.setSelected(true);
					cbCentral.setSelected(false);
					cbNorth.setSelected(false);
					cbSouth.setEnabled(false);
				}
			}
		});
		
		// ------------------------- BUTTONS ----------------------------------
		JButton btnAddOrEdit = new JButton(formAction.equals(Manage.REFEREE_EDIT) 
				? Manage.REFEREE_EDIT : Manage.REFEREE_ADD);
		btnAddOrEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (btnAddOrEdit.getText()) {
					case Manage.REFEREE_EDIT : 
						btnAddOrEdit.setText(Manage.REFEREE_SAVE);
						cbAwardingBodies.setEnabled(true);
						cbQualificationLevel.setEnabled(true);
						cbHomeAreas.setEnabled(true);
						cbNorth.setEnabled(true);
						cbCentral.setEnabled(true);
						cbSouth.setEnabled(true);
						break;
					case Manage.REFEREE_SAVE :
						if (dataSource.update(referee)) {
							refereesTab.refreshTableData();
							JOptionPane.showMessageDialog(null, "Referee details have been updated.",
									"New Data", JOptionPane.INFORMATION_MESSAGE);
							btnAddOrEdit.setText(Manage.REFEREE_SAVE);
							showAddOrEditForm(Manage.REFEREE_EDIT);
						};
						break;
					case Manage.REFEREE_ADD :
						if (validateName(txtEditFirstName.getText()) 
								&& validateName(txtEditLastName.getText())) {
							referee.setFirstName(txtEditFirstName.getText().trim());
							referee.setLastName(txtEditLastName.getText().trim());
							if (validateAllocations(txtEditAllocations.getText().trim())) {
									referee.setId(dataSource.getRefereeId(referee));
									if (dataSource.add(referee)) {
										JOptionPane.showMessageDialog(null, 
											"New Referee has been added.",
											"New Referee", JOptionPane.INFORMATION_MESSAGE);
									} else {
										JOptionPane.showMessageDialog(null, 
												"You have reached the maximum "
												+ "number of allowed referees " + DataSource.MAX_REFEREES,
												"New Referee", JOptionPane.WARNING_MESSAGE);
									}
									refereesTab.refreshTableData();
									showAddOrEditForm(Manage.REFEREE_EDIT);
								} 
						}
						break;
					default: 
						break;
				}
			}
		});
		
		btnFormCancel = new JButton(Manage.REFEREE_CANCEL);
		btnFormCancel.addActionListener(this);
		
		JButton btnFormDelete = new JButton(Manage.REFEREE_DELETE);
		btnFormDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (dataSource.remove(referee)) {
					runSearch(referee.getFirstName(),referee.getLastName());
					refereesTab.refreshTableData();
					JOptionPane.showMessageDialog(null, "Referee has been removed",
							"Entry removed", JOptionPane.INFORMATION_MESSAGE);
					
				} else {
					JOptionPane.showMessageDialog(null, "Referee has not been found",
						"No entry", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		JPanel addFormButtons;
		if (formAction.equals(Manage.REFEREE_EDIT)) {
			addFormButtons = new JPanel(new GridLayout(1,3));
			addFormButtons.add(btnAddOrEdit);
			addFormButtons.add(btnFormCancel);
			addFormButtons.add(btnFormDelete);
		} else {
			addFormButtons = new JPanel(new GridLayout(1,2));
			addFormButtons.add(btnAddOrEdit);
			addFormButtons.add(btnFormCancel);
		}
		editPanel.add(addFormInput);
		editPanel.add(addFormButtons,BorderLayout.SOUTH);
		this.revalidate();
	}

	/**
	 * Checks if the text input string for the name were valid.
	 * @param name
	 * @return boolean if input valid
	 */
	private boolean validateName(String name) {
		if(!name.matches(".*\\d.*")){
			if (!name.trim().equals("")) {
				return true;
			} else {
	    		JOptionPane.showMessageDialog(null, 
	            	"This field cannot be empty.",
	        		"Invalid input: " + !name.trim().equals(""), JOptionPane.WARNING_MESSAGE);
				return false;	
			}
		} else{
    		JOptionPane.showMessageDialog(null, 
        		"This field cannot contain numbers.",
    			"Invalid input: " + name, JOptionPane.WARNING_MESSAGE);
    		return false;
		}
	}
	
	/**
	 * Ensures the number input in the allocations text box is a valid number.
	 * The method allows the input number to be smaller than the original.
	 * @param allocations
	 * @return whether allocations is a valid number
	 */
    public boolean validateAllocations(String allocations) {
		if(!allocations.matches(".*\\[a-zA-Z]+.*")){
    		try {
    			int alloc = Integer.parseInt(allocations.trim());
    			if (alloc > -1) {
    				referee.setAllocations(alloc);
    				return true;
    			} else {
            		JOptionPane.showMessageDialog(null, 
            			"Input must be a number greater or equal to 0",
        				"Invalid input", JOptionPane.WARNING_MESSAGE);
    				return false;	
    			}
    		} catch (NumberFormatException nfe) {
        		JOptionPane.showMessageDialog(null, "Input must be a valid number.",
					"Invalid input", JOptionPane.WARNING_MESSAGE);
        		return false;
    		}
		} else{
    		JOptionPane.showMessageDialog(null, 
        		"This field cannot contain characters.",
    			"Invalid input: " + allocations, JOptionPane.WARNING_MESSAGE);
    		return false;
		}
    }

	/**
	 * Searches the referees array from the data source for a referee with 
	 * the specified firstName and lastName.
	 * 
	 * The searching and updating of the referees has a minor flaw. It is
	 * possible to have two referees with the same first name and surname
	 * added but only one will ever be edited. This problem can be solved 
	 * with the adding of search by id functionality not mentioned in the
	 * specification document. No attempt was made to implement that.
	 * @param firstName
	 * @param lastName
	 */
	private void runSearch(String firstName, String lastName) {
		referee = dataSource.search(firstName,lastName);
		if (referee != null) {
			showAddOrEditForm(Manage.REFEREE_EDIT);
		} else {
			editPanel.removeAll();
			JPanel noResultPanel = new JPanel(new BorderLayout());
			JLabel lblNoResults = new JLabel("No Results");
			lblNoResults.setHorizontalAlignment(JLabel.CENTER);
			lblNoResults.setVerticalAlignment(JLabel.CENTER);
			noResultPanel.add(lblNoResults,BorderLayout.CENTER);
			editPanel.add(noResultPanel);
			this.revalidate();
		}
	}
	
	/**
	 * The listener method for ActionListener object that this class 
	 * implements.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnFormCancel) {
			editPanel.removeAll();
			editPanel.add(new JPanel(new BorderLayout()));
			this.revalidate();
		} 
	}

}
