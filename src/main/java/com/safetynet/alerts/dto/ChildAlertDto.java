package com.safetynet.alerts.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ChildAlertDto {

    private final List<ChildAlertChildDto> children;

    private final List<ChildAlertFamilyMemberDto> familyMembers;

    public ChildAlertDto(List<ChildAlertChildDto> children, List<ChildAlertFamilyMemberDto> familyMembers){
        this.children = children;
        this.familyMembers = familyMembers;
    }

}
