CREATE DATABASE politicos;

USE politicos;

CREATE TABLE political(
	id					INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nombre 								VARCHAR(255) NOT NULL,
	descripcion 					VARCHAR(255) NOT NULL,
    comentarios_negativos 					DOUBLE NOT NULL,
    comentarios_positivos 					DOUBLE NOT NULL,
    comentarios_neutros  					DOUBLE NOT NULL
);

INSERT INTO political(nombre, descripcion, comentarios_negativos, comentarios_positivos, comentarios_neutros) values
	('Sebastian Piñera', 'Presidente de la Republica', 0.0, 0.0, 0.0),
	('Andrés Chadwick', 'Ministerio del Interior y Seguridad Pública', 0.0, 0.0, 0.0),
	('Roberto Ampuero', 'Ministro de Relaciones Exteriores', 0.0, 0.0, 0.0),
	('Alberto Espina', 'Ministro de Defensa Nacional', 0.0, 0.0, 0.0),
	('Felipe Larraín Bascuñán', 'Ministro de Hacienda', 0.0, 0.0, 0.0),
	('Gonzalo Blumel Mac-Iver', 'Ministro Secretario General de la Presidencia', 0.0, 0.0, 0.0),
	('Cecilia Pérez', 'Ministro Secretario General de Gobierno', 0.0, 0.0, 0.0),
	('José Ramón Valente Vías', 'Ministro de Economía, Fomento y Turismo', 0.0, 0.0, 0.0),
	('Alfredo Moreno Charme', 'Ministro de Desarrollo Social', 0.0, 0.0, 0.0),
	('Marcela Cubillos Sigall', 'Ministro de Educación', 0.0, 0.0, 0.0),
	('Hernán Larraín Fernández', 'Ministro de Justicia y Derechos Humanos', 0.0, 0.0, 0.0),
	('Nicolás Monckeberg Díaz', 'Ministro del Trabajo y Previsión Social', 0.0, 0.0, 0.0),
	('Juan Andrés Fontaine Talavera', 'Ministro de Obras Públicas', 0.0, 0.0, 0.0),
	('Emilio Santelices Cuevas', 'Ministro de Salud', 0.0, 0.0, 0.0),
	('Cristian Monckeberg Bruner', 'Ministro de Vivienda y Urbanismo', 0.0, 0.0, 0.0),
	('Antonio Walker Prieto', 'Ministro de Agricultura', 0.0, 0.0, 0.0),
	('Baldo Prokurica Prokurica', 'Ministro de Minería', 0.0, 0.0, 0.0),
	('Gloria Hutt Hesse', 'Ministra de Transportes y Telecomunicaciones', 0.0, 0.0, 0.0),
	('Felipe Ward Edwards', 'Ministro de Bienes Nacionales', 0.0, 0.0, 0.0),
	('Susana Jiménez Schuster', 'Ministra de Energía', 0.0, 0.0, 0.0),
	('María Carolina Schmidt Zaldívar', 'Ministra de Medio Ambiente', 0.0, 0.0, 0.0),
	('Pauline Kantor Pupkin', 'Ministra del Deporte', 0.0, 0.0, 0.0),
	('Isabel Plá Jarufe', 'Ministra de la Mujer y la Equidad de Género', 0.0, 0.0, 0.0),
	('Mauricio José Rojas Mullor', 'Ministro de la Cultura y Las Artes', 0.0, 0.0, 0.0);
