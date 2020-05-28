package main.serverAccess;


import main.entity.*;
import okhttp3.*;
import org.json.*;

import javax.print.DocFlavor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ServerAccess {
    private String bearerToken;
    private String role;

    public ServerAccess() {
    }

    public ServerAccess(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public String getRole() {
        return role;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public boolean auth(String userName, String password) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n\t\"userName\":\"" + userName
                + "\",\n\t\"password\":\"" + password + "\"\n}");
        Request request = new Request.Builder()
                .url("http://localhost:8080/bat/auth/singin")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        JSONObject object = new JSONObject(response.body().string());
        if (object.has("token")) {
            User user = new User(object.getString("userName"), object.getString("token"));
            role = object.getJSONArray("role").get(0).toString();
            this.bearerToken = user.getToken();
            return true;
        }
        return false;
    }

    private String getRequest(String requestUrl) throws Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(requestUrl)
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + bearerToken)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            return response.body().string();
        }
        return null;
    }

    private String postRequest(String requestUrl, String requestBody) throws Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder()
                .url(requestUrl)
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + bearerToken)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            return response.body().string();
        }
        return null;
    }

    public List<Journal> findAllJournal() throws Exception {
        List<Journal> list = new ArrayList<>();
        StringBuilder bs = new StringBuilder(Objects.requireNonNull(getRequest("http://localhost:8080/bat/journals")));
        bs.insert(0, "{");
        bs.insert(1, " \"journals\" : ");
        bs.insert(bs.length(), "}");
        JSONObject object = new JSONObject(bs.toString());
        JSONArray array = object.getJSONArray("journals");
        for (int i = 0; i < array.length(); i++) {
            JSONObject jurmObj = array.getJSONObject(i);
            Journal journal = Journal.getJournalsFromJSON(jurmObj);
            list.add(journal);
        }
        return list;
    }

    public Journal findJournalById(Long id) throws Exception{
        List<Journal> list = findAllJournal();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                return list.get(i);
            }
        }
        return null;
    }

    public Journal findJournalByRouteId(Long id) throws Exception {
        List<Journal> list = findAllJournal();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRoutes_id().getId().equals(id)) {
                return list.get(i);
            }
        }
        return null;
    }

    public Auto findAutoById(Long id) throws Exception {
        List<Auto> list = findAllAuto();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                return list.get(i);
            }
        }
        return null;
    }

    public List<Auto> findAllAuto() throws Exception{
        List<Auto> list = new ArrayList<>();
        StringBuilder bs = new StringBuilder(Objects.requireNonNull(getRequest("http://localhost:8080/bat/autos")));
        bs.insert(0, "{");
        bs.insert(1, " \"auto\" : ");
        bs.insert(bs.length(), "}");
        JSONObject object = new JSONObject(bs.toString());
        JSONArray array = object.getJSONArray("auto");
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            Auto auto = Auto.getAutoFromJSON(obj);
            list.add(auto);
        }
        return list;
    }

    public List<AutoPersonnel> findAllAutoPersonnel() throws Exception{
        List<AutoPersonnel> autoPersonnels = new ArrayList<>();
        StringBuilder bs = new StringBuilder(Objects.requireNonNull(getRequest(
                "http://localhost:8080/bat/autoPersonnels")));
        bs.insert(0, "{");
        bs.insert(1, " \"autoPersonnel\" : ");
        bs.insert(bs.length(), "}");
        JSONObject object = new JSONObject(bs.toString());
        JSONArray array = object.getJSONArray("autoPersonnel");
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            AutoPersonnel personnel = AutoPersonnel.getAutoPersonnelFromJSON(obj);
            autoPersonnels.add(personnel);
        }
        return autoPersonnels;
    }

    public String findAllRoutes() throws Exception{
        StringBuilder stringBuilder = new StringBuilder();

        StringBuilder bs = new StringBuilder(Objects.requireNonNull(getRequest(
                "http://localhost:8080/bat/routes")));
        bs.insert(0, "{");
        bs.insert(1, " \"routes\":");
        bs.insert(bs.length(), "}");
        JSONObject object = new JSONObject(bs.toString());
        JSONArray array = object.getJSONArray("routes");
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            Routes routes = Routes.getRoutesFromJSON(obj);
            stringBuilder.append(routes.toString() + "\n");
        }
        return stringBuilder.toString();
    }

    public void insertRoutes(Routes routes) throws Exception {
        postRequest("http://localhost:8080/bat/addRoute", routes.getStringToPostRequest());
    }

    public void insertRouteWithJournal(Routes routes) throws Exception {
        postRequest("http://localhost:8080/bat/addRouteWithJournal", routes.getStringToPostRequest());
    }

    public Long insertAuto(Auto auto) throws Exception {
        String str = postRequest("http://localhost:8080/bat/addAuto", auto.getStringToPostRequest());
        JSONObject object = new JSONObject(str);
        return object.getLong("id");
    }

    public Long insertAutoPersonnel(AutoPersonnel autoPersonnel) throws Exception {
         String str =  postRequest("http://localhost:8080/bat/addAutoPersonnel", autoPersonnel.getStringToPostRequest());
         JSONObject object = new JSONObject(str);
         return object.getLong("id");
    }

    public void setAutoInJournal(Long auto_id, Long journal_id) throws Exception {
        Auto auto = findAutoById(auto_id);
        Journal journal = findJournalById(journal_id);

        if (journal != null) {
           String str = postRequest("http://localhost:8080/bat/setAutoInJournal/" + journal.getId(), auto.getStringToPostRequest());
           System.out.println(str.toString());
        }
    }

    public void setTimeOutInJournal(Long id) throws Exception {
        Date date = new Date();
        String str = date.toString().replaceAll(" ", "_");
        System.out.println(postRequest("http://localhost:8080/bat/setTimeOutInJournal/" + id, str));
    }

    public void setTimeInInJournal(Long id) throws Exception {
        Date date = new Date();
        String str = date.toString().replaceAll(" ", "_");
        System.out.println(postRequest("http://localhost:8080/bat/setTimeInInJournal/" + id, str));
    }

    public void setAutoPersonnelInAuto(Long id, AutoPersonnel autoPersonnel) throws Exception{
        postRequest("http://localhost:8080/bat/setAutoPersonnelInAuto/" + id, autoPersonnel.getStringToPostRequest());
    }

    public void setAutoWithPersonnel(Long id, Auto auto) throws Exception {
        System.out.println(postRequest("http://localhost:8080/bat/setAutoWithPersonnel/" + id, auto.getStringToPostSet()));
    }

    public String getRoutesData() throws Exception{
        StringBuilder stringBuilder = new StringBuilder();
        List<Journal> list = findAllJournal();
        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i).getTimeIn().equals("Car_In_Park")) ||
                    (list.get(i).getTimeIn().equals("Car_Didn't_Return_Yet"))) {
                Long id = list.get(i).getRoutes_id().getId();
                String name = list.get(i).getRoutes_id().getName();
                String Status = (list.get(i).getTimeOut().equals("Car_In_Park")) ? "Didn't_Get" : "Got";
                stringBuilder.append(id + " " + name + " " + Status + "\n");
            }
        }
        return stringBuilder.toString();
    }

    public String getBigDataFromJournal() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        List<Journal> list = findAllJournal();
        for (int i = 0; i < list.size(); i++) {
            Long id = list.get(i).getId();
            String timeOut = list.get(i).getTimeOut();
            String timeIn = list.get(i).getTimeIn();
            Long auto_id = list.get(i).getAuto_id().getId();
            Long routes_id = list.get(i).getRoutes_id().getId();

            stringBuilder.append(id + " " + timeOut + " " + timeIn + " " + routes_id + " " + auto_id);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
