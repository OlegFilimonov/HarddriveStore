/*
 * Copyright (c) 2016 Oleg Filimonov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.olegfilimonov.harddrivestore;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

/**
 * @author Oleg Filimonov
 */
@ViewScoped
@ManagedBean(name = "driveView")
public class NewHardDriveView implements Serializable {

    private boolean adding = false;

    private String name;
    private int manufacturer_id;
    private String country;
    private String driveInterface;
    private String formFactor;

    private Integer size;
    private Integer rotationSpeed;
    private Integer retailPrice;
    private Integer quantity;

    @ManagedProperty("#{storeService}")
    private StoreService storeService;

    public void addNewHardDrive() {

        String request = String.format("INSERT INTO" +
                        "  dbo.hard_drive (manufacturer_id, name, size, interface, rotation_speed, form_factor, price)" +
                        " VALUES (%d, '%s', %d, '%s', %d, '%s', %d);",
                manufacturer_id,
                name,
                size,
                driveInterface,
                rotationSpeed,
                formFactor,
                retailPrice
        );

        storeService.executeSql(request);

        name = "";
        country = "";
        driveInterface = "";
        formFactor = "";
        size = null;
        rotationSpeed = null;
        retailPrice = null;
        quantity = null;
    }

    public void toggleAdding() {
        adding = !adding;
    }

    public boolean isAdding() {
        return adding;
    }

    public void setAdding(boolean adding) {
        this.adding = adding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(int manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    public StoreService getStoreService() {
        return storeService;
    }

    public void setStoreService(StoreService storeService) {
        this.storeService = storeService;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDriveInterface() {
        return driveInterface;
    }

    public void setDriveInterface(String driveInterface) {
        this.driveInterface = driveInterface;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(Integer rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public Integer getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Integer retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
