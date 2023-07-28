package id.gibranharahap.pet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetModel {
    private Long id;
    private Long categoryId;
    private String name;
    private String photoUrls;
    private Long tagsId;
    private String status;
}