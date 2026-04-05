package main.java.com.xrusa.animals.services;

import java.util.List;

import main.java.com.xrusa.animals.entities.StandingOrder;
import main.java.com.xrusa.animals.enums.Role;
import main.java.com.xrusa.animals.enums.StandingOrderStatus;

public interface StandingOrderService {

  List<StandingOrder> getAllStandingOrders(Role role);

  void changeStatndingOrderStatus(Role role, StandingOrderStatus status, String orderId);

  void createStandingOrder(StandingOrder standingOrder, Role role);

  List<StandingOrder> getStandingOrdersByCustomerId(String userId);

}