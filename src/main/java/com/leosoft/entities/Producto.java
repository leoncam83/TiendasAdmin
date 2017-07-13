/**
 * 
 */
package com.leosoft.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author leonc
 *
 */
@Entity
@Table(name="productos", uniqueConstraints= {
		@UniqueConstraint(name="unique_nombre_producto", columnNames= {"nombre", "tienda_id"})
})
public class Producto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8163882172099430086L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, length=255)
	@Size(max=255)
	@NotEmpty
	private String nombre;
	
	@Column(nullable=true, length=1000)
	@Size(max=1000)
	private String descripcion;
	
	@Column(precision=15, scale=5, nullable=false, columnDefinition="DECIMAL(15,5) NOT NULL DEFAULT 0")
	private BigDecimal precio;
		
	@ManyToOne
	@JoinColumn(name="tienda_id", referencedColumnName="id", foreignKey=@ForeignKey(name="fk_tienda_producto"))
	private Tienda tienda;
	
	public Producto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
