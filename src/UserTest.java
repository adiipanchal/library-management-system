import dao.UserDAO;
import model.User;

public class UserTest {
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        boolean registered = dao.registerUser(new User("Test User", "test@lib.com", "MEMBER"), "pass123");
        System.out.println("Registered: " + registered);

        boolean again = dao.registerUser(new User("Test User", "test@lib.com", "MEMBER"), "pass123");
        System.out.println("Registered again: " + again);

        User ok = dao.login("test@lib.com", "pass123");
        System.out.println("Login (correct password): " + ok);

        User wrong = dao.login("test@lib.com", "wrongpass");
        System.out.println("Login (wrong password): " + wrong);

        User unknown = dao.login("nobody@lib.com", "pass123");
        System.out.println("Login (unknown email): " + unknown);
    }
}
