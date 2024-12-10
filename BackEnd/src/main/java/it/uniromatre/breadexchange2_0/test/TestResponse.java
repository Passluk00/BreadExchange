package it.uniromatre.breadexchange2_0.test;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestResponse {

    private Integer id;
    private String name;
    private String description;
    private byte[] test_url;
    private String owner;



}
