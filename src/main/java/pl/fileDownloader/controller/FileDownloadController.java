package pl.fileDownloader.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileDownloadController {

    private static final String FILE_PATH = "/home/rafal/sample.pdf";

    @GetMapping("/")
    public String fileUploadForm() {
        return "fileDownloadView";
    }

    @GetMapping("/download1")
    public ResponseEntity<InputStreamResource> downloadFiles1() throws FileNotFoundException {

        File file = new File(FILE_PATH);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_PDF).contentLength(file.length())
                .body(resource);

    }

    @GetMapping("download2")
    public ResponseEntity<ByteArrayResource> donwloadFiles2() throws IOException {

        Path path = Paths.get(FILE_PATH);
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + path.getFileName().toFile())
                .contentType(MediaType.APPLICATION_PDF).contentLength(data.length)
                .body(resource);

    }

}
