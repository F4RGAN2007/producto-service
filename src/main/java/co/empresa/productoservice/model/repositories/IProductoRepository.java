package co.empresa.productoservice.model.repositories;

import co.empresa.productoservice.model.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface que hereda de CrudRepository para realizar las
 * operaciones de CRUD sobre la entidad Producto
 */

/**
 * Por qué: JpaRepository extiende PagingAndSortingRepository,
 * dándote findAll(Pageable) y ordenación “out-of-the-box”. (Denkitronik)
 */
public interface IProductoRepository extends JpaRepository<Producto, Long> { }