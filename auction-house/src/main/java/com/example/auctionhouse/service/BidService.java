package com.example.auctionhouse.service;

import com.example.auctionhouse.dto.BidRequestDTO;
import com.example.auctionhouse.dto.PaymentRequestDTO;
import com.example.auctionhouse.model.Account;
import com.example.auctionhouse.model.Product;
import com.example.auctionhouse.repository.BidRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.JsonObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Currency;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidService {

    private final BidRepository bidRepository;
    private final ProductService productService;
    private final AccountService accountService;
    private final PaymentService paymentService;

    @Value(value = "${app.exchange-api-key}")
    private String exchangeKey;
    @Value(value = "${app.exchange-api-url}")
    private String exchangeUrl;

    @Transactional
    public void placeBid(BidRequestDTO request, Long accountId) throws IOException {
        Product product = productService.getProductById(request.productId());
        Account account = accountService.getAccount(accountId);

        BigDecimal minimalBidValue = exchangeAmount(request.currency(), product.getCurrency(), product.getCurrentPrice().add(product.getBidIncrement()));
        if (request.bidValue().compareTo(minimalBidValue) < 0) {
            throw new IllegalArgumentException("BID VALUE MUST BE EQUAL/ABOVE MINIMAL VALUE :: "+minimalBidValue);
        }

        paymentService.createPayment(
                new PaymentRequestDTO(

                )
        );

    }

    private BigDecimal exchangeAmount(Currency input, Currency output, BigDecimal value) throws IOException {
        log.info("EXCHANGING AMOUNT :: {} :: TO CURRENCY OUTPUT :: {} ",value,output);
        String str = exchangeUrl
                .replace("${INPUT}",input.toString())
                .replace("${OUTPUT}",output.toString())
                .replace("${VALUE}",value.toString())
                .replace("${EXCHANGE_API_KEY}", exchangeKey);
        URL url = new URL(str);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        JsonObject jsonObject = JsonParser.parse(new InputStreamReader((InputStream) connection.getContent()));
        String result = jsonObject.get("result").getAsString();

        if (!result.equalsIgnoreCase("success")) {
            throw new RuntimeException("FAILED TO EXCHANGE VALUE :: RESULT ::"+jsonObject.getAsString());
        }

        return jsonObject.get("conversion_result").getAsBigDecimal();
    }
}
