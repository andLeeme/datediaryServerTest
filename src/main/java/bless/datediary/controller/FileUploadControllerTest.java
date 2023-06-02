package bless.datediary.controller;

import bless.datediary.model.upLoad;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class FileUploadControllerTest {

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/api/image2")
    public ArrayList<upLoad> handleFileUpload(@RequestPart HashMap<String, String> data, @RequestParam("file") MultipartFile file) throws IOException {

        // 파일 저장 경로 생성

        System.out.println(file.getOriginalFilename());

        System.out.println(data.get("couple_index"));

        file.transferTo(new File("C:\\users\\USER\\image\\" + "couple_index_"+data.get("couple_index")+".jpg"));

        System.out.println(file + "저장 완료");

        ArrayList<upLoad> upLoad = new ArrayList<upLoad>();

        upLoad uLoad = new upLoad();

        uLoad.setTest("이미지 저장 후 리턴했습니다.");

        upLoad.add(uLoad);


        return upLoad;

    }
}

