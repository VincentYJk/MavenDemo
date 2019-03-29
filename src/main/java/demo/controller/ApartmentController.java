package demo.controller;

import demo.dao.ApartmentDao;
import demo.model.ApartmentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2019/3/29 17:16
 */
@RestController
@RequestMapping("/ac")
public class ApartmentController {
    @Autowired
    private ApartmentDao apartmentDao;
    
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public String query(@RequestParam(required = false,defaultValue = "梁明辉") String name) {
        ApartmentModel apartmentModel = apartmentDao.queryApartmentValueByName(name);
        return apartmentModel.toString();
    }
}
