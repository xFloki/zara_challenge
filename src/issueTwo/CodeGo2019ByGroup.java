package issueTwo;

import java.io.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.*;
import java.util.Map.*;
import java.util.function.*;
import java.util.stream.*;

public class CodeGo2019ByGroup {

    static final String SEMICOLON = ";";
    static final String COLON = ",";
    static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
    static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.000");

    static final Float EXPERIENCE_PRICE_BY_HOUR = 0.03f;

    static enum Warehouse {
        NEW_YORK(-4),
        SAN_FRANCISCO(-7);

        private Integer timeZoneOffset;

        Warehouse(int timeZoneOffset) {
            this.timeZoneOffset = timeZoneOffset;
        }

        static Warehouse fromName(String input) {
            switch (input) {
            case "New York":
                return NEW_YORK;
            case "San Francisco":
                return SAN_FRANCISCO;
            }
            return null;
        }

        public String toName() {
            switch (this) {
            case NEW_YORK:
                return "New York";
            case SAN_FRANCISCO:
                return "San Francisco";
            }
            return null;
        }

        public Integer getTimeZoneOffset() {
            return timeZoneOffset;
        }
    }

    static class Stock {
        final String itemId;
        final Warehouse warehouse;
        final int stock;

        Stock(String itemId, Warehouse warehouse, int stock) {
            this.itemId = itemId;
            this.warehouse = warehouse;
            this.stock = stock;
        }

        public String getItemId() {
            return itemId;
        }

        public Warehouse getWarehouse() {
            return warehouse;
        }

        public int getStock() {
            return stock;
        }

    }

    static class BoxType {
        final String boxType;
        final int maxWeight;
        final int length, width, height; // cm
        final float volume; // dm3

        BoxType(String boxType, int maxWeight, int length, int width, int height, float volume) {
            this.boxType = boxType;
            this.maxWeight = maxWeight;
            this.length = length;
            this.width = width;
            this.height = height;
            this.volume = volume;
        }

        public String getBoxType() {
            return boxType;
        }

        public int getMaxWeight() {
            return maxWeight;
        }

        public int getLength() {
            return length;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public float getVolume() {
            return volume;
        }

    }

    static class CarrierPricing {
        final Warehouse warehouse;
        final String targetState;
        final float volumePrice; // $/dm3

        CarrierPricing(Warehouse warehouse, String targetState, float volumePrice) {
            this.warehouse = warehouse;
            this.targetState = targetState;
            this.volumePrice = volumePrice;
        }

        public Warehouse getWarehouse() {
            return warehouse;
        }

        public String getTargetState() {
            return targetState;
        }

        public float getVolumePrice() {
            return volumePrice;
        }

    }

    static class ShippingHour {
        final DayOfWeek day;
        final LocalTime time;

        ShippingHour(DayOfWeek day, LocalTime time) {
            this.time = time;
            this.day = day;
        }

        public DayOfWeek getDay() {
            return day;
        }

        public LocalTime getTime() {
            return time;
        }

    }

    static class DepartureTime {
        final Warehouse warehouse;
        final String targetState;
        final List<ShippingHour> shippingHours;

        DepartureTime(Warehouse warehouse, String targetState, List<ShippingHour> shippingHours) {
            this.warehouse = warehouse;
            this.targetState = targetState;
            this.shippingHours = shippingHours;
        }

        public Warehouse getWarehouse() {
            return warehouse;
        }

        public String getTargetState() {
            return targetState;
        }

        public List<ShippingHour> getShippingHours() {
            return shippingHours;
        }

    }

    static class CarrierTime {
        final Warehouse warehouse;
        final String targetState;
        final int carrierTime; // in hours

        CarrierTime(Warehouse warehouse, String targetState, int carrierTime) {
            this.warehouse = warehouse;
            this.targetState = targetState;
            this.carrierTime = carrierTime;
        }

        public Warehouse getWarehouse() {
            return warehouse;
        }

        public String getTargetState() {
            return targetState;
        }

        public int getCarrierTime() {
            return carrierTime;
        }

    }

    static class Item {
        final String itemId;
        final int weight;
        final int length, width, height;

        Item(String itemId, int weight, int length, int width, int height) {
            this.itemId = itemId;
            this.weight = weight;
            this.length = length;
            this.width = width;
            this.height = height;
        }

        public String getItemId() {
            return itemId;
        }

        public int getLength() {
            return length;
        }

        public int getWeight() {
            return weight;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

    }

    static class Order {
        final long orderId;
        final LocalDateTime orderDate;
        final String itemId;
        final String targetState;

        Order(long orderId, LocalDateTime orderDate, String itemId, String targetState) {
            this.orderId = orderId;
            this.itemId = itemId;
            this.orderDate = orderDate;
            this.targetState = targetState;
        }

        public long getOrderId() {
            return orderId;
        }

        public LocalDateTime getOrderDate() {
            return orderDate;
        }

        public String getItemId() {
            return itemId;
        }

        public String getTargetState() {
            return targetState;
        }

    }

    static class ShipmentInfo {
        final Order order;
        final Warehouse warehouse;
        final LocalDateTime guaranteedDeliveryDate;
        final String boxType;
        final float shippingPrice;

        ShipmentInfo(Order order, Warehouse warehouse, LocalDateTime guaranteedDeliveryDate, String boxType,
                float shippingPrice) {
            this.order = order;
            this.warehouse = warehouse;
            this.guaranteedDeliveryDate = guaranteedDeliveryDate;
            this.boxType = boxType;
            this.shippingPrice = shippingPrice;
        }

        public Order getOrder() {
            return order;
        }

        public String getItemId() {
            return order.itemId;
        }

        public Warehouse getWarehouse() {
            return warehouse;
        }

        public LocalDateTime getGuaranteedDeliveryDate() {
            return guaranteedDeliveryDate;
        }

        public String getBoxType() {
            return boxType;
        }

        public float getShippingPrice() {
            return shippingPrice;
        }

        public String toCsvLine() {
            return new StringBuilder().append(order.orderId).append(SEMICOLON).append(warehouse.toName())
                    .append(SEMICOLON).append(DATE_PATTERN.format(guaranteedDeliveryDate)).append(SEMICOLON)
                    .append(boxType).append(SEMICOLON).append(DECIMAL_FORMAT.format(shippingPrice)).append(SEMICOLON)
                    .append(DECIMAL_FORMAT.format(getShippingExperiencePrice())).toString();
        }

        public Float getShippingExperiencePrice() {
            long hours = order.orderDate.until(guaranteedDeliveryDate, ChronoUnit.HOURS);
            return hours * EXPERIENCE_PRICE_BY_HOUR;
        }

        public Float getTotalPrice() {
            return getShippingPrice() + getShippingExperiencePrice();
        }

    }

    static class CsvParser {

        public static final Order parseOrder(String inputLine) {
            String[] input = inputLine.split(SEMICOLON);
            LocalDateTime orderDate = LocalDateTime.parse(input[1], DATE_PATTERN);
            return new Order(Long.valueOf(input[0]), orderDate, input[2], input[4]);
        }

        public static final Stock parseStock(String inputLine) {
            String[] input = inputLine.split(SEMICOLON);
            return new Stock(input[0], Warehouse.fromName(input[1]), Integer.valueOf(input[2]));
        }

        public static final BoxType parseBoxType(String inputLine) {
            String[] input = inputLine.split(SEMICOLON);
            return new BoxType(input[0], Integer.valueOf(input[1]), Integer.valueOf(input[2]),
                    Integer.valueOf(input[3]), Integer.valueOf(input[4]), Float.valueOf(input[5].replaceAll(",", ".")));
        }

        public static final CarrierPricing parseCarrierPricings(String inputLine) {
            String[] input = inputLine.split(SEMICOLON);
            String costString = input[2];
            return new CarrierPricing(Warehouse.fromName(input[0]), input[1],
                    Float.valueOf(costString.replaceAll(",", ".")));
        }

        public static final DepartureTime parseDepartureTime(String inputLine) {
            String[] input = inputLine.split(SEMICOLON);
            Warehouse warehouse = Warehouse.fromName(input[0]);

            String departureTimes[] = input[2].split(COLON);
            List<ShippingHour> shippingHours = Arrays.stream(departureTimes).map(new Function<String, ShippingHour>() {

                @Override
                public ShippingHour apply(String departureTime) {
                    String[] values = departureTime.trim().split(" ");
                    DayOfWeek dayOfWeek = DayOfWeek.valueOf(values[0]);
                    LocalTime localTime = LocalTime.parse(values[1]);
                    return new ShippingHour(dayOfWeek, localTime);
                }
            }).collect(Collectors.toList());
            return new DepartureTime(warehouse, input[1], shippingHours);
        }

        public static final CarrierTime parseCarrierTime(String inputLine) {
            String[] input = inputLine.split(SEMICOLON);
            return new CarrierTime(Warehouse.fromName(input[0]), input[1], Integer.valueOf(input[2].split(" ")[0]));
        }

        public static final Item parseItem(String inputLine) {
            String[] input = inputLine.split(SEMICOLON);
            return new Item(input[0], Integer.valueOf(input[2]), Integer.valueOf(input[3]), Integer.valueOf(input[4]),
                    Integer.valueOf(input[5]));
        }
    }

    static class ShipmentsManager {

        final Integer PACKAGE_PREPARATION_HOURS = 4;

        static class NoSuitableBoxException extends RuntimeException {
            private static final long serialVersionUID = 7513400494522133911L;

            public NoSuitableBoxException(String itemId) {
                super("There is no suitable for item " + itemId);
            }
        }

        static class NoSuitableWarehouseException extends RuntimeException {
            private static final long serialVersionUID = 7513400494522133911L;

            public NoSuitableWarehouseException(String itemId, String targetState) {
                super("There is no warehouse with stock and deliver options for item " + itemId + " and target state "
                        + targetState);
            }
        }

        public ShipmentsManager(List<Item> items, List<BoxType> boxTypes, List<CarrierPricing> carrierPricings,
                List<DepartureTime> departureTimes, List<CarrierTime> carrierTimes, List<Stock> initialStocks) {
        }

        public List<ShipmentInfo> findBestShipmentInfo(List<Order> orders) {
            return new ArrayList<ShipmentInfo>();
        }
    }
}

