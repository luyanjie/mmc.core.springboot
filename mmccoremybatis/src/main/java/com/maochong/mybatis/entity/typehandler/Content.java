package com.maochong.mybatis.entity.typehandler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Content implements Serializable {
    private String province;
    private String city;

    public Content(String address) {
        if (address != null) {
            String[] segments = address.split(",");
            if (segments.length > 1) {
                this.province = segments[0];
                this.city = segments[1];
            }
            else if (segments.length > 0) {
                this.city = segments[0];
            }
        }
    }

    @Override
    public String toString() {
        return this.province + "," + this.city;
    }
}

