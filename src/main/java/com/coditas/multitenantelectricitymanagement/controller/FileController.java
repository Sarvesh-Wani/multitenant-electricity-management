package com.coditas.multitenantelectricitymanagement.controller;


import com.coditas.multitenantelectricitymanagement.entity.File;
import com.coditas.multitenantelectricitymanagement.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class FileController {
    @Autowired
    private FileRepository fileRepository;
    @PostMapping("/saveFile")
    public String saveFile(@RequestParam("file") MultipartFile file){
        try {
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            byte[] fileContent = file.getBytes();
            File savefile = new File(fileName, contentType, fileContent);
            fileRepository.save(savefile);

            return "File saved successfully";
        }

        catch(Exception e) {
            return "File not saved";
        }
    }
}
