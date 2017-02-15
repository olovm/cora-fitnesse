package se.uu.ub.cora.fitnesse;

public final class AuthTokenHolder {
    private static String authTokenForAdmin;
    private static String authTokenForUser;

    public static synchronized String getAuthTokenForAdmin() {
        return authTokenForAdmin;
    }

    public static synchronized void setAuthTokenForAdmin(String authTokenIn) {
        authTokenForAdmin = authTokenIn;
    }

    public static synchronized void setAuthTokenForUser(String authTokenIn) {
        authTokenForUser = authTokenIn;
    }

    public static String getAuthTokenForUser() {
        return authTokenForUser;
    }
}
