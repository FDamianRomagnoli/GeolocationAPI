package com.mercadolibre.app_geo.service;

import com.mercadolibre.app_geo.dto.IpApiResponse;
import com.mercadolibre.app_geo.exception.BusinessException;
import com.mercadolibre.app_geo.exception.messages.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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
    ) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("ipDataCache")
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

        if (isValidIp(ip)) {
            log.info("IP Valida, consultando por informacion a Api externa");

            ResponseEntity<IpApiResponse> response = restTemplate.getForEntity(url, IpApiResponse.class);
            IpApiResponse result = response.getBody();

            if (!hasCountryNameAndCode(result) || !hasLatitudeAndLongitude(result)) {
                log.error("Api externa no devolvio correctamente datos claves como país y codigo");
                throw new BusinessException(ErrorMessage.IP_ERROR_API.getCode(), ErrorMessage.IP_ERROR_API.getMessage());
            }

            return result;
        }
        else{
            log.error("IP Invalida");
            throw new BusinessException(ErrorMessage.INVALID_IP.getCode(), ErrorMessage.INVALID_IP.getMessage(), ip);
        }

    }

    private boolean isValidIp(String ip) {
        log.info("Validando si la IP ingresada es pública y posee un formato válido");

        log.info("Validando formato IPv4 de Ip Ingresada: {}", ip);
        String ipv4Regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}$";
        if (!ip.matches(ipv4Regex)) {
            log.error("IP con formato inválido: " + ip);
            return false;
        }

        try {
            InetAddress inet = InetAddress.getByName(ip);
            return !(inet.isAnyLocalAddress()
                    || inet.isLoopbackAddress()
                    || inet.isSiteLocalAddress()
                    || inet.isLinkLocalAddress()
                    || inet.isMulticastAddress());

        } catch (UnknownHostException e) {
            log.error("IP inválida: " + e.getMessage());
            return false;
        }
    }

    private Boolean hasCountryNameAndCode(IpApiResponse source){
        log.info("ISO CODE: " + source.getCountry_code());
        return source.getCountry_name() != null && source.getCountry_code() != null;
    }

    private Boolean hasLatitudeAndLongitude(IpApiResponse source){
        return source.getLongitude() != null && source.getLatitude() != null;
    }

}
