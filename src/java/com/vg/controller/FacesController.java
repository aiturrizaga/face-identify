package com.vg.controller;

import com.vg.dao.FacesPerson;
import com.vg.model.Persona;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import lombok.Data;

@Named(value = "facesController")
@SessionScoped
@Data
public class FacesController implements Serializable {

    private Persona persona = new Persona();

    //METODOS AGRUPADOS
    public void firstPersonImage() throws Exception {
        createPerson();
        addFacesPerson();
        trainFacesPerson();
    }

    public void secondPersonImage() throws IOException {
        detectFacesPerson();
        identifyFacesPerson();
    }

    //METODOS MAIN
    public void createPerson() throws Exception {
        FacesPerson dao;
        try {
            dao = new FacesPerson();
            dao.createPersonas(persona, "Nuevo usuario");
        } catch (IOException e) {
            throw e;
        }
    }

    public void addFacesPerson() throws IOException {
        FacesPerson dao;
        try {
            dao = new FacesPerson();
            dao.addFacesPersonas(persona, "NewFace");
        } catch (IOException e) {
            throw e;
        }
    }

    public void trainFacesPerson() throws IOException {
        FacesPerson dao;
        try {
            dao = new FacesPerson();
            dao.trainPersonas();
        } catch (IOException e) {
            throw e;
        }
    }

    public void detectFacesPerson() throws IOException {
        FacesPerson dao;
        try {
            dao = new FacesPerson();
            dao.detectFacesPersona(persona);
        } catch (IOException e) {
            throw e;
        }
    }

    public void identifyFacesPerson() throws IOException {
        FacesPerson dao;
        try {
            dao = new FacesPerson();
            dao.identifyFacesPersona(persona);
        } catch (IOException e) {
            throw e;
        }
    }

}
