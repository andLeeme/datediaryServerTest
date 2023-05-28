package bless.datediary.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Controller
public class FileUploadController {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.maxFileSize}")
    private long maxFileSize;

    @PostMapping("/api/image")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {

        // 전달된 파일이 비어있는지 확인
        if (file.isEmpty()) {
            return "redirect:/error";
        }

        // 파일 크기 확인
        if (file.getSize() > maxFileSize) {
            return "redirect:/error";
        }

        try {
            // 업로드할 파일명 생성
            // StringUtils.cleanPath 메서드로 파일명에 포함된 경로 정보나 잘못된 문자를 정리
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            // 파일 저장 경로 생성
            Path uploadDir = Path.of(uploadPath);
            Path filePath = uploadDir.resolve(fileName);

            // 파일 저장
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 파일 저장 성공시 처리
            file.getInputStream().close(); // 파일 핸들 닫기



            // 임시 파일 삭제
            file.transferTo(filePath.toFile());

            return "redirect:/success";
        } catch (IOException e) {
            // 파일 저장 실패시 처리
            return "redirect:/error";
        } finally {
            try {
                file.getInputStream().close(); // 파일 핸들 닫기
            } catch (IOException e) {
                e.printStackTrace(); // 예외 처리
            }
        }
    }
}
