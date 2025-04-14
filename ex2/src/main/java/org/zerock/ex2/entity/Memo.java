package org.zerock.ex2.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity //해당 클래스의 인스턴스들이 JPA로 관리되는 앤티티 객체라는 것을 의미한다.
@Table(name = "tbl_memo") //엔티티 클래스를 어떤 테이블로 생성할 것인지에 대한 정보를 담기위한 어노테이션
@ToString
@Getter //getter 메서드 생성
@Builder // 객체를 생성
@AllArgsConstructor   //이 2개 어노테이션은 builder 어노테이션 사용하려면 같이 처리해야 에러발생 x
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK를 자동으로 생성하고자 할 때 사용한다
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;
}
