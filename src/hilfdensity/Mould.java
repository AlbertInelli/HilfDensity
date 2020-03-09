package hilfdensity;

import java.util.Date;

public class Mould {
    private String mouldName; //name of the compaction mould
    private int mouldWeight; //weight of the mould, the geotech weighs the mould before testing
    private int mouldVolume; //volume of the mould, maybe change after calibration
    private Date lastCalibration; //calibration date of the mould, after alot of use it may change volume,
    private String mouldType;//1litre mould , 2litre mould or 2.4l mould
    
    Mould(String mouldName, int mouldWeight, int mouldVolume, Date lastCalibration, String mouldType)
    {
        this.mouldName = mouldName;
        this.mouldWeight = mouldWeight;
        this.mouldVolume = mouldVolume;
        this.lastCalibration = lastCalibration;
        this.mouldType = mouldType;
    }

    public String getMouldName() {
        return mouldName;
    }

    public void setMouldName(String mouldName) {
        this.mouldName = mouldName;
    }

    public int getMouldWeight() {
        return mouldWeight;
    }

    public void setMouldWeight(int mouldWeight) {
        this.mouldWeight = mouldWeight;
    }

    public int getMouldVolume() {
        return mouldVolume;
    }

    public void setMouldVolume(int mouldVolume) {
        this.mouldVolume = mouldVolume;
    }

    public Date getLastCalibration() {
        return lastCalibration;
    }

    public void setLastCalibration(Date lastCalibration) {
        this.lastCalibration = lastCalibration;
    }

    public String getMouldType() {
        return mouldType;
    }

    public void setMouldType(String mouldType) {
        this.mouldType = mouldType;
    }
    
    @Override
    public String toString()
    {
        return getMouldName();
    }
    
}
