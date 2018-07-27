package cl.citiaps.spring.backend.rest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import cl.citiaps.spring.backend.MongoConnection;
import cl.citiaps.spring.backend.entities.Political;
import cl.citiaps.spring.backend.repository.PoliticalRepository;

@RestController  
@RequestMapping("/politicals")
public class PoliticalService {
	
	@Autowired
	private PoliticalRepository politicalRepository;
		
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Political> getAllPoliticals() {

		List<Political> politicalsList = new ArrayList<Political>();

		double totalPositivos = 0.0;
		double totalNegativos = 0.0;

		double max = 0;

		Iterable<Political> politicals = politicalRepository.findAll();

		for(Political political:politicals)
		{
			if(max < political.getComentariosNegativos())
			{
				max = political.getComentariosNegativos();
			}
			if(max < political.getComentariosPositivos())
			{
				max = political.getComentariosPositivos();
			}
			totalPositivos = totalPositivos + political.getComentariosPositivos();
			totalNegativos = totalNegativos + political.getComentariosNegativos();
		}
		for(Political political:politicals)
		{
			political.setComentariosPositivos( this.roundTwoDecimals(( political.getComentariosPositivos() * 100.0 / (totalNegativos + totalPositivos) )) );
			political.setComentariosNegativos( this.roundTwoDecimals((-political.getComentariosNegativos() * 100.0 / (totalNegativos + totalPositivos) )) );
		}
		for(Political political:politicals)
		{
			politicalsList.add(political);
		}

		Political falso = new Political();

		falso.setNombre("Margen");
		falso.setComentariosNegativos(this.roundTwoDecimals(( -max * 100.0 / (totalNegativos + totalPositivos) )));
		falso.setComentariosPositivos(this.roundTwoDecimals(( max * 100.0 / (totalNegativos + totalPositivos) )));
		falso.setComentariosNeutros(10);
		falso.setDescripcion("Este es el margen");

		politicalsList.add(falso);

		return politicalsList; 

		/*
		double totalPositivos = 0.0;
		double totalNegativos = 0.0;

		Iterable<Artista> artistas = artistaRepository.findAll();
		for(Artista artista:artistas)
		{
			totalPositivos = totalPositivos + artista.getComentariosPositivos();
			totalNegativos = totalNegativos + artista.getComentariosNegativos();
		}
		for(Artista artista:artistas)
		{
			artista.setComentariosPositivos( this.roundTwoDecimals(( artista.getComentariosPositivos() * 100.0 / (totalNegativos + totalPositivos) )) );
			artista.setComentariosNegativos( this.roundTwoDecimals((-artista.getComentariosNegativos() * 100.0 / (totalNegativos + totalPositivos) )) );
		}
		return artistas;
		*/
	}
	
	/*public void updateKeyWords(){
		Iterable<Political> politicals = getAllPoliticals();
		
		try{
			FileWriter fichero = new FileWriter("words.dat");
			FileWriter ficheroverificador = new FileWriter("verificador.txt");
			ficheroverificador.write("true");
			for(Political political:politicals){
				fichero.write(political.getNombre()+ "\n");
			}
			
			fichero.close();
			ficheroverificador.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}*/
	//@RequestMapping(value = "/restart", method = RequestMethod.GET)
	//@ResponseBody
	//public  void restart() {
		//updateKeyWords();
	//}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public  Political findOne(@PathVariable("id") Integer id) {
		
		return politicalRepository.findOne(id);
	}
	//@RequestMapping(value = "/{id}/{idGenero}/{idUsuario}", method = RequestMethod.PUT)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public void update(@PathVariable("id") Integer id, @RequestBody Political resource) {//public void update(@PathVariable("id") Integer id, @PathVariable("idGenero") Integer idGenero, @PathVariable("idUsuario") Integer idUsuario, @RequestBody Artista resource) {
		Political political = politicalRepository.findOne(id);
		//Genero genero = generoRepository.findOne(idGenero);
		//Usuario usuario = usuarioRepository.findOne(idUsuario);
		political.setComentariosNegativos(0);
		political.setComentariosPositivos(0);
		political.setComentariosNeutros(0);
		political.setDescripcion(resource.getDescripcion());
		political.setNombre(resource.getNombre());
		//artista.setUsuario(usuario);
		//artista.setGenero(genero);
		politicalRepository.save(political);
	}
	
	//@RequestMapping(value = "/{idgenero}/{idusuario}", method = RequestMethod.POST)
	@RequestMapping(value = "/{idgenero}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Political create(@RequestBody Political resource) {
//		public Political create(@PathVariable("idgenero") Integer idgenero, @PathVariable("idusuario") Integer idusuario,@RequestBody Artista resource) {
		//Genero genero = generoRepository.findOne(idgenero);
		//Usuario usuario = usuarioRepository.findOne(idusuario);
		//resource.setUsuario(usuario);
		//resource.setGenero(genero);
	    return politicalRepository.save(resource);
	}

	public double roundTwoDecimals(double d)
	{
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d).replace(',', '.'));
	}

	@RequestMapping(value = "/total", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> total()
	{	
		double total = 0;
		double positivos = 0;
		double negativos = 0;
		MongoConnection mc = new MongoConnection("twitter2", "tweet");
		mc.connect();
		DBCursor cursor = mc.getTweets();
    	while (cursor.hasNext()) {
    		DBObject cur = cursor.next();
    		System.out.println(cur.get("analysis"));
    		if(cur.get("analysis").toString().equals("Negative")) {
    			negativos = negativos + 1;
    			total = total + 1;
    		}
    		if(cur.get("analysis").toString().equals("Positive")) {
    			positivos = positivos + 1;
    			total = total + 1;
    		}
    		if(cur.get("analysis").toString().equals("Neutral")) {
    			positivos = positivos + 1;
    			total = total + 1;
    		}
    	}
    	
		//Iterable<Political> politicals = politicalRepository.findAll();

		//for(Political political:politicals)
		//{
			//positivos = positivos + political.getComentariosPositivos();
			//negativos = negativos + political.getComentariosNegativos();
		//}

		//total = positivos + negativos;

		return mapTriple("total", total, "positivos", positivos, "negativos", negativos);
	}

	private Map<String, Object> mapTriple(String key1, Object value1, String key2, Object value2, String key3, Object value3) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put(key1, value1);
		result.put(key2, value2);
		result.put(key3, value3);
		return result;
	}
}
	 
