package me.smecsia.smartfox.example.handlers;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import me.smecsia.smartfox.example.common.Constants;
import me.smecsia.smartfox.tools.AbstractClientRequestHandler;
import me.smecsia.smartfox.tools.annotations.Security;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * @author Ilya Sadykov
 *         Date: 04.10.12
 *         Time: 12:51
 */
@Security(authRequired = true)
public class PrivateMessageHandler extends AbstractClientRequestHandler {

    @Override
    public void doHandle(User user, ISFSObject isfsObject) {
        Integer toUserId = isfsObject.getInt(Constants.Param.TO_USER_ID);
        String message = isfsObject.getUtfString(Constants.Param.MESSAGE);
        if (toUserId == null) {
            sendPrivateMessageError(user, Constants.ErrorCode.USER_NOT_FOUND);
        } else {
            if (isEmpty(message)) {
                sendPrivateMessageError(user, toUserId, Constants.ErrorCode.MESSAGE_CANNOT_BE_EMPTY);
            } else {
                User toUser = null;
                for (User player : getParentExtension().getParentRoom().getPlayersList()) {
                    if (player.getId() == toUserId) {
                        toUser = player;
                        break;
                    }
                }
                if (toUser != null) {
                    sendPrivateMessageOk(user, toUser, message);
                } else {
                    sendPrivateMessageError(user, toUserId, Constants.ErrorCode.USER_NOT_FOUND);
                }
            }
        }
    }

    private void sendPrivateMessageError(User fromUser, int errorCode) {
        ISFSObject data = new SFSObject();
        data.putInt(Constants.Param.ERROR_CODE, errorCode);
        this.send(Constants.Event.PRIVATE_MESSAGE_SEND_ERROR, data, fromUser);
    }

    private void sendPrivateMessageError(User fromUser, Integer toUserId, int errorCode) {
        ISFSObject data = new SFSObject();
        data.putInt(Constants.Param.ERROR_CODE, errorCode);
        data.putInt(Constants.Param.TO_USER_ID, toUserId);
        this.send(Constants.Event.PRIVATE_MESSAGE_SEND_ERROR, data, fromUser);
    }

    private void sendPrivateMessageOk(User fromUser, User toUser, String message) {
        ISFSObject data = new SFSObject();

        data.putInt(Constants.Param.FROM_USER_ID, fromUser.getId());
        data.putInt(Constants.Param.TO_USER_ID, toUser.getId());
        data.putUtfString(Constants.Param.MESSAGE, message);

        this.send(Constants.Request.PRIVATE_MESSAGE, data, toUser, false);
        this.send(Constants.Event.PRIVATE_MESSAGE_SEND_OK, data, fromUser, false);
    }

}
