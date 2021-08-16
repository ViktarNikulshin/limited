package com.beta.limited.servise.impl;

import com.beta.limited.entity.Address;
import com.beta.limited.entity.Reference;
import com.beta.limited.servise.AddressService;
import com.beta.limited.servise.ReferenceService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final ReferenceService referenceService;


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
                    Pattern p = Pattern.compile("(\\d+)");
                    Matcher m = p.matcher(builder.toString());
                    if (m.find()) {
                        int j = builder.toString().indexOf(m.group(0));
                        address.setHouse(Integer.parseInt(m.group(0)));
                        if(builder.toString().contains("д.")){
                            address.setStreet(builder.toString().substring(0, j - 3));
                        }else {
                            address.setStreet(builder.toString().substring(0, j -1));
                        }
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
            return address;// итоговая широта и долгота
        } catch (JSONException e) {
            System.err.println(e);
            return address;
        }
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

