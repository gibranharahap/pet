package id.gibranharahap.pet.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest {
    private Long id;
    private CategoryModel category;
    private String name;
    private List<String> photoUrls;
    private List<TagsModel> tags;
    private String status;
}
