package com.example.gce_mobile;

public class Mission {
    private  int id ;
    private String name ;
    private String location;
    private String image;


    public  Mission(String name, String location , String image, int id){
        this.name =name ;
        this.location = location;
        this.image =image ;
        this.id = id;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
