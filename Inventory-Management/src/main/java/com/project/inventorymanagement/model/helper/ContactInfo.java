package com.project.inventorymanagement.model.helper;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Embeddable
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfo {
    private String contactName;
    @Email @NotNull
    private String email;
    private String phone;
    private String extra;
}
