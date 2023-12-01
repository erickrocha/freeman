package com.erocha.freeman.commons.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
class UUIDGeneratorTest {

    @Test
    void generate_uuid() {
        String uuidOne = UUIDGenerator.generate();
        String uuidTwo = UUIDGenerator.generate();
        assertAll("different UUIDS", () -> assertNotEquals(uuidOne, uuidTwo));
    }

}
