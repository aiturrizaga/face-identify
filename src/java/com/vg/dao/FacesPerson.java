package com.vg.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vg.model.Persona;
import com.vg.services.Configuration;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class FacesPerson {

    //PASO 01
    public void createPersonas(Persona per, String Descripcion) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        JsonParser convert = new JsonParser();
        try {
            StringEntity bodyInitial = new StringEntity("{\n"
                    + "    \"name\": \"" + per.getUrl() + "\",\n"
                    + "    \"userData\": \"" + Descripcion + "\"\n"
                    + "}");

            HttpPost request = new HttpPost(Configuration.Location + "/persongroups/usuarios/persons");
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Ocp-Apim-Subscription-Key", Configuration.key);
            request.setEntity(bodyInitial);

            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            JsonObject object = convert.parse(EntityUtils.toString(entity)).getAsJsonObject();
            per.setPersonId(object.get("personId").getAsString());
        } catch (IOException e) {
            throw e;
        }
    }

    //PASO 02
    public void addFacesPersonas(Persona per, String Descripcion) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            StringEntity bodyInitial = new StringEntity("{\n"
                    + "    \"url\": \"" + per.getUrl() + "\"\n"
                    + "}");

            HttpPost request = new HttpPost(Configuration.Location + "/persongroups/usuarios/persons/" + per.getPersonId() + "/persistedFaces?userData=" + Descripcion);
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Ocp-Apim-Subscription-Key", Configuration.key);
            request.setEntity(bodyInitial);

            httpClient.execute(request);
        } catch (IOException e) {
            throw e;
        }
    }

    //PASO 03
    public void trainPersonas() throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost request = new HttpPost(Configuration.Location + "/persongroups/usuarios/train");
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Ocp-Apim-Subscription-Key", Configuration.key);

            httpClient.execute(request);
        } catch (IOException e) {
            throw e;
        }
    }

    //PASO 04
    public void detectFacesPersona(Persona per) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        JsonParser convert = new JsonParser();
        try {
            StringEntity bodyInitial = new StringEntity("{\n"
                    + "    \"url\": \"" + per.getUrlDetect() + "\"\n"
                    + "}");

            HttpPost request = new HttpPost(Configuration.Location + "/detect");
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Ocp-Apim-Subscription-Key", Configuration.key);
            request.setEntity(bodyInitial);

            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            JsonArray array = convert.parse(EntityUtils.toString(entity)).getAsJsonArray();
            JsonObject object = array.get(0).getAsJsonObject();
            per.setFaceId(object.get("faceId").getAsString());
        } catch (IOException e) {
            throw e;
        }
    }

    //PASO 05
    public void identifyFacesPersona(Persona per) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        JsonParser convert = new JsonParser();
        try {
            StringEntity bodyInitial = new StringEntity("{\n"
                    + "    \"personGroupId\": \"usuarios\",\n"
                    + "    \"faceIds\": [\n"
                    + "        \"" + per.getFaceId() + "\"\n"
                    + "    ],\n"
                    + "    \"maxNumOfCandidatesReturned\": 1,\n"
                    + "    \"confidenceThreshold\": 0.5\n"
                    + "}");

            HttpPost request = new HttpPost(Configuration.Location + "/identify");
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Ocp-Apim-Subscription-Key", Configuration.key);
            request.setEntity(bodyInitial);

            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            JsonArray array = convert.parse(EntityUtils.toString(entity)).getAsJsonArray();
            JsonObject object = array.get(0).getAsJsonObject();
            JsonArray candidates = object.getAsJsonArray("candidates");
            JsonObject object2 = candidates.get(0).getAsJsonObject();
            per.setConfianza(object2.get("confidence").getAsString());
        } catch (IOException e) {
            throw e;
        }
    }

}
