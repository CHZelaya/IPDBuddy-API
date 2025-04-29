package oosd.sait.ipdbuddyapi.exceptions;

public class NewPasswordRequiredException extends RuntimeException {
    private final String session;
    private final String email;

    public NewPasswordRequiredException(String message,String session, String email) {
        super(message);
        this.session = session;
        this.email = email;
    }

    public String getSession() {
        return session;
    }

    public String getEmail() {
        return email;
    }

}
