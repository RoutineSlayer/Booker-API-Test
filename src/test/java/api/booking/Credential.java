package api.booking;

import io.github.cdimascio.dotenv.Dotenv;

public final class Credential {
    private final static String username;
    private final static String password;

    static {
        Dotenv dotenv = Dotenv.load();
        username = dotenv.get("USER_NAME");
        password = dotenv.get("PASSWORD");
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}
