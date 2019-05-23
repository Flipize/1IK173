import java.util.Date;

public class Suspension {

    int memberID;
    boolean suspended;
    //int numberOfSuspensions = 0;
    Date startDate;
    Date endDate;

    public Suspension () {}

    public Suspension (int memberID, boolean suspended, Date startDate, Date endDate) {
        this.memberID = memberID;
        this.suspended = suspended;
        //this.numberOfSuspensions = numberOfSuspensions;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
