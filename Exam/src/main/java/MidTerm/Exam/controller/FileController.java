package MidTerm.Exam.controller;

import MidTerm.Exam.model.FileModel;
import MidTerm.Exam.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Controller
public class FileController {

    @Autowired
    FileService fileService;


    @GetMapping("/admin_view")
    public String view(  Model model ){
        List<FileModel> files =  fileService.getfile();
        model.addAttribute("files" , files);
        return "admin_view";
    }


    @PostMapping("/submit")
    public String submit(@RequestParam("myfile") MultipartFile file) throws IOException {

        String mylocation = System.getProperty("user.dir") + "/src/main/resources/static/";

        String filename = file.getOriginalFilename();

        File mySavedFile = new File( mylocation + filename);

        InputStream inputStream = file.getInputStream();

        OutputStream outputStream = new FileOutputStream(mySavedFile);

        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1){
            outputStream.write(bytes , 0 , read);
        }

        String mylink = "http://localhost:8080" + filename;

        FileModel fileModel = new FileModel();
        fileModel.setFilename(mylink);

        fileService.save(fileModel);

        return "redirect:/";
    }

}

