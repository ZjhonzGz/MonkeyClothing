-- ============================================================
-- BASE DE DATOS: Monkey Clothing (Versión Final Unificada)
-- Proyecto académico compatible con Spring + JPA + JasperReports
-- ============================================================

DROP DATABASE IF EXISTS dbmonkeyclothes;
CREATE DATABASE dbmonkeyclothes CHARACTER SET utf8mb4;
USE dbmonkeyclothes;

-- ============================================================
-- TABLA: CATEGORIAS
-- ============================================================

CREATE TABLE categorias(
    idcate      VARCHAR(10) PRIMARY KEY,
    descripcion VARCHAR(50) NOT NULL UNIQUE
);

-- ============================================================
-- TABLA: ROL
-- ============================================================

CREATE TABLE rol(
    idrol       INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL UNIQUE
);

-- ============================================================
-- TABLA: USUARIO
-- ============================================================

CREATE TABLE usuario(
    idusuario   BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombres     VARCHAR(100) NOT NULL,
    apellidos   VARCHAR(100) NOT NULL,
    usuario     VARCHAR(50)  NOT NULL UNIQUE,
    clave       VARCHAR(100) NOT NULL,
    idrol       INT NOT NULL,
    estado      TINYINT DEFAULT 1,

    CONSTRAINT fk_usuario_rol FOREIGN KEY (idrol) REFERENCES rol(idrol)
);

-- ============================================================
-- TABLA: PRODUCTO
-- Cada registro representa un producto vendible (SKU)
-- ============================================================

CREATE TABLE producto(
    idproducto      INT AUTO_INCREMENT PRIMARY KEY,
    codigo          VARCHAR(50)   NOT NULL UNIQUE,
    descripcion     VARCHAR(150)  NOT NULL,
    precio_compra   DECIMAL(10,2) NOT NULL,
    precio_venta    DECIMAL(10,2) NOT NULL,
    stock           INT NOT NULL DEFAULT 0,
    idcate          VARCHAR(10)   NOT NULL,
    fecha_registro  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_producto_categoria FOREIGN KEY (idcate) REFERENCES categorias(idcate),
    CONSTRAINT chk_stock             CHECK (stock >= 0),
    CONSTRAINT chk_precio            CHECK (precio_venta >= precio_compra)
);

-- ============================================================
-- TABLA: CLIENTE
-- email  : correo del cliente / suscriptor
-- origen : 'newsletter' si viene del landing page | NULL si es cliente directo
-- ============================================================

CREATE TABLE cliente(
    idclie          INT AUTO_INCREMENT PRIMARY KEY,
    razon_soc       VARCHAR(100) NOT NULL,
    nombre_ciudad   VARCHAR(100),
    direccion_clie  VARCHAR(150),
    telefono        VARCHAR(20),
    email           VARCHAR(150) DEFAULT NULL,
    origen          VARCHAR(20)  DEFAULT NULL
);

-- ============================================================
-- TABLA: ASESOR DE VENTA
-- ============================================================

CREATE TABLE asesor_vta(
    codasesor   INT AUTO_INCREMENT PRIMARY KEY,
    nombres     VARCHAR(80)  NOT NULL,
    email       VARCHAR(100),
    telefono    VARCHAR(20)
);

-- ============================================================
-- TABLA: VENTAS (CABECERA)
-- ============================================================

CREATE TABLE ventas(
    nroventa    BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha       DATETIME DEFAULT CURRENT_TIMESTAMP,
    idclie      INT NOT NULL,
    codasesor   INT NOT NULL,
    subtotal    DECIMAL(10,2) NOT NULL,
    igv         DECIMAL(10,2) NOT NULL,
    total       DECIMAL(10,2) NOT NULL,
    ganancia    DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_venta_cliente     FOREIGN KEY (idclie)    REFERENCES cliente(idclie),
    CONSTRAINT fk_venta_asesorventa FOREIGN KEY (codasesor) REFERENCES asesor_vta(codasesor)
);

-- ============================================================
-- TABLA: DETALLE DE VENTAS
-- El descuento de stock se gestiona desde Java
-- ============================================================

CREATE TABLE detalle_ventas(
    nroventa        BIGINT        NOT NULL,
    idproducto      INT           NOT NULL,
    cantidad_vta    INT           NOT NULL,
    precio_vta      DECIMAL(10,2) NOT NULL,
    importe_vta     DECIMAL(10,2) NOT NULL,
    precio_compra   DECIMAL(10,2) NOT NULL,

    PRIMARY KEY (nroventa, idproducto),

    CONSTRAINT fk_detventa_venta    FOREIGN KEY (nroventa)   REFERENCES ventas(nroventa),
    CONSTRAINT fk_detventa_producto FOREIGN KEY (idproducto) REFERENCES producto(idproducto)
);

-- ============================================================
-- VISTAS (PARA JASPERREPORTS)
-- ============================================================

CREATE VIEW vista_reporte_inventario AS
SELECT
    p.idproducto   AS id_producto,
    p.codigo       AS codigo,
    p.descripcion  AS producto,
    c.descripcion  AS categoria,
    p.precio_venta AS precio,
    p.stock        AS stock
FROM producto p
INNER JOIN categorias c ON p.idcate = c.idcate;

CREATE VIEW vista_reporte_ventas AS
SELECT
    v.nroventa,
    v.fecha,
    c.razon_soc  AS cliente,
    a.nombres    AS asesor,
    v.subtotal,
    v.igv,
    v.total
FROM ventas v
INNER JOIN cliente    c ON v.idclie    = c.idclie
INNER JOIN asesor_vta a ON v.codasesor = a.codasesor;

CREATE VIEW vista_reporte_ganancias AS
SELECT
    c.descripcion AS categoria,
    SUM(dv.importe_vta - (dv.cantidad_vta * dv.precio_compra)) AS ganancia
FROM detalle_ventas dv
INNER JOIN producto   p ON dv.idproducto = p.idproducto
INNER JOIN categorias c ON p.idcate      = c.idcate
GROUP BY c.descripcion;

-- ============================================================
-- DATOS INICIALES
-- ============================================================

INSERT INTO categorias VALUES
('C01', 'Polos'),
('C02', 'Poleras'),
('C03', 'Jackets'),
('C04', 'Shorts'),
('C05', 'Pantalones');

INSERT INTO rol (descripcion) VALUES
('Administrador'),
('Vendedor');

INSERT INTO usuario (nombres, apellidos, usuario, clave, idrol) VALUES
('Admin', 'Monkey Clothing', 'admin', '123', 1);

INSERT INTO asesor_vta (nombres, email, telefono) VALUES
('Carlos Mendoza', 'ventas@monkey.com', '991100001'),
('Valeria Torres',  'store@monkey.com',  '991100002');

INSERT INTO cliente (razon_soc, nombre_ciudad, direccion_clie, telefono, email, origen) VALUES
('Cliente Mostrador', 'Lima', 'Venta directa', '-', NULL, NULL);

INSERT INTO producto (codigo, descripcion, precio_compra, precio_venta, stock, idcate) VALUES
('MC-P001',  'Polo Oversized Negro',   25,  45,  50, 'C01'),
('MC-P002',  'Polo Graphic Blanco',    28,  50,  40, 'C01'),
('MC-PL01',  'Polera Hoodie Beige',    55,  95,  30, 'C02'),
('MC-J001',  'Jacket Cargo Militar',   80, 150,  20, 'C03'),
('MC-S001',  'Short Cargo Beige',      35,  65,  35, 'C04'),
('MC-PT01',  'Pantalon Cargo Wide',    65, 120,  25, 'C05');

-- ============================================================
-- FIN DEL SCRIPT
-- ============================================================