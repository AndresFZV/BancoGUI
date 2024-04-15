package co.edu.uniquindio.banco.modelo;

import lombok.*;

/**
 * Clase que representa un usuario del banco
 * @version 1.0
 * @autor caflorezvi
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @EqualsAndHashCode.Include
    private final String numeroIdentificacion;

    private final String nombre;
    private final String direccion;
    private final String correoElectronico;
    private final String contrasena;
    private CuentaAhorros cuenta;

    public Usuario(String numeroIdentificacion, String nombre, String direccion, String correoElectronico, String contrasena) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
    }


}
