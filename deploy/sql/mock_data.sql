-- 清空现有数据
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE tb_user_contest;
TRUNCATE TABLE tb_contest_question;
TRUNCATE TABLE tb_question_language;
TRUNCATE TABLE tb_question_case;
TRUNCATE TABLE tb_question;
TRUNCATE TABLE tb_contest;
TRUNCATE TABLE tb_language;
TRUNCATE TABLE tb_system_user;
TRUNCATE TABLE tb_user;
SET FOREIGN_KEY_CHECKS = 1;

-- 插入系统用户数据
INSERT INTO tb_system_user (user_id, user_account, user_password, nick_name, grade, active, create_time) VALUES
(1, 'admin', '$2a$10$X/hX5k2XZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8', '系统管理员', 0, 1, NOW()),
(2, 'teacher1', '$2a$10$X/hX5k2XZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8', '张老师', 1, 1, NOW()),
(3, 'teacher2', '$2a$10$X/hX5k2XZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8', '李老师', 1, 1, NOW()),
(4, 'teacher3', '$2a$10$X/hX5k2XZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8', '王老师', 1, 1, NOW()),
(5, 'teacher4', '$2a$10$X/hX5k2XZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8', '赵老师', 1, 1, NOW()),
(6, 'teacher5', '$2a$10$X/hX5k2XZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8ZQN8', '钱老师', 1, 1, NOW());

-- 插入编程语言数据
INSERT INTO tb_language (language_id, name, is_enabled, create_by, create_time) VALUES
(1, 'Java 11', 1, 1, NOW()),
(2, 'Python 3.9', 1, 1, NOW()),
(3, 'C++17', 1, 1, NOW()),
(4, 'JavaScript (Node.js)', 1, 1, NOW()),
(5, 'Go 1.16', 1, 1, NOW()),
(6, 'Rust 1.70', 1, 1, NOW()),
(7, 'Swift 5.0', 1, 1, NOW()),
(8, 'Kotlin 1.8', 1, 1, NOW());

-- 插入题目数据
INSERT INTO tb_question (question_id, title, difficulty, content, tags, source, hint, create_by, create_time) VALUES
(1, '两数之和', 1, '给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出和为目标值 target 的那两个整数，并返回它们的数组下标。', '数组,哈希表', 'LeetCode 1', '你可以想出一个时间复杂度小于 O(n²) 的算法吗？', 1, NOW()),
(2, '最长回文子串', 2, '给你一个字符串 s，找到 s 中最长的回文子串。', '字符串,动态规划', 'LeetCode 5', '中心扩展法是一个很好的解法', 1, NOW()),
(3, '合并K个升序链表', 3, '给你一个链表数组，每个链表都已经按升序排列。请你将所有链表合并到一个升序链表中，返回合并后的链表。', '链表,堆,分治', 'LeetCode 23', '可以使用优先队列来优化', 1, NOW()),
(4, '二叉树的最大深度', 1, '给定一个二叉树，找出其最大深度。二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。', '树,深度优先搜索', 'LeetCode 104', '可以使用递归或迭代的方式解决', 1, NOW()),
(5, 'LRU缓存机制', 2, '设计和实现一个 LRU (最近最少使用) 缓存机制。', '设计,哈希表,链表', 'LeetCode 146', '使用双向链表和哈希表可以实现O(1)的操作', 1, NOW()),
(6, '字符串转整数', 2, '请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位整数。', '字符串,数学', 'LeetCode 8', '注意处理各种边界情况', 2, NOW()),
(7, '正则表达式匹配', 3, '给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 . 和 * 的正则表达式匹配。', '字符串,动态规划', 'LeetCode 10', '考虑使用动态规划解决', 2, NOW()),
(8, '盛最多水的容器', 2, '给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。', '数组,双指针', 'LeetCode 11', '使用双指针法可以优化时间复杂度', 2, NOW()),
(9, '三数之和', 2, '给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？', '数组,双指针', 'LeetCode 15', '注意去重处理', 3, NOW()),
(10, '删除链表的倒数第N个节点', 2, '给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。', '链表,双指针', 'LeetCode 19', '使用快慢指针可以一次遍历完成', 3, NOW()),
(11, '有效的括号', 1, '给定一个只包括 (，)，{，}，[，] 的字符串 s ，判断字符串是否有效。', '栈,字符串', 'LeetCode 20', '使用栈来匹配括号', 3, NOW()),
(12, '合并两个有序链表', 1, '将两个升序链表合并为一个新的 升序 链表并返回。', '链表,递归', 'LeetCode 21', '可以使用递归或迭代的方式解决', 4, NOW()),
(13, '括号生成', 2, '数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。', '字符串,回溯', 'LeetCode 22', '使用回溯法生成所有可能的组合', 4, NOW()),
(14, 'K个一组翻转链表', 3, '给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。', '链表,递归', 'LeetCode 25', '注意处理不足k个节点的情况', 4, NOW()),
(15, '移除重复元素', 1, '给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。', '数组,双指针', 'LeetCode 27', '使用双指针可以原地修改数组', 5, NOW());

-- 插入题目测试用例
INSERT INTO tb_question_case (case_id, question_id, input, output, is_sample, score, create_by, create_time) VALUES
(1, 1, '[2,7,11,15]\n9', '[0,1]', 1, 10, 1, NOW()),
(2, 1, '[3,2,4]\n6', '[1,2]', 2, 10, 1, NOW()),
(3, 1, '[3,3]\n6', '[0,1]', 0, 10, 1, NOW()),
(4, 2, '"babad"', '"bab"', 1, 10, 1, NOW()),
(5, 2, '"cbbd"', '"bb"', 2, 10, 1, NOW()),
(6, 3, '[[1,4,5],[1,3,4],[2,6]]', '[1,1,2,3,4,4,5,6]', 1, 10, 1, NOW()),
(7, 4, '[3,9,20,null,null,15,7]', '3', 1, 10, 1, NOW()),
(8, 5, '["LRUCache","put","put","get","put","get","put","get","get","get"]\n[[2],[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]', '[null,null,null,1,null,-1,null,-1,3,4]', 1, 10, 1, NOW()),
(9, 6, '"42"', '42', 1, 10, 2, NOW()),
(10, 6, '"   -42"', '-42', 2, 10, 2, NOW()),
(11, 7, '"aa"\n"a"', 'false', 1, 10, 2, NOW()),
(12, 7, '"aa"\n"a*"', 'true', 2, 10, 2, NOW()),
(13, 8, '[1,8,6,2,5,4,8,3,7]', '49', 1, 10, 2, NOW()),
(14, 9, '[-1,0,1,2,-1,-4]', '[[-1,-1,2],[-1,0,1]]', 1, 10, 2, NOW()),
(15, 10, '[1,2,3,4,5]\n2', '[1,2,3,5]', 1, 10, 3, NOW()),
(16, 11, '"()"', 'true', 1, 10, 3, NOW()),
(17, 11, '"()[]{}"', 'true', 2, 10, 3, NOW()),
(18, 12, '[1,2,4]\n[1,3,4]', '[1,1,2,3,4,4]', 1, 10, 3, NOW()),
(19, 13, '3', '["((()))","(()())","(())()","()(())","()()()"]', 1, 10, 4, NOW()),
(20, 14, '[1,2,3,4,5]\n2', '[2,1,4,3,5]', 1, 10, 4, NOW()),
(21, 15, '[3,2,2,3]\n3', '2', 1, 10, 5, NOW());

-- 插入题目语言限制
INSERT INTO tb_question_language (question_language_id, question_id, language_id, time_limit, space_limit, default_code, main_func, create_by, create_time) VALUES
(1, 1, 1, 1000, 256, 'public class Solution {\n    public int[] twoSum(int[] nums, int target) {\n        // TODO: 实现你的代码\n        return new int[0];\n    }\n}', 'public static void main(String[] args) {\n    Solution solution = new Solution();\n    int[] nums = {2, 7, 11, 15};\n    int target = 9;\n    int[] result = solution.twoSum(nums, target);\n    System.out.println(Arrays.toString(result));\n}', 1, NOW()),
(2, 1, 2, 1000, 256, 'def twoSum(nums, target):\n    # TODO: 实现你的代码\n    pass', 'if __name__ == "__main__":\n    nums = [2, 7, 11, 15]\n    target = 9\n    result = twoSum(nums, target)\n    print(result)', 1, NOW()),
(3, 2, 1, 2000, 512, 'public class Solution {\n    public String longestPalindrome(String s) {\n        // TODO: 实现你的代码\n        return "";\n    }\n}', 'public static void main(String[] args) {\n    Solution solution = new Solution();\n    String s = "babad";\n    String result = solution.longestPalindrome(s);\n    System.out.println(result);\n}', 1, NOW()),
(4, 2, 2, 2000, 512, 'def longestPalindrome(s):\n    # TODO: 实现你的代码\n    pass', 'if __name__ == "__main__":\n    s = "babad"\n    result = longestPalindrome(s)\n    print(result)', 1, NOW()),
(5, 3, 1, 3000, 1024, 'public class Solution {\n    public ListNode mergeKLists(ListNode[] lists) {\n        // TODO: 实现你的代码\n        return null;\n    }\n}', 'public static void main(String[] args) {\n    Solution solution = new Solution();\n    ListNode[] lists = new ListNode[3];\n    // TODO: 初始化链表数组\n    ListNode result = solution.mergeKLists(lists);\n    // TODO: 打印结果\n}', 2, NOW()),
(6, 3, 2, 3000, 1024, 'def mergeKLists(lists):\n    # TODO: 实现你的代码\n    pass', 'if __name__ == "__main__":\n    lists = []\n    # TODO: 初始化链表数组\n    result = mergeKLists(lists)\n    # TODO: 打印结果', 2, NOW());

-- 插入竞赛数据
INSERT INTO tb_contest (contest_id, title, description, start_time, end_time, status, allowed_languages, create_by, create_time) VALUES
(1, '2024春季算法竞赛', '本次竞赛包含5道算法题目，难度从简单到困难不等。', '2024-03-01 09:00:00', '2024-03-01 12:00:00', 1, '1,2,3', 1, NOW()),
(2, '2024春季编程马拉松', '24小时不间断编程挑战，包含10道题目。', '2024-04-01 00:00:00', '2024-04-02 00:00:00', 1, '1,2,3,4,5', 1, NOW()),
(3, '2024校际算法邀请赛', '面向全国高校的算法竞赛，题目难度适中。', '2024-05-01 09:00:00', '2024-05-01 17:00:00', 0, '1,2,3', 1, NOW()),
(4, '2024暑期算法训练营', '为期一个月的算法训练营，每天一道题目。', '2024-07-01 00:00:00', '2024-07-31 23:59:59', 1, '1,2,3,4,5,6,7,8', 2, NOW()),
(5, '2024秋季编程挑战赛', '面向在校学生的编程挑战赛，题目难度适中。', '2024-09-01 09:00:00', '2024-09-01 18:00:00', 0, '1,2,3,4', 2, NOW()),
(6, '2024冬季算法大赛', '年度最重要的算法竞赛，题目难度较高。', '2024-12-01 09:00:00', '2024-12-01 18:00:00', 0, '1,2,3,4,5,6,7,8', 3, NOW()),
(7, '2024新生编程大赛', '面向大一新生的编程比赛，题目难度较低。', '2024-10-01 09:00:00', '2024-10-01 17:00:00', 0, '1,2,3', 3, NOW()),
(8, '2024研究生算法竞赛', '面向研究生的算法竞赛，题目难度较高。', '2024-11-01 09:00:00', '2024-11-01 18:00:00', 0, '1,2,3,4,5', 4, NOW()),
(9, '2024算法思维挑战赛', '注重算法思维和解题技巧的比赛。', '2024-03-15 09:00:00', '2024-03-15 17:00:00', 1, '1,2,3,4', 1, NOW()),
(10, '2024数据结构专项赛', '专注于数据结构应用的比赛。', '2024-04-15 09:00:00', '2024-04-15 17:00:00', 1, '1,2,3', 2, NOW()),
(11, '2024动态规划专题赛', '专注于动态规划算法的比赛。', '2024-05-15 09:00:00', '2024-05-15 17:00:00', 0, '1,2,3,4,5', 2, NOW()),
(12, '2024图论算法挑战赛', '专注于图论算法的比赛。', '2024-06-01 09:00:00', '2024-06-01 17:00:00', 0, '1,2,3', 3, NOW()),
(13, '2024字符串处理大赛', '专注于字符串处理算法的比赛。', '2024-06-15 09:00:00', '2024-06-15 17:00:00', 0, '1,2,3,4', 3, NOW()),
(14, '2024数学建模编程赛', '结合数学建模的编程比赛。', '2024-07-15 09:00:00', '2024-07-15 17:00:00', 1, '1,2,3,4,5,6', 4, NOW()),
(15, '2024人工智能算法赛', '专注于AI相关算法的比赛。', '2024-08-01 09:00:00', '2024-08-01 17:00:00', 1, '1,2,3,4,5', 4, NOW()),
(16, '2024网络安全编程赛', '专注于网络安全相关算法的比赛。', '2024-08-15 09:00:00', '2024-08-15 17:00:00', 0, '1,2,3,4', 5, NOW()),
(17, '2024区块链算法挑战赛', '专注于区块链相关算法的比赛。', '2024-09-15 09:00:00', '2024-09-15 17:00:00', 0, '1,2,3,4,5', 5, NOW()),
(18, '2024云计算编程大赛', '专注于云计算相关算法的比赛。', '2024-10-15 09:00:00', '2024-10-15 17:00:00', 0, '1,2,3,4,5,6', 5, NOW()),
(19, '2024大数据处理挑战赛', '专注于大数据处理算法的比赛。', '2024-11-15 09:00:00', '2024-11-15 17:00:00', 0, '1,2,3,4,5', 6, NOW()),
(20, '2024物联网算法大赛', '专注于物联网相关算法的比赛。', '2024-12-15 09:00:00', '2024-12-15 17:00:00', 0, '1,2,3,4', 6, NOW()),
(21, '2024移动应用开发赛', '专注于移动应用开发算法的比赛。', '2024-03-20 09:00:00', '2024-03-20 17:00:00', 1, '1,2,3,4,5,6,7,8', 1, NOW()),
(22, '2024Web全栈开发赛', '专注于Web开发相关算法的比赛。', '2024-04-20 09:00:00', '2024-04-20 17:00:00', 1, '1,2,3,4,5', 2, NOW()),
(23, '2024游戏开发算法赛', '专注于游戏开发相关算法的比赛。', '2024-05-20 09:00:00', '2024-05-20 17:00:00', 0, '1,2,3,4', 2, NOW()),
(24, '2024嵌入式系统赛', '专注于嵌入式系统相关算法的比赛。', '2024-06-20 09:00:00', '2024-06-20 17:00:00', 0, '1,2,3', 3, NOW()),
(25, '2024操作系统算法赛', '专注于操作系统相关算法的比赛。', '2024-07-20 09:00:00', '2024-07-20 17:00:00', 0, '1,2,3,4', 3, NOW()),
(26, '2024数据库优化赛', '专注于数据库相关算法的比赛。', '2024-08-20 09:00:00', '2024-08-20 17:00:00', 1, '1,2,3,4,5', 4, NOW()),
(27, '2024网络协议算法赛', '专注于网络协议相关算法的比赛。', '2024-09-20 09:00:00', '2024-09-20 17:00:00', 1, '1,2,3,4', 4, NOW()),
(28, '2024编译器设计赛', '专注于编译器相关算法的比赛。', '2024-10-20 09:00:00', '2024-10-20 17:00:00', 0, '1,2,3,4,5', 5, NOW()),
(29, '2024虚拟机优化赛', '专注于虚拟机相关算法的比赛。', '2024-11-20 09:00:00', '2024-11-20 17:00:00', 0, '1,2,3,4', 5, NOW()),
(30, '2024系统架构设计赛', '专注于系统架构相关算法的比赛。', '2024-12-20 09:00:00', '2024-12-20 17:00:00', 0, '1,2,3,4,5,6', 5, NOW());

-- 插入竞赛题目关联
INSERT INTO tb_contest_question (contest_question_id, contest_id, question_id, display_order, display_title, score, create_by, create_time) VALUES
(1, 1, 1, 1, 'A. 两数之和', 10, 1, NOW()),
(2, 1, 2, 2, 'B. 最长回文子串', 20, 1, NOW()),
(3, 1, 3, 3, 'C. 合并K个升序链表', 30, 1, NOW()),
(4, 2, 4, 1, 'A. 二叉树的最大深度', 15, 1, NOW()),
(5, 2, 5, 2, 'B. LRU缓存机制', 25, 1, NOW()),
(6, 3, 6, 1, 'A. 字符串转整数', 15, 2, NOW()),
(7, 3, 7, 2, 'B. 正则表达式匹配', 25, 2, NOW()),
(8, 4, 8, 1, 'A. 盛最多水的容器', 20, 2, NOW()),
(9, 4, 9, 2, 'B. 三数之和', 30, 2, NOW()),
(10, 5, 10, 1, 'A. 删除链表的倒数第N个节点', 20, 2, NOW()),
(11, 5, 11, 2, 'B. 有效的括号', 15, 2, NOW()),
(12, 6, 12, 1, 'A. 合并两个有序链表', 15, 3, NOW()),
(13, 6, 13, 2, 'B. 括号生成', 25, 3, NOW()),
(14, 7, 14, 1, 'A. K个一组翻转链表', 30, 3, NOW()),
(15, 8, 15, 1, 'A. 移除重复元素', 10, 4, NOW()),
(16, 9, 1, 1, 'A. 两数之和', 15, 1, NOW()),
(17, 9, 2, 2, 'B. 最长回文子串', 25, 1, NOW()),
(18, 10, 3, 1, 'A. 合并K个升序链表', 20, 2, NOW()),
(19, 10, 4, 2, 'B. 二叉树的最大深度', 15, 2, NOW()),
(20, 11, 5, 1, 'A. LRU缓存机制', 20, 2, NOW()),
(21, 11, 6, 2, 'B. 字符串转整数', 15, 2, NOW()),
(22, 12, 7, 1, 'A. 正则表达式匹配', 25, 3, NOW()),
(23, 12, 8, 2, 'B. 盛最多水的容器', 20, 3, NOW()),
(24, 13, 9, 1, 'A. 三数之和', 20, 3, NOW()),
(25, 13, 10, 2, 'B. 删除链表的倒数第N个节点', 15, 3, NOW()),
(26, 14, 11, 1, 'A. 有效的括号', 15, 4, NOW()),
(27, 14, 12, 2, 'B. 合并两个有序链表', 15, 4, NOW()),
(28, 15, 13, 1, 'A. 括号生成', 20, 4, NOW()),
(29, 15, 14, 2, 'B. K个一组翻转链表', 25, 4, NOW()),
(30, 16, 15, 1, 'A. 移除重复元素', 10, 5, NOW()),
(31, 16, 1, 2, 'B. 两数之和', 15, 5, NOW()),
(32, 17, 2, 1, 'A. 最长回文子串', 20, 5, NOW()),
(33, 17, 3, 2, 'B. 合并K个升序链表', 25, 5, NOW()),
(34, 18, 4, 1, 'A. 二叉树的最大深度', 15, 5, NOW()),
(35, 18, 5, 2, 'B. LRU缓存机制', 20, 5, NOW()),
(36, 19, 6, 1, 'A. 字符串转整数', 15, 6, NOW()),
(37, 19, 7, 2, 'B. 正则表达式匹配', 25, 6, NOW()),
(38, 20, 8, 1, 'A. 盛最多水的容器', 20, 6, NOW()),
(39, 20, 9, 2, 'B. 三数之和', 20, 6, NOW()),
(40, 21, 10, 1, 'A. 删除链表的倒数第N个节点', 15, 1, NOW()),
(41, 21, 11, 2, 'B. 有效的括号', 15, 1, NOW()),
(42, 22, 12, 1, 'A. 合并两个有序链表', 15, 2, NOW()),
(43, 22, 13, 2, 'B. 括号生成', 20, 2, NOW()),
(44, 23, 14, 1, 'A. K个一组翻转链表', 25, 2, NOW()),
(45, 23, 15, 2, 'B. 移除重复元素', 10, 2, NOW()),
(46, 24, 1, 1, 'A. 两数之和', 15, 3, NOW()),
(47, 24, 2, 2, 'B. 最长回文子串', 20, 3, NOW()),
(48, 25, 3, 1, 'A. 合并K个升序链表', 25, 3, NOW()),
(49, 25, 4, 2, 'B. 二叉树的最大深度', 15, 3, NOW()),
(50, 26, 5, 1, 'A. LRU缓存机制', 20, 4, NOW()),
(51, 26, 6, 2, 'B. 字符串转整数', 15, 4, NOW()),
(52, 27, 7, 1, 'A. 正则表达式匹配', 25, 4, NOW()),
(53, 27, 8, 2, 'B. 盛最多水的容器', 20, 4, NOW()),
(54, 28, 9, 1, 'A. 三数之和', 20, 5, NOW()),
(55, 28, 10, 2, 'B. 删除链表的倒数第N个节点', 15, 5, NOW()),
(56, 29, 11, 1, 'A. 有效的括号', 15, 5, NOW()),
(57, 29, 12, 2, 'B. 合并两个有序链表', 15, 5, NOW()),
(58, 30, 13, 1, 'A. 括号生成', 20, 5, NOW()),
(59, 30, 14, 2, 'B. K个一组翻转链表', 25, 5, NOW());

-- 插入用户数据
INSERT INTO tb_user (user_id, nick_name, head_image, sex, phone, email, school_name, major_name, introduce, status, create_by, create_time) VALUES
(1, '张三', 'https://example.com/avatar1.jpg', 1, '13800138001', 'zhangsan@example.com', '北京大学', '计算机科学', '热爱编程，喜欢算法', 1, 1, NOW()),
(2, '李四', 'https://example.com/avatar2.jpg', 1, '13800138002', 'lisi@example.com', '清华大学', '软件工程', 'ACM选手，热爱竞赛', 1, 1, NOW()),
(3, '王五', 'https://example.com/avatar3.jpg', 2, '13800138003', 'wangwu@example.com', '浙江大学', '人工智能', '机器学习爱好者', 1, 1, NOW()),
(4, '赵六', 'https://example.com/avatar4.jpg', 1, '13800138004', 'zhaoliu@example.com', '复旦大学', '计算机科学', '全栈开发工程师', 1, 1, NOW()),
(5, '钱七', 'https://example.com/avatar5.jpg', 2, '13800138005', 'qianqi@example.com', '上海交通大学', '软件工程', '前端开发工程师', 1, 1, NOW()),
(6, '孙八', 'https://example.com/avatar6.jpg', 1, '13800138006', 'sunba@example.com', '南京大学', '人工智能', '深度学习研究者', 1, 1, NOW()),
(7, '周九', 'https://example.com/avatar7.jpg', 2, '13800138007', 'zhoujiu@example.com', '武汉大学', '计算机科学', '后端开发工程师', 1, 1, NOW()),
(8, '吴十', 'https://example.com/avatar8.jpg', 1, '13800138008', 'wushi@example.com', '华中科技大学', '软件工程', 'DevOps工程师', 1, 1, NOW()),
(9, '郑十一', 'https://example.com/avatar9.jpg', 2, '13800138009', 'zhengshiyi@example.com', '中山大学', '计算机科学', '算法工程师', 1, 1, NOW()),
(10, '王十二', 'https://example.com/avatar10.jpg', 1, '13800138010', 'wangshier@example.com', '四川大学', '软件工程', '测试工程师', 1, 1, NOW());

-- 插入用户竞赛关联
INSERT INTO tb_user_contest (user_contest_id, user_id, contest_id, score, contest_rank, create_by, create_time) VALUES
(1, 1, 1, 60, 1, 1, NOW()),
(2, 2, 1, 50, 2, 1, NOW()),
(3, 3, 1, 40, 3, 1, NOW()),
(4, 1, 2, 35, 1, 1, NOW()),
(5, 2, 2, 30, 2, 1, NOW()),
(6, 4, 1, 55, 4, 1, NOW()),
(7, 5, 1, 45, 5, 1, NOW()),
(8, 6, 2, 25, 3, 1, NOW()),
(9, 7, 2, 20, 4, 1, NOW()),
(10, 8, 2, 15, 5, 1, NOW()),
(11, 9, 3, 40, 1, 2, NOW()),
(12, 10, 3, 35, 2, 2, NOW()),
(13, 1, 3, 30, 3, 2, NOW()),
(14, 2, 4, 45, 1, 2, NOW()),
(15, 3, 4, 40, 2, 2, NOW()),
(16, 4, 4, 35, 3, 2, NOW()),
(17, 5, 5, 35, 1, 2, NOW()),
(18, 6, 5, 30, 2, 2, NOW()),
(19, 7, 6, 25, 1, 3, NOW()),
(20, 8, 6, 20, 2, 3, NOW()),
(21, 9, 7, 30, 1, 3, NOW()),
(22, 10, 7, 25, 2, 3, NOW()),
(23, 1, 8, 10, 1, 4, NOW()),
(24, 2, 8, 8, 2, 4, NOW()); 