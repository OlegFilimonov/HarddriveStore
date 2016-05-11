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

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

/**
 * @author Oleg Filimonov
 */
@ViewScoped
@ManagedBean
public class DataView implements Serializable {

    private List<Harddrive> harddrives;

    @PostConstruct
    public void init() {
    }

    public List<Harddrive> getHarddrives() {
        return harddrives;
    }

    public void downloadHarddrives(){
        harddrives = StoreService.getAllHardDrives();
    }

    public void setHarddrives(List<Harddrive> harddrives) {
        this.harddrives = harddrives;
    }
}
