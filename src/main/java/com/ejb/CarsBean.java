package com.ejb;

import com.common.CarDto;
import com.parking.parkinglot.entities.Car;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {

    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> findAllCars(){
        LOG.info("findAllCars");
        try {
            TypedQuery<Car> typedQuery =
                    entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        }catch (Exception ex){
            throw new EJBException(ex);
        }
    }

    private List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> carDtoList = new ArrayList<>();

        for(Car c : cars){
            carDtoList.add(new CarDto(c.getId(), c.getLicensePlate(), c.getParkingSpot(), c.getOwner().getUsername()));
        }
        return carDtoList;
    }
}
