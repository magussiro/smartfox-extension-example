package me.smecsia.smartfox.example;

import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.SFSExtension;
import me.smecsia.smartfox.example.common.Constants;
import me.smecsia.smartfox.example.handlers.OnUserLoginHandler;
import me.smecsia.smartfox.example.handlers.PrivateMessageHandler;

/**
 * @author Ilya Sadykov
 *         Date: 23.10.12
 *         Time: 15:28
 */
public class ExampleExtension extends SFSExtension {
    @Override
    public void init() {
        addRequestHandler(Constants.Request.PRIVATE_MESSAGE, PrivateMessageHandler.class);

        addEventHandler(SFSEventType.USER_LOGIN, OnUserLoginHandler.class);
    }
}
