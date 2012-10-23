package me.smecsia.smartfox.example.common;

/**
 * @author Ilya Sadykov
 *         Date: 21.09.12
 *         Time: 16:50
 */
public interface Constants {
    public static interface Event {
        public static final String PRIVATE_MESSAGE_SEND_ERROR = "privateMessageSendError";
        public static final String PRIVATE_MESSAGE_SEND_OK = "privateMessageSendOK";
    }

    public static interface Request {
        public static final String PRIVATE_MESSAGE = "privateMessage";
    }

    public static interface Param {
        public static final String FROM_USER_ID = "fromUserId";
        public static final String TO_USER_ID = "toUserId";
        public static final String MESSAGE = "message";
        public static final String ERROR_CODE = "errorCode";
    }

    public static interface ErrorCode {
        public static final Integer MESSAGE_CANNOT_BE_EMPTY = 5;
        public static final Integer USER_NOT_FOUND = 6;
        public static final me.smecsia.smartfox.example.common.ErrorCode LOGIN_TYPE_EMPTY = new me.smecsia.smartfox.example.common.ErrorCode(32003);
        public static final me.smecsia.smartfox.example.common.ErrorCode LOGIN_TYPE_UNKNOWN = new me.smecsia.smartfox.example.common.ErrorCode(32004);
        public static final me.smecsia.smartfox.example.common.ErrorCode LOGIN_BAD_PASSWORD = new me.smecsia.smartfox.example.common.ErrorCode(32006);
        public static final me.smecsia.smartfox.example.common.ErrorCode LOGIN_BAD_USERNAME = new me.smecsia.smartfox.example.common.ErrorCode(32007);
    }

    public static interface Auth {
        public static interface LoginType {
            public static final int LOGIN_WITH_GUEST_ACCOUNT = 0;
            public static final int LOGIN_USING_TOKEN = 1;
            public static final int LOGIN_USING_EMAIL = 2;
        }

        public static interface LoginParam {
            public static final String LOGIN_TYPE = "loginType";
        }
    }
}
