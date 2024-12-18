package com.prandini.smartwallet.common.rest.service.actions;

/*
 * @author prandini
 * created 12/17/24
 */

import com.prandini.smartwallet.common.rest.domain.ErrorLog;
import com.prandini.smartwallet.common.rest.model.ErrorLogInput;
import com.prandini.smartwallet.common.rest.repository.ErrorLogRepository;
import jakarta.annotation.Resource;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@CommonsLog
public class ErrorLogCreator {

    @Resource
    private ErrorLogRepository repository;

    public ErrorLog create(ErrorLogInput input){
        log.info("Criando log de erro.");

       try {
           ErrorLog errorLog = ErrorLog.builder()
                   .errorMessage(input.getErrorMessage())
                   .timestamp(input.getTimestamp())
                   .uri(saveStacktraceToFile(input.getStackTrace()))
                   .operador(input.getOperador())
                   .build();

           return this.repository.save(errorLog);
       }catch (IOException e) {
           log.error("Erro ao salvar stacktrace em arquivo.", e);
           return null;
       }
    }

    private String saveStacktraceToFile(String stacktrace) throws IOException {

        String logDir = "logs/stacktraces";
        Path dir = Paths.get(logDir);

        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }

        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".log";
        Path filePath = dir.resolve(fileName);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            writer.write(stacktrace);
        }

        return filePath.toString();
    }
}
