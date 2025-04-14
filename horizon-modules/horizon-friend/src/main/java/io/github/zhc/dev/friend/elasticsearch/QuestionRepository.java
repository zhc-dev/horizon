package io.github.zhc.dev.friend.elasticsearch;

import com.github.pagehelper.Page;
import io.github.zhc.dev.friend.model.entity.question.es.QuestionES;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhc-dev
 * @data 2025/4/14 21:37
 */
@Repository
public interface QuestionRepository extends ElasticsearchRepository<QuestionES, Long> {

    Page<QuestionES> findQuestionByDifficulty(Integer difficulty, Pageable pageable);

    @Query("{\"bool\": {\"should\": [{ \"match\": { \"title\": \"?0\" } },{ \"match\": { \"content\": \"?1\" } },{ \"match\": { \"source\": \"?3\" } },{ \"match\": { \"tags\": \"?4\" } },{ \"match\": { \"hint\": \"?5\" } }],\"minimum_should_match\": 1,\"must\": [{\"term\": {\"difficulty\": \"?2\"}}]}}")
    Page<QuestionES> findByTitleOrContentOrTagsOOrSourceOrHintAndDifficulty(String keywordTitle, String keywordContent, Integer difficulty, Pageable pageable);

    @Query("{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?1\"}}, {\"match\": {\"source\": \"?2\"}}, {\"match\": {\"tags\": \"?3\"}}, {\"match\": {\"hint\": \"?4\"}}], \"minimum_should_match\": 1}}")
    Page<QuestionES> findByTitleOrContentOrTagsOOrSourceOrHint(String keywordTitle, String keywordContent, String keywordSource, String keywordTags, String keywordHint, Pageable pageable);
}