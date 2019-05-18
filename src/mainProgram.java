public class mainProgram {

    public static void main(String[] args) {

        Member henrik = new Member(1, "Henrik Judeson", 0, "Student");
        Librarian fittLisa = new Librarian(1, "Lisa Särbarnson");
        Book gameOfThrones = new Book(100001, "Game of Thrones", 2);

        System.out.println(henrik.toString() + "\n");
        System.out.println(fittLisa.toString()+ "\n");
        System.out.println(gameOfThrones.toString());

    }
}
