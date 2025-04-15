package io.github.zhc.dev.system.elasticsearch;

import io.github.zhc.dev.system.model.entity.question.es.QuestionES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
/**
 * @author zhc-dev
 * @data 2025/4/15 21:27
 */
@Repository
public interface QuestionRepository extends ElasticsearchRepository<QuestionES, Long> {

}