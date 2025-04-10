package io.github.zhc.dev.common.core.constants;

/**
 * 缓存常量
 *
 * @author zhc.dev
 * @date 2025/3/27 22:23
 */
public interface CacheConstants {
    // JWT相关常量
    String JWT_PAYLOAD_KEY_PREFIX = "auth:jwt:payload:user_id:";
    Long JWT_TOKEN_DEFAULT_EXPIRATION_MINUTES = 720L;
    Long JWT_TOKEN_REFRESH_THRESHOLD_MINUTES = 180L;
    long JWT_REFRESH_TIME = 3;

    // 邮箱验证码相关常量
    String EMAIL_CODE_KEY_PREFIX = "email:code:";
    String EMAIL_CODE_TIMES_KEY_PREFIX = "email:code:times:";

    // 竞赛相关常量
    String CONTEST_UNFINISHED_LIST_KEY = "contest:unfinished:list";
    String CONTEST_HISTORY_LIST_KEY = "contest:history:list";
    String CONTEST_DETAIL_KEY_PREFIX = "contest:detail:";
    String CONTEST_QUESTION_LIST_KEY_PREFIX = "contest:question:list:";
    String CONTEST_RANK_LIST_KEY_PREFIX = "contest:rank:list:";

    // 用户相关常量
    String USER_CONTEST_LIST_KEY_PREFIX = "user:contest:list:";
    String USER_DETAIL_KEY_PREFIX = "user:detail:";
    long USER_EXPIRATION_TIME = 10;
    String USER_UPLOAD_TIMES_KEY = "user:upload:times:";

    // 题目相关常量
    String QUESTION_LIST_KEY = "question:list:";
    String QUESTION_HOST_LIST_KEY = "question:host:list:";

    // 消息相关常量
    String USER_MESSAGE_LIST_KEY_PREFIX = "user:message:list:";
    String MESSAGE_DETAIL_KEY_PREFIX = "message:detail:";

    // 分页相关常量
    long DEFAULT_START = 0;
    long DEFAULT_END = -1;
}
