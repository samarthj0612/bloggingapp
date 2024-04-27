package com.samdb.bloggingapp.payloads;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private String fileName;
    private String message;
}
