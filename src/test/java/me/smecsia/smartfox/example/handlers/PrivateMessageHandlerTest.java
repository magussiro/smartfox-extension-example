package me.smecsia.smartfox.example.handlers;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import me.smecsia.smartfox.example.common.Constants;
import me.smecsia.smartfox.testing.AbstractRequestHandlerTest;
import org.junit.Test;

/**
 * Copyright (c) 2012 i-Free. All Rights Reserved.
 *
 * @author Ilya Sadykov
 *         Date: 04.10.12
 *         Time: 17:55
 */
public class PrivateMessageHandlerTest extends AbstractRequestHandlerTest {

    @Test
    public void testPrivateMessageSendError() throws Exception {
        User fromUser = player("Guest1");
        User toUser = player("Guest2");
        setPlayersInRoom(fromUser, toUser);

        verifyWhen(
                fromUser,
                doRequest().
                        putString(Constants.Param.MESSAGE, "").
                        putInt(Constants.Param.TO_USER_ID, toUser.getId()),
                then(
                        user(fromUser).
                                getResponse(Constants.Event.PRIVATE_MESSAGE_SEND_ERROR).
                                putInt(Constants.Param.TO_USER_ID, toUser.getId()).
                                putInt(Constants.Param.ERROR_CODE, Constants.ErrorCode.MESSAGE_CANNOT_BE_EMPTY)
                )
        );
    }

    @Test
    public void testPrivateMessageSendOk() throws Exception {
        User fromUser = player("Guest1");
        User toUser = player("Guest2");
        setPlayersInRoom(fromUser, toUser);

        ISFSObject params = response().
                putInt(Constants.Param.TO_USER_ID, toUser.getId()).
                putString(Constants.Param.MESSAGE, "Some message").
                putInt(Constants.Param.FROM_USER_ID, fromUser.getId()).get();

        verifyWhen(
                fromUser,

                doRequest().
                        putString(Constants.Param.MESSAGE, "Some message").
                        putInt(Constants.Param.TO_USER_ID, toUser.getId()),
                then(
                        user(toUser).
                                getResponse(Constants.Request.PRIVATE_MESSAGE).
                                putInt(Constants.Param.TO_USER_ID, toUser.getId()).
                                putString(Constants.Param.MESSAGE, "Some message").
                                putInt(Constants.Param.FROM_USER_ID, fromUser.getId()).
                                withNoUDP()
                ),

                then(user(fromUser).getResponse(Constants.Event.PRIVATE_MESSAGE_SEND_OK).set(params).withNoUDP())
        );
    }


}
