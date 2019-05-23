import java.time.LocalDate;

public class Suspension {

    int memberID;
    int suspensions;
    LocalDate startDate;
    LocalDate endDate;

    public Suspension () {}

    public Suspension (int memberID, int suspensions, LocalDate startDate, LocalDate endDate) {
        this.memberID = memberID;
        this.suspensions = suspensions;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public int getSuspensions() {
        return suspensions;
    }

    public void setSuspensions(int suspensions) {
        this.suspensions = suspensions;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
