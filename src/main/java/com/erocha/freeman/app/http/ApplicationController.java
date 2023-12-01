package com.erocha.freeman.app.http;

import com.erocha.freeman.app.http.json.Config;
import com.erocha.freeman.app.usecases.GetConfiguration;
import com.erocha.freeman.dataload.usecases.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    private GetConfiguration getConfiguration;

    private DataLoader dataLoader;

    @Autowired
    public ApplicationController(GetConfiguration getConfiguration, DataLoader dataLoader) {
        this.getConfiguration = getConfiguration;
        this.dataLoader = dataLoader;
    }

    @GetMapping("/configuration")
    public Config getConfig(Authentication authentication) {
        return getConfiguration.execute(authentication.getName());
    }

    @PostMapping("/load")
    public ResponseEntity<List<String>> dataLoader(@RequestParam("file") MultipartFile file) throws IOException {
        String text = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
        List<String> response = dataLoader.execute(text);
        return ResponseEntity.ok(response);
    }
}
