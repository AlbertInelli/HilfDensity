package hilfdensity;
import java.util.ArrayList;
import java.util.Date;

public class HilfDensity {

    public static void main(String[] args) {
        HilfDensity initData = new HilfDensity();
        ArrayList<Mould> aMouldList = initData.getAllAMoulds(); // arraylist for all moulds
        ArrayList<String> userList = initData.getAllUsersNames();//arraylist for all users
        ArrayList<String> ovenList = initData.getAllOvens();//arraylist for all ovens
        ArrayList<String> balanceList = initData.getAllBalances();//arraylist for all balances(scales)
        ArrayList<String> hammerList = initData.getAllHammers();//arraylist for allcompaction hammers
        ArrayList<String> fracList = initData.getFractions();//arraylist for the 19 and 37.5 mm fractions   

        HilfWorksheet newWorkSheet = new HilfWorksheet(aMouldList, userList, ovenList, balanceList, hammerList, fracList);  //starts the main hilf sheet Gui
    }
    
    //the below methods are all similiar where they create and add data to an arraylist to be used in jcomboboxes in the main hilf worksheet GUI
    private ArrayList<Mould> getAllAMoulds()
    {
        ArrayList<Mould> aMouldList = new ArrayList<>(); // arraylist for all 1 litre moulds
        Date lastCalDate = new Date(2019-9-5);
        
        Mould mould1 = new Mould("AM15", 0, 995, lastCalDate, "1Litre");
        Mould mould2 = new Mould("AM21", 0, 993, lastCalDate, "1Litre");
        Mould mould3 = new Mould("AM25", 0, 991, lastCalDate,"1Litre");
        Mould mould4 = new Mould("AM27", 0, 995, lastCalDate, "1Litre");
        Mould mould5 = new Mould("AM30", 0, 997, lastCalDate, "1Litre");
        Mould mould6 = new Mould("AM40", 0, 995, lastCalDate, "1Litre");
        Mould mould7 = new Mould("AM50", 0, 991, lastCalDate, "1Litre");
        Mould mould8 = new Mould("2.4/3", 0, 1560, lastCalDate, "2.4Litre");
        Mould mould9 = new Mould("3.1/2", 0, 1780, lastCalDate, "2.4Litre");
        Mould mould10 = new Mould("Whiskers", 0, 1800, lastCalDate,"2.4Litre");
        Mould mould11 = new Mould("2.4/7", 0, 1650, lastCalDate, "2.4Litre");

        aMouldList.add(mould1);
        aMouldList.add(mould2);
        aMouldList.add(mould3);
        aMouldList.add(mould4);
        aMouldList.add(mould5);
        aMouldList.add(mould6);
        aMouldList.add(mould7);
        aMouldList.add(mould8);
        aMouldList.add(mould9);
        aMouldList.add(mould10);
        aMouldList.add(mould11);

        return aMouldList;
    }
    
    private ArrayList<String> getAllUsersNames()
    {
        ArrayList<String> userList = new ArrayList<>();
        String user1 = "Albert Iannelli";
        String user2 = "Aaron Griffiths";
        String user3 = "Sam Mahdavi";
        String user4 = "Luke Romano";
        String user5 = "Christopher Rothwell";
        String user6 = "Dillon Beltrame";
        String user7 = "Paula Vakaruru";
        
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        userList.add(user6);
        userList.add(user7);
        
        return userList;
    }
    
    private ArrayList<String> getAllOvens()
    {
        ArrayList<String> ovenList = new ArrayList<>();
        String oven1 = "W02";
        String oven2 = "X07";
        String oven3 = "W07";
        
        ovenList.add(oven1);
        ovenList.add(oven2);
        ovenList.add(oven3);
        
        return ovenList;
    }
    
    private ArrayList<String> getAllBalances()
    {
        ArrayList<String> balanceList = new ArrayList<>();
        String bal1 = "WB14";
        String bal2 = "APL-98";
        String bal3 = "WB12";
        
        balanceList.add(bal1);
        balanceList.add(bal2);
        balanceList.add(bal3);
        
        return balanceList;
    }
    
    private ArrayList<String> getAllHammers()
    {
        ArrayList<String> hammerList = new ArrayList<>();
        String hammer1 = "WCHS-05";
        String hammer2 = "WCHS-07";
        String hammer3 = "STD H4";
        String hammer4 = "STD 05";
        String hammer5 = "MOD 5";
        String hammer6 = "MOD 01";
        String hammer7 = "WCHM- 05";
        
        hammerList.add(hammer1);
        hammerList.add(hammer2);
        hammerList.add(hammer3);
        hammerList.add(hammer4);
        hammerList.add(hammer5);
        hammerList.add(hammer6);
        hammerList.add(hammer7);
        
        return hammerList;
        
    }
    
    private ArrayList<String> getFractions()
    {
        ArrayList<String> fractions = new ArrayList<>();
        String frac1 = "19mm";
        String frac2 = "37.5mm";
        fractions.add(frac1);
        fractions.add(frac2);
        
        return fractions;
    }
    
}
