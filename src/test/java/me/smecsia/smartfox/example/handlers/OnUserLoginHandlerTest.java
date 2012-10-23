package me.smecsia.smartfox.example.handlers;

import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.exceptions.SFSLoginException;
import me.smecsia.smartfox.testing.AbstractServerEventHandlerTest;
import org.junit.Before;
import org.junit.Test;

import static me.smecsia.smartfox.example.common.Constants.Auth.LoginParam.LOGIN_TYPE;
import static me.smecsia.smartfox.example.common.Constants.Auth.LoginType.LOGIN_USING_EMAIL;
import static me.smecsia.smartfox.example.common.Constants.Auth.LoginType.LOGIN_WITH_GUEST_ACCOUNT;
import static me.smecsia.smartfox.example.common.Constants.ErrorCode.*;

/**
 * @author Ilya Sadykov
 *         Date: 09.10.12
 *         Time: 19:04
 */
public class OnUserLoginHandlerTest extends AbstractServerEventHandlerTest {

    User player;

    @Before
    public void prepareProfile() {
        player = player("SomeName");
    }

    @Test
    public void testLoginWithoutLoginType() {
        verifyWhen(
                eventOccurred(SFSEventType.USER_LOGIN),
                then(
                        exceptionIsRaised(SFSLoginException.class, LOGIN_TYPE_EMPTY)
                )
        );
    }

    @Test
    public void testRequestGuestToken() {
        verifyWhen(
                eventOccurred(SFSEventType.USER_LOGIN).
                        withParam(SFSEventParam.LOGIN_NAME, "").
                        withLoginInInt(LOGIN_TYPE, LOGIN_WITH_GUEST_ACCOUNT),
                then(
                        noExceptionRaised()
                )
        );
    }

    @Test
    public void testLoginWithEmptyPassword() {
        verifyWhen(
                eventOccurred(SFSEventType.USER_LOGIN).
                        withParam(SFSEventParam.LOGIN_NAME, player.getName()).
                        withLoginInInt(LOGIN_TYPE, LOGIN_USING_EMAIL),
                then(
                        exceptionIsRaised(SFSLoginException.class, LOGIN_BAD_PASSWORD)
                )
        );
    }

    @Test
    public void testLoginWithEmptyLoginName() {
        verifyWhen(
                eventOccurred(SFSEventType.USER_LOGIN).
                        withParam(SFSEventParam.LOGIN_NAME, "").
                        withLoginInInt(LOGIN_TYPE, LOGIN_USING_EMAIL),
                then(
                        exceptionIsRaised(SFSLoginException.class, LOGIN_BAD_USERNAME)
                )
        );
    }
}
