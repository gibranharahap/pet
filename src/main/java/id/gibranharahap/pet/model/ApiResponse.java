package id.gibranharahap.pet.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {
    public static ResponseEntity<Object> generateResponse(Long id, HttpStatus status, String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        map.put("message", id);
        map.put("code", status.value());
        
        return new ResponseEntity<Object>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        map.put("message", message);
        map.put("code", status.value());
        
        return new ResponseEntity<Object>(map, status);
    }
}
