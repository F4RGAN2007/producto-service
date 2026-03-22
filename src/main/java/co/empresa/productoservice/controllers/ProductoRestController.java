package co.empresa.productoservice.controllers;


import co.empresa.productoservice.model.entities.Producto;
import co.empresa.productoservice.model.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/producto-service")
public class ProductoRestController {

    private final IProductoService productoService;

    // Evita "strings mágicos"
    private static final String MENSAJE = "mensaje";
    private static final String PRODUCTO = "producto";
    private static final String PRODUCTOS = "productos";

    public ProductoRestController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/productos")
    public ResponseEntity<Map<String, Object>> getProductos() {
        List<Producto> productos = productoService.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put(PRODUCTOS, productos);
        return ResponseEntity.ok(response); // 200 OK
    }

    @PostMapping("/productos")
    public ResponseEntity<Map<String, Object>> save(@RequestBody Producto producto) {
        Map<String, Object> response = new HashMap<>();
        Producto nuevoProducto = productoService.save(producto);
        response.put(MENSAJE, "El producto ha sido creado con éxito!");
        response.put(PRODUCTO, nuevoProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 Created
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Long id,
            @RequestBody Producto cambios) {

        Map<String, Object> resp = new HashMap<>();
        Producto existente = productoService.findById(id);
        if (existente == null) {
            resp.put(MENSAJE, "Producto no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
        // Actualiza campos mínimos (ejemplo simple)
        existente.setNombre(cambios.getNombre());
        existente.setDescripcion(cambios.getDescripcion());
        existente.setPrecio(cambios.getPrecio());

        Producto actualizado = productoService.update(existente);
        resp.put(MENSAJE, "Actualizado con éxito");
        resp.put(PRODUCTO, actualizado);
        return ResponseEntity.ok(resp); // 200 OK
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        Map<String, Object> resp = new HashMap<>();
        Producto existente = productoService.findById(id);
        if (existente == null) {
            resp.put(MENSAJE, "Producto no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
        productoService.delete(existente);
        resp.put(MENSAJE, "Eliminado con éxito");
        return ResponseEntity.ok(resp); // 200 OK
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Map<String, Object> resp = new HashMap<>();
        Producto p = productoService.findById(id);
        if (p == null) {
            resp.put(MENSAJE, "Producto no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
        resp.put(PRODUCTO, p);
        return ResponseEntity.ok(resp);
    }
}
