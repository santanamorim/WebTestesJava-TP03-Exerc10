import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UniversityApi {

    public static void main(String[] args) {
        try {
            String jsonResponse = getUniversitiesInBrazil();
            University[] universities = parseJsonToUniversities(jsonResponse);

            for (University university : universities) {
                System.out.println(university);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getUniversitiesInBrazil() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://universities.hipolabs.com/search?country=Brazil"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static University[] parseJsonToUniversities(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, University[].class);
    }
}

@Data
class University {
    private String name;
    private String country;
    private String[] web_pages;
    private String alpha_two_code;
    private String state_province;
    private String domain;
}
