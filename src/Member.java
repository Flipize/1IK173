public class Member {

    private int id;
    private String name;
    private long personalNumber;
    private String membershipType;

    public Member () {}

    public Member (int id, String name, long personalNumber, String membershipType) {
        this.id = id;
        this.name = name;
        this.personalNumber = personalNumber;
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

    public long getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(long personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }


    @Override
    public String toString() {
        return "Id:" + id + "\nNamn: " + name  + "\nPersonal Number: " + personalNumber +  "\nMembership type: " + membershipType;
    }
}
