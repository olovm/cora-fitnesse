package se.uu.ub.cora.fitnesse;

public final class AuthTokenHolder {
    private static String adminAuthToken;
    private static String userAuthToken;

    public AuthTokenHolder(){
        // needed by fitnesse
        super();
    }

    public static synchronized String getAdminAuthToken() {
        return adminAuthToken;
    }

    public static synchronized void setAdminAuthToken(String authTokenIn) {
        adminAuthToken = authTokenIn;
    }

    public static synchronized void setUserAuthToken(String authTokenIn) {
        userAuthToken = authTokenIn;
    }

    public static synchronized String getUserAuthToken() {
        return userAuthToken;
    }
}
