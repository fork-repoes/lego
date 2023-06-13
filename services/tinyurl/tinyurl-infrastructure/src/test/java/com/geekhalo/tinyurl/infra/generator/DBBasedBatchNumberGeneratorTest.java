package com.geekhalo.tinyurl.infra.generator;

import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import com.geekhalo.tinyurl.infra.TestApplication;
import com.geekhalo.tinyurl.infra.generator.db.DBBasedBatchNumberGenerator;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("db-batch")
@Getter
class DBBasedBatchNumberGeneratorTest extends AbstractNumberGeneratorTest{

    @Autowired
    private DBBasedBatchNumberGenerator dbBasedBatchNumberGenerator;

    @Override
    NumberGenerator getNumberGenerator() {
        return dbBasedBatchNumberGenerator;
    }
}