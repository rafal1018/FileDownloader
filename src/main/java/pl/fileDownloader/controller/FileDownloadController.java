package pl.fileDownloader.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Controller
public class FileDownloadController {

    private static final String FILE_PATH = "/home/rafal/sample.pdf";

    @GetMapping("/")
    public String fileUploadForm() {
        return "fileDownloadView";
    }

    @GetMapping("/donwload1")
    public ResponseEntity<InputStreamResource> downloadFiles1() throws FileNotFoundException {

        File file = new File(FILE_PATH);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_PDF).contentLength(file.length())
                .body(resource);

    }

}
