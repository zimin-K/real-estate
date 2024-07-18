package project.realestate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import project.realestate.constant.Role;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "member")
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    // 이름, 이메일, 비밀번호, 닉네임

    @Column
    //@NotBlank(message = "필수 입력값입니다.")
    private String name;

    @Column(unique = true)
    //@NotBlank(message = "필수 입력값입니다.")
    private String email;

    @Column
    //@NotBlank(message = "필수 입력값입니다.")
    private String password;

    @Column(unique = true)
    //@NotBlank(message = "필수 입력값입니다.")
    private String nickName;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void change(String name, String email, String password, String nickName) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }

    // 관리자 계정 생성용
    public void insertAdmin(String name, String email, String password, String nickName, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.role = role;
    }

    public Role setRole(Role user) {
        return Role.USER;
    }

    public Role getRole(){
        return role;
    }
}
