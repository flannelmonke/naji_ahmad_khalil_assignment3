CREATE TABLE product_catalog(
		product_code INT PRIMARY KEY NOT NULL,
		product_brand VARCHAR(55) NOT NULL,
		quantity INT NOT NULL,
		unit_price NUMERIC NOT NULL
	);