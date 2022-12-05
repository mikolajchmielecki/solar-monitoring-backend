/*
 * SolarMonitoring
 * Copyright (C) 2022 Mikołaj Chmielecki
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * LICENSE file in root directory contains a copy of the GNU General Public License.
 */

package pl.edu.pwr.solarmonitoring.model;

import lombok.*;
import pl.edu.pwr.solarmonitoring.utils.EncryptionUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "counters")
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String login;

    @Column
    private String password;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "counter")
    private List<RemittedHistoryData> remitEnergy;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "counter")
    private List<ChargedHistoryData> chargeEnergy;

    public String getPassword() {
        return password != null ? EncryptionUtils.decrypt(password) : null;
    }

    public void setPassword(String password) {
        this.password = EncryptionUtils.encrypt(password);
    }

}
