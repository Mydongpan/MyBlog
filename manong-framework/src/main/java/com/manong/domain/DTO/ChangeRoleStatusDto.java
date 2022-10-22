package com.manong.domain.DTO;

public class ChangeRoleStatusDto {

    private Long id;
    private String status;

    public ChangeRoleStatusDto() {
    }

    public ChangeRoleStatusDto(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
