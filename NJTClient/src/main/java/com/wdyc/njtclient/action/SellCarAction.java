/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdyc.njtclient.action;

import com.wdyc.njtclient.constants.Constants;
import com.wdyc.njtclient.dto.CarDTO;
import com.wdyc.njtclient.dto.ClientDTO;
import com.wdyc.njtclient.dto.UserDTO;
import com.wdyc.njtclient.rest.ws.RestWSClient;
import com.wdyc.njtclient.validation.UserValidator;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

/**
 *
 * @author Dusan
 */
public class SellCarAction extends AbstractAction {

    @Override
    public String execute(HttpServletRequest request) {
        try {
            String carId = request.getParameter("car_id");
            String buyerUsername = request.getParameter("buyer");
            String rowIndex = request.getParameter("rowIndex");
            
            if (!UserValidator.getInstance().validateBuyer(buyerUsername)) {
            request.setAttribute("message", "Kupac sa tim podacima ne postoji!");
            request.setAttribute("buyer", buyerUsername);
            request.setAttribute("index", rowIndex);
            request.setAttribute("invalid", true);
            SellCarPageAction sellCarAction = new SellCarPageAction();
            return sellCarAction.execute(request);
        }

            RestWSClient.getInstance().setTarget(Constants.USERS_PATH);
            UserDTO buyer = RestWSClient.getInstance().getByParameter_JSON(UserDTO.class, Constants.USER_USERNAME_PARAM, buyerUsername);
            ClientDTO newOwner = new ClientDTO();
            newOwner.setId(buyer.getId());
            CarDTO car = new CarDTO();
            car.setOwner(newOwner);
            RestWSClient.getInstance().setTarget(Constants.CARS_PATH);
            Response response = RestWSClient.getInstance().updateById_JSON(car, carId);
            if (response.getStatus() == 200) {
                CarDTO soldCar = response.readEntity(CarDTO.class);
                request.setAttribute("message", "Sistem ne moze da proda automobil!");
            } else {
                String errorMessage = response.readEntity(String.class);
                request.setAttribute("message", errorMessage);
            }
            return "index";
        } catch (Exception ex) {
            request.setAttribute("message", "Sistem ne moze da proda automobil!");
            return "index";
        }
    }

}
