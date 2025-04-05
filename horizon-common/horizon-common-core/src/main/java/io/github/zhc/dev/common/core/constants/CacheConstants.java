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

    public static final long REFRESH_TIME = 3;

    public final static String PHONE_CODE_KEY = "p:c:";

    public final static String CODE_TIME_KEY = "c:t:";

    public final static String CONTEST_UNFINISHED_LIST = "e:t:l"; // 未完赛竞赛列表

    public final static String EXAM_HISTORY_LIST = "e:h:l";  // 历史竞赛列表

    public final static String CONTEST_DETAIL = "e:d:";    //竞赛详情信息

    public final static String USER_EXAM_LIST = "u:e:l:";   //用户竞赛列表

    public final static String USER_DETAIL = "u:d:";   //用户详情信息

    public final static long USER_EXP = 10;
    public static final String USER_UPLOAD_TIMES_KEY = "u:u:t";

    public static final String QUESTION_LIST = "q:l";

    public static final String QUESTION_HOST_LIST = "q:h:l";

    public static final String CONTEST_QUESTION_LIST = "e:q:l:";

    public static final String USER_MESSAGE_LIST = "u:m:l:";

    public static final String MESSAGE_DETAIL = "m:d:";

    public static final String EXAM_RANK_LIST = "e:r:l:";

    public static final long DEFAULT_START = 0;

    public static final long DEFAULT_END = -1;
}
