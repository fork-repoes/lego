package com.geekhalo.lego.core.bitop.intop;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * Created by taoli on 2022/11/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class IntMaskOpTest {
    @Test
    void or() {
        IntMaskOp bitOp1 = IntMaskOp.getByBitIndex(1);
        IntMaskOp bitOp2 = IntMaskOp.getByBitIndex(2);

        IntBitOp bitOp = bitOp1.or(bitOp2);

        {
            int value = 0;
            Assertions.assertFalse(bitOp1.match(value));
            Assertions.assertFalse(bitOp2.match(value));
            Assertions.assertFalse(bitOp.match(value));
        }

        {
            int value = bitOp1.set(0);
            Assertions.assertTrue(bitOp1.match(value));
            Assertions.assertFalse(bitOp2.match(value));
            Assertions.assertTrue(bitOp.match(value));
        }

        {
            int value = bitOp2.set(0);
            Assertions.assertFalse(bitOp1.match(value));
            Assertions.assertTrue(bitOp2.match(value));
            Assertions.assertTrue(bitOp.match(value));
        }

        {
            int value = bitOp1.set(0);
            value = bitOp2.set(value);
            Assertions.assertTrue(bitOp1.match(value));
            Assertions.assertTrue(bitOp2.match(value));
            Assertions.assertTrue(bitOp.match(value));
        }
    }

    @Test
    void and() {
        IntMaskOp bitOp1 = IntMaskOp.getByBitIndex(1);
        IntMaskOp bitOp2 = IntMaskOp.getByBitIndex(2);

        IntBitOp bitOp = bitOp1.and(bitOp2);

        {
            int value = 0;
            Assertions.assertFalse(bitOp1.match(value));
            Assertions.assertFalse(bitOp2.match(value));
            Assertions.assertFalse(bitOp.match(value));
        }

        {
            int value = bitOp1.set(0);
            Assertions.assertTrue(bitOp1.match(value));
            Assertions.assertFalse(bitOp2.match(value));
            Assertions.assertFalse(bitOp.match(value));
        }

        {
            int value = bitOp2.set(0);
            Assertions.assertFalse(bitOp1.match(value));
            Assertions.assertTrue(bitOp2.match(value));
            Assertions.assertFalse(bitOp.match(value));
        }

        {
            int value = bitOp1.set(0);
            value = bitOp2.set(value);
            Assertions.assertTrue(bitOp1.match(value));
            Assertions.assertTrue(bitOp2.match(value));
            Assertions.assertTrue(bitOp.match(value));
        }
    }

    @Test
    void not() {
        IntMaskOp bitOp1 = IntMaskOp.getByBitIndex(1);
        IntBitOp bitOp = bitOp1.not();

        {
            int value = 0;
            Assertions.assertFalse(bitOp1.match(value));
            Assertions.assertTrue(bitOp.match(value));
        }

        {
            int value = bitOp1.set(0);
            Assertions.assertTrue(bitOp1.match(value));
            Assertions.assertFalse(bitOp.match(value));
        }

    }

    @Test
    void bitTest(){
        Set<IntMaskOp> intMaskOps = Sets.newHashSet();
        for (int i=1; i <= 32; i++){
            int value = 0;
            IntMaskOp intMaskOp = IntMaskOp.getByBitIndex(i);
            intMaskOps.add(intMaskOp);

            Assertions.assertFalse(intMaskOp.match(value));
            Assertions.assertFalse(intMaskOp.isSet(value));

            value = intMaskOp.set(value);
            Assertions.assertTrue(intMaskOp.match(value));
            Assertions.assertTrue(intMaskOp.isSet(value));

            value = intMaskOp.unset(value);
            Assertions.assertFalse(intMaskOp.match(value));
            Assertions.assertFalse(intMaskOp.isSet(value));
        }
        Assertions.assertEquals(32, intMaskOps.size());
    }

    @Test
    void toSqlFilter() {
        IntBitOp bitOp1 = IntMaskOp.getByBitIndex(1);
        IntBitOp bitOp2 = IntMaskOp.getByBitIndex(2);
        IntBitOp bitOp3 = IntMaskOp.getByBitIndex(3);
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