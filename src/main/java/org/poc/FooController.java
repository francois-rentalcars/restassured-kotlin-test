package org.poc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class FooController {

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/foo")
    public FooDto getDummyDto() {
        Map responseBody = restTemplate.getForEntity("http://localhost:8181/bar", Map.class).getBody();
        String value = (String) responseBody.get("inner");

        FooDto fooDto = new FooDto();
        fooDto.setKey(value);
        return fooDto;
    }

    public static class FooDto {

        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

}
