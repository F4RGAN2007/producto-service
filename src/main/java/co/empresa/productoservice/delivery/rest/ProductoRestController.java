package co.empresa.productoservice.delivery.rest;

import co.empresa.productoservice.domain.exception.ProductoNoEncontradoException;
import co.empresa.productoservice.domain.exception.ValidationException;
import co.empresa.productoservice.domain.model.Producto;
import co.empresa.productoservice.domain.services.IProductoService;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/producto-service")
public class ProductoRestController {

    private final IProductoService productoService;

    private static final String MENSAJE = "mensaje";
    private static final String PRODUCTO = "producto";
    private static final String PRODUCTOS = "productos";

    public ProductoRestController(IProductoService productoService) {
        this.productoService = productoService;
    }

    // ✅ CREATE
    @PostMapping("/productos")
    public ResponseEntity<Map<String, Object>> save(
            @Valid @RequestBody Producto producto,
            BindingResult result) {

        if (result.hasErrors()) {
            throw new ValidationException(result);
        }

        Producto creado = productoService.save(producto);

        Map<String, Object> resp = new HashMap<>();
        resp.put(MENSAJE, "El producto ha sido creado con éxito");
        resp.put(PRODUCTO, creado);

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // ✅ GET BY ID
    @GetMapping("/productos/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Producto p = productoService.findById(id);

        if (p == null) {
            throw new ProductoNoEncontradoException(id);
        }

        return ResponseEntity.ok(Map.of(PRODUCTO, p));
    }

    // ✅ GET ALL
    @GetMapping("/productos")
    public ResponseEntity<Map<String, Object>> getProductos() {
        List<Producto> productos = productoService.findAll();
        return ResponseEntity.ok(Map.of(PRODUCTOS, productos));
    }

    // ✅ UPDATE (IMPORTANTE: ahora incluye stock y foto)
    @PutMapping("/productos/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Long id,
            @Valid @RequestBody Producto cambios,
            BindingResult result) {

        if (result.hasErrors()) {
            throw new ValidationException(result);
        }

        Producto existente = productoService.findById(id);

        if (existente == null) {
            throw new ProductoNoEncontradoException(id);
        }

        // 🔥 Actualizar TODOS los campos relevantes
        existente.setNombre(cambios.getNombre());
        existente.setDescripcion(cambios.getDescripcion());
        existente.setPrecio(cambios.getPrecio());
        existente.setStock(cambios.getStock());
        existente.setFoto(cambios.getFoto());

        Producto actualizado = productoService.update(existente);

        return ResponseEntity.ok(Map.of(
                MENSAJE, "Producto actualizado con éxito",
                PRODUCTO, actualizado
        ));
    }

    // ✅ DELETE
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

        Producto existente = productoService.findById(id);

        if (existente == null) {
            throw new ProductoNoEncontradoException(id);
        }

        productoService.delete(existente);

        return ResponseEntity.ok(Map.of(
                MENSAJE, "Producto eliminado con éxito"
        ));
    }
}