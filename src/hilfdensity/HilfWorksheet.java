
package hilfdensity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class HilfWorksheet {
    private JFrame mainFrame = new JFrame();
    private JPanel mainPanel = new JPanel();
    private JPanel gridBagPanel = new JPanel();
    private JPanel aboveTablePanel = new JPanel();
    private JPanel tablePanel = new JPanel();
    private JPanel closePanel = new JPanel();
    
    private JScrollPane tableScroll;//scrollpane that goes in the jtable
    private HilfTable hilfTable; //table for all hilf density information
    
    //all jlabels are paired with a jtextfield or jcombobox with the same name but with textfield instead of label at the end
    private JLabel nameLabel; //label for who the current user for entering the data is.
    private JLabel prepLabel;//label for who prepared the current test
    private JLabel noteLabel;//label for the notes section
    private JLabel massLabel; //label for total sample mass
    private JLabel mouldLabel;//label for mould
    private JLabel ovenLabel;//label for oven
    private JLabel balanceLabel;//label for scale used
    private JLabel nineTeenLabel;//label for 19mm
    private JLabel nineTeenPercLabel;//label for percent 19mm rock
    private JLabel osContLabel;////label for os cont
    private JLabel hammerLabel;//label for hammer
    private JLabel thirtyLabel;//label for 37.5mm rock
    private JLabel thirtyPercLabel;//label for percent 37.5mm rock
    private JLabel osContMassLabel;//constant mass label
    private JLabel fractionLabel;//label for fration used
    private JLabel descLabel;//label for soil description
    private JLabel osUnderLabel;//oversize under balance
    private JLabel osVolumeLabel;//volume of oversize label
    private JLabel osDensityLabel;//density of 
    private JLabel osWetLabel;//ooversize wet weight label
    private JLabel osDryLabel;//oversize dry weight label
    private JLabel effortLabel;//effort label fr standard or modified
    private JLabel stdLabel;//label for standard compaction
    private JLabel modLabel;//label for modified compation

    private JComboBox nameBox; //combobox for user options.
    private JComboBox prepBox;//combobox for prep user options
    private JComboBox mouldBox;//all moulds in the application
    private JComboBox ovenBox;//all ovens jcombobox
    private JComboBox balanceBox;//combobox  for balances
    private JComboBox hammerBox;//combobox for hammers
    private JComboBox fractionBox;//jcombobox for fractions
    
    //these jtextfields are paired with their jlabels, they have the same name but instead of massLabel for example it will be massField
    private JTextArea noteField;//the large notes jtextarea in the gui
    private JTextField massField;
    private JTextField nineTeenField;
    private JTextField osContField;
    private JTextField thirtyField;
    private JTextField osContMassField;
    private JTextField descField;
    private JTextField osUnderField;
    private JTextField osVolumeField;
    private JTextField osDensityField;
    private JTextField osWetField;
    private JTextField osDryField;
    
    private JButton addColumn;// the buttonfor addingcolumns to the jtable
    private JButton removeColumn;// the button for removing columns from the jtable
    private JButton closeButton;//close the application button
    
    private JRadioButton stdRadio;//radio for standard compaction selection
    private JRadioButton modRadio;//radio for mod compation selection
    private ButtonGroup buttonGroup = new ButtonGroup();//button group for the buttons so only one can be selected
    
    private JSeparator line = new JSeparator(SwingConstants.HORIZONTAL);
    
    HilfWorksheet(ArrayList<Mould> aMouldList, ArrayList<String> usersList, ArrayList<String> ovenList, ArrayList<String> balanceList, ArrayList<String> hammerList, ArrayList<String> fracList)
    {
        
        mainFrame.setVisible(true);
        mainFrame.setSize(500,500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainFrame.add(mainPanel);//the main panel is a box layout, holds the gridbaglayout, flow layout and jtable descending down, so when a new layout is added it will be underneath the previous one
        
        mainPanel.add(gridBagPanel);//adds gridbag panel to this outer panel
        //creastes the gridbag layout and its settings
        
        GridBagConstraints c = new GridBagConstraints();
        gridBagPanel.setLayout(new GridBagLayout());
        gridBagPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(4,5,4,4); // padding between components
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH; // starts all components being pressed against the left side of its cell.
        
        createUserInfoAndNotes(c, usersList);//creates the testing, prep user comboboes and the large note jtextfield
        createFirstColumn(c, aMouldList, balanceList, hammerList, fracList);// creates the first column of data
        startNewColumn(c, 2);//changes the gridbagconstraints to begin at the next column
        createSecondColumn(c);//creates the second column of data
        startNewColumn(c, 4);
        createThirdColumn(c, ovenList);//creates the third column of data
        startNewRow(c);//start a new row for the add and remove column buttons
       // createSeparator(c);//creates the line between the main form (gridbaglayout) and the panel just above the jtable(flowlayout)
        createComponentsAboveTable(aMouldList);//creates a line of components for selecting some details about the compaction and add and remove table column;
        //createSeparator(c);//creates line between the add and remove columns(flow layout) and the table
        createHilfTable();//creates the hilf table for all data underneath the add and remove column flowlayout
        createSavePanel();//creates the new flowlayout with the save and close button.
    }
    
    //creates the bottom save and close panel by using the add to flowlayout overloaded methods
    //no save functionality
    private void createSavePanel()
    {
        closePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 5));//sets the flowlayout of the panel and makes the components go on the riht
        closeButton = addToFlowPanel(closePanel, closeButton, "Close");
        
        closeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();//exits the application when close button is pressed
            }
        });
        mainPanel.add(closePanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);//sets frame to middle of screen
    }
    
    //creates the hilf table, calls the constructor which has the look feel and functionality of the hilf tabl inside.
    private void createHilfTable()
    {
        hilfTable = new HilfTable();//assigns the JTable here to the hilf table class we made 
        hilfTable.setBorder(BorderFactory.createLineBorder(Color.gray));
        mainPanel.add(hilfTable);//add it to the main panel which will place it under the flow layout because it is a y axis boxlayout.
    }
    //This method just makes the jcombobox red to signify an error, possibly create overloaded version for textfields.
    private void setErrorColour(JComboBox box)
    {
        box.setBackground(Color.RED);
    }
    
    //This method creates the components above the jtable in its own layout, it also has the handlers for the buttons which adds and removes columns to the table
    //The addColumn button will also prefills some information in the JTable such as mould used, hmmer used and volume of mould.
    private void createComponentsAboveTable(ArrayList<Mould>aMouldList)
    {
        aboveTablePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));//creates the flowlayout
        aboveTablePanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        mainPanel.add(aboveTablePanel);//adds above table panel to the mainpanel wich is a boxlayout descending so will place it underneath the grudbaglayout
        
        //set of overloaded methods that enable me to put any component in and add it to the flow layout, creating the line of componenets under the separator
        addColumn = addToFlowPanel(aboveTablePanel, addColumn, "Add Column");
        removeColumn = addToFlowPanel(aboveTablePanel, removeColumn, "Remove Column");
        effortLabel = addToFlowPanel(aboveTablePanel, effortLabel, "Compactive Effort: ");
        stdLabel = addToFlowPanel(aboveTablePanel, stdLabel, "Standard");
        stdRadio = addToFlowPanel(aboveTablePanel, stdRadio);
        modLabel = addToFlowPanel(aboveTablePanel, modLabel, "Modified");
        modRadio = addToFlowPanel(aboveTablePanel, modRadio);
        buttonGroup.add(stdRadio);//adds them to button group so only one can be selected
        buttonGroup.add(modRadio);
        
        addColumn.addActionListener(new ActionListener(){//this listener calls the hilftable add column method and prefills the mould volume, hammer used and mould used data
            @Override
            public void actionPerformed(ActionEvent e) {
        
                try
                {
                    String mouldUsed = mouldBox.getSelectedItem().toString();//gets the mould used in a variable from the combobox
                    String hammerUsed = hammerBox.getSelectedItem().toString();//gets the hammer used in a variable from the combobox
                    mouldBox.setBackground(Color.LIGHT_GRAY);//sets the background colour back to default incase it was red before so it doesnt stay red
                    hammerBox.setBackground(Color.LIGHT_GRAY);

                  for(Mould mlds: aMouldList)//loops through all moulds to match the name selected so we can get that moulds volume and name to prefill
                  {
                      if(mlds.getMouldName().equals(mouldUsed))
                      {
                          hilfTable.addColumn(mouldUsed, hammerUsed, mlds.getMouldVolume());
                          break; // breaks straight away instead of continuing looping because only one mould is needed
                      }
                  }
                  mouldBox.setBackground(null);//setst he background colour to null if they were previously red from user not selecting anything
                  hammerBox.setBackground(null);
                }
                catch(NullPointerException exc)//catches the exception if the comboboxes have nothing selected and sets the jcomboboxes that arent selected to red for the user
                {
                    JOptionPane.showMessageDialog(null, "Missing Information for Prefill");//displays a message for the user
                    if(mouldBox.getSelectedIndex() == -1)
                    {
                        setErrorColour(mouldBox);//sets the colour to red of the combobox  that wasnt selecting
                    }
                    if(hammerBox.getSelectedIndex() == -1)
                    {
                        setErrorColour(hammerBox);
                    }
                }
            }
        });
        
        //calls the remove column method which removes a column off the end of the jtable
        removeColumn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                hilfTable.removeColumn();
            }
        });
    }
    
    //the following 3 are overloaded methods that just add any component to the flow layout, we can put jbutton or jlabel in and it will use the correct method.
    //it takes in the panel to add the compoennt, the actual component(Had to do this so the input parameters were different otherwise overloading wouldnt work)
    //and the text that goes into the compoennt
    //for jlabel
    private JLabel addToFlowPanel(JPanel flowPanel, JLabel labelName, String labelText)
    {
        labelName = new JLabel(labelText);
        flowPanel.add(labelName);
        
        return labelName;
    }
    //for jbuttons
    private JButton addToFlowPanel(JPanel flowPanel, JButton buttonName, String labelText)
    {
        buttonName = new JButton(labelText);
        flowPanel.add(buttonName);
        
        return buttonName;
    }
    //for radiobutton
    private JRadioButton addToFlowPanel(JPanel flowPanel, JRadioButton radioName)
    {
        radioName = new JRadioButton();
        flowPanel.add(radioName);
        
        return radioName;
        
    }

    //calculates the rock volume and density depending on what mass was retained on a certain seive and also the mass of the rock under the water
    //it calculates rock volume and then density of rock and fills in the uneditable textfields for the user.
    private void calcVolumeAndDensity(int massRetained, int underBalanceWeight, JTextField volumeField, JTextField densityField)
    {
        DecimalFormat df = new DecimalFormat("####0.000");
        double rockVolume = massRetained - underBalanceWeight;// getst he rock volume according to the australian standard
        double density = massRetained / rockVolume;// gets the density of the rock
        
        volumeField.setText(String.valueOf(rockVolume));// setst he volume field to the volume calculated
        densityField.setText(String.valueOf(df.format(density)));//sets the density field to the densitycalculated
        
        if(massRetained == 0)// if the massRetained is 0 that menas all other values will be 0 straight away.
        {
            volumeField.setText("0");
            densityField.setText("0");
        }
        
    }
    
    //creates the user info such as user who tested and preparation user aswell as the notes jtextfield
    private void createUserInfoAndNotes(GridBagConstraints c, ArrayList<String> usersList)
    {
        nameLabel = createNewLabelMoveRight(c, "Tested By: ");// label and corresponding comobox for the user who is testing
        nameBox = createNewBoxMoveRight(c, usersList);
        
        prepLabel = createNewLabelMoveRight(c, "Prepared By: ");//label and corresponding comobobox for user who prepared the sample.
        prepBox = createNewBoxMoveRight(c, usersList);
        
        startNewRow(c);//starts a new row underneath these 2 jcomboboxes

        //the notes will spand more than one grid making it a large area. So gridwidth is changed from 1 to 6 and then back to 1.
        noteLabel = createNewLabelMoveRight(c, "Notes: " );//create note label
        c.gridwidth = 6;//sets the gridwidth so the notes component goes over many cells in the gridbaglayout and not so its just the size of the regular text fields
        noteField = createNewTextAreaMoveRight(c);//create large textfield for notes.
        //noteField.setHorizontalAlignment(SwingConstants.LEFT);
        c.gridwidth = 1;//reset gridwidth after notes field has been added
        startNewRow(c);//starts a new row.
    }
    
    //creates the third column of data in the gridbaglayout and holds components used for a moisture content oven sample
    private void createThirdColumn(GridBagConstraints c, ArrayList<String> ovenList)
    {
        
        createLargePadding(c, 4,50,4,4);// creates large padding betweeen the secondcolumn and the third column just so it looks abit cleaner
        ovenLabel = createNewLabelMoveRight(c, "Oven No: ");//oven label and corresponding combobox
        resetPadding(c);//resets the padding temporarily so there is not a big gap between the jlabel and the corresponding tetfield
        ovenBox = createNewBoxMoveDown(c, ovenList);//populates the oven jcombobox with the list of ovens
        
        createLargePadding(c, 4,50,4,4);
        osContLabel= createNewLabelMoveRight(c, "OS Container Number: ");
        resetPadding(c);
        osContField = createNewTextFieldMoveDown(c);
        
        createLargePadding(c, 4,50,4,4);
        osContMassLabel = createNewLabelMoveRight(c, "OS Container Mass (g): ");
        resetPadding(c);
        osContMassField = createNewTextFieldMoveDown(c);
        
        createLargePadding(c, 4,50,4,4);
        osWetLabel = createNewLabelMoveRight(c, "Wet OS + Const Mass (g): ");
        resetPadding(c);
        osWetField = createNewTextFieldMoveDown(c);
        
        createLargePadding(c, 4,50,4,4);
        osDryLabel = createNewLabelMoveRight(c, "Dry OS + Const Mass (g): ");
        resetPadding(c);
        osDryField = createNewTextFieldMoveDown(c);
        
    }
    
    //creates the second column of data in the gridbaglayout which involves the sample mass, rock, rock under balance, volume and density of rock
    private void createSecondColumn(GridBagConstraints c)
    {
        massLabel = createNewLabelMoveRight(c, "Total Sample Wet Mass (g): ");//label for samplemass
        massField = createNewTextFieldMoveDown(c);//textfield for sample mass

        //To get the percentage of rock t display in the same cell, i made the textfield, fulfill only the vertical constraints
        //Then i could resize the textfield however i liked horizontally.
        nineTeenLabel = createNewLabelMoveRight(c, "Mass Retained 19.0mm (g): ");
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.WEST;//here i made it anchor to the left side of the cell so it only took the first half of the cell up. Not increasing x value cause it would jump to next cell
        
        nineTeenField = new JTextField(6);// didnt use the method i made because i didnt want x or y to increase because this will share a cell with the percent label
        nineTeenField.setHorizontalAlignment(SwingConstants.RIGHT);
        gridBagPanel.add(nineTeenField, c);
        
        c.anchor = GridBagConstraints.EAST;//anchor the nineteen percent label to the right of the same cell

        nineTeenPercLabel = createNewLabelMoveDown(c, "0.0");// label for the changing percent rock on the 19.

        //same process of fitting two components in the one cell as above. the c.fill is already vertical but the anchor needs to be changedback to west for the new jlabel
        c.anchor = GridBagConstraints.WEST;
        
        thirtyLabel = createNewLabelMoveRight(c, "Mass Retained 37.5mm (g): ");
        thirtyField = new JTextField(6);
        thirtyField.setHorizontalAlignment(SwingConstants.RIGHT);
        gridBagPanel.add(thirtyField,c);
        c.anchor = GridBagConstraints.EAST;//anchor the nineteen percent label to the right of the same cell

        thirtyPercLabel = createNewLabelMoveDown(c, "0.0");

        c.fill = GridBagConstraints.BOTH; //reset fill
        c.anchor = GridBagConstraints.WEST; // reset anchor.
        
        osUnderLabel = createNewLabelMoveRight(c, "Oversize Mass Under Balance (g): ");
        osUnderField = createNewTextFieldMoveDown(c);
        
        osVolumeLabel = createNewLabelMoveRight(c, "Oversize Volume (cm3): " );
        osVolumeField = createNewTextFieldMoveDown(c);
        osVolumeField.setEditable(false);//sets the cells to uneditable because it holds a calculation that doesnt need to be touchedby the user
        
        osDensityLabel = createNewLabelMoveRight(c, "Oversize Density (t/m3): " );
        osDensityField = createNewTextFieldMoveDown(c);
        osDensityField.setEditable(false);

        osUnderField.addFocusListener(new FocusAdapter(){// the focus listener can tell if the user is clicked into the textfield or not, once they click out it is focusLost and then the textfields underneath will update
            @Override
            public void focusLost(FocusEvent e) {
                 
                try{
                    int underBalance = Integer.parseInt(osUnderField.getText());//gets underbalance wieght used in the calculation
                    
                    if(!"".equals(nineTeenField.getText()))//if there is something typed into the ninteen field itll use nineteentextfield in calculation
                    {
                        int nineTeen = Integer.parseInt(nineTeenField.getText());//have to convert these to int to do calculations on them in the method
                        calcVolumeAndDensity(nineTeen, underBalance, osVolumeField, osDensityField);//method that calculaes the volume and density and updates the volume and density textfields with the results
                    }
                    else if(!"".equals(thirtyField.getText()))//if there is something typed into the thirty field itll use thirtytextfield in calculation
                    {
                        int thirty = Integer.parseInt(thirtyField.getText());
                        calcVolumeAndDensity(thirty, underBalance, osVolumeField, osDensityField);
                    }
                }
                catch(NumberFormatException exc)//catches the exception because after nineteenfield was being used to change the rock percentage from the other listener, it would throw this because underbalance wasnt filled in
                {
                }
            }
        });
        
        nineTeenField.addFocusListener(new FocusAdapter(){//adds listener to the ninteen rock field to change the jlabel to the rock percentage depending on the value in the field
            @Override
            public void focusLost(FocusEvent e) {
                try{
                    //gets the totalmass and rock and passes into the method to calculate total rock percentage in total mass of sample
                    double totalMass = Double.parseDouble(massField.getText());
                    double nineTeen = Double.parseDouble(nineTeenField.getText());
                    calcPercentageRock(totalMass, nineTeen, nineTeenPercLabel);
                }
                catch(NumberFormatException exc)//same here, catchs numberformat because if the user clicked off without typing anything it would throw error
                {
                    
                }

            }
        });
        
        thirtyField.addFocusListener(new FocusAdapter(){//exactly the same as the method above just for the thirty percent field.
            @Override
            public void focusLost(FocusEvent e){
                try{
                    double totalMass = Double.parseDouble(massField.getText());
                    double thirty = Double.parseDouble(thirtyField.getText());
                    calcPercentageRock(totalMass, thirty, thirtyPercLabel);
                    
                }
                catch(NumberFormatException exc)
                {
                    
                }
                
            } 
        });
    }
    
    //calculates the percentage of rock in the sample, done by taking the weight of the rock divided by the sample x 100
    private void calcPercentageRock(double totalMass, double rockMass, JLabel percLabel){
        double percentage = 0;
        DecimalFormat df = new DecimalFormat("####0.0");//decimal format so doubles are to 1 decimal place
        if(rockMass != 0.0)//cant divide by 0
        {
           percentage = (rockMass / totalMass) * 100;//work out the percentage of rock in the total sample
 
        }
        
        percLabel.setText(String.valueOf(df.format(percentage))); //sets the jlabel to the percentage
    }
    
    //creates the first column of data in the upper form of the entire frame(gridbaglayout). contains hammer used, mould, moven and sample description,
    //info in this column is used in prefilling the jtable with ould and hammer data
    private void createFirstColumn(GridBagConstraints c, ArrayList<Mould> aMouldList, ArrayList<String> balanceList, ArrayList<String> hammerList, ArrayList<String> fracList)
    {
        mouldLabel = createNewLabelMoveRight(c, "Mould Used: ");//label for mould used and corresponding jcombobox
        mouldBox = new JComboBox(aMouldList.toArray());//couldnt use method here because these are class objects not strings, moulds have other attributes to them that are also in the jtable
        mouldBox.setSelectedIndex(-1);//sets the jcombobox so it is blank
        gridBagPanel.add(mouldBox, c);
        c.gridx = 0;
        c.gridy++;

        balanceLabel = createNewLabelMoveRight(c, "Balance: ");//balance label and corresponding combobox
        balanceBox = createNewBoxMoveDown(c, balanceList);//create the jombobox and move underneath in the gridbaglayout for the next component
        
        hammerLabel = createNewLabelMoveRight(c, "Hammer No: ");
        hammerBox = createNewBoxMoveDown(c, hammerList);
        
        fractionLabel = createNewLabelMoveRight(c, "Fraction Tested: ");
        fractionBox = createNewBoxMoveDown(c, fracList);
        
        descLabel = createNewLabelMoveRight(c, "Soil Description: ");
        descField = createNewTextFieldMoveDown(c);
    }

   //starts a new row by increasing the y constraint(moving down the page) and setting the x constraint to 0 to make the component in the default spot in the cell
    private void startNewRow(GridBagConstraints c)
    {
        c.gridy++;
        c.gridx = 0; 
    }
    
    //creates a big gap between this components end (right to left) and the next components start
    private void createLargePadding(GridBagConstraints c, int paddingValueTop, int paddingValueRight, int paddingValueDown, int paddingValueLeft)
    {
        c.insets = new Insets(paddingValueTop,paddingValueRight, paddingValueDown,paddingValueLeft);
    }
    //this method resets the padding back to its default spacing value after i create a padding change.
    private void resetPadding(GridBagConstraints c)
    {
        c.insets = new Insets(4,4,4,4);
    }
    
    //This method is used to create a new column, the parameter int row number will line up with the c.grid x value. so if you pass in 2 for the row number, it will send the next component to row 2.
    //and c.gridy = 2 will always be the start of the 3 main columns of information.
    private void startNewColumn(GridBagConstraints c, int rowNumber)
    {
        c.gridy = 2;
        c.gridx = rowNumber;
    }
    
    //creates the large notes field which is a text area
    private JTextArea createNewTextAreaMoveRight(GridBagConstraints c)
    {
        JTextArea newTextArea = new JTextArea();
        //newTextField.setHorizontalAlignment(SwingConstants.RIGHT);// makes the text when written in the field type from the right of thefield
        newTextArea.setPreferredSize(new Dimension(5,50));//sets the size of the notes field
        newTextArea.setLineWrap(true);//sets so text when hitting the end of the area doesnt keep going it starts again on the next ine
        newTextArea.setBorder(BorderFactory.createLineBorder(Color.gray));//sets border to gray
        c.fill = GridBagConstraints.BOTH;
        gridBagPanel.add(newTextArea, c);
        c.gridx++;
        return newTextArea;
    }
    
    //This method creates a label, adds it to the current panel and increases the x gridbagconstraints(makes the next component be to the right which is normall a textfield)
    private JLabel createNewLabelMoveRight(GridBagConstraints c, String contents)
    {
        JLabel newLabel = new JLabel(contents); // create new jlabel
        gridBagPanel.add(newLabel, c);//add it to the panel with the gridbagconstraints
        c.gridx++;// increase gridbag x constraints so next component will be to the right of this compnenet.
        
        return newLabel;// returns the label , it gets assigned to the JLabel we want it to be, so it may be used or manipulated in the main code block
    }
    
    //creates a new text field and moves down in the gridbagconstraint ready for the next component underneath
    private JTextField createNewTextFieldMoveDown(GridBagConstraints c)
    {
        JTextField newTextField = new JTextField(7);// creates new text field, this size is overwritten by preferred size but had to be passed it to prevent errors
        newTextField.setHorizontalAlignment(SwingConstants.RIGHT);// makes the text come from right to left
        newTextField.setPreferredSize(new Dimension(30,25));// sets size of the text field
        c.fill = GridBagConstraints.BOTH;
        gridBagPanel.add(newTextField, c);
        c.gridy++; // moves down in the constraints
        c.gridx = c.gridx -1; // this needs to be done because now the next component is under the textfield but we want it to be under the jlabel
        return newTextField;
    }
    
    //creates a new jlabel and then moves underneath fior the next component
    private JLabel createNewLabelMoveDown(GridBagConstraints c, String contents)
    {
        JLabel newLabel = new JLabel(contents); // create new jlabel
        gridBagPanel.add(newLabel, c);//add it to the panel with the gridbagconstraints
        c.gridy++;// increase gridbag y constraints so next component will be on the next "row" below this component
        c.gridx = c.gridx -1;
        return newLabel;// returns the label , it gets assigned to the JLabel we want it to be, so it may be used or manipulated in the main code block
    }
    
    //creates a new combobox and moves the gridbaglayout right ready for the next component, this used in the testing user and preperation user jcomboboxes
    //right at the top of the screen in the program
    private JComboBox createNewBoxMoveRight(GridBagConstraints c, ArrayList<String> boxContents)
    {
        String contents[] = boxContents.toArray(new String[boxContents.size()]);//converts the contents of the box to array and also the same size as the arraylist passed in
        JComboBox newBox = new JComboBox(contents);//sets the contents to the jcombobox
        newBox.setSelectedIndex(-1);
        gridBagPanel.add(newBox, c);
        c.gridx++;
        
        return newBox;
    }
    //same as the method above but it moves the gridbaglayout down to the next row
    private JComboBox createNewBoxMoveDown(GridBagConstraints c, ArrayList<String> boxContents)
    {
        String contents[] = boxContents.toArray(new String[boxContents.size()]);//puts the arraylist into the jcombobox by using the.toArray and assigning the same size to that array
        JComboBox newBox = new JComboBox(contents);//puts the contents inside of the jcombobox
        newBox.setSelectedIndex(-1);//puts the default index to blank.
        gridBagPanel.add(newBox, c);//adds to the panel
        c.gridy++;// moves the next component down
        c.gridx = c.gridx -1;//then resets the x component to be in line with the previous label. If this is not done the next component will be out of line and the next jlabel will be under the previous jcombobox not the previous jlabel
        
        return newBox;
    }


}
