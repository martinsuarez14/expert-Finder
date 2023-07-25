package com.egg.expertfinder.entity;

import java.io.IOException;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String mime;
    private String name;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

//  Constructor para instanciar una Image
    public Image(MultipartFile file) throws IOException {
        this.mime = file.getContentType();
        this.name = file.getName();
        this.content = file.getBytes();
    }

}
