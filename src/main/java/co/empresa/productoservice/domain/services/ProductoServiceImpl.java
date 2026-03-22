package co.empresa.productoservice.domain.services;

import co.empresa.productoservice.domain.model.Producto;
import co.empresa.productoservice.domain.repositories.IProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase que implementa los métodos de la interfaz IProductoService
 * para realizar las operaciones de negocio sobre la entidad Producto
 */
@Service
public class ProductoServiceImpl implements IProductoService {

    private final IProductoRepository repo;

    public ProductoServiceImpl(IProductoRepository repo) { this.repo = repo; }

    @Transactional public Producto save(Producto p) { return repo.save(p); }
    @Transactional public Producto update(Producto p) { return repo.save(p); }
    @Transactional public void delete(Producto p) { repo.delete(p); }

    @Transactional(readOnly = true) public Producto findById(Long id) { return repo.findById(id).orElse(null); }
    @Transactional(readOnly = true) public List<Producto> findAll() { return repo.findAll(); }
    @Transactional(readOnly = true) public Page<Producto> findAll(Pageable pageable) { return repo.findAll(pageable); }
}