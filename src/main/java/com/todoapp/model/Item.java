package com.todoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;


@Entity(name = "items")
public class Item {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String icerik;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "itemList_id" )
    private ItemList itemList;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    @Transient
    private int status; // 0 now < deadline

    public Item(String icerik,ItemList itemList) {
        this.icerik = icerik;
        this.createDate = LocalDate.now();
        this.itemList = itemList;
    }

    public Item() {
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public ItemList getItemList() {
        return itemList;
    }

    public void setItemList(ItemList itemList) {
        this.itemList = itemList;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean getStatus() {
        return deadline.compareTo(LocalDate.now()) < 0 ? false : true;
    }

    @Override
    public String toString() {
        return "Item{" +
                "icerik='" + icerik + '\'' +
                '}';
    }
}
