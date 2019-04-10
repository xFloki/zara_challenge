package issueOne;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class CodeGo2019 {

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
    	
    	private List<BoxType> boxTypes;
		private List<Item> items;
		private List<CarrierPricing> carrierPricings;
		private List<DepartureTime> departureTimes;
		private List<CarrierTime> carrierTimes;
		private List<Stock> initialStocks;

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
        	this.items = items;
        	this.boxTypes = boxTypes;
        	this.carrierPricings = carrierPricings;
        	this.departureTimes = departureTimes;
        	this.carrierTimes = carrierTimes;
        	this.initialStocks = initialStocks;
        }

        public ShipmentInfo findBestShipmentInfo(Order order) {
        	
        	System.out.println("---------------");
        	
        	System.out.println("-----ORDER-----");
        	
        	System.out.println("---------------");
        	
        	
        	System.out.println(order.getOrderDate());
        	System.out.println(order.getOrderDate().getDayOfWeek());
        	System.out.println(order.getTargetState());
        	
        	Item item = this.items.stream()
        			.filter(i -> i.itemId.equals(order.getItemId())).findFirst().orElse(null);
        	
        	BoxType selectedBox = null;
        	// CHOOSE BEST BOX TYPE
        	for (BoxType box : this.boxTypes) {
        		if(box.maxWeight < item.weight) continue;
        		if(box.height < item.height) continue;
        		System.out.println("ANALYZING BOX TYPE " + box.boxType);
        		System.out.println("WIDTH " + box.width);
        		System.out.println("LENGTH " + box.length);
        		if((box.width < item.width || box.length < item.length) 
        				&& (box.width < item.length || box.length < item.width)) {
        			continue;
        		}
        		
        		selectedBox = box;
        		break;
        	}
        	
        	System.out.println("---------------");
        	
        	System.out.println("-----ITEM-----");
        	
        	System.out.println("---------------");
        	
        	System.out.println(item.weight);
        	System.out.println("LENGTH " + item.length);
    		System.out.println("WIDTH " + item.width);
        	System.out.println(item.height);
        	System.out.println(item.getItemId());
        	
        	float shipingPrice = 0f;
        	ShipmentInfo best = null;
        	LocalDateTime dt = order.getOrderDate().plusHours(PACKAGE_PREPARATION_HOURS);
        	System.out.println("----------------");
        	System.out.println("------READY-----");
			System.out.println("----------------");
			System.out.println(dt);
			
			
        	for (Warehouse warehouse : Warehouse.values()) {
        		Stock stock = this.initialStocks.stream()
            			.filter(s -> s.itemId.equals(order.getItemId()) && s.warehouse.equals(warehouse)).findFirst().orElse(null);
        		if(stock.stock == 0) continue;
        		
        		CarrierPricing car = this.carrierPricings.stream()
            			.filter(c -> c.warehouse.equals(warehouse) && c.targetState.equals(order.targetState))
            			.findFirst().orElse(null);
        		
        		System.out.println("------------------------");
            	
            	System.out.println("-----SHIPPING PRICE-----");
            	
            	System.out.println("------------------------");
        		System.out.println(car.getTargetState());
        		System.out.println(car.getVolumePrice());
        		System.out.println(car.getWarehouse());
        		
        		System.out.println("------------------------");
        		
        		shipingPrice = selectedBox.getVolume() * car.getVolumePrice();
        		
        		System.out.println(shipingPrice);
        		
        		System.out.println("------------------------");
        		
        		List<DepartureTime> departures = this.departureTimes.stream()
            			.filter(d -> d.targetState.equals(car.targetState) && d.warehouse.equals(car.getWarehouse()))
            			.collect(Collectors.toList());
        		CarrierTime time = this.carrierTimes.stream()
            			.filter(t -> t.targetState.equals(car.targetState) && t.warehouse.equals(car.warehouse))
            			.findFirst().orElse(null);
        		
        		
        		
        		System.out.println("----------------------STOCK--------------------");
        		System.out.println("------PRODUCT " + stock.getItemId() + "------");
    			System.out.println("------QUANTITY " + stock.getStock() + "--------");
    			System.out.println("---------------------------------");
    			System.out.println("------------------------------------------------");
        		
        		for (DepartureTime de : departures) {
        			
        			System.out.println("------------------------------");
        			System.out.println("------DEPARTURE " + de.warehouse + "------");
        			System.out.println("------------------------------");
            		for(ShippingHour hour : de.getShippingHours()) {
            			System.out.println("--------TIME---------");
            			System.out.println(hour.getDay() + " " + hour.getTime());
            			System.out.println("---------------------");
            			System.out.println(warehouse.timeZoneOffset);
            			System.out.println(dt);
            			System.out.println(dt.plusHours(warehouse.timeZoneOffset));
                    	LocalDateTime nextDepartureDate =
                    			dt.plusHours(warehouse.timeZoneOffset).with(TemporalAdjusters.nextOrSame(hour.getDay()))
                    				.withHour(hour.getTime().getHour())
                    				.withMinute(hour.getTime().getMinute());
                    	System.out.println("------TIME DEPART------");
            			System.out.println(nextDepartureDate);
            			System.out.println("-----------------------");
            			
            			// DELIVER IN THE SAME DAY BUT NOT EARLY ENOUGH
            			if(dt.plusHours(warehouse.timeZoneOffset).isAfter(nextDepartureDate)) {
            				continue;
            			}
            			
                    	LocalDateTime nextDepartureDateUTC = nextDepartureDate.minusHours(car.getWarehouse().timeZoneOffset);
                    	
                    	System.out.println("------TIME DEPART UTC------");
            			System.out.println(nextDepartureDateUTC);
            			System.out.println("-----------------------");
            			
            			LocalDateTime deliveredGuarented = nextDepartureDateUTC.plusHours(time.carrierTime);
            			
            			System.out.println("------DELIVER GUARANTEE------");
            			System.out.println(deliveredGuarented);
            			System.out.println("-----------------------------------------");
            			System.out.println("-------------TIME TRANSPORT---------------");
            			System.out.println(time.getCarrierTime() + " FROM " + time.getWarehouse() + " TO " + time.getTargetState());
            			System.out.println("----------------------------------------");
                    	
                    	// Calculate for each one of the options the shipping experience price
                    	
                    	ShipmentInfo infoTest = new ShipmentInfo(order, car.warehouse, deliveredGuarented, selectedBox.getBoxType(), shipingPrice);
                    	System.out.println(infoTest.getShippingExperiencePrice());
                    	if (best == null) {
                    		best = infoTest;
                    	} 
                    	
            
                		// GETTER BETTER PRICE ONE
                		if (best.getTotalPrice() > infoTest.getTotalPrice()) {
                			best = infoTest;
                		} 
                		
                		System.out.println("----------------- PRICES -------------------");
                		System.out.println("------TOTAL PRICE: " + infoTest.getTotalPrice() + " ------");
                		System.out.println("------PRICE EXPERIENCE " + infoTest.getShippingExperiencePrice() + " ------");
                		System.out.println("------PRICE SHIPPING " + infoTest.getShippingPrice() + " ------");
            			System.out.println("---------------------------------");
                		
                		
            			float a = best.getTotalPrice();
            			float b = infoTest.getTotalPrice();
                		if (-0.0001 <= a-b && a-b <= 0.0001) {
                			System.out.println("--------ALERT---------");
            				System.out.println("--------ALERT---------");
            				System.out.println("--------ALERT---------");
            				System.out.println("--------ALERT---------");
                			Stock stock1 = this.initialStocks.stream()
                        			.filter(s -> s.itemId.equals(order.getItemId()) && s.warehouse.equals(Warehouse.NEW_YORK)).findFirst().orElse(null);
                			System.out.println("STOCK NEW YORK  " + stock1);
                			Stock stock2 = this.initialStocks.stream()
                        			.filter(s -> s.itemId.equals(order.getItemId()) && s.warehouse.equals(Warehouse.SAN_FRANCISCO)).findFirst().orElse(null);
                			System.out.println("STOCK SAN FRANCISCO  " + stock2);
                			if (infoTest.warehouse == Warehouse.NEW_YORK && stock1.stock >= stock2.stock) {
                				best = infoTest;
                			}
                			if (infoTest.warehouse == Warehouse.SAN_FRANCISCO && stock2.stock >= stock1.stock) {
                				best = infoTest;
                			}
                		}
            		}
            		
            	}
        	}
        	
        	final Warehouse selectedWarehouseString = best.warehouse;
        	
        	//UPDATE STOCK
        	this.initialStocks = this.initialStocks.stream()
        			.map(o ->  o.itemId.equals(order.itemId) && o.warehouse.equals(selectedWarehouseString) ?
        					new Stock(order.itemId, selectedWarehouseString, o.stock - 1) : o ).collect(Collectors.toList());
        	
        	return best;
        }

    }

    public static void main(String[] args) throws IOException {
        List<Stock> stocks = new ArrayList<>();
        List<BoxType> boxTypes = new ArrayList<>();
        List<CarrierPricing> carrierPricings = new ArrayList<>();
        List<DepartureTime> departureTimes = new ArrayList<>();
        List<CarrierTime> carrierTimes = new ArrayList<>();
        List<Item> items = new ArrayList<>();

        List<Order> orders = new ArrayList<>();

        Consumer<String> stockConsumer = input -> stocks.add(CsvParser.parseStock(input));
        Consumer<String> boxTypeConsumer = input -> boxTypes.add(CsvParser.parseBoxType(input));
        Consumer<String> carrierPricingConsumer = input -> carrierPricings.add(CsvParser.parseCarrierPricings(input));
        Consumer<String> departureTimeConsumer = input -> departureTimes.add(CsvParser.parseDepartureTime(input));
        Consumer<String> carrierTimeConsumer = input -> carrierTimes.add(CsvParser.parseCarrierTime(input));
        Consumer<String> itemConsumer = input -> items.add(CsvParser.parseItem(input));
        Consumer<String> orderConsumer = input -> orders.add(CsvParser.parseOrder(input));

        BufferedWriter bw = new BufferedWriter(new FileWriter("src/text.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("src/input001.txt")), StandardCharsets.UTF_8));
        
        String inputLine;
        Consumer<String> consumer = t -> {
        };
        while ((inputLine = br.readLine()) != null) {
            switch (inputLine) {
            case "---Orders---":
                consumer = orderConsumer;
                break;
            case "---Stocks---":
                consumer = stockConsumer;
                break;
            case "---BoxTypes---":
                consumer = boxTypeConsumer;
                break;
            case "---CarrierPricing---":
                consumer = carrierPricingConsumer;
                break;
            case "---DepartureTimes---":
                consumer = departureTimeConsumer;
                break;
            case "---CarrierTimes---":
                consumer = carrierTimeConsumer;
                break;
            case "---Items---":
                consumer = itemConsumer;
                break;
            default:
                consumer.accept(inputLine);
                break;
            }
        }
        br.close();

        Collections.sort(orders, new Comparator<Order>() {
            @Override
            public int compare(Order arg0, Order arg1) {
                return arg0.getOrderDate().compareTo(arg1.getOrderDate());
            }
        });

        ShipmentsManager shipmentsManager = new ShipmentsManager(items, boxTypes, carrierPricings, departureTimes,
                carrierTimes, stocks);

        List<ShipmentInfo> shipmentInfos = orders.stream().map(shipmentsManager::findBestShipmentInfo)
                .collect(Collectors.toList());
        

        Collections.sort(shipmentInfos, new Comparator<ShipmentInfo>() {
            @Override
            public int compare(ShipmentInfo arg0, ShipmentInfo arg1) {
                return arg0.getOrder().getOrderDate().compareTo(arg1.getOrder().getOrderDate());
            }
        });

        Float totalShipmentPrice = 0.0f;

        for (ShipmentInfo shipmentInfo : shipmentInfos) {
            totalShipmentPrice += shipmentInfo.shippingPrice + shipmentInfo.getShippingExperiencePrice();
        }
        StringBuilder output = new StringBuilder();
        output.append(totalShipmentPrice + "\n");
        for (ShipmentInfo shipmentInfo : shipmentInfos) {
            output.append(shipmentInfo.toCsvLine() + "\n");
        }
        bw.write(output.toString());
        bw.close();
        System.out.println("Your total shipment price is: " + totalShipmentPrice);
    };

}
