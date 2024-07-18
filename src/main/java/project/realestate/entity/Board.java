package project.realestate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "board")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno; // 게시판에 표시될 번호

    // 제목, 주소, 크기, 가격(string), 특징
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String size; // 평수

    @Column(nullable = false)
    private String price; // 전세,월세,매매 직접입력

    private String content; // 설명

    @ElementCollection
    private List<String> imagePaths; // 이미지 경로


    public void change(String title, String address, String size, String price, String content, List<String> imagePaths) {
        this.title = title;
        this.address = address;
        this.size = size;
        this.price = price;
        this.content = content;
        this.imagePaths = imagePaths;
    }
}
