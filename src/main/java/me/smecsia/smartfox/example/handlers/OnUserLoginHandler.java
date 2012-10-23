package me.smecsia.smartfox.example.handlers;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.exceptions.SFSErrorData;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.exceptions.SFSLoginException;
import me.smecsia.smartfox.example.common.ErrorCode;
import me.smecsia.smartfox.tools.AbstractServerEventHandler;

import static me.smecsia.smartfox.example.common.Constants.Auth.LoginParam.LOGIN_TYPE;
import static me.smecsia.smartfox.example.common.Constants.Auth.LoginType.*;
import static me.smecsia.smartfox.example.common.Constants.ErrorCode.*;
import static org.apache.commons.lang.StringUtils.isEmpty;

public class OnUserLoginHandler extends AbstractServerEventHandler {

    @Override
    public void doHandle(ISFSEvent event) throws SFSException {
        String emailOrToken = (String) event.getParameter(SFSEventParam.LOGIN_NAME);
        String userPassword = (String) event.getParameter(SFSEventParam.LOGIN_PASSWORD);
        ISFSObject inputData = (ISFSObject) event.getParameter(SFSEventParam.LOGIN_IN_DATA);
        Integer loginType = inputData.getInt(LOGIN_TYPE);

        validateEmptyLoginType(loginType);

        switch (loginType) {
            case LOGIN_USING_TOKEN:
                validateEmptyUsername(emailOrToken);
                break;
            case LOGIN_USING_EMAIL:
                validateEmptyUsername(emailOrToken);
                validateEmptyPassword(userPassword);
                break;
            case LOGIN_WITH_GUEST_ACCOUNT:
                break;
            default:
                throwLoginError(LOGIN_TYPE_UNKNOWN);
        }
    }

    private void throwLoginError(ErrorCode errorCode) throws SFSLoginException {
        throw new SFSLoginException("Could not login", new SFSErrorData(errorCode));
    }

    private void validateEmptyLoginType(Integer loginType) throws SFSLoginException {
        if (loginType == null) {
            throwLoginError(LOGIN_TYPE_EMPTY);
        }
    }

    private void validateEmptyUsername(String userName) throws SFSLoginException {
        if (isEmpty(userName)) {
            throwLoginError(LOGIN_BAD_USERNAME);
        }
    }

    private void validateEmptyPassword(String password) throws SFSLoginException {
        if (isEmpty(password)) {
            throwLoginError(LOGIN_BAD_PASSWORD);
        }
    }
}
