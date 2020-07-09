package com.example.prm02.Model;

public class ImageModel {
    private String description;
    private String imageName;

    public ImageModel(String description, String imageName) {
        this.description = description;
        this.imageName = imageName+".jpg";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "ImageModel{" +
                "description='" + description + '\'' +
                ", imageName='" + imageName+".jpg" + '\'' +
                '}';
    }
}
