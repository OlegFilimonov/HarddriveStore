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

import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Filimonov
 */
@ViewScoped
@ManagedBean
public class DataView implements Serializable {

    private List<HardDrive> hardDrives;
    private List<Order> orders;

    @ManagedProperty("#{storeService}")
    private StoreService storeService;

    private boolean filteringEnabled = false;

    @PostConstruct
    public void init() {
        downloadHarddrives();
        downloadOrders();
    }

    private void downloadOrders() {
        this.orders = storeService.getAllOrders();
    }

    public List<HardDrive> getHardDrives() {
        return hardDrives;
    }

    public void downloadHarddrives() {
        this.hardDrives = storeService.getAllHardDrives();
    }

    public void setHardDrives(List<HardDrive> hardDrives) {
        this.hardDrives = hardDrives;
    }

    public boolean isFilteringEnabled() {
        return filteringEnabled;
    }

    public void setFilteringEnabled(boolean filteringEnabled) {
        this.filteringEnabled = filteringEnabled;
    }

    public List<String> getManufaturers() {
        List<String> manufaturers = new ArrayList<>();
        for (HardDrive hardDrive : hardDrives) {

            String manufacturer = hardDrive.getManufacturer();

            if (!manufaturers.contains(manufacturer))
                manufaturers.add(manufacturer);
        }

        return manufaturers;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Hard Drive Edited", ((HardDrive) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Hard Drive Cancelled", ((HardDrive) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public StoreService getStoreService() {
        return storeService;
    }

    public void setStoreService(StoreService storeService) {
        this.storeService = storeService;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
