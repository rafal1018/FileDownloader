package pl.fileDownloader.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
    public ResponseEntity<InputStreamResource> downloadFile1() throws FileNotFoundException {

        File file = new File(FILE_PATH);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_PDF).contentLength(file.length())
                .body(resource);

    }

    @GetMapping("/download2")
    public ResponseEntity<ByteArrayResource> donwloadFile2() throws IOException {

        Path path = Paths.get(FILE_PATH);
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + path.getFileName().toFile())
                .contentType(MediaType.APPLICATION_PDF).contentLength(data.length)
                .body(resource);

    }

    @GetMapping("/download3")
    public void downloadFile3(HttpServletResponse response) throws IOException {

        File file = new File(FILE_PATH);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.flush();
        inputStream.close();

    }

}
