package com.geekhalo.tinyurl.infra.generator;

import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import com.geekhalo.tinyurl.TestApplication;
import com.geekhalo.tinyurl.infra.generator.db.DBBasedSingleNumberGenerator;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("db-single")
@Getter
class DBBasedSingleNumberGeneratorTest extends AbstractNumberGeneratorTest{

    @Autowired
    private DBBasedSingleNumberGenerator dbBasedSingleNumberGenerator;

    @Override
    NumberGenerator getNumberGenerator() {
        return dbBasedSingleNumberGenerator;
    }
}