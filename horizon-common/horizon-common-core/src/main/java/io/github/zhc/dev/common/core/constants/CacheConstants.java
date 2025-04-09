package io.github.zhc.dev.common.core.constants;

/**
 * 缓存常量
 *
 * @author zhc.dev
 * @date 2025/3/27 22:23
 */
public interface CacheConstants {
    String JWT_PAYLOAD_REDIS_KEY_PREFIX = "auth:jwt:payload:user_id:";
    Long JWT_TOKEN_DEFAULT_EXPIRATION_MINUTES = 720L;
    Long JWT_TOKEN_REFRESH_THRESHOLD_MINUTES = 180L;

    long REFRESH_TIME = 3;

    String EMAIL_CODE_KEY_PREFIX = "email:code:"; // 邮箱验证码

    String EMAIL_CODE_TIMES_REDIS_KEY_PREFIX = "email:code:times:"; // 邮箱验证码发送次数

    String CONTEST_UNFINISHED_LIST = "contest:unfinished:list"; // 未完赛竞赛列表

    String EXAM_HISTORY_LIST = "contest:history:list";  // 历史竞赛列表

    String CONTEST_DETAIL_PREFIX = "contest:detail:";    //竞赛详情信息

    String USER_EXAM_LIST = "user:contest:list:";   //用户竞赛列表

    public final static String USER_DETAIL = "user:detail:";   //用户详情信息

    long USER_EXP = 10;
    String USER_UPLOAD_TIMES_KEY = "u:u:t";

    String QUESTION_LIST = "q:l";

    String QUESTION_HOST_LIST = "q:h:l";

    String CONTEST_QUESTION_LIST = "contest:question:list:";

    String USER_MESSAGE_LIST = "u:m:l:";

    String MESSAGE_DETAIL = "m:d:";

    String EXAM_RANK_LIST = "e:r:l:";

    long DEFAULT_START = 0;
    long DEFAULT_END = -1;
}
