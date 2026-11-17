package es.safareyes.parkcontrol.dtos.response;

import es.safareyes.parkcontrol.modelos.TipoPromocion;

public record PromocionResponse(

        Integer id,
        String nombre,
        Integer minDescuento,
        TipoPromocion tipo

) {}
