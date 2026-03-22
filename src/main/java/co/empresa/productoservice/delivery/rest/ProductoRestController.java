package co.empresa.productoservice.delivery.rest;


import co.empresa.productoservice.domain.exception.ProductoNoEncontradoException;
import co.empresa.productoservice.domain.exception.ValidationException;
import co.empresa.productoservice.domain.model.Producto;
import co.empresa.productoservice.domain.services.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

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
    private static final String ERRORES = "errores";

    public ProductoRestController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/productos")
    public ResponseEntity<Map<String,Object>> save(
            @Valid @RequestBody Producto producto,
            BindingResult result) {

        if (result.hasErrors()) {
            throw new ValidationException(result); // lo captura el Advice
        }
        var creado = productoService.save(producto);
        var resp = new HashMap<String,Object>();
        resp.put("mensaje", "El producto ha sido creado con éxito!");
        resp.put("producto", creado);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Map<String,Object>> findById(@PathVariable Long id){
        var p = productoService.findById(id);
        if (p == null) throw new ProductoNoEncontradoException(id);
        return ResponseEntity.ok(Map.of("producto", p));
    }


    @GetMapping("/productos")
    public ResponseEntity<Map<String, Object>> getProductos() {
        List<Producto> productos = productoService.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put(PRODUCTOS, productos);
        return ResponseEntity.ok(response); // 200 OK
    }
/**
    @PostMapping("/productos")
    public ResponseEntity<Map<String,Object>> create(@RequestBody Producto producto) {

        Producto nuevo = productoService.save(producto);
        Map<String,Object> resp = new HashMap<>();
        resp.put(MENSAJE, "Creado con éxito");
        resp.put(PRODUCTO, nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }
 */

    @PutMapping("/productos/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Long id,
            @Valid @RequestBody Producto cambios) {

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






}
