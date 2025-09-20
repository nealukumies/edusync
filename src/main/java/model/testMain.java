package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.Authetication.Delete;
import model.Authetication.Login;
import model.Authetication.Register;
import model.Authetication.Update;

public class testMain {
    public static void main(String[] args) throws JsonProcessingException {
        System.out.println(Login.tryLogin("katti@matikainen.fi", "salasana"));
        Delete.tryDelete();
    }
}


//Login.tryLogin("matti@matikainen.fi", "salasana")
//Update.tryUpdate("Katti", "katti@matikainen.fi")
//Register.tryRegister("Matti", "katti@matikainen.fi", "salasana")
//Delete.tryDelete()

