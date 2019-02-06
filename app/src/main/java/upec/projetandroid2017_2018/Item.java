package upec.projetandroid2017_2018;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

/**
 * Created by greg9 on 01/03/2018.
 */

public class Item {
    private String id;
    private String name;
    private String nbElements;
    private ItemType type;
    public Item(String id, String name , ItemType type) {
        this.id=id;
        this.name=name;
        this.type=type;
        nbElements="";
    }

    public Item(String id, String name ,String nbElements, ItemType type) {
        this.id=id;
        this.name=name;
        this.nbElements=nbElements;
        this.type=type;
    }


    public String getId() {
        return id;
    }

    public ItemType getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNbElements() {
        return nbElements;
    }
}
