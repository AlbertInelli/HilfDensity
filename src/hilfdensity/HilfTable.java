package hilfdensity;

import java.awt.Color;
import java.awt.Dimension;
import java.text.DecimalFormat;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;



public class HilfTable extends JPanel{
     private JTable hilfTable; // table for the hilf data
     private JScrollPane tableScroll;// scroll bar for the table
     private DefaultTableModel model;// table model which is used to add and remove columns and data
     private int currentColumn = 1;//column count for keeping of track of adding and removing columns
     DecimalFormat df = new DecimalFormat("####0.000");//creates a decimal format so i can get the doubles into 3 decimal places which is required for some values according to AUstralian standards
     
     HilfTable()
     {
         Object[] columnNames = {"WetDensity"};//this is not seen only used to put in default tablemodel parameters
         
         //the current permanent column data which is what will be needed for every hilf worksheet, this isset not editable and acts as the headers on left side of the table
         Object[][] data = {{"Compaction Mould"}, {"Compactor"}, {"Mass of Mould (g)"}, {"Volume of Mould"}, 
                            {"Mass of Sub-Sample"}, {"Target Moisture Content (%)"},
                            {"Target Water Change (g)"}, {"Actual Water Added"}, {"Moisture Change Achieved (%)"},
                            {"Mass of Mould & Wet Soil (g)"}, {"Mass of Wet Soil Only (g)"}, {"Wet Density"}, {"Converted Wet Density"}
         };
         
         model = new DefaultTableModel(data, columnNames){ //overides the isCellEditable making only the left cells(the row headings editable and everything else not.)
             @Override
             public boolean isCellEditable(int row, int col) {
                 return col != 0; // makes all cells except row 0 (header row) editable
            } 
         };

         hilfTable = new JTable();
         hilfTable.setModel(model);//sets the t]default table model to the hilftable
         hilfTable.setBackground(Color.WHITE);//sets background to white to its easy to see data
         hilfTable.setRowHeight(19);//sets row height so it is readable
         hilfTable.setPreferredScrollableViewportSize(new Dimension(1000, 250));//determines viewable area of table(size pretty much)
         hilfTable.setFillsViewportHeight(true);
         hilfTable.setTableHeader(null);//no table header
         tableScroll = new JScrollPane(hilfTable);//adds scrollpane to table, not really needed but just incase more columns added later for whatever reson
         add(tableScroll);//adds to the extending jpanel
         
         //adds a tablemodel listener which can listen for cell changes in the table, depending on which cell hs been tyed into we can then use those values to change other cells
         //with getValueAt i had to cast to a string and then a double, casting straight to a double wouldnt work so i used Double.parsedouble and it was solved
         model.addTableModelListener(new TableModelListener(){
             @Override
             public void tableChanged(TableModelEvent e) {
                 try{
                    //i had to declare the same variables inside the different if statements because if i declared them up the top then initilized them in the if statement, the data wouldnt stay in the local variable asoon as i clicked off the cell so other cells which used that value in a calculation would be getting empty string or 0
                   int row = e.getFirstRow();//gets the current row that the cell that has been clicked on is in. //
                   int col = e.getColumn();//gets the current column that the clicked row is in so table can differentiate between row
                   if(row == 5)//this row is for percent water calculation the tester should put in
                   {
                       String targetPerc = (String) hilfTable.getModel().getValueAt(row, col);//gets target water percentagey
                       String sampleMass =  (String) hilfTable.getModel().getValueAt(4, col);//couldnt put sample mass at the top of the function because when i pressed add column it tried to get it and throw error
                       double waterToBeAdded = Double.parseDouble(sampleMass) * (Double.parseDouble(targetPerc) / 100);//uses sample mass from the top of the function times by percent / 100 to get the water percent needed
                       hilfTable.getModel().setValueAt(waterToBeAdded, 6, col);//sets the value in the
                   }
                   if(row == 7)//for actual water added by the tester, gets the percent water the tester adds
                   {
                      String sampleMass =  (String) hilfTable.getModel().getValueAt(4, col);//gets the sub sample mass
                      String actualWater = (String) hilfTable.getModel().getValueAt(row, col);//gets the water added by the user
                      double moistureChange = (Double.parseDouble(actualWater) / Double.parseDouble(sampleMass)) * 100;//finds the percent water the user actually has put in the sample
                      hilfTable.getModel().setValueAt(df.format(moistureChange), 8, col);//adds the moisture change achieved to the moisture change achieved cell
                   }

                   if(row == 9)//if the user puts in the last cell that is needed by them (mass of mould and wet soil)
                   {
                       String mouldMass = (String) hilfTable.getModel().getValueAt(2, col);//gets the mass of the mould in the jtable
                       String mouldAndSoil = (String) hilfTable.getModel().getValueAt(row, col);//row = 9
                       String actualWater = (String) hilfTable.getModel().getValueAt(7, col);//gets the water added by the user
                       String sampleMass =  (String) hilfTable.getModel().getValueAt(4, col);//gets the sub sample mass

                       double moistureChange = (Double.parseDouble(actualWater) / (Double.parseDouble(sampleMass))) * 100;//gets the moisture change achievedby the tester

                       double wetSoilOnly = Double.parseDouble(mouldAndSoil) - Double.parseDouble(mouldMass);//takes away the mould weight from mass and soil to get soil only. Needed it double so i could get to 3 decimal places for wet density
                       hilfTable.getModel().setValueAt(wetSoilOnly, 10, col);//sets the value at the current column and row 10 which is wetsoilonly cell

                       int mouldVolume = (int) hilfTable.getModel().getValueAt(3, col); // gets the volume of the mould needed to calculate wet density
                       Double wetDensity = wetSoilOnly / mouldVolume;//gets wet density by dividing wetsoil only by mould volume
                       hilfTable.getModel().setValueAt(df.format(wetDensity), 11, col); //uses df.format to get the 3 decimal places and sets the wet density cell to the value.

                       double convertedWetDensity = (wetDensity / (100 + moistureChange)) * 100; // need to be able to append a digit onto the end of decimal place not add it
                       hilfTable.getModel().setValueAt(df.format(convertedWetDensity), 12, col); 
                }
             }
             catch(NullPointerException exc)//catches if the user misses a cell and they will have to enter it, no error message is needed just need to pevent crashing
             {
                 
             }
           }
             
         });
     }

     //this method is called from the hilfworksheet class when the add colum button is pressed
     //Simply sets the column count to + 1 (prefix) of what it is now, effectively just adding a column on the end
     public void addColumn(String mouldUsed, String hammerUsed, int mouldVolume)
     {
         if(currentColumn < 5)// maxes columns that can be made to 4(total 5 with the headers)
         {
            model.setColumnCount(++currentColumn);
         }
            
            hilfTable.getModel().setValueAt(mouldUsed, 0, currentColumn -1);//prefills the newly added column with the correct data that was passed in
            hilfTable.getModel().setValueAt(hammerUsed, 1, currentColumn -1);
            hilfTable.getModel().setValueAt(mouldVolume, 3, currentColumn - 1);           
     }
     
     //then this function does the opposite where it -1 column count so it deletes the column off the end
     public void removeColumn(){
        if(currentColumn >= 2)//stops it at 2 because column 1 is the headers and we dont want that removed
        {
           model.setColumnCount(--currentColumn);
        }
     }
     
}