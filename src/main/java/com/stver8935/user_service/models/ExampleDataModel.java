package com.stver8935.user_service.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@Getter
@Setter
public class ExampleDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String text;

    public ExampleDataModel(){}

    public ExampleDataModel(long id, String text){
        this.id = id;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }
}
