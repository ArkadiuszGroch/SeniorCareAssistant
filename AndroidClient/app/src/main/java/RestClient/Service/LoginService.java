package RestClient.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;
import RestClient.AnswerTemplate;
import RestClient.Entity.Senior;

/**
 * Created by goco on 05.11.2017.
 */

public class LoginService {
    private String url = RestConfiguration.LOGIN;
    private AnswerTemplate answer = null;
    private Senior senior;

    public LoginService(Senior senior) {
        this.senior = senior;
        this.answer = new AnswerTemplate();
    }

    public AnswerTemplate execute() {
        return execute(senior);
    }

    public AnswerTemplate execute(Senior senior) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Senior> request = new HttpEntity<Senior>(senior);

            ResponseEntity<String> response = restTemplate
                    .exchange(url, HttpMethod.POST, request, String.class);
            if (response.getStatusCode().toString().equals("200")) {
                answer.setCode(Integer.parseInt(response.getStatusCode().toString()));
                answer.setObject(response.getBody());
            } else {
                answer.setErrorCode(AnswerTemplate.ErrorCode.INCORRENT_LOGIN_OR_PASSWORD);
            }
        } catch (Exception ex) {
            answer.setErrorCode(AnswerTemplate.ErrorCode.CONNECTION_ERROR);
        }
        return answer;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AnswerTemplate getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerTemplate answer) {
        this.answer = answer;
    }

    public Senior getSenior() {
        return senior;
    }

    public void setSenior(Senior senior) {
        this.senior = senior;
    }
}