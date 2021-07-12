package com.philips.productcatalog.infrastructure.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base entity.
 */
@Data
@AllArgsConstructor
public abstract class BaseModel {

    @Id
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDateTime;

    /**
     * Class constructor.
     */
    protected BaseModel() {
        if (id == null) {
            setId(UUID.randomUUID().toString());
        }
        setCreationDateTime(LocalDateTime.now());
    }
}
