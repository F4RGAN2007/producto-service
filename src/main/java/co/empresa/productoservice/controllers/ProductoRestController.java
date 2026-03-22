package co.empresa.productoservice.controllers;


import co.empresa.productoservice.model.entities.Producto;
import co.empresa.productoservice.model.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/v1/producto-service")
public class ProductoRestController {

    private IProductoService productoService;

    @Autowired
    public ProductoRestController(IProductoService productoService) {
        this.productoService = productoService;
    }

    /*
    @GetMapping("/productos")
    public List<Producto> getProductos(){
        return productoService.findAll();
    }
     */

    @PostMapping("/productos")
    public Producto save(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @DeleteMapping("/productos")
    public void delete(@RequestBody Producto producto){
        productoService.delete(producto);
    }

    @PutMapping("/productos")
    public Producto update(@RequestBody Producto producto){
        return productoService.update(producto);
    }

    @GetMapping("/productos/{id}")
    public Producto findById(@PathVariable("id") Long id){
        return productoService.findById(id);
    }

    @GetMapping("/producto/page/{page}")
    public ResponseEntity<Object> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4); // 4 ítems por página
        Page<Producto> productos = productoService.findAll(pageable);
        return ResponseEntity.ok(productos);
    }
}
