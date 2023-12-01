package com.erocha.freeman.dataload.usecases;


import com.erocha.freeman.dataload.domain.IAppointment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CsvImporterTest {


    @Test
    void test_source_file_not_found() throws Exception {
        IOException exception = assertThrows(NoSuchFileException.class, () -> {
            Path path = Paths.get("src/test/resources/tidmes.csv");
            String text = new String(Files.readAllBytes(path));
            String[] array = text.split("\n");
            CsvLoader loader = new CsvLoader();
            loader.execute(List.of(array));
        });
        assertEquals("src/test/resources/tidmes.csv", exception.getMessage());
    }

    @Test
    void test_import_success() throws Exception {
        Path path = Paths.get("src/test/resources/times.csv");
        String text = new String(Files.readAllBytes(path));
        String[] array = text.split("\n");
        CsvLoader loader = new CsvLoader();
        List<IAppointment> appointments = loader.execute(List.of(array));
        assertNotNull(appointments);
        assertTrue(appointments.size() > 0);
    }
}
