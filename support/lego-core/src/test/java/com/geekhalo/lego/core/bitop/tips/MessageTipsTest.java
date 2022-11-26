package com.geekhalo.lego.core.bitop.tips;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by taoli on 2022/11/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class MessageTipsTest {
    private MessageTips messageTips = new MessageTips();

    @Test
    public void match() {
        {
            Assertions.assertFalse(this.messageTips.isLike());
            this.messageTips.setLike(true);
            Assertions.assertTrue(this.messageTips.isLike());
            this.messageTips.setLike(false);
            Assertions.assertFalse(this.messageTips.isLike());
        }

        {
            Assertions.assertFalse(this.messageTips.isSystem());
            this.messageTips.setSystem(true);
            Assertions.assertTrue(this.messageTips.isSystem());
            this.messageTips.setSystem(false);
            Assertions.assertFalse(this.messageTips.isSystem());
        }

        {

            System.out.println(this.messageTips.filterBySystem("type"));

            System.out.println(this.messageTips.filterByLike("type"));

            System.out.println(this.messageTips.filterBySystemOrLike("type"));

            System.out.println(this.messageTips.filterBySystemAndLike("type"));


            System.out.println(this.messageTips.filterByNotSystem("type"));

        }
    }
}