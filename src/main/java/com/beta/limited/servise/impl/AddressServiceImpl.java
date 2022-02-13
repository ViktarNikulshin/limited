package com.beta.limited.servise.impl;

import com.beta.limited.controller.api.Geo;
import com.beta.limited.entity.Address;
import com.beta.limited.entity.Reference;
import com.beta.limited.model.response.Activity;
import com.beta.limited.model.routing.Objective;
import com.beta.limited.model.routing.Root;
import com.beta.limited.model.routing.ServicePoints;
import com.beta.limited.model.routing.Vehicle;
import com.beta.limited.repository.AddressRepository;
import com.beta.limited.servise.AddressService;
import com.beta.limited.servise.ReferenceService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {


    public static final String KEY = "585f856c-dffb-48f3-b5b2-16c2775ed625";
    private final ReferenceService referenceService;
    private final AddressRepository addressRepository;
    private final Geo geo;


    @Override
    public Address findAddressToReport(String order, Address address) throws IOException {
        List<String> cites = referenceService.getAllReferenceByType(1).stream().map(Reference::getName).collect(Collectors.toList());
        StringBuilder builder = new StringBuilder(order);
        for (String city : cites) {
            if (order.contains(city + ",")) {
                int i = order.indexOf(city);
                i += city.length();
                address.setCity(city);
                builder.delete(0, i);
                builder = new StringBuilder(builder.toString().trim());
                if (address.getFullAddress() == null) {
                    Integer house = findFirstNumber(builder.toString());
                    int j = builder.indexOf(house.toString());
                    if (j <= 7) {
                        String street = builder.toString().substring(j + house.toString().length());
                        house = findFirstNumber(street);
                        j = builder.indexOf(house.toString());
                    }
                    address.setHouse(house);
                    if (builder.toString().contains("д.")) {
                        address.setStreet(builder.toString().substring(0, j - 3));
                    } else {
                        address.setStreet(builder.toString().substring(0, j - 1));
                    }
                } else {
                    List<String> typeStreet = referenceService.getAllReferenceByType(2).stream().map(Reference::getName).collect(Collectors.toList());
                    String lastStr = builder.toString().substring(builder.lastIndexOf(" ") + 1);
                    for (String type : typeStreet) {
                        Character end = builder.charAt(builder.toString().lastIndexOf(type) + type.length());
                        if (builder.toString().contains(type)) {
                            if (end.equals(',')) {
                                builder = new StringBuilder(builder.toString().substring(0, builder.toString().lastIndexOf(type)));
                                address.setStreet(builder.toString().substring(builder.toString().lastIndexOf(',') + 1));
                            } else {
                                builder.delete(0, builder.indexOf(type));
                                address.setStreet(builder.toString().substring(type.length() + 1, builder.lastIndexOf(" ") - 1));
                            }
                            break;
                        }
                    }
                    Pattern p = Pattern.compile("(\\d+)");
                    Matcher m = p.matcher(lastStr);
                    if (m.find()) {
                        address.setHouse(Integer.parseInt(m.group(0)));
                        if (lastStr.length() != m.group(0).length()) {
                            address.setBuilding(lastStr.substring(m.group(0).length()));
                        }
                    }
                }
            }
        }
        address = seFlat(order, address);
        return getLocation(address);
    }

    public Address getLocation(Address address) throws IOException, JSONException {
        String strAddress;
        if (address.getFullAddress() == null || address.getFullAddress().equals("")) {
            if (address.getCity() == null || address.getStreet() == null || address.getHouse() == null) {
                return address;
            }
            strAddress = new StringBuilder().append(address.getCity()).append(address.getStreet()).append(address.getHouse()).toString();
        } else {
            strAddress = address.getFullAddress();
        }
        URL obj = new URL("https://geocode-maps.yandex.ru/1.x/");
        String encodeAddress = URLEncoder.encode(strAddress, "utf-8");
        String query = "format=json&apikey=3a4596d6-8c05-4473-bb30-063f47d8133c&geocode=" + encodeAddress;
        URL url = new URL(obj + "?" + query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "PostmanRuntime/7.28.2");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer resp = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            resp.append(inputLine);
        }
        in.close();
        try {
            JSONObject response = new JSONObject(resp.toString());
            JSONObject location = response.getJSONObject("response");
            JSONObject geo = location.getJSONObject("GeoObjectCollection");
            JSONObject feature = geo.getJSONArray("featureMember").getJSONObject(0);
            JSONObject geoObj = feature.getJSONObject("GeoObject");
            JSONObject metaData = geoObj.getJSONObject("metaDataProperty");
            JSONObject geocoderMetaData = metaData.getJSONObject("GeocoderMetaData");
            address.setFullAddress(geocoderMetaData.getString("text"));
            JSONObject point = geoObj.getJSONObject("Point");
            String pos = point.getString("pos");
            List<String> listPoint = Arrays.asList(pos.split(" "));
            address.setPos(listPoint.get(1) + "," + listPoint.get(0));
            address.setLat(listPoint.get(1));
            address.setLon(listPoint.get(0));
            return address;// итоговая широта и долгота
        } catch (JSONException e) {
            System.err.println(e);
            return address;
        }
    }

    @Override
    public void findOptimalRouting(List<Address> addresses, Integer id) {
        Root root = new Root();
        com.beta.limited.model.routing.Address startAddress = com.beta.limited.model.routing.Address.builder()
                .location_id("start")
                .lat(53.93450)
                .lon(27.42758)
                .build();
        com.beta.limited.model.routing.Address endAddress;
        if(id ==1) {
            endAddress = com.beta.limited.model.routing.Address.builder()
                    .location_id("end")
                    .lon(27.460853)
                    .lat(53.863038)
                    .build();
        }else {
            endAddress = com.beta.limited.model.routing.Address.builder()
                    .location_id("end")
                    .lon(27.605534)
                    .lat(53.940547)
                    .build();
        }
        List<Vehicle> vehicles = new ArrayList<>();
        Vehicle vehicleStart = new Vehicle();
        vehicleStart.setVehicle_id("start");
        vehicleStart.setStart_address(startAddress);
        vehicleStart.setEnd_address(endAddress);
        vehicles.add(vehicleStart);
        root.setVehicles(vehicles);
        List<ServicePoints> points = new ArrayList<>();
        addresses.forEach(a -> {
            points.add(ServicePoints.builder()
                    .id(a.getId().toString())
                    .name(a.getPos())
                    .address(com.beta.limited.model.routing.Address.builder()
                            .location_id(a.getId().toString())
                            .lat(Double.parseDouble(a.getLat()))
                            .lon(Double.parseDouble(a.getLon()))
                            .build())
                    .build());
        });
        root.setServicePoints(points);
        List<Objective> objectives = new ArrayList<>();
        objectives.add(Objective.builder()
                .type("min")
                .value("vehicles")
                .build());
        objectives.add(Objective.builder()
                .type("min")
                .value("completion_time")
                .build());
        root.setObjectives(objectives);
        int i = 1;
        com.beta.limited.model.response.Root response = geo.getRout(KEY, root);
        for (Activity activity : response.getSolution().getRoutes().get(0).getActivities()) {
            if (activity.getType().equals("start") || activity.getType().equals("end")) {

            } else {
                Address address = addressRepository.findById(Integer.parseInt(activity.getLocation_id())).orElse(null);
                address.setRouting(i);
                addressRepository.save(address);
                i++;
            }
        }
    }

    @Override
    public Address findAddressById(Integer id) {
        return addressRepository.findById(id).orElse(new Address());
    }

    public Address seFlat(String order, Address address) {
        if (order.contains("(K)") || order.contains("(К)")) {
            if (order.contains("кв.")) {
                StringBuilder flat = new StringBuilder(order.substring(order.indexOf("кв.")));
                address.setFlat(findFirstNumber(flat.toString()));
            }
            if (order.contains("эт.")) {
                StringBuilder entrance = new StringBuilder(order.substring(order.indexOf("эт.")));
                entrance.delete(entrance.toString().indexOf(","), entrance.length());
                address.setEntrance(findFirstNumber(entrance.toString()));
            }
            if (order.contains("под.")) {
                StringBuilder floor = new StringBuilder(order.substring(order.indexOf("под.")));
                floor.delete(floor.indexOf(","), floor.length());
                address.setFloor(findFirstNumber(floor.toString()));
            }
        }
        return address;
    }

    public Integer findFirstNumber(String text) {
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(text);
        if (m.find()) {
            return Integer.parseInt(m.group(0));
        }
        return null;
    }

}

