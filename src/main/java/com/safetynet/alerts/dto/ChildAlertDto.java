package com.safetynet.alerts.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChildAlertDto {

    private final List<ChildAlertChildDto> children = new ArrayList<>();

    private final List<ChildAlertFamilyMemberDto> familyMembers = new ArrayList<>();

    public ChildAlertDto(List<ChildAlertChildDto> children, List<ChildAlertFamilyMemberDto> familyMembers){
        this.children.addAll(children);
        this.familyMembers.addAll(familyMembers);
    }

}
