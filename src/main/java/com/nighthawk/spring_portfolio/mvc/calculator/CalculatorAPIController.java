package com.nighthawk.spring_portfolio.mvc.calculator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/calculator")
public class CalculatorAPIController {

    @GetMapping("/{expression}")
    public ResponseEntity<String> getResult(@PathVariable String expression) {

        // Creating object calc from the Calculator class
        Calculator calc = new Calculator(expression);
        // Calls toString
        String result = calc.toString();
        if (result != null) {
            return new ResponseEntity<String>(result, HttpStatus.OK);
        }

        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);       
    }

}