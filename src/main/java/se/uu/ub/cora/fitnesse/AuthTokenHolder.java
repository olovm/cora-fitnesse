package se.uu.ub.cora.fitnesse;

public final class AuthTokenHolder {
    private static String authToken;

    public static synchronized String getAuthToken() {
        return authToken;
    }

    public static synchronized void setAuthToken(String authTokenIn) {
        authToken = authTokenIn;
    }
}
