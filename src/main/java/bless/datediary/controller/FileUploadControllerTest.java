package bless.datediary.controller;

import bless.datediary.database_connection.DBConn;
import bless.datediary.model.upLoad;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

        //file.transferTo(new File("C:\\users\\USER\\image\\" + "couple_index_"+data.get("couple_index")+".jpg"));

        file.transferTo(new File("C:\\users\\USER\\image\\" + data.get("couple_index") + ".jpg"));

        System.out.println(file + "저장 완료");

        ArrayList<upLoad> upLoad = new ArrayList<upLoad>();

        upLoad uLoad = new upLoad();

        uLoad.setTest("이미지 저장 후 리턴했습니다.");

        upLoad.add(uLoad);


        DBConn DBconn;
        Connection conn = null;
        PreparedStatement pstmt = null;


        try {
            DBconn = new DBConn();
            conn = DBconn.connect();

            String sql = "insert notice (couple_index, timestamp2, name2, type2, month, day) values (?,?,?,?,?,?);";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, data.get("couple_index"));
            pstmt.setString(2, data.get("timestamp2"));
            pstmt.setString(3, data.get("name2"));
            pstmt.setString(4, "2");
            pstmt.setString(5, "13");
            pstmt.setString(6, "32");

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }

        return upLoad;

    }


    @GetMapping(value = "/getImageFrom/{coupleIndex}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> userSearch(@PathVariable("coupleIndex") String coupleIndex, Model model) throws IOException {

        model.addAttribute("coupleIndex", coupleIndex);

        //커플인덱스로 변경 필요
        InputStream imageStream = new FileInputStream("C:/Users/USER/image/" + coupleIndex + ".jpg");
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
    }
}

