package project.realestate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long bno;
    private String title;
    private String address;
    private String size;
    private String price;
    private String content;
    private List<String> imagePaths;

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }
}
