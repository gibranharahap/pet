package id.gibranharahap.pet.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import id.gibranharahap.pet.model.ApiResponse;
import id.gibranharahap.pet.model.CategoryModel;
import id.gibranharahap.pet.model.PetModel;
import id.gibranharahap.pet.model.PetRequest;
import id.gibranharahap.pet.model.TagsModel;
import id.gibranharahap.pet.repository.PetDb;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetDb petDb;

    @GetMapping("/{petId}")
    public PetModel getPet(@PathVariable("petId") Long id){
        return petDb.getPetByIdPet(id);
    }

    @PostMapping
    public PetRequest postPet(@RequestBody @Valid PetRequest petReq){
        PetModel pet = new PetModel();
        pet.setId(petReq.getId());
        CategoryModel category = petReq.getCategory();
        pet.setCategoryId(category.getId());
        pet.setName(petReq.getName());
        pet.setPhotoUrls(petReq.getPhotoUrls().get(0));
        pet.setTagsId(petReq.getTags().get(0).getId());
        List<TagsModel> tagsList = petReq.getTags();
        pet.setStatus(petReq.getStatus());
        petDb.addPet(pet);
        if(petDb.findCategory(category.getId()) == null){
            petDb.addCategory(category);
        }
        for(TagsModel tags: tagsList){
            if(petDb.findTags(tags.getId()) == null){
                petDb.addTags(tags);
            }
        }
        return petReq;
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Object> deletePet(@PathVariable("petId") Long id){
        try {
            petDb.deletePet(id);
            return ApiResponse.generateResponse(id, HttpStatus.OK, "unknown");
        } catch (Exception e) {
            return ApiResponse.generateResponse(id, HttpStatus.MULTI_STATUS, e.getMessage());
        }
    }

    @PutMapping
    public PetRequest updatePet(@RequestBody @Valid PetRequest petReq){
        PetModel pet = petDb.getPetByIdPet(petReq.getId());
        pet.setId(petReq.getId());
        CategoryModel category = petReq.getCategory();
        pet.setCategoryId(category.getId());
        pet.setName(petReq.getName());
        pet.setPhotoUrls(petReq.getPhotoUrls().get(0));
        pet.setTagsId(petReq.getTags().get(0).getId());
        pet.setStatus(petReq.getStatus());
        petDb.updatePet(pet);
        if(petDb.findCategory(category.getId()) == null){
            petDb.addCategory(category);
        }
        return petReq;
    }

    @PostMapping("/{petId}")
    public ResponseEntity<Object> updatePetForm(@PathVariable("petId") Long petId, @RequestParam("petId") Long id, 
    @RequestParam("name") String name, @RequestParam("status") String status){
        try {
            PetModel pet = petDb.getPetByIdPet(id);
            pet.setName(name);
            pet.setStatus(status);
            petDb.updatePet(pet);
            return ApiResponse.generateResponse(id, HttpStatus.OK, "unknown");
        } catch (Exception e) {
            return ApiResponse.generateResponse(id, HttpStatus.MULTI_STATUS, e.getMessage());
        }
    }

    @GetMapping("/findByStatus")
    public List<PetModel> findPetByStatus(@RequestParam("status") String status){
        return petDb.findPetByStatus(status);
    }

    @PostMapping("/{petId}/uploadImage")
    public ResponseEntity<Object> uploadImage(@PathVariable("petId") Long petId, @RequestParam("petId") Long id, 
    @RequestParam("additionalMetadata") String additionalMetadata, @RequestParam("file") MultipartFile file){
        
        String filename = file.getOriginalFilename();
        
        try {
            String directoryPath = "src/main/resources/static/";

            Path directory = Path.of(directoryPath);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            Path filePath = directory.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


            return ApiResponse.generateResponse("additionalMetadata: " + additionalMetadata + "\nFile uploaded to " + directoryPath + filename + ", " + file.getSize() + " bytes", HttpStatus.OK, "unknown");
        } catch (IOException e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }

    }
    
}
