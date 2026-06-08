package com.plazoleta.restaurantes.application.dto.response;

public class PlatoInfoResponse {

    private Long idPlato;
    private String nombrePlato;
    private boolean activo;

    public PlatoInfoResponse(Long idPlato, String nombrePlato, boolean activo) {
        this.idPlato = idPlato;
        this.nombrePlato = nombrePlato;
        this.activo = activo;
    }

    public Long getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(Long idPlato) {
        this.idPlato = idPlato;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
