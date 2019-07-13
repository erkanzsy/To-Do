package com.todoapp.db;

import com.todoapp.model.Item;
import com.todoapp.model.ItemList;
import com.todoapp.model.User;
import com.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DNInit implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        User user = new User("admin",passwordEncoder.encode("admin"),"ADMIN","READ,WRITE");

        ItemList itemList = new ItemList("Itemlist 1",user);
        ItemList itemList2 = new ItemList("Itemlist 2",user);

        Item item = new Item("Onu yap",itemList);
        item.setCreateDate(LocalDate.now());
        item.setDeadline(LocalDate.of(2012,9,21));
        Item item1 = new Item("Bunu yap",itemList);
        item1.setCreateDate(LocalDate.now());
        item1.setDeadline(LocalDate.of(2011,12,2));
        Item item2 = new Item("Bunu yapma",itemList);
        item2.setCreateDate(LocalDate.now());
        item2.setDeadline(LocalDate.of(2021,12,2));

        List<Item> items = new ArrayList<>();

        items.add(item);
        items.add(item1);
        items.add(item2);

        itemList.setItemList(items);

        user.setLists(Arrays.asList(itemList,itemList2));

        userRepository.save(user);

        user = new User("1",passwordEncoder.encode("1"),"USER","READ");

        userRepository.save(user);



    }
}
