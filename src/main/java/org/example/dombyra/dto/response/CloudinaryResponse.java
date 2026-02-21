package org.example.dombyra.dto.response;

import lombok.Builder;

@Builder
public record CloudinaryResponse(String publicId, String url) {
}
