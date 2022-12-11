package com.geekhalo.lego.core.enums.mvc;

import com.geekhalo.lego.common.enums.CommonEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/12/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ApiModel(description = "通用枚举")
public class CommonEnumVO {
    @ApiModelProperty(notes = "Code")
    private final int code;

    @ApiModelProperty(notes = "Name")
    private final String name;

    @ApiModelProperty(notes = "描述")
    private final String desc;

    public static CommonEnumVO from(CommonEnum commonEnum){
        if (commonEnum == null){
            return null;
        }
        return new CommonEnumVO(commonEnum.getCode(), commonEnum.getName(), commonEnum.getDescription());
    }

    public static List<CommonEnumVO> from(List<CommonEnum> commonEnums){
        if (CollectionUtils.isEmpty(commonEnums)){
            return Collections.emptyList();
        }
        return commonEnums.stream()
                .filter(Objects::nonNull)
                .map(CommonEnumVO::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
