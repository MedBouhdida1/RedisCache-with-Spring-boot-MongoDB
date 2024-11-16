package com.hungrycoder.order_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(collection = "order")
public class Order implements Serializable {
    @Id
    private String id;
    private String productId;
    private Date date;
}