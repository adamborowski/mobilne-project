package pl.adamborowski.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ItemException extends Exception {

    public ItemException(Cause cause, String message) {
        super(message);
        this.cause = cause;
    }

    @AllArgsConstructor
    public enum Cause {
        GUID_NOT_FOUND(1001),
        GUID_ALREADY_EXISTS(2001),
        NAME_ALREADY_EXISTS(2002),
        GUID_ALREADY_DELETED(3001),
        GUID_ALREADY_DELETED_NAME_RECREATED(3002);
        @Getter
        private int errorCode;
    }

    private final Cause cause;

    public int getErrorCode() {
        return cause.getErrorCode();
    }
}
