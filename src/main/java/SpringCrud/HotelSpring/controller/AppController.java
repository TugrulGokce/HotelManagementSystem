/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpringCrud.HotelSpring.controller;

import SpringCrud.HotelSpring.model.Rezervation;
import SpringCrud.HotelSpring.model.Room;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import SpringCrud.HotelSpring.model.User;
import SpringCrud.HotelSpring.services.RezervationServices;
import SpringCrud.HotelSpring.services.RoomServices;
import SpringCrud.HotelSpring.services.UserServices;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class AppController {

    @Autowired
    private UserServices service1;

    @Autowired
    private RoomServices roomService;

    @Autowired
    private RezervationServices reserveRepo;


    @RequestMapping("/")
    public String viewHomePage1(Model model, Room room, HttpServletRequest request, HttpServletResponse response) {
        List<Room> listRoom = roomService.listAll();
        model.addAttribute("listRoom", listRoom);

        Cookie cookie = new Cookie("AdminSign", "ADMIN");
        Cookie cookie1 = new Cookie("HMSCookie", "HotelManagementSystem");

        cookie.setMaxAge(15);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        cookie1.setMaxAge(15);
        cookie1.setSecure(true);
        cookie1.setHttpOnly(true);
        cookie1.setPath("/");

        response.addCookie(cookie);
        response.addCookie(cookie1);

        return "homepage";
    }

    @RequestMapping("/list_user")
    public String viewListUserPage(Model model, User user) {
        List<User> listUsers = service1.listAll();
        model.addAttribute("listUsers", listUsers);
        //System.out.println("List:" + listUsers);
        return "index";
    }

    @RequestMapping("/new")
    public String newUserPage(Model model) {
        User user = new User();
        model.addAttribute(user);
        return "new_user";
    }

    @RequestMapping("/newRoom")
    public String newRoomPage(Model model) {
        Room room = new Room();
        model.addAttribute(room);
        return "new_room";
    }

    @RequestMapping(value = "/saveRoom", method = RequestMethod.POST)
    public String saveRoom(@ModelAttribute("room") Room room) {
        roomService.save(room);
        return "redirect:/";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user)  {
        user.setEnabled(true);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        service1.save(user);
        return "redirect:/list_user";
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User user) {
        user.setEnabled(true);
        service1.save(user);
        return "redirect:/list_user";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditUserPage(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_student");
        User user = service1.get(id);
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteUserPage(@PathVariable(name = "id") Long id) {
        service1.delete(id);
        return "redirect:/list_user";
    }

    @RequestMapping("/deleteRoom/{id}")
    public String deleteRoomPage(@PathVariable(name = "id") Long id) {
        roomService.delete(id);
        return "redirect:/";
    }

    @RequestMapping("/reservedRoom/{id}")
    public ModelAndView reservedRoomPage(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("reserve_room");
        Room room = roomService.get(id);
        mav.addObject("room", room);
        return mav;
    }

    @RequestMapping(value = "/reservRoom", method = RequestMethod.POST)
    public String reservRoom(@ModelAttribute("room") Rezervation rezervation) {
        reserveRepo.save(rezervation);
        return "redirect:/";
    }

    @RequestMapping("/reserve_list")
    public String viewListReservePage(Model model, Rezervation rezervation) {
        List<Rezervation> listReserveRoom = reserveRepo.listAll();
        model.addAttribute("listReserveRoom", listReserveRoom);
        return "reserved_list";
    }

    @RequestMapping("/deleteRez/{id}")
    public String deleteRezPage(@PathVariable(name = "id") Long id) {
        reserveRepo.delete(id);
        return "redirect:/reserve_list";
    }
}
