public class Member {

    private int id;
    private String name;
    private String membershipType;
    private boolean suspended = false;
    private int suspensions;

    public Member () {}

    public Member (int id, String name, String membershipType) {
        this.id = id;
        this.name = name;
        this.membershipType = membershipType;
        this.suspensions = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public int getSuspensions() {
        return suspensions;
    }

    public void setSuspensions(int suspensions) {
        this.suspensions = suspensions;
    }

    @Override
    public String toString() {
        return "Id:" + id + "\nNamn: " + name  + "\nMembership type: " + membershipType + "\nSuspended: " + suspended;
    }
}
