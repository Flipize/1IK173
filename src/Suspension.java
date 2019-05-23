import java.util.Date;

public class Suspension {

    int memberID;
    int suspensions;
    Date startDate;
    Date endDate;

    public Suspension () {}

    public Suspension (int memberID, int suspensions, Date startDate, Date endDate) {
        this.memberID = memberID;
        this.suspensions = suspensions;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
