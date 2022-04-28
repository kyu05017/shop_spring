package com.shop.dto;

import com.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ItemImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();
    // 맴버 변수로 ModelMapper 객체를 추가합니다. [ 메모리 할당 ]

    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg,ItemImgDto.class);
        // ItemImgDto 엔티티 객체를 파라미터로 받아서 itemImg 객체의 자료형과 맴버변수의 이름이 같을때 ItemImgDto
        // 값을 복사해서 반환 합니다. stati  메소드로 선언해 ItemImgDto 객체를 생성하지 않아도 호출할 수 있도록 합니다.
    }

}