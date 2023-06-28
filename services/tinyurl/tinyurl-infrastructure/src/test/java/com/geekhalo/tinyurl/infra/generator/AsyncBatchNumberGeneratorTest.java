package com.geekhalo.tinyurl.infra.generator;

import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import com.geekhalo.tinyurl.TestApplication;
import com.google.common.base.Preconditions;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("db-single-async")
@Getter
class AsyncBatchNumberGeneratorTest extends AbstractNumberGeneratorTest{

    @Autowired
    private NumberGenerator numberGenerator;

    @Override
    NumberGenerator getNumberGenerator() {
        Preconditions.checkArgument(this.numberGenerator instanceof QueueBasedAsyncNumberGenerator);
        return numberGenerator;
    }
}