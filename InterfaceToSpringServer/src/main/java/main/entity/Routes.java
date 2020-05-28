package main.entity;

import org.json.JSONObject;

public class Routes {
    private Long id;
    private String name;

    public Routes() {
        this.id = (long)-1;
        this.name = "";
    }

    public Routes(String name) {
        this.name = name;
    }

    public Routes(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Routes getRoutesFromJSON(JSONObject object) {
        Routes routes = new Routes();
        if (object != null) {
            routes.setName(object.getString("name"));
            routes.setId(object.getLong("id"));
        }
        return routes;
    }

    public String getStringToPostRequest() {
        return "{\"name\": \"" + name + "\"}";
    }

    @Override
    public String toString() {
        return "Routes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
