package com.stver8935.rest_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * 유저 데이터 모델
 *
 * @author : hangihyeong
 * @ packageName : com.stver8935.rest_api.models
 * @ fileName : UserDto
 * @since : 24. 8. 14.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(
        name = "USERS", uniqueConstraints =
        @UniqueConstraint(
                columnNames = {"userName","email","phoneNum"}
        )
) // 명시적 테이블 매핑
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phoneNum;

    private int age;

    private String refreshToken;

    private int loginFailCount;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatusCode userStatusCode;
}
