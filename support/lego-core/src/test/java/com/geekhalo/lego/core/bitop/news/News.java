package com.geekhalo.lego.core.bitop.news;

import com.geekhalo.lego.core.bitop.longop.LongBitOp;
import com.geekhalo.lego.core.bitop.longop.LongMaskOp;
import lombok.Data;

/**
 * Created by taoli on 2022/12/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class News {
    private static LongMaskOp SYSTEM = LongMaskOp.MASK_1;
    private static LongMaskOp COMMON_USER = LongMaskOp.MASK_2;
    private static LongMaskOp VIP_USER = LongMaskOp.MASK_3;

    private static LongMaskOp AUDIT_SUBMIT = LongMaskOp.MASK_17;
    private static LongMaskOp AUDIT_PASSED = LongMaskOp.MASK_18;
    private static LongMaskOp AUDIT_REJECT = LongMaskOp.MASK_19;

    private static LongMaskOp BLACK_LIST = LongMaskOp.MASK_33;
    private static LongMaskOp WHITE_LIST = LongMaskOp.MASK_34;

    private long status = 0;

    private LongBitOp isShowBitOp = createIsShowOp();

    private LongBitOp createIsShowOp() {
        LongBitOp system = SYSTEM;
        LongBitOp commonUser = COMMON_USER.and(AUDIT_PASSED);
        LongBitOp vidUser = VIP_USER.and(AUDIT_SUBMIT.or(AUDIT_PASSED));
        LongBitOp black = BLACK_LIST;
        LongBitOp white = WHITE_LIST;
        return black.not()
                .and(system.or(commonUser).or(vidUser))
                .or(white);
    }

    public boolean isShow(){
        return isShowBitOp.match(this.status);
    }

    public String getSqlFilter(String field){
        return isShowBitOp.toSqlFilter(field);
    }

    public void setSystem(){
        cleanUserType();
        this.status = SYSTEM.set(this.status);
    }

    public void setCommonUser(){
        cleanUserType();
        this.status = COMMON_USER.set(this.status);
    }

    public void setVipUser(){
        cleanUserType();
        this.status = VIP_USER.set(this.status);
    }

    private void cleanUserType(){
        this.status = SYSTEM.unset(this.status);
        this.status = COMMON_USER.unset(this.status);
        this.status = VIP_USER.unset(this.status);
    }

    public void submitAudit(){
        cleanAudit();
        this.status = AUDIT_SUBMIT.set(this.status);
    }

    public void passAudit(){
        cleanAudit();
        this.status = AUDIT_PASSED.set(this.status);
    }

    public void rejectAudit(){
        cleanAudit();
        this.status = AUDIT_REJECT.set(this.status);
    }

    private void cleanAudit(){
        this.status = AUDIT_SUBMIT.unset(this.status);
        this.status = AUDIT_PASSED.unset(this.status);
        this.status = AUDIT_REJECT.unset(this.status);
    }

    public void setBlackList(){
        cleanList();
        this.status = BLACK_LIST.set(this.status);
    }

    public void setWhiteList(){
        cleanList();
        this.status = WHITE_LIST.set(this.status);
    }

    private void cleanList() {
        this.status = BLACK_LIST.unset(this.status);
        this.status = WHITE_LIST.unset(this.status);
    }


}
