package com.todoapp.controller;

import com.todoapp.model.Item;
import com.todoapp.model.ItemList;
import com.todoapp.model.User;
import com.todoapp.repository.ItemListRepository;
import com.todoapp.repository.ItemRepository;
import com.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class IndexController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemListRepository listRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/")
    public String index(Principal user,Model model){
        model.addAttribute("user",userRepository.findByUsername(user.getName()));
        model.addAttribute("newList",new ItemList());
        model.addAttribute("newItem",new Item());
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model){
        if (userRepository.findByUsername(user.getUsername()) != null){
            model.addAttribute("user",user);
            model.addAttribute("message","Kullanıcı zaten var");
            return "registration";
        }else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles("USER");
            user.setPermissions("READ");
            userRepository.save(user);
            model.addAttribute("message",user.getUsername() + " adlı kullanıcı kaydedildi");
            return "login";
        }
    }

    @GetMapping("/show/list/{id}")
    public String show(@PathVariable long id, Model model,Principal user){
        model.addAttribute("user",userRepository.findByUsername(user.getName()));
        model.addAttribute("list",listRepository.findById(id).get());
        model.addAttribute("newList",new ItemList());
        model.addAttribute("newItem",new Item());
        return "index";
    }

    @GetMapping("/incele/item/{itemid}/{listid}")
    public String inceleItem(@PathVariable long itemid,@PathVariable long listid, Model model,Principal user){
        model.addAttribute("user",userRepository.findByUsername(user.getName()));
        model.addAttribute("list",listRepository.findById(listid).get());
        model.addAttribute("newList",new ItemList());
        model.addAttribute("newItem",itemRepository.findById(itemid).get());
        model.addAttribute("tip","incele");
        return "index";
    }

    @GetMapping("/delete/item/{itemid}/{listid}")
    public String delete(@PathVariable long itemid,@PathVariable long listid, Model model,Principal user){
        itemRepository.deleteById(itemid);
        model.addAttribute("user",userRepository.findByUsername(user.getName()));

        if (listRepository.findById(listid).get().getItemList().size() == 0)
            listRepository.deleteById(listid);

        else
            model.addAttribute("list",listRepository.findById(listid).get());


        model.addAttribute("user",userRepository.findByUsername(user.getName()));
        model.addAttribute("newList",new ItemList());
        model.addAttribute("newItem",new Item());
        return "index";
    }

    @PostMapping("/newItemList")
    public String newItemList(@ModelAttribute ItemList itemList,Principal user,Model model){
        itemList.setUser(userRepository.findByUsername(user.getName()));

        listRepository.save(itemList);

        model.addAttribute("user",userRepository.findByUsername(user.getName()));
        model.addAttribute("newList",new ItemList());
        return "index";
    }


    @PostMapping("/newItem/{itemlistid}")
    public String newItem(@ModelAttribute Item item, Principal user, Model model,@PathVariable long itemlistid ){
        item.setItemList(listRepository.findById(itemlistid).get());
        item.setCreateDate(LocalDate.now());

        itemRepository.save(item);

        model.addAttribute("user",userRepository.findByUsername(user.getName()));
        model.addAttribute("list",listRepository.findById(itemlistid).get());
        model.addAttribute("newList",new ItemList());
        model.addAttribute("newItem",new Item());
        return "index";
    }

    @PostMapping("/incele/kaydet/{itemid}/{itemlistid}")
    public String updateItem(@ModelAttribute Item item,@PathVariable long itemid,@PathVariable long itemlistid, Model model,Principal user){

        itemRepository.deleteById(itemid);

        item.setItemList(listRepository.findById(itemlistid).get());
        item.setCreateDate(LocalDate.now());

        itemRepository.save(item);

        model.addAttribute("user",userRepository.findByUsername(user.getName()));
        model.addAttribute("list",listRepository.findById(item.getItemList().getId()).get());
        model.addAttribute("newList",new ItemList());
        model.addAttribute("newItem",new Item());
        return "index";
    }

}
