package example.com.klfpi.Data;

/**
 * Created by User on 7/30/2017.
 */

public class DataNotifyInfo {
    String FirmName;
    String Status;

    public String getFirmName() {
        return FirmName;
    }

    public void setFirmName(String firmName) {
        FirmName = firmName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public DataNotifyInfo(String firmName, String status) {
        FirmName = firmName;
        Status = status;
    }
    @Override
    public String toString() {
        return String.format(FirmName);
    }
}
