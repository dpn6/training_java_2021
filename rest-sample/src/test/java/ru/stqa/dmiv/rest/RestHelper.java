package ru.stqa.dmiv.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import java.io.IOException;
import java.util.Set;

import static com.google.gson.JsonParser.parseString;

public class RestHelper {

  public Set<Issue> getIssues() throws IOException {
    String json = RestAssured.get("https://bugify.stqa.ru/api/issues.json").asString();
    JsonElement parsed = parseString(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
    }.getType());
  }

  public int createIssue(Issue newIssue) throws IOException {
    String json = RestAssured.given()
            .parameter("subject", newIssue.getSubject())
            .parameter("description", newIssue.getDescription())
            .post("https://bugify.stqa.ru/api/issues.json").asString();

    JsonElement parsed = parseString(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

  public boolean isIssueOpen(int issueId) {
    String json = RestAssured.get(String.format("https://bugify.stqa.ru/api/issues/%s.json", issueId)).asString();
    JsonElement parsed = parseString(json);
//    String stateName = parsed.getAsJsonObject().get("issues[0].state_name").getAsString();

    String stateName = parsed.getAsJsonObject().get("issues").getAsJsonArray().get(0).getAsJsonObject().get("state_name").getAsString();

    if(! stateName.equals("Closed")){
      return true;
    }
    return false;
  }

  public void closeIssue(Issue toCloseIssue) {
//"issue_id": "1287",
//     "state": "3"
//    "state_name": Closed
//    String json1 = RestAssured.get("https://bugify.stqa.ru/api/issues/1287.json").asString();
//    JsonElement parsed = parseString(json1);

String urlPost = String.format("https://bugify.stqa.ru/api/issues/%s.json", toCloseIssue.getId());
    RestAssured.given()
            .parameter("issue[state]", 3)
//            .parameter("state", 3)
            .parameter("method", "update")
            .post(urlPost);

//    JsonElement parsed = parseString(json);
//    return parsed.getAsJsonObject().get("state_name").getAsString();
  }
}
