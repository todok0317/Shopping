package com.example.shopping.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter @Setter
@Embeddable
@NoArgsConstructor
public class Address {

    @NotBlank(message = "주소를 입력해주세요")
    private String zipcode;
    private String address;
    private String detailAddr;
    private String subAddr;

    @Builder
    public Address (String zipcode, String address, String detailAddr, String subAddr) {
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddr = detailAddr;
        this.subAddr = subAddr;
    }


}
