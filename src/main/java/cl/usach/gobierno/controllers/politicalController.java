package cl.usach.gobierno.controllers;

import cl.usach.gobierno.Lucene;
import cl.usach.gobierno.entities.Political;
import cl.usach.gobierno.repositories.PoliticalRepository;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import cl.usach.gobierno.MongoConnection;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

import javax.persistence.PreUpdate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController    // This means that this class is a Controller
@RequestMapping("/politicals")

public class politicalController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private PoliticalRepository politicalRepository;

    /**
     * Obtiene la informacion de todos los politicos
     * @return OK
     */
    @CrossOrigin
    @GetMapping(path="/")
    public @ResponseBody
    Iterable<Political> getAllPolitical() {
        // This returns a JSON or XML with the users
        return politicalRepository.findAll();
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseEntity<Political> createPolitical(@RequestBody Political resource) {

        if (resource.getNombre().equals("") || resource.getCargo().equals("")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(politicalRepository.save(resource), HttpStatus.CREATED);
        }
    }
    

    /**
     * Obtiene la informacion de un politico dado su id
     * @param id
     * @return OK
     */
    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Political> findOne(@PathVariable("id") Integer id){
        Political political = politicalRepository.findById(id);

        if (political == null){
            return new ResponseEntity<>(political, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(political, HttpStatus.OK);
        }
    }

    /**
     * Borra un politico dado su id
     * @param id
     * @return OK
     */
    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Political> deletePolitical(@PathVariable("id") Integer id){
        Political political = politicalRepository.findById(id);
        if (political == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            politicalRepository.delete(political);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * Permite cambiar datos de un politico dado su id
     *
     * @param resource
     * @param id
     * @return OK
     */
    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Political> editPolitical(@RequestBody Political resource, @PathVariable("id") Integer id){
        Political politicalOld = politicalRepository.findById(id);
        if (politicalOld == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            resource.setId(id);
            return new ResponseEntity<>(politicalRepository.save(resource), HttpStatus.OK);
        }
    }

    /**
     *  Obtiene el total de comentarios de un politico dado su id,
     *  los cuales son entregados al front
     */
    @CrossOrigin
    @RequestMapping(value = "/{id}/total", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Map> getTotal(@PathVariable("id") Integer id) {
        Political political = politicalRepository.findById(id);
        Lucene luca = new Lucene();
        ArrayList<Integer> aprobacion = new ArrayList<>();
        aprobacion = luca.getAnalysis(political.getNombre());
        ArrayList<Map> resultado = new ArrayList<>();
        Map<String, Object> result = new HashMap<String, Object>(2);

        result.put("aprobacion", "Positiva");
        result.put("numero", aprobacion.get(0));
        resultado.add(result);

        Map<String, Object> result2 = new HashMap<String, Object>(2);
        result2.put("aprobacion", "Negativa");
        result2.put("numero", aprobacion.get(1));
        resultado.add(result2);

        Map<String, Object> result3 = new HashMap<String, Object>(2);
        result3.put("aprobacion", "Neutra");
        result3.put("numero", aprobacion.get(2));
        resultado.add(result3);

        return resultado;
    }

    @CrossOrigin
    @RequestMapping(value = "/updateTotal", method = RequestMethod.GET)
    public HttpStatus updateTotal(){
        Lucene luca = new Lucene();
        ArrayList<Integer> aprobacion = new ArrayList<>();
        for (int i = 1; i < 25;i++){
            Political political2 = politicalRepository.findById(i);
            aprobacion = luca.getAnalysis(political2.getNombre());
            political2.setCompositivos(aprobacion.get(0));
            political2.setComnegativos(aprobacion.get(1));
            political2.setComneutros(aprobacion.get(2));
            politicalRepository.save(political2);
        }
        return HttpStatus.OK;
    }

    /**
     * Obtiene el total de comentarios de los tweet indexados
     */
    @CrossOrigin
    @RequestMapping(value = "/total/general", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Map> getTotal() {

        Lucene luca = new Lucene();
        ArrayList<Integer> aprobacion = new ArrayList<>();
        aprobacion = luca.getAnalysisGeneral();
        ArrayList<Map> resultado = new ArrayList<>();
        Map<String, Object> result = new HashMap<String, Object>(2);

        result.put("aprobacion", "Positiva");
        result.put("numero", aprobacion.get(0));
        resultado.add(result);

        Map<String, Object> result2 = new HashMap<String, Object>(2);
        result2.put("aprobacion", "Negativa");
        result2.put("numero", aprobacion.get(1));
        resultado.add(result2);

        Map<String, Object> result3 = new HashMap<String, Object>(2);
        result3.put("aprobacion", "Neutra");
        result3.put("numero", aprobacion.get(2));
        resultado.add(result3);

        return resultado;
    }
}