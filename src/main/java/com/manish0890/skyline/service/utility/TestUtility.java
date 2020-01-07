package com.manish0890.skyline.service.utility;

import com.manish0890.skyline.service.document.Customer;
import com.manish0890.skyline.service.document.dto.ErrorDetail;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtility {
    private static final SecureRandom RANDOM = new SecureRandom();

    private TestUtility() {
    }

    public static String randomId() {
        return randomLong().toString();
    }

    public static Long randomLong() {
        return RANDOM.longs(1L, Long.MAX_VALUE).findAny().getAsLong();
    }

    public static int randomInt() {
        return randomInt(1, Integer.MAX_VALUE);
    }

    public static int randomInt(int start, int stop) {
        return RANDOM.ints(start, stop).findAny().getAsInt();
    }

    public static String randomAlphabetic() {
        return randomAlphabetic(10);
    }

    public static String randomAlphabetic(int characterCount) {
        return RandomStringUtils.randomAlphabetic(characterCount);
    }

    public static String randomAlphanumeric(int characterCount) {
        return RandomStringUtils.randomAlphanumeric(characterCount);
    }

    public static String randomNumeric(int characterCount) {
        return RandomStringUtils.randomNumeric(characterCount);
    }

    public static Date randomDate() {
        return new Date(randomLong());
    }

    public static HttpMethod randomHttpMethod() {
        HttpMethod httpMethod = null;
        List<HttpMethod> httpMethods = Arrays.asList(HttpMethod.values());
        if (!httpMethods.isEmpty()) {
            int index = RANDOM.nextInt(httpMethods.size());
            httpMethod = httpMethods.get(index);
        }
        return httpMethod;
    }

    public static HttpStatus randomHttpStatus() {
        List<HttpStatus> httpStatuses = filterOutDeprecatedValuesFromEnum(HttpStatus.class);
        return httpStatuses.get(RANDOM.nextInt(httpStatuses.size()));
    }

    public static HttpStatus randomSuccessHttpStatus() {
        List<HttpStatus> httpStatuses = filterOutDeprecatedValuesFromEnum(HttpStatus.class);
        httpStatuses.removeIf(httpStatus -> !httpStatus.is2xxSuccessful());
        return httpStatuses.get(RANDOM.nextInt(httpStatuses.size()));
    }

    public static HttpStatus randomErrorHttpStatus() {
        List<HttpStatus> httpStatuses = filterOutDeprecatedValuesFromEnum(HttpStatus.class);
        httpStatuses.removeIf(httpStatus -> !httpStatus.isError());
        return httpStatuses.get(RANDOM.nextInt(httpStatuses.size()));
    }

    public static HttpStatus randomErrorHttpStatus(HttpStatus... notThese) {
        HttpStatus httpStatus = randomErrorHttpStatus();

        if (notThese != null) {
            for (HttpStatus temp : notThese) {
                if (temp.equals(httpStatus)) {
                    return randomErrorHttpStatus(notThese);
                }
            }
        }

        return httpStatus;
    }

    public static ErrorDetail randomErrorDetail() {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setStatus(randomHttpStatus().value());
        errorDetail.setError(randomAlphabetic(50));
        errorDetail.setMessage(randomAlphabetic(40));
        errorDetail.setTrace(randomAlphabetic(40));
        errorDetail.setTimestamp(randomDate().toString());
        return errorDetail;
    }

    public static Customer customerWithTestValues(){
        Customer customer = new Customer();
        customer.setName(randomAlphabetic());
        customer.setCity(randomAlphabetic());
        customer.setDob(randomDate());
        customer.setZipcode(randomNumeric(5));
        customer.setPhone(randomNumeric(10));
        return customer;
    }


    /*
     * Retrieve enum values without the @Deprecated annotation
     */
    private static <E extends Enum<E>> List<E> filterOutDeprecatedValuesFromEnum(Class<E> enumClass) {
        return EnumSet.allOf(enumClass).stream().filter(value -> {
            try {
                Field field = enumClass.getField(value.name());
                return !field.isAnnotationPresent(Deprecated.class);
            } catch (Exception e) {
                return false;
            }
        }).collect(Collectors.toList());
    }
}