package refai.project.manager;

import refai.project.model.Delivery;
import refai.project.model.Customer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryManager {
    private Map<String, Delivery> deliveries = new HashMap<>();

    public Delivery scheduleDelivery(String customerId, String orderId, String scheduledTimeStr) {
        try {
            Delivery delivery = new Delivery(customerId, orderId, scheduledTimeStr);
            deliveries.put(delivery.getDeliveryId(), delivery);
            return delivery;
        } catch (ParseException e) {
            System.out.println("*** Error: Invalid date format - " + e.getMessage());
            return null;
        }
    }

    public Delivery getDelivery(String deliveryId) {
        return deliveries.get(deliveryId);
    }

    public List<Delivery> getUpcomingDeliveries() {
        List<Delivery> upcoming = new ArrayList<>();
        Date now = new Date();

        for (Delivery delivery : deliveries.values()) {
            if ("SCHEDULED".equals(delivery.getStatus()) && delivery.getScheduledTime().after(now)) {
                upcoming.add(delivery);
            }
        }

        return upcoming;
    }

    public List<Delivery> getUpcomingDeliveriesForCustomer(String customerId) {
        List<Delivery> upcoming = new ArrayList<>();
        Date now = new Date();

        for (Delivery delivery : deliveries.values()) {
            if (customerId.equals(delivery.getCustomerId()) &&
                    "SCHEDULED".equals(delivery.getStatus()) &&
                    delivery.getScheduledTime().after(now)) {
                upcoming.add(delivery);
            }
        }

        return upcoming;
    }

    public void markDeliveryAsSpecial(String deliveryId) {
        Delivery delivery = deliveries.get(deliveryId);
        if (delivery != null) {
            delivery.setSpecialOrder(true);
        }
    }
}