package io.github.zhc.dev.system.service.impl.question;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.security.exception.ServiceException;
import io.github.zhc.dev.system.mapper.language.LanguageMapper;
import io.github.zhc.dev.system.mapper.question.QuestionCaseMapper;
import io.github.zhc.dev.system.mapper.question.QuestionLanguageMapper;
import io.github.zhc.dev.system.mapper.question.QuestionMapper;
import io.github.zhc.dev.system.model.dto.question.*;
import io.github.zhc.dev.system.model.entity.question.Question;
import io.github.zhc.dev.system.model.entity.question.QuestionCase;
import io.github.zhc.dev.system.model.entity.question.QuestionLanguage;
import io.github.zhc.dev.system.model.vo.question.QuestionCaseVO;
import io.github.zhc.dev.system.model.vo.question.QuestionDetailVO;
import io.github.zhc.dev.system.model.vo.question.QuestionLanguageVO;
import io.github.zhc.dev.system.model.vo.question.QuestionVO;
import io.github.zhc.dev.system.service.question.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhc.dev
 * @date 2025/3/30 23:00
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionCaseMapper questionCaseMapper;

    @Resource
    QuestionLanguageMapper questionLanguageMapper;

    @Resource
    private LanguageMapper languageMapper;

    /**
     * 查询题目列表
     *
     * @param questionQueryRequest 查询请求
     * @return 题目列表
     */
    @Override
    public List<QuestionVO> list(QuestionQueryRequest questionQueryRequest) {
        // 开始分页查询,查询前会先执行count, 所以不需要再查询总数了
        PageHelper.startPage(questionQueryRequest.getPageNum(), questionQueryRequest.getPageSize());
        return questionMapper.selectQuestionList(questionQueryRequest);
    }

    /**
     * 添加题目
     *
     * @param questionAddRequest 添加请求
     * @return 添加结果(受影响的行数)
     */
    @Override
    public int add(QuestionAddRequest questionAddRequest) {
        Question question = new Question();
        BeanUtil.copyProperties(questionAddRequest, question);
        int count = questionMapper.insert(question);
        for (QuestionCaseRequest caseRequest : questionAddRequest.getCases()) {
            QuestionCase questionCase = new QuestionCase();
            BeanUtil.copyProperties(caseRequest, questionCase);
            questionCase.setQuestionId(question.getQuestionId());
            questionCaseMapper.insert(questionCase);
        }

        for (QuestionLanguageRequest languageRequest : questionAddRequest.getLanguages()) {
            QuestionLanguage questionLanguage = new QuestionLanguage();
            BeanUtil.copyProperties(languageRequest, questionLanguage);
            questionLanguage.setQuestionId(question.getQuestionId());
            questionLanguageMapper.insert(questionLanguage);
        }

        return count;
    }

    /**
     * 查询题目详情
     *
     * @param questionId 题目id
     * @return 题目详情
     */
    @Override
    public QuestionDetailVO detail(Long questionId) {
        Question question = questionMapper.selectById(questionId);
        List<QuestionCase> cases = questionCaseMapper.selectList(new LambdaQueryWrapper<QuestionCase>().eq(QuestionCase::getQuestionId, questionId));
        List<QuestionLanguage> languages = questionLanguageMapper.selectList(new LambdaQueryWrapper<QuestionLanguage>().eq(QuestionLanguage::getQuestionId, questionId));
        if (question == null || cases == null || languages == null)
            throw new ServiceException(ResultCode.FAILED_NOT_EXISTS);
        QuestionDetailVO questionDetailVO = new QuestionDetailVO();
        BeanUtil.copyProperties(question, questionDetailVO);
        List<QuestionCaseVO> casesVO = BeanUtil.copyToList(cases, QuestionCaseVO.class);
        List<QuestionLanguageVO> languageVOS = BeanUtil.copyToList(languages, QuestionLanguageVO.class);
        for (int i = 0; i < languages.size(); i++) {
            languageVOS.get(i).setName(languageMapper.selectById(languages.get(i)).getName());
        }
        questionDetailVO.setCases(casesVO);
        questionDetailVO.setLanguages(languageVOS);
        return questionDetailVO;
    }

    /**
     * 修改题目
     *
     * @param questionEditRequest 修改请求
     * @return 修改结果(受影响的行数)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int edit(QuestionEditRequest questionEditRequest) {
        Long questionId = questionEditRequest.getQuestionId();
        if (questionId == null) throw new ServiceException(ResultCode.FAILED_PARAMS_VALIDATE);

        // 1. 获取原有题目信息
        Question oldQuestion = questionMapper.selectById(questionId);
        if (oldQuestion == null) throw new ServiceException(ResultCode.FAILED_NOT_EXISTS);

        // 2. 更新题目基本信息
        BeanUtil.copyProperties(questionEditRequest, oldQuestion);
        int result = questionMapper.updateById(oldQuestion);

        // 3. 处理测试用例
        handleTestCases(questionId, questionEditRequest.getCases());

        // 4. 处理语言配置
        handleLanguageConfigs(questionId, questionEditRequest.getLanguages());
        return result;
    }

    /**
     * 处理测试用例的新增、修改和删除
     */
    private void handleTestCases(Long questionId, List<QuestionCaseRequest> newCases) {
        // 获取所有原有测试用例
        List<QuestionCase> existingCases = questionCaseMapper.selectList(
                new LambdaQueryWrapper<QuestionCase>()
                        .eq(QuestionCase::getQuestionId, questionId));

        // 将已有的测试用例按caseId分组
        Map<Long, QuestionCase> existingCaseMap = new HashMap<>();
        for (QuestionCase existingCase : existingCases) {
            if (existingCase.getCaseId() != null) {
                existingCaseMap.put(existingCase.getCaseId(), existingCase);
            }
        }

        // 分类处理测试用例
        List<QuestionCaseRequest> casesToUpdate = new ArrayList<>();
        List<QuestionCaseRequest> casesToInsert = new ArrayList<>();

        for (QuestionCaseRequest caseReq : newCases) {
            if (caseReq.getCaseId() != null && existingCaseMap.containsKey(caseReq.getCaseId())) {
                // 已存在的测试用例 - 需要更新
                casesToUpdate.add(caseReq);
            } else {
                // 新的测试用例 - 需要插入
                casesToInsert.add(caseReq);
            }
        }

        // 收集要保留的测试用例ID
        Set<Long> caseIdsToKeep = casesToUpdate.stream()
                .map(QuestionCaseRequest::getCaseId)
                .collect(Collectors.toSet());

        // 找出要删除的测试用例ID
        Set<Long> caseIdsToRemove = existingCaseMap.keySet().stream()
                .filter(id -> !caseIdsToKeep.contains(id))
                .collect(Collectors.toSet());

        // 1. 删除不再需要的测试用例
        if (!caseIdsToRemove.isEmpty()) {
            for (Long caseId : caseIdsToRemove) {
                questionCaseMapper.deleteById(caseId);
            }
        }

        // 2. 更新已有的测试用例
        for (QuestionCaseRequest caseRequest : casesToUpdate) {
            QuestionCase questionCase = new QuestionCase();
            BeanUtil.copyProperties(caseRequest, questionCase);
            questionCase.setQuestionId(questionId);
            questionCaseMapper.updateById(questionCase);
        }

        // 3. 插入新的测试用例
        for (QuestionCaseRequest caseRequest : casesToInsert) {
            QuestionCase questionCase = new QuestionCase();
            BeanUtil.copyProperties(caseRequest, questionCase);
            questionCase.setQuestionId(questionId);
            questionCase.setCaseId(null); // 确保是新增
            questionCaseMapper.insert(questionCase);
        }
    }
    /**
     * 处理语言配置的新增、修改和删除
     */
    private void handleLanguageConfigs(Long questionId, List<QuestionLanguageRequest> newLanguages) {
        // 获取所有原有语言配置
        List<QuestionLanguage> existingLanguages = questionLanguageMapper.selectList(
                new LambdaQueryWrapper<QuestionLanguage>()
                        .eq(QuestionLanguage::getQuestionId, questionId));

        // 创建语言ID到配置的映射（使用language_id而非questionLanguageId）
        Map<Long, QuestionLanguage> existingLanguageMap = existingLanguages.stream()
                .collect(Collectors.toMap(QuestionLanguage::getLanguageId, lang -> lang));

        // 收集请求中的语言ID
        Set<Long> requestLanguageIds = newLanguages.stream()
                .map(QuestionLanguageRequest::getLanguageId)
                .collect(Collectors.toSet());

        // 1. 处理删除的语言配置 - 找出已有配置中不在请求列表中的语言ID
        Set<Long> languageIdsToRemove = existingLanguageMap.keySet().stream()
                .filter(langId -> !requestLanguageIds.contains(langId))
                .collect(Collectors.toSet());

        if (!languageIdsToRemove.isEmpty()) {
            for (Long langId : languageIdsToRemove) {
                QuestionLanguage langToRemove = existingLanguageMap.get(langId);
                questionLanguageMapper.deleteById(langToRemove.getQuestionLanguageId());
            }
        }

        // 2. 处理新增和更新的语言配置
        for (QuestionLanguageRequest languageRequest : newLanguages) {
            Long languageId = languageRequest.getLanguageId();

            if (existingLanguageMap.containsKey(languageId)) {
                // 已存在该语言的配置，执行更新
                QuestionLanguage existingLang = existingLanguageMap.get(languageId);

                // 确保使用现有的questionLanguageId
                Long existingId = existingLang.getQuestionLanguageId();

                QuestionLanguage questionLanguage = new QuestionLanguage();
                BeanUtil.copyProperties(languageRequest, questionLanguage);
                questionLanguage.setQuestionId(questionId);
                questionLanguage.setQuestionLanguageId(existingId);

                questionLanguageMapper.updateById(questionLanguage);
            } else {
                // 不存在该语言的配置，执行新增
                QuestionLanguage questionLanguage = new QuestionLanguage();
                BeanUtil.copyProperties(languageRequest, questionLanguage);
                questionLanguage.setQuestionId(questionId);

                questionLanguageMapper.insert(questionLanguage);
            }
        }
    }

    @Override
    public int delete(Long questionId) {
        Question question = questionMapper.selectById(questionId);
        if (question == null) throw new ServiceException(ResultCode.FAILED_NOT_EXISTS);
        return questionMapper.deleteById(questionId);
    }

}
