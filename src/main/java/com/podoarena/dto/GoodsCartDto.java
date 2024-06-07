package com.podoarena.dto;

import com.podoarena.entity.Concert;
import com.podoarena.entity.GoodsCart;
import com.podoarena.entity.OrderGoods;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class GoodsCartDto {


    @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
    private Long goodsCartId;

    @Min(value = 1, message = "굿즈는 최소 1개 이상 담아주세요")
    private int goodsCount;

    private static ModelMapper modelMapper = new ModelMapper();

    //dto -> entity
    public GoodsCart createGoodsCart() {
        return modelMapper.map(this, GoodsCart.class);
    }
    //entity -> dto
    public static GoodsCartDto of(GoodsCart goodsCart) {
        return modelMapper.map(goodsCart, GoodsCartDto.class);
    }
}
