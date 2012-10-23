package me.smecsia.smartfox.example.common;

import com.smartfoxserver.v2.exceptions.IErrorCode;

/**
 *
 * @author Ilya Sadykov
 *         Date: 09.10.12
 *         Time: 18:51
 */
public class ErrorCode implements IErrorCode {

    private short errorCode = 0;

    public ErrorCode(Integer errorCode) {
        this.errorCode = errorCode.shortValue();
    }

    public ErrorCode(short errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public short getId() {
        return (short) errorCode;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof ErrorCode && ((ErrorCode) o).getId() == getId());
    }

    public static IErrorCode valueOf(String message) {
        return new ErrorCode(Integer.valueOf(message));
    }
}
