package realEstateApp.models;

import javax.persistence.*;
import java.lang.reflect.Type;

@Entity
@Table(name = "Estates")
public class Estates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEstates")
    private int id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressID")
    private Addresses address;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materialID")        
    private Materials material;
    @Column(name = "price")
    private int price;
    @Column(name = "baths")
    private int baths;
    @Column(name = "bedrooms")
    private int bedrooms;
    @Column(name = "title")
    private String title;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeID")
    private Type type;


    public Estates(){}

    public Estates(int price, int baths, int bedrooms, String title) {
        this.price = price;
        this.baths = baths;
        this.bedrooms = bedrooms;
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Addresses getAddress() {
        return address;
    }


    public int getBaths() {
        return baths;
    }

    public void setBaths(int baths) {
        this.baths = baths;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public Materials getMaterial() {
        return material;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Id: %d, Address: %s," +
                " Material: %s, Price: %d," +
                " Baths: %d," +
                " Bedrooms: %d" +
                " Type: %s" +
                " Title: %s",
                getId(),getAddress(),
                getMaterial(),getPrice(),
                getBaths(),getBedrooms(),
                getType(),getTitle());

    }
}
