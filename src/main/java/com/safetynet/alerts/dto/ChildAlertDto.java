package com.safetynet.alerts.dto;

import java.util.List;

public class ChildAlertDto {
    public List<ChildAlertChildDto> Children;

    public ChildAlertDto(List<ChildAlertChildDto> children){
        this.Children = children;
    }
}
