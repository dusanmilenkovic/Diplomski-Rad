/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdyc.njtclient.action;

import com.wdyc.njtclient.constants.Constants;
import com.wdyc.njtclient.dto.CarPartDTO;
import com.wdyc.njtclient.rest.ws.RestWSClient;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Dusan
 */
public class ChangeCarPartAction extends AbstractAction {

    @Override
    public String execute(HttpServletRequest request) {

        RestWSClient.getInstance().setTarget(Constants.CAR_PARTS_PATH);
        Response response = RestWSClient.getInstance().getAll_JSON();

        if (response.getStatus() == 200) {
            List<CarPartDTO> carParts = response.readEntity(new GenericType<List<CarPartDTO>>() {});

            for(CarPartDTO carPart : carParts) {
                if(carPart.getId().equalsIgnoreCase("1")) {
                    carParts.remove(carPart);
                    break;
                }
            }
            request.setAttribute("car_parts", carParts);

            return "update_part";
        } else {
            String poruka = response.readEntity(String.class);
            request.setAttribute("message", poruka);
            return "admin";
        }

    }

}
