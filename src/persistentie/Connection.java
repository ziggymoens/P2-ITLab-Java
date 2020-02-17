package persistentie;

public class Connection {
    public static boolean ONLINE;

    public static void setONLINE(boolean ONLINE) {
        Connection.ONLINE = ONLINE;
    }

    public static boolean isONLINE() {
        return ONLINE;
    }
}
