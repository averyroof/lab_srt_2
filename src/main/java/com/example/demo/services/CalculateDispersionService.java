package com.example.demo.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CalculateDispersionService {

    public List<Float> parsingData(String data) {
        List<Float> listUSD = null;
        data = data.replaceAll("\\d{4}-\\d{2}-\\d{2}", "");
        Pattern p = Pattern.compile("([0-9]*[.])?[0-9]+");
        listUSD = p.matcher(data).results()
                .map(matchResult -> matchResult.group())
                .map(Float::parseFloat)
                .map(aFloat -> (float) Math.pow(Math.tan(aFloat), 7.5))
                .collect(Collectors.toList());
        return listUSD;
    }

    public Float dispersion(List<Float> listUSD) {
        Float disp = null;
        int length = listUSD.size();
        Float sum = listUSD.stream().reduce(Float::sum).get();
        Float mean = sum / length;
        disp = listUSD.stream().map(usd -> (float) Math.pow((usd - mean), 2)).reduce(Float::sum).get() / length;
        return disp;
    }
}
