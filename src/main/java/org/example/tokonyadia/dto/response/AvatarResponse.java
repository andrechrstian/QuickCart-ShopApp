package org.example.tokonyadia.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvatarResponse {
    private String url;
    private String name;
}
