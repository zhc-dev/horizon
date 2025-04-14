package io.github.zhc.dev.friend.elasticsearch;

import io.github.zhc.dev.friend.model.entity.question.es.QuestionES;
import org.springframework.data.domain.Page;
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

    @Query("{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?1\"}}, {\"match\": {\"tags\": \"?2\"}}, {\"match\": {\"source\": \"?3\"}}, {\"match\": {\"hint\": \"?4\"}}], \"minimum_should_match\": 1, \"must\": [{\"term\": {\"difficulty\": ?5}}]}}")
    Page<QuestionES> findByTitleOrContentOrTagsOrSourceOrHintAndDifficulty(String keywordTitle, String keywordContent, String keywordTags, String keywordSource, String keywordHint, Integer difficulty, Pageable pageable);

    @Query("{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?1\"}}, {\"match\": {\"tags\": \"?2\"}}, {\"match\": {\"source\": \"?3\"}}, {\"match\": {\"hint\": \"?4\"}}], \"minimum_should_match\": 1}}")
    Page<QuestionES> findByTitleOrContentOrTagsOrSourceOrHint(String keywordTitle, String keywordContent, String keywordTags, String keywordSource, String keywordHint, Pageable pageable);
}