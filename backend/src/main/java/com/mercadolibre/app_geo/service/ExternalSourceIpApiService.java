package com.mercadolibre.app_geo.service;

import com.mercadolibre.app_geo.dto.IpApiResponse;
import com.mercadolibre.app_geo.exception.BusinessException;
import com.mercadolibre.app_geo.exception.messages.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Service
public class ExternalSourceIpApiService {

    private final RestTemplate restTemplate;

    @Value("${ipapi.api.key}")
    private String apiKey;

    @Autowired
    public ExternalSourceIpApiService(
            RestTemplate restTemplate
    ){
        this.restTemplate = restTemplate;
    }

    public IpApiResponse findDataByIp(String ip) {

        String url = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("api.ipapi.com")
                .path("/api/" + ip)
                .queryParam("access_key", apiKey)
                .queryParam("language", "es")
                .build()
                .toUriString();

        if(isValidIp(ip)){
            ResponseEntity<IpApiResponse> response = restTemplate.getForEntity(url, IpApiResponse.class);
            return response.getBody();
        }
        else{
            throw new BusinessException(ErrorMessage.INVALID_IP.getCode(), ErrorMessage.INVALID_IP.getMessage(), ip);
        }

    }

    private boolean isValidIp(String ip){

        log.info("Validando si la IP ingresada es publica y posee un formato valido");
        try {
            InetAddress inet = InetAddress.getByName(ip);

            return !(inet.isAnyLocalAddress() ||
                    inet.isLoopbackAddress() ||
                    inet.isSiteLocalAddress() ||
                    inet.isLinkLocalAddress() ||
                    inet.isMulticastAddress());

        } catch (UnknownHostException e) {
            log.error("IP Invalida: " + e.getMessage());
            return false;
        }
    }

}
