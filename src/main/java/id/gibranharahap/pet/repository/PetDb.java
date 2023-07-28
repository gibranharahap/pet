package id.gibranharahap.pet.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import id.gibranharahap.pet.model.CategoryModel;
import id.gibranharahap.pet.model.PetModel;
import id.gibranharahap.pet.model.TagsModel;

@Mapper
public interface PetDb {

    @Insert("INSERT INTO pet(id, categoryId, name, photoUrls, tagsId, status) VALUES(#{id}, #{categoryId}, #{name}, #{photoUrls}, #{tagsId}, #{status})")
    public void addPet(PetModel pet);
    
    @Select("SELECT * FROM pet WHERE id = #{id}")
    public PetModel getPetByIdPet(Long id);

    @Update("UPDATE pet SET name=#{name}, status=#{status} WHERE id = #{id}")
    public boolean updatePet(PetModel pet);

    @Delete("DELETE FROM pet WHERE id = #{id}")
    public boolean deletePet(Long id);

    @Select("SELECT * FROM pet WHERE status = #{status}")
    public List<PetModel> findPetByStatus(String status);

    @Select("SELECT * FROM category WHERE id = #{id}")
    public CategoryModel findCategory(Long id);

    @Insert("INSERT INTO category(id, name) VALUES(#{id}, #{name})")
    public boolean addCategory(CategoryModel category);

    @Select("SELECT * FROM tags WHERE id = #{id}")
    public TagsModel findTags(Long id);

    @Insert("INSERT INTO tags(id, name) VALUES(#{id}, #{name})")
    public boolean addTags(TagsModel tags);

}
