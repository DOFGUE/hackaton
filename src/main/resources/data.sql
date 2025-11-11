-- Insertar estados iniciales si no existen
INSERT IGNORE INTO estados (idEstado, nombreEstado) VALUES (1, 'ESPERA');
INSERT IGNORE INTO estados (idEstado, nombreEstado) VALUES (2, 'EN VALIDACION');
INSERT IGNORE INTO estados (idEstado, nombreEstado) VALUES (3, 'EN EJECUCION');
INSERT IGNORE INTO estados (idEstado, nombreEstado) VALUES (4, 'SOLUCIONADO');
INSERT IGNORE INTO estados (idEstado, nombreEstado) VALUES (5, 'CERRADO');
