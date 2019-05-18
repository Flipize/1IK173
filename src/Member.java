public class Member {

    private int id;
    private String name;
    private int itemCount = 0;
    private String membershipType;
    private boolean suspended = false;

    public Member () {}

    public Member (int id, String name, int itemCount, String membershipType) {
        this.id = id;
        this.name = name;
        this.itemCount = itemCount;
        this.membershipType = membershipType;
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

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
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

    @Override
    public String toString() {
        return "Id:" + id + "\nNamn: " + name + "\nItem count: "+  itemCount + "\nMembership type: " + membershipType + "\nSuspended: " + suspended;
    }
}
