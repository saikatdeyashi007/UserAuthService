package dev.saikat.userauthservice.pojos;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentTime {

    public String getTimeByZone(String zone) {

        //Get the current time in the asking time zone
        ZoneId istZoneId = ZoneId.of(zone);
        ZonedDateTime nowInIST = ZonedDateTime.now(istZoneId);

        //"dd-MM-yyyy HH:mm:ss z" will format the output to include the time zone abbreviation
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z");

        return nowInIST.format(formatter);
    }

}
