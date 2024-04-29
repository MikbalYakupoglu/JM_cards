package com.example.cards.controller;

import com.example.cards.constants.CardsConstants;
import com.example.cards.constants.ServerConstants;
import com.example.cards.dto.CardDto;
import com.example.cards.dto.ResponseDto;
import com.example.cards.service.CardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/cards", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CardsController {

    private final CardService cardService;
    private static final Logger logger = LogManager.getLogger(CardsController.class);

    @Autowired
    public CardsController(CardService cardService) {
        this.cardService = cardService;
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam
                                                  @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
                                                  String mobileNumber) {
        cardService.createCard(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.CARD_CREATED_SUCCESSFULLY));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CardDto> fetchCard(@RequestParam
                                             @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
                                             String mobileNumber) {
        logger.info("fetching card with mobileNumber : {}", mobileNumber);
        CardDto cardDto = cardService.fetchCard(mobileNumber);

        return new ResponseEntity<>(cardDto, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> fetchCard(@Valid @RequestBody CardDto cardDto) {
        boolean result = cardService.updateCard(cardDto);

        if (!result) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ServerConstants.INTERNAL_SERVER_ERROR));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CardsConstants.CARD_UPDATED_SUCCESSFULLY));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam
                                                  @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
                                                  String mobileNumber) {
        boolean result = cardService.deleteCard(mobileNumber);

        if (!result) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ServerConstants.INTERNAL_SERVER_ERROR));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CardsConstants.CARD_DELETED_SUCCESSFULLY));
    }
}
