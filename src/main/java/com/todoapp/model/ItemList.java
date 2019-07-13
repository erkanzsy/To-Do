package com.todoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "itemLists")
public class ItemList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String itemListname;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "itemList", cascade = CascadeType.ALL)
    private List<Item> items;

    public ItemList() {
    }

    public ItemList(String itemListname,User user) {
        this.itemListname = itemListname;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemListname() {
        return itemListname;
    }

    public void setItemListname(String itemListname) {
        this.itemListname = itemListname;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItemList() {
        return items;
    }

    public void setItemList(List<Item> itemList) {
        this.items = itemList;
    }

    @Override
    public String toString() {
        return "ItemList{" +
                "id=" + id +
                ", itemListname='" + itemListname + '\'' +
                '}';
    }
}
