package com.example.lottospringbatch.batch;

import com.example.lottospringbatch.entity.LottoEntity;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class JpaBulkInsertItemWriter implements ItemWriter<LottoEntity> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void write(List<? extends LottoEntity> lottoEntities) throws Exception {
        for (LottoEntity entity : lottoEntities) {
            entityManager.persist(entity);
        }
    }
}
