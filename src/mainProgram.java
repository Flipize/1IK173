public class mainProgram {

    public static void main(String[] args) {

        Member henrik = new Member(1, "Henrik Judeson", 0, "Student");
        Librarian fittLisa = new Librarian(01, "Lisa Särbarnson");

        System.out.println(henrik.toString());
        System.out.println();
        System.out.println(fittLisa.toString());
    }
}
