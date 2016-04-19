package gvillani.it.pinnedlistdemo;

import com.gvillani.pinnedlist.GroupListWrapper;

/**
 * Created by Giuseppe on 09/04/2016.
 */
public class Contact implements GroupListWrapper.Selector{
    private String name;
    private String surname;
    private Integer photo;

    public Contact(String name, String surname, Integer photo) {
        this.name = name;
        this.surname = surname;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhoto() {
        return photo;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", photo=" + photo +
                '}';
    }

    @Override
    public String select() {
        return getName()+getSurname();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
