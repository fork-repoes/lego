package com.geekhalo.lego.core.bitop.longop;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by taoli on 2022/11/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class LongMaskOpTest {
    @Test
    void or() {
        LongMaskOp bitOp1 = LongMaskOp.getByBitIndex(1);
        LongMaskOp bitOp2 = LongMaskOp.getByBitIndex(2);
        LongBitOp bitOp = bitOp1.or(bitOp2);

        {
            long value = 0;
            Assertions.assertFalse(bitOp1.match(value));
            Assertions.assertFalse(bitOp2.match(value));
            Assertions.assertFalse(bitOp.match(value));
        }

        {
            long value = bitOp1.set(0L);
            Assertions.assertTrue(bitOp1.match(value));
            Assertions.assertFalse(bitOp2.match(value));
            Assertions.assertTrue(bitOp.match(value));
        }

        {
            long value = bitOp2.set(0L);
            Assertions.assertFalse(bitOp1.match(value));
            Assertions.assertTrue(bitOp2.match(value));
            Assertions.assertTrue(bitOp.match(value));
        }

        {
            long value = bitOp1.set(0L);
            value = bitOp2.set(value);
            Assertions.assertTrue(bitOp1.match(value));
            Assertions.assertTrue(bitOp2.match(value));
            Assertions.assertTrue(bitOp.match(value));
        }
    }

    @Test
    void and() {
        LongMaskOp bitOp1 = LongMaskOp.getByBitIndex(1);
        LongMaskOp bitOp2 = LongMaskOp.getByBitIndex(2);

        LongBitOp bitOp = bitOp1.and(bitOp2);

        {
            long value = 0;
            Assertions.assertFalse(bitOp1.match(value));
            Assertions.assertFalse(bitOp2.match(value));
            Assertions.assertFalse(bitOp.match(value));
        }

        {
            long value = bitOp1.set(0L);
            Assertions.assertTrue(bitOp1.match(value));
            Assertions.assertFalse(bitOp2.match(value));
            Assertions.assertFalse(bitOp.match(value));
        }

        {
            long value = bitOp2.set(0L);
            Assertions.assertFalse(bitOp1.match(value));
            Assertions.assertTrue(bitOp2.match(value));
            Assertions.assertFalse(bitOp.match(value));
        }

        {
            long value = bitOp1.set(0L);
            value = bitOp2.set(value);
            Assertions.assertTrue(bitOp1.match(value));
            Assertions.assertTrue(bitOp2.match(value));
            Assertions.assertTrue(bitOp.match(value));
        }
    }

    @Test
    void not() {
        LongMaskOp bitOp1 = LongMaskOp.getByBitIndex(1);
        LongBitOp bitOp = bitOp1.not();

        {
            long value = 0L;
            Assertions.assertFalse(bitOp1.match(value));
            Assertions.assertTrue(bitOp.match(value));
        }

        {
            long value = bitOp1.set(0L);
            Assertions.assertTrue(bitOp1.match(value));
            Assertions.assertFalse(bitOp.match(value));
        }

    }

    @Test
    void bitTest(){
        Set<LongMaskOp> longMaskOps = Sets.newHashSet();
        for (int i=1; i <= 64; i++){
            long value = 0;
            LongMaskOp longMaskOp = LongMaskOp.getByBitIndex(i);
            longMaskOps.add(longMaskOp);

            Assertions.assertFalse(longMaskOp.match(value));
            Assertions.assertFalse(longMaskOp.isSet(value));

            value = longMaskOp.set(value);
            Assertions.assertTrue(longMaskOp.match(value));
            Assertions.assertTrue(longMaskOp.isSet(value));

            value = longMaskOp.unset(value);
            Assertions.assertFalse(longMaskOp.match(value));
            Assertions.assertFalse(longMaskOp.isSet(value));
        }
        Assertions.assertEquals(64, longMaskOps.size());
    }

    @Test
    void toSqlFilter() {
        LongBitOp bitOp1 = LongMaskOp.getByBitIndex(1);
        LongBitOp bitOp2 = LongMaskOp.getByBitIndex(2);
        LongBitOp bitOp3 = LongMaskOp.getByBitIndex(3);
        {
            Assertions.assertEquals("(type & 1)=1", bitOp1.toSqlFilter("type"));
            Assertions.assertEquals("(type & 2)=2", bitOp2.toSqlFilter("type"));
            Assertions.assertEquals("(type & 4)=4", bitOp3.toSqlFilter("type"));
        }

        {
            Assertions.assertEquals("(type & 1)<>1", bitOp1.not().toSqlFilter("type"));
            Assertions.assertEquals("(type & 2)<>2", bitOp2.not().toSqlFilter("type"));
            Assertions.assertEquals("(type & 4)<>4", bitOp3.not().toSqlFilter("type"));
        }

        {
            Assertions.assertEquals("((type & 1)=1 and (type & 2)=2)", bitOp1.and(bitOp2).toSqlFilter("type"));
            Assertions.assertEquals("((type & 2)=2 and (type & 4)=4)", bitOp2.and(bitOp3).toSqlFilter("type"));
            Assertions.assertEquals("((type & 1)=1 and (type & 4)=4)", bitOp1.and(bitOp3).toSqlFilter("type"));
        }
        {
            Assertions.assertEquals("((type & 1)=1 or (type & 2)=2)", bitOp1.or(bitOp2).toSqlFilter("type"));
            Assertions.assertEquals("((type & 2)=2 or (type & 4)=4)", bitOp2.or(bitOp3).toSqlFilter("type"));
            Assertions.assertEquals("((type & 1)=1 or (type & 4)=4)", bitOp1.or(bitOp3).toSqlFilter("type"));
        }
    }
}