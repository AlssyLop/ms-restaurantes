package com.plazoleta.restaurantes.application.dto.response;

import java.util.List;

public class PlatoPageResponse {

    private List<PlatoListadoResponse> contenido;
    private int paginaActual;
    private int totalPaginas;
    private long totalElementos;

    public PlatoPageResponse() {}

    public PlatoPageResponse(List<PlatoListadoResponse> contenido,
                             int paginaActual, int totalPaginas, long totalElementos) {
        this.contenido = contenido;
        this.paginaActual = paginaActual;
        this.totalPaginas = totalPaginas;
        this.totalElementos = totalElementos;
    }

    public List<PlatoListadoResponse> getContenido() { return contenido; }
    public void setContenido(List<PlatoListadoResponse> contenido) { this.contenido = contenido; }

    public int getPaginaActual() { return paginaActual; }
    public void setPaginaActual(int paginaActual) { this.paginaActual = paginaActual; }

    public int getTotalPaginas() { return totalPaginas; }
    public void setTotalPaginas(int totalPaginas) { this.totalPaginas = totalPaginas; }

    public long getTotalElementos() { return totalElementos; }
    public void setTotalElementos(long totalElementos) { this.totalElementos = totalElementos; }
}
