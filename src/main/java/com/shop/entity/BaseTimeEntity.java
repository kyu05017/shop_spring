package com.shop.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


// Auditiong을 적용하기 위해서 @EntityListeners을 추가합니다.
@EntityListeners(value = {AuditingEntityListener.class})
//공통 맵핑정보가 필요할 때 사용하는 어노테이션으로 부모클래스를 상속 받는 자식 클래스에 맵핑 정보만 제공합니다.
@MappedSuperclass
@Getter @Setter
public abstract class BaseTimeEntity {

    //엔티티가 생성되어 저장될 때 시간을 자동으로 저장합니다.
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime;

    //엔티티의 값이 변경할 대 시간을 자동으로 저장합니다.
    @LastModifiedDate
    private LocalDateTime updateTime;

}